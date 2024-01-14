package com.sap.cloud.security.samples.resecurity.services;

import com.sap.cloud.security.samples.resecurity.commands.ProjectCommand;
import com.sap.cloud.security.samples.resecurity.converters.ProjectCommandToProject;
import com.sap.cloud.security.samples.resecurity.converters.ProjectToProjectCommand;
import com.sap.cloud.security.samples.resecurity.model.Company;
import com.sap.cloud.security.samples.resecurity.model.Location;
import com.sap.cloud.security.samples.resecurity.model.ProfitCenter;
import com.sap.cloud.security.samples.resecurity.model.Project;
import com.sap.cloud.security.samples.resecurity.repositories.ProjectRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class ProjectServiceImpl implements ProjectService {
    private final ProjectToProjectCommand projectToProjectCommand;
    private final ProjectCommandToProject projectCommandToProject;
    private final ProjectRepository projectRepository;

    public ProjectServiceImpl(ProjectToProjectCommand projectToProjectCommand,
                              ProjectCommandToProject projectCommandToProject,
                              ProjectRepository projectRepository) {
        this.projectToProjectCommand = projectToProjectCommand;
        this.projectCommandToProject = projectCommandToProject;
        this.projectRepository = projectRepository;
    }


    @Override
    @Transactional
    public Set<ProjectCommand> getProjectCommands() {
        return StreamSupport.stream(projectRepository.findAll()
                        .spliterator(), false)
                .map(projectToProjectCommand::convert)
                .collect(Collectors.toSet());
    }

    @Override
    public Project findById(Long l) {
        Optional<Project> projectOptional = projectRepository.findById(l);

        if (!projectOptional.isPresent()) {
            throw new RuntimeException("Project Not Found!");
        }

        return projectOptional.get();
    }

    @Override
    public void deleteById(Long idToDelete) {
        projectRepository.deleteById(idToDelete);
    }

    @Override
    @Transactional
    public ProjectCommand saveProjectCommand(ProjectCommand command) {

        Project detachedProject = projectCommandToProject.convert(command);
        Project savedProject = projectRepository.save(detachedProject);
        log.debug("Saved Company Id:" + savedProject.getProjectCode());
        return projectToProjectCommand.convert(savedProject);

    }


    @Override
    public Project updateProject(Project newProject, Long projectCode) {

        return projectRepository.findById(projectCode).map(oldProject -> {
            if (newProject.getProjectId() != oldProject.getProjectId())
                oldProject.setProjectId(newProject.getProjectId());
            if (newProject.getProjectDescription() != oldProject.getProjectDescription())
                oldProject.setProjectDescription(newProject.getProjectDescription());
            if (newProject.getProfit() != oldProject.getProfit()) oldProject.setProfit(newProject.getProfit());
            if (newProject.getValidFrom() != oldProject.getValidFrom())
                oldProject.setValidFrom(newProject.getValidFrom());
            if ((newProject.getCompanyCode() != null)) {
                Company company = new Company();
                company.setCompanyCode(newProject.getCompanyCode());
                oldProject.setCompany(company);
                company.addProject(oldProject);
            }
            if ((newProject.getProfitCode() != null)) {
                ProfitCenter profitCenter = new ProfitCenter();
                profitCenter.setProfitCode(newProject.getProfitCode());
                oldProject.setProfitCenter(profitCenter);
                profitCenter.addProject(oldProject);
            }
            if ((newProject.getLocationCode() != null)) {
                Location location = new Location();
                location.setLocationCode(newProject.getLocationCode());
                oldProject.setLocation(location);
                location.setProject(oldProject);
            }
            return projectRepository.save(oldProject);
        }).orElseThrow(() -> new RuntimeException("Project not found"));
    }


    @Override
    @Transactional
    public ProjectCommand findProjectCommandById(Long l) {
        return projectToProjectCommand.convert(findById(l));
    }
}
