package com.voting;

import com.voting.model.Restaurant;
import com.voting.model.Role;
import com.voting.model.User;
import com.voting.service.RestaurantService;
import com.voting.service.UserService;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 automatic resource management
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext(
                "spring/spring-app.xml", "spring/spring-db.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));

            System.out.println("\n_______________________________");
            UserService userService = appCtx.getBean(UserService.class);
            userService.create(new User(null, "new_email_1_@mail.ru", "password", Role.ROLE_ADMIN));
            userService.create(new User(null, "new_email_2_@mail.ru", "password", Role.ROLE_ADMIN, Role.ROLE_USER));
            System.out.println("\nuserService.getAll(): \n" + userService.getAll());

            System.out.println("\n_______________________________");
            RestaurantService restaurantService = appCtx.getBean(RestaurantService.class);
            restaurantService.create(new Restaurant(null, "New_Restaurant_1"));
            restaurantService.create(new Restaurant(null, "New_Restaurant_2"));
            System.out.println("\nrestaurantService.getAll(): \n" + restaurantService.getAll());
        }
    }
}