/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pokeapi.ui.screens;

import pokeapi.ui.components.PixelButton;
import pokeapi.ui.components.PixelPanel;
import pokeapi.ui.windows.ScreenManager;

import javax.swing.*;
import java.awt.*;


/**
 *
 * @author Jesùs
 */

public class GenerationSelectorScreen extends PixelPanel {

    private final ScreenManager manager;

    public GenerationSelectorScreen(ScreenManager manager) {
        this.manager = manager;
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Select a gen:");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(title.getFont().deriveFont(22f));
        add(title, BorderLayout.NORTH);

        JPanel grid = new JPanel(new GridLayout(3, 3, 10, 10));
        grid.setOpaque(false);

        for (int gen = 1; gen <= 9; gen++) {
            PixelButton b = new PixelButton("Generation " + gen);
            int g = gen;
            b.addActionListener(e -> manager.showGenerationList(g));
            grid.add(b);
        }

        add(grid, BorderLayout.CENTER);

        PixelButton back = new PixelButton("⟵ Back");
        back.addActionListener(e -> manager.showMainMenu());
        JPanel bottom = new JPanel();
        bottom.add(back);

        add(bottom, BorderLayout.SOUTH);
    }
}
