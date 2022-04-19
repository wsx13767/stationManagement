package com.example.stationManagement.service.impl;

import com.example.stationManagement.database.entity.Nurse;
import com.example.stationManagement.database.entity.NurseStationMapping;
import com.example.stationManagement.database.repository.NurseRepository;
import com.example.stationManagement.database.repository.NurseStationMappingRepository;
import com.example.stationManagement.model.NurseInfo;
import com.example.stationManagement.model.StationInfoOfNurse;
import com.example.stationManagement.service.NurseService;
import org.hibernate.internal.util.collections.CollectionHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NurseServiceImpl implements NurseService {
    private NurseRepository nurseRepository;
    private NurseStationMappingRepository nurseStationMappingRepository;

    public NurseServiceImpl(NurseRepository nurseRepository, NurseStationMappingRepository nurseStationMappingRepository) {
        this.nurseRepository = nurseRepository;
        this.nurseStationMappingRepository = nurseStationMappingRepository;
    }

    @Override
    public Iterable<Nurse> findAll() {
        return nurseRepository.findAll();
    }

    @Override
    public Optional<Nurse> findById(Long id) {
        return nurseRepository.findById(id);
    }

    @Override
    @Transactional
    public Nurse createNurseAndMapping(NurseInfo nurseInfo) {
        Nurse nurse = new Nurse();
        nurse.setId(nurseInfo.getId());
        nurse.setName(nurseInfo.getName());
        nurse = nurseRepository.save(nurse);

        if (CollectionHelper.isNotEmpty(nurseInfo.getStationIds())) {
            List<NurseStationMapping> nurseStationMappings = nurseInfo.getStationIds().stream().map(stationId -> {
                NurseStationMapping mapping = new NurseStationMapping();
                mapping.setNurseId(nurseInfo.getId());
                mapping.setStationId(stationId);
                return mapping;
            }).collect(Collectors.toList());
            nurseStationMappingRepository.saveAll(nurseStationMappings);
        }

        return nurse;
    }

    @Override
    @Transactional
    public Nurse updateNurseAndMapping(Nurse nurse, NurseInfo nurseInfo) {
        nurseRepository.delete(nurse);

        Nurse newNurse = new Nurse();
        newNurse.setId(nurseInfo.getId());
        newNurse.setName(nurseInfo.getName());

        newNurse = nurseRepository.save(newNurse);


        nurseStationMappingRepository.deleteByNurseId(nurse.getId());

        if (CollectionHelper.isNotEmpty(nurseInfo.getStationIds())) {
            List<NurseStationMapping> nurseStationMappings = nurseInfo.getStationIds().stream().map(stationId -> {
                NurseStationMapping mapping = new NurseStationMapping();
                mapping.setNurseId(nurseInfo.getId());
                mapping.setStationId(stationId);
                return mapping;
            }).collect(Collectors.toList());
            nurseStationMappingRepository.saveAll(nurseStationMappings);
        }

        return newNurse;
    }

    @Override
    @Transactional
    public void deleteNurseAndMapping(Long nurseId) {
        nurseRepository.deleteById(nurseId);
        nurseStationMappingRepository.deleteByNurseId(nurseId);
    }

    @Override
    public List<StationInfoOfNurse> findStationInfoByNurseId(Long nurseId) {
        return nurseStationMappingRepository.findByNurseId(nurseId)
                .stream().map(mapping -> {
            StationInfoOfNurse stationInfoOfNurse = new StationInfoOfNurse();
            stationInfoOfNurse.setId(mapping.getStationId());
            stationInfoOfNurse.setName(mapping.getStation().getName());
            return stationInfoOfNurse;
        }).collect(Collectors.toList());
    }
}
