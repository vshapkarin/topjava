package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MapMealStorage implements Storage {
    private ConcurrentMap<Integer, Meal> mealStorage;
    private AtomicInteger counter;

    public MapMealStorage(List<Meal> mealList) {
        mealStorage = new ConcurrentHashMap<>();
        mealList.forEach(this::save);
    }

    @Override
    public Meal save(Meal meal) {
        int id = counter == null ? (counter = new AtomicInteger(0)).get() : counter.incrementAndGet();
        meal.setId(id);
        return mealStorage.put(id, meal);
    }

    @Override
    public void delete(int id) {
        mealStorage.remove(id);
    }

    @Override
    public void update(Meal meal) {
        mealStorage.replace(meal.getId(), meal);
    }

    @Override
    public Meal get(int id) {
        return mealStorage.get(id);
    }

    @Override
    public List<Meal> getList() {
        return new ArrayList<>(mealStorage.values());
    }
}
