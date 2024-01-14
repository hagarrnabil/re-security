package com.sap.cloud.security.samples.resecurity.controllers;

import com.sap.cloud.security.samples.resecurity.commands.UnitStatusCommand;
import com.sap.cloud.security.samples.resecurity.converters.UnitStatusToUnitStatusCommand;
import com.sap.cloud.security.samples.resecurity.model.UnitStatus;
import com.sap.cloud.security.samples.resecurity.repositories.UnitStatusRepository;
import com.sap.cloud.security.samples.resecurity.services.UnitStatusService;
import jakarta.validation.constraints.NotNull;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;


@RestController
public class UnitStatusController {
    private final UnitStatusRepository unitStatusRepository;
    private final UnitStatusService unitStatusService;
    private final UnitStatusToUnitStatusCommand unitStatusToUnitStatusCommand;

    public UnitStatusController(UnitStatusRepository unitStatusRepository, UnitStatusService unitStatusService,
                                UnitStatusToUnitStatusCommand unitStatusToUnitStatusCommand) {
        this.unitStatusRepository = unitStatusRepository;
        this.unitStatusService = unitStatusService;
        this.unitStatusToUnitStatusCommand = unitStatusToUnitStatusCommand;
    }

    @GetMapping("/unitstatuses")
    Set<UnitStatusCommand> all() {
        return unitStatusService.getUnitStatusCommands();
    }

    @GetMapping("/unitstatuses/{unitStatusCode}")
    public Optional<UnitStatusCommand> findByIds(@PathVariable @NotNull Long unitStatusCode) {

        return Optional.ofNullable(unitStatusService.findUnitStatusCommandById(unitStatusCode));
    }

    @PostMapping("/unitstatuses")
    @Transactional
    UnitStatusCommand newUnitStatusCommand(@RequestBody UnitStatus newUnitStatus) {

        UnitStatus unitStatus = unitStatusRepository.save(newUnitStatus);
        UnitStatusCommand command = unitStatusService.saveUnitStatusCommand(unitStatusToUnitStatusCommand.convert(unitStatus));
        return command;

    }

    @DeleteMapping("/unitstatuses/{unitStatusCode}")
    void deleteUnitStatusCommand(@PathVariable Long unitStatusCode) {
        unitStatusService.deleteById(unitStatusCode);
    }

    @PutMapping
    @RequestMapping("/unitstatuses/{unitStatusCode}")
    @Transactional
    UnitStatusCommand updateUnitStatusCommand(@RequestBody UnitStatusCommand newUnitStatusCommand, @PathVariable Long unitStatusCode) {

        UnitStatusCommand command = unitStatusToUnitStatusCommand.convert(unitStatusService.updateUnitStatus(newUnitStatusCommand, unitStatusCode));
        return command;
    }

}
