package com.sap.cloud.security.samples.resecurity.converters;


import com.sap.cloud.security.samples.resecurity.commands.AreaMasterDetailCommand;
import com.sap.cloud.security.samples.resecurity.model.*;
import io.micrometer.common.lang.Nullable;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AreaMasterDetailCommandToAreaMasterDetail implements Converter<AreaMasterDetailCommand, AreaMasterDetail> {
    private final UnitCommandToUnit unitConverter;

    public AreaMasterDetailCommandToAreaMasterDetail(UnitCommandToUnit unitConverter) {
        this.unitConverter = unitConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public AreaMasterDetail convert(AreaMasterDetailCommand source) {
        if (source == null) {
            return null;
        }

        final AreaMasterDetail areaMasterDetail = new AreaMasterDetail();
        areaMasterDetail.setAreaCode(source.getAreaCode());
        if (source.getProjectAreaCode() != null) {
            ProjectArea projectArea = new ProjectArea();
            projectArea.setProjectAreaCode(source.getProjectAreaCode());
            areaMasterDetail.setProjectArea(projectArea);
            projectArea.addAMD(areaMasterDetail);
        }
        if (source.getBuildingAreaCode() != null) {
            BuildingArea buildingArea = new BuildingArea();
            buildingArea.setBuildingAreaCode(source.getBuildingAreaCode());
            areaMasterDetail.setBuildingArea(buildingArea);
            buildingArea.addAMD(areaMasterDetail);
        }
        if (source.getUnitAreaCode() != null) {
            UnitArea unitArea = new UnitArea();
            unitArea.setUnitAreaCode(source.getUnitAreaCode());
            areaMasterDetail.setUnitArea(unitArea);
            unitArea.addAMD(areaMasterDetail);
        }
        if (source.getMeasurementCode() != null) {
            UnitOfMeasurement measurement = new UnitOfMeasurement();
            measurement.setMeasurementCode(source.getMeasurementCode());
            areaMasterDetail.setUnitOfMeasurement(measurement);
            measurement.setArea(areaMasterDetail);
        }
        areaMasterDetail.setAreaMaster(source.getAreaMaster());
        areaMasterDetail.setDescription(source.getDescription());
        areaMasterDetail.setProjectFlag(source.getProjectFlag());
        areaMasterDetail.setBuildingFlag(source.getBuildingFlag());
        areaMasterDetail.setUnitFlag(source.getUnitFlag());
        if (source.getUnitCommands() != null && source.getUnitCommands().size() > 0){
            source.getUnitCommands()
                    .forEach( unitCommand -> areaMasterDetail.getUnits().add(unitConverter.convert(unitCommand)));
        }
        return areaMasterDetail;
    }

}
