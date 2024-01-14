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
public class UnitAreaCommand implements Serializable {
    private Long unitAreaCode;
    private String unitArea;
    private String description;
    @JsonIgnore
    private Set<AreaMasterDetailCommand> areaMasterDetailCommands = new HashSet<>();
}
