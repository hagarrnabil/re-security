package com.sap.cloud.security.samples.resecurity.services;

import com.sap.cloud.security.samples.resecurity.commands.UsageTypeCommand;
import com.sap.cloud.security.samples.resecurity.model.UsageType;

import java.util.Set;

public interface UsageTypeSevice {
    Set<UsageTypeCommand> getUsageTypeCommands();

    UsageType findById(Long l);

    void deleteById(Long idToDelete);

    UsageTypeCommand saveUsageTypeCommand(UsageTypeCommand command);
    UsageType updateUsageType(UsageTypeCommand newUsageTypeCommand, Long l);

    UsageTypeCommand findUsageTypeCommandById(Long l);
}
