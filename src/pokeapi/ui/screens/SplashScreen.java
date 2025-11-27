/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pokeapi.ui.screens;

import pokeapi.ui.windows.ScreenManager;
import pokeapi.ui.components.PixelPanel;
import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Jesùs
 */


public class SplashScreen extends PixelPanel {
    private final ScreenManager manager;

    public SplashScreen(ScreenManager manager) {
        this.manager = manager;
        setLayout(new GridBagLayout());
        JLabel label = new JLabel("POKEDEX");
        label.setFont(getFont().deriveFont(32f));
        add(label);
        // auto-switch after 1.2s
        Timer t = new Timer(1200, e -> manager.showMainMenu());
        t.setRepeats(false);
        t.start();
    }
}
