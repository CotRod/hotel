package com.github.cotrod.hotel.web.servlet;

import com.github.cotrod.hotel.model.Role;
import com.github.cotrod.hotel.model.User;
import com.github.cotrod.hotel.service.AuthService;
import com.github.cotrod.hotel.service.impl.DefaultAuthService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.github.cotrod.hotel.model.Role.*;
import static com.github.cotrod.hotel.web.WebUtils.forward;
import static com.github.cotrod.hotel.web.WebUtils.redirect;

@WebServlet(urlPatterns = {"/login", "/"})
public class LoginServlet extends HttpServlet {
    private AuthService authService = DefaultAuthService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        forward("login", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        User user = authService.getUser(login, password);

        if (user != null) {
            req.getSession().setAttribute("login", login);
            req.getSession().setAttribute("role", user.getRole().name());
            resp.addCookie(new Cookie("myAppUserCookie", user.toString()));
            redirect("home", req, resp);
        } else {
            req.setAttribute("error", true);
            forward("login", req, resp);
        }
    }
}
