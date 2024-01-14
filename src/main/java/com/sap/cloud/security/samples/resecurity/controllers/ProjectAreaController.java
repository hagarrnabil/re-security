package com.sap.cloud.security.samples.resecurity.controllers;

import com.sap.cloud.security.samples.resecurity.commands.ProjectAreaCommand;
import com.sap.cloud.security.samples.resecurity.converters.ProjectAreaToProjectAreaCommand;
import com.sap.cloud.security.samples.resecurity.services.ProjectAreaService;
import jakarta.validation.constraints.NotNull;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RestController
public class ProjectAreaController {
    private final ProjectAreaService projectAreaService;
    private final ProjectAreaToProjectAreaCommand projectAreaToProjectAreaCommand;

    public ProjectAreaController(ProjectAreaService projectAreaService,
                                 ProjectAreaToProjectAreaCommand projectAreaToProjectAreaCommand) {
        this.projectAreaService = projectAreaService;
        this.projectAreaToProjectAreaCommand = projectAreaToProjectAreaCommand;
    }

    @GetMapping("/projectareas")
    Set<ProjectAreaCommand> all() {
        return projectAreaService.getProjectAreaCommands();
    }

    @GetMapping("/projectareas/{projectAreaCode}")
    public Optional<ProjectAreaCommand> findByIds(@PathVariable @NotNull Long projectAreaCode) {

        return Optional.ofNullable(projectAreaService.findProjectAreaCommandById(projectAreaCode));
    }

    @PostMapping("/projectareas")
    ProjectAreaCommand newProjectAreaCommand(@RequestBody ProjectAreaCommand newProjectAreaCommand) {

        ProjectAreaCommand savedCommand = projectAreaService.saveProjectAreaCommand(newProjectAreaCommand);
        return savedCommand;

    }

    @DeleteMapping("/projectareas/{projectAreaCode}")
    void deleteProjectAreaCommand(@PathVariable Long projectAreaCode) {
        projectAreaService.deleteById(projectAreaCode);
    }

    @PutMapping
    @RequestMapping("/projectareas/{projectAreaCode}")
    @Transactional
    ProjectAreaCommand updateProjectAreaCommand(@RequestBody ProjectAreaCommand newProjectAreaCommand, @PathVariable Long projectAreaCode) {

        ProjectAreaCommand command = projectAreaToProjectAreaCommand.convert(projectAreaService.updateProjectArea(newProjectAreaCommand, projectAreaCode));
        return command;
    }
}
