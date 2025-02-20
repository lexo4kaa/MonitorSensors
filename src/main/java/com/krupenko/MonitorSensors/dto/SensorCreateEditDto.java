package com.krupenko.MonitorSensors.dto;

import com.krupenko.MonitorSensors.validation.Range;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

@Value
@Range
public class SensorCreateEditDto implements HasRange<Integer> {

    @NotBlank(message = "name {jakarta.validation.constraints.NotBlank.message}")
    @Size(min = 3, max = 30, message = "name {jakarta.validation.constraints.Size.message}")
    String name;

    @NotBlank(message = "model {jakarta.validation.constraints.NotBlank.message}")
    @Size(max = 15, message = "model {jakarta.validation.constraints.Size.message}")
    String model;

    @Min(value = 0, message = "fromValue {jakarta.validation.constraints.Min.message}")
    @Max(value = Integer.MAX_VALUE, message = "fromValue {jakarta.validation.constraints.Min.message}")
    @NotNull(message = "fromValue {jakarta.validation.constraints.NotNull.message}")
    Integer fromValue;

    @Min(value = 0, message = "toValue {jakarta.validation.constraints.Min.message}")
    @Max(value = Integer.MAX_VALUE, message = "toValue {jakarta.validation.constraints.Min.message}")
    @NotNull(message = "toValue {jakarta.validation.constraints.NotNull.message}")
    Integer toValue;

    @NotNull(message = "typeId {jakarta.validation.constraints.NotNull.message}")
    Integer typeId;

    Integer unitId;

    @Size(max = 40, message = "location {jakarta.validation.constraints.Size.message}")
    String location;

    @Size(max = 200, message = "description {jakarta.validation.constraints.Size.message}")
    String description;

}
