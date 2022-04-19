package com.example.stationManagement.database.entity;

import com.example.stationManagement.database.entity.pk.NurseStationMappingId;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@IdClass(NurseStationMappingId.class)
@Entity
@Table(name = "nurse_station_mapping")
public class NurseStationMapping implements Serializable {
    @Id
    @Column(name = "station_id")
    private Long stationId;

    @Id
    @Column(name = "nurse_id")
    private Long nurseId;

    @CreationTimestamp
    @Column(name = "create_time")
    private LocalDateTime createTime;

    @ManyToOne
    @JoinColumn(name = "nurse_id", insertable = false, updatable = false)
    private Nurse nurse;

    @ManyToOne
    @JoinColumn(name = "station_id", insertable = false, updatable = false)
    private Station station;

    public Long getStationId() {
        return stationId;
    }

    public void setStationId(Long stationId) {
        this.stationId = stationId;
    }

    public Long getNurseId() {
        return nurseId;
    }

    public void setNurseId(Long nurseId) {
        this.nurseId = nurseId;
    }

    public Nurse getNurse() {
        return nurse;
    }

    public void setNurse(Nurse nurse) {
        this.nurse = nurse;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NurseStationMapping that = (NurseStationMapping) o;
        return Objects.equals(stationId, that.stationId) &&
                Objects.equals(nurseId, that.nurseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stationId, nurseId);
    }
}
