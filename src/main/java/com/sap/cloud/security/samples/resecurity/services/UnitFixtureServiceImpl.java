package com.sap.cloud.security.samples.resecurity.services;

import com.sap.cloud.security.samples.resecurity.commands.UnitFixtureCommand;
import com.sap.cloud.security.samples.resecurity.converters.UnitFixtureCommandToUnitFixture;
import com.sap.cloud.security.samples.resecurity.converters.UnitFixtureToUnitFixtureCommand;
import com.sap.cloud.security.samples.resecurity.model.UnitFixture;
import com.sap.cloud.security.samples.resecurity.repositories.UnitFixtureRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class UnitFixtureServiceImpl implements UnitFixtureService {
    private final UnitFixtureToUnitFixtureCommand unitFixtureToUnitFixtureCommand;
    private final UnitFixtureCommandToUnitFixture unitFixtureCommandToUnitFixture;
    private final UnitFixtureRepository unitFixtureRepository;

    public UnitFixtureServiceImpl(UnitFixtureToUnitFixtureCommand unitFixtureToUnitFixtureCommand,
                                  UnitFixtureCommandToUnitFixture unitFixtureCommandToUnitFixture,
                                  UnitFixtureRepository unitFixtureRepository)
    {
        this.unitFixtureToUnitFixtureCommand = unitFixtureToUnitFixtureCommand;
        this.unitFixtureCommandToUnitFixture = unitFixtureCommandToUnitFixture;
        this.unitFixtureRepository = unitFixtureRepository;
    }

    @Override
    @Transactional
    public Set<UnitFixtureCommand> getUnitFixtureCommands() {
        return StreamSupport.stream(unitFixtureRepository.findAll()
                        .spliterator(), false)
                .map(unitFixtureToUnitFixtureCommand::convert)
                .collect(Collectors.toSet());
    }

    @Override
    public UnitFixture findById(Long l) {
        Optional<UnitFixture> unitFixtureOptional = unitFixtureRepository.findById(l);

        if (!unitFixtureOptional.isPresent()) {
            throw new RuntimeException("Unit Fixture Not Found!");
        }

        return unitFixtureOptional.get();
    }

    @Override
    public void deleteById(Long idToDelete) {
        unitFixtureRepository.deleteById(idToDelete);
    }

    @Override
    @Transactional
    public UnitFixtureCommand saveUnitFixtureCommand(UnitFixtureCommand command) {
        UnitFixture detachedUnitFixture = unitFixtureCommandToUnitFixture.convert(command);
        UnitFixture savedUnitFixture = unitFixtureRepository.save(detachedUnitFixture);
        log.debug("Saved Unit Fixture Id:" + savedUnitFixture.getUnitFixtureCode());
        return unitFixtureToUnitFixtureCommand.convert(savedUnitFixture);
    }

    @Override
    public UnitFixture updateUnitFixture(UnitFixtureCommand newUnitFixtureCommand, Long l) {
        return unitFixtureRepository.findById(l).map(oldUnitFixture -> {
            if (newUnitFixtureCommand.getFixtureId() != oldUnitFixture.getFixtureId()) oldUnitFixture.setFixtureId(newUnitFixtureCommand.getFixtureId());
            if (newUnitFixtureCommand.getFixtureDescr() != oldUnitFixture.getFixtureDescr()) oldUnitFixture.setFixtureDescr(newUnitFixtureCommand.getFixtureDescr());
            return unitFixtureRepository.save(oldUnitFixture);
        }).orElseThrow(() -> new RuntimeException("Unit Fixture not found"));
    }


    @Override
    @Transactional
    public UnitFixtureCommand findUnitFixtureCommandById(Long l) {
        return unitFixtureToUnitFixtureCommand.convert(findById(l));
    }
}
