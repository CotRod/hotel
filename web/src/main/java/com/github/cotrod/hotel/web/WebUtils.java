package com.github.cotrod.hotel.web;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class WebUtils {
    public static void forward(String page, HttpServletRequest req, HttpServletResponse resp) {
        try {
            req.getRequestDispatcher("/" + page + ".jsp").forward(req, resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void redirect(String page, HttpServletRequest req, HttpServletResponse resp) {
        try {
            resp.sendRedirect(req.getContextPath() + "/" + page);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteCookie(HttpServletResponse resp) {
        Cookie cookie = new Cookie("myAppUserCookie", "");
        cookie.setMaxAge(0);
        resp.addCookie(cookie);
    }
}
