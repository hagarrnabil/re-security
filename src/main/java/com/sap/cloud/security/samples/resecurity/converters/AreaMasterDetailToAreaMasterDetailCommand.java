package com.sap.cloud.security.samples.resecurity.converters;


import com.sap.cloud.security.samples.resecurity.commands.AreaMasterDetailCommand;
import com.sap.cloud.security.samples.resecurity.model.AreaMasterDetail;
import io.micrometer.common.lang.Nullable;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AreaMasterDetailToAreaMasterDetailCommand implements Converter<AreaMasterDetail, AreaMasterDetailCommand> {
    private final UnitToUnitCommand unitConverter;

    public AreaMasterDetailToAreaMasterDetailCommand(UnitToUnitCommand unitConverter) {
        this.unitConverter = unitConverter;
    }
    @Synchronized
    @Nullable
    @Override
    public AreaMasterDetailCommand convert(AreaMasterDetail source) {

        if (source == null) {
            return null;
        }

        final AreaMasterDetailCommand areaMasterDetailCommand = new AreaMasterDetailCommand();
        areaMasterDetailCommand.setAreaCode(source.getAreaCode());
        if (source.getProjectArea() != null) {
            areaMasterDetailCommand.setProjectAreaCode(source.getProjectArea().getProjectAreaCode());
        }
        if (source.getBuildingArea() != null) {
            areaMasterDetailCommand.setBuildingAreaCode(source.getBuildingArea().getBuildingAreaCode());
        }
        if (source.getUnitArea() != null) {
            areaMasterDetailCommand.setUnitAreaCode(source.getUnitArea().getUnitAreaCode());
        }
        if (source.getUnitOfMeasurement() != null) {
            areaMasterDetailCommand.setMeasurementCode(source.getUnitOfMeasurement().getMeasurementCode());
        }
        areaMasterDetailCommand.setAreaMaster(source.getAreaMaster());
        areaMasterDetailCommand.setDescription(source.getDescription());
        areaMasterDetailCommand.setProjectFlag(source.getProjectFlag());
        areaMasterDetailCommand.setBuildingFlag(source.getBuildingFlag());
        areaMasterDetailCommand.setUnitFlag(source.getUnitFlag());
        if (source.getUnits() != null && source.getUnits().size() > 0){
            source.getUnits()
                    .forEach(unit -> areaMasterDetailCommand.getUnitCommands().add(unitConverter.convert(unit)));
        }
        return areaMasterDetailCommand;
    }
}
