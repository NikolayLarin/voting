package com.voting.service;

import com.voting.model.Vote;
import com.voting.repository.vote.VoteRepository;
import com.voting.util.exception.IllegalRequestDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalTime;
import java.util.List;

import static com.voting.util.ValidationUtil.checkNotFoundWithId;

@Service
public class VoteService {

    private final VoteRepository repository;

    @Autowired
    public VoteService(VoteRepository repository) {
        this.repository = repository;
    }

    public Vote create(Vote vote, int userId, int restaurantId) {
        return save(vote, userId, restaurantId);
    }

    public void delete(int id, int userId) {
        checkNotFoundWithId(repository.delete(id, userId), id);
    }

    public Vote get(int id, int restaurantId) {
        return checkNotFoundWithId(repository.get(id, restaurantId), id);
    }

    public List<Vote> getAll(int userId) {
        return repository.getAll(userId);
    }

    public void update(Vote vote, int userId, int restaurantId) {
        if (LocalTime.now().isBefore(LocalTime.of(11, 0))) {
            throw new IllegalRequestDataException("Voting time expired at 11:00 AM");
        }
        save(vote, userId, restaurantId);
    }

    private Vote save(Vote vote, int userId, int restaurantId) {
        Assert.notNull(vote, "dish must not be null");
        return checkNotFoundWithId(repository.save(vote, userId, restaurantId), vote.getId());
    }
}