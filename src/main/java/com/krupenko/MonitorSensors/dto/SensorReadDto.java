package com.krupenko.MonitorSensors.dto;

import lombok.Value;

@Value
public class SensorReadDto {
    Integer id;
    String name;
    String model;
    RangeReadDto range;
    TypeReadDto type;
    UnitReadDto unit;
    String location;
    String description;
}
