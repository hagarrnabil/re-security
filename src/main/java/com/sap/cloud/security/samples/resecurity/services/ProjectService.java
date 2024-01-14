package com.sap.cloud.security.samples.resecurity.services;

import com.sap.cloud.security.samples.resecurity.commands.ProjectCommand;
import com.sap.cloud.security.samples.resecurity.model.Project;

import java.util.Set;

public interface ProjectService {
    Set<ProjectCommand> getProjectCommands();

    Project findById(Long l);

    void deleteById(Long idToDelete);

    ProjectCommand saveProjectCommand(ProjectCommand command);


    Project updateProject(Project newProject, Long l);


    ProjectCommand findProjectCommandById(Long l);
}
