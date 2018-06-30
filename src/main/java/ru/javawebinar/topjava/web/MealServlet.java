package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.Repository;
import ru.javawebinar.topjava.repository.memoryrepository.MealMemoryRepository;
import ru.javawebinar.topjava.util.FillUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static final Repository<Meal> repository = MealMemoryRepository.getRepository();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        FillUtil.getMealList().forEach(repository::add);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");

        request.setAttribute("meals", MealsUtil.getFilteredWithExceeded(repository.query(m -> true), LocalTime.MIN, LocalTime.MAX));
        request.getRequestDispatcher("meals.jsp").forward(request, response);
    }
}
