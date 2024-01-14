package com.sap.cloud.security.samples.resecurity.services;

import com.sap.cloud.security.samples.resecurity.commands.UnitOfMeasurementCommand;
import com.sap.cloud.security.samples.resecurity.converters.UnitOfMeasurementCommandToUnitOfMeasurement;
import com.sap.cloud.security.samples.resecurity.converters.UnitOfMeasurementToUnitOfMeasurementCommand;
import com.sap.cloud.security.samples.resecurity.model.UnitOfMeasurement;
import com.sap.cloud.security.samples.resecurity.repositories.UnitOfMeasurementRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class MeasurementServiceImpl implements MeasurementService {
    private final UnitOfMeasurementToUnitOfMeasurementCommand unitOfMeasurementToUnitOfMeasurementCommand;
    private final UnitOfMeasurementCommandToUnitOfMeasurement unitOfMeasurementCommandToUnitOfMeasurement;
    private final UnitOfMeasurementRepository unitOfMeasurementRepository;

    public MeasurementServiceImpl(UnitOfMeasurementToUnitOfMeasurementCommand unitOfMeasurementToUnitOfMeasurementCommand,
                                  UnitOfMeasurementCommandToUnitOfMeasurement unitOfMeasurementCommandToUnitOfMeasurement,
                                  UnitOfMeasurementRepository unitOfMeasurementRepository)
    {
        this.unitOfMeasurementToUnitOfMeasurementCommand = unitOfMeasurementToUnitOfMeasurementCommand;
        this.unitOfMeasurementCommandToUnitOfMeasurement = unitOfMeasurementCommandToUnitOfMeasurement;
        this.unitOfMeasurementRepository = unitOfMeasurementRepository;
    }

    @Override
    @Transactional
    public Set<UnitOfMeasurementCommand> getUOMCommands() {
        return StreamSupport.stream(unitOfMeasurementRepository.findAll()
                        .spliterator(), false)
                .map(unitOfMeasurementToUnitOfMeasurementCommand::convert)
                .collect(Collectors.toSet());
    }

    @Override
    public UnitOfMeasurement findById(Long l) {
        Optional<UnitOfMeasurement> measurementOptional = unitOfMeasurementRepository.findById(l);

        if (!measurementOptional.isPresent()) {
            throw new RuntimeException("UOM Not Found!");
        }

        return measurementOptional.get();
    }

    @Override
    public void deleteById(Long idToDelete) {
        unitOfMeasurementRepository.deleteById(idToDelete);
    }

    @Override
    @Transactional
    public UnitOfMeasurementCommand saveUOMCommand(UnitOfMeasurementCommand command) {
        UnitOfMeasurement detachedUOM = unitOfMeasurementCommandToUnitOfMeasurement.convert(command);
        UnitOfMeasurement savedUOM = unitOfMeasurementRepository.save(detachedUOM);
        log.debug("Saved UOM Id:" + savedUOM.getMeasurementCode());
        return unitOfMeasurementToUnitOfMeasurementCommand.convert(savedUOM);
    }

    @Override
    public UnitOfMeasurement updateUOM(UnitOfMeasurementCommand newMeasurementCommand, Long l) {
        return unitOfMeasurementRepository.findById(l).map(oldMeasurement -> {
           if (newMeasurementCommand.getUomID() != oldMeasurement.getUomID()) oldMeasurement.setUomID(newMeasurementCommand.getUomID());
            if (newMeasurementCommand.getUomDescr() != oldMeasurement.getUomDescr()) oldMeasurement.setUomDescr(newMeasurementCommand.getUomDescr());
            return unitOfMeasurementRepository.save(oldMeasurement);
        }).orElseThrow(() -> new RuntimeException("Measurement not found"));
    }

    @Override
    @Transactional
    public UnitOfMeasurementCommand findUOMCommandById(Long l) {
        return unitOfMeasurementToUnitOfMeasurementCommand.convert(findById(l));
    }
}
