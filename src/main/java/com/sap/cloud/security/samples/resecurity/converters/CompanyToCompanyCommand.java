package com.sap.cloud.security.samples.resecurity.converters;

import com.sap.cloud.security.samples.resecurity.commands.CompanyCommand;
import com.sap.cloud.security.samples.resecurity.model.Company;
import io.micrometer.common.lang.Nullable;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CompanyToCompanyCommand implements Converter<Company, CompanyCommand> {
    private final ProjectToProjectCommand projectConverter;

    public CompanyToCompanyCommand(ProjectToProjectCommand projectConverter) {
        this.projectConverter = projectConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public CompanyCommand convert(Company source) {
        if (source == null) {
            return null;
        }
        final CompanyCommand companyCommand = new CompanyCommand();
        companyCommand.setCompanyCode(source.getCompanyCode());
        companyCommand.setCompanyCodeId(source.getCompanyCodeId());
        companyCommand.setCompanyCodeDescription(source.getCompanyCodeDescription());
        if (source.getProjects() != null && source.getProjects().size() > 0){
            source.getProjects()
                    .forEach(company -> companyCommand.getProjectCommands().add(projectConverter.convert(company)));
        }
        return companyCommand;
    }

}
