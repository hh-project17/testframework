package ru.hh.school.testframework;

import com.codeborne.selenide.WebDriverRunner;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.hh.school.testframework.db.Database;
import ru.hh.school.testframework.db.RemindStatus;
import ru.hh.school.testframework.ui.cp.ApplicantsPage;
import ru.hh.school.testframework.ui.cp.LoginPage;
import ru.hh.school.testframework.ui.cp.ReminderPage;
import ru.hh.school.testframework.ui.main.MainPage;
import ru.hh.school.testframework.util.PropertyLoader;

import java.time.LocalDateTime;
import java.util.*;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static ru.hh.school.testframework.db.Tables.CORE_REMINDMESUBSCRIBER;

/**
 * Тесты по фитче напоминания о новом наборе
 */
public class RemindMeTest {

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
     * 1. Открыть главную страницу
     * 2. Засабмитить форму напоминания
     * Ожидаемый результат:
     * Пользователь видит, что заявка обработана
     * и в бд есть запись об участнике
     */
    @Test
    public void successSubmitTest() {
        String name = "ivan";
        String email = "test@mail.ru";

        MainPage mainPage = open(stand, MainPage.class);

        assertTrue(mainPage.submitRemindForm(name, email));
        assertTrue(db.isRecordPresent(CORE_REMINDMESUBSCRIBER,
                Collections.singletonMap("email", email)));
    }

    /**
     * Шаги:
     * 1. Открыть главную страницу
     * 2. Попытаться засабмитить форму напоминания с невалидным эл.адр.
     * Ожидаемый результат:
     * Пользователь видит сообщение об ошибке
     * и в бд нет записи об участнике
     */
    @Test
    public void errorShouldAppearWhenBadEmailEntered() {
        MainPage mainPage = open(stand, MainPage.class);
        mainPage.submitRemindForm("ivan", "test.ru");
        assertTrue(mainPage.isErrorLabelVisible());
        assertFalse(db.isRecordPresent(CORE_REMINDMESUBSCRIBER,
                Collections.singletonMap("email", "test.ru")));
    }

    /**
     * Шаги:
     * 1. Открыть главную страницу
     * 2. Засабмитить форму напоминания
     * 3. Перейти в админке на закладку Напоминания
     * 4. Выделить пользователя и отправить напоминание
     * Ожидаемый результат:
     * 2. Пользователь видит, что заявка обработана
     * 3. Виден пользователь ранее засабмиченный
     * 4. Статус пользователя изменился на 'Напоминание отправлено'
     */
    @Test
    public void sendRemind() {
        String email = new Random().nextDouble() + "@mail.ru";
        String name = "ivan";

        MainPage mainPage = open(stand, MainPage.class);
        assertTrue(mainPage.submitRemindForm(name, email));

        LoginPage loginPage = open(stand + "/cp", LoginPage.class);
        ApplicantsPage applicantsPage = loginPage.login(adminLogin, adminPass);

        ReminderPage reminderPage = applicantsPage.clickRemindersTab();
        List<ReminderPage.User> users = reminderPage.getUsers();

        assertTrue(users.size() == 1);

        ReminderPage.User user = users.get(0);
        assertTrue(email.equals(user.getEmail()));
        assertTrue(name.equals(user.getName()));
        assertTrue(user.getDate().isAfter(LocalDateTime.now().minusMinutes(3)));
        assertTrue(RemindStatus.AWAIT.getStrStatus().equals(user.getStatus()));

        reminderPage.sendRemindFor(Collections.singletonList(email));

        Map<String, String> whereParams = new HashMap<>();
        whereParams.put("status", RemindStatus.SENT.getNumStatus() + "");
        whereParams.put("email", email);
        assertTrue(db.isRecordPresent(CORE_REMINDMESUBSCRIBER, whereParams));

        user = reminderPage.getUsers().get(0);
        assertTrue(email.equals(user.getEmail()));
        assertTrue(name.equals(user.getName()));
        assertTrue(RemindStatus.SENT.getStrStatus().equals(user.getStatus()));
    }

    /**
     * Шаги:
     * 1. Открыть главную страницу
     * 2. Засабмитить форму напоминания
     * 3. Перейти в админке на закладку Напоминания
     * 4. Выделить пользователя и заигнорить
     * Ожидаемый результат:
     * 2. Пользователь видит, что заявка обработана
     * 3. Виден пользователь ранее засабмиченный
     * 4. Статус пользователя изменился на 'Игнорировать' и был перенесен в
     * фильтр Игнорировать
     *
     */
    @Test
    public void ignoreUser() {
        String email = new Random().nextDouble() + "@mail.ru";
        String name = "ivan";

        MainPage mainPage = open(stand, MainPage.class);
        assertTrue(mainPage.submitRemindForm(name, email));

        LoginPage loginPage = open(stand + "/cp", LoginPage.class);
        ApplicantsPage applicantsPage = loginPage.login(adminLogin, adminPass);

        ReminderPage reminderPage = applicantsPage.clickRemindersTab();
        reminderPage.ignoreUsers(Collections.singletonList(email));

        Map<String, String> whereParams = new HashMap<>();
        whereParams.put("status", RemindStatus.IGNORE.getNumStatus() + "");
        whereParams.put("email", email);
        assertTrue(db.isRecordPresent(CORE_REMINDMESUBSCRIBER, whereParams));

        reminderPage.setIgnoreFilter();
        ReminderPage.User user = reminderPage.getUsers().get(0);
        assertTrue(email.equals(user.getEmail()));
        assertTrue(name.equals(user.getName()));
        assertTrue(RemindStatus.IGNORE.getStrStatus().equals(user.getStatus()));
    }
}
