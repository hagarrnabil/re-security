package com.sap.cloud.security.samples.resecurity.services;

import com.sap.cloud.security.samples.resecurity.commands.UnitSubtypeCommand;
import com.sap.cloud.security.samples.resecurity.converters.UnitSubtypeCommandToUnitSubtype;
import com.sap.cloud.security.samples.resecurity.converters.UnitSubtypeToUnitSubtypeCommand;
import com.sap.cloud.security.samples.resecurity.model.UnitSubtype;
import com.sap.cloud.security.samples.resecurity.repositories.UnitSubtypeRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class UnitSubtypeServiceImpl implements UnitSubtypeService{
    private final UnitSubtypeToUnitSubtypeCommand unitSubtypeToUnitSubtypeCommand;
    private final UnitSubtypeCommandToUnitSubtype unitSubtypeCommandToUnitSubtype;
    private final UnitSubtypeRepository unitSubtypeRepository;

    public UnitSubtypeServiceImpl(UnitSubtypeToUnitSubtypeCommand unitSubtypeToUnitSubtypeCommand,
                                  UnitSubtypeCommandToUnitSubtype unitSubtypeCommandToUnitSubtype,
                                  UnitSubtypeRepository unitSubtypeRepository)
    {
        this.unitSubtypeToUnitSubtypeCommand = unitSubtypeToUnitSubtypeCommand;
        this.unitSubtypeCommandToUnitSubtype = unitSubtypeCommandToUnitSubtype;
        this.unitSubtypeRepository = unitSubtypeRepository;
    }

    @Override
    @Transactional
    public Set<UnitSubtypeCommand> getUnitSubtypeCommands() {
        return StreamSupport.stream(unitSubtypeRepository.findAll()
                        .spliterator(), false)
                .map(unitSubtypeToUnitSubtypeCommand::convert)
                .collect(Collectors.toSet());
    }

    @Override
    public UnitSubtype findById(Long l) {
        Optional<UnitSubtype> unitSubtypeOptional = unitSubtypeRepository.findById(l);

        if (!unitSubtypeOptional.isPresent()) {
            throw new RuntimeException("Unit Subtype Not Found!");
        }

        return unitSubtypeOptional.get();
    }

    @Override
    public void deleteById(Long idToDelete) {
        unitSubtypeRepository.deleteById(idToDelete);
    }

    @Override
    @Transactional
    public UnitSubtypeCommand saveUnitSubtypeCommand(UnitSubtypeCommand command) {

        UnitSubtype detachedUnitSubtype = unitSubtypeCommandToUnitSubtype.convert(command);
        UnitSubtype savedUnitSubtype = unitSubtypeRepository.save(detachedUnitSubtype);
        log.debug("Saved Unit Subtype Id:" + savedUnitSubtype.getUnitSubtypeCode());
        return unitSubtypeToUnitSubtypeCommand.convert(savedUnitSubtype);

    }

    @Override
    public UnitSubtype updateUnitSubtype(UnitSubtypeCommand newUnitSubtypeCommand, Long l) {
        return unitSubtypeRepository.findById(l).map(oldUnitSubtype -> {
            if (newUnitSubtypeCommand.getSubtypeId() != oldUnitSubtype.getSubtypeId()) oldUnitSubtype.setSubtypeId(newUnitSubtypeCommand.getSubtypeId());
            if (newUnitSubtypeCommand.getSubtypeDescr() != oldUnitSubtype.getSubtypeDescr()) oldUnitSubtype.setSubtypeDescr(newUnitSubtypeCommand.getSubtypeDescr());
            return unitSubtypeRepository.save(oldUnitSubtype);
        }).orElseThrow(() -> new RuntimeException("Unit Subtype not found"));
    }

    @Override
    @Transactional
    public UnitSubtypeCommand findUnitSubtypeCommandById(Long l) {
        return unitSubtypeToUnitSubtypeCommand.convert(findById(l));
    }
}
