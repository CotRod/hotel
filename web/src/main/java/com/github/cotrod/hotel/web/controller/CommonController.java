package com.github.cotrod.hotel.web.controller;

import com.github.cotrod.hotel.model.Role;
import com.github.cotrod.hotel.model.UserDTO;
import com.github.cotrod.hotel.model.UserLoginDTO;
import com.github.cotrod.hotel.model.UserSignupDTO;
import com.github.cotrod.hotel.service.UserService;
import com.github.cotrod.hotel.web.rq.LoginRq;
import com.github.cotrod.hotel.web.rq.SignupRq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import static com.github.cotrod.hotel.web.WebUtils.getAuthorities;
import static com.github.cotrod.hotel.web.WebUtils.isUserNotAuth;

@Controller
@RequestMapping
public class CommonController {
    private static final Logger log = LoggerFactory.getLogger(CommonController.class);
    private final UserService userService;

    public CommonController(UserService userService) {
        this.userService = userService;
    }

// ************* Login *************

    @GetMapping(path = {"/login", "/"})
    public String login() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (isUserNotAuth(authentication)) {
            return "login";
        }
        return "redirect:/home";
    }

    @PostMapping(path = {"/login", "/"})
    public String login(LoginRq req, ModelMap modelMap) {
        String login = req.getLogin();
        String password = req.getPassword();
        UserLoginDTO userLogin = new UserLoginDTO(login, password);
        UserDTO user = userService.getUser(userLogin);

        if (user != null) {
            Authentication auth = new UsernamePasswordAuthenticationToken(user, null, getAuthorities(user));
            SecurityContextHolder.getContext().setAuthentication(auth);
            return "redirect:/home";
        } else {
            modelMap.addAttribute("error", true);
            log.warn("user {} couldn't log in with password {}", login, password);
            return "login";
        }
    }

// ************* Signup *************

    @GetMapping("/signup")
    public String signup() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (isUserNotAuth(authentication)) {
            return "signup";
        }
        return "redirect:/home";
    }

    @PostMapping("/signup")
    public String signup(SignupRq req, ModelMap modelMap) {
        String login = req.getLogin();
        String password = req.getPassword();
        String firstName = req.getFirstName();
        String lastName = req.getLastName();
        UserSignupDTO userSignup = new UserSignupDTO(login, password, firstName, lastName);
        UserDTO userDTO = userService.saveUser(userSignup);
        if (userDTO == null) {
            modelMap.addAttribute("error", true);
            log.warn("user {} couldn't signup", login);
            return "signup";
        } else {
            Authentication auth = new UsernamePasswordAuthenticationToken(userDTO, null, getAuthorities(userDTO));
            SecurityContextHolder.getContext().setAuthentication(auth);
            return "redirect:/home";
        }
    }

    // ************* Logout *************

    @GetMapping("/logout")
    public String logout(HttpServletRequest req) {
        SecurityContextHolder.clearContext();
        try {
            req.logout();
        } catch (ServletException e) {
            log.error("Logout exception  user id - {}",
                    ((UserDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());
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
}
