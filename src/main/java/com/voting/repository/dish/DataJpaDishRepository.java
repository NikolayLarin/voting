package com.voting.repository.dish;

import com.voting.model.Dish;
import com.voting.repository.restaurant.CrudRestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
public class DataJpaDishRepository {

    private final CrudDishRepository dishRepository;
    private final CrudRestaurantRepository restaurantRepository;

    @Autowired
    public DataJpaDishRepository(CrudDishRepository dishRepository, CrudRestaurantRepository restaurantRepository) {
        this.dishRepository = dishRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Transactional
    // null if updated dish do not belong to restaurant
    public Dish save(Dish dish, int restaurantId) {
        if (!dish.isNew() && get(dish.getId(), restaurantId) == null) {
            return null;
        }
        dish.setRestaurant(restaurantRepository.getOne(restaurantId));
        return dishRepository.save(dish);
    }

    // false if dish do not belong to restaurant
    public boolean delete(int id, int restaurantId) {
        return dishRepository.delete(id, restaurantId) != 0;
    }

    // null if dish do not belong to restaurant
    public Dish get(int id, int restaurantId) {
        return dishRepository
                .findById(id)
                .filter(dish -> dish.getRestaurant().getId() == restaurantId)
                .orElse(null);
    }

    // ORDERED by date desc, price asc
    public List<Dish> getAll(int restaurantId) {
        return dishRepository.getAll(restaurantId);
    }

    // ORDERED by price asc
    public List<Dish> getMenuOnDate(LocalDate date, int restaurantId) {
        return dishRepository.getMenuOnDate(date, restaurantId);
    }

    // ORDERED by date desc, price asc
    public List<Dish> getBetweenInclusive(LocalDate startDate, LocalDate endDate, int restaurantId) {
        return dishRepository.getBetweenInclusive(startDate, endDate, restaurantId);
    }
}