package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.Repository;
import ru.javawebinar.topjava.repository.memoryrepository.UserMemoryRepository;
import ru.javawebinar.topjava.util.FillUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

public class UserServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);
    private static final Repository<User> repository = UserMemoryRepository.getRepository();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        FillUtil.getUserList().forEach(repository::add);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String cancelEdit = request.getParameter("CancelEdit");
        if (cancelEdit != null) {
            response.sendRedirect("users");
            return;
        }
        String save = request.getParameter("save");


        int id = Integer.parseInt(request.getParameter("id"));
        String login = request.getParameter("login");
        String fullName = request.getParameter("fullName");
        int caloriesPerDate = Integer.parseInt(request.getParameter("caloriesPerDate"));

        User user = new User(login, fullName, caloriesPerDate, id);

        if (save != null) {
            if (id != 0) {
                repository.update(user);
            } else {
                repository.add(user);
            }
            response.sendRedirect("users");
        }
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");
        String ids = request.getParameter("id");

        User user;
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
                    user = repository.query(u -> u.getId() == id1).get(0);
                    repository.delete(user);
                    response.sendRedirect("users");
                    return;
                case "new":
                    user = new User();
                    request.setAttribute("user", user);
                    request.getRequestDispatcher("user.jsp").forward(request, response);
                    return;
                case "edit":
                    user = repository.query(u -> u.getId() == id1).get(0);
                    request.setAttribute("user", user);
                    request.getRequestDispatcher("user.jsp").forward(request, response);
                    return;
                default:
                    throw new IllegalArgumentException("Action " + action + " is illegal");
            }
        }

        // log.debug("redirect to users");
        request.setAttribute("users", repository.query(m -> true));
        request.getRequestDispatcher("users.jsp").forward(request, response);
    }
}
