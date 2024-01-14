package com.sap.cloud.security.samples.resecurity.services;

import com.sap.cloud.security.samples.resecurity.commands.BuildingCommand;
import com.sap.cloud.security.samples.resecurity.model.Building;

import java.util.Set;

public interface BuildingService {
    Set<BuildingCommand> getBuildingCommands();

    Building findById(Long l);

    void deleteById(Long idToDelete);

    BuildingCommand saveBuildingCommand(BuildingCommand command);
    Building updateBuilding(Building newBuilding, Long l);

    BuildingCommand findBuildingCommandById(Long l);
}
