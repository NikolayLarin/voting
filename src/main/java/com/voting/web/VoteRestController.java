package com.voting.web;

import com.voting.model.Vote;
import com.voting.service.VoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.voting.web.SecurityUtil.authUserId;

@RestController
@RequestMapping(value = VoteRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteRestController {

    static final String REST_URL = "/votes";

    protected final Logger log = LoggerFactory.getLogger(getClass());

    private final VoteService service;

    @Autowired
    public VoteRestController(VoteService service) {
        this.service = service;
    }

    @GetMapping
    public List<Vote> getAll() {
        log.info("getAll votes for user with id={}", authUserId());
        return service.getAll(authUserId());
    }
}