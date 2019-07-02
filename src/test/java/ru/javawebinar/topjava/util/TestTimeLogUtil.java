package ru.javawebinar.topjava.util;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.TimeUnit;

public class TestTimeLogUtil {

    private static Logger log = LoggerFactory.getLogger("TimeLogger");

    public static Table<String, String, String> timeTotals = HashBasedTable.create();

    private static final String COL_0 = "Test name";
    private static final String COL_1 = "Status";
    private static final String COL_2 = "Time spent(ms)";

    public static void logInfo(Description description, String status, long nanos) {
        String testName = description.getMethodName();
        String timeSpent = String.valueOf(TimeUnit.NANOSECONDS.toMillis(nanos));
        timeTotals.put(testName, COL_1, status);
        timeTotals.put(testName, COL_2, timeSpent);
        log.info(String.format("Test %s %s, spent %s milliseconds",
                testName, status, timeSpent));
    }

    public static void logTimeTotals() {
        StringBuilder builder = new StringBuilder("\n\n")
                .append(String.format("%-20s", COL_0))
                .append(String.format("%-15s%-15s", timeTotals.columnKeySet().toArray()))
                .append("\n--------------------------------------------------\n");
        for (String row : timeTotals.rowKeySet()) {
            builder.append(String.format("%-20s", row));
            for (Map.Entry<String, String> entry : timeTotals.row(row).entrySet()) {
                builder.append(String.format("%-15s", entry.getValue()));
            }
            builder.append("\n");
        }
        log.info(builder.toString());
    }
}
