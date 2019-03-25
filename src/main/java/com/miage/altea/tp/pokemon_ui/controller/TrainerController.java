package com.miage.altea.tp.pokemon_ui.controller;

import com.miage.altea.tp.pokemon_ui.pokemonTypes.bo.PokemonType;
import com.miage.altea.tp.pokemon_ui.pokemonTypes.service.PokemonTypeService;
import com.miage.altea.tp.pokemon_ui.trainers.bo.Pokemon;
import com.miage.altea.tp.pokemon_ui.trainers.bo.Trainer;
import com.miage.altea.tp.pokemon_ui.trainers.service.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

@Controller
public class TrainerController {

    TrainerService trainerService;
    PokemonTypeService pokemonService;

    @GetMapping("/trainers")
    public ModelAndView trainers(){
        HashMap<String, List<Trainer>> map = new HashMap<>();
        List<Trainer> trains = trainerService.listTrainers();
        for(Trainer t : trains) {
            this.setTrainerTeam(t);
        }
        trains.sort(Comparator.comparing(Trainer::getName));
        map.put("trainers", trains);
        return new ModelAndView("trainers", map);
    }

    @GetMapping("/trainers/{name}")
    public ModelAndView getTrainer(@PathVariable("name") String name) {
        HashMap<String, Trainer> map = new HashMap<>();
        Trainer t = trainerService.getTrainer(name);
        this.setTrainerTeam(t);
        map.put("trainer", t);
        return new ModelAndView("singleTrainer", map);
    }

    @GetMapping("/profile")
    public ModelAndView getProfile() {
        HashMap<String, Trainer> map = new HashMap<>();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails) {
            Trainer t = trainerService.getTrainer(((UserDetails) principal).getUsername());
            this.setTrainerTeam(t);
            map.put("trainer", t);
        }
        return new ModelAndView("profile", map);
    }

    @Autowired
    public void setTrainerService(TrainerService service) {
        this.trainerService = service;
    }

    @Autowired
    public void setPokemonService(PokemonTypeService service) {
        this.pokemonService = service;
    }

    private void setTrainerTeam(Trainer t) {
        List<PokemonType> truePoks = pokemonService.listPokemonsTypes();
        List<PokemonType> trainerTeam = new ArrayList<>();
        for (Pokemon p : t.getTeam()) {
            for (PokemonType pT : truePoks) {
                if (p.getPokemonType() == pT.getId()) {
                    pT.setName(pT.getName().toUpperCase());
                    pT.setLevel(p.getLevel());
                    trainerTeam.add(pT);
                }
            }
        }
        t.setTeams(trainerTeam);
    }

}