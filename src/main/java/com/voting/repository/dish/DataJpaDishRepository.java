package com.voting.repository.dish;

import com.voting.model.Dish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class DataJpaDishRepository implements DishRepository {

    private final CrudDishRepository crudRepository;

    @Autowired
    public DataJpaDishRepository(CrudDishRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    @Override
    public Dish save(Dish dish, int restaurantId) {
        if (!dish.isNew() && get(dish.getId(), restaurantId) == null) {
            return null;
        }
        dish.setRestaurantId(restaurantId);
        return crudRepository.save(dish);
    }

    @Override
    public boolean delete(int id, int restaurantId) {
        return crudRepository.delete(id, restaurantId) != 0;
    }

    @Override
    public Dish get(int id, int restaurantId) {
        return crudRepository
                .findById(id)
                .filter(dish -> dish.getRestaurantId() == restaurantId)
                .orElse(null);
    }

    @Override
    public List<Dish> getAll(int restaurantId) {
        return crudRepository.getAll(restaurantId);
    }

    @Override
    public List<Dish> getAllOnDate(LocalDate date, int restaurantId) {
        return crudRepository.getAllOnDate(date, restaurantId);
    }

    @Override
    public List<Dish> getDayMenus(LocalDate date) {
        return crudRepository.getDayMenus(date);
    }

    @Override
    public List<Dish> getBetweenInclusive(LocalDate startDate, LocalDate endDate, int restaurantId) {
        return crudRepository.getBetweenInclusive(startDate, endDate, restaurantId);
    }
}