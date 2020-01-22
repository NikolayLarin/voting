package com.voting.web.controller;

import com.voting.model.Restaurant;
import com.voting.repository.restaurant.RestaurantRepository;
import com.voting.web.ExceptionInfoHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class UniqueRestaurantValidator implements org.springframework.validation.Validator {

    private final RestaurantRepository repository;

    @Autowired
    public UniqueRestaurantValidator(RestaurantRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Restaurant.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Restaurant restaurant = (Restaurant) target;
        Restaurant dbRestaurant = repository.getByName(restaurant.getName());
        if (dbRestaurant != null) {
            errors.rejectValue("name", ExceptionInfoHandler.DUPLICATE_RESTAURANT_NAME_CODE);
        }
    }
}