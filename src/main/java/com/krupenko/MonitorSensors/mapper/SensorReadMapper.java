package com.krupenko.MonitorSensors.mapper;

import com.krupenko.MonitorSensors.database.entity.Sensor;
import com.krupenko.MonitorSensors.dto.RangeReadDto;
import com.krupenko.MonitorSensors.dto.SensorReadDto;
import com.krupenko.MonitorSensors.dto.TypeReadDto;
import com.krupenko.MonitorSensors.dto.UnitReadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SensorReadMapper implements Mapper<Sensor, SensorReadDto> {

    private final TypeReadMapper typeReadMapper;
    private final UnitReadMapper unitReadMapper;

    @Override
    public SensorReadDto map(Sensor object) {
        TypeReadDto type = Optional.ofNullable(object.getType())
                .map(typeReadMapper::map)
                .orElseThrow(IllegalArgumentException::new);
        UnitReadDto unit = Optional.ofNullable(object.getUnit())
                .map(unitReadMapper::map)
                .orElse(null);
        RangeReadDto range = new RangeReadDto(object.getFrom(), object.getTo());
        return new SensorReadDto(
                object.getId(),
                object.getName(),
                object.getModel(),
                range,
                type,
                unit,
                object.getLocation(),
                object.getDescription()
        );
    }

}
