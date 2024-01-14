package com.sap.cloud.security.samples.resecurity.controllers;

import com.sap.cloud.security.samples.resecurity.commands.CompanyCommand;
import com.sap.cloud.security.samples.resecurity.converters.CompanyToCompanyCommand;
import com.sap.cloud.security.samples.resecurity.repositories.CompanyRepository;
import com.sap.cloud.security.samples.resecurity.services.CompanyService;
import jakarta.validation.constraints.NotNull;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;


@RestController
public class CompanyController {
    private final CompanyRepository companyRepository;
    private final CompanyToCompanyCommand companyToCompanyCommand;
    private final CompanyService companyService;

    public CompanyController(CompanyRepository companyRepository,
                             CompanyToCompanyCommand companyToCompanyCommand,
                             CompanyService companyService)
    {
        this.companyRepository = companyRepository;
        this.companyToCompanyCommand = companyToCompanyCommand;
        this.companyService = companyService;
    }

    @GetMapping("/companies")
    Set<CompanyCommand> all() {
        return companyService.getCompanyCommands();
    }

    @GetMapping("/companies/{companyCode}")
    public Optional<CompanyCommand> findByIds(@PathVariable @NotNull Long companyCode) {

        return Optional.ofNullable(companyService.findCompanyCommandById(companyCode));
    }

    @PostMapping("/companies")
    CompanyCommand newCompanyCommand(@RequestBody CompanyCommand newCompanyCommand) {

        CompanyCommand savedCommand = companyService.saveCompanyCommand(newCompanyCommand);
        return savedCommand;

    }

    @DeleteMapping("/companies/{companyCode}")
    void deleteCompanyCommand(@PathVariable Long companyCode) {
        companyService.deleteById(companyCode);
    }

    @PutMapping
    @RequestMapping("/companies/{companyCode}")
    @Transactional
    CompanyCommand updateCompany(@RequestBody CompanyCommand newCompanyCommand, @PathVariable Long companyCode) {

        CompanyCommand command = companyToCompanyCommand.convert(companyService.updateCompany(newCompanyCommand, companyCode));
        return command;
    }
}