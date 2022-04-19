package com.example.stationManagement.database.entity.pk;

import java.io.Serializable;
import java.util.Objects;

public class NurseStationMappingId implements Serializable {
    private Long stationId;
    private Long nurseId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NurseStationMappingId that = (NurseStationMappingId) o;
        return Objects.equals(stationId, that.stationId) &&
                Objects.equals(nurseId, that.nurseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stationId, nurseId);
    }
}
