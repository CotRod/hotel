package com.github.cotrod.hotel.web.servlet;

import com.github.cotrod.hotel.model.UserDTO;
import com.github.cotrod.hotel.service.UserService;
import com.github.cotrod.hotel.service.impl.DefaultUserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.github.cotrod.hotel.web.WebUtils.forward;
import static com.github.cotrod.hotel.web.WebUtils.redirect;

@WebServlet(urlPatterns = {"/profile/admin/home", "/profile/admin"})
public class AdminHomeServlet extends HttpServlet {
    private UserService userService = DefaultUserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<UserDTO> users = userService.getUsers();
        req.setAttribute("users", users);
        forward("adminHome", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long id = Long.valueOf(req.getParameter("btn"));
        userService.deleteUser(id);
        redirect("profile/admin/home", req, resp);
    }
}
