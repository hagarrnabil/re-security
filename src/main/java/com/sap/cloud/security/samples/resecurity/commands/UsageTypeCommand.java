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
public class UsageTypeCommand implements Serializable {
    private Long usageTypeCode;
    private String usageId;
    private String usageDescr;
    @JsonIgnore
    private Set<UnitCommand> unitCommands = new HashSet<>();
}
