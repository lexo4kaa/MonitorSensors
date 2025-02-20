package com.krupenko.MonitorSensors.dto;

public interface HasRange<T extends Number> {

    T getFromValue();

    T getToValue();

}
