package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.javawebinar.topjava.model.Meal;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
public class JspMealController extends MealController {


    @GetMapping("/meals")
    public String meals(Model model) {
        model.addAttribute("meals", super.getAll());
        return "meals";
    }

    @GetMapping("/meals/update")
    public String update(HttpServletRequest request, Model model) {
        Integer id = Integer.valueOf(request.getParameter("id"));
        model.addAttribute("meal", super.get(id));
        return "mealForm";
    }

    @GetMapping("/meals/delete")
    public String delete(HttpServletRequest request, Model model) {
        Integer id = Integer.valueOf(request.getParameter("id"));
        super.delete(id);
        return "redirect:/meals";
    }

    @GetMapping("/meals/new")
    public String create(Model model) {
        final Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
        model.addAttribute("meal", meal);
        model.addAttribute("action", "create");
        return "mealForm";
    }

    @GetMapping("/meals/filter")
    public String mealsFiltered(HttpServletRequest request,
                              Model model) {

        LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = parseLocalTime(request.getParameter("endTime"));
        model.addAttribute("meals", super.getBetween(startDate, startTime, endDate, endTime));
        return "meals";
    }


    @PostMapping("/meals")
    public String save(HttpServletRequest request) {
        Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));

        if (request.getParameter("id").isEmpty()) {
            super.create(meal);
        } else {
            super.update(meal, getId(request));
        }
        return "redirect:/meals";
    }
}
