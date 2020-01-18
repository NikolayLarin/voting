package com.voting.service;

import com.voting.UserTestData;
import com.voting.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.voting.UserTestData.assertMatch;

public class UserServiceTest extends AbstractServiceTest {

    @Autowired
    private UserService service;

    @Test
    public void create() {
        User newUser = UserTestData.getNew();
        User created = service.create(newUser);
        Integer newId = created.getId();
        newUser.setId(newId);
        assertMatch(created, newUser);
        assertMatch(service.get(newId), newUser);
    }

    @Test
    public void createWithEmptyRoles() {
        User newUser = UserTestData.getNewWithEmptyRoles();
        User created = service.create(newUser);
        Integer newId = created.getId();
        newUser.setId(newId);
        assertMatch(created, newUser);
        assertMatch(service.get(newId), newUser);
    }

    @Test
    public void delete() {
    }

    @Test
    public void get() {
    }

    @Test
    public void getByEmail() {
    }

    @Test
    public void getAll() {
    }

    @Test
    public void update() {
    }
}