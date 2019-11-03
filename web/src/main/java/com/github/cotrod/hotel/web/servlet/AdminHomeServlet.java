package com.github.cotrod.hotel.web.servlet;

import com.github.cotrod.hotel.model.Decision;
import com.github.cotrod.hotel.model.OrderDTO;
import com.github.cotrod.hotel.service.OrderService;
import com.github.cotrod.hotel.service.UserService;
import com.github.cotrod.hotel.service.impl.DefaultOrderService;
import com.github.cotrod.hotel.service.impl.DefaultUserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.github.cotrod.hotel.web.WebUtils.*;

@WebServlet(urlPatterns = {"/profile/admin/home", "/profile/admin"})
public class AdminHomeServlet extends HttpServlet {
    private UserService userService = DefaultUserService.getInstance();
    private OrderService orderService = DefaultOrderService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer page = (Integer) req.getSession().getAttribute("pageNum");
        List<OrderDTO> orders = orderService.getOrders(0L, page);
        req.setAttribute("orders", orders);
        forward("adminHome", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("btn") != null) {
            long id = Long.parseLong(req.getParameter("btn"));
            Decision decision = Decision.valueOf(req.getParameter("decision"));
            orderService.updateDecision(id, decision);
        }
        setPageNumber(req);
        redirect("profile/admin/home", req, resp);
    }
}
