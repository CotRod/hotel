package com.github.cotrod.hotel.web.servlet;

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

@WebServlet(urlPatterns = "/signup")
public class SignupServlet extends HttpServlet {
    private UserService userService = DefaultUserService.getInstance();
    private static final Logger log = LoggerFactory.getLogger(SignupServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        forward("signup", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        UserDTO userDTO = new UserDTO();
        userDTO.setLogin(login);
        userDTO.setPassword(password);
        userDTO.setFirstName(firstName);
        userDTO.setLastName(lastName);
        userDTO = userService.saveUser(userDTO);
        if (userDTO == null) {
            req.setAttribute("error", true);
            log.warn("user {} couldn't signup", login);
            forward("signup", req, resp);
        } else {
            req.getSession().setAttribute("user", userDTO);
            resp.addCookie(new Cookie("myAppUserCookie", userDTO.getLogin()));
            entryProfile(req, resp);
        }
    }
}
