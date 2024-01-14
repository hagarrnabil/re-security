package com.sap.cloud.security.samples.resecurity.controllers;

import com.sap.cloud.security.samples.resecurity.repositories.MethodOfCalculationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MethodOfCalculationController {
    private MethodOfCalculationRepository methodOfCalculationRepository;

    public MethodOfCalculationController(MethodOfCalculationRepository methodOfCalculationRepository) {
        this.methodOfCalculationRepository = methodOfCalculationRepository;
    }

    @RequestMapping("/moc")
    public String getMoc(Model model) {

        model.addAttribute("moc", methodOfCalculationRepository.findAll());

        return "moc";

    }

}
