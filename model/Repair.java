package com.usterka.restapi.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Repair {
    private long id;
    private String registrationNo;
    private LocalDate expire;
    private LocalDate startDate;
    private Status status;
    private String description;
    private List<Picture> pictures;
    private String errorCode;
    private String repairDescription;
    private String spitzName;
    private List<Continuation> continuations;

    public Repair() {
        pictures = new ArrayList<>();
        continuations = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRegistrationNo() {
        return registrationNo;
    }

    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }

    public LocalDate getExpire() {
        return expire;
    }

    public void setExpire(LocalDate expire) {
        this.expire = expire;
    }

    public LocalDate getStartDate() { return startDate; }

    public void setStartDate(LocalDate startDate) {this.startDate = startDate; }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Picture> getPictures() {
        return pictures;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getRepairDescription() {
        return repairDescription;
    }

    public void setRepairDescription(String repairDescription) {
        this.repairDescription = repairDescription;
    }

    public String getSpitzName() {
        return spitzName;
    }

    public void setSpitzName(String spitzName) {
        this.spitzName = spitzName;
    }

    public List<Continuation> getContinuations() {
        return continuations;
    }
}
