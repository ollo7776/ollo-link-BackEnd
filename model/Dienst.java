package com.usterka.restapi.model;

import java.time.LocalDate;

public class Dienst {
    private long id;
    private String number;
    private LocalDate date;
    private int hourBegin;
    private int minutesBegin;
    private int hourEnd;
    private int minutesEnd;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getHourBegin() {
        return hourBegin;
    }

    public void setHourBegin(int hourBegin) {
        this.hourBegin = hourBegin;
    }

    public int getMinutesBegin() {
        return minutesBegin;
    }

    public void setMinutesBegin(int minutesBegin) {
        this.minutesBegin = minutesBegin;
    }

    public int getHourEnd() {
        return hourEnd;
    }

    public void setHourEnd(int hourEnd) {
        this.hourEnd = hourEnd;
    }

    public int getMinutesEnd() {
        return minutesEnd;
    }

    public void setMinutesEnd(int minutesEnd) {
        this.minutesEnd = minutesEnd;
    }
}


