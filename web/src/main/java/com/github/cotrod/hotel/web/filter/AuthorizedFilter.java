package com.github.cotrod.hotel.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static com.github.cotrod.hotel.web.WebUtils.findCookie;
import static com.github.cotrod.hotel.web.WebUtils.redirect;

@WebFilter(urlPatterns = {"/login", "/signup", "/"})
public class AuthorizedFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        Optional<Cookie> cookie = findCookie("myAppUserCookie", req);
        if (cookie.isPresent()) {
            redirect("Home", req, resp);
        } else {
            filterChain.doFilter(req, resp);
        }
    }
}