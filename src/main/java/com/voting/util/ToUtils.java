package com.voting.util;

import com.voting.model.Dish;
import com.voting.model.Vote;
import com.voting.to.DishTo;
import com.voting.to.VoteTo;

public class ToUtils {

    public static Dish createFromTo(DishTo dishTo) {
        return new Dish(null, dishTo.getDate(), dishTo.getName(), dishTo.getPrice());
    }

    public static Vote createFromTo(VoteTo voteTo) {
        return new Vote(voteTo.getId(), voteTo.getDate(), null, null);
    }
}