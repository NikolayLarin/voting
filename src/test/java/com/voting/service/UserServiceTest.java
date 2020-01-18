package com.voting.service;

import com.voting.UserTestData;
import com.voting.model.Restaurant;
import com.voting.model.Role;
import com.voting.model.User;
import com.voting.util.exception.NotFoundException;
import org.hsqldb.HsqlException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static com.voting.UserTestData.ADMIN;
import static com.voting.UserTestData.ADMIN_ID;
import static com.voting.UserTestData.USER;
import static com.voting.UserTestData.USER_ID;
import static com.voting.UserTestData.assertMatch;
import static com.voting.UserTestData.getNew;
import static com.voting.UserTestData.getUpdated;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserServiceTest extends AbstractServiceTest {

    @Autowired
    private UserService service;

    @Test
    void create() {
        User newUser = UserTestData.getNew();
        User created = service.create(newUser);
        Integer newId = created.getId();
        newUser.setId(newId);
        assertMatch(created, newUser);
        assertMatch(service.get(newId), newUser);
    }

    @Test
    void createWithEmptyRoles() {
        User newUser = UserTestData.getNewWithEmptyRoles();
        User created = service.create(newUser);
        Integer newId = created.getId();
        newUser.setId(newId);
        assertMatch(created, newUser);
        assertMatch(service.get(newId), newUser);
    }

    @Test
    void createWithTwoRoles() {
        User newUser = new User(ADMIN);
        User created = service.create(newUser);
        Integer newId = created.getId();
        newUser.setId(newId);
        assertMatch(created, newUser);
        assertMatch(service.get(newId), newUser);
    }

    @Test
    void delete() {
        service.delete(USER_ID);
        assertThrows(NotFoundException.class, () ->
                service.delete(USER_ID));
    }

    @Test
    void deletedNotFound() {
        assertThrows(NotFoundException.class, () ->
                service.delete(1));
    }

    @Test
    void get() {
        User user = service.get(ADMIN_ID);
        assertMatch(user, ADMIN);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () ->
                service.get(1));
    }

    @Test
    void getByEmail() {
        User user = service.getByEmail("user@gmail.com");
        assertMatch(user, USER);
    }

    @Test
    void getAll() {
        List<User> all = service.getAll();
        assertMatch(all, ADMIN, USER);
    }

    @Test
    void update() {
        User updated = getUpdated();
        service.update(new User(updated));
        assertMatch(service.get(USER_ID), updated);
    }

    @Test
    void updateNotFound() {
        User notExist = getNew();
        notExist.setId(1);
        NotFoundException e = assertThrows(NotFoundException.class, () ->
                service.update(notExist));
        assertEquals(e.getMessage(), "Not found entity with id=" + 1);
    }

    @Test
    void createWithException() {
        validateRootCause(() -> service.create(new User(null, "  ", "password", Role.ROLE_USER)), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new User(null, "user@gmail.com", "pass", Role.ROLE_USER)), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new User(null, "user@gmail.com", "password", Role.ROLE_USER)), HsqlException.class);
    }
}