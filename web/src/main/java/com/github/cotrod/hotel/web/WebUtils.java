package com.github.cotrod.hotel.web;

import com.github.cotrod.hotel.model.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class WebUtils {
    private static final Logger log = LoggerFactory.getLogger(WebUtils.class);

    public static Integer getPage(HttpServletRequest req) {
        String fromRequest;
        if ((fromRequest = req.getParameter("pageNum")) != null) {
            return Integer.valueOf(fromRequest);
        } else {
            return 0;
        }
    }

    public static boolean isUserNotAuth(Authentication authentication) {
        return authentication == null || "anonymousUser".equals(authentication.getPrincipal());
    }

    public static List<GrantedAuthority> getAuthorities(UserDTO userDTO) {
        String role = "ROLE_" + userDTO.getRole().name();
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add((GrantedAuthority) () -> role);
        return grantedAuthorities;
    }
}
