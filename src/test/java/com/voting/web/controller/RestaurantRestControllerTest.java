package com.voting.web.controller;

import com.voting.DishTestData;
import com.voting.RestaurantTestData;
import com.voting.service.RestaurantService;
import com.voting.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.voting.DishTestData.DISH01;
import static com.voting.DishTestData.RESTAURANT_1_DISHES;
import static com.voting.RestaurantTestData.RESTAURANTS;
import static com.voting.RestaurantTestData.RESTAURANT_1;
import static com.voting.RestaurantTestData.RESTAURANT_1_ID;
import static com.voting.RestaurantTestData.contentJson;
import static com.voting.TestUtil.userHttpBasic;
import static com.voting.UserTestData.USER;
import static com.voting.web.controller.RestaurantRestController.REST_URL;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RestaurantRestControllerTest extends AbstractControllerTest {

    @Autowired
    RestaurantService restaurantService;

    @Test
    void getAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(RESTAURANTS));
    }

    @Test
    void getAllTodayMenus() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "/menus")
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RestaurantTestData.contentJsonWithDishes(RestaurantTestData.getRestaurantsWithTodayMenu()));
    }

    @Test
    void get() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "/" + RESTAURANT_1_ID)
                .with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(RESTAURANT_1));
    }

    @Test
    void getUnAuth() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "/menus"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getAllDishes() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "/" + RESTAURANT_1_ID + "/dishes")
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DishTestData.contentJson(RESTAURANT_1_DISHES));
    }

    @Test
    void getDish() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "/" + RESTAURANT_1_ID + "/dishes/" + DISH01.getId())
                .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DishTestData.contentJson(DISH01));
    }
}