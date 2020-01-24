package com.voting.service;

import com.voting.DishTestData;
import com.voting.RestaurantTestData;
import com.voting.model.Restaurant;
import com.voting.util.exception.NotFoundException;
import org.hsqldb.HsqlException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.util.List;

import static com.voting.RestaurantTestData.RESTAURANTS;
import static com.voting.RestaurantTestData.RESTAURANT_1;
import static com.voting.RestaurantTestData.RESTAURANT_1_ID;
import static com.voting.RestaurantTestData.assertMatch;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RestaurantServiceTest extends AbstractServiceTest {

    @Autowired
    private RestaurantService service;

    @Test
    void create() {
        Restaurant newRestaurant = RestaurantTestData.getNew();
        Restaurant created = service.create(newRestaurant);
        Integer newId = created.getId();
        newRestaurant.setId(newId);
        assertMatch(created, newRestaurant);
        assertMatch(service.get(newId), newRestaurant);
    }

    @Test
    void delete() {
        service.delete(RESTAURANT_1_ID);
        assertThrows(NotFoundException.class, () ->
                service.get(RESTAURANT_1_ID));
    }

    @Test
    void deleteNotFound() {
        assertThrows(NotFoundException.class, () ->
                service.delete(1));
    }

    @Test
    void get() {
        Restaurant actual = service.get(RESTAURANT_1_ID);
        assertMatch(actual, RESTAURANT_1);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () ->
                service.get(1));
    }

    @Test
    void getAll() {
        final List<Restaurant> all = service.getAll();
        assertMatch(all, RESTAURANTS);
    }

    @Test
    void getAllWithTodayMenu() {
        final List<Restaurant> all = service.getAllWithTodayMenu();
        assertMatch(all, RESTAURANTS);
        all.forEach(restaurant ->
                DishTestData.assertMatch(
                        restaurant.getDishes(),
                        RestaurantTestData.getDishesOnDate(restaurant.getId(), LocalDate.now())));
    }

    @Test
    void update() {
        Restaurant updated = RestaurantTestData.getUpdated();
        service.update(updated);
        assertMatch(service.get(RESTAURANT_1_ID), updated);
    }

    @Test
    void updateNotFound() {
        NotFoundException e = assertThrows(NotFoundException.class, () ->
                service.update(new Restaurant(1, "NotExist")));
        assertEquals(e.getMessage(), "Not found entity with id=" + 1);
    }

    @Test
    void createWithException() {
        validateRootCause(() -> service.create(new Restaurant(null, "  ")), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new Restaurant(null, "Restaurant_1")), HsqlException.class);
    }
}