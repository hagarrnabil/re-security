package com.sap.cloud.security.samples.resecurity.controllers;

import com.sap.cloud.security.samples.resecurity.commands.UnitSubtypeCommand;
import com.sap.cloud.security.samples.resecurity.converters.UnitSubtypeToUnitSubtypeCommand;
import com.sap.cloud.security.samples.resecurity.model.UnitSubtype;
import com.sap.cloud.security.samples.resecurity.repositories.UnitSubtypeRepository;
import com.sap.cloud.security.samples.resecurity.services.UnitSubtypeService;
import jakarta.validation.constraints.NotNull;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;


@RestController
public class USubtypeController {
    private final UnitSubtypeRepository unitSubtypeRepository;
    private final UnitSubtypeService unitSubtypeService;
    private final UnitSubtypeToUnitSubtypeCommand unitSubtypeToUnitSubtypeCommand;

    public USubtypeController(UnitSubtypeRepository unitSubtypeRepository, UnitSubtypeService unitSubtypeService,
                              UnitSubtypeToUnitSubtypeCommand unitSubtypeToUnitSubtypeCommand) {
        this.unitSubtypeRepository = unitSubtypeRepository;
        this.unitSubtypeService = unitSubtypeService;
        this.unitSubtypeToUnitSubtypeCommand = unitSubtypeToUnitSubtypeCommand;
    }

    @GetMapping("/unitsubtypes")
    Set<UnitSubtypeCommand> all() {
        return unitSubtypeService.getUnitSubtypeCommands();
    }

    @GetMapping("/unitsubtypes/{unitSubtypeCode}")
    public Optional<UnitSubtypeCommand> findByIds(@PathVariable @NotNull Long unitSubtypeCode) {

        return Optional.ofNullable(unitSubtypeService.findUnitSubtypeCommandById(unitSubtypeCode));
    }

    @PostMapping("/unitsubtypes")
    @Transactional
    UnitSubtypeCommand newUnitSubtypeCommand(@RequestBody UnitSubtype newUnitSubtype) {

        UnitSubtype unitSubtype = unitSubtypeRepository.save(newUnitSubtype);
        UnitSubtypeCommand command = unitSubtypeService.saveUnitSubtypeCommand(unitSubtypeToUnitSubtypeCommand.convert(unitSubtype));
        return command;

    }

    @DeleteMapping("/unitsubtypes/{unitSubtypeCode}")
    void deleteUnitSubtypeCommand(@PathVariable Long unitSubtypeCode) {
        unitSubtypeService.deleteById(unitSubtypeCode);
    }

    @PutMapping
    @RequestMapping("/unitsubtypes/{unitSubtypeCode}")
    @Transactional
    UnitSubtypeCommand updateUnitSubtypeCommand(@RequestBody UnitSubtypeCommand newUnitSubtypeCommand, @PathVariable Long unitSubtypeCode) {

        UnitSubtypeCommand command = unitSubtypeToUnitSubtypeCommand.convert(unitSubtypeService.updateUnitSubtype(newUnitSubtypeCommand, unitSubtypeCode));
        return command;
    }

}
