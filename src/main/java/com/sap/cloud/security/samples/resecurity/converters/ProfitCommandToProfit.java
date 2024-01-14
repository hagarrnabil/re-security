package com.sap.cloud.security.samples.resecurity.converters;

import com.sap.cloud.security.samples.resecurity.commands.ProfitCenterCommand;
import com.sap.cloud.security.samples.resecurity.model.ProfitCenter;
import io.micrometer.common.lang.Nullable;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProfitCommandToProfit implements Converter<ProfitCenterCommand, ProfitCenter> {
    private final ProjectCommandToProject projectConverter;
    private final BuildingCommandToBuilding buildingConverter;

    public ProfitCommandToProfit(ProjectCommandToProject projectConverter, BuildingCommandToBuilding buildingConverter) {
        this.projectConverter = projectConverter;
        this.buildingConverter = buildingConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public ProfitCenter convert(ProfitCenterCommand source) {
        if (source == null) {
            return null;
        }

        final ProfitCenter profitCenter = new ProfitCenter();
        profitCenter.setProfitCode(source.getProfitCode());
        profitCenter.setProfitId(source.getProfitId());
        profitCenter.setProfitDescr(source.getProfitDescr());
        if (source.getProjectCommands() != null && source.getProjectCommands().size() > 0){
            source.getProjectCommands()
                    .forEach( projectCommand -> profitCenter.getProjects().add(projectConverter.convert(projectCommand)));
        }
        if (source.getBuildingCommands() != null && source.getBuildingCommands().size() > 0){
            source.getBuildingCommands()
                    .forEach( buildingCommand -> profitCenter.getBuildings().add(buildingConverter.convert(buildingCommand)));
        }
        return profitCenter;
    }
}
