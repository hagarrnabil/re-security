package com.sap.cloud.security.samples.resecurity.services;

import com.sap.cloud.security.samples.resecurity.commands.ProjectAreaCommand;
import com.sap.cloud.security.samples.resecurity.model.ProjectArea;

import java.util.Set;

public interface ProjectAreaService {
    Set<ProjectAreaCommand> getProjectAreaCommands();

    ProjectArea findById(Long l);

    void deleteById(Long idToDelete);

    ProjectAreaCommand saveProjectAreaCommand(ProjectAreaCommand command);
    ProjectArea updateProjectArea(ProjectAreaCommand newProjectAreaCommand, Long l);

    ProjectAreaCommand findProjectAreaCommandById(Long l);
}
