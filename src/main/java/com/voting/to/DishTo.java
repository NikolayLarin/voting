package com.voting.to;

import com.voting.HasId;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

public class DishTo implements HasId, Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private LocalDate date;

    @NotBlank
    @Size(min = 2, max = 120)
//    @SafeHtml // https://stackoverflow.com/questions/17480809
    private String name;

    @Range(min = 1)
    @NotNull
    private Integer price; // in cents

    public DishTo() {
    }

    public DishTo(Integer id, LocalDate date, String name, Integer price) {
        this.id = id;
        this.date = date;
        this.name = name;
        this.price = price;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "Dish{" +
                "id=" + id +
                ", date=" + date +
                ", name=" + name +
                ", price=" + price +
                '}';
    }
}