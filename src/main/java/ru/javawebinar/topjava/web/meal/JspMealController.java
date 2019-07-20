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
import ru.javawebinar.topjava.web.SecurityUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.springframework.util.StringUtils.isEmpty;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
@RequestMapping(value = "/meals")
public class JspMealController extends AbstractMealController {
    @Autowired
    public JspMealController(MealService service) {
        super(service);
    }

    @GetMapping
    public String meals(Model model) {
        model.addAttribute("meals", getAll());
        return "meals";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("meal", new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000));
        return "mealForm";
    }

    @GetMapping("/update")
    public String update(Model model,
                         @RequestParam String id) {
        model.addAttribute("meal", service.get(Integer.parseInt(id), SecurityUtil.authUserId()));
        return "mealForm";
    }

    @GetMapping("/delete")
    public String delete(HttpServletRequest request) {
        delete(Integer.parseInt(request.getParameter("id")));
        return "redirect:" + request.getHeader("referer");
    }

    @GetMapping("/filter")
    public String filter(Model model,
                         @RequestParam String startDate,
                         @RequestParam String endDate,
                         @RequestParam String startTime,
                         @RequestParam String endTime) {
        if (isEmpty(startDate) && isEmpty(endDate) && isEmpty(startTime) && isEmpty(endTime)) {
            return "redirect:/meals";
        }
        model.addAttribute("meals", getBetween(
                parseLocalDate(startDate),
                parseLocalTime(startTime),
                parseLocalDate(endDate),
                parseLocalTime(endTime)));
        return "meals";
    }

    @PostMapping("/meals")
    public String createOrEdit(HttpServletRequest request,
                               @RequestParam String id) {
        Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));
        if (isEmpty(id)) {
            create(meal);
        } else {
            update(meal, Integer.parseInt(id));
        }
        return "redirect:/meals";
    }
}
