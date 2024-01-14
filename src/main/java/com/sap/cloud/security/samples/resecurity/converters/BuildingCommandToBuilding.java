package com.sap.cloud.security.samples.resecurity.converters;

import com.sap.cloud.security.samples.resecurity.commands.BuildingCommand;
import com.sap.cloud.security.samples.resecurity.model.Building;
import com.sap.cloud.security.samples.resecurity.model.BuildingType;
import com.sap.cloud.security.samples.resecurity.model.ProfitCenter;
import com.sap.cloud.security.samples.resecurity.model.Project;
import io.micrometer.common.lang.Nullable;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class BuildingCommandToBuilding implements Converter<BuildingCommand, Building> {
    private final UnitCommandToUnit unitConverter;

    public BuildingCommandToBuilding(UnitCommandToUnit unitConverter) {
        this.unitConverter = unitConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public Building convert(BuildingCommand source) {
        if (source == null) {
            return null;
        }

        final Building building = new Building();
        building.setBuildingCode(source.getBuildingCode());
        if (source.getProjectCode() != null) {
            Project project = new Project();
            project.setProjectCode(source.getProjectCode());
            building.setProject(project);
            project.addBuilding(building);
        }
        if (source.getProfitCode() != null) {
            ProfitCenter profitCenter = new ProfitCenter();
            profitCenter.setProfitCode(source.getProfitCode());
            building.setProfitCenter(profitCenter);
            profitCenter.addBuilding(building);
        }
        if (source.getBuildingTypeCode() != null) {
            BuildingType buildingType = new BuildingType();
            buildingType.setBuildingTypeCode(source.getBuildingTypeCode());
            building.setBuildingType(buildingType);
            buildingType.addBuilding(building);
        }
        building.setBuildingId(source.getBuildingId());
        building.setBuildingDescription(source.getBuildingDescription());
        building.setOldNumber(source.getOldNumber());
        building.setValidFrom(source.getValidFrom());
        building.setNumberOfFloors(source.getNumberOfFloors());
        building.setProfit(source.getProfit());
        if (source.getUnitCommands() != null && source.getUnitCommands().size() > 0){
            source.getUnitCommands()
                    .forEach( unitCommand -> building.getUnits().add(unitConverter.convert(unitCommand)));
        }
        return building;
    }

}
