/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pokeapi.main;
import java.util.List;
import pokeapi.api.PokemonAPIClient;
import pokeapi.model.Pokemon;
import pokeapi.parser.PokemonParser;
import java.util.Scanner;
import org.json.JSONObject;
import org.json.JSONArray;

/**
 *
 * @author Jesùs
 */




public class Main {

    private static final String GREEN = "\u001B[32m";
    private static final String CYAN = "\u001B[36m";
    private static final String YELLOW = "\u001B[33m";
    private static final String RESET = "\u001B[0m";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        PokemonAPIClient client = new PokemonAPIClient();

        System.out.println(GREEN + "=== Welcome to the PokeAPI Java Client ===" + RESET);

        while (true) {
            System.out.println("\n" + CYAN + "Menu Options:" + RESET);
            System.out.println("1. Search Pokémon by name or ID");
            System.out.println("2. Get a random Pokémon");
            System.out.println("3. List Pokémon by type");
            System.out.println("4. List Pokémon by generation");
            System.out.println("5. Exit");
            System.out.print(YELLOW + "Choose an option: " + RESET);

            String option = sc.nextLine().trim();
            switch (option) {
                case "1" -> searchPokemon(client, sc);
                case "2" -> randomPokemon(client);
                case "3" -> listByType(client, sc);
                case "4" -> listByGeneration(client, sc);
                case "5" -> {
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void searchPokemon(PokemonAPIClient client, Scanner sc) {
        System.out.print("Enter Pokémon name or ID: ");
        String name = sc.nextLine();
        String response = client.getPokemonData(name);
        if (response.isEmpty()) {
            System.out.println("Pokémon not found.");
            return;
        }
        Pokemon p = PokemonParser.parsePokemon(response);
        printPokemonInfo(p);
    }

    private static void randomPokemon(PokemonAPIClient client) {
        String response = client.getPokemonData(String.valueOf((int)(Math.random()*800)+1));

        Pokemon p = PokemonParser.parsePokemon(response);
        printPokemonInfo(p);
    }

    private static void listByType(PokemonAPIClient client, Scanner sc) {
        System.out.print("Enter Pokémon type (e.g. fire, water): ");
        String type = sc.nextLine();
        List<Pokemon> response = client.getPokemonsByType(type, 20);
        JSONObject obj = new JSONObject(response);
        JSONArray pokemonList = obj.getJSONArray("pokemon");
        System.out.println("Pokémon of type '" + type + "':");
        for (int i = 0; i < Math.min(10, pokemonList.length()); i++) {
            String name = pokemonList.getJSONObject(i).getJSONObject("pokemon").getString("name");
            System.out.println("- " + name);
        }
    }

    private static void listByGeneration(PokemonAPIClient client, Scanner sc) {
        System.out.print("Enter generation number (1-9): ");
        int gen = sc.nextInt();
        sc.nextLine();
        List<Pokemon> response = client.getPokemonsByGeneration(gen, 20);

        JSONObject obj = new JSONObject(response);
        JSONArray species = obj.getJSONArray("pokemon_species");
        System.out.println("Pokémon from generation " + gen + ":");
        for (int i = 0; i < Math.min(10, species.length()); i++) {
            System.out.println("- " + species.getJSONObject(i).getString("name"));
        }
    }

    private static void printPokemonInfo(Pokemon p) {
        System.out.println("\n" + CYAN + "=== Pokémon Summary ===" + RESET);
        System.out.println(p);
        System.out.println("Height: " + p.getHeight());
        System.out.println("Weight: " + p.getWeight());
        System.out.println("Base Experience: " + p.getBaseExperience());
        System.out.println("Abilities: " + p.getAbilities());
        System.out.println("Types: " + p.getTypes());
        System.out.println("Stats:");
        p.getStats().forEach(stat -> System.out.println("  - " + stat));
        System.out.println("Image URL: " + p.getImageUrl());
    }
}