package com.github.cotrod.hotel.web.servlet;

import com.github.cotrod.hotel.model.HotelRoom;
import com.github.cotrod.hotel.service.HotelRoomService;
import com.github.cotrod.hotel.service.impl.DefaultHotelRoomService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.github.cotrod.hotel.web.WebUtils.forward;

@WebServlet(urlPatterns = "/profile/user/order")
public class UserOrderServlet extends HttpServlet {
    private HotelRoomService hotelRoomService = DefaultHotelRoomService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<HotelRoom> rooms = hotelRoomService.getRooms();
        req.setAttribute("rooms", rooms);
        forward("userOrderPage", req, resp);
    }
}
