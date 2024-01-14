package com.sap.cloud.security.samples.resecurity.services;

import com.sap.cloud.security.samples.resecurity.commands.ProjectAreaCommand;
import com.sap.cloud.security.samples.resecurity.converters.ProjectAreaCommandToProjectArea;
import com.sap.cloud.security.samples.resecurity.converters.ProjectAreaToProjectAreaCommand;
import com.sap.cloud.security.samples.resecurity.model.ProjectArea;
import com.sap.cloud.security.samples.resecurity.repositories.ProjectAreaRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class ProjectAreaServiceImpl implements ProjectAreaService{
    private final ProjectAreaToProjectAreaCommand projectAreaToProjectAreaCommand;
    private final ProjectAreaCommandToProjectArea projectAreaCommandToProjectArea;
    private final ProjectAreaRepository projectAreaRepository;

    public ProjectAreaServiceImpl(ProjectAreaToProjectAreaCommand projectAreaToProjectAreaCommand,
                                  ProjectAreaCommandToProjectArea projectAreaCommandToProjectArea,
                                  ProjectAreaRepository projectAreaRepository)
    {
        this.projectAreaToProjectAreaCommand = projectAreaToProjectAreaCommand;
        this.projectAreaCommandToProjectArea = projectAreaCommandToProjectArea;
        this.projectAreaRepository = projectAreaRepository;
    }

    @Override
    @Transactional
    public Set<ProjectAreaCommand> getProjectAreaCommands() {
        return StreamSupport.stream(projectAreaRepository.findAll()
                        .spliterator(), false)
                .map(projectAreaToProjectAreaCommand::convert)
                .collect(Collectors.toSet());
    }

    @Override
    public ProjectArea findById(Long l) {
        Optional<ProjectArea> projectAreaOptional = projectAreaRepository.findById(l);

        if (!projectAreaOptional.isPresent()) {
            throw new RuntimeException("Project Area Not Found!");
        }

        return projectAreaOptional.get();
    }

    @Override
    public void deleteById(Long idToDelete) {
        projectAreaRepository.deleteById(idToDelete);
    }

    @Override
    @Transactional
    public ProjectAreaCommand saveProjectAreaCommand(ProjectAreaCommand command) {

        ProjectArea detachedProjectArea = projectAreaCommandToProjectArea.convert(command);
        ProjectArea savedProjectArea = projectAreaRepository.save(detachedProjectArea);
        log.debug("Saved Project Area Id:" + savedProjectArea.getProjectAreaCode());
        return projectAreaToProjectAreaCommand.convert(savedProjectArea);

    }

    @Override
    public ProjectArea updateProjectArea(ProjectAreaCommand newProjectAreaCommand, Long l) {
        return projectAreaRepository.findById(l).map(oldProjectArea -> {
            if (newProjectAreaCommand.getProjectArea() != oldProjectArea.getProjectArea()) oldProjectArea.setProjectArea(newProjectAreaCommand.getProjectArea());
            if (newProjectAreaCommand.getDescription() != oldProjectArea.getDescription()) oldProjectArea.setDescription(newProjectAreaCommand.getDescription());
            return projectAreaRepository.save(oldProjectArea);
        }).orElseThrow(() -> new RuntimeException("Project Area not found"));
    }

    @Override
    @Transactional
    public ProjectAreaCommand findProjectAreaCommandById(Long l) {
        return projectAreaToProjectAreaCommand.convert(findById(l));
    }
}
