package com.miage.altea.tp.pokemon_ui.controller;

import com.miage.altea.tp.pokemon_ui.trainers.bo.Trainer;
import com.miage.altea.tp.pokemon_ui.trainers.service.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import java.util.HashMap;
import java.util.Map;

@Controller
public class ProfileController {

    private TrainerService trainerService;

    @Autowired
    public void setTrainersService(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @GetMapping("/profile")
    public ModelAndView getProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails principal = (UserDetails) authentication.getPrincipal();
        Map<String, Object> stringObjectMap = new HashMap<>();
        Trainer trainer = this.trainerService.getTrainer(principal.getUsername());
        stringObjectMap.put("trainer", trainer);
        return new ModelAndView("trainer", stringObjectMap);
    }

}
