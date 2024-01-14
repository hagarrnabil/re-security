package com.sap.cloud.security.samples.resecurity.services;


import com.sap.cloud.security.samples.resecurity.commands.BuildingCommand;
import com.sap.cloud.security.samples.resecurity.converters.BuildingCommandToBuilding;
import com.sap.cloud.security.samples.resecurity.converters.BuildingToBuildingCommand;
import com.sap.cloud.security.samples.resecurity.model.Building;
import com.sap.cloud.security.samples.resecurity.model.BuildingType;
import com.sap.cloud.security.samples.resecurity.model.ProfitCenter;
import com.sap.cloud.security.samples.resecurity.model.Project;
import com.sap.cloud.security.samples.resecurity.repositories.BuildingRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class BuildingServiceImpl implements BuildingService{
    private final BuildingToBuildingCommand buildingToBuildingCommand;
    private final BuildingCommandToBuilding buildingCommandToBuilding;
    private final BuildingRepository buildingRepository;

    public BuildingServiceImpl(BuildingToBuildingCommand buildingToBuildingCommand, BuildingCommandToBuilding buildingCommandToBuilding, BuildingRepository buildingRepository) {
        this.buildingToBuildingCommand = buildingToBuildingCommand;
        this.buildingCommandToBuilding = buildingCommandToBuilding;
        this.buildingRepository = buildingRepository;
    }

    @Override
    @Transactional
    public Set<BuildingCommand> getBuildingCommands() {
        return StreamSupport.stream(buildingRepository.findAll()
                        .spliterator(), false)
                .map(buildingToBuildingCommand::convert)
                .collect(Collectors.toSet());

    }

    @Override
    public Building findById(Long l) {
        Optional<Building> buildingOptional = buildingRepository.findById(l);

        if (!buildingOptional.isPresent()) {
            throw new RuntimeException("Building Not Found!");
        }

        return buildingOptional.get();
    }

    @Override
    public void deleteById(Long idToDelete) {
        buildingRepository.deleteById(idToDelete);
    }

    @Override
    @Transactional
    public BuildingCommand saveBuildingCommand(BuildingCommand command) {

        Building detachedBuilding = buildingCommandToBuilding.convert(command);
        Building savedBuilding = buildingRepository.save(detachedBuilding);
        log.debug("Saved Building Id:" + savedBuilding.getBuildingCode());
        return buildingToBuildingCommand.convert(savedBuilding);

    }

    @Override
    public Building updateBuilding(Building newBuilding, Long l) {
        return buildingRepository.findById(l).map(oldBuilding -> {
            if(newBuilding.getBuildingId() != oldBuilding.getBuildingId()) oldBuilding.setBuildingId(newBuilding.getBuildingId());
            if(newBuilding.getBuildingDescription() != oldBuilding.getBuildingDescription()) oldBuilding.setBuildingDescription(newBuilding.getBuildingDescription());
            if (newBuilding.getProfit() != oldBuilding.getProfit()) oldBuilding.setProfit(newBuilding.getProfit());
            if(newBuilding.getNumberOfFloors() != oldBuilding.getNumberOfFloors()) oldBuilding.setNumberOfFloors(newBuilding.getNumberOfFloors());
            if (newBuilding.getOldNumber() != oldBuilding.getOldNumber()) oldBuilding.setOldNumber(newBuilding.getOldNumber());
            if (newBuilding.getValidFrom() != oldBuilding.getValidFrom()) oldBuilding.setValidFrom(newBuilding.getValidFrom());
            if (newBuilding.getProfitCode() != null) {
                ProfitCenter profitCenter = new ProfitCenter();
                profitCenter.setProfitCode(newBuilding.getProfitCode());
                oldBuilding.setProfitCenter(profitCenter);
                profitCenter.addBuilding(oldBuilding);
            }
            if (newBuilding.getProjectCode() != null) {
                Project project = new Project();
                project.setProjectCode(newBuilding.getProjectCode());
                oldBuilding.setProject(project);
                project.addBuilding(oldBuilding);
            }
            if (newBuilding.getBuildingTypeCode() != null) {
                BuildingType buildingType = new BuildingType();
                buildingType.setBuildingTypeCode(newBuilding.getBuildingTypeCode());
                oldBuilding.setBuildingType(buildingType);
                buildingType.addBuilding(oldBuilding);
            }
            return buildingRepository.save(oldBuilding);
        }).orElseThrow(() -> new RuntimeException("Building not found"));
    }

    @Override
    @Transactional
    public BuildingCommand findBuildingCommandById(Long l) {
        return buildingToBuildingCommand.convert(findById(l));
    }
}
