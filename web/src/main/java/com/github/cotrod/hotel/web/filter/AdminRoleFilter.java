package com.github.cotrod.hotel.web.filter;

import com.github.cotrod.hotel.model.Role;
import com.github.cotrod.hotel.model.UserDTO;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.github.cotrod.hotel.model.Role.ADMIN;
import static com.github.cotrod.hotel.web.WebUtils.getRole;
import static com.github.cotrod.hotel.web.WebUtils.redirect;

@WebFilter(urlPatterns = "/profile/admin/*")
public class AdminRoleFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        UserDTO userDTO = (UserDTO) req.getSession().getAttribute("user");
        if (userDTO == null) {
            redirect("login", req, resp);
        }
        Role role = getRole(req, resp);
        if (role.equals(ADMIN)) {
            filterChain.doFilter(req, resp);
        } else {
            redirect("profile/user/home", req, resp);
        }
    }
}
