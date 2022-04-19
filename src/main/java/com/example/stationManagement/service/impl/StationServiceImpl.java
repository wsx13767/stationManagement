package com.example.stationManagement.service.impl;

import com.example.stationManagement.database.entity.NurseStationMapping;
import com.example.stationManagement.database.entity.Station;
import com.example.stationManagement.database.repository.NurseStationMappingRepository;
import com.example.stationManagement.database.repository.StationRepository;
import com.example.stationManagement.model.NurseInfoOfStation;
import com.example.stationManagement.model.StationInfo;
import com.example.stationManagement.service.StationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StationServiceImpl implements StationService {
    private StationRepository stationRepository;
    private NurseStationMappingRepository nurseStationMappingRepository;

    public StationServiceImpl(StationRepository stationRepository,
                              NurseStationMappingRepository nurseStationMappingRepository) {
        this.stationRepository = stationRepository;
        this.nurseStationMappingRepository = nurseStationMappingRepository;
    }

    @Override
    public Iterable<Station> findAll() {
        return stationRepository.findAll();
    }

    @Override
    public Station findById(Long id) {
        if (id == null) new RuntimeException("id錯誤");

        Station station = stationRepository.findById(id).get();

        return station;
    }

    @Override
    public Station createStation(StationInfo stationInfo) {
        if (stationInfo == null) throw new RuntimeException("stationInfo不可為空");
        if (StringUtils.isBlank(stationInfo.getName())) throw new RuntimeException("name 不可為空");

        if (stationRepository.findByName(stationInfo.getName()).isPresent())
            throw new RuntimeException("station名稱重複");

        Station station = new Station();
        station.setName(stationInfo.getName());

        return stationRepository.save(station);
    }

    @Override
    public Station updateStation(Long id, StationInfo stationInfo) {
        if (id == null) new RuntimeException("id錯誤");
        if (stationRepository.findByName(stationInfo.getName()).isPresent())
            throw new RuntimeException("station名稱重複");

        Optional<Station> dbStationOptional = stationRepository.findById(id);

        if (dbStationOptional.isEmpty())
            throw new RuntimeException("station不存在");

        Station dbStation = dbStationOptional.get();
        dbStation.setName(stationInfo.getName());

        return stationRepository.save(dbStation);

    }

    @Override
    @Transactional
    public void deleteStationAndMapping(Long stationId) {
        if (stationId == null) new RuntimeException("stationId錯誤");
        stationRepository.deleteById(stationId);
        nurseStationMappingRepository.deleteByStationId(stationId);
    }

    @Override
    public List<NurseInfoOfStation> findNurseInfoByStationId(Long stationId) {
        if (stationId == null) new RuntimeException("stationId錯誤");

        return nurseStationMappingRepository.findByStationId(stationId)
                .stream().map(mapping -> {
                    NurseInfoOfStation info = new NurseInfoOfStation();
                    info.setSno(mapping.getNurse().getSno());
                    info.setName(mapping.getNurse().getName());
                    info.setJoinTime(mapping.getCreateTime());
                    return info;
                }).collect(Collectors.toList());
    }
}
