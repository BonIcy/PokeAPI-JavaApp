/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pokeapi.ui.screens;

import pokeapi.ui.data.PokemonProvider;
import pokeapi.ui.components.PokemonCard;
import pokeapi.ui.components.PixelButton;
import pokeapi.ui.components.PixelPanel;
import pokeapi.ui.windows.ScreenManager;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Jesùs
 */


public class RandomPokemonScreen extends PixelPanel {

    private final ScreenManager manager;
    private final PokemonProvider provider = new PokemonProvider();

    private JPanel container;

    public RandomPokemonScreen(ScreenManager manager) {
        this.manager = manager;
        setLayout(new BorderLayout());
        init();
    }

    private void init() {

        JPanel top = new JPanel();
        top.setOpaque(false);

        PixelButton back = new PixelButton("⟵ Back");
        back.addActionListener(e -> manager.showMainMenu());
        top.add(back);

        add(top, BorderLayout.NORTH);

        container = new JPanel();
        container.setOpaque(false);
        add(container, BorderLayout.CENTER);
    }

    public void reload() {
        container.removeAll();
        var p = provider.getRandomPokemon();

        PokemonCard card = new PokemonCard(p);
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        card.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                manager.openDetail(p);
            }
        });

        container.add(card);
        revalidate();
        repaint();
    }
}

