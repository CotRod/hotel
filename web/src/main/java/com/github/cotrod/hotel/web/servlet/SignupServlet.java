package com.github.cotrod.hotel.web.servlet;

import com.github.cotrod.hotel.model.User;
import com.github.cotrod.hotel.service.AuthService;
import com.github.cotrod.hotel.service.impl.DefaultAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.github.cotrod.hotel.web.WebUtils.entryProfile;
import static com.github.cotrod.hotel.web.WebUtils.forward;

@WebServlet(urlPatterns = "/signup")
public class SignupServlet extends HttpServlet {
    private AuthService authService = DefaultAuthService.getInstance();
    private static final Logger log = LoggerFactory.getLogger(SignupServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        forward("signup", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        User user = authService.saveUser(login, password);
        if (user == null) {
            req.setAttribute("error", true);
            log.warn("user {} couldn't signup", login);
            forward("signup", req, resp);
        } else {
            req.getSession().setAttribute("login", login);  //todo replace to WevUtils
            req.getSession().setAttribute("role", user.getRole().name());
            resp.addCookie(new Cookie("myAppUserCookie", user.toString()));
            entryProfile(req, resp);
        }
    }
}
