package com.example.stationManagement.model;

import java.time.LocalDateTime;

public class NurseInfoOfStation {
    private String sno;
    private String name;
    private LocalDateTime joinTime;

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(LocalDateTime joinTime) {
        this.joinTime = joinTime;
    }
}
