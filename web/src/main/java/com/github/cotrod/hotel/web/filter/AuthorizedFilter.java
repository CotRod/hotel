package com.github.cotrod.hotel.web.filter;

import com.github.cotrod.hotel.service.UserService;
import com.github.cotrod.hotel.service.impl.DefaultUserService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static com.github.cotrod.hotel.web.WebUtils.entryProfile;
import static com.github.cotrod.hotel.web.WebUtils.findCookie;

@WebFilter(urlPatterns = {"/login", "/signup", "/"})
public class AuthorizedFilter implements Filter {
    private UserService userService = DefaultUserService.getInstance();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        Optional<Cookie> cookie = findCookie("myAppUserCookie", req);
        if (cookie.isPresent()) {
            if (req.getSession().getAttribute("user") == null) {
                req.getSession().setAttribute("user", userService.getUserByLogin(cookie.get().getValue()));//todo need to check null
            }
            entryProfile(req, resp); //todo get role from cookie
        } else {
            filterChain.doFilter(req, resp);
        }
    }
}