package ru.javawebinar.topjava.util;

import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class TestTimeLogUtil {

    private static Logger log = LoggerFactory.getLogger("TimeLogger");

    private static StringBuilder logTable = new StringBuilder("\n")
            .append("Test name")
            .append("           Time spent(ms)")
            .append("\n");

    public static void logInfo(Description description, String status, long nanos) {
        String testName = description.getMethodName();
        long timeSpent = TimeUnit.NANOSECONDS.toMillis(nanos);
        logTable.append(String.format("%-20s", testName))
                .append(String.format("%-15d", timeSpent))
                .append("\n");
        log.info(String.format("Test %s %s, spent %d milliseconds",
                testName, status, timeSpent));
    }

    public static void logTimeTotals() {
        log.info(logTable.toString());
    }
}