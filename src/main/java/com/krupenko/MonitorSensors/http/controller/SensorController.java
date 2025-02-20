package com.krupenko.MonitorSensors.http.controller;

import com.krupenko.MonitorSensors.dto.SensorCreateEditDto;
import com.krupenko.MonitorSensors.dto.SensorFilter;
import com.krupenko.MonitorSensors.service.SensorService;
import com.krupenko.MonitorSensors.service.TypeService;
import com.krupenko.MonitorSensors.service.UnitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/sensors")
@RequiredArgsConstructor
public class SensorController {

    private final SensorService sensorService;
    private final TypeService typeService;
    private final UnitService unitService;

    @GetMapping
    public String findAll(Model model, SensorFilter sensorFilter) {
        model.addAttribute("sensors", sensorService.findAll(sensorFilter));
        model.addAttribute("filter", sensorFilter);
        return "sensor/sensors";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Integer id, Model model) {
        return sensorService.findById(id)
                .map(sensor -> {
                    model.addAttribute("sensor", sensor);
                    model.addAttribute("types", typeService.findAll());
                    model.addAttribute("units", unitService.findAll());
                    return "sensor/sensor";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/create")
    public String create(@ModelAttribute("sensor") SensorCreateEditDto sensor, Model model) {
        model.addAttribute("sensor", sensor);
        model.addAttribute("types", typeService.findAll());
        model.addAttribute("units", unitService.findAll());
        return "sensor/create";
    }

    @PostMapping
    public String create(@ModelAttribute @Validated SensorCreateEditDto sensor, BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("sensor", sensor);
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/sensors/create";
        }
        return "redirect:/sensors/" + sensorService.create(sensor).getId();
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable Integer id, @ModelAttribute @Validated SensorCreateEditDto sensor,
                         BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/sensors/{id}";
        }
        return sensorService.update(id, sensor)
                .map(s -> "redirect:/sensors/{id}")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Integer id) {
        if (!sensorService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/sensors";
    }

}
