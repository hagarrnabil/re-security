package com.sap.cloud.security.samples.resecurity.services;

import com.sap.cloud.security.samples.resecurity.commands.BuildingTypeCommand;
import com.sap.cloud.security.samples.resecurity.model.BuildingType;

import java.util.Set;

public interface BuildingTypeService {
    Set<BuildingTypeCommand> getBuildingTypeCommands();

    BuildingType findById(Long l);

    void deleteById(Long idToDelete);

    BuildingTypeCommand saveBuildingTypeCommand(BuildingTypeCommand command);
    BuildingType updateBuildingType(BuildingTypeCommand newBuildingTypeCommand, Long l);

    BuildingTypeCommand findBuildingTypeCommandById(Long l);
}
