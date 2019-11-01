package com.github.cotrod.hotel.web.servlet;

import com.github.cotrod.hotel.model.HotelRoomDTO;
import com.github.cotrod.hotel.model.OrderDTO;
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
import java.time.LocalDate;
import java.util.List;

import static com.github.cotrod.hotel.web.WebUtils.forward;

@WebServlet(urlPatterns = "/profile/user/order")
public class UserOrderServlet extends HttpServlet {
    private HotelRoomService hotelRoomService = DefaultHotelRoomService.getInstance();
    private OrderService orderService = DefaultOrderService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<HotelRoomDTO> rooms = hotelRoomService.getRooms();
        req.setAttribute("rooms", rooms);
        forward("userOrderPage", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long roomId = Long.valueOf(req.getParameter("radio"));
        Long clientId = ((UserDTO) req.getSession().getAttribute("user")).getId();
        LocalDate dateIn = LocalDate.parse(req.getParameter("dateIn"));
        LocalDate dateOut = LocalDate.parse(req.getParameter("dateOut"));
        OrderDTO order = new OrderDTO(roomId, clientId, dateIn, dateOut);
        Long orderId = orderService.makeOrder(order);
        req.setAttribute("orderId", orderId);
        forward("userOrderPage", req, resp);
    }
}
