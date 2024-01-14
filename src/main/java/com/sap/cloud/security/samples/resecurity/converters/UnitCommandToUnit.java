package com.sap.cloud.security.samples.resecurity.converters;

import com.sap.cloud.security.samples.resecurity.commands.UnitCommand;
import com.sap.cloud.security.samples.resecurity.model.*;
import io.micrometer.common.lang.Nullable;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UnitCommandToUnit implements Converter<UnitCommand, Unit> {

    @Synchronized
    @Nullable
    @Override
    public Unit convert(UnitCommand source) {
        if (source == null) {
            return null;
        }

        final Unit unit = new Unit();
        unit.setUnitCode(source.getUnitCode());
        if (source.getBuildingCode() != null) {
            Building building = new Building();
            building.setBuildingCode(source.getBuildingCode());
            unit.setBuilding(building);
            building.addUnit(unit);
        }
        if (source.getUnitOrientationCode() != null) {
            UnitOrientation unitOrientation = new UnitOrientation();
            unitOrientation.setUnitOrientationCode(source.getUnitOrientationCode());
            unit.setUnitOrientation(unitOrientation);
            unitOrientation.addUnit(unit);
        }
        if (source.getUnitFixtureCode() != null) {
            UnitFixture unitFixture = new UnitFixture();
            unitFixture.setUnitFixtureCode(source.getUnitFixtureCode());
            unit.setUnitFixture(unitFixture);
            unitFixture.addUnit(unit);
        }
        if (source.getUnitStatusCode() != null) {
            UnitStatus unitStatus = new UnitStatus();
            unitStatus.setUnitStatusCode(source.getUnitStatusCode());
            unit.setUnitStatus(unitStatus);
            unitStatus.addUnit(unit);
        }
        if (source.getUsageTypeCode() != null) {
            UsageType usageType = new UsageType();
            usageType.setUsageTypeCode(source.getUsageTypeCode());
            unit.setUsageType(usageType);
            usageType.addUnit(unit);
        }
        if (source.getUnitViewCode() != null) {
            UnitView unitView = new UnitView();
            unitView.setUnitViewCode(source.getUnitViewCode());
            unit.setUnitView(unitView);
            unitView.addUnit(unit);
        }
        if (source.getUnitSubtypeCode() != null) {
            UnitSubtype unitSubtype = new UnitSubtype();
            unitSubtype.setUnitSubtypeCode(source.getUnitSubtypeCode());
            unit.setUnitSubtype(unitSubtype);
            unitSubtype.addUnit(unit);
        }
        if (source.getUnitFloorCode() != null) {
            UnitFloor unitFloor = new UnitFloor();
            unitFloor.setUnitFloorCode(source.getUnitFloorCode());
            unit.setUnitFloor(unitFloor);
            unitFloor.addUnit(unit);
        }
        if (source.getAreaCode() != null) {
            AreaMasterDetail areaMasterDetail = new AreaMasterDetail();
            areaMasterDetail.setAreaCode(source.getAreaCode());
            unit.setAreaMasterDetail(areaMasterDetail);
            areaMasterDetail.addUnit(unit);
        }
        unit.setUnitKey(source.getUnitKey());
        unit.setOldNumber(source.getOldNumber());
        unit.setDescription(source.getDescription());
        unit.setBlockingDate(source.getBlockingDate());
        unit.setBlockingReason(source.getBlockingReason());
        unit.setSalesPhase(source.getSalesPhase());
        unit.setConstructionDate(source.getConstructionDate());
        unit.setUnitDeliveryDate(source.getUnitDeliveryDate());
        unit.setArea(source.getArea());
        unit.setAreaValue(source.getAreaValue());
        unit.setNoOfRooms(source.getNoOfRooms());
        unit.setPrice(source.getPrice());
        unit.setValidFrom(source.getValidFrom());
        unit.setFromFloor(source.getFromFloor());
        unit.setToFloor(source.getToFloor());
        return unit;
    }

}
