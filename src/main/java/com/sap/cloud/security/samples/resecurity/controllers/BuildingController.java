package com.sap.cloud.security.samples.resecurity.controllers;

import com.sap.cloud.security.samples.resecurity.commands.BuildingCommand;
import com.sap.cloud.security.samples.resecurity.converters.BuildingToBuildingCommand;
import com.sap.cloud.security.samples.resecurity.model.Building;
import com.sap.cloud.security.samples.resecurity.repositories.BuildingRepository;
import com.sap.cloud.security.samples.resecurity.services.BuildingService;
import jakarta.validation.constraints.NotNull;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;


@RestController
public class BuildingController {
    private final BuildingRepository buildingRepository;
    private final BuildingService buildingService;
    private final BuildingToBuildingCommand buildingToBuildingCommand;

    public BuildingController(BuildingRepository buildingRepository, BuildingService buildingService, BuildingToBuildingCommand buildingToBuildingCommand) {
        this.buildingRepository = buildingRepository;
        this.buildingService = buildingService;
        this.buildingToBuildingCommand = buildingToBuildingCommand;
    }

    @GetMapping("/buildings")
    Set<BuildingCommand> all() {
        return buildingService.getBuildingCommands();
    }

    @GetMapping("/buildings/{buildingCode}")
    public Optional<BuildingCommand> findByIds(@PathVariable @NotNull Long buildingCode) {

        return Optional.ofNullable(buildingService.findBuildingCommandById(buildingCode));
    }

    @PostMapping("/buildings")
    BuildingCommand newBuildingCommand(@RequestBody BuildingCommand newBuildingCommand) {

        BuildingCommand savedCommand = buildingService.saveBuildingCommand(newBuildingCommand);
        return savedCommand;

    }

    @DeleteMapping("/buildings/{buildingCode}")
    void deleteBuildingCommand(@PathVariable Long buildingCode) {
        buildingService.deleteById(buildingCode);
    }

    @PutMapping
    @RequestMapping("/buildings/{buildingCode}")
    @Transactional
    BuildingCommand updateBuildingCommand(@RequestBody Building newBuilding, @PathVariable Long buildingCode) {

        BuildingCommand command = buildingToBuildingCommand.convert(buildingService.updateBuilding(newBuilding, buildingCode));
        return command;
    }
}