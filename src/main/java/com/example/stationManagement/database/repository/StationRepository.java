package com.example.stationManagement.database.repository;

import com.example.stationManagement.database.entity.Station;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface StationRepository extends CrudRepository<Station, Long> {

    @Query("select s from Station s where s.name = ?1")
    Optional<Station> findByName(String name);
}
