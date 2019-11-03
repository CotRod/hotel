package com.github.cotrod.hotel.web.filter;

import com.github.cotrod.hotel.model.Role;
import com.github.cotrod.hotel.model.UserDTO;
import com.github.cotrod.hotel.service.OrderService;
import com.github.cotrod.hotel.service.impl.DefaultOrderService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.github.cotrod.hotel.web.WebUtils.redirect;

@WebFilter(urlPatterns = {"/profile/user/home", "/profile/user", "/profile", "/profile/admin/home", "/profile/admin"})
public class PageNumFilter implements Filter {
    private OrderService orderService = DefaultOrderService.getInstance();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        UserDTO user = (UserDTO) req.getSession().getAttribute("user");
        if (user == null) {
            redirect("login", req, resp);
        }
        long userId;
        Integer page = (Integer) req.getSession().getAttribute("pageNum");
        if (page == null) {
            page = 0;
            req.getSession().setAttribute("pageNum", page);
        }
        Role role = user.getRole();
        if (role.equals("USER")) {
            userId = user.getId();
        } else {
            userId = 0L;
        }
        req.getSession().setAttribute("notLast", orderService.isNotLastPage(userId, page));
        filterChain.doFilter(req, resp);
    }
}
