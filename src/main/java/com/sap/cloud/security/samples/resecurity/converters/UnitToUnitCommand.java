package com.sap.cloud.security.samples.resecurity.converters;

import com.sap.cloud.security.samples.resecurity.commands.UnitCommand;
import com.sap.cloud.security.samples.resecurity.model.Unit;
import io.micrometer.common.lang.Nullable;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UnitToUnitCommand implements Converter<Unit, UnitCommand> {

    @Synchronized
    @Nullable
    @Override
    public UnitCommand convert(Unit source) {
        if (source == null) {
            return null;
        }

        final UnitCommand unitCommand = new UnitCommand();
        unitCommand.setUnitCode(source.getUnitCode());
        if (source.getBuilding() != null) {
            unitCommand.setBuildingCode(source.getBuilding().getBuildingCode());
        }
        if (source.getUnitOrientation() != null) {
            unitCommand.setUnitOrientationCode(source.getUnitOrientation().getUnitOrientationCode());
        }
        if (source.getUnitFixture() != null) {
            unitCommand.setUnitFixtureCode(source.getUnitFixture().getUnitFixtureCode());
        }
        if (source.getUnitStatus() != null) {
            unitCommand.setUnitStatusCode(source.getUnitStatus().getUnitStatusCode());
        }
        if (source.getUnitView() != null) {
            unitCommand.setUnitViewCode(source.getUnitView().getUnitViewCode());
        }
        if (source.getUsageType() != null) {
            unitCommand.setUsageTypeCode(source.getUsageType().getUsageTypeCode());
        }
        if (source.getUnitSubtype() != null) {
            unitCommand.setUnitSubtypeCode(source.getUnitSubtype().getUnitSubtypeCode());
        }
        if (source.getUnitFloor() != null) {
            unitCommand.setUnitFloorCode(source.getUnitFloor().getUnitFloorCode());
        }
        if (source.getAreaMasterDetail() != null) {
            unitCommand.setAreaCode(source.getAreaMasterDetail().getAreaCode());
        }
        unitCommand.setUnitKey(source.getUnitKey());
        unitCommand.setOldNumber(source.getOldNumber());
        unitCommand.setDescription(source.getDescription());
        unitCommand.setBlockingDate(source.getBlockingDate());
        unitCommand.setBlockingReason(source.getBlockingReason());
        unitCommand.setSalesPhase(source.getSalesPhase());
        unitCommand.setConstructionDate(source.getConstructionDate());
        unitCommand.setUnitDeliveryDate(source.getUnitDeliveryDate());
        unitCommand.setArea(source.getArea());
        unitCommand.setAreaValue(source.getAreaValue());
        unitCommand.setPrice(source.getPrice());
        unitCommand.setNoOfRooms(source.getNoOfRooms());
        unitCommand.setValidFrom(source.getValidFrom());
        unitCommand.setFromFloor(source.getFromFloor());
        unitCommand.setToFloor(source.getToFloor());
        return unitCommand;
    }
}
