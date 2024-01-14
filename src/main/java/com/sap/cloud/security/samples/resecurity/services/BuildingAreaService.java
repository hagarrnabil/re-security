package com.sap.cloud.security.samples.resecurity.services;


import com.sap.cloud.security.samples.resecurity.commands.BuildingAreaCommand;
import com.sap.cloud.security.samples.resecurity.model.BuildingArea;

import java.util.Set;

public interface BuildingAreaService {
    Set<BuildingAreaCommand> getBuildingAreaCommands();

    BuildingArea findById(Long l);

    void deleteById(Long idToDelete);

    BuildingAreaCommand saveBuildingAreaCommand(BuildingAreaCommand command);
    BuildingArea updateBuildingArea(BuildingAreaCommand newBuildingAreaCommand, Long l);

    BuildingAreaCommand findBuildingAreaCommandById(Long l);
}
