package com.sap.cloud.security.samples.resecurity.controllers;

import com.sap.cloud.security.samples.resecurity.commands.ProfitCenterCommand;
import com.sap.cloud.security.samples.resecurity.converters.ProfitToProfitCommand;
import com.sap.cloud.security.samples.resecurity.repositories.ProfitCenterRepository;
import com.sap.cloud.security.samples.resecurity.services.ProfitService;
import jakarta.validation.constraints.NotNull;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;


@RestController
public class ProfitCenterController {
    private final ProfitCenterRepository profitCenterRepository;
    private final ProfitService profitService;
    private final ProfitToProfitCommand profitToProfitCommand;

    public ProfitCenterController(ProfitCenterRepository profitCenterRepository, ProfitService profitService,
                                  ProfitToProfitCommand profitToProfitCommand) {
        this.profitCenterRepository = profitCenterRepository;
        this.profitService = profitService;
        this.profitToProfitCommand = profitToProfitCommand;
    }

    @GetMapping("/profits")
    Set<ProfitCenterCommand> all() {
        return profitService.getProfitCommands();
    }

    @GetMapping("/profits/{profitCode}")
    public Optional<ProfitCenterCommand> findByIds(@PathVariable @NotNull Long profitCode) {

        return Optional.ofNullable(profitService.findProfitCommandById(profitCode));
    }

    @PostMapping("/profits")
    ProfitCenterCommand newProfitCommand(@RequestBody ProfitCenterCommand newProfitCommand) {

        ProfitCenterCommand savedCommand = profitService.saveProfitCommand(newProfitCommand);
        return savedCommand;

    }

    @DeleteMapping("/profits/{profitCode}")
    void deleteProfitCommand(@PathVariable Long profitCode) {
        profitService.deleteById(profitCode);
    }

    @PutMapping
    @RequestMapping("/profits/{profitCode}")
    @Transactional
    ProfitCenterCommand updateProfitCommand(@RequestBody ProfitCenterCommand newProfitCommand, @PathVariable Long profitCode) {

        ProfitCenterCommand command = profitToProfitCommand.convert(profitService.updateProfit(newProfitCommand, profitCode));
        return command;
    }
}
