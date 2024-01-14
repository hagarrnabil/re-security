package com.sap.cloud.security.samples.resecurity.controllers;

import com.sap.cloud.security.samples.resecurity.commands.UnitFixtureCommand;
import com.sap.cloud.security.samples.resecurity.converters.UnitFixtureToUnitFixtureCommand;
import com.sap.cloud.security.samples.resecurity.model.UnitFixture;
import com.sap.cloud.security.samples.resecurity.repositories.UnitFixtureRepository;
import com.sap.cloud.security.samples.resecurity.services.UnitFixtureService;
import jakarta.validation.constraints.NotNull;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RestController
public class UnitFixtureController {
    private final UnitFixtureRepository unitFixtureRepository;
    private final UnitFixtureService unitFixtureService;
    private final UnitFixtureToUnitFixtureCommand unitFixtureToUnitFixtureCommand;

    public UnitFixtureController(UnitFixtureRepository unitFixtureRepository, UnitFixtureService unitFixtureService,
                                 UnitFixtureToUnitFixtureCommand unitFixtureToUnitFixtureCommand) {
        this.unitFixtureRepository = unitFixtureRepository;
        this.unitFixtureService = unitFixtureService;
        this.unitFixtureToUnitFixtureCommand = unitFixtureToUnitFixtureCommand;
    }

    @GetMapping("/unitfixture")
    Set<UnitFixtureCommand> all() {
        return unitFixtureService.getUnitFixtureCommands();
    }

    @GetMapping("/unitfixture/{unitFixtureCode}")
    public Optional<UnitFixtureCommand> findByIds(@PathVariable @NotNull Long unitFixtureCode) {

        return Optional.ofNullable(unitFixtureService.findUnitFixtureCommandById(unitFixtureCode));
    }

    @PostMapping("/unitfixture")
    UnitFixtureCommand newUnitFixtureCommand(@RequestBody UnitFixture unitFixture) {

        UnitFixture unitFixture1 = unitFixtureRepository.save(unitFixture);
        UnitFixtureCommand command = unitFixtureService.saveUnitFixtureCommand(unitFixtureToUnitFixtureCommand.convert(unitFixture1));
        return command;
    }

    @DeleteMapping("/unitfixture/{unitFixtureCode}")
    void deleteUnitFixtureCommand(@PathVariable Long unitFixtureCode) {
        unitFixtureService.deleteById(unitFixtureCode);
    }

    @PutMapping
    @RequestMapping("/unitfixture/{unitFixtureCode}")
    @Transactional
    UnitFixtureCommand updateUnitFixtureCommand(@RequestBody UnitFixtureCommand newUnitFixtureCmmand, @PathVariable Long unitFixtureCode) {

        UnitFixtureCommand command = unitFixtureToUnitFixtureCommand.convert(unitFixtureService.updateUnitFixture(newUnitFixtureCmmand, unitFixtureCode));
        return command;
    }

}
