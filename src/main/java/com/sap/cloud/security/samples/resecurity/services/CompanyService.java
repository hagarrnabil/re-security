package com.sap.cloud.security.samples.resecurity.services;

import com.sap.cloud.security.samples.resecurity.commands.CompanyCommand;
import com.sap.cloud.security.samples.resecurity.model.Company;

import java.util.Set;

public interface CompanyService {
    Set<CompanyCommand> getCompanyCommands();

    Company findById(Long l);

    void deleteById(Long idToDelete);

    CompanyCommand saveCompanyCommand(CompanyCommand command);

    Company updateCompany(CompanyCommand newCompanyCommand, Long l);

    CompanyCommand findCompanyCommandById(Long l);

}
