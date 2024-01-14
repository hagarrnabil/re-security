package com.sap.cloud.security.samples.resecurity.converters;

import com.sap.cloud.security.samples.resecurity.commands.CompanyCommand;
import com.sap.cloud.security.samples.resecurity.model.Company;
import io.micrometer.common.lang.Nullable;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CompanyCommandToCompany implements Converter<CompanyCommand, Company> {

    private final ProjectCommandToProject projectConverter;

    public CompanyCommandToCompany( ProjectCommandToProject projectConverter) {
        this.projectConverter = projectConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public Company convert(CompanyCommand source) {
        if (source == null) {
            return null;
        }

        final Company company = new Company();
        company.setCompanyCode(source.getCompanyCode());
        company.setCompanyCodeId(source.getCompanyCodeId());
        company.setCompanyCodeDescription(source.getCompanyCodeDescription());
        if (source.getProjectCommands() != null && source.getProjectCommands().size() > 0){
            source.getProjectCommands()
                    .forEach( projectCommand -> company.getProjects().add(projectConverter.convert(projectCommand)));
        }
        return company;
    }

}
