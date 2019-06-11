package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.storage.ConcurrentHashMapStorage;
import ru.javawebinar.topjava.storage.Storage;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MealServlet extends HttpServlet {
    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = new ConcurrentHashMapStorage(Stream.of(
                new Meal(LocalDateTime.of(2019, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new Meal(LocalDateTime.of(2019, Month.MAY, 30, 13, 0), "Обед", 1000),
                new Meal(LocalDateTime.of(2019, Month.MAY, 30, 20, 0), "Ужин", 600),
                new Meal(LocalDateTime.of(2019, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new Meal(LocalDateTime.of(2019, Month.MAY, 31, 13, 0), "Обед", 500),
                new Meal(LocalDateTime.of(2019, Month.MAY, 31, 20, 0), "Ужин", 490)
        ).collect(Collectors.toMap(Meal::getId, meal -> meal)));
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String requestId = request.getParameter("id");
        int id = requestId.equals("") ? -1 : Integer.valueOf(requestId);

        String requestDateTime = request.getParameter("datetime");
        LocalDateTime dateTime = requestDateTime.equals("") ? LocalDateTime.now() : LocalDateTime.parse(requestDateTime);

        String requestCalories = request.getParameter("calories");
        int calories = requestCalories.equals("") ? 0 : Integer.valueOf(requestCalories);

        String description = request.getParameter("description");
        Meal newMeal;
        if (id == -1) {
            newMeal = new Meal(dateTime, description, calories);
            storage.save(newMeal);
        } else {
            newMeal = storage.get(id);
            newMeal.setDateTime(dateTime);
            newMeal.setDescription(description);
            newMeal.setCalories(calories);
            storage.update(newMeal);
        }
        response.sendRedirect("meals");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String id = request.getParameter("id");
        if (action == null) {
            request.setAttribute("meals", MealsUtil.getFilteredWithExcessInOnePass2(storage.getList(), LocalTime.MIN, LocalTime.MAX, 2000));
            request.getRequestDispatcher("/WEB-INF/jsp/meals.jsp").forward(request, response);
            return;
        }
        Meal meal;
        switch (action) {
            case "delete":
                storage.delete(Integer.valueOf(id));
                response.sendRedirect("meals");
                return;
            case "create":
                response.sendRedirect("meals?action=edit&id=-1");
                return;
            case "edit":
                meal = storage.get(Integer.valueOf(id));
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/WEB-INF/jsp/edit.jsp").forward(request, response);
                return;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
    }
}
