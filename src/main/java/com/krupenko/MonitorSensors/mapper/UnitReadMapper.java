package com.krupenko.MonitorSensors.mapper;

import com.krupenko.MonitorSensors.dto.UnitReadDto;
import com.krupenko.MonitorSensors.database.entity.Unit;
import org.springframework.stereotype.Component;

@Component
public class UnitReadMapper implements Mapper<Unit, UnitReadDto> {

    @Override
    public UnitReadDto map(Unit object) {
        return new UnitReadDto(
                object.getId(),
                object.getValue()
        );
    }

}
