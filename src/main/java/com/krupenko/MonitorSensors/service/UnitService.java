package com.krupenko.MonitorSensors.service;

import com.krupenko.MonitorSensors.database.repository.UnitRepository;
import com.krupenko.MonitorSensors.dto.UnitReadDto;
import com.krupenko.MonitorSensors.mapper.UnitReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UnitService {

    private final UnitRepository unitRepository;
    private final UnitReadMapper unitReadMapper;

    public List<UnitReadDto> findAll() {
        return unitRepository.findAll().stream().map(unitReadMapper::map).toList();
    }

    public Optional<UnitReadDto> findById(Integer id) {
        return unitRepository.findById(id).map(unitReadMapper::map);
    }

}
