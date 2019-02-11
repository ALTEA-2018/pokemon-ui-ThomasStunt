package com.miage.altea.tp.pokemon_ui.pokemonTypes.service;

import com.miage.altea.tp.pokemon_ui.pokemonTypes.bo.PokemonType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class PokemonTypeServiceImpl implements PokemonTypeService {

    RestTemplate restTemplate;
    String actualUrl;

    public List<PokemonType> listPokemonsTypes() {
        PokemonType[] pokemons = restTemplate.getForObject(actualUrl+"/pokemon-types/", PokemonType[].class);
        return Arrays.asList(pokemons);
    }

    public PokemonType getPokemon(int id) {
        PokemonType pok = restTemplate.getForObject(actualUrl+"/pokemon-types/?id=", PokemonType.class, id);
        return pok;
    }

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Value("${pokemonType.service.url}")
    void setPokemonTypeServiceUrl(String pokemonServiceUrl) {
        this.actualUrl = pokemonServiceUrl;
    }
}