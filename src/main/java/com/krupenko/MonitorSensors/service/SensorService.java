package com.krupenko.MonitorSensors.service;

import com.krupenko.MonitorSensors.database.repository.SensorRepository;
import com.krupenko.MonitorSensors.dto.SensorCreateEditDto;
import com.krupenko.MonitorSensors.dto.SensorFilter;
import com.krupenko.MonitorSensors.dto.SensorReadDto;
import com.krupenko.MonitorSensors.mapper.SensorCreateEditMapper;
import com.krupenko.MonitorSensors.mapper.SensorReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SensorService {

    private final SensorRepository sensorRepository;
    private final SensorReadMapper sensorReadMapper;
    private final SensorCreateEditMapper sensorCreateEditMapper;

    public List<SensorReadDto> findAll(SensorFilter sensorFilter) {
        return sensorRepository.findAllByFilter(sensorFilter).stream()
                .map(sensorReadMapper::sensorToSensorReadDto)
                .toList();
    }

    public Optional<SensorReadDto> findById(Integer id) {
        return sensorRepository.findById(id).map(sensorReadMapper::sensorToSensorReadDto);
    }

    @Transactional
    public SensorReadDto create(SensorCreateEditDto sensorDto) {
        return Optional.of(sensorDto)
                .map(sensorCreateEditMapper::sensorCreateEditDtoToSensor)
                .map(sensorRepository::save)
                .map(sensorReadMapper::sensorToSensorReadDto)
                .orElseThrow();
    }

    @Transactional
    public Optional<SensorReadDto> update(Integer id, SensorCreateEditDto sensorDto) {
        return sensorRepository.findById(id)
                .map(entity -> sensorCreateEditMapper.sensorCreateEditDtoToSensor(sensorDto, entity))
                .map(sensorRepository::saveAndFlush)
                .map(sensorReadMapper::sensorToSensorReadDto);
    }

    @Transactional
    public boolean delete(Integer id) {
        return sensorRepository.findById(id)
                .map(entity -> {
                    sensorRepository.delete(entity);
                    sensorRepository.flush();
                    return true;
                }).orElse(false);
    }

}
