package com.miage.altea.tp.pokemon_ui.controller;

import com.miage.altea.tp.pokemon_ui.pokemonTypes.bo.PokemonType;
import com.miage.altea.tp.pokemon_ui.pokemonTypes.service.PokemonTypeService;
import com.miage.altea.tp.pokemon_ui.pokemonTypes.service.PokemonTypeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class PokemonTypeController {

    PokemonTypeService service;

    @GetMapping("/pokedex")
    public ModelAndView pokedex(){
        HashMap<String, List<PokemonType>> map = new HashMap<>();
        List<PokemonType> poks = service.listPokemonsTypes();
        poks.sort(Comparator.comparing(PokemonType::getId));
        map.put("pokemonTypes", poks);
        return new ModelAndView("pokedex", map);
    }

    @Autowired
    public void setPokemonTypeService(PokemonTypeService service) {
        this.service = service;
    }

}