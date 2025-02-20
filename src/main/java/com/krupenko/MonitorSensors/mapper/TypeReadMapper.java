package com.krupenko.MonitorSensors.mapper;

import com.krupenko.MonitorSensors.dto.TypeReadDto;
import com.krupenko.MonitorSensors.database.entity.Type;
import org.springframework.stereotype.Component;

@Component
public class TypeReadMapper implements Mapper<Type, TypeReadDto> {

    @Override
    public TypeReadDto map(Type object) {
        return new TypeReadDto(
                object.getId(),
                object.getValue()
        );
    }

}
