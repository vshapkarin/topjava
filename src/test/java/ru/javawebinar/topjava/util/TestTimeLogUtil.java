package ru.javawebinar.topjava.util;

import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TestTimeLogUtil {

    private static Logger log = LoggerFactory.getLogger("TimeLogger");

    public static List<String> testTimeTotals = new ArrayList<>();

    public static void logInfo(Description description, String status, long nanos) {
        String testName = description.getMethodName();
        String message = String.format("Test %s %s, spent %d microseconds",
                testName, status, TimeUnit.NANOSECONDS.toMicros(nanos));
        testTimeTotals.add(message + "\n");
        log.info(message);
    }


}
