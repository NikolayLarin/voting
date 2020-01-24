package com.voting.repository.vote;

import com.voting.model.Vote;
import com.voting.repository.restaurant.CrudRestaurantRepository;
import com.voting.repository.user.CrudUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class DataJpaVoteRepository {
    private final CrudVoteRepository crudVoteRepository;
    private final CrudUserRepository crudUserRepository;
    private final CrudRestaurantRepository crudRestaurantRepository;

    @Autowired
    public DataJpaVoteRepository(CrudVoteRepository crudVoteRepository,
                                 CrudUserRepository crudUserRepository,
                                 CrudRestaurantRepository crudRestaurantRepository) {
        this.crudVoteRepository = crudVoteRepository;
        this.crudUserRepository = crudUserRepository;
        this.crudRestaurantRepository = crudRestaurantRepository;
    }

    @Transactional
    // null if updated vote do not belong to user
    public Vote save(Vote vote, int userId, int restaurantId) {
        if (!vote.isNew() && get(vote.getId(), userId) == null) {
            return null;
        }
        vote.setUser(crudUserRepository.getOne(userId));
        vote.setRestaurant(crudRestaurantRepository.getOne(restaurantId));
        return crudVoteRepository.save(vote);
    }

    // false if vote do not belong to user
    public boolean delete(int id, int userId) {
        return crudVoteRepository.delete(id, userId) != 0;
    }

    // null if vote do not belong to user
    public Vote get(int id, int userId) {
        return crudVoteRepository
                .findById(id)
                .filter(vote -> vote.getUser().getId() == userId)
                .orElse(null);
    }

    // ORDERED by date desc
    public List<Vote> getAll(int userId) {
        return crudVoteRepository.getAll(userId);
    }
}