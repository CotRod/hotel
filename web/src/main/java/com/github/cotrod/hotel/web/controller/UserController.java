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
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static com.github.cotrod.hotel.web.WebUtils.getPage;

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
    public String userHome(HttpServletRequest req, ModelMap modelMap, UsernamePasswordAuthenticationToken principal) {
        Integer page = getPage(req);
        UserDTO user = ((UserDTO) principal.getPrincipal());
        List<OrderDTO> orders = orderService.getOrders(user.getId(), page);
        modelMap.addAttribute("orders", orders);
        modelMap.addAttribute("notLast", orderService.isNotLastPage(user.getId(), page));
        modelMap.addAttribute("user", user);
        modelMap.addAttribute("pageNum", page);
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
    public String order(CreateOrderRq orderRq, UsernamePasswordAuthenticationToken principal, ModelMap modelMap) {
        Long roomId = orderRq.getRoomId();
        Long clientId = ((UserDTO) principal.getPrincipal()).getId();
        LocalDate dateIn = orderRq.getDateIn();
        LocalDate dateOut = orderRq.getDateOut();
        OrderCreateDTO order = new OrderCreateDTO(roomId, clientId, dateIn, dateOut);
        Long orderId = orderService.makeOrder(order);
        modelMap.addAttribute("orderId", orderId);
        return "userMeal";
    }

    // ************* Meal *************

    @PostMapping("/meal")
    public String meal(CreateMealRq mealRq) {
        Long orderId = mealRq.getOrderId();
        if (orderId == null) {
            return "redirect:/user/order";
        }
        if (mealRq.getMeals() == null) {
            return "userMeal";
        }
        String[] meals = mealRq.getMeals();
        Arrays.stream(meals).forEach(meal -> mealService.addMealToOrder(orderId, meal));
        return "redirect:/user";
    }

    // ************* Settings *************

    @GetMapping("/settings")
    public String settings() {
        return "userSettings";
    }

    @PostMapping("/changePassword")
    public String changePassword(ChangePasswordRq passwordRq, UsernamePasswordAuthenticationToken principal, ModelMap modelMap) {
        long id = ((UserDTO) principal.getPrincipal()).getId();
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

    @PostMapping("/deleteOrder")
    public String deleteOrder(@RequestParam("orderId") Long orderId) {
        orderService.deleteOrder(orderId);
        return "redirect:/home";
    }

}
