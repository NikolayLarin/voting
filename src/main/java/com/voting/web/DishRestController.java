package com.voting.web;

import com.voting.model.Dish;
import com.voting.service.DishService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = DishRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class DishRestController {

    static final String REST_URL = "/menus";

    protected final Logger log = LoggerFactory.getLogger(getClass());

    private final DishService service;

    @Autowired
    public DishRestController(DishService service) {
        this.service = service;
    }

    @GetMapping
    public List<Dish> getTodayMenus() {
        log.info("getTodayMenus");
        return service.getTodayMenus();
    }

    @GetMapping("/{restaurantId}")
    public List<Dish> getAllOnDate(@RequestParam LocalDate date, @PathVariable int restaurantId) {
        log.info("get menu on date {} for restaurant {}", date, restaurantId);
        return service.getAllOnDate(date, restaurantId);
    }

    @GetMapping("/{restaurantId}/filter")
    public List<Dish> getBetween(@RequestParam @NonNull LocalDate startDate,
                                 @RequestParam @NonNull LocalDate endDate,
                                 @PathVariable int restaurantId) {
        log.info("getAll dishes of restaurant {} between dates ({} – {})", restaurantId, startDate, endDate);
        return service.getBetweenInclusive(startDate, endDate, restaurantId);
    }
}