package com.usterka.restapi.model;

import java.time.LocalDate;

public class Continuation {
    private long id;
    private String spitzName;
    private LocalDate clickDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSpitzName() {
        return spitzName;
    }

    public void setSpitzName(String spitzName) {
        this.spitzName = spitzName;
    }

    public LocalDate getClickDate() {
        return clickDate;
    }

    public void setClickDate(LocalDate clickDate) {
        this.clickDate = clickDate;
    }


}

