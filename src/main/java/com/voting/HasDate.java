package com.voting;

import java.time.LocalDate;

public interface HasDate {
    LocalDate getDate();

    void setDate(LocalDate date);
}