package com.sap.cloud.security.samples.resecurity.controllers;

import com.sap.cloud.security.samples.resecurity.commands.UnitOfMeasurementCommand;
import com.sap.cloud.security.samples.resecurity.converters.UnitOfMeasurementToUnitOfMeasurementCommand;
import com.sap.cloud.security.samples.resecurity.model.UnitOfMeasurement;
import com.sap.cloud.security.samples.resecurity.repositories.UnitOfMeasurementRepository;
import com.sap.cloud.security.samples.resecurity.services.MeasurementService;
import jakarta.validation.constraints.NotNull;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;


@RestController
public class UnitOfMeasurementController {
    private final UnitOfMeasurementRepository unitOfMeasurementRepository;
    private final MeasurementService measurementService;
    private final UnitOfMeasurementToUnitOfMeasurementCommand unitOfMeasurementToUnitOfMeasurementCommand;

    public UnitOfMeasurementController(UnitOfMeasurementRepository unitOfMeasurementRepository, MeasurementService measurementService,
                                       UnitOfMeasurementToUnitOfMeasurementCommand unitOfMeasurementToUnitOfMeasurementCommand) {
        this.unitOfMeasurementRepository = unitOfMeasurementRepository;
        this.measurementService = measurementService;
        this.unitOfMeasurementToUnitOfMeasurementCommand = unitOfMeasurementToUnitOfMeasurementCommand;
    }

    @GetMapping("/measurements")
    Set<UnitOfMeasurementCommand> all() {
        return measurementService.getUOMCommands();
    }

    @GetMapping("/measurements/{measurementCode}")
    public Optional<UnitOfMeasurementCommand> findByIds(@PathVariable @NotNull Long measurementCode) {

        return Optional.ofNullable(measurementService.findUOMCommandById(measurementCode));
    }

    @PostMapping("/measurements")
    @Transactional
    UnitOfMeasurementCommand newUOMCommand(@RequestBody UnitOfMeasurement newUOM) {

        UnitOfMeasurement measurement = unitOfMeasurementRepository.save(newUOM);
        UnitOfMeasurementCommand command = measurementService.saveUOMCommand(unitOfMeasurementToUnitOfMeasurementCommand.convert(measurement));
        return command;

    }

    @DeleteMapping("/measurements/{measurementCode}")
    void deleteUOMCommand(@PathVariable Long measurementCode) {
        measurementService.deleteById(measurementCode);
    }

    @PutMapping
    @RequestMapping("/measurements/{measurementCode}")
    @Transactional
    UnitOfMeasurementCommand updateUOMCommand(@RequestBody UnitOfMeasurementCommand newUOMCommand, @PathVariable Long measurementCode) {

        UnitOfMeasurementCommand command = unitOfMeasurementToUnitOfMeasurementCommand.convert(measurementService.updateUOM(newUOMCommand, measurementCode));
        return command;
    }
}