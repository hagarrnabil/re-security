package com.sap.cloud.security.samples.resecurity.controllers;


import com.sap.cloud.security.samples.resecurity.commands.AreaMasterDetailCommand;
import com.sap.cloud.security.samples.resecurity.converters.AreaMasterDetailToAreaMasterDetailCommand;
import com.sap.cloud.security.samples.resecurity.model.AreaMasterDetail;
import com.sap.cloud.security.samples.resecurity.services.AreaMasterDetailService;
import jakarta.validation.constraints.NotNull;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RestController
public class AreaDetailController {
    private final AreaMasterDetailService areaMasterDetailService;
    private final AreaMasterDetailToAreaMasterDetailCommand areaMasterDetailToAreaMasterDetailCommand;

    public AreaDetailController(AreaMasterDetailService areaMasterDetailService,
                                AreaMasterDetailToAreaMasterDetailCommand areaMasterDetailToAreaMasterDetailCommand) {
        this.areaMasterDetailService = areaMasterDetailService;
        this.areaMasterDetailToAreaMasterDetailCommand = areaMasterDetailToAreaMasterDetailCommand;
    }

    @GetMapping("/areas")
    Set<AreaMasterDetailCommand> all() {
        return areaMasterDetailService.getAreaCommands();
    }

    @GetMapping("/areas/{areaCode}")
    public Optional<AreaMasterDetailCommand> findByIds(@PathVariable @NotNull Long areaCode) {

        return Optional.ofNullable(areaMasterDetailService.findAreaCommandById(areaCode));
    }

    @PostMapping("/areas")
    AreaMasterDetailCommand newAreaDetailCommand(@RequestBody AreaMasterDetailCommand newAreaDetailCommand) {

        AreaMasterDetailCommand savedCommand = areaMasterDetailService.saveAreaCommand(newAreaDetailCommand);
        return savedCommand;

    }

    @DeleteMapping("/areas/{areaCode}")
    void deleteAreaDetailCommand(@PathVariable Long areaCode) {
        areaMasterDetailService.deleteById(areaCode);
    }

    @PutMapping
    @RequestMapping("/areas/{areaCode}")
    @Transactional
    AreaMasterDetailCommand updateAreaDetailCommand(@RequestBody AreaMasterDetail newAreaDetail, @PathVariable Long areaCode) {

        AreaMasterDetailCommand command = areaMasterDetailToAreaMasterDetailCommand.convert(areaMasterDetailService.updateArea(newAreaDetail, areaCode));
        return command;
    }
}
