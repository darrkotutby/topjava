package ru.javawebinar.topjava.web;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Vermut
 */
public class AuthentificationPropagationListener implements ServletRequestListener {

    @Override
    public void requestInitialized(ServletRequestEvent event) {
        HttpServletRequest request = (HttpServletRequest) event.getServletRequest();
        HttpSession session = request.getSession(false);
        if (session == null) {
            session = request.getSession(true);
            session.setAttribute("userId", 1);
        }
        Integer userId = (Integer) session.getAttribute("userId");
        SecurityUtil.setUserId(userId);
    }

    @Override
    public void requestDestroyed(ServletRequestEvent event) {
        SecurityUtil.setUserId(null);
    }

}
