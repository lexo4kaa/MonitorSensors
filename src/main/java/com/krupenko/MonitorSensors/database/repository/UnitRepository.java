package com.krupenko.MonitorSensors.database.repository;

import com.krupenko.MonitorSensors.database.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnitRepository extends JpaRepository<Unit, Integer> {
}
