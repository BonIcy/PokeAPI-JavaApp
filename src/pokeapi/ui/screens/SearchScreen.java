/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pokeapi.ui.screens;

import pokeapi.ui.windows.ScreenManager;
import pokeapi.ui.components.PixelPanel;
import pokeapi.ui.components.PixelButton;
import pokeapi.ui.components.PixelTextField;
import pokeapi.ui.components.LoadingIndicator;
import pokeapi.ui.data.PokemonProvider;
import pokeapi.model.Pokemon;
import java.awt.*;
import javax.swing.*;


/**
 *
 * @author Jesùs
 */

public class SearchScreen extends PixelPanel {
    private final ScreenManager manager;
    private final PokemonProvider provider = new PokemonProvider();

    public SearchScreen(ScreenManager manager) {
        this.manager = manager;
        setLayout(new BorderLayout());
        init();
    }

    private void init() {
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        PixelTextField input = new PixelTextField(18);
        PixelButton btnSearch = new PixelButton("Search");
        PixelButton btnBack = new PixelButton("Back");

        top.add(new JLabel("Name / ID:"));
        top.add(input);
        top.add(btnSearch);
        top.add(btnBack);
        add(top, BorderLayout.NORTH);

        JPanel center = new JPanel(new BorderLayout());
        center.add(new JLabel("Result:"), BorderLayout.NORTH);
        JTextArea resultArea = new JTextArea();
        resultArea.setEditable(false);
        center.add(new JScrollPane(resultArea), BorderLayout.CENTER);

        LoadingIndicator loader = new LoadingIndicator();
        center.add(loader, BorderLayout.SOUTH);

        add(center, BorderLayout.CENTER);

        btnBack.addActionListener(e -> manager.showMainMenu());

        btnSearch.addActionListener(e -> {
            String q = input.getText().trim();
            if (q.isEmpty()) return;
            loader.start();
            resultArea.setText("");
            // background fetch
            SwingWorker<Pokemon, Void> worker = new SwingWorker<>() {
                @Override
                protected Pokemon doInBackground() {
                    return provider.getPokemon(q.toLowerCase());
                }

                @Override
                protected void done() {
                    loader.stop();
                    try {
                        Pokemon p = get();
                        if (p == null) {
                            resultArea.setText("Not found or error.");
                        } else {
                            resultArea.setText(formatPokemon(p));
                            // set data for detail screen and show
                            PokemonDetailScreen detail = (PokemonDetailScreen) findScreen(manager, "DETAIL");
                            if (detail != null) {
                                detail.setPokemon(p);
                                manager.showDetail();
                            }
                        }
                    } catch (Exception ex) {
                        resultArea.setText("Error: " + ex.getMessage());
                    }
                }
            };
            worker.execute();
        });
    }

    private Component findScreen(ScreenManager manager, String card) {
        for (Component c : manager.getCurrentPanel().getParent().getComponents()) {
            if (c instanceof pokeapi.ui.screens.PokemonDetailScreen) {
                return c;
            }
        }
        return null;
    }

    private String formatPokemon(Pokemon p) {
        StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(p.getId()).append("\n");
        sb.append("Name: ").append(p.getName()).append("\n");
        sb.append("Height: ").append(p.getHeight()).append("\n");
        sb.append("Weight: ").append(p.getWeight()).append("\n");
        sb.append("Base XP: ").append(p.getBaseExperience()).append("\n");
        sb.append("Types: ");
        p.getTypes().forEach(t -> sb.append(t.getName()).append(" "));
        sb.append("\nAbilities:\n");
        p.getAbilities().forEach(a -> sb.append("- ").append(a.getName()).append(a.isHidden() ? " (Hidden)" : "").append("\n"));
        sb.append("\nStats:\n");
        p.getStats().forEach(s -> sb.append(s.getName()).append(": ").append(s.getBaseValue()).append("\n"));
        return sb.toString();
    }
}
