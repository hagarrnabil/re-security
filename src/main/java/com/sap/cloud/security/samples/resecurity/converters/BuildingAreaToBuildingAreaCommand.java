package com.sap.cloud.security.samples.resecurity.converters;

import com.sap.cloud.security.samples.resecurity.commands.BuildingAreaCommand;
import com.sap.cloud.security.samples.resecurity.model.BuildingArea;
import io.micrometer.common.lang.Nullable;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class BuildingAreaToBuildingAreaCommand implements Converter<BuildingArea, BuildingAreaCommand> {
    private final AreaMasterDetailToAreaMasterDetailCommand areaConverter;

    public BuildingAreaToBuildingAreaCommand(AreaMasterDetailToAreaMasterDetailCommand areaConverter) {
        this.areaConverter = areaConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public BuildingAreaCommand convert(BuildingArea source) {
        if (source == null) {
            return null;
        }

        final BuildingAreaCommand buildingAreaCommand = new BuildingAreaCommand();
        buildingAreaCommand.setBuildingAreaCode(source.getBuildingAreaCode());
        buildingAreaCommand.setBuildingArea(source.getBuildingArea());
        buildingAreaCommand.setDescription(source.getDescription());
        if (source.getAreaMasterDetails() != null && source.getAreaMasterDetails().size() > 0){
            source.getAreaMasterDetails()
                    .forEach(areaMasterDetail -> buildingAreaCommand.getAreaMasterDetailCommands().add(areaConverter.convert(areaMasterDetail)));
        }
        return buildingAreaCommand;
    }
}
