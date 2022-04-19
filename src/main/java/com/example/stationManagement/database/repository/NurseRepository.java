package com.example.stationManagement.database.repository;

import com.example.stationManagement.database.entity.Nurse;
import org.springframework.data.repository.CrudRepository;

public interface NurseRepository extends CrudRepository<Nurse, Long> {
}
