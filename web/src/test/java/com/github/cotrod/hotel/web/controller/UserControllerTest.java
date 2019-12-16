package com.github.cotrod.hotel.web.controller;

import com.github.cotrod.hotel.model.HotelRoomDTO;
import com.github.cotrod.hotel.model.OrderDTO;
import com.github.cotrod.hotel.model.UserDTO;
import com.github.cotrod.hotel.service.HotelRoomService;
import com.github.cotrod.hotel.service.MealService;
import com.github.cotrod.hotel.service.OrderService;
import com.github.cotrod.hotel.service.UserService;
import com.github.cotrod.hotel.web.rq.ChangePasswordRq;
import com.github.cotrod.hotel.web.rq.CreateMealRq;
import com.github.cotrod.hotel.web.rq.CreateOrderRq;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    private UserDTO user = new UserDTO();
    private List<OrderDTO> orders = new ArrayList<>();
    private List<HotelRoomDTO> rooms = new ArrayList<>();
    private String[] meals = {"BREAKFAST", "LUNCH"};

    @Mock
    OrderService orderService;
    @Mock
    HotelRoomService hotelRoomService;
    @Mock
    MealService mealService;
    @Mock
    UserService userService;
    @Mock
    HttpServletRequest req;
    @Mock
    UsernamePasswordAuthenticationToken principal;
    @Mock
    ModelMap modelMap;
    @Mock
    CreateOrderRq orderRq;
    @Mock
    CreateMealRq mealRq;
    @Mock
    ChangePasswordRq passwordRq;
    @InjectMocks
    UserController userController;

    @Test
    void userHome() {
        user.setId(1L);
        when(req.getParameter("pageNum")).thenReturn(null);
        when(principal.getPrincipal()).thenReturn(user);
        when(orderService.getOrders(1L, 0)).thenReturn(orders);
        when(orderService.isNotLastPage(1L, 0)).thenReturn(true);
        when(modelMap.addAttribute(anyString(), any())).thenReturn(modelMap);
        assertEquals("userHome", userController.userHome(req, modelMap, principal));
    }

    @Test
    void order() {
        when(hotelRoomService.getRooms()).thenReturn(rooms);
        when(modelMap.addAttribute(anyString(), any())).thenReturn(modelMap);
        assertEquals("userOrderPage", userController.order(modelMap));
    }

    @Test
    void testOrder() {
        user.setId(1L);
        when(orderRq.getRoomId()).thenReturn(1L);
        when(principal.getPrincipal()).thenReturn(user);
        when(orderRq.getDateIn()).thenReturn(LocalDate.now());
        when(orderRq.getDateOut()).thenReturn(LocalDate.now());
        when(orderService.makeOrder(any())).thenReturn(1L);
        when(modelMap.addAttribute(anyString(), any())).thenReturn(modelMap);
        assertEquals("userMeal", userController.order(orderRq, principal, modelMap));
    }

    @Test
    void meal() {
        when(mealRq.getOrderId()).thenReturn(null);
        assertEquals("redirect:/user/order", userController.meal(mealRq));
        when(mealRq.getOrderId()).thenReturn(1L);
        when(mealRq.getMeals()).thenReturn(null);
        assertEquals("userMeal", userController.meal(mealRq));
        when(mealRq.getOrderId()).thenReturn(1L);
        when(mealRq.getMeals()).thenReturn(meals);
        doNothing().when(mealService).addMealToOrder(anyLong(), anyString());
        assertEquals("redirect:/user", userController.meal(mealRq));
    }

    @Test
    void settings() {
        assertEquals("userSettings", userController.settings());
    }

    @Test
    void changePassword() {
        user.setId(1L);
        when(principal.getPrincipal()).thenReturn(user);
        when(passwordRq.getOldPassword()).thenReturn("anyString");
        when(passwordRq.getNewPassword1()).thenReturn("anyString()");
        when(passwordRq.getNewPassword2()).thenReturn("anyString()");
        when(modelMap.addAttribute(anyString(), any())).thenReturn(modelMap);
        when(userService.changePassword(anyLong(), any())).thenReturn(true);
        assertEquals("userSettings", userController.changePassword(passwordRq, principal, modelMap));
        when(userService.changePassword(anyLong(), any())).thenReturn(false);
        assertEquals("userSettings", userController.changePassword(passwordRq, principal, modelMap));
    }
}