package com.voting.service;

import com.voting.DishTestData;
import com.voting.model.Dish;
import com.voting.util.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.util.List;

import static com.voting.DishTestData.DATE_2020_01_10;
import static com.voting.DishTestData.DATE_2020_01_11;
import static com.voting.DishTestData.DISH01;
import static com.voting.DishTestData.DISH_1_ID;
import static com.voting.DishTestData.MENUS_ON_NOW;
import static com.voting.DishTestData.RESTAURANT_1_DISHES;
import static com.voting.DishTestData.RESTAURANT_1_MENU_BETWEEN_START_DATE_AND_END_DATE;
import static com.voting.DishTestData.RESTAURANT_1_MENU_ON_2020_01_10;
import static com.voting.DishTestData.RESTAURANT_2_DISHES;
import static com.voting.DishTestData.RESTAURANT_3_DISHES;
import static com.voting.DishTestData.assertMatch;
import static com.voting.RestaurantTestData.RESTAURANT_1_ID;
import static com.voting.RestaurantTestData.RESTAURANT_2_ID;
import static com.voting.RestaurantTestData.RESTAURANT_3_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DishServiceTest extends AbstractServiceTest {

    @Autowired
    private DishService service;

    @Test
    void create() {
        Dish newDish = DishTestData.getNew();
        Dish created = service.create(newDish, RESTAURANT_1_ID);
        Integer newId = created.getId();
        newDish.setId(newId);
        assertMatch(created, newDish);
        assertMatch(service.get(newId, RESTAURANT_1_ID), newDish);
    }

    @Test
    void delete() {
        service.delete(DISH_1_ID, RESTAURANT_1_ID);
        assertThrows(NotFoundException.class, () ->
                service.get(DISH_1_ID, RESTAURANT_1_ID));
    }

    @Test
    void deleteNotFound() {
        assertThrows(NotFoundException.class, () ->
                service.delete(1, RESTAURANT_1_ID));
    }

    @Test
    void deleteNotOwn() {
        assertThrows(NotFoundException.class, () ->
                service.delete(DISH_1_ID, RESTAURANT_2_ID));
    }

    @Test
    void get() {
        Dish actual = service.get(DISH_1_ID, RESTAURANT_1_ID);
        assertMatch(actual, DISH01);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () ->
                service.get(1, RESTAURANT_1_ID));
    }

    @Test
    void getNotOwn() {
        assertThrows(NotFoundException.class, () ->
                service.get(DISH_1_ID, RESTAURANT_2_ID));
    }

    @Test
    void getAll() {
        assertMatch(service.getAll(RESTAURANT_1_ID), RESTAURANT_1_DISHES);
        assertMatch(service.getAll(RESTAURANT_2_ID), RESTAURANT_2_DISHES);
        assertMatch(service.getAll(RESTAURANT_3_ID), RESTAURANT_3_DISHES);
    }

    @Test
    void getMenuOnDate() {
        final List<Dish> menuOnDate = service.getMenuOnDate(
                LocalDate.of(2020, 1, 10), RESTAURANT_1_ID);
        assertMatch(menuOnDate, RESTAURANT_1_MENU_ON_2020_01_10);
    }

    @Test
    void getTodayMenus() {
        final List<Dish> todayMenus = service.getTodayMenus();
        assertMatch(todayMenus, MENUS_ON_NOW);

    }

    @Test
    void getBetweenInclusive() {
        assertMatch(service.getBetweenInclusive(DATE_2020_01_10, DATE_2020_01_11, RESTAURANT_1_ID),
                RESTAURANT_1_MENU_BETWEEN_START_DATE_AND_END_DATE);
    }

    @Test
    void update() {
        Dish updated = DishTestData.getUpdated();
        service.update(updated, RESTAURANT_1_ID);
        assertMatch(service.get(DISH_1_ID, RESTAURANT_1_ID), updated);
    }

    @Test
    void updateNotFound() {
        Dish updated = DishTestData.getUpdated();
        updated.setId(1);
        NotFoundException e = assertThrows(NotFoundException.class, () -> service.update(updated, RESTAURANT_1_ID));
        assertEquals(e.getMessage(), "Not found entity with id=" + 1);
    }

    @Test
    void updateNotOwn() {
        NotFoundException e = assertThrows(NotFoundException.class, () -> service.update(new Dish(DISH01), RESTAURANT_2_ID));
        assertEquals(e.getMessage(), "Not found entity with id=" + DISH_1_ID);
    }

    @Test
    void createWithException() {
        validateRootCause(() -> service.create(
                new Dish(null, null, "dishName", 500), RESTAURANT_1_ID),
//                new Dish(null, null, "dishName", 500, RESTAURANT_1_ID), RESTAURANT_1_ID),
                ConstraintViolationException.class);

        validateRootCause(() -> service.create(
                new Dish(null, LocalDate.now(), "  ", 500), RESTAURANT_1_ID),
//                new Dish(null, LocalDate.now(), "  ", 500, RESTAURANT_1_ID), RESTAURANT_1_ID),
                ConstraintViolationException.class);

        validateRootCause(() -> service.create(
                new Dish(null, LocalDate.now(), "o", 500), RESTAURANT_1_ID),
//                new Dish(null, LocalDate.now(), "o", 500, RESTAURANT_1_ID), RESTAURANT_1_ID),
                ConstraintViolationException.class);

        validateRootCause(() -> service.create(
                new Dish(null, LocalDate.now(), "dishName", 0), RESTAURANT_1_ID),
//                new Dish(null, LocalDate.now(), "dishName", 0, RESTAURANT_1_ID), RESTAURANT_1_ID),
                ConstraintViolationException.class);
    }
}