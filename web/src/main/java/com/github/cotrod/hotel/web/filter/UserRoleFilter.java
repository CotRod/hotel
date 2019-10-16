package com.github.cotrod.hotel.web.filter;

import com.github.cotrod.hotel.model.Role;
import com.github.cotrod.hotel.model.UserDTO;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.github.cotrod.hotel.model.Role.USER;
import static com.github.cotrod.hotel.web.WebUtils.redirect;

@WebFilter(urlPatterns = {"/profile/user/*", "/profile"})
public class UserRoleFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        Role role = ((UserDTO) req.getSession().getAttribute("user")).getRole();
        if (role.equals(USER)) {
            filterChain.doFilter(req, resp);
        } else {
            redirect("profile/admin", req, resp);
        }
    }
}
