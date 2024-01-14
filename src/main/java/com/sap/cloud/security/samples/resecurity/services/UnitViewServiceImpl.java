package com.sap.cloud.security.samples.resecurity.services;

import com.sap.cloud.security.samples.resecurity.commands.UnitViewCommand;
import com.sap.cloud.security.samples.resecurity.converters.UnitViewCommandToUnitView;
import com.sap.cloud.security.samples.resecurity.converters.UnitViewToUnitViewCommand;
import com.sap.cloud.security.samples.resecurity.model.UnitView;
import com.sap.cloud.security.samples.resecurity.repositories.UnitViewRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class UnitViewServiceImpl implements UnitViewService{
    private final UnitViewToUnitViewCommand unitViewToUnitViewCommand;
    private final UnitViewCommandToUnitView unitViewCommandToUnitView;
    private final UnitViewRepository unitViewRepository;

    public UnitViewServiceImpl(UnitViewToUnitViewCommand unitViewToUnitViewCommand,
                               UnitViewCommandToUnitView unitViewCommandToUnitView,
                               UnitViewRepository unitViewRepository)
    {
        this.unitViewToUnitViewCommand = unitViewToUnitViewCommand;
        this.unitViewCommandToUnitView = unitViewCommandToUnitView;
        this.unitViewRepository = unitViewRepository;
    }

    @Override
    @Transactional
    public Set<UnitViewCommand> getUnitViewCommands() {
        return StreamSupport.stream(unitViewRepository.findAll()
                        .spliterator(), false)
                .map(unitViewToUnitViewCommand::convert)
                .collect(Collectors.toSet());
    }

    @Override
    public UnitView findById(Long l) {
        Optional<UnitView> unitViewOptional = unitViewRepository.findById(l);

        if (!unitViewOptional.isPresent()) {
            throw new RuntimeException("Unit View Not Found!");
        }

        return unitViewOptional.get();
    }

    @Override
    public void deleteById(Long idToDelete) {
        unitViewRepository.deleteById(idToDelete);
    }

    @Override
    @Transactional
    public UnitViewCommand saveUnitViewCommand(UnitViewCommand command) {
        UnitView detachedUnitView = unitViewCommandToUnitView.convert(command);
        UnitView savedUnitView = unitViewRepository.save(detachedUnitView);
        log.debug("Saved Unit View Id:" + savedUnitView.getUnitViewCode());
        return unitViewToUnitViewCommand.convert(savedUnitView);
    }

    @Override
    public UnitView updateUnitView(UnitViewCommand newUnitViewCommand, Long l) {
        return unitViewRepository.findById(l).map(oldUnitView -> {
            if (newUnitViewCommand.getViewId() != oldUnitView.getViewId()) oldUnitView.setViewId(newUnitViewCommand.getViewId());
            if (newUnitViewCommand.getViewDescr() != oldUnitView.getViewDescr()) oldUnitView.setViewDescr(newUnitViewCommand.getViewDescr());
            return unitViewRepository.save(oldUnitView);
        }).orElseThrow(() -> new RuntimeException("Unit View not found"));
    }

    @Override
    @Transactional
    public UnitViewCommand findUnitViewCommandById(Long l) {
        return unitViewToUnitViewCommand.convert(findById(l));
    }
}
