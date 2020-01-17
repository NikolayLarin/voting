package com.voting.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name = "dishes", uniqueConstraints = {@UniqueConstraint(columnNames =
        {"restaurantId", "date", "name"}, name = "dishes_unique_restaurant_date_name_idx")})
public class Dish extends AbstractBaseEntity {

    @Column(name = "date", nullable = false)
    @NotNull
    private LocalDate date;

    @Column(name = "name", nullable = false)
    @NotBlank
    @Size(min = 2, max = 120)
    private String name;

    // in cents
    @Column(name = "price", nullable = false)
    @Range(min = 1)
    private Integer price;

    // https://stackoverflow.com/questions/6311776/hibernate-foreign-keys-instead-of-entities
    @JoinColumn(name = "restaurantId", nullable = false, updatable = false, insertable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private Integer restaurantId;

    public Dish() {
    }

    public Dish(Integer id, LocalDate date, String name, Integer price, Integer restaurantId) {
        super(id);
        this.date = date;
        this.name = name;
        this.price = price;
        this.restaurantId = restaurantId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurant_id) {
        this.restaurantId = restaurant_id;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "id=" + id +
                ", name=" + name +
                ", price=" + price +
                '}';
    }
}