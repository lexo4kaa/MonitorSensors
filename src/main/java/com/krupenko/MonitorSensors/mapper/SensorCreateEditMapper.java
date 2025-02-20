package com.krupenko.MonitorSensors.mapper;

import com.krupenko.MonitorSensors.database.entity.Sensor;
import com.krupenko.MonitorSensors.database.entity.Type;
import com.krupenko.MonitorSensors.database.entity.Unit;
import com.krupenko.MonitorSensors.database.repository.TypeRepository;
import com.krupenko.MonitorSensors.database.repository.UnitRepository;
import com.krupenko.MonitorSensors.dto.SensorCreateEditDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SensorCreateEditMapper implements Mapper<SensorCreateEditDto, Sensor> {

    private final TypeRepository typeRepository;
    private final UnitRepository unitRepository;

    @Override
    public Sensor map(SensorCreateEditDto fromObject, Sensor toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    @Override
    public Sensor map(SensorCreateEditDto object) {
        Sensor sensor = new Sensor();
        copy(object, sensor);
        return sensor;
    }

    private void copy(SensorCreateEditDto object, Sensor sensor) {
        sensor.setName(object.getName());
        sensor.setModel(object.getModel());
        sensor.setFrom(object.getFromValue());
        sensor.setTo(object.getToValue());
        sensor.setType(getType(object.getTypeId()));
        sensor.setUnit(getUnit(object.getUnitId()));
        sensor.setLocation(object.getLocation());
        sensor.setDescription(object.getDescription());
    }

    private Type getType(Integer typeId) {
        return Optional.ofNullable(typeId).flatMap(typeRepository::findById).orElseThrow(IllegalArgumentException::new);
    }

    private Unit getUnit(Integer unitId) {
        return Optional.ofNullable(unitId).flatMap(unitRepository::findById).orElse(null);
    }

}
