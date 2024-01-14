package com.sap.cloud.security.samples.resecurity.controllers;

import com.sap.cloud.security.samples.resecurity.commands.UnitAreaCommand;
import com.sap.cloud.security.samples.resecurity.converters.UnitAreaToUnitAreaCommand;
import com.sap.cloud.security.samples.resecurity.services.UnitAreaService;
import jakarta.validation.constraints.NotNull;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RestController
public class UnitAreaController {
    private final UnitAreaService unitAreaService;
    private final UnitAreaToUnitAreaCommand unitAreaToUnitAreaCommand;

    public UnitAreaController(UnitAreaService unitAreaService,
                              UnitAreaToUnitAreaCommand unitAreaToUnitAreaCommand) {
        this.unitAreaService = unitAreaService;
        this.unitAreaToUnitAreaCommand = unitAreaToUnitAreaCommand;
    }

    @GetMapping("/unitareas")
    Set<UnitAreaCommand> all() {
        return unitAreaService.getUnitAreaCommands();
    }

    @GetMapping("/unitareas/{unitAreaCode}")
    public Optional<UnitAreaCommand> findByIds(@PathVariable @NotNull Long unitAreaCode) {

        return Optional.ofNullable(unitAreaService.findUnitAreaCommandById(unitAreaCode));
    }

    @PostMapping("/unitareas")
    UnitAreaCommand newUnitAreaCommand(@RequestBody UnitAreaCommand newUnitAreaCommand) {

        UnitAreaCommand savedCommand = unitAreaService.saveUnitAreaCommand(newUnitAreaCommand);
        return savedCommand;

    }

    @DeleteMapping("/unitareas/{unitAreaCode}")
    void deleteUnitAreaCommand(@PathVariable Long unitAreaCode) {
        unitAreaService.deleteById(unitAreaCode);
    }

    @PutMapping
    @RequestMapping("/unitareas/{unitAreaCode}")
    @Transactional
    UnitAreaCommand updateUnitAreaCommand(@RequestBody UnitAreaCommand newUnitAreaCommand, @PathVariable Long unitAreaCode) {

        UnitAreaCommand command = unitAreaToUnitAreaCommand.convert(unitAreaService.updateUnitArea(newUnitAreaCommand, unitAreaCode));
        return command;
    }
}
