package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.springframework.util.StringUtils.isEmpty;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;
import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
@RequestMapping(value = "/meals")
public class JspMealController {
    @Autowired
    private MealService service;

    @GetMapping
    public String meals(Model model) {
        List<MealTo> meals = MealsUtil.getWithExcess(service.getAll(SecurityUtil.authUserId()),
                SecurityUtil.authUserCaloriesPerDay());
        model.addAttribute("meals", meals);
        return "meals";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("action", "create");
        model.addAttribute("meal", new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000));
        return "mealForm";
    }

    @GetMapping("/update")
    public String update(Model model,
                         @RequestParam String id) {
        model.addAttribute("action", "update");
        model.addAttribute("meal", service.get(Integer.parseInt(id), SecurityUtil.authUserId()));
        return "mealForm";
    }

    @GetMapping("/delete")
    public String delete(HttpServletRequest request,
                         @RequestParam String id) {
        String referrer = request.getHeader("referer");
        service.delete(Integer.parseInt(id), SecurityUtil.authUserId());
        return "redirect:" + referrer;
    }

    @GetMapping("/filter")
    public String filter(HttpServletRequest request,
                         HttpServletResponse response,
                         Model model,
                         @RequestParam String startDate,
                         @RequestParam String endDate,
                         @RequestParam String startTime,
                         @RequestParam String endTime) {
        if (isEmpty(startDate) && isEmpty(endDate) && isEmpty(startTime) && isEmpty(endTime)) {
            redirectToMeals(request, response);
        }
        List<Meal> mealsDateFiltered = service.getBetweenDates(parseLocalDate(startDate),
                parseLocalDate(endDate),
                SecurityUtil.authUserId());
        List<MealTo> mealsDateTimeFiltered = MealsUtil.getFilteredWithExcess(mealsDateFiltered,
                SecurityUtil.authUserCaloriesPerDay(),
                parseLocalTime(startTime),
                parseLocalTime(endTime));
        model.addAttribute("meals", mealsDateTimeFiltered);
        return "meals";
    }

    @PostMapping("/meals")
    public void createOrEdit(HttpServletRequest request,
                       HttpServletResponse response,
                       @RequestParam String id) {
        Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));
        int userId = SecurityUtil.authUserId();
        if (isEmpty(id)) {
            checkNew(meal);
            service.create(meal, userId);
        } else {
            assureIdConsistent(meal, Integer.parseInt(id));
            service.update(meal, userId);
        }
        redirectToMeals(request, response);
    }

    private void redirectToMeals(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.sendRedirect(request.getContextPath() + "/meals");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
