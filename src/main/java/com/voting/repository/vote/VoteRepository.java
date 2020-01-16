package com.voting.repository.vote;

import com.voting.model.Vote;

import java.util.List;

public interface VoteRepository {
    // null if updated vote do not belong to user
    Vote save(Vote vote, int userId, int restaurantId);

    // false if vote do not belong to user
    boolean delete(int id, int userId);

    // null if vote do not belong to user
    Vote get(int id, int userId);

    // ORDERED by date desc
    List<Vote> getAll(int userId);
}