package com.sap.cloud.security.samples.resecurity.controllers;

import com.sap.cloud.security.samples.resecurity.commands.BuildingAreaCommand;
import com.sap.cloud.security.samples.resecurity.converters.BuildingAreaToBuildingAreaCommand;
import com.sap.cloud.security.samples.resecurity.services.BuildingAreaService;
import jakarta.validation.constraints.NotNull;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RestController
public class BuildingAreaController {
    private final BuildingAreaService buildingAreaService;
    private final BuildingAreaToBuildingAreaCommand buildingAreaToBuildingAreaCommand;

    public BuildingAreaController(BuildingAreaService buildingAreaService, BuildingAreaToBuildingAreaCommand buildingAreaToBuildingAreaCommand) {
        this.buildingAreaService = buildingAreaService;
        this.buildingAreaToBuildingAreaCommand = buildingAreaToBuildingAreaCommand;
    }

    @GetMapping("/buildingareas")
    Set<BuildingAreaCommand> all() {
        return buildingAreaService.getBuildingAreaCommands();
    }

    @GetMapping("/buildingareas/{buildingAreaCode}")
    public Optional<BuildingAreaCommand> findByIds(@PathVariable @NotNull Long buildingAreaCode) {

        return Optional.ofNullable(buildingAreaService.findBuildingAreaCommandById(buildingAreaCode));
    }

    @PostMapping("/buildingareas")
    BuildingAreaCommand newBuildingAreaCommand(@RequestBody BuildingAreaCommand newBuildingAreaCommand) {

        BuildingAreaCommand savedCommand = buildingAreaService.saveBuildingAreaCommand(newBuildingAreaCommand);
        return savedCommand;

    }

    @DeleteMapping("/buildingareas/{buildingAreaCode}")
    void deleteBuildingAreaCommand(@PathVariable Long buildingAreaCode) {
        buildingAreaService.deleteById(buildingAreaCode);
    }

    @PutMapping
    @RequestMapping("/buildingareas/{buildingAreaCode}")
    @Transactional
    BuildingAreaCommand updateBuildingAreaCommand(@RequestBody BuildingAreaCommand newBuildingAreaCommand, @PathVariable Long buildingAreaCode) {

        BuildingAreaCommand command = buildingAreaToBuildingAreaCommand.convert(buildingAreaService.updateBuildingArea(newBuildingAreaCommand, buildingAreaCode));
        return command;
    }
}