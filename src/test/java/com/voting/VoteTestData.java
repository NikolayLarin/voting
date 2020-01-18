package com.voting;

import com.voting.model.Vote;

import java.time.LocalDate;
import java.util.List;

import static com.voting.DishTestData.DATE_2020_01_10;
import static com.voting.DishTestData.DATE_2020_01_11;
import static com.voting.RestaurantTestData.RESTAURANT_1;
import static com.voting.RestaurantTestData.RESTAURANT_2;
import static com.voting.RestaurantTestData.RESTAURANT_3;
import static com.voting.UserTestData.ADMIN;
import static com.voting.UserTestData.USER;
import static com.voting.model.AbstractBaseEntity.START_SEQ;
import static org.assertj.core.api.Assertions.assertThat;

public class VoteTestData {
    public static final int VOTE_1_ID = START_SEQ + 26;

    public static final Vote VOTE_1_USER_RESTAURANT_1 = new Vote(VOTE_1_ID, DATE_2020_01_10, USER, RESTAURANT_1);
    public static final Vote VOTE_2_ADMIN_RESTAURANT_1 = new Vote(VOTE_1_ID + 1, DATE_2020_01_10, ADMIN, RESTAURANT_1);
    public static final Vote VOTE_3_USER_RESTAURANT_2 = new Vote(VOTE_1_ID + 2, DATE_2020_01_11, USER, RESTAURANT_2);
    public static final Vote VOTE_4_ADMIN_RESTAURANT_3 = new Vote(VOTE_1_ID + 3, DATE_2020_01_11, ADMIN, RESTAURANT_3);

    public static final List<Vote> USER_VOTES = List.of(VOTE_3_USER_RESTAURANT_2, VOTE_1_USER_RESTAURANT_1);
    public static final List<Vote> ADMIN_VOTES = List.of(VOTE_4_ADMIN_RESTAURANT_3, VOTE_2_ADMIN_RESTAURANT_1);

    public static Vote getNew() {
        return new Vote(null, LocalDate.now(), USER, RESTAURANT_1);
    }

    public static Vote getUpdated() {
        Vote updated = new Vote(VOTE_1_USER_RESTAURANT_1);
        updated.setRestaurant(RESTAURANT_2);
        return updated;
    }

    public static void assertMatch(Vote actual, Vote expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "user", "restaurant");
    }

    public static void assertMatch(Iterable<Vote> actual, Vote... expected) {
        assertMatch(actual, List.of(expected));
    }

    public static void assertMatch(Iterable<Vote> actual, Iterable<Vote> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("user", "restaurant").isEqualTo(expected);
    }
}