package ru.hh.school.testframework.util;

import java.util.function.Supplier;

public class Waiter {

    private static final long TIMEOUT = Long.parseLong(PropertyLoader.load().getProperty("waiter.timeout"));

    public static Boolean withWait(Supplier<Boolean> sup) {
        long end = System.currentTimeMillis() + TIMEOUT;
        while (end > System.currentTimeMillis()) {
            boolean result = sup.get();
            if (result) {
                return true;
            }
        }
        return sup.get();
    }

}
