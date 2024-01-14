package com.sap.cloud.security.samples.resecurity.services;

import com.sap.cloud.security.samples.resecurity.commands.UsageTypeCommand;
import com.sap.cloud.security.samples.resecurity.converters.UsageTypeCommandToUsageType;
import com.sap.cloud.security.samples.resecurity.converters.UsageTypeToUsageTypeCommand;
import com.sap.cloud.security.samples.resecurity.model.UsageType;
import com.sap.cloud.security.samples.resecurity.repositories.UsageTypeRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class UsageTypeServiceImpl implements UsageTypeSevice{
    private final UsageTypeToUsageTypeCommand usageTypeToUsageTypeCommand;
    private final UsageTypeCommandToUsageType usageTypeCommandToUsageType;
    private final UsageTypeRepository usageTypeRepository;

    public UsageTypeServiceImpl(UsageTypeToUsageTypeCommand usageTypeToUsageTypeCommand, UsageTypeCommandToUsageType usageTypeCommandToUsageType, UsageTypeRepository usageTypeRepository) {
        this.usageTypeToUsageTypeCommand = usageTypeToUsageTypeCommand;
        this.usageTypeCommandToUsageType = usageTypeCommandToUsageType;
        this.usageTypeRepository = usageTypeRepository;
    }

    @Override
    @Transactional
    public Set<UsageTypeCommand> getUsageTypeCommands() {
        return StreamSupport.stream(usageTypeRepository.findAll()
                        .spliterator(), false)
                .map(usageTypeToUsageTypeCommand::convert)
                .collect(Collectors.toSet());
    }

    @Override
    public UsageType findById(Long l) {
        Optional<UsageType> usageTypeOptional = usageTypeRepository.findById(l);

        if (!usageTypeOptional.isPresent()) {
            throw new RuntimeException("Usage Type Not Found!");
        }

        return usageTypeOptional.get();
    }

    @Override
    public void deleteById(Long idToDelete) {
        usageTypeRepository.deleteById(idToDelete);
    }

    @Override
    @Transactional
    public UsageTypeCommand saveUsageTypeCommand(UsageTypeCommand command) {
        UsageType detachedUsageType = usageTypeCommandToUsageType.convert(command);
        UsageType savedUsageType = usageTypeRepository.save(detachedUsageType);
        log.debug("Saved Usage Type Id:" + savedUsageType.getUsageTypeCode());
        return usageTypeToUsageTypeCommand.convert(savedUsageType);
    }

    @Override
    public UsageType updateUsageType(UsageTypeCommand newUsageTypeCommand, Long l) {
        return usageTypeRepository.findById(l).map(oldUsageType -> {
            if (newUsageTypeCommand.getUsageId() != oldUsageType.getUsageId()) oldUsageType.setUsageId(newUsageTypeCommand.getUsageId());
            if (newUsageTypeCommand.getUsageDescr() != oldUsageType.getUsageDescr()) oldUsageType.setUsageDescr(newUsageTypeCommand.getUsageDescr());
            return usageTypeRepository.save(oldUsageType);
        }).orElseThrow(() -> new RuntimeException("Usage Type not found"));
    }

    @Override
    @Transactional
    public UsageTypeCommand findUsageTypeCommandById(Long l) {
        return usageTypeToUsageTypeCommand.convert(findById(l));
    }
}
