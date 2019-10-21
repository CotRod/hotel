package com.github.cotrod.hotel.web.servlet;

import com.github.cotrod.hotel.model.OrderUserDTO;
import com.github.cotrod.hotel.model.UserDTO;
import com.github.cotrod.hotel.service.HotelRoomService;
import com.github.cotrod.hotel.service.OrderService;
import com.github.cotrod.hotel.service.impl.DefaultHotelRoomService;
import com.github.cotrod.hotel.service.impl.DefaultOrderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.github.cotrod.hotel.web.WebUtils.forward;

@WebServlet(urlPatterns = {"/profile/user/home", "/profile/user", "/profile"})
public class UserHomeServlet extends HttpServlet {
    private HotelRoomService hotelRoomService = DefaultHotelRoomService.getInstance();
    private OrderService orderService = DefaultOrderService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long userId = ((UserDTO) req.getSession().getAttribute("user")).getId();
        List<OrderUserDTO> orders = orderService.getUserOrders(userId);
        req.setAttribute("orders", orders);
        forward("userHome", req, resp);
    }
}
