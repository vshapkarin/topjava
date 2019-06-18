package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.Collection;

public interface MealService {

    Meal create(int userId, Meal meal);

    void delete(int userId, int id) throws NotFoundException;

    Meal get(int userId, int id) throws NotFoundException;

    void update(int userId, Meal meal) throws NotFoundException;

    Collection<Meal> getAll(int userId);

    Collection<Meal> getAllFiltered(int userId, LocalDate startDate, LocalDate endDate);
}