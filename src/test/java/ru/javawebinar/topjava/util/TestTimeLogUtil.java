package ru.javawebinar.topjava.util;

import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TestTimeLogUtil {

    private static Logger log = LoggerFactory.getLogger("TimeLogger");

    public static List<String> timeTotals = new ArrayList<>();

    public static void logInfo(Description description, String status, long nanos) {
        String message = String.format("Test %s %s, spent %d milliseconds",
                description.getMethodName(), status, TimeUnit.NANOSECONDS.toMillis(nanos));
        timeTotals.add("\n" + message);
        log.info(message);
    }

    public static void logTimeTotals() {
        log.info(timeTotals.toString());
    }


}
