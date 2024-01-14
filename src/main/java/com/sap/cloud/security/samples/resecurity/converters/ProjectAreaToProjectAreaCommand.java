package com.sap.cloud.security.samples.resecurity.converters;

import com.sap.cloud.security.samples.resecurity.commands.ProjectAreaCommand;
import com.sap.cloud.security.samples.resecurity.model.ProjectArea;
import io.micrometer.common.lang.Nullable;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProjectAreaToProjectAreaCommand implements Converter<ProjectArea, ProjectAreaCommand> {
    private final AreaMasterDetailToAreaMasterDetailCommand areaConverter;

    public ProjectAreaToProjectAreaCommand(AreaMasterDetailToAreaMasterDetailCommand areaConverter) {
        this.areaConverter = areaConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public ProjectAreaCommand convert(ProjectArea source) {
        if (source == null) {
            return null;
        }

        final ProjectAreaCommand projectAreaCommand = new ProjectAreaCommand();
        projectAreaCommand.setProjectAreaCode(source.getProjectAreaCode());
        projectAreaCommand.setProjectArea(source.getProjectArea());
        projectAreaCommand.setDescription(source.getDescription());
        if (source.getAreaMasterDetails() != null && source.getAreaMasterDetails().size() > 0){
            source.getAreaMasterDetails()
                    .forEach(areaMasterDetail -> projectAreaCommand.getAreaMasterDetailCommands().add(areaConverter.convert(areaMasterDetail)));
        }
        return projectAreaCommand;
    }
}
