package ru.hh.school.testframework.db;

public enum RemindStatus {

    AWAIT(0, "Ожидает напоминания"),
    SENT(1, "Напоминание отправлено"),
    IGNORE(2, "Игнорировать");

    private int numStatus;
    private String strStatus;

    RemindStatus(int i, String status) {
        numStatus = i;
        strStatus = status;
    }

    public int getNumStatus() {
        return numStatus;
    }

    public String getStrStatus() {
        return strStatus;
    }
}
