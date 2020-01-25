package com.voting.service;

import com.voting.model.Dish;
import com.voting.repository.dish.DataJpaDishRepository;
import com.voting.to.DishTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;

import static com.voting.util.DateTimeUtil.getEndExclusive;
import static com.voting.util.DateTimeUtil.getStartInclusive;
import static com.voting.util.ToUtils.createFromTo;
import static com.voting.util.ValidationUtil.checkDate;
import static com.voting.util.ValidationUtil.checkNotFoundWithId;

@Service
public class DishService {

    private final DataJpaDishRepository repository;

    @Autowired
    public DishService(DataJpaDishRepository repository) {
        this.repository = repository;
    }

    public Dish create(DishTo dishTo, int restaurantId) {
        return save(createFromTo(checkDate(dishTo)), restaurantId);
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

    public List<Dish> getMenuOnDate(LocalDate date, int restaurantId) {
        return repository.getMenuOnDate(date, restaurantId);
    }

    public List<Dish> getBetweenInclusive(LocalDate startDate, LocalDate endDate, int restaurantId) {
        return repository.getBetweenInclusive(
                getStartInclusive(startDate).toLocalDate(),
                getEndExclusive(endDate).toLocalDate(),
                restaurantId);
    }

    public void update(DishTo dishTo, int restaurantId) {
        update(createFromTo(checkDate(dishTo)), restaurantId);
    }

    public void update(Dish dish, int restaurantId) {
        save(dish, restaurantId);
    }

    private Dish save(Dish dish, int restaurantId) {
        Assert.notNull(dish, "dish must not be null");
        return checkNotFoundWithId(repository.save(dish, restaurantId), dish.getId());
    }
}