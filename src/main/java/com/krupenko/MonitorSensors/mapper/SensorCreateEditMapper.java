package com.krupenko.MonitorSensors.mapper;

import com.krupenko.MonitorSensors.database.entity.Sensor;
import com.krupenko.MonitorSensors.database.entity.Type;
import com.krupenko.MonitorSensors.database.entity.Unit;
import com.krupenko.MonitorSensors.database.repository.TypeRepository;
import com.krupenko.MonitorSensors.database.repository.UnitRepository;
import com.krupenko.MonitorSensors.dto.SensorCreateEditDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Mapper(componentModel = "spring", uses = {TypeMapper.class, UnitMapper.class})
public interface SensorCreateEditMapper {

    @Mapping(source = "typeId", target = "type")
    @Mapping(source = "unitId", target = "unit")
    @Mapping(source = "fromValue", target = "from")
    @Mapping(source = "toValue", target = "to")
    Sensor sensorCreateEditDtoToSensor(SensorCreateEditDto fromObject, @MappingTarget Sensor toObject);

    @Mapping(source = "typeId", target = "type")
    @Mapping(source = "unitId", target = "unit")
    @Mapping(source = "fromValue", target = "from")
    @Mapping(source = "toValue", target = "to")
    Sensor sensorCreateEditDtoToSensor(SensorCreateEditDto object);

}

@Mapper(componentModel = "spring")
abstract class TypeMapper {

    @Autowired
    protected TypeRepository typeRepository;

    protected Type typeIdToType(Integer typeId) {
        return Optional.ofNullable(typeId)
                .flatMap(typeRepository::findById)
                .orElseThrow(IllegalArgumentException::new);
    }

}

@Mapper(componentModel = "spring")
abstract class UnitMapper {

    @Autowired
    protected UnitRepository unitRepository;

    protected Unit unitIdToUnit(Integer unitId) {
        return Optional.ofNullable(unitId)
                .flatMap(unitRepository::findById)
                .orElse(null);
    }

}
