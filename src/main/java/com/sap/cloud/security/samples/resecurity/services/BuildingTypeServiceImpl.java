package com.sap.cloud.security.samples.resecurity.services;

import com.sap.cloud.security.samples.resecurity.commands.BuildingTypeCommand;
import com.sap.cloud.security.samples.resecurity.converters.BuildingTypeCommandToBuildingType;
import com.sap.cloud.security.samples.resecurity.converters.BuildingTypeToBuildingTypeCommand;
import com.sap.cloud.security.samples.resecurity.model.BuildingType;
import com.sap.cloud.security.samples.resecurity.repositories.BuildingTypeRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class
BuildingTypeServiceImpl implements BuildingTypeService{
    private final BuildingTypeToBuildingTypeCommand buildingTypeToBuildingTypeCommand;
    private final BuildingTypeCommandToBuildingType buildingTypeCommandToBuildingType;
    private final BuildingTypeRepository buildingTypeRepository;

    public BuildingTypeServiceImpl(BuildingTypeToBuildingTypeCommand buildingTypeToBuildingTypeCommand,
                                   BuildingTypeCommandToBuildingType buildingTypeCommandToBuildingType,
                                   BuildingTypeRepository buildingTypeRepository)
    {
        this.buildingTypeToBuildingTypeCommand = buildingTypeToBuildingTypeCommand;
        this.buildingTypeCommandToBuildingType = buildingTypeCommandToBuildingType;
        this.buildingTypeRepository = buildingTypeRepository;
    }

    @Override
    @Transactional
    public Set<BuildingTypeCommand> getBuildingTypeCommands() {
        return StreamSupport.stream(buildingTypeRepository.findAll()
                        .spliterator(), false)
                .map(buildingTypeToBuildingTypeCommand::convert)
                .collect(Collectors.toSet());
    }

    @Override
    public BuildingType findById(Long l) {
        Optional<BuildingType> buildingTypeOptional = buildingTypeRepository.findById(l);

        if (!buildingTypeOptional.isPresent()) {
            throw new RuntimeException("Building Type Not Found!");
        }

        return buildingTypeOptional.get();
    }

    @Override
    public void deleteById(Long idToDelete) {
        buildingTypeRepository.deleteById(idToDelete);
    }

    @Override
    @Transactional
    public BuildingTypeCommand saveBuildingTypeCommand(BuildingTypeCommand command) {

        BuildingType detachedBuildingType = buildingTypeCommandToBuildingType.convert(command);
        BuildingType savedBuildingType = buildingTypeRepository.save(detachedBuildingType);
        log.debug("Saved Building Type Id:" + savedBuildingType.getBuildingTypeCode());
        return buildingTypeToBuildingTypeCommand.convert(savedBuildingType);

    }

    @Override
    public BuildingType updateBuildingType(BuildingTypeCommand newBuildingTypeCommand, Long l) {
        return buildingTypeRepository.findById(l).map(oldBuildingType -> {
            if(newBuildingTypeCommand.getBuildingTypeId() != oldBuildingType.getBuildingTypeId()) oldBuildingType.setBuildingTypeId(newBuildingTypeCommand.getBuildingTypeId());
            if(newBuildingTypeCommand.getBuildingTypeDescr() != oldBuildingType.getBuildingTypeDescr()) oldBuildingType.setBuildingTypeDescr(newBuildingTypeCommand.getBuildingTypeDescr());
            return buildingTypeRepository.save(oldBuildingType);
        }).orElseThrow(() -> new RuntimeException("Building Type not found"));
    }

    @Override
    @Transactional
    public BuildingTypeCommand findBuildingTypeCommandById(Long l) {
        return buildingTypeToBuildingTypeCommand.convert(findById(l));
    }
}
