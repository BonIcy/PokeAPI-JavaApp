/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pokeapi.ui.data;

import java.util.List;
import pokeapi.api.PokemonAPIClient;
import pokeapi.parser.PokemonParser;
import pokeapi.model.Pokemon;

/**
 *
 * @author Jesùs
 */



public class PokemonProvider {
    private final PokemonAPIClient client;

    public PokemonProvider() {
        client = new PokemonAPIClient();
    }

    public Pokemon getPokemon(String idOrName) {
       try {
           String json = client.getPokemonData(idOrName);
           if (json == null || json.isBlank()) return null;
           return PokemonParser.parsePokemon(json);
       } catch (Exception e) {
           return null;
       }
   }
    public Pokemon getRandomPokemon() {
    try {
        int randomId = (int)(Math.random() * 800) + 1;
        String json = client.getPokemonData(String.valueOf(randomId));
        if (json == null || json.isBlank()) return null;
        return PokemonParser.parsePokemon(json);
    } catch (Exception e) {
        return null;
    }
}

    public List<Pokemon> getPokemonsByGeneration(int gen) {
        try {
            return client.getPokemonsByGeneration(gen, 200);
        } catch (Exception e) {
            return List.of();
        }
}
}
