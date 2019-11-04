package com.github.cotrod.hotel.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.github.cotrod.hotel.web.WebUtils.redirect;

@WebFilter(urlPatterns = "/profile/user/order/meal")
public class UserMealFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        Long orderId = (Long) req.getSession().getAttribute("orderId");
        if (orderId > 0) {
            chain.doFilter(req, resp);
        } else {
            redirect("profile/user/order", req, resp);
        }
    }
}
