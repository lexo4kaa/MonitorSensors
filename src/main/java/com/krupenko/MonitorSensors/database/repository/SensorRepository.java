package com.krupenko.MonitorSensors.database.repository;

import com.krupenko.MonitorSensors.database.entity.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorRepository extends JpaRepository<Sensor, Integer>, FilterSensorRepository {
}
