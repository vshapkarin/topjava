package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {

    public static final LocalDateTime START_DATE_TIME = LocalDateTime.of(2000, 12, 5, 12, 0);
    public static final LocalDateTime END_DATE_TIME = LocalDateTime.of(2000, 12, 5, 18, 0);
    public static final int USER_ID = START_SEQ;
    public static final int MEAL_ID = START_SEQ + 2;

    public static final Meal MEAL_1 = new Meal(MEAL_ID, LocalDateTime.of(2000, 12, 4, 10, 0), "first meal - 1 user", 700);
    public static final Meal MEAL_2 = new Meal(MEAL_ID + 1, LocalDateTime.of(2000, 12, 5, 14, 0), "second meal - 1 user", 900);
    public static final Meal MEAL_3 = new Meal(MEAL_ID + 2, LocalDateTime.of(2000, 12, 5, 19, 0), "third meal - 1 user", 1100);
    public static final Meal MEAL_4 = new Meal(MEAL_ID + 3, LocalDateTime.of(2000, 12, 6, 11, 0), "first meal - 2 user", 600);
    public static final Meal MEAL_5 = new Meal(MEAL_ID + 4, LocalDateTime.of(2000, 12, 6, 15, 0), "second meal - 2 user", 1000);
    public static final Meal MEAL_6 = new Meal(MEAL_ID + 5, LocalDateTime.of(2000, 12, 6, 23, 0), "third meal - 2 user", 300);

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertThat(actual).usingDefaultElementComparator().isEqualTo(Arrays.asList(expected));
    }
}
