package ru.hh.school.testframework.ui.cp;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import ru.hh.school.testframework.util.Waiter;

public class TasksPage {

    public enum CaseType {
        SMOKE("Кейс на проверку работоспособности кода"),
        REGULAR("Стандартный тест-кейс");

        private String name;

        CaseType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

    }

    @FindBy(css = "a.btn")
    private SelenideElement buttonAddTask;

//    todo make separate page if needed
//    current opened task page

    @FindBy(css = "#id_block")
    private SelenideElement fieldBlock;

    @FindBy(css = "#id_title")
    private SelenideElement fieldTitle;

    @FindBy(css = "#id_text")
    private SelenideElement fieldText;

    @FindBy(css = "button.btn-primary")
    private SelenideElement buttonSave;

    @FindBy(css = "i.icon-plus-sign")
    private SelenideElement buttonAddCase;

//    todo make separate page if needed
//    case addition page

    @FindBy(css = "#id_type")
    private SelenideElement droplistType;

    @FindBy(css = "#id_input")
    private SelenideElement fieldInput;

    @FindBy(css = "#id_output")
    private SelenideElement fieldOutput;

    /**
     *
     * @return true if success add
     */
    public boolean addTask(int block, String title, String text) {
        buttonAddTask.click();
        fieldBlock.val(String.valueOf(block));
        fieldTitle.val(title);
        fieldText.val(text);
        buttonSave.click();
        return Waiter.withWait(() -> buttonAddCase.is(Condition.visible));
    }

    //todo - method not ready yet
    public void addCaseForOpenedTask(CaseType type, String input, String output) {
        buttonAddCase.click();
        Select dropdown = new Select(droplistType.should(Condition.appear));
        dropdown.selectByValue(type.getName());
        fieldInput.val(input);
        fieldOutput.val(output);
        buttonSave.click();
        buttonAddTask.should(Condition.appear);
    }
    
}
