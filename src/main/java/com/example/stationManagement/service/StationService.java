package com.example.stationManagement.service;

import com.example.stationManagement.database.entity.Station;
import com.example.stationManagement.model.NurseInfoOfStation;
import com.example.stationManagement.model.StationInfo;

import java.util.List;

public interface StationService {
    Iterable<Station> findAll();
    Station findById(Long id);
    Station createStation(StationInfo stationInfo);
    Station updateStation(Long id, StationInfo stationInfo);
    void deleteStationAndMapping(Long stationId);
    List<NurseInfoOfStation> findNurseInfoByStationId(Long stationId);
}
