package com.sap.cloud.security.samples.resecurity.services;

import com.sap.cloud.security.samples.resecurity.commands.CompanyCommand;
import com.sap.cloud.security.samples.resecurity.converters.CompanyCommandToCompany;
import com.sap.cloud.security.samples.resecurity.converters.CompanyToCompanyCommand;
import com.sap.cloud.security.samples.resecurity.converters.ProjectCommandToProject;
import com.sap.cloud.security.samples.resecurity.model.Company;
import com.sap.cloud.security.samples.resecurity.repositories.CompanyRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class  CompanyServiceImpl implements CompanyService {
    private final CompanyCommandToCompany companyCommandToCompany;
    private final CompanyToCompanyCommand companyToCompanyCommand;
    private final ProjectCommandToProject projectConverter;
    private final CompanyRepository companyRepository;

    public CompanyServiceImpl(CompanyCommandToCompany companyCommandToCompany, CompanyToCompanyCommand companyToCompanyCommand, ProjectCommandToProject projectConverter,
                              CompanyRepository companyRepository) {
        this.companyCommandToCompany = companyCommandToCompany;
        this.companyToCompanyCommand = companyToCompanyCommand;
        this.projectConverter = projectConverter;
        this.companyRepository = companyRepository;
    }

    @Override
    @Transactional
    public Set<CompanyCommand> getCompanyCommands() {
        return StreamSupport.stream(companyRepository.findAll()
                        .spliterator(), false)
                .map(companyToCompanyCommand::convert)
                .collect(Collectors.toSet());
    }

    @Override
    public Company findById(Long l) {
        Optional<Company> companyOptional = companyRepository.findById(l);

        if (!companyOptional.isPresent()) {
            throw new RuntimeException("Company Not Found!");
        }

        return companyOptional.get();
    }

    @Override
    public void deleteById(Long idToDelete) {
        companyRepository.deleteById(idToDelete);
    }

    @Override
    @Transactional
    public CompanyCommand saveCompanyCommand(CompanyCommand command) {

        Company detachedCompany = companyCommandToCompany.convert(command);
        Company savedCompany = companyRepository.save(detachedCompany);
        log.debug("Saved Company Id:" + savedCompany.getCompanyCode());
        return companyToCompanyCommand.convert(savedCompany);

    }

    @Override
    public Company updateCompany(CompanyCommand newCompanyCommand, Long l) {
        return companyRepository.findById(l).map(oldCompany -> {
            if (newCompanyCommand.getCompanyCodeId() != oldCompany.getCompanyCodeId()) oldCompany.setCompanyCodeId(newCompanyCommand.getCompanyCodeId());
            if (newCompanyCommand.getCompanyCodeDescription() != oldCompany.getCompanyCodeDescription()) oldCompany.setCompanyCodeDescription(newCompanyCommand.getCompanyCodeDescription());
            return companyRepository.save(oldCompany);
        }).orElseThrow(() -> new RuntimeException("Company not found"));
    }


    @Override
    @Transactional
    public CompanyCommand findCompanyCommandById(Long l) {
        return companyToCompanyCommand.convert(findById(l));
    }

}
