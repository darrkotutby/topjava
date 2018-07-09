package ru.javawebinar.topjava.web;

import org.slf4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

public class UserServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, NumberFormatException {
        request.setCharacterEncoding("UTF-8");
        String userId = request.getParameter("userId");


        Integer id = Integer.parseInt(userId);
        SecurityUtil.setUserId(id);
        HttpSession session = request.getSession(true);
        session.setAttribute("userId", id);
        session.removeAttribute("dateFrom");
        session.removeAttribute("dateTo");
        session.removeAttribute("timeFrom");
        session.removeAttribute("timeTo");

        response.sendRedirect("meals");
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("forward to users");
        request.getRequestDispatcher("/users.jsp").forward(request, response);
    }
}
