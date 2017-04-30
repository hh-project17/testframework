package ru.hh.school.testframework.ui.main;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;
import ru.hh.school.testframework.util.Waiter;

import static com.codeborne.selenide.Selenide.$;

public class MainPage {

    @FindBy(css = ".button promo__button")
    private SelenideElement buttonPromo;

    @FindBy(css = "#id_name")
    private SelenideElement fieldName;

    @FindBy(css = "#id_email")
    private SelenideElement fieldEmail;

    @FindBy(css = "#submit_form")
    private SelenideElement buttonRemind;

    @FindBy(css = ".form__character-success")
    private SelenideElement bannerSuccess;

    @FindBy(css = ".error")
    private SelenideElement labelError;

    public boolean submitRemindForm(String name, String email) {
        $(fieldName).scrollTo();
        $(fieldName).val(name);
        $(fieldEmail).val(email);
        $(buttonRemind).click();
        return Waiter.withWait(() -> $(bannerSuccess).is(Condition.visible));
    }

    public boolean isErrorLabelVisible() {
        return Waiter.withWait(() -> $(labelError).is(Condition.visible));
    }

}
