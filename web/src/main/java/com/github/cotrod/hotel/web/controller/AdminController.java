package com.github.cotrod.hotel.web.controller;

import com.github.cotrod.hotel.model.Decision;
import com.github.cotrod.hotel.model.OrderDTO;
import com.github.cotrod.hotel.service.OrderService;
import com.github.cotrod.hotel.service.UserService;
import com.github.cotrod.hotel.web.rq.ChangeDecisionRq;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;

import static com.github.cotrod.hotel.web.WebUtils.setPageNumber;

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
    public String adminHome(ModelMap modelMap) throws ServletException, IOException {
        Integer page;
        if (modelMap.containsAttribute("pageNum")) {
            page = (Integer) modelMap.getAttribute("pageNum");
        } else {
            page = 0;
            modelMap.addAttribute("pageNum", page);
        }
        List<OrderDTO> orders = orderService.getOrders(0L, page);
        modelMap.addAttribute("orders", orders);
        return "adminHome";
    }

    @PostMapping
    public String changePage(ModelMap modelMap) {
        setPageNumber(modelMap);
        return "adminHome";
    }

    @PostMapping("/changeDecision")
    public String changeDecision(ChangeDecisionRq decisionRq) {
        long id = decisionRq.getOrderId();
        Decision decision = decisionRq.getDecision();
        orderService.updateDecision(id, decision);
        return "adminHome";
    }
}
