package com.voting;

import com.voting.model.Restaurant;

import java.util.List;

import static com.voting.model.AbstractBaseEntity.START_SEQ;
import static org.assertj.core.api.Assertions.assertThat;

public class RestaurantTestData {
    public static final int RESTAURANT_1_ID = START_SEQ + 2;
    public static final int RESTAURANT_2_ID = START_SEQ + 3;
    public static final int RESTAURANT_3_ID = START_SEQ + 4;

    public static final Restaurant RESTAURANT_1 = new Restaurant(RESTAURANT_1_ID, "Restaurant_1");
    public static final Restaurant RESTAURANT_2 = new Restaurant(RESTAURANT_2_ID, "Restaurant_2");
    public static final Restaurant RESTAURANT_3 = new Restaurant(RESTAURANT_3_ID, "Restaurant_3");

    public static final List<Restaurant> RESTAURANTS = List.of(RESTAURANT_1, RESTAURANT_2, RESTAURANT_3);

    public static Restaurant getNew() {
        return new Restaurant(null, "newRestaurant");
    }

    public static Restaurant getUpdated() {
        Restaurant updated = new Restaurant(RESTAURANT_1);
        updated.setName("newRestaurantUpdated");
        return updated;
    }

    public static void assertMatch(Restaurant actual, Restaurant expected) {
        assertThat(actual).isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Restaurant> actual, Restaurant... expected) {
        assertMatch(actual, List.of(expected));
    }

    public static void assertMatch(Iterable<Restaurant> actual, Iterable<Restaurant> expected) {
        assertThat(actual).usingDefaultElementComparator().isEqualTo(expected);
    }
}