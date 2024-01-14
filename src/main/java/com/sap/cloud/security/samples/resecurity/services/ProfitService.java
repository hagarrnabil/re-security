package com.sap.cloud.security.samples.resecurity.services;

import com.sap.cloud.security.samples.resecurity.commands.ProfitCenterCommand;
import com.sap.cloud.security.samples.resecurity.model.ProfitCenter;

import java.util.Set;

public interface ProfitService {
    Set<ProfitCenterCommand> getProfitCommands();

    ProfitCenter findById(Long l);

    void deleteById(Long idToDelete);

    ProfitCenterCommand saveProfitCommand(ProfitCenterCommand command);
    ProfitCenter updateProfit(ProfitCenterCommand newProfitCommand, Long l);

    ProfitCenterCommand findProfitCommandById(Long l);
}
