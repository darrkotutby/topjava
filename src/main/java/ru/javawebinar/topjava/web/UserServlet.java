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


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to users");
        request.setAttribute("users", repository.query(m -> true));
        request.getRequestDispatcher("users.jsp").forward(request, response);
    }
}
