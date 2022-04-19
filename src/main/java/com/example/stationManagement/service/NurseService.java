package com.example.stationManagement.service;

import com.example.stationManagement.database.entity.Nurse;
import com.example.stationManagement.model.NurseInfo;
import com.example.stationManagement.model.StationInfoOfNurse;

import java.util.List;
import java.util.Optional;

public interface NurseService {
    Iterable<Nurse> findAll();
    Optional<Nurse> findById(Long id);
    Nurse createNurseAndMapping(NurseInfo nurseInfo);
    Nurse updateNurseAndMapping(Nurse nurse, NurseInfo nurseInfo);
    void deleteNurseAndMapping(Long nurseId);
    List<StationInfoOfNurse> findStationInfoByNurseId(Long nurseId);
}
