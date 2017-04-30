package ru.hh.school.testframework.ui.cp;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;

import static com.codeborne.selenide.Selenide.$;

public class SchoolSettingsPage {

    @FindBy(css = "[name=pass_mark]")
    private SelenideElement fieldPassMark;

    @FindBy(css = "[name=deadline_period]")
    private SelenideElement fieldDeadline;

    @FindBy(css = ".btn")
    private SelenideElement buttonSave;

    public void setSettings(int passMark, int deadline) {
        $(fieldPassMark).val(String.valueOf(passMark));
        $(fieldDeadline).val(String.valueOf(deadline));
        $(buttonSave).click();
    }
}
