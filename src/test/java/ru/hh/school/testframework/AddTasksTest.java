package ru.hh.school.testframework;

import com.codeborne.selenide.WebDriverRunner;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.hh.school.testframework.db.Database;
import ru.hh.school.testframework.ui.cp.ApplicantsPage;
import ru.hh.school.testframework.ui.cp.LoginPage;
import ru.hh.school.testframework.ui.cp.TasksPage;
import ru.hh.school.testframework.ui.main.MainPage;
import ru.hh.school.testframework.util.PropertyLoader;

import java.util.Collections;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.Assert.assertTrue;
import static ru.hh.school.testframework.db.Tables.CORE_REMINDMESUBSCRIBER;

public class AddTasksTest {

    private static Database db;
    private static String stand;
    private static String adminLogin;
    private static String adminPass;

    @BeforeClass
    public static void initialize() {
        db = new Database();
        stand = PropertyLoader.load().getProperty("stand.url");
        adminLogin = PropertyLoader.load().getProperty("admin.login");
        adminPass = PropertyLoader.load().getProperty("admin.pass");
    }

    @Before
    public void cleanUp() {
        db.cleanCoreTables();
        WebDriverRunner.clearBrowserCache();
    }

    /**
     * Шаги:
     * Ожидаемый результат:
     */
    @Test
    public void successSubmitTest() {
        String name = "ivan";
        String email = "test@mail.ru";

        LoginPage loginPage = open(stand + "/cp", LoginPage.class);
        ApplicantsPage adminPage = loginPage.login(adminLogin, adminPass);

        TasksPage tasksPage = adminPage.clickTasksTab();
        tasksPage.addTask(1, "test", "TEXT");
        tasksPage.addCaseForOpenedTask(TasksPage.CaseType.REGULAR, "in", "out");
    }


}
