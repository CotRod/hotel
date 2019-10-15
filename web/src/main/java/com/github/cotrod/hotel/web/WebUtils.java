package com.github.cotrod.hotel.web;

import com.github.cotrod.hotel.model.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

import static com.github.cotrod.hotel.model.Role.ADMIN;
import static com.github.cotrod.hotel.model.Role.USER;

public class WebUtils {
    private static final Logger log = LoggerFactory.getLogger(WebUtils.class);

    public static void forward(String page, HttpServletRequest req, HttpServletResponse resp) {
        try {
            req.getRequestDispatcher("/WEB-INF/" + page + ".jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            log.warn("Exception during forward to page {}", page);
            throw new RuntimeException();
        }
    }

    public static void redirect(String page, HttpServletRequest req, HttpServletResponse resp) {
        try {
            resp.sendRedirect(req.getContextPath() + "/" + page);
        } catch (IOException e) {
            log.warn("Exception during redirect to page {}", page);
            throw new RuntimeException();
        }
    }

    public static void entryProfile(HttpServletRequest req, HttpServletResponse resp) {
        Role role = Role.valueOf((String) req.getSession().getAttribute("role"));   //todo role from cookie
        if (USER.equals(role)) {
            redirect("profile/user/home", req, resp);
        } else if (ADMIN.equals(role)) {
            redirect("profile/admin/home", req, resp);
        } else {
            log.warn("role hasn't been found");
            throw new RuntimeException();
        }
    }

    public static void deleteCookie(HttpServletResponse resp) {
        Cookie cookie = new Cookie("myAppUserCookie", "");
        cookie.setMaxAge(0);
        resp.addCookie(cookie);
    }

    public static Optional<Cookie> findCookie(String cookieName, HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            return Arrays.stream(cookies)
                    .filter(c -> c.getName().equals(cookieName))
                    .findAny();
        } else {
            return Optional.empty();
        }
    }


}
