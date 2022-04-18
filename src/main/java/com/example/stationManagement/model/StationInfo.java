package com.example.stationManagement.model;

import javax.validation.constraints.NotBlank;

public class StationInfo {

    @NotBlank(message = "名稱不可為空")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
