package com.sap.cloud.security.samples.resecurity.controllers;

import com.sap.cloud.security.samples.resecurity.commands.UnitCommand;
import com.sap.cloud.security.samples.resecurity.converters.UnitToUnitCommand;
import com.sap.cloud.security.samples.resecurity.model.Unit;
import com.sap.cloud.security.samples.resecurity.repositories.UnitRepository;
import com.sap.cloud.security.samples.resecurity.services.UnitService;
import jakarta.validation.constraints.NotNull;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;


@RestController
public class UnitController {
    private final UnitRepository unitRepository;

    private final UnitService unitService;
    private final UnitToUnitCommand unitToUnitCommand;

    public UnitController(UnitRepository unitRepository, UnitService unitService,
                          UnitToUnitCommand unitToUnitCommand) {
        this.unitRepository = unitRepository;
        this.unitService = unitService;
        this.unitToUnitCommand = unitToUnitCommand;
    }

    @GetMapping("/units")
    Set<UnitCommand> all() {
        return unitService.getUnitCommands();
    }

    @GetMapping("/units/{unitCode}")
    public Optional<UnitCommand> findByIds(@PathVariable @NotNull Long unitCode) {

        return Optional.ofNullable(unitService.findUnitCommandById(unitCode));
    }

    @PostMapping("/units")
    UnitCommand newUnitCommand(@RequestBody UnitCommand newUnitCommand) {

        UnitCommand savedCommand = unitService.saveUnitCommand(newUnitCommand);
        return savedCommand;

    }

    @DeleteMapping("/units/{unitCode}")
    void deleteUnitCommand(@PathVariable Long unitCode) {
        unitService.deleteById(unitCode);
    }

    @PutMapping
    @RequestMapping("/units/{unitCode}")
    @Transactional
    UnitCommand updateUnitCommand(@RequestBody Unit newUnit, @PathVariable Long unitCode) {

        UnitCommand command = unitToUnitCommand.convert(unitService.updateUnit(newUnit, unitCode));
        return command;
    }
}