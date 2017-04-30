package ru.hh.school.testframework.ui.cp;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Selenide.$;

public class ReminderPage {

    @FindBy(css = "tbody tr")
    private ElementsCollection userRows;

    //buttons

    @FindBy(css = "a.btn")
    private SelenideElement buttonSend;

    @FindBy(xpath = "(//a[contains(@class, 'btn')])[2]")
    private SelenideElement buttonIgnore;

    //filters

    @FindBy(xpath = "(//ul[contains(@class,'nav-list')]// a)[1]")
    private SelenideElement linkAll;

    @FindBy(xpath = "(//ul[contains(@class,'nav-list')]// a)[2]")
    private SelenideElement linkAwait;

    @FindBy(xpath = "(//ul[contains(@class,'nav-list')]// a)[3]")
    private SelenideElement linkSent;

    @FindBy(xpath = "(//ul[contains(@class,'nav-list')]// a)[4]")
    private SelenideElement linkIgnore;



    public List<User> getUsers() {
        List<User> userList = new ArrayList<>();
        for (SelenideElement user : userRows) {
            userList.add(parseRow(user));
        }
        return userList;
    }

    public void sendRemindFor(List<String> emails) {
        selectUsers(emails);
        $(buttonSend).click();
        $("#overlay").should(Condition.disappear);
    }

    public void ignoreUsers(List<String> emails) {
        selectUsers(emails);
        $(buttonIgnore).click();
        $("#overlay").should(Condition.disappear);
    }

    public void setIgnoreFilter() {
        linkIgnore.click();
    }

    private void selectUsers(List<String> emails) {
        for (SelenideElement user : userRows) {
            SelenideElement email = $(user).$$("td").get(2);
            if (emails.contains(email.getText())) {
                $(user).$$("td").get(0).click();
            }
        }
    }

    private User parseRow(SelenideElement user) {
        ElementsCollection data = $(user).$$("td");
        String name = data.get(1).getText();
        String email = data.get(2).getText();
        LocalDateTime dateTime = LocalDateTime.parse(data.get(3).getText(),
                DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
        String status = data.get(4).getText();
        return new User(name, email, dateTime, status);
    }

    public static class User {
        private final String name;
        private final String email;
        private final LocalDateTime date;
        private final String status;

        public User(String name, String email, LocalDateTime date, String status) {
            this.name = name;
            this.email = email;
            this.date = date;
            this.status = status;
        }

        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }

        public LocalDateTime getDate() {
            return date;
        }

        public String getStatus() {
            return status;
        }
    }
}
