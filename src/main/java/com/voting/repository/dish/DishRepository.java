package com.voting.repository.dish;

import com.voting.model.Dish;
import org.springframework.lang.NonNull;

import java.time.LocalDate;
import java.util.List;

public interface DishRepository {
    // null if updated dish do not belong to restaurant
    Dish save(Dish dish, int restaurantId);

    // false if dish do not belong to restaurant
    boolean delete(int id, int restaurantId);

    // null if dish do not belong to restaurant
    Dish get(int id, int restaurantId);

    // ORDERED by date desc, price asc
    List<Dish> getAll(int restaurantId);

    // ORDERED by price asc
    List<Dish> getAllOnDate(@NonNull LocalDate date, int restaurantId);

    // ORDERED by price asc
    List<Dish> getDayMenus(@NonNull LocalDate date);

    // ORDERED by date desc, price asc
    List<Dish> getBetweenInclusive(@NonNull LocalDate startDate, @NonNull LocalDate endDate, int restaurantId);
}