/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pokeapi.model;

/**
 *
 * @author Jesùs
 */

public class Stat {
    private String name;
    private int baseValue;

    public Stat(String name, int baseValue) {
        this.name = name;
        this.baseValue = baseValue;
    }

    public String getName() {
        return name;
    }

    public int getBaseValue() {
        return baseValue;
    }

    @Override
    public String toString() {
        return name + ": " + baseValue;
    }
}

