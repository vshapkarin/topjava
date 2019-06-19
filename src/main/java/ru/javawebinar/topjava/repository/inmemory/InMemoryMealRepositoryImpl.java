package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepositoryImpl.class);
    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(meal -> {
            save(1, meal);
        });
        MealsUtil.MEALS_2.forEach(meal -> {
            save(2, meal);
        });
    }

    @Override
    public Meal save(int userId, Meal meal) {
        if (meal.isNew()) {
            log.info("save new meal");
            meal.setId(counter.incrementAndGet());
            repository.computeIfAbsent(userId, map -> new HashMap<>()).put(meal.getId(), meal);
            return meal;
        }
        int mealId = meal.getId();
        log.info("update meal Id={}", mealId);
        Map<Integer, Meal> accMap = repository.get(userId);
        if (accMap != null && accMap.containsKey(mealId)) {
            accMap.replace(mealId, meal);
            return meal;
        }
        return null;
    }

    @Override
    public boolean delete(int userId, int mealId) {
        log.info("delete meal Id={}", mealId);
        Map<Integer, Meal> accMap = repository.get(userId);
        return (accMap != null ? accMap.remove(mealId) : null) != null;
    }

    @Override
    public Meal get(int userId, int mealId) {
        log.info("get meal Id={}", mealId);
        Map<Integer, Meal> accMap = repository.get(userId);
        return accMap != null ? accMap.get(mealId) : null;
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        log.info("getAll userId={}", userId);
        return getAllFiltered(userId, meal -> true);
    }

    @Override
    public Collection<Meal> getAllFiltered(int userId, LocalDate startDate, LocalDate endDate) {
        return getAllFiltered(userId, meal -> DateTimeUtil.isBetween(meal.getDate(), startDate, endDate));
    }

    @Override
    public Collection<Meal> getAllFiltered(int userId, Predicate<Meal> filter) {
        Map<Integer, Meal> accMap = repository.get(userId);
        return accMap == null
                ? Collections.emptyList()
                : accMap.values().stream()
                .filter(filter)
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }
}

