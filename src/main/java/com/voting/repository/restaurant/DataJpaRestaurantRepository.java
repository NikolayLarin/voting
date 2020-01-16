package com.voting.repository.restaurant;

import com.voting.model.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DataJpaRestaurantRepository implements RestaurantRepository {
    private static final Sort SORT_BY_ID = Sort.by(Sort.Direction.ASC, "id");

    private final CrudRestaurantRepository crudRepository;

    @Autowired
    public DataJpaRestaurantRepository(CrudRestaurantRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    @Override
    public Restaurant save(Restaurant restaurant) {
        if (!restaurant.isNew() && get(restaurant.getId()) == null) {
            return null;
        }
        return crudRepository.save(restaurant);
    }

    @Override
    public boolean delete(int id) {
        return crudRepository.delete(id) != 0;
    }

    @Override
    public Restaurant get(int id) {
        return crudRepository.findById(id).orElse(null);
    }

    @Override
    public List<Restaurant> getAll() {
        return crudRepository.findAll(SORT_BY_ID);
    }
}