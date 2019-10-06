package com.github.cotrod.hotel.web.servlet;

import com.github.cotrod.hotel.model.Role;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.github.cotrod.hotel.model.Role.ADMIN;
import static com.github.cotrod.hotel.web.WebUtils.forward;
@WebServlet(urlPatterns = "/Home")
public class HomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Role role = Role.valueOf((String) req.getSession().getAttribute("role"));
        if(role.equals(ADMIN)){
            forward("adminHome", req, resp);
        }else {
            forward("userHome", req, resp);
        }
    }
}
