package com.sap.cloud.security.samples.resecurity.commands;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
@Getter
@Setter
@NoArgsConstructor
public class BuildingTypeCommand implements Serializable {
    private Long buildingTypeCode;
    private String buildingTypeId;
    private String buildingTypeDescr;
    @JsonIgnore
    private Set<BuildingCommand> buildingCommands = new HashSet<>();
}
