package com.voting.web.controller;

import com.voting.model.Dish;
import com.voting.service.DishService;
import com.voting.to.DishTo;
import com.voting.util.exception.NotFoundException;
import com.voting.web.AbstractControllerTest;
import com.voting.web.json.JsonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.voting.DishTestData.DISH13;
import static com.voting.DishTestData.DISH_1_ID;
import static com.voting.DishTestData.assertMatch;
import static com.voting.RestaurantTestData.RESTAURANT_1_ID;
import static com.voting.TestUtil.readFromJson;
import static com.voting.TestUtil.userHttpBasic;
import static com.voting.UserTestData.ADMIN;
import static com.voting.util.ToUtils.createFromTo;
import static com.voting.web.controller.AdminDishRestController.REST_URL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AdminDishRestControllerTest extends AbstractControllerTest {
    private static final String REST_TEST_URL = REST_URL + "/" + RESTAURANT_1_ID + "/dishes";

    @Autowired
    private DishService dishService;

    @Test
    void createWithLocation() throws Exception {
        DishTo newDishTo = new DishTo(null, null, "newDish", 2500);
        Dish newDish = createFromTo(newDishTo);
        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.post(REST_TEST_URL)
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newDishTo)))
                .andDo(print())
                .andExpect(status().isCreated());

        Dish created = readFromJson(action, Dish.class);
        Integer newId = created.getId();
        newDish.setId(newId);
        assertMatch(created, newDish);
        assertMatch(dishService.get(newId, RESTAURANT_1_ID), newDish);
    }

    @Test
    void createXss() throws Exception {
        DishTo newDishTo = new DishTo(null, null, "name<script>alert('XSS')</script>", 2500);
        mockMvc.perform(MockMvcRequestBuilders.post(REST_TEST_URL)
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newDishTo)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(REST_TEST_URL + "/" + DISH_1_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> dishService.get(DISH_1_ID, RESTAURANT_1_ID));
    }

    @Test
    void update() throws Exception {
        Integer updatedId = DISH13.getId();
        DishTo updatedTo = new DishTo(null, null, "updated", 2800);
        Dish updated = createFromTo(updatedTo);
        updated.setId(updatedId);
        mockMvc.perform(MockMvcRequestBuilders.put(REST_TEST_URL + "/" + updatedId)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(ADMIN))
                .content(JsonUtil.writeValue(updatedTo)))
                .andDo(print())
                .andExpect(status().isNoContent());

        Dish actual = dishService.get(updatedId, RESTAURANT_1_ID);
        assertEquals(DISH13.getDate(), actual.getDate());
        assertMatch(actual, updated);
    }
}