package com.example.stationManagement.controller;

import com.example.stationManagement.database.entity.Station;
import com.example.stationManagement.database.repository.StationRepository;
import com.example.stationManagement.model.StationInfo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.Optional;

@RequestMapping("/station")
@RestController
@Validated
public class StationController {
    private StationRepository stationRepository;

    public StationController(StationRepository stationRepository) {
        this.stationRepository = stationRepository;
    }

    @GetMapping
    public Iterable<Station> getStations() {
        return stationRepository.findAll();
    }

    @GetMapping("/{id}")
    public Station getStation(@PathVariable @Min(0) Long id) {
        return stationRepository.findById(id).get();
    }

    @PostMapping
    public Station createStation(@RequestBody @Valid StationInfo stationInfo) {

        Optional<Station> stationOptional = stationRepository.findByName(stationInfo.getName());

        if (stationOptional.isPresent())
            throw new RuntimeException("station名稱重複");

        Station station = new Station();
        station.setName(stationInfo.getName());

        return stationRepository.save(station);
    }

    @PutMapping("/{id}")
    public Station updateStation(@PathVariable @Min(1) Long id, @RequestBody @Valid StationInfo station) {
        if (stationRepository.findByName(station.getName()).isPresent())
            throw new RuntimeException("station名稱重複");

        Optional<Station> dbStationOptional = stationRepository.findById(id);
        if (dbStationOptional.isPresent()) {
            Station dbStation = dbStationOptional.get();
            dbStation.setName(station.getName());

            return stationRepository.save(dbStation);
        }

        throw new RuntimeException("station不存在");
    }

}
