package com.krupenko.MonitorSensors.mapper;

import com.krupenko.MonitorSensors.database.entity.Unit;
import com.krupenko.MonitorSensors.dto.UnitReadDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UnitReadMapper {

    UnitReadDto unitToUnitReadDto(Unit unit);

}
