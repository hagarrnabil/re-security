package com.sap.cloud.security.samples.resecurity.controllers;

import com.sap.cloud.security.samples.resecurity.commands.UnitFloorCommand;
import com.sap.cloud.security.samples.resecurity.converters.UnitFloorToUnitFloorCommand;
import com.sap.cloud.security.samples.resecurity.model.UnitFloor;
import com.sap.cloud.security.samples.resecurity.repositories.UnitFloorRepository;
import com.sap.cloud.security.samples.resecurity.services.UnitFloorService;
import jakarta.validation.constraints.NotNull;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;


@RestController
public class UnitFloorController {
    private final UnitFloorRepository unitFloorRepository;
    private final UnitFloorService unitFloorService;
    private final UnitFloorToUnitFloorCommand unitFloorToUnitFloorCommand;

    public UnitFloorController(UnitFloorRepository unitFloorRepository, UnitFloorService unitFloorService,
                               UnitFloorToUnitFloorCommand unitFloorToUnitFloorCommand) {
        this.unitFloorRepository = unitFloorRepository;
        this.unitFloorService = unitFloorService;
        this.unitFloorToUnitFloorCommand = unitFloorToUnitFloorCommand;
    }

    @GetMapping("/unitfloors")
    Set<UnitFloorCommand> all() {
        return unitFloorService.getUnitFloorCommands();
    }

    @GetMapping("/unitfloors/{unitFloorCode}")
    public Optional<UnitFloorCommand> findByIds(@PathVariable @NotNull Long unitFloorCode) {

        return Optional.ofNullable(unitFloorService.findUnitFloorCommandById(unitFloorCode));
    }

    @PostMapping("/unitfloors")
    @Transactional
    UnitFloorCommand newUnitFloorCommand(@RequestBody UnitFloor newUnitFloor) {

        UnitFloor unitFloor = unitFloorRepository.save(newUnitFloor);
        UnitFloorCommand command = unitFloorService.saveUnitFloorCommand(unitFloorToUnitFloorCommand.convert(unitFloor));
        return command;

    }

    @DeleteMapping("/unitfloors/{unitFloorCode}")
    void deleteUnitFloorCommand(@PathVariable Long unitFloorCode) {
        unitFloorService.deleteById(unitFloorCode);
    }

    @PutMapping
    @RequestMapping("/unitfloors/{unitFloorCode}")
    @Transactional
    UnitFloorCommand updateUnitFloorCommand(@RequestBody UnitFloorCommand newUnitFloorCommand, @PathVariable Long unitFloorCode) {

        UnitFloorCommand command = unitFloorToUnitFloorCommand.convert(unitFloorService.updateUnitFloor(newUnitFloorCommand, unitFloorCode));
        return command;
    }

}
