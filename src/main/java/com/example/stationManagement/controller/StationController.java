package com.example.stationManagement.controller;

import com.example.stationManagement.database.entity.Station;
import com.example.stationManagement.model.NurseInfoOfStation;
import com.example.stationManagement.model.StationInfo;
import com.example.stationManagement.service.StationService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RequestMapping("/station")
@RestController
@Validated
public class StationController {
    private StationService stationService;

    public StationController(StationService stationService) {
        this.stationService = stationService;
    }

    @GetMapping
    public Iterable<Station> getStations() {
        return stationService.findAll();
    }

    @GetMapping("/{id}")
    public Station getStation(@PathVariable @Min(0) Long id) {
        return stationService.findById(id);
    }

    @PostMapping
    public Station createStation(@RequestBody @Valid StationInfo stationInfo) {
        return stationService.createStation(stationInfo);
    }

    @PutMapping("/{id}")
    public Station updateStation(@PathVariable @Min(1) Long id, @RequestBody @Valid StationInfo stationInfo) {
        return stationService.updateStation(id, stationInfo);
    }

    @DeleteMapping("/{id}")
    public String deleteStation(@PathVariable @Min(0) Long id) {
        stationService.deleteStationAndMapping(id);
        return "success";
    }

    @GetMapping("/{stationId}/nurses")
    public List<NurseInfoOfStation> findNurseInfoByStationId(@PathVariable @Min(0) Long stationId) {
        return stationService.findNurseInfoByStationId(stationId);
    }

}
