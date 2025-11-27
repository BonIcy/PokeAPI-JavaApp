/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pokeapi.ui.screens;

import pokeapi.ui.windows.ScreenManager;
import pokeapi.ui.components.PixelPanel;
import pokeapi.ui.components.PixelButton;
import pokeapi.ui.theme.PixelFonts;
import pokeapi.model.Pokemon;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;


/**
 *
 * @author Jesùs
 */


public class PokemonDetailScreen extends PixelPanel {
    private final ScreenManager manager;
    private final JPanel content;
    private Pokemon current;

    public PokemonDetailScreen(ScreenManager manager) {
        this.manager = manager;
        setLayout(new BorderLayout());
        content = new JPanel(new BorderLayout());
        add(content, BorderLayout.CENTER);
        initTop();
    }

    private void initTop() {
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        PixelButton back = new PixelButton("Volver");
        back.addActionListener(e -> manager.showMainMenu());
        top.add(back);
        add(top, BorderLayout.NORTH);
    }

    public void setPokemon(Pokemon p) {
        this.current = p;
        render();
    }

    private void render() {
        content.removeAll();
        if (current == null) {
            content.add(new JLabel("Not pokemon selected."), BorderLayout.CENTER);
            revalidate(); repaint();
            return;
        }

        JPanel left = new JPanel(new BorderLayout());
        left.setPreferredSize(new Dimension(320, 400));
        try {
            String imgUrl = current.getImageUrl();
            if (imgUrl != null) {
                BufferedImage img = ImageIO.read(new URL(imgUrl));
                JLabel pic = new JLabel(new ImageIcon(img.getScaledInstance(256, 256, Image.SCALE_FAST)));
                left.add(pic, BorderLayout.NORTH);
            }
        } catch (Exception e) {
            // ignore
        }

        JLabel name = new JLabel(current.getName() + "  #" + current.getId());
        name.setFont(PixelFonts.large());
        left.add(name, BorderLayout.SOUTH);

        JPanel right = new JPanel();
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
        right.add(new JLabel("Height: " + current.getHeight()));
        right.add(new JLabel("Weight: " + current.getWeight()));
        right.add(new JLabel("Base XP: " + current.getBaseExperience()));

        right.add(new JLabel("Types:"));
        current.getTypes().forEach(t -> right.add(new JLabel("- " + t.getName())));

        right.add(new JLabel("Abilities:"));
        current.getAbilities().forEach(a -> right.add(new JLabel("- " + a.getName() + (a.isHidden() ? " (Hidden)" : ""))));

        right.add(new JLabel("Stats:"));
        current.getStats().forEach(s -> {
            JPanel row = new JPanel(new BorderLayout());
            row.add(new JLabel(s.getName()), BorderLayout.WEST);
            JProgressBar bar = new JProgressBar(0, 255);
            bar.setValue(s.getBaseValue());
            bar.setStringPainted(true);
            row.add(bar, BorderLayout.CENTER);
            right.add(row);
        });

        content.add(left, BorderLayout.WEST);
        content.add(new JScrollPane(right), BorderLayout.CENTER);

        revalidate();
        repaint();
    }
}
