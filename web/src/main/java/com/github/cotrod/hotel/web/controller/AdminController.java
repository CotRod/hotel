package com.github.cotrod.hotel.web.controller;

import com.github.cotrod.hotel.model.Decision;
import com.github.cotrod.hotel.model.OrderDTO;
import com.github.cotrod.hotel.model.UserDTO;
import com.github.cotrod.hotel.service.OrderService;
import com.github.cotrod.hotel.service.UserService;
import com.github.cotrod.hotel.web.rq.ChangeDecisionRq;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.github.cotrod.hotel.web.WebUtils.getPage;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private UserService userService;
    private OrderService orderService;

    public AdminController(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }

    @GetMapping
    public String adminHome(HttpServletRequest req, ModelMap modelMap, UsernamePasswordAuthenticationToken principal) {
        Integer page = getPage(req);
        UserDTO user = ((UserDTO) principal.getPrincipal());
        List<OrderDTO> orders = orderService.getOrders(0L, page);
        modelMap.addAttribute("orders", orders);
        modelMap.addAttribute("notLast", orderService.isNotLastPage(0L, page));
        modelMap.addAttribute("user", user);
        modelMap.addAttribute("pageNum", page);

        return "adminHome";
    }

    @PostMapping("/changeDecision")
    public String changeDecision(ChangeDecisionRq decisionRq) {
        long id = decisionRq.getOrderId();
        Decision decision = decisionRq.getDecision();
        orderService.updateDecision(id, decision);
        return "redirect:/admin";
    }

    @GetMapping("/userList")
    public String userList(ModelMap modelMap) {
        List<UserDTO> userList = userService.getUsers();
        modelMap.addAttribute("users", userList);
        return "userList";
    }

    @PostMapping("/userList")
    public String userList(@RequestParam("btn") Long userId) {
        userService.deleteUser(userId);
        return "redirect:/admin/userList";
    }
}
