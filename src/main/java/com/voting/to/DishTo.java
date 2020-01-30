package com.voting.to;

import com.voting.HasDate;
import com.voting.web.XssEscape;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

public class DishTo extends BaseTo implements HasDate, Serializable {
    private static final long serialVersionUID = 1L;

    private LocalDate date;

    @NotBlank
    @Size(min = 2, max = 120)
//    @SafeHtml // https://stackoverflow.com/questions/17480809,
//    deprecated because of vulnerability https://in.relation.to/2019/11/20/hibernate-validator-610-6018-released/
    @XssEscape
    private String name;

    @Range(min = 1)
    @NotNull
    private Integer price; // in cents

    public DishTo() {
    }

    public DishTo(Integer id, LocalDate date, String name, Integer price) {
        super(id);
        this.date = date;
        this.name = name;
        this.price = price;
    }

    @Override
    public LocalDate getDate() {
        return date;
    }

    @Override
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