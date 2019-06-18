package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.MealsUtil.isExistAndValid;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepositoryImpl.class);
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(meal -> {
            meal.setUserId(1);
            save(1, meal);
        });
    }

    @Override
    public Meal save(int userId, Meal meal) {
        if (meal.isNew()) {
            log.info("save new meal");
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            return meal;
        }
        int mealId = meal.getId();
        log.info("update meal Id={}", mealId);
        Meal oldMeal = repository.get(mealId);
        if (isExistAndValid(userId, oldMeal)) {
            repository.replace(mealId, meal);
            return meal;
        }
        return null;
    }

    @Override
    public boolean delete(int userId, int mealId) {
        log.info("delete meal Id={}", mealId);
        Meal oldMeal = repository.get(mealId);
        return (isExistAndValid(userId, oldMeal) ? repository.remove(mealId) : null) != null;
    }

    @Override
    public Meal get(int userId, int mealId) {
        log.info("get meal Id={}", mealId);
        Meal oldMeal = repository.get(mealId);
        return isExistAndValid(userId, oldMeal) ? oldMeal : null;
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        log.info("getAll userId={}", userId);
        return repository.values().stream()
                .filter(meal -> meal.getUserId() == userId)
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Meal> getAllFiltered(int userId, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        return getAll(userId).stream()
                .filter(meal -> DateTimeUtil.isBetween(meal.getDate(), startDate, endDate)
                        && DateTimeUtil.isBetween(meal.getTime(), startTime, endTime))
                .collect(Collectors.toList());
    }
}

