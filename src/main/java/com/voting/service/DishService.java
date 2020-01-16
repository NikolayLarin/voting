package com.voting.service;

import com.voting.model.Dish;
import com.voting.repository.dish.DishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;

import static com.voting.util.ValidationUtil.checkNotFoundWithId;

@Service
public class DishService {

    private final DishRepository repository;

    @Autowired
    public DishService(DishRepository repository) {
        this.repository = repository;
    }

    public Dish create(Dish dish, int restaurantId) {
        return save(dish, restaurantId);
    }

    public void delete(int id, int restaurantId) {
        checkNotFoundWithId(repository.delete(id, restaurantId), id);
    }

    public Dish get(int id, int restaurantId) {
        return checkNotFoundWithId(repository.get(id, restaurantId), id);
    }

    public List<Dish> getAll(int restaurantId) {
        return repository.getAll(restaurantId);
    }

    public List<Dish> getAllOnDate(LocalDate date, int restaurantId) {
        return repository.getAllOnDate(date, restaurantId);
    }

    public List<Dish> getTodayMenus() {
        return repository.getDayMenus(LocalDate.now());
    }

    public List<Dish> getBetweenInclusive(LocalDate startDate, LocalDate endDate, int restaurantId) {
        return repository.getBetweenInclusive(startDate, endDate, restaurantId);
    }

    public void update(Dish dish, int restaurantId) {
        save(dish, restaurantId);
    }

    private Dish save(Dish dish, int restaurantId) {
        Assert.notNull(dish, "dish must not be null");
        return checkNotFoundWithId(repository.save(dish, restaurantId), dish.getId());
    }
}