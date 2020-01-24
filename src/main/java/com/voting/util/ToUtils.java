package com.voting.util;

import com.voting.model.Dish;
import com.voting.to.DishTo;

public class ToUtils {

    public static Dish createFromTo(DishTo dishTo) {
        return new Dish(null, dishTo.getDate(), dishTo.getName(), dishTo.getPrice());
    }
}
