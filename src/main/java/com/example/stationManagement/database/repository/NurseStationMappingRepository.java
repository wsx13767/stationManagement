package com.example.stationManagement.database.repository;

import com.example.stationManagement.database.entity.NurseStationMapping;
import com.example.stationManagement.database.entity.pk.NurseStationMappingId;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NurseStationMappingRepository extends CrudRepository<NurseStationMapping, NurseStationMappingId> {
    @Query("select n from NurseStationMapping n where n.stationId = ?1")
    List<NurseStationMapping> findByStationId(Long stationId);

    @Modifying
    @Query("delete from NurseStationMapping n where n.stationId = ?1")
    void deleteByStationId(Long stationId);
}
