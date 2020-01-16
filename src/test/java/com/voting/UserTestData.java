package com.voting;

import com.voting.model.Role;
import com.voting.model.User;

import java.util.Collections;
import java.util.List;

import static com.voting.model.AbstractBaseEntity.START_SEQ;
import static org.assertj.core.api.Assertions.assertThat;

public class UserTestData {
    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;

    public static final User USER = new User(USER_ID, "user@yandex.ru", "password", Role.ROLE_USER);
    public static final User ADMIN = new User(ADMIN_ID, "admin@gmail.com", "admin", Role.ROLE_ADMIN, Role.ROLE_USER);

    public static User getNew() {
        return new User(null, "new@gmail.com", "newPass", Collections.singleton(Role.ROLE_USER));
    }

    public static User getNewWithEmptyRoles() {
        return new User(null, "newWithNoRoles@gmail.com", "newPass", Collections.emptySet());
    }

    public static User getUpdated() {
        User updated = new User(USER);
        updated.setEmail("newEmail@gmail.com");
        return updated;
    }

    public static void assertMatch(User actual, User expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "meals");
    }

    public static void assertMatch(Iterable<User> actual, User... expected) {
        assertMatch(actual, List.of(expected));
    }

    public static void assertMatch(Iterable<User> actual, Iterable<User> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("meals").isEqualTo(expected);
    }
}