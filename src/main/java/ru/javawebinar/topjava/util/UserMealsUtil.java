package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
        System.out.println("Loops");
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(23, 0), 2000)
                .forEach(System.out::println);

        System.out.println("\nStreams");
        getFilteredWithExceededStream(mealList, LocalTime.of(7, 0), LocalTime.of(23, 0), 2000)
                .forEach(System.out::println);
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> dailyCalories = new HashMap<>();
        List<UserMealWithExceed> result = new ArrayList<>();
        for (UserMeal meal : mealList) {
            LocalDateTime dateTime = meal.getDateTime();
            int calories = meal.getCalories();
            dailyCalories.merge(dateTime.toLocalDate(), calories, Integer::sum);
            if (TimeUtil.isBetween(dateTime.toLocalTime(), startTime, endTime)) {
                result.add(new UserMealWithExceed(dateTime, meal.getDescription(), calories, false));
            }
        }
        for (UserMealWithExceed meal : result) {
            if (dailyCalories.getOrDefault(meal.getDate(), 0) > caloriesPerDay) {
                meal.setExceed(true);
            }
        }
        return result;
    }

    public static List<UserMealWithExceed> getFilteredWithExceededStream(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        return mealList.stream()
                .collect(Collectors.groupingBy(a -> a.getDateTime().toLocalDate()))
                .values()
                .stream()
                .flatMap(userMeals -> userMeals.stream().mapToInt(UserMeal::getCalories).sum() > caloriesPerDay
                        ? userMeals.stream().map(meal -> new UserMealWithExceed(meal, true))
                        : userMeals.stream().map(meal -> new UserMealWithExceed(meal, false)))
                .filter(meal -> TimeUtil.isBetween(meal.getTime(), startTime, endTime))
                .collect(Collectors.toList());
    }
}

