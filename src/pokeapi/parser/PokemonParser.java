/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pokeapi.parser;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import pokeapi.model.*;
/**
 *
 * @author Jesùs
 */


public class PokemonParser {

    public static Pokemon parsePokemon(String json) {
        JSONObject obj = new JSONObject(json);

        int id = obj.getInt("id");
        String name = obj.getString("name");
        int height = obj.getInt("height");
        int weight = obj.getInt("weight");
        int baseExperience = obj.getInt("base_experience");

        // Abilities
        List<Ability> abilities = new ArrayList<>();
        JSONArray abilitiesArray = obj.getJSONArray("abilities");
        for (int i = 0; i < abilitiesArray.length(); i++) {
            JSONObject abilityObj = abilitiesArray.getJSONObject(i);
            String abilityName = abilityObj.getJSONObject("ability").getString("name");
            boolean isHidden = abilityObj.getBoolean("is_hidden");
            abilities.add(new Ability(abilityName, isHidden));
        }

        // Types
        List<Type> types = new ArrayList<>();
        JSONArray typesArray = obj.getJSONArray("types");
        for (int i = 0; i < typesArray.length(); i++) {
            JSONObject typeObj = typesArray.getJSONObject(i);
            String typeName = typeObj.getJSONObject("type").getString("name");
            int slot = typeObj.getInt("slot");
            types.add(new Type(typeName, slot));
        }

        // Stats
        List<Stat> stats = new ArrayList<>();
        JSONArray statsArray = obj.getJSONArray("stats");
        for (int i = 0; i < statsArray.length(); i++) {
            JSONObject statObj = statsArray.getJSONObject(i);
            String statName = statObj.getJSONObject("stat").getString("name");
            int baseValue = statObj.getInt("base_stat");
            stats.add(new Stat(statName, baseValue));
        }

        // Sprite (image)
        String imageUrl = obj.getJSONObject("sprites").getString("front_default");

        return new Pokemon(id, name, height, weight, baseExperience, abilities, types, stats, imageUrl);
    }
}