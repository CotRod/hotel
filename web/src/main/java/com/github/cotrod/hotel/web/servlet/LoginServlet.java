package com.github.cotrod.hotel.web.servlet;

import com.github.cotrod.hotel.model.User;
import com.github.cotrod.hotel.model.UserDTO;
import com.github.cotrod.hotel.service.UserService;
import com.github.cotrod.hotel.service.impl.DefaultUserService;
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

@WebServlet(urlPatterns = {"/login", "/"})
public class LoginServlet extends HttpServlet {
    private UserService userService = DefaultUserService.getInstance();
    private static final Logger log = LoggerFactory.getLogger(LoginServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        forward("login", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDTO userDTO = new UserDTO();
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        User user = new User(login, password);
        userDTO = userService.getUser(user);
//        User user = authService.getUser(login, password);

        if (userDTO != null) {
            req.getSession().setAttribute("user", userDTO);
            resp.addCookie(new Cookie("myAppUserCookie", userDTO.getLogin()));
            entryProfile(req, resp);
        } else {
            req.setAttribute("error", true);
            log.warn("user {} couldn't log in with password {}", login, password);
            forward("login", req, resp);
        }
    }
}
