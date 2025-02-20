package com.krupenko.MonitorSensors.database.repository;

import com.krupenko.MonitorSensors.database.entity.Sensor;
import com.krupenko.MonitorSensors.dto.SensorFilter;

import java.util.List;

public interface FilterSensorRepository {

    List<Sensor> findAllByFilter(SensorFilter filter);

}
