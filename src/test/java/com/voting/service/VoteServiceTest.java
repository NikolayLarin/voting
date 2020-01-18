package com.voting.service;

import com.voting.VoteTestData;
import com.voting.model.Vote;
import com.voting.util.exception.IllegalRequestDataException;
import com.voting.util.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.voting.RestaurantTestData.RESTAURANT_1_ID;
import static com.voting.RestaurantTestData.RESTAURANT_2_ID;
import static com.voting.UserTestData.ADMIN_ID;
import static com.voting.UserTestData.USER_ID;
import static com.voting.VoteTestData.ADMIN_VOTES;
import static com.voting.VoteTestData.USER_VOTES;
import static com.voting.VoteTestData.VOTE_1_ID;
import static com.voting.VoteTestData.VOTE_1_USER_RESTAURANT_1;
import static com.voting.VoteTestData.assertMatch;
import static com.voting.VoteTestData.getUpdated;
import static com.voting.util.DateTimeUtil.isVotingTimeExpired;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class VoteServiceTest extends AbstractServiceTest {

    @Autowired
    private VoteService service;

    @Test
    void create() {
        Vote newVote = VoteTestData.getNew();
        Vote created = service.create(USER_ID, RESTAURANT_1_ID);
        Integer newId = created.getId();
        newVote.setId(newId);
        assertMatch(created, newVote);
        assertMatch(service.get(newId, USER_ID), newVote);
    }

    @Test
    void delete() {
        service.delete(VOTE_1_ID, USER_ID);
        assertThrows(NotFoundException.class, () ->
                service.get(VOTE_1_ID, USER_ID));
    }

    @Test
    void deleteNotFound() {
        assertThrows(NotFoundException.class, () ->
                service.delete(1, USER_ID));
    }

    @Test
    void deleteNotOwn() {
        assertThrows(NotFoundException.class, () ->
                service.delete(VOTE_1_ID, RESTAURANT_1_ID));
    }

    @Test
    void get() {
        Vote actual = service.get(VOTE_1_ID, USER_ID);
        assertMatch(actual, VOTE_1_USER_RESTAURANT_1);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () ->
                service.get(1, USER_ID));
    }

    @Test
    void getNotOwn() {
        assertThrows(NotFoundException.class, () ->
                service.get(VOTE_1_ID, ADMIN_ID));
    }

    @Test
    void getAll() {
        assertMatch(service.getAll(USER_ID), USER_VOTES);
        assertMatch(service.getAll(ADMIN_ID), ADMIN_VOTES);
    }

    @Test
    void update() {
        Vote updated = getUpdated();
        if (isVotingTimeExpired()) {
            IllegalRequestDataException e = assertThrows(IllegalRequestDataException.class, () -> update(updated));
            assertEquals(e.getMessage(), "Voting time expired at 11:00 AM");
        } else {
            update(updated);
            assertMatch(service.get(VOTE_1_ID, USER_ID), updated);
        }
    }

    @Test
    void updateNotFound() {
        if (!isVotingTimeExpired()) {
            Vote updated = getUpdated();
            updated.setId(1);
            NotFoundException e = assertThrows(NotFoundException.class, () -> update(updated));
            assertEquals(e.getMessage(), "Not found entity with id=" + 1);
        }
    }

    @Test
    void updateNotOwn() {
        if (!isVotingTimeExpired()) {
            NotFoundException e = assertThrows(NotFoundException.class, () ->
                    service.update(new Vote(VOTE_1_USER_RESTAURANT_1), ADMIN_ID, RESTAURANT_2_ID));
            assertEquals(e.getMessage(), "Not found entity with id=" + VOTE_1_ID);
        }
    }

    private void update(Vote updated) {
        service.update(updated, USER_ID, RESTAURANT_2_ID);
    }
}