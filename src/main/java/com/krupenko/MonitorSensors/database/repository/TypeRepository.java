package com.krupenko.MonitorSensors.database.repository;

import com.krupenko.MonitorSensors.database.entity.Type;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeRepository extends JpaRepository<Type, Integer> {
}
