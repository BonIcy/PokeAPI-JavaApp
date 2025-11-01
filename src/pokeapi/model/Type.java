/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pokeapi.model;

/**
 *
 * @author Jesùs
 */

public class Type {
    private String name;
    private int slot;

    public Type(String name, int slot) {
        this.name = name;
        this.slot = slot;
    }

    public String getName() {
        return name;
    }

    public int getSlot() {
        return slot;
    }

    @Override
    public String toString() {
        return name;
    }
}

