package com.sap.cloud.security.samples.resecurity.services;


import com.sap.cloud.security.samples.resecurity.commands.BuildingAreaCommand;
import com.sap.cloud.security.samples.resecurity.converters.BuildingAreaCommandToBuildingArea;
import com.sap.cloud.security.samples.resecurity.converters.BuildingAreaToBuildingAreaCommand;
import com.sap.cloud.security.samples.resecurity.model.BuildingArea;
import com.sap.cloud.security.samples.resecurity.repositories.BuildingAreaRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class BuildingAreaServiceImpl implements BuildingAreaService{
    private final BuildingAreaToBuildingAreaCommand buildingAreaToBuildingAreaCommand;
    private final BuildingAreaCommandToBuildingArea buildingAreaCommandToBuildingArea;
    private final BuildingAreaRepository buildingAreaRepository;

    public BuildingAreaServiceImpl(BuildingAreaToBuildingAreaCommand buildingAreaToBuildingAreaCommand,
                                   BuildingAreaCommandToBuildingArea buildingAreaCommandToBuildingArea,
                                   BuildingAreaRepository buildingAreaRepository)
    {
        this.buildingAreaToBuildingAreaCommand = buildingAreaToBuildingAreaCommand;
        this.buildingAreaCommandToBuildingArea = buildingAreaCommandToBuildingArea;
        this.buildingAreaRepository = buildingAreaRepository;
    }

    @Override
    @Transactional
    public Set<BuildingAreaCommand> getBuildingAreaCommands() {
        return StreamSupport.stream(buildingAreaRepository.findAll()
                        .spliterator(), false)
                .map(buildingAreaToBuildingAreaCommand::convert)
                .collect(Collectors.toSet());
    }

    @Override
    public BuildingArea findById(Long l) {
        Optional<BuildingArea> buildingAreaOptional = buildingAreaRepository.findById(l);

        if (!buildingAreaOptional.isPresent()) {
            throw new RuntimeException("Building Area Not Found!");
        }

        return buildingAreaOptional.get();
    }

    @Override
    public void deleteById(Long idToDelete) {
        buildingAreaRepository.deleteById(idToDelete);
    }

    @Override
    @Transactional
    public BuildingAreaCommand saveBuildingAreaCommand(BuildingAreaCommand command) {

        BuildingArea detachedBuildingArea = buildingAreaCommandToBuildingArea.convert(command);
        BuildingArea savedBuildingArea = buildingAreaRepository.save(detachedBuildingArea);
        log.debug("Saved Building Area Id:" + savedBuildingArea.getBuildingAreaCode());
        return buildingAreaToBuildingAreaCommand.convert(savedBuildingArea);

    }

    @Override
    public BuildingArea updateBuildingArea(BuildingAreaCommand newBuildingAreaCommand, Long l) {
        return buildingAreaRepository.findById(l).map(oldBuildingArea -> {
            if (newBuildingAreaCommand.getBuildingArea() != oldBuildingArea.getBuildingArea()) oldBuildingArea.setBuildingArea(newBuildingAreaCommand.getBuildingArea());
            if (newBuildingAreaCommand.getDescription() != oldBuildingArea.getDescription()) oldBuildingArea.setDescription(newBuildingAreaCommand.getDescription());
            return buildingAreaRepository.save(oldBuildingArea);
        }).orElseThrow(() -> new RuntimeException("Building Area not found"));
    }

    @Override
    @Transactional
    public BuildingAreaCommand findBuildingAreaCommandById(Long l) {
        return buildingAreaToBuildingAreaCommand.convert(findById(l));
    }
}
