package com.sap.cloud.security.samples.resecurity.controllers;

import com.sap.cloud.security.samples.resecurity.commands.CompanyCommand;
import com.sap.cloud.security.samples.resecurity.converters.CompanyToCompanyCommand;
import com.sap.cloud.security.samples.resecurity.repositories.CompanyRepository;
import com.sap.cloud.security.samples.resecurity.services.CompanyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;


@RestController
public class CompanyController {

    private static final Logger logger = LoggerFactory.getLogger(CompanyController.class);
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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    @PreAuthorize("hasAuthority('Admin')")
    Set<CompanyCommand> all() {
        return companyService.getCompanyCommands();
    }


    @GetMapping("/companies/{companyCode}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    @PreAuthorize("hasAuthority('Admin')")
    public Optional<CompanyCommand> findByIds(@PathVariable @NotNull Long companyCode) {

        return Optional.ofNullable(companyService.findCompanyCommandById(companyCode));
    }


    @PostMapping("/companies")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    @PreAuthorize("hasAuthority('Admin')")
    CompanyCommand newCompanyCommand(@RequestBody CompanyCommand newCompanyCommand) {

        CompanyCommand savedCommand = companyService.saveCompanyCommand(newCompanyCommand);
        return savedCommand;

    }


    @DeleteMapping("/companies/{companyCode}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    @PreAuthorize("hasAuthority('Admin')")
    void deleteCompanyCommand(@PathVariable Long companyCode) {
        companyService.deleteById(companyCode);
    }


    @PutMapping
    @RequestMapping("/companies/{companyCode}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    @PreAuthorize("hasAuthority('Admin')")
    @Transactional
    CompanyCommand updateCompany(@RequestBody CompanyCommand newCompanyCommand, @PathVariable Long companyCode) {

        CompanyCommand command = companyToCompanyCommand.convert(companyService.updateCompany(newCompanyCommand, companyCode));
        return command;
    }
}