/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pokeapi.ui.screens;


import pokeapi.ui.components.PixelButton;
import pokeapi.ui.components.PixelPanel;
import pokeapi.ui.components.PokemonCard;
import pokeapi.ui.data.PokemonProvider;
import pokeapi.ui.windows.ScreenManager;
import pokeapi.api.PokemonAPIClient;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import pokeapi.model.Pokemon;

/**
 *
 * @author Jesùs
 */

public class GenerationListScreen extends PixelPanel {

    private final ScreenManager manager;
    private final PokemonProvider provider = new PokemonProvider();

    private JPanel list;

    public GenerationListScreen(ScreenManager manager) {
        this.manager = manager;
        setLayout(new BorderLayout());
        init();
    }

    private void init() {
        JPanel top = new JPanel();
        top.setOpaque(false);

        PixelButton back = new PixelButton("⟵ Back");
        back.addActionListener(e -> manager.showGenerationSelector());
        top.add(back);

        add(top, BorderLayout.NORTH);

        list = new JPanel(new GridLayout(0, 1, 6, 6));
        list.setOpaque(false);

        JScrollPane scroll = new JScrollPane(list);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);

        add(scroll, BorderLayout.CENTER);
    }

    public void loadGeneration(int gen) {
        list.removeAll();
        var pokes = provider.getPokemonsByGeneration(gen);

        for (var p : pokes) {
            PokemonCard card = new PokemonCard(p);
            card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            card.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    manager.openDetail(p);
                }
            });

            list.add(card);
        }

        revalidate();
        repaint();
    }
}
