package com.sap.cloud.security.samples.resecurity.commands;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sap.cloud.security.samples.resecurity.commands.AreaMasterDetailCommand;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class BuildingAreaCommand implements Serializable {
    private Long buildingAreaCode;
    private String buildingArea;
    private String description;
    @JsonIgnore
    private Set<AreaMasterDetailCommand> areaMasterDetailCommands = new HashSet<>();
}
