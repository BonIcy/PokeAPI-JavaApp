/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pokeapi.model;
import java.util.List;
/**
 *
 * @author Jesùs
 */



public class Pokemon {
    private int id;
    private String name;
    private int height;
    private int weight;
    private int baseExperience;
    private List<Ability> abilities;
    private List<Type> types;
    private List<Stat> stats;
    private String imageUrl;

    public Pokemon(int id, String name, int height, int weight, int baseExperience,
                   List<Ability> abilities, List<Type> types, List<Stat> stats, String imageUrl) {
        this.id = id;
        this.name = name;
        this.height = height;
        this.weight = weight;
        this.baseExperience = baseExperience;
        this.abilities = abilities;
        this.types = types;
        this.stats = stats;
        this.imageUrl = imageUrl;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getHeight() { return height; }
    public int getWeight() { return weight; }
    public int getBaseExperience() { return baseExperience; }
    public List<Ability> getAbilities() { return abilities; }
    public List<Type> getTypes() { return types; }
    public List<Stat> getStats() { return stats; }
    public String getImageUrl() { return imageUrl; }

    @Override
    public String toString() {
        return "Pokémon #" + id + " - " + name.toUpperCase();
    }
}