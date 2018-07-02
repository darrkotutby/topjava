package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.Repository;
import ru.javawebinar.topjava.repository.memoryrepository.MealMemoryRepository;
import ru.javawebinar.topjava.util.FillUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.TimeUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static Repository<Meal> repository;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        repository = new MealMemoryRepository();
        FillUtil.getMealList().forEach(repository::add);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        int id = Integer.parseInt(request.getParameter("id"));
        LocalDateTime localDateTime = LocalDateTime.parse(request.getParameter("dateTime"), TimeUtil.getFormatter());
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        Meal meal = new Meal(localDateTime, description, calories, id);
        if (id != 0) {
            repository.update(meal);
        } else {
            repository.add(meal);
        }
        response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String ids = request.getParameter("id");

        if (action == null || (action.equals("delete") || action.equals("edit")) && ids == null) {
            action = "default";
        }

        switch (action) {
            case "delete": {
                int id = Integer.parseInt(ids);
                repository.delete(id);
                response.sendRedirect("meals");
                return;
            }
            case "new": {
                mealEdit(request, response, new Meal());
                return;
            }
            case "edit": {
                mealEdit(request, response, repository.get(Integer.parseInt(ids)));
                return;
            }
            default:
                request.setAttribute("meals", MealsUtil.getFilteredWithExceeded(repository.query(m -> true), LocalTime.MIN, LocalTime.MAX, 2000));
                request.getRequestDispatcher("meals.jsp").forward(request, response);
        }
    }

    private void mealEdit(HttpServletRequest request, HttpServletResponse response, Meal meal) throws ServletException, IOException {
        request.setAttribute("meal", meal);
        request.getRequestDispatcher("meal.jsp").forward(request, response);
    }
}
