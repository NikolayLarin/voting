package com.voting.service;

import com.voting.model.Restaurant;
import com.voting.repository.restaurant.DataJpaRestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;

import static com.voting.util.ValidationUtil.checkNotFoundWithId;

@Service
public class RestaurantService {

    private final DataJpaRestaurantRepository repository;

    @Autowired
    public RestaurantService(DataJpaRestaurantRepository repository) {
        this.repository = repository;
    }

    public Restaurant create(Restaurant restaurant) {
        return save(restaurant);
    }

    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id), id);
    }

    public Restaurant get(int id) {
        return checkNotFoundWithId(repository.get(id), id);
    }

    public List<Restaurant> getAll() {
        return repository.getAll();
    }

    public void update(Restaurant restaurant) {
        save(restaurant);
    }

    public List<Restaurant> getAllWithTodayMenu() {
        return repository.getAllWithDishesOnDate(LocalDate.now());
    }

    private Restaurant save(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        return checkNotFoundWithId(repository.save(restaurant), restaurant.getId());
    }
}