/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package pokeapi.api;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;
import pokeapi.utils.CacheManager;
/**
 *
 * @author Jesùs Martpinez 
 */


public class PokemonAPIClient {
    private static final String BASE_URL = "https://pokeapi.co/api/v2/";
    private static final int MAX_POKEMON = 1025; 

    private String fetchData(String endpoint) {
        if (CacheManager.contains(endpoint)) {
            System.out.println("[CACHE] Using cached data for: " + endpoint);
            return CacheManager.get(endpoint);
        }

        StringBuilder response = new StringBuilder();

        try {
            URL url = new URL(endpoint);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream())
                );
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                CacheManager.save(endpoint, response.toString()); 
            } else {
                System.out.println("HTTP error: " + responseCode);
            }

        } catch (Exception e) {
            System.out.println("Connection error: " + e.getMessage());
        }

        return response.toString();
    }


    public String getPokemonData(String nameOrId) {
        return fetchData(BASE_URL + "pokemon/" + nameOrId.toLowerCase());
    }


    public String getRandomPokemonData() {
        int randomId = new Random().nextInt(MAX_POKEMON) + 1;
        System.out.println("Fetching random Pokémon (ID: " + randomId + ")");
        return getPokemonData(String.valueOf(randomId));
    }


    public String getPokemonByType(String type) {
        return fetchData(BASE_URL + "type/" + type.toLowerCase());
    }


    public String getPokemonByGeneration(int generationId) {
        return fetchData(BASE_URL + "generation/" + generationId);
    }
}