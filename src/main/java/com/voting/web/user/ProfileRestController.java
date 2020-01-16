package com.voting.web.user;

import com.voting.model.User;
import com.voting.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static com.voting.util.ValidationUtil.assureIdConsistent;
import static com.voting.web.SecurityUtil.authUserId;


@RestController
@RequestMapping(ProfileRestController.REST_URL)
public class ProfileRestController {
    static final String REST_URL = "/rest/profile";

    protected final Logger log = LoggerFactory.getLogger(getClass());

    private final UserService service;

    @Autowired
    public ProfileRestController(UserService service) {
        this.service = service;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public User get() {
        log.info("get {}", authUserId());
        return service.get(authUserId());
    }

    @GetMapping("/by")
    public User getByMail(@RequestParam String email) {
        log.info("getByEmail {}", email);
        return service.getByEmail(email);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete() {
        log.info("delete {}", authUserId());
        service.delete(authUserId());
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody User user) {
        log.info("update {} with id={}", user, authUserId());
        assureIdConsistent(user, authUserId());
        service.update(user);
    }
}