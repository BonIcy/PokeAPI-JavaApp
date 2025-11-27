/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pokeapi.api;

import pokeapi.model.Pokemon;
import pokeapi.parser.PokemonParser;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Jesùs Martpinez 
 */





public class PokemonAPIClient {

    private static final String BASE = "https://pokeapi.co/api/v2";
    private static final int CONNECT_TIMEOUT = 6000;
    private static final int READ_TIMEOUT = 8000;

    public PokemonAPIClient() {
    }


    public String getRandomPokemonData() {
        int randomId = (int) (Math.random() * 898) + 1;
        return httpGet(BASE + "/pokemon/" + randomId);
    }

    public String getPokemonByType(String type) {
        return httpGet(BASE + "/type/" + type.toLowerCase());
    }

    public String getPokemonByGeneration(int gen) {
        return httpGet(BASE + "/generation/" + gen);
    }


    public String getPokemonData(String nameOrId) {
        return httpGet(BASE + "/pokemon/" + nameOrId);
    }

    public List<Pokemon> getPokemonsByType(String type, int limit) {
        List<Pokemon> result = new ArrayList<>();
        try {
            String raw = httpGet(BASE + "/type/" + type.toLowerCase());
            if (raw == null || raw.isBlank()) return result;

            JSONObject root = new JSONObject(raw);
            JSONArray arr = root.optJSONArray("pokemon");
            if (arr == null) return result;

            int count = Math.min(limit, arr.length());

            for (int i = 0; i < count; i++) {
                JSONObject item = arr.getJSONObject(i);
                JSONObject poke = item.getJSONObject("pokemon");
                String name = poke.getString("name");

                String pjson = getPokemonData(name);
                if (pjson == null) continue;

                try {
                    Pokemon p = PokemonParser.parsePokemon(pjson);
                    if (p != null) result.add(p);
                } catch (Exception ignored) {}
            }
        } catch (Exception ignored) {}

        return result;
    }

    public List<Pokemon> getPokemonsByGeneration(int gen, int limit) {
        List<Pokemon> result = new ArrayList<>();
        try {
            String raw = httpGet(BASE + "/generation/" + gen);
            if (raw == null || raw.isBlank()) return result;

            JSONObject root = new JSONObject(raw);
            JSONArray arr = root.optJSONArray("pokemon_species");
            if (arr == null) return result;

            int count = Math.min(limit, arr.length());

            for (int i = 0; i < count; i++) {
                String name = arr.getJSONObject(i).getString("name");

                String pjson = getPokemonData(name);
                if (pjson == null) continue;

                try {
                    Pokemon p = PokemonParser.parsePokemon(pjson); 
                    if (p != null) result.add(p);
                } catch (Exception ignored) {}
            }

        } catch (Exception ignored) {}

        return result;
    }


    private String httpGet(String endpoint) {
        HttpURLConnection conn = null;
        BufferedReader reader = null;
        try {
            URL url = new URL(endpoint);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(CONNECT_TIMEOUT);
            conn.setReadTimeout(READ_TIMEOUT);
            conn.setRequestProperty("User-Agent", "PokeAPI-JavaApp/1.0");

            int code = conn.getResponseCode();
            InputStream is = (code >= 200 && code < 300) ? conn.getInputStream() : conn.getErrorStream();

            if (is == null) return null;

            reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null)
                sb.append(line).append("\n");

            return sb.toString();

        } catch (Exception e) {
            return null;
        } finally {
            try { if (reader != null) reader.close(); } catch (Exception ignored) {}
            if (conn != null) conn.disconnect();
        }
    }
}
