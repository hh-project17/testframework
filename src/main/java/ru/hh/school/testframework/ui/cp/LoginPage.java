package ru.hh.school.testframework.ui.cp;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

public class LoginPage {

    @FindBy(css = "#id_username")
    private SelenideElement fieldLogin;

    @FindBy(css = "#id_password")
    private SelenideElement fieldPass;

    @FindBy(css = "input[type=submit]")
    private SelenideElement buttonLogin;

    public ApplicantsPage login(String login, String pass) {
        $(fieldLogin).val(login);
        $(fieldPass).val(pass);
        $(buttonLogin).click();
        return page(ApplicantsPage.class);
    }
}
