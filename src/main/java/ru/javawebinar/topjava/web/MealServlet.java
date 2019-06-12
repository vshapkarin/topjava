package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.storage.MapMealStorage;
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

public class MealServlet extends HttpServlet {
    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = new MapMealStorage(MealsUtil.testMeals);
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
        Meal newMeal = new Meal(dateTime, description, calories);
        if (id == -1) {
            storage.save(newMeal);
        } else {
            newMeal.setId(id);
            storage.update(newMeal);
        }
        response.sendRedirect("meals");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String id = request.getParameter("id");
        if (action == null) {
            action = "";
        }
        Meal meal;
        switch (action) {
            case "delete":
                storage.delete(Integer.valueOf(id));
                response.sendRedirect("meals");
                return;
            case "create":
                meal = new Meal(null, null, 0);
                meal.setId(-1);
                break;
            case "edit":
                meal = storage.get(Integer.valueOf(id));
                break;
            default:
                request.setAttribute("meals", MealsUtil.getFilteredWithExcess(storage.getList(), LocalTime.MIN, LocalTime.MAX, 2000));
                request.getRequestDispatcher("/WEB-INF/jsp/meals.jsp").forward(request, response);
                return;
        }
        request.setAttribute("meal", meal);
        request.getRequestDispatcher("/WEB-INF/jsp/edit.jsp").forward(request, response);
    }
}
