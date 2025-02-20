package com.krupenko.MonitorSensors.http.rest;

import com.krupenko.MonitorSensors.dto.SensorCreateEditDto;
import com.krupenko.MonitorSensors.dto.SensorFilter;
import com.krupenko.MonitorSensors.dto.SensorReadDto;
import com.krupenko.MonitorSensors.service.SensorService;
import com.krupenko.MonitorSensors.service.TypeService;
import com.krupenko.MonitorSensors.service.UnitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/sensors")
@RequiredArgsConstructor
public class SensorRestController {

    private final SensorService sensorService;
    private final TypeService typeService;
    private final UnitService unitService;

    @GetMapping
    public List<SensorReadDto> findAll(@RequestParam(required = false) String name,
                                       @RequestParam(required = false) String model) {
        return sensorService.findAll(new SensorFilter(name, model));
    }

    @GetMapping("/{id}")
    public SensorReadDto findById(@PathVariable("id") Integer id) {
        return sensorService.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Validated SensorCreateEditDto sensor, BindingResult bindingResult) {
        List<String> errors = getBindingResultErrors(bindingResult);
        validateTypeId(sensor.getTypeId(), errors);
        validateUnitId(sensor.getUnitId(), errors);
        if (!errors.isEmpty()) {
            return getBadRequestResponseWithReasons(errors);
        }
        Integer id = sensorService.create(sensor).getId();
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody @Validated SensorCreateEditDto sensor,
                                    BindingResult bindingResult) {
        List<String> errors = getBindingResultErrors(bindingResult);
        validateTypeId(sensor.getTypeId(), errors);
        validateUnitId(sensor.getUnitId(), errors);
        if (!errors.isEmpty()) {
            return getBadRequestResponseWithReasons(errors);
        }
        return sensorService.update(id, sensor)
                .map(updatedSensor -> new ResponseEntity<>(updatedSensor, HttpStatus.OK))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        boolean success = sensorService.delete(id);
        return new ResponseEntity<>(success ? HttpStatus.NO_CONTENT : HttpStatus.NOT_FOUND);
    }

    private ArrayList<String> getBindingResultErrors(BindingResult bindingResult) {
        return new ArrayList<>(bindingResult.getAllErrors().stream().map(ObjectError::getDefaultMessage).toList());
    }

    private void validateTypeId(Integer typeId, List<String> errors) {
        if (typeId != null && typeService.findById(typeId).isEmpty()) {
            errors.add("Type не найден");
        }
    }

    private void validateUnitId(Integer unitId, List<String> errors) {
        if (unitId != null && unitService.findById(unitId).isEmpty()) {
            errors.add("Unit не найден");
        }
    }

    private ResponseEntity<String> getBadRequestResponseWithReasons(List<String> errorMessages) {
        StringBuilder reason = new StringBuilder();
        for (String errorMessage : errorMessages) {
            reason.append(errorMessage).append("\n");
        }
        return new ResponseEntity<>(reason.toString(), HttpStatus.BAD_REQUEST);
    }

}
