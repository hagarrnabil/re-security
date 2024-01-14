package com.sap.cloud.security.samples.resecurity.controllers;

import com.sap.cloud.security.samples.resecurity.commands.BuildingTypeCommand;
import com.sap.cloud.security.samples.resecurity.converters.BuildingTypeToBuildingTypeCommand;
import com.sap.cloud.security.samples.resecurity.repositories.BuildingTypeRepository;
import com.sap.cloud.security.samples.resecurity.services.BuildingTypeService;
import jakarta.validation.constraints.NotNull;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;


@RestController
public class BuildingTypeController {
    private final BuildingTypeRepository buildingTypeRepository;
    private final BuildingTypeService buildingTypeService;
    private final BuildingTypeToBuildingTypeCommand buildingTypeToBuildingTypeCommand;

    public BuildingTypeController(BuildingTypeRepository buildingTypeRepository, BuildingTypeService buildingTypeService,
                                  BuildingTypeToBuildingTypeCommand buildingTypeToBuildingTypeCommand) {
        this.buildingTypeRepository = buildingTypeRepository;
        this.buildingTypeService = buildingTypeService;
        this.buildingTypeToBuildingTypeCommand = buildingTypeToBuildingTypeCommand;
    }

    @GetMapping("/buildingtypes")
    Set<BuildingTypeCommand> all() {
        return buildingTypeService.getBuildingTypeCommands();
    }

    @GetMapping("/buildingtypes/{buildingTypeCode}")
    public Optional<BuildingTypeCommand> findByIds(@PathVariable @NotNull Long buildingTypeCode) {

        return Optional.ofNullable(buildingTypeService.findBuildingTypeCommandById(buildingTypeCode));
    }

    @PostMapping("/buildingtypes")
    BuildingTypeCommand newBuildingTypeCommand(@RequestBody BuildingTypeCommand newBuildingTypeCommand) {

        BuildingTypeCommand savedCommand = buildingTypeService.saveBuildingTypeCommand(newBuildingTypeCommand);
        return savedCommand;

    }

    @DeleteMapping("/buildingtypes/{buildingTypeCode}")
    void deleteBuildingTypeCommand(@PathVariable Long buildingTypeCode) {
        buildingTypeService.deleteById(buildingTypeCode);
    }

    @PutMapping
    @RequestMapping("/buildingtypes/{buildingTypeCode}")
    @Transactional
    BuildingTypeCommand updateBuildingTypeCommand(@RequestBody BuildingTypeCommand newBuildingTypeCommand, @PathVariable Long buildingTypeCode) {

        BuildingTypeCommand command = buildingTypeToBuildingTypeCommand.convert(buildingTypeService.updateBuildingType(newBuildingTypeCommand, buildingTypeCode));
        return command;
    }
}

