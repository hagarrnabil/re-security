package com.sap.cloud.security.samples.resecurity.controllers;

import com.sap.cloud.security.samples.resecurity.commands.UsageTypeCommand;
import com.sap.cloud.security.samples.resecurity.converters.UsageTypeToUsageTypeCommand;
import com.sap.cloud.security.samples.resecurity.repositories.UsageTypeRepository;
import com.sap.cloud.security.samples.resecurity.services.UsageTypeSevice;
import jakarta.validation.constraints.NotNull;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;


@RestController
public class UsageTypeController {
    private final UsageTypeRepository usageTypeRepository;
    private final UsageTypeSevice usageTypeSevice;
    private final UsageTypeToUsageTypeCommand usageTypeToUsageTypeCommand;

    public UsageTypeController(UsageTypeRepository usageTypeRepository, UsageTypeSevice usageTypeSevice,
                               UsageTypeToUsageTypeCommand usageTypeToUsageTypeCommand) {
        this.usageTypeRepository = usageTypeRepository;
        this.usageTypeSevice = usageTypeSevice;
        this.usageTypeToUsageTypeCommand = usageTypeToUsageTypeCommand;
    }

    @GetMapping("/usagetype")
    Set<UsageTypeCommand> all() {
        return usageTypeSevice.getUsageTypeCommands();
    }

    @GetMapping("/usagetype/{usageTypeCode}")
    public Optional<UsageTypeCommand> findByIds(@PathVariable @NotNull Long usageTypeCode) {

        return Optional.ofNullable(usageTypeSevice.findUsageTypeCommandById(usageTypeCode));
    }

    @PostMapping("/usagetype")
    UsageTypeCommand newUsageTypeCommand(@RequestBody UsageTypeCommand newUsageTypeCommand) {

        UsageTypeCommand savedCommand = usageTypeSevice.saveUsageTypeCommand(newUsageTypeCommand);
        return savedCommand;

    }

    @DeleteMapping("/usagetype/{usageTypeCode}")
    void deleteUsageTypeCommand(@PathVariable Long usageTypeCode) {
        usageTypeSevice.deleteById(usageTypeCode);
    }

    @PutMapping
    @RequestMapping("/usagetype/{usageTypeCode}")
    @Transactional
    UsageTypeCommand updateUsageTypeCommand(@RequestBody UsageTypeCommand newUsageTypeCommand, @PathVariable Long usageTypeCode) {

        UsageTypeCommand command = usageTypeToUsageTypeCommand.convert(usageTypeSevice.updateUsageType(newUsageTypeCommand, usageTypeCode));
        return command;
    }
}
