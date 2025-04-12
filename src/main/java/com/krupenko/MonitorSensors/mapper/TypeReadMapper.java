package com.krupenko.MonitorSensors.mapper;

import com.krupenko.MonitorSensors.database.entity.Type;
import com.krupenko.MonitorSensors.dto.TypeReadDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TypeReadMapper {

    TypeReadDto typeToTypeReadDto(Type type);

}
