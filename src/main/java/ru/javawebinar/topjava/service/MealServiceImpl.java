package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.Collection;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFound;

@Service
public class MealServiceImpl implements MealService {

    private MealRepository repository;

    @Autowired
    public MealServiceImpl(MealRepository repository) {
        this.repository = repository;
    }

    @Override
    public Meal create(int userId, Meal meal) {
        meal.setUserId(userId);
        return repository.save(userId, meal);
    }

    @Override
    public void delete(int userId, int id) throws NotFoundException {
        checkNotFound(repository.delete(userId, id), "user id=" + userId + " meal id=" + id);
    }

    @Override
    public Meal get(int userId, int id) throws NotFoundException {
        return checkNotFound(repository.get(userId, id), "user id=" + userId + " meal id=" + id);
    }

    @Override
    public void update(int userId, Meal meal) throws NotFoundException {
        checkNotFound(repository.save(userId, meal), "user id=" + userId + " meal id=" + meal.getId());
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        return repository.getAll(userId);
    }

    @Override
    public Collection<Meal> getAllFiltered(int userId, LocalDate startDate, LocalDate endDate) {
        return repository.getAllFiltered(userId, startDate, endDate);
    }
}