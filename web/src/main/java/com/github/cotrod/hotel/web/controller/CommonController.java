package com.github.cotrod.hotel.web.controller;

import com.github.cotrod.hotel.model.Role;
import com.github.cotrod.hotel.model.UserDTO;
import com.github.cotrod.hotel.model.UserLoginDTO;
import com.github.cotrod.hotel.model.UserSignupDTO;
import com.github.cotrod.hotel.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping
public class CommonController {
    private static final Logger log = LoggerFactory.getLogger(CommonController.class);
    private final UserService userService;

    public CommonController(UserService userService) {
        this.userService = userService;
    }

// ************* Login *************

    @GetMapping(path = {"/", "/login"})
    public String login(SecurityContext securityContext) {
        Authentication authentication = securityContext.getAuthentication();
        if (authentication == null) {
            return "login";
        }
        return "redirect:/home"; // TODO: 10.12.2019
    }

    @PostMapping("/login")
    public String login(HttpServletRequest req, SecurityContext securityContext) {
        UserDTO userDTO = new UserDTO();
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        UserLoginDTO userLogin = new UserLoginDTO(login, password);
        userDTO = userService.getUser(userLogin);

        if (userDTO != null) {
            Authentication auth = new UsernamePasswordAuthenticationToken(userDTO, null, getAuthorities(userDTO));
            securityContext.setAuthentication(auth);
            return "redirect:/home"; // TODO: 10.12.2019
        } else {
            req.setAttribute("error", true);
            log.warn("user {} couldn't log in with password {}", login, password);
            return "login";
        }
    }

// ************* Signup *************

    @GetMapping("/signup")
    public String signup(SecurityContext securityContext) {
        Authentication authentication = securityContext.getAuthentication();
        if (authentication == null) {
            return "signup";
        }
        return "redirect:/home"; // TODO: 10.12.2019
    }

    @PostMapping("/signup")
    public String signup(HttpServletRequest req, SecurityContext securityContext) {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        UserSignupDTO userSignup = new UserSignupDTO(login, password, firstName, lastName);
        UserDTO userDTO = userService.saveUser(userSignup);
        if (userDTO == null) {
            req.setAttribute("error", true);
            log.warn("user {} couldn't signup", login);
            return "signup";
        } else {
            Authentication auth = new UsernamePasswordAuthenticationToken(userDTO, null, getAuthorities(userDTO));
            securityContext.setAuthentication(auth);
            return "redirect:/home"; // TODO: 10.12.2019
        }
    }

    // ************* Logout *************

    @GetMapping("/logout")
    public String logout(HttpServletRequest req) {
        SecurityContextHolder.clearContext();
        try {
            req.logout();
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/login";
    }

    // ************* resolver *************

    @GetMapping("/home")
    public String resolver(UsernamePasswordAuthenticationToken auth) {
        UserDTO user = (UserDTO) auth.getPrincipal();
        if (user.getRole().equals(Role.USER)) {
            return "redirect:/user";
        } else {
            return "redirect:/admin";
        }
    }

    private List<GrantedAuthority> getAuthorities(UserDTO userDTO) {
        String role = "ROLE_" + userDTO.getRole().name();
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add((GrantedAuthority) () -> role);
        return grantedAuthorities;
    }
}
