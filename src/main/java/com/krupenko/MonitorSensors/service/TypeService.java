package com.krupenko.MonitorSensors.service;

import com.krupenko.MonitorSensors.database.repository.TypeRepository;
import com.krupenko.MonitorSensors.dto.TypeReadDto;
import com.krupenko.MonitorSensors.mapper.TypeReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TypeService {

    private final TypeRepository typeRepository;
    private final TypeReadMapper typeReadMapper;

    public List<TypeReadDto> findAll() {
        return typeRepository.findAll().stream().map(typeReadMapper::typeToTypeReadDto).toList();
    }

    public Optional<TypeReadDto> findById(Integer id) {
        return typeRepository.findById(id).map(typeReadMapper::typeToTypeReadDto);
    }

}
