package com.example.stationManagement.controller;

import com.example.stationManagement.database.entity.Nurse;
import com.example.stationManagement.database.entity.Station;
import com.example.stationManagement.model.NurseInfo;
import com.example.stationManagement.model.StationInfo;
import com.example.stationManagement.model.StationInfoOfNurse;
import com.example.stationManagement.service.NurseService;
import com.example.stationManagement.service.StationService;
import org.hibernate.internal.util.collections.CollectionHelper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Optional;

@Validated
@RequestMapping("/nurse")
@RestController
public class NurseInfoController {
    private NurseService nurseService;
    private StationService stationService;

    public NurseInfoController(NurseService nurseService, StationService stationService) {
        this.nurseService = nurseService;
        this.stationService = stationService;
    }

    @GetMapping
    public Iterable<Nurse> findAll() {
        return nurseService.findAll();
    }

    @GetMapping("/{id}")
    public Nurse getNurse(@PathVariable @Min(0) Long id) {
        return nurseService.findById(id).get();
    }

    @PostMapping
    public Nurse createNurse(@RequestBody @Valid NurseInfo nurseInfo) {
        if (nurseService.findById(nurseInfo.getId()).isPresent()) throw new RuntimeException("nurse已存在");

        if (CollectionHelper.isNotEmpty(nurseInfo.getStationIds())) {
            List<Station> stations = stationService.findAllById(nurseInfo.getStationIds());
            if (stations.size() != nurseInfo.getStationIds().size()) throw new RuntimeException("站點資料有誤");
        }

        return nurseService.createNurseAndMapping(nurseInfo);
    }

    @DeleteMapping("/{id}")
    public String deleteNurse(@PathVariable @Min(0) Long id) {
        nurseService.deleteNurseAndMapping(id);
        return "success";
    }

    @PutMapping("/{id}")
    public Nurse updateNurse(@PathVariable Long id, @RequestBody @Valid NurseInfo nurseInfo) {
        Optional<Nurse> nurseOptional = nurseService.findById(id);
        if (nurseOptional.isEmpty()) throw new RuntimeException("nurse不存在");

        if (CollectionHelper.isNotEmpty(nurseInfo.getStationIds())) {
            List<Station> stations = stationService.findAllById(nurseInfo.getStationIds());
            if (stations.size() != nurseInfo.getStationIds().size()) throw new RuntimeException("站點資料有誤");
        }

        return nurseService.updateNurseAndMapping(nurseOptional.get(), nurseInfo);
    }

    @GetMapping("/{nurseId}/stations")
    public List<StationInfoOfNurse> findStationInfoByNurseId(@PathVariable @Min(0) Long nurseId) {
        return nurseService.findStationInfoByNurseId(nurseId);
    }
}
