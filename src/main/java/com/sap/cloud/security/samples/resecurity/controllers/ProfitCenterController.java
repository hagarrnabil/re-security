package com.sap.cloud.security.samples.resecurity.controllers;

import com.sap.cloud.security.samples.resecurity.commands.ProfitCenterCommand;
import com.sap.cloud.security.samples.resecurity.converters.ProfitToProfitCommand;
import com.sap.cloud.security.samples.resecurity.repositories.ProfitCenterRepository;
import com.sap.cloud.security.samples.resecurity.services.ProfitService;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasRole('ROLE_USER')")
//    @PreAuthorize("hasAuthority('Read')")
    Set<ProfitCenterCommand> all() {
        return profitService.getProfitCommands();
    }

    @GetMapping("/profits/{profitCode}")
    @PreAuthorize("hasAuthority('Read')")
    public Optional<ProfitCenterCommand> findByIds(@PathVariable @NotNull Long profitCode) {

        return Optional.ofNullable(profitService.findProfitCommandById(profitCode));
    }

    @PostMapping("/profits")
    @PreAuthorize("hasAuthority('Read')")
    ProfitCenterCommand newProfitCommand(@RequestBody ProfitCenterCommand newProfitCommand) {

        ProfitCenterCommand savedCommand = profitService.saveProfitCommand(newProfitCommand);
        return savedCommand;

    }

    @DeleteMapping("/profits/{profitCode}")
    @PreAuthorize("hasAuthority('Read')")
    void deleteProfitCommand(@PathVariable Long profitCode) {
        profitService.deleteById(profitCode);
    }

    @PutMapping
    @RequestMapping("/profits/{profitCode}")
    @PreAuthorize("hasAuthority('Read')")
    @Transactional
    ProfitCenterCommand updateProfitCommand(@RequestBody ProfitCenterCommand newProfitCommand, @PathVariable Long profitCode) {

        ProfitCenterCommand command = profitToProfitCommand.convert(profitService.updateProfit(newProfitCommand, profitCode));
        return command;
    }
}
