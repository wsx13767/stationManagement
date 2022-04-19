package com.example.stationManagement.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

public class NurseInfo {
    @Min(0)
    private Long id;

    @NotBlank(message = "姓名不可為空")
    private String name;

    private Set<Long> stationIds;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Long> getStationIds() {
        return stationIds;
    }

    public void setStationIds(Set<Long> stationIds) {
        this.stationIds = stationIds;
    }
}
