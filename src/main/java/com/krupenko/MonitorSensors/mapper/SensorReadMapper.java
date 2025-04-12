package com.krupenko.MonitorSensors.mapper;

import com.krupenko.MonitorSensors.database.entity.Sensor;
import com.krupenko.MonitorSensors.database.entity.Type;
import com.krupenko.MonitorSensors.database.entity.Unit;
import com.krupenko.MonitorSensors.dto.SensorReadDto;
import com.krupenko.MonitorSensors.dto.TypeReadDto;
import com.krupenko.MonitorSensors.dto.UnitReadDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Mapper(componentModel = "spring")
public abstract class SensorReadMapper {

    @Autowired
    protected TypeReadMapper typeReadMapper;

    @Autowired
    protected UnitReadMapper unitReadMapper;

    @Mapping(source = "type", target = "type", qualifiedByName = "typeToTypeReadDto")
    @Mapping(source = "unit", target = "unit", qualifiedByName = "unitToUnitReadDto")
    @Mapping(source = "from", target = "range.from")
    @Mapping(source = "to", target = "range.to")
    public abstract SensorReadDto sensorToSensorReadDto(Sensor object);

    @Named("typeToTypeReadDto")
    protected TypeReadDto typeToTypeReadDto(Type type) {
        return Optional.ofNullable(type)
                .map(typeReadMapper::typeToTypeReadDto)
                .orElseThrow(IllegalArgumentException::new);
    }

    @Named("unitToUnitReadDto")
    protected UnitReadDto unitToUnitReadDto(Unit unit) {
        return Optional.ofNullable(unit)
                .map(unitReadMapper::unitToUnitReadDto)
                .orElse(null);
    }

}
