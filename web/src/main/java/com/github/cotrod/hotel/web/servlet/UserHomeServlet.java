package com.github.cotrod.hotel.web.servlet;

import com.github.cotrod.hotel.service.HotelRoomService;
import com.github.cotrod.hotel.service.impl.DefaultHotelRoomService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.github.cotrod.hotel.web.WebUtils.forward;

@WebServlet(urlPatterns = {"/profile/user/home", "/profile/user", "/profile"})
public class UserHomeServlet extends HttpServlet {
    private HotelRoomService hotelRoomService = DefaultHotelRoomService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        forward("userHome", req, resp);
    }
}
