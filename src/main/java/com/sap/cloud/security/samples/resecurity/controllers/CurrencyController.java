package com.sap.cloud.security.samples.resecurity.controllers;

import com.sap.cloud.security.samples.resecurity.repositories.CurrencyRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class CurrencyController {
    private CurrencyRepository currencyRepository;

    public CurrencyController(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    @RequestMapping("/currencies")
    public String getCurrencies(Model model) {

        model.addAttribute("currencies", currencyRepository.findAll());

        return "currencies";

    }
}

