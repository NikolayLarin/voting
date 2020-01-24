package com.voting;

import com.voting.model.Dish;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.voting.model.AbstractBaseEntity.START_SEQ;
import static java.time.LocalDate.now;
import static java.time.LocalDate.of;
import static org.assertj.core.api.Assertions.assertThat;

public class DishTestData {
    public static final LocalDate DATE_2020_01_10 = LocalDate.of(2020, 1, 10);
    public static final LocalDate DATE_2020_01_11 = LocalDate.of(2020, 1, 11);

    public static final int DISH_1_ID = START_SEQ + 5;

    //RESTAURANT_1
    public static final Dish DISH01 = new Dish(DISH_1_ID, of(2020, 1, 10), "Борщ", 2500);
    public static final Dish DISH02 = new Dish(DISH_1_ID + 1, of(2020, 1, 10), "Каша", 2000);
    //RESTAURANT_2
    public static final Dish DISH03 = new Dish(DISH_1_ID + 2, of(2020, 1, 10), "Котлета", 3500);
    public static final Dish DISH04 = new Dish(DISH_1_ID + 3, of(2020, 1, 10), "Суп", 2400);
    //RESTAURANT_3
    public static final Dish DISH05 = new Dish(DISH_1_ID + 4, of(2020, 1, 10), "Пюре", 1800);
    public static final Dish DISH06 = new Dish(DISH_1_ID + 5, of(2020, 1, 10), "Филе", 4000);
    //RESTAURANT_1
    public static final Dish DISH07 = new Dish(DISH_1_ID + 6, of(2020, 1, 11), "Бограч", 2800);
    public static final Dish DISH08 = new Dish(DISH_1_ID + 7, of(2020, 1, 11), "Плов", 2200);
    //RESTAURANT_2
    public static final Dish DISH09 = new Dish(DISH_1_ID + 8, of(2020, 1, 11), "Отбивная", 3800);
    public static final Dish DISH10 = new Dish(DISH_1_ID + 9, of(2020, 1, 11), "Борщ", 2600);
    //RESTAURANT_3
    public static final Dish DISH11 = new Dish(DISH_1_ID + 10, of(2020, 1, 11), "Каша", 2100);
    public static final Dish DISH12 = new Dish(DISH_1_ID + 11, of(2020, 1, 11), "Котлета", 3400);

    //RESTAURANT_1
    public static final Dish DISH13 = new Dish(DISH_1_ID + 12, now(), "Борщ", 2500);
    public static final Dish DISH14 = new Dish(DISH_1_ID + 13, now(), "Каша", 2000);
    public static final Dish DISH15 = new Dish(DISH_1_ID + 14, now(), "Котлета", 3500);
    //RESTAURANT_2
    public static final Dish DISH16 = new Dish(DISH_1_ID + 15, now(), "Суп", 2400);
    public static final Dish DISH17 = new Dish(DISH_1_ID + 16, now(), "Пюре", 1800);
    public static final Dish DISH18 = new Dish(DISH_1_ID + 17, now(), "Филе", 4000);
    //RESTAURANT_3
    public static final Dish DISH19 = new Dish(DISH_1_ID + 18, now(), "Бограч", 2800);
    public static final Dish DISH20 = new Dish(DISH_1_ID + 19, now(), "Плов", 2200);
    public static final Dish DISH21 = new Dish(DISH_1_ID + 20, now(), "Отбивная", 3800);

    // ORDERED by date desc, price asc
    public static final List<Dish> RESTAURANT_1_DISHES = List.of(
            DISH14, DISH13, DISH15, DISH08, DISH07, DISH02, DISH01);

    public static final List<Dish> RESTAURANT_2_DISHES = List.of(
            DISH17, DISH16, DISH18, DISH10, DISH09, DISH04, DISH03);

    public static final List<Dish> RESTAURANT_3_DISHES = List.of(
            DISH20, DISH19, DISH21, DISH11, DISH12, DISH05, DISH06);

    public static final List<Dish> RESTAURANT_1_MENU_ON_2020_01_10 = RESTAURANT_1_DISHES
            .stream()
            .filter(dish -> dish.getDate().isEqual(DATE_2020_01_10))
            .collect(Collectors.toList());

    public static final List<Dish> RESTAURANT_1_MENU_BETWEEN_START_DATE_AND_END_DATE = RESTAURANT_1_DISHES
            .stream()
            .filter(dish ->
                    dish.getDate().isAfter(DATE_2020_01_10.minusDays(1)) && dish.getDate().isBefore(DATE_2020_01_11.plusDays(1)))
            .collect(Collectors.toList());


    public static final List<Dish> MENUS_ON_NOW = Stream
            .concat(Stream.concat(RESTAURANT_1_DISHES.stream(), RESTAURANT_2_DISHES.stream()), RESTAURANT_3_DISHES.stream())
            .filter(dish -> dish.getDate().isEqual(LocalDate.now()))
            .sorted(Comparator.comparing(Dish::getPrice))
            .collect(Collectors.toList());

    public static Dish getNew() {
        return new Dish(null, now(), "newDish", 2500);//, RESTAURANT_1_ID);
    }

    public static Dish getUpdated() {
        Dish updated = new Dish(DISH01);
        updated.setName("nameUpdated");
        return updated;
    }

    public static void assertMatch(Dish actual, Dish expected) {
        assertThat(actual).isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Dish> actual, Dish... expected) {
        assertMatch(actual, List.of(expected));
    }

    public static void assertMatch(Iterable<Dish> actual, Iterable<Dish> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("restaurant").isEqualTo(expected);
    }
}