package com.sap.cloud.security.samples.resecurity.controllers;

import com.sap.cloud.security.samples.resecurity.commands.UnitViewCommand;
import com.sap.cloud.security.samples.resecurity.converters.UnitViewToUnitViewCommand;
import com.sap.cloud.security.samples.resecurity.model.UnitView;
import com.sap.cloud.security.samples.resecurity.repositories.UnitViewRepository;
import com.sap.cloud.security.samples.resecurity.services.UnitViewService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RestController
public class UnitViewController {
    private final UnitViewRepository unitViewRepository;
    private final UnitViewService unitViewService;
    private final UnitViewToUnitViewCommand unitViewToUnitViewCommand;

    public UnitViewController(UnitViewRepository unitViewRepository, UnitViewService unitViewService,
                              UnitViewToUnitViewCommand unitViewToUnitViewCommand) {
        this.unitViewRepository = unitViewRepository;
        this.unitViewService = unitViewService;
        this.unitViewToUnitViewCommand = unitViewToUnitViewCommand;
    }

    @GetMapping("/unitviews")
    Set<UnitViewCommand> all() {
        return unitViewService.getUnitViewCommands();
    }

    @GetMapping("/unitviews/{unitViewCode}")
    public Optional<UnitViewCommand> findByIds(@PathVariable @NotNull Long unitViewCode) {

        return Optional.ofNullable(unitViewService.findUnitViewCommandById(unitViewCode));
    }

    @PostMapping("/unitviews")
    @Transactional
    UnitViewCommand newUnitViewCommand(@Valid  @RequestBody UnitView newUnitView) {

        UnitView unitView = unitViewRepository.save(newUnitView);
        UnitViewCommand command = unitViewService.saveUnitViewCommand(unitViewToUnitViewCommand.convert(unitView));
        return command;

    }

    @DeleteMapping("/unitviews/{unitViewCode}")
    void deleteUnitViewCommand(@PathVariable Long unitViewCode) {
        unitViewService.deleteById(unitViewCode);
    }

    @PutMapping
    @RequestMapping("/unitviews/{unitViewCode}")
    @Transactional
    UnitViewCommand updateUnitViewCommand(@RequestBody UnitViewCommand newUnitViewCommand, @PathVariable Long unitViewCode) {

        UnitViewCommand command = unitViewToUnitViewCommand.convert(unitViewService.updateUnitView(newUnitViewCommand, unitViewCode));
        return command;
    }
}
