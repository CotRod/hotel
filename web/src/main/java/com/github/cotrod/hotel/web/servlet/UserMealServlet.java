package com.github.cotrod.hotel.web.servlet;

import com.github.cotrod.hotel.service.MealService;
import com.github.cotrod.hotel.service.impl.DefaultMealService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

import static com.github.cotrod.hotel.web.WebUtils.forward;
import static com.github.cotrod.hotel.web.WebUtils.redirect;

@WebServlet(urlPatterns = "/profile/user/order/meal")
public class UserMealServlet extends HttpServlet {
    private MealService mealService = DefaultMealService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        forward("userMealPage", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameterValues("meals") == null) {
            redirect("profile/user/order/meal", req, resp);
        }
        String[] meals = req.getParameterValues("meals");
        Long orderId = (Long) req.getSession().getAttribute("orderId");
        Arrays.stream(meals).forEach(meal -> mealService.addMealToOrder(orderId, meal));
        req.getSession().removeAttribute("orderId");
        redirect("profile/user/home", req, resp);
    }
}
