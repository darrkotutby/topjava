package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.Repository;
import ru.javawebinar.topjava.repository.memoryrepository.MealMemoryRepository;
import ru.javawebinar.topjava.repository.memoryrepository.UserMemoryRepository;
import ru.javawebinar.topjava.util.FillUtil;
import ru.javawebinar.topjava.util.MealsUtil;

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
    private static final Repository<Meal> repository = MealMemoryRepository.getRepository();
    private static final Repository<User> userRepository = UserMemoryRepository.getRepository();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        FillUtil.getMealList().forEach(repository::add);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String cancelEdit = request.getParameter("CancelEdit");
        if (cancelEdit != null) {
            response.sendRedirect("meals");
            return;
        }

        String save = request.getParameter("save");


        int id = Integer.parseInt(request.getParameter("id"));
        User user = userRepository.query(u -> u.getId() == Integer.parseInt(request.getParameter("user"))).get(0);
        String dates = request.getParameter("dateTime");
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));

        LocalDateTime localDateTime = LocalDateTime.parse(dates);

        Meal meal = new Meal(localDateTime, description, calories, id, user);


        if (save != null) {
            if (id != 0) {
                repository.update(meal);
            } else {
                repository.add(meal);
            }
            response.sendRedirect("meals");
        }
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");
        String ids = request.getParameter("id");

        Meal meal;
        if (action != null) {

            int id = 0;

            if (!action.equals("new")) {
                if (ids != null) {
                    id = Integer.parseInt(ids);
                } else {
                    throw new IllegalArgumentException("Id has to be not null");
                }
            }

            int id1 = id;

            switch (action) {
                case "delete":
                    meal = repository.query(u -> u.getId() == id1).get(0);
                    repository.delete(meal);
                    response.sendRedirect("meals");
                    return;
                case "new":
                    meal = new Meal();
                    MealEdit(request, response, meal);
                    return;
                case "edit":
                    meal = repository.query(u -> u.getId() == id1).get(0);
                    MealEdit(request, response, meal);
                    return;
                default:
                    throw new IllegalArgumentException("Action " + action + " is illegal");
            }
        }

        request.setAttribute("meals", MealsUtil.getFilteredWithExceeded(repository.query(m -> true), LocalTime.MIN, LocalTime.MAX));
        request.getRequestDispatcher("meals.jsp").forward(request, response);
    }

    private void MealEdit(HttpServletRequest request, HttpServletResponse response, Meal meal) throws ServletException, IOException {
        request.setAttribute("meal", meal);
        request.setAttribute("users", userRepository.query(m -> true));
        request.getRequestDispatcher("meal.jsp").forward(request, response);
    }
}
