package com.sap.cloud.security.samples.resecurity.controllers;

import com.sap.cloud.security.samples.resecurity.commands.ProjectCommand;
import com.sap.cloud.security.samples.resecurity.converters.ProjectToProjectCommand;
import com.sap.cloud.security.samples.resecurity.model.Project;
import com.sap.cloud.security.samples.resecurity.repositories.ProjectRepository;
import com.sap.cloud.security.samples.resecurity.services.ProjectService;
import jakarta.validation.constraints.NotNull;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RestController
public class ProjectController {
    private final ProjectRepository projectRepository;
    private final ProjectService projectService;
    private final ProjectToProjectCommand projectToProjectCommand;

    public ProjectController(ProjectRepository projectRepository, ProjectService projectService,
                             ProjectToProjectCommand projectToProjectCommand) {
        this.projectRepository = projectRepository;
        this.projectService = projectService;
        this.projectToProjectCommand = projectToProjectCommand;
    }

    @GetMapping("/projects")
    Set<ProjectCommand> all() {
        return projectService.getProjectCommands();
    }

    @GetMapping("/projects/{projectCode}")
    public Optional<ProjectCommand> findByIds(@PathVariable @NotNull Long projectCode) {

        return Optional.ofNullable(projectService.findProjectCommandById(projectCode));

    }

    @PostMapping("/projects")
    ProjectCommand newProjectCommand(@RequestBody ProjectCommand newProjectCommand) {

        ProjectCommand savedCommand = projectService.saveProjectCommand(newProjectCommand);
        return savedCommand;

    }

    @DeleteMapping("/projects/{projectCode}")
    void deleteProjectCommand(@PathVariable Long projectCode) {
        projectService.deleteById(projectCode);
    }

//    @PutMapping
//    @RequestMapping("/projects/{projectCode}")
//    ProjectCommand updateProjectCommand(@PathVariable Long projectCode) {
//
////        projectService.findProjectCommandById(projectCode);
//        ProjectCommand savedCommand = projectService.updatePC(projectCode);
//        return savedCommand;
//    }

    @PutMapping
    @RequestMapping("/projects/{projectCode}")
    @Transactional
    ProjectCommand updateProjectCommand(@RequestBody Project newProject, @PathVariable Long projectCode){

        ProjectCommand command = projectToProjectCommand.convert(projectService.updateProject(newProject,projectCode));
        return command;
    }

}