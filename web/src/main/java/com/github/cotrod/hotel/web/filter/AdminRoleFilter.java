package com.github.cotrod.hotel.web.filter;

import com.github.cotrod.hotel.model.Role;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.github.cotrod.hotel.model.Role.*;
import static com.github.cotrod.hotel.web.WebUtils.redirect;

@WebFilter(urlPatterns = {"/userHome","/home"})
public class AdminRoleFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req= (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        Role role = Role.valueOf((String) req.getSession().getAttribute("role"));
        if(role.equals(ADMIN)){
            redirect("adminHome", req, resp);
        }else {
            filterChain.doFilter(req,resp);
        }
    }
}
