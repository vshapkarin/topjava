package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.MealsUtil.DEFAULT_CALORIES_PER_DAY;
import static ru.javawebinar.topjava.util.MealsUtil.getWithExcess;
import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService service;

    public Meal create(Meal meal) {
        log.info("create userId={} new meal", authUserId());
        return service.create(authUserId(), meal);
    }

    public void delete(int id) {
        log.info("delete userId={}, mealId={}", authUserId(), id);
        service.delete(authUserId(), id);
    }

    public Meal get(int id) {
        log.info("get userId={}, mealId={}", authUserId(), id);
        return service.get(authUserId(), id);
    }

    public void update(Meal meal, int id) {
        log.info("update userId={}, mealId={}", authUserId(), meal.getId());
        assureIdConsistent(meal, id);
        service.update(authUserId(), meal);
    }

    public List<MealTo> getAll() {
        log.info("getAll userId={}", authUserId());
        return getWithExcess(service.getAll(authUserId()), DEFAULT_CALORIES_PER_DAY);
    }

    public List<MealTo> getAllFiltered(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        log.info("getAllFiltered userId={} startDate={} endDate={} startTime={} endTime={}",
                authUserId(), startDate, endDate, startTime, endTime);
        return getWithExcess(service.getAllFiltered(authUserId(),
                startDate == null ? LocalDate.MIN : startDate,
                endDate == null ? LocalDate.MAX : endDate,
                startTime == null ? LocalTime.MIN : startTime,
                endTime == null ? LocalTime.MAX : endTime), DEFAULT_CALORIES_PER_DAY);
    }
}