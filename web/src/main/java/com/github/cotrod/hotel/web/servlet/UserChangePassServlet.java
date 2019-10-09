package com.github.cotrod.hotel.web.servlet;

import com.github.cotrod.hotel.model.User;
import com.github.cotrod.hotel.service.AuthService;
import com.github.cotrod.hotel.service.impl.DefaultAuthService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.github.cotrod.hotel.web.WebUtils.forward;

@WebServlet(urlPatterns = "/changePassword")
public class UserChangePassServlet extends HttpServlet {
    private AuthService service = DefaultAuthService.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        forward("userChangePassword", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = (String) req.getSession().getAttribute("login");
        String oldPassword = req.getParameter("oldPassword");
        String newPassword1 = req.getParameter("newPassword1");
        String newPassword2 = req.getParameter("newPassword2");
        User user = new User(login,oldPassword);
        if(service.changePassword(user, newPassword1, newPassword2)){
            req.setAttribute("success", true);
            forward("userChangePassword", req, resp);
        }else {
            req.setAttribute("error", true);
            forward("userChangePassword", req, resp);
        }
    }
}
