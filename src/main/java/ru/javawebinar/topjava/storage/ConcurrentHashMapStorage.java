package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ConcurrentHashMapStorage implements Storage {
    ConcurrentMap<Integer, Meal> mealStorage;

    public ConcurrentHashMapStorage(Map<Integer, Meal> map) {
        mealStorage = new ConcurrentHashMap<>(map);
    }

    @Override
    public void save(Meal meal) {
        mealStorage.put(meal.getId(), meal);
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
