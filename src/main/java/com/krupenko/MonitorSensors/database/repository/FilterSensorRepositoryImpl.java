package com.krupenko.MonitorSensors.database.repository;

import com.krupenko.MonitorSensors.database.entity.Sensor;
import com.krupenko.MonitorSensors.dto.SensorFilter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class FilterSensorRepositoryImpl implements FilterSensorRepository {

    private final EntityManager entityManager;

    @Override
    public List<Sensor> findAllByFilter(SensorFilter filter) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Sensor> criteria = criteriaBuilder.createQuery(Sensor.class);

        Root<Sensor> sensor = criteria.from(Sensor.class);
        criteria.select(sensor);

        List<Predicate> predicates = new ArrayList<>();
        if (StringUtils.hasText(filter.name())) {
            predicates.add(criteriaBuilder.like(sensor.get("name"), wrapInPercentages(filter.name())));
        }
        if (StringUtils.hasText(filter.model())) {
            predicates.add(criteriaBuilder.like(sensor.get("model"), wrapInPercentages(filter.model())));
        }
        criteria.where(predicates.toArray(Predicate[]::new));

        return entityManager.createQuery(criteria).getResultList();
    }

    private String wrapInPercentages(String name) {
        return "%" + name + "%";
    }

}
