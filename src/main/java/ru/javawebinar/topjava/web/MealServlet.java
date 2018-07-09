package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);


    private ConfigurableApplicationContext appCtx;
    private MealRestController controller;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");

        controller = appCtx.getBean(MealRestController.class);
    }

    @Override
    public void destroy() {
        // log.info("appCtx {}" , appCtx);
        appCtx.close();
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");

        Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")), Integer.parseInt(request.getParameter("userId")));

        log.info(meal.isNew() ? "Create {}" : "Update {}", meal);

        if (meal.isNew()) {
            controller.create(meal);
        } else {
            controller.update(meal, Integer.valueOf(id));
        }

        HttpSession session = request.getSession(true);
        session.setAttribute("dateFrom", request.getParameter("dateFrom"));
        session.setAttribute("dateTo", request.getParameter("dateTo"));
        session.setAttribute("timeFrom", request.getParameter("timeFrom"));
        session.setAttribute("timeTo", request.getParameter("timeTo"));

        response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String df = request.getParameter("dateFrom");
        String dt = request.getParameter("dateTo");
        String tf = request.getParameter("timeFrom");
        String tt = request.getParameter("timeTo");


        HttpSession session = request.getSession(false);
        if (session != null) {
            if (df == null) {
                df = (String) request.getSession(false).getAttribute("dateFrom");
            } else {
                session.setAttribute("dateFrom", df);
            }

            if (dt == null) {
                dt = (String) request.getSession(false).getAttribute("dateTo");
            } else {
                session.setAttribute("dateTo", dt);
            }

            if (tf == null) {
                tf = (String) request.getSession(false).getAttribute("timeFrom");
            } else {
                session.setAttribute("timeFrom", tf);
            }

            if (tt == null) {
                tt = (String) request.getSession(false).getAttribute("timeTo");
            } else {
                session.setAttribute("timeTo", tt);
            }
        }


        String action = request.getParameter("action");

        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                log.info("Delete {}", id);
                controller.delete(id);
                response.sendRedirect("meals");
                break;
            case "create":
            case "update":
                final Meal meal = "create".equals(action) ?
                        new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000, SecurityUtil.authUserId()) :
                        controller.get(getId(request));
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                break;
            case "all":
            default:
                log.info("getAll");

                LocalDate dateFrom = df == null || df.equals("") ? null : LocalDate.parse(df);
                LocalDate dateTo = dt == null || dt.equals("") ? null : LocalDate.parse(dt);
                LocalTime timeFrom = tf == null || tf.equals("") ? null : LocalTime.parse(tf);
                LocalTime timeTo = tt == null || tt.equals("") ? null : LocalTime.parse(tt);

                request.setAttribute("meals",
                        controller.getAll(dateFrom, dateTo, timeFrom, timeTo));
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
