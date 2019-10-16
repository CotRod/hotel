package com.github.cotrod.hotel.web.servlet;

import com.github.cotrod.hotel.model.ChangePassDTO;
import com.github.cotrod.hotel.model.UserDTO;
import com.github.cotrod.hotel.service.UserService;
import com.github.cotrod.hotel.service.impl.DefaultUserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.github.cotrod.hotel.web.WebUtils.forward;

@WebServlet(urlPatterns = "/changePassword")
public class UserChangePassServlet extends HttpServlet {
    private UserService service = DefaultUserService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long id = ((UserDTO) req.getSession().getAttribute("user")).getId();
        String oldPassword = req.getParameter("oldPassword");
        String newPassword1 = req.getParameter("newPassword1");
        String newPassword2 = req.getParameter("newPassword2");
        ChangePassDTO changePass = new ChangePassDTO(oldPassword, newPassword1, newPassword2);
        if (service.changePassword(id, changePass)) {
            req.setAttribute("success", true);
        } else {
            req.setAttribute("error", true);
        }
        forward("userSettings", req, resp);
    }
}
