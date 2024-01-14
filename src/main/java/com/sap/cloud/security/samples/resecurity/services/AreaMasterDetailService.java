package com.sap.cloud.security.samples.resecurity.services;



import com.sap.cloud.security.samples.resecurity.commands.AreaMasterDetailCommand;
import com.sap.cloud.security.samples.resecurity.model.AreaMasterDetail;

import java.util.Set;

public interface AreaMasterDetailService {
    Set<AreaMasterDetailCommand> getAreaCommands();

    AreaMasterDetail findById(Long l);

    void deleteById(Long idToDelete);

    AreaMasterDetailCommand saveAreaCommand(AreaMasterDetailCommand command);
    AreaMasterDetail updateArea(AreaMasterDetail newArea, Long l);

    AreaMasterDetailCommand findAreaCommandById(Long l);
}
