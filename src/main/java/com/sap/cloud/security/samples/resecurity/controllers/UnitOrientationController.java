package com.sap.cloud.security.samples.resecurity.controllers;

import com.sap.cloud.security.samples.resecurity.commands.UnitOrientationCommand;
import com.sap.cloud.security.samples.resecurity.converters.UnitOrientationToUnitOrientationCommand;
import com.sap.cloud.security.samples.resecurity.model.UnitOrientation;
import com.sap.cloud.security.samples.resecurity.repositories.UnitOrientationRepository;
import com.sap.cloud.security.samples.resecurity.services.UnitOrientationService;
import jakarta.validation.constraints.NotNull;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;


@RestController
public class UnitOrientationController {
    private final UnitOrientationRepository unitOrientationRepository;
    private final UnitOrientationService unitOrientationService;
    private final UnitOrientationToUnitOrientationCommand unitOrientationToUnitOrientationCommand;

    public UnitOrientationController(UnitOrientationRepository unitOrientationRepository, UnitOrientationService unitOrientationService,
                                     UnitOrientationToUnitOrientationCommand unitOrientationToUnitOrientationCommand) {
        this.unitOrientationRepository = unitOrientationRepository;
        this.unitOrientationService = unitOrientationService;
        this.unitOrientationToUnitOrientationCommand = unitOrientationToUnitOrientationCommand;
    }

    @GetMapping("/unitorientations")
    Set<UnitOrientationCommand> all() {
        return unitOrientationService.getUnitOrientationCommands();
    }

    @GetMapping("/unitorientations/{unitOrientationCode}")
    public Optional<UnitOrientationCommand> findByIds(@PathVariable @NotNull Long unitOrientationCode) {

        return Optional.ofNullable(unitOrientationService.findUnitOrientationCommandById(unitOrientationCode));
    }

    @PostMapping("/unitorientations")
    @Transactional
    UnitOrientationCommand newUnitOrientationCommand(@RequestBody UnitOrientation newUnitOrientation) {

        UnitOrientation unitOrientation = unitOrientationRepository.save(newUnitOrientation);
        UnitOrientationCommand command = unitOrientationService.saveUnitOrientationCommand(unitOrientationToUnitOrientationCommand.convert(unitOrientation));
        return command;

    }

    @DeleteMapping("/unitorientations/{unitOrientationCode}")
    void deleteUnitOrientationCommand(@PathVariable Long companyCode) {
        unitOrientationService.deleteById(companyCode);
    }

    @PutMapping
    @RequestMapping("/unitorientations/{unitOrientationCode}")
    @Transactional
    UnitOrientationCommand updateUnitOrientationCommand(@RequestBody UnitOrientationCommand newUnitOrientationCommand, @PathVariable Long unitOrientationCode) {

        UnitOrientationCommand command = unitOrientationToUnitOrientationCommand.convert(unitOrientationService.updateUnitOrientation(newUnitOrientationCommand, unitOrientationCode));
        return command;
    }

}
