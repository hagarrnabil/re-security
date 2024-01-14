package com.sap.cloud.security.samples.resecurity.services;

import com.sap.cloud.security.samples.resecurity.commands.UnitCommand;
import com.sap.cloud.security.samples.resecurity.converters.UnitCommandToUnit;
import com.sap.cloud.security.samples.resecurity.converters.UnitToUnitCommand;
import com.sap.cloud.security.samples.resecurity.model.*;
import com.sap.cloud.security.samples.resecurity.repositories.UnitRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class UnitServiceImpl implements UnitService {
    private final UnitToUnitCommand unitToUnitCommand;
    private final UnitCommandToUnit unitCommandToUnit;
    private final UnitRepository unitRepository;

    public UnitServiceImpl(UnitToUnitCommand unitToUnitCommand, UnitCommandToUnit unitCommandToUnit, UnitRepository unitRepository) {
        this.unitToUnitCommand = unitToUnitCommand;
        this.unitCommandToUnit = unitCommandToUnit;
        this.unitRepository = unitRepository;
    }

    @Override
    @Transactional
    public Set<UnitCommand> getUnitCommands() {
        return StreamSupport.stream(unitRepository.findAll()
                        .spliterator(), false)
                .map(unitToUnitCommand::convert)
                .collect(Collectors.toSet());
    }

    @Override
    public Unit findById(Long l) {
        Optional<Unit> unitOptional = unitRepository.findById(l);

        if (!unitOptional.isPresent()) {
            throw new RuntimeException("Unit Not Found!");
        }

        return unitOptional.get();
    }

    @Override
    public void deleteById(Long idToDelete) {
        unitRepository.deleteById(idToDelete);
    }

    @Override
    @Transactional
    public UnitCommand saveUnitCommand(UnitCommand command) {

        Unit detachedUnit = unitCommandToUnit.convert(command);
        Unit savedUnit = unitRepository.save(detachedUnit);
        log.debug("Saved Unit Id:" + savedUnit.getUnitCode());
        return unitToUnitCommand.convert(savedUnit);

    }

    @Override
    public Unit updateUnit(Unit newUnit, Long l) {
        return unitRepository.findById(l).map(oldUnit -> {
            if (newUnit.getUnitKey() != oldUnit.getUnitKey()) oldUnit.setUnitKey(newUnit.getUnitKey());
            if (newUnit.getDescription() != oldUnit.getDescription()) oldUnit.setDescription(newUnit.getDescription());
            if (newUnit.getOldNumber() != oldUnit.getOldNumber()) oldUnit.setOldNumber(newUnit.getOldNumber());
            if (newUnit.getBlockingDate() != oldUnit.getBlockingDate()) oldUnit.setBlockingDate(newUnit.getBlockingDate());
            if (newUnit.getBlockingReason() != oldUnit.getBlockingReason()) oldUnit.setBlockingReason(newUnit.getBlockingReason());
            if (newUnit.getSalesPhase() != oldUnit.getSalesPhase()) oldUnit.setSalesPhase(newUnit.getSalesPhase());
            if (newUnit.getConstructionDate() != oldUnit.getConstructionDate()) oldUnit.setConstructionDate(newUnit.getConstructionDate());
            if (newUnit.getUnitDeliveryDate() != oldUnit.getUnitDeliveryDate()) oldUnit.setUnitDeliveryDate(newUnit.getUnitDeliveryDate());
            if (newUnit.getArea() != oldUnit.getArea()) oldUnit.setArea(newUnit.getArea());
            if (newUnit.getAreaValue() != oldUnit.getAreaValue()) oldUnit.setAreaValue(newUnit.getAreaValue());
            if (newUnit.getPrice() != oldUnit.getPrice()) oldUnit.setPrice(newUnit.getPrice());
            if (newUnit.getNoOfRooms() != oldUnit.getNoOfRooms()) oldUnit.setNoOfRooms(newUnit.getNoOfRooms());
            if (newUnit.getValidFrom() != oldUnit.getValidFrom()) oldUnit.setValidFrom(newUnit.getValidFrom());
            if (newUnit.getFromFloor() != oldUnit.getFromFloor()) oldUnit.setFromFloor(newUnit.getFromFloor());
            if (newUnit.getToFloor() != oldUnit.getToFloor()) oldUnit.setToFloor(newUnit.getToFloor());
            if (newUnit.getBuildingCode() != null) {
                Building building = new Building();
                building.setBuildingCode(newUnit.getBuildingCode());
                oldUnit.setBuilding(building);
                building.addUnit(oldUnit);
            }
            if (newUnit.getUnitFloorCode() != null) {
                UnitFloor unitFloor = new UnitFloor();
                unitFloor.setUnitFloorCode(newUnit.getUnitFloorCode());
                oldUnit.setUnitFloor(unitFloor);
                unitFloor.addUnit(oldUnit);
            }
            if (newUnit.getUnitOrientationCode() != null) {
                UnitOrientation unitOrientation = new UnitOrientation();
                unitOrientation.setUnitOrientationCode(newUnit.getUnitOrientationCode());
                oldUnit.setUnitOrientation(unitOrientation);
                unitOrientation.addUnit(oldUnit);
            }
            if (newUnit.getUnitFixtureCode() != null) {
                UnitFixture unitFixture = new UnitFixture();
                unitFixture.setUnitFixtureCode(newUnit.getUnitFixtureCode());
                oldUnit.setUnitFixture(unitFixture);
                unitFixture.addUnit(oldUnit);
            }
            if (newUnit.getUnitStatusCode() != null) {
                UnitStatus unitStatus = new UnitStatus();
                unitStatus.setUnitStatusCode(newUnit.getUnitStatusCode());
                oldUnit.setUnitStatus(unitStatus);
                unitStatus.addUnit(oldUnit);
            }
            if (newUnit.getUnitViewCode() != null) {
                UnitView unitView = new UnitView();
                unitView.setUnitViewCode(newUnit.getUnitViewCode());
                oldUnit.setUnitView(unitView);
                unitView.addUnit(oldUnit);
            }
            if (newUnit.getUsageTypeCode() != null) {
                UsageType usageType = new UsageType();
                usageType.setUsageTypeCode(newUnit.getUsageTypeCode());
                oldUnit.setUsageType(usageType);
                usageType.addUnit(oldUnit);
            }
            if (newUnit.getUnitSubtypeCode() != null) {
                UnitSubtype unitSubtype = new UnitSubtype();
                unitSubtype.setUnitSubtypeCode(newUnit.getUnitSubtypeCode());
                oldUnit.setUnitSubtype(unitSubtype);
                unitSubtype.addUnit(oldUnit);
            }
            if (newUnit.getAreaCode() != null) {
                AreaMasterDetail areaMasterDetail = new AreaMasterDetail();
                areaMasterDetail.setAreaCode(newUnit.getAreaCode());
                oldUnit.setAreaMasterDetail(areaMasterDetail);
                areaMasterDetail.addUnit(oldUnit);
            }
            return unitRepository.save(oldUnit);
        }).orElseThrow(() -> new RuntimeException("Unit not found"));
    }

    @Override
    @Transactional
    public UnitCommand findUnitCommandById(Long l) {
        return unitToUnitCommand.convert(findById(l));
    }
}
