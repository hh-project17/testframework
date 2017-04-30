package ru.hh.school.testframework.ui.cp;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

public class ApplicantsPage {

    // navigations tabs

    @FindBy(xpath = "(//ul[contains(@class,'nav-tabs')]//a)[2]")
    private SelenideElement tabStatistic;

    @FindBy(xpath = "(//ul[contains(@class,'nav-tabs')]//a)[3]")
    private SelenideElement tabSettings;

    @FindBy(xpath = "(//ul[contains(@class,'nav-tabs')]//a)[4]")
    private SelenideElement tabTasks;

    @FindBy(xpath = "(//ul[contains(@class,'nav-tabs')]//a)[5]")
    private SelenideElement tabReminders;

    //users

    @FindBy(css = "tbody tr")
    private ElementsCollection userRows;


    public StatisticPage clickStatisticTab() {
        $(tabStatistic).click();
        return page(StatisticPage.class);
    }

    public SchoolSettingsPage clickSettingsTab() {
        $(tabSettings).click();
        return page(SchoolSettingsPage.class);
    }

    public TasksPage clickTasksTab() {
        $(tabTasks).click();
        return page(TasksPage.class);
    }

    public ReminderPage clickRemindersTab() {
        $(tabReminders).click();
        return page(ReminderPage.class);
    }
}
