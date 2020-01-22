package com.voting.repository.restaurant;

import com.voting.model.Restaurant;
import org.springframework.lang.NonNull;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface RestaurantRepository {
    // null if not found, when updated
    Restaurant save(@NotNull Restaurant restaurant);

    // false if not found
    boolean delete(int id);

    // null if not found
    Restaurant get(int id);

    List<Restaurant> getAll();

    // null if not found
    Restaurant getByName(@NonNull String name);
}