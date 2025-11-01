/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pokeapi.model;

/**
 *
 * @author Jesùs
 */

public class Ability {
    private String name;
    private boolean isHidden;

    public Ability(String name, boolean isHidden) {
        this.name = name;
        this.isHidden = isHidden;
    }

    public String getName() {
        return name;
    }

    public boolean isHidden() {
        return isHidden;
    }

    @Override
    public String toString() {
        return name + (isHidden ? " (Hidden)" : "");
    }
}
