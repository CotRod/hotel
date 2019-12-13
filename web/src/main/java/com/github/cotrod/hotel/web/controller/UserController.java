package com.github.cotrod.hotel.web.controller;

import com.github.cotrod.hotel.model.*;
import com.github.cotrod.hotel.service.HotelRoomService;
import com.github.cotrod.hotel.service.MealService;
import com.github.cotrod.hotel.service.OrderService;
import com.github.cotrod.hotel.service.UserService;
import com.github.cotrod.hotel.web.rq.ChangePasswordRq;
import com.github.cotrod.hotel.web.rq.CreateMealRq;
import com.github.cotrod.hotel.web.rq.CreateOrderRq;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static com.github.cotrod.hotel.web.WebUtils.setPageNumber;

@Controller
@RequestMapping("/user")
public class UserController {
    private final OrderService orderService;
    private final HotelRoomService hotelRoomService;
    private final MealService mealService;
    private final UserService userService;

    public UserController(OrderService orderService, HotelRoomService hotelRoomService, MealService mealService, UserService userService) {
        this.orderService = orderService;
        this.hotelRoomService = hotelRoomService;
        this.mealService = mealService;
        this.userService = userService;
    }

    // ************* Home *************

    @GetMapping
    public String userHome(ModelMap modelMap, SecurityContext securityContext) {
        Integer page;
        if (modelMap.containsAttribute("pageNum")) {
            page = (Integer) modelMap.getAttribute("pageNum");
        } else {
            page = 0;
            modelMap.addAttribute("pageNum", page);
        }
        Long userId = ((UserDTO) securityContext.getAuthentication().getPrincipal()).getId();
        List<OrderDTO> orders = orderService.getOrders(userId, page);
        modelMap.addAttribute("orders", orders);
        return "userHome";
    }

    @PostMapping
    public String userHome(ModelMap modelMap) {
        setPageNumber(modelMap);
        return "userHome";
    }

    // ************* Orders *************

    @GetMapping("/order")
    public String order(ModelMap modelMap) {
        List<HotelRoomDTO> rooms = hotelRoomService.getRooms();
        modelMap.addAttribute("rooms", rooms);
        return "userOrderPage";
    }

    @PostMapping("/order")
    public String order(CreateOrderRq orderRq, UsernamePasswordAuthenticationToken authentication, ModelMap modelMap) {
        Long roomId = orderRq.getRoomId();
        Long clientId = ((UserDTO) authentication.getPrincipal()).getId();
        LocalDate dateIn = orderRq.getDateIn();
        LocalDate dateOut = orderRq.getDateOut();
        OrderCreateDTO order = new OrderCreateDTO(roomId, clientId, dateIn, dateOut);
        Long orderId = orderService.makeOrder(order);
        modelMap.addAttribute("orderId", orderId); //todo
        return "userMeal";
    }

    // ************* Meal *************

    @PostMapping("/meal")
    public String meal(ModelMap modelMap, CreateMealRq mealRq) {
        if (mealRq.getMeals() == null) {
            return "userMeal";
        }
        String[] meals = mealRq.getMeals();
        Long orderId = (Long) modelMap.getAttribute("orderId");
        if (orderId == null) {
            return "redirect:/order";
        }
        Arrays.stream(meals).forEach(meal -> mealService.addMealToOrder(orderId, meal));
        return "redirect:/user";
    }

    // ************* Settings *************

    @GetMapping("/settings")
    public String settings() {
        return "userSettings";
    }

    @PostMapping("/changePassword")
    public String changePassword(ChangePasswordRq passwordRq, UsernamePasswordAuthenticationToken auth, ModelMap modelMap) {
        long id = ((UserDTO) auth.getPrincipal()).getId();
        String oldPassword = passwordRq.getOldPassword();
        String newPassword1 = passwordRq.getNewPassword1();
        String newPassword2 = passwordRq.getNewPassword2();
        ChangePassDTO changePass = new ChangePassDTO(oldPassword, newPassword1, newPassword2);
        if (userService.changePassword(id, changePass)) {
            modelMap.addAttribute("success", true);
        } else {
            modelMap.addAttribute("error", true);
        }
        return "userSettings";
    }

}
