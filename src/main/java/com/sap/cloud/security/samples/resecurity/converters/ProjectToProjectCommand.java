package com.sap.cloud.security.samples.resecurity.converters;

import com.sap.cloud.security.samples.resecurity.commands.ProjectCommand;
import com.sap.cloud.security.samples.resecurity.model.Project;
import io.micrometer.common.lang.Nullable;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ProjectToProjectCommand implements Converter<Project, ProjectCommand> {
    private final LocationToLocationCommand locationConverter;
    private final BuildingToBuildingCommand buildingConverter;

    public ProjectToProjectCommand(LocationToLocationCommand locationConverter, BuildingToBuildingCommand buildingConverter) {
        this.locationConverter = locationConverter;
        this.buildingConverter = buildingConverter;
    }


    @Synchronized
    @Nullable
    @Override
    public ProjectCommand convert(Project source) {

        if (source == null) {
            return null;
        }

        final ProjectCommand projectCommand = new ProjectCommand();
        projectCommand.setProjectCode(source.getProjectCode());
        if (source.getCompany() != null) {
            projectCommand.setCompanyCode(source.getCompany().getCompanyCode());
        }
        if (source.getProfitCenter() != null) {
            projectCommand.setProfitCode(source.getProfitCenter().getProfitCode());
        }
        if (source.getLocation() != null) {
            projectCommand.setLocationCode(source.getLocation().getLocationCode());
        }
        projectCommand.setProjectId(source.getProjectId());
        projectCommand.setProjectDescription(source.getProjectDescription());
        projectCommand.setValidFrom(source.getValidFrom());
        projectCommand.setProfit(source.getProfit());
        if (source.getBuildings() != null && source.getBuildings().size() > 0){
            source.getBuildings()
                    .forEach(building -> projectCommand.getBuildingCommands().add(buildingConverter.convert(building)));
        }
        return projectCommand;

    }


    public Project convert(Optional<Project> project) {
        if (project != null)
            return project.get();
        else
            return null;
    }
}
