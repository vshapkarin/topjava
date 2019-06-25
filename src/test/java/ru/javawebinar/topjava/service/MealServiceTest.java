package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.MealTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        assertThat(service.get(MEAL_ID, USER_ID)).isEqualToComparingFieldByField(MEAL_1);
    }

    @Test(expected = NotFoundException.class)
    public void getNotYours() {
        assertThat(service.get(MEAL_ID + 3, USER_ID)).isEqualToComparingFieldByField(MEAL_4);
    }

    @Test
    public void delete() {
        service.delete(MEAL_ID, USER_ID);
        assertMatch(service.getAll(USER_ID), MEAL_3, MEAL_2);
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotYours() {
        service.delete(MEAL_ID + 3, USER_ID);
        assertMatch(service.getAll(USER_ID + 1), MEAL_6, MEAL_5);
    }

    @Test
    public void getBetweenDates() {
        assertMatch(service.getBetweenDates(START_DATE_TIME.toLocalDate(), END_DATE_TIME.toLocalDate(), USER_ID), MEAL_3, MEAL_2);
    }

    @Test
    public void getBetweenDateTimes() {
        assertMatch(service.getBetweenDateTimes(START_DATE_TIME, END_DATE_TIME, USER_ID), MEAL_2);
    }

    @Test
    public void getAll() {
        assertMatch(service.getAll(USER_ID), MEAL_3, MEAL_2, MEAL_1);
    }

    @Test
    public void update() {
        Meal updated = new Meal(MEAL_1);
        updated.setDescription("Updated first meal - 1 user");
        updated.setCalories(800000);
        service.update(updated, USER_ID);
        assertThat(service.get(MEAL_ID, USER_ID)).isEqualToComparingFieldByField(updated);
    }

    @Test(expected = NotFoundException.class)
    public void updateNotYours() {
        Meal updated = new Meal(MEAL_4);
        updated.setDescription("Updated first meal - 2 user");
        updated.setCalories(800000);
        service.update(updated, USER_ID);
        assertThat(service.get(MEAL_ID + 3, USER_ID)).isEqualToComparingFieldByField(updated);
    }

    @Test
    public void create() {
        Meal newMeal = new Meal(null, LocalDateTime.of(2000, 12, 8, 10, 0), "new meal - 1 user", 100);
        Meal created = service.create(newMeal, USER_ID);
        newMeal.setId(created.getId());
        assertMatch(service.getAll(USER_ID), newMeal, MEAL_3, MEAL_2, MEAL_1);
    }
}