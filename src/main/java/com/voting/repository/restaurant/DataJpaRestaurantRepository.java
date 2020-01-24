package com.voting.repository.restaurant;

import com.voting.model.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class DataJpaRestaurantRepository {
    private static final Sort SORT_BY_NAME = Sort.by(Sort.Direction.ASC, "name");

    private final CrudRestaurantRepository crudRepository;

    @Autowired
    // null if not found, when updated
    public DataJpaRestaurantRepository(CrudRestaurantRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    // null if not found, when updated
    public Restaurant save(Restaurant restaurant) {
        if (!restaurant.isNew() && get(restaurant.getId()) == null) {
            return null;
        }
        return crudRepository.save(restaurant);
    }

    // false if not found
    public boolean delete(int id) {
        return crudRepository.delete(id) != 0;
    }

    // null if not found
    public Restaurant get(int id) {
        return crudRepository.findById(id).orElse(null);
    }

    // ORDERED by name asc
    public List<Restaurant> getAll() {
        return crudRepository.findAll(SORT_BY_NAME);
    }

    // null if not found
    public Restaurant getByName(String name) {
        return crudRepository.getByName(name);
    }

    // ORDERED by restaurant name asc and dishes price asc
    public List<Restaurant> getAllWithDishesOnDate(LocalDate date) {
        return crudRepository.getAllWithDishesOnDate(date);
    }
}