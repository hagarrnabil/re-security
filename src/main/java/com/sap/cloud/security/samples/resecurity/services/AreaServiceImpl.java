package com.sap.cloud.security.samples.resecurity.services;


import com.sap.cloud.security.samples.resecurity.commands.AreaMasterDetailCommand;
import com.sap.cloud.security.samples.resecurity.converters.AreaMasterDetailCommandToAreaMasterDetail;
import com.sap.cloud.security.samples.resecurity.converters.AreaMasterDetailToAreaMasterDetailCommand;
import com.sap.cloud.security.samples.resecurity.model.*;
import com.sap.cloud.security.samples.resecurity.repositories.AreaMasterDetailRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class AreaServiceImpl implements AreaMasterDetailService{
    private final AreaMasterDetailToAreaMasterDetailCommand areaMasterDetailToAreaMasterDetailCommand;
    private final AreaMasterDetailCommandToAreaMasterDetail areaMasterDetailCommandToAreaMasterDetail;
    private final AreaMasterDetailRepository areaMasterDetailRepository;

    public AreaServiceImpl(AreaMasterDetailToAreaMasterDetailCommand areaMasterDetailToAreaMasterDetailCommand,
                           AreaMasterDetailCommandToAreaMasterDetail areaMasterDetailCommandToAreaMasterDetail,
                           AreaMasterDetailRepository areaMasterDetailRepository)
    {
        this.areaMasterDetailToAreaMasterDetailCommand = areaMasterDetailToAreaMasterDetailCommand;
        this.areaMasterDetailCommandToAreaMasterDetail = areaMasterDetailCommandToAreaMasterDetail;
        this.areaMasterDetailRepository = areaMasterDetailRepository;
    }

    @Override
    @Transactional
    public Set<AreaMasterDetailCommand> getAreaCommands() {
        return StreamSupport.stream(areaMasterDetailRepository.findAll()
                        .spliterator(), false)
                .map(areaMasterDetailToAreaMasterDetailCommand::convert)
                .collect(Collectors.toSet());
    }

    @Override
    public AreaMasterDetail findById(Long l) {
        Optional<AreaMasterDetail> areaMasterDetailOptional = areaMasterDetailRepository.findById(l);

        if (!areaMasterDetailOptional.isPresent()) {
            throw new RuntimeException("Area Not Found!");
        }

        return areaMasterDetailOptional.get();
    }

    @Override
    public void deleteById(Long idToDelete) {
        areaMasterDetailRepository.deleteById(idToDelete);
    }

    @Override
    @Transactional
    public AreaMasterDetailCommand saveAreaCommand(AreaMasterDetailCommand command) {

        AreaMasterDetail detachedArea= areaMasterDetailCommandToAreaMasterDetail.convert(command);
        AreaMasterDetail savedArea = areaMasterDetailRepository.save(detachedArea);
        log.debug("Saved Area Id:" + savedArea.getAreaCode());
        return areaMasterDetailToAreaMasterDetailCommand.convert(savedArea);

    }

    @Override
    public AreaMasterDetail updateArea(AreaMasterDetail newArea, Long l) {
        return areaMasterDetailRepository.findById(l).map(oldArea -> {
            if(newArea.getAreaMaster() != oldArea.getAreaMaster()) oldArea.setAreaMaster(newArea.getAreaMaster());
            if(newArea.getDescription() != oldArea.getDescription()) oldArea.setDescription(newArea.getDescription());
            if(newArea.getBuildingFlag() != oldArea.getBuildingFlag()) oldArea.setBuildingFlag(newArea.getBuildingFlag());
            if(newArea.getProjectFlag() != oldArea.getProjectFlag()) oldArea.setProjectFlag(newArea.getProjectFlag());
            if(newArea.getUnitFlag() != oldArea.getUnitFlag()) oldArea.setUnitFlag(newArea.getUnitFlag());
            if (newArea.getProjectAreaCode() != null) {
                ProjectArea projectArea = new ProjectArea();
                projectArea.setProjectAreaCode(newArea.getProjectAreaCode());
                oldArea.setProjectArea(projectArea);
                projectArea.addAMD(oldArea);
            }
            if (newArea.getBuildingAreaCode() != null) {
                BuildingArea buildingArea = new BuildingArea();
                buildingArea.setBuildingAreaCode(newArea.getBuildingAreaCode());
                oldArea.setBuildingArea(buildingArea);
                buildingArea.addAMD(oldArea);
            }
            if (newArea.getUnitAreaCode() != null) {
                UnitArea unitArea = new UnitArea();
                unitArea.setUnitAreaCode(newArea.getUnitAreaCode());
                oldArea.setUnitArea(unitArea);
                unitArea.addAMD(oldArea);
            }
            if (newArea.getMeasurementCode() != null) {
                UnitOfMeasurement measurement = new UnitOfMeasurement();
                measurement.setMeasurementCode(newArea.getMeasurementCode());
                oldArea.setUnitOfMeasurement(measurement);
                measurement.setArea(oldArea);
            }
            return areaMasterDetailRepository.save(oldArea);
        }).orElseThrow(() -> new RuntimeException("Area not found"));
    }

    @Override
    @Transactional
    public AreaMasterDetailCommand findAreaCommandById(Long l) {
        return areaMasterDetailToAreaMasterDetailCommand.convert(findById(l));
    }
}
