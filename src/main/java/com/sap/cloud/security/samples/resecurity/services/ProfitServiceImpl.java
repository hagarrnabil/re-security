package com.sap.cloud.security.samples.resecurity.services;

import com.sap.cloud.security.samples.resecurity.commands.ProfitCenterCommand;
import com.sap.cloud.security.samples.resecurity.converters.ProfitCommandToProfit;
import com.sap.cloud.security.samples.resecurity.converters.ProfitToProfitCommand;
import com.sap.cloud.security.samples.resecurity.model.ProfitCenter;
import com.sap.cloud.security.samples.resecurity.repositories.ProfitCenterRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class ProfitServiceImpl implements ProfitService{
    private final ProfitCommandToProfit profitCommandToProfit;
    private final ProfitToProfitCommand profitToProfitCommand;
    private final ProfitCenterRepository profitCenterRepository;

    public ProfitServiceImpl(ProfitCommandToProfit profitCommandToProfit, ProfitToProfitCommand profitToProfitCommand, ProfitCenterRepository profitCenterRepository) {
        this.profitCommandToProfit = profitCommandToProfit;
        this.profitToProfitCommand = profitToProfitCommand;
        this.profitCenterRepository = profitCenterRepository;
    }

    @Override
    @Transactional
    public Set<ProfitCenterCommand> getProfitCommands() {
        return StreamSupport.stream(profitCenterRepository.findAll()
                        .spliterator(), false)
                .map(profitToProfitCommand::convert)
                .collect(Collectors.toSet());
    }

    @Override
    public ProfitCenter findById(Long l) {
        Optional<ProfitCenter> profitCenterOptional = profitCenterRepository.findById(l);

        if (!profitCenterOptional.isPresent()) {
            throw new RuntimeException("Profit Center Not Found!");
        }

        return profitCenterOptional.get();
    }

    @Override
    public void deleteById(Long idToDelete) {
        profitCenterRepository.deleteById(idToDelete);
    }

    @Override
    @Transactional
    public ProfitCenterCommand saveProfitCommand(ProfitCenterCommand command) {

        ProfitCenter detachedProfit = profitCommandToProfit.convert(command);
        ProfitCenter savedProfit = profitCenterRepository.save(detachedProfit);
        log.debug("Saved Profit Center Id:" + savedProfit.getProfitCode());
        return profitToProfitCommand.convert(savedProfit);

    }

    @Override
    public ProfitCenter updateProfit(ProfitCenterCommand newProfitCommand, Long l) {
        return profitCenterRepository.findById(l).map(oldProfit -> {
            if (newProfitCommand.getProfitId() != oldProfit.getProfitId()) oldProfit.setProfitId(newProfitCommand.getProfitId());
            if (newProfitCommand.getProfitDescr() != oldProfit.getProfitDescr()) oldProfit.setProfitDescr(newProfitCommand.getProfitDescr());
            return profitCenterRepository.save(oldProfit);
        }).orElseThrow(() -> new RuntimeException("Profit not found"));
    }

    @Override
    @Transactional
    public ProfitCenterCommand findProfitCommandById(Long l) {
        return profitToProfitCommand.convert(findById(l));
    }
}
