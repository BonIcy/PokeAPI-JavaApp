/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pokeapi.ui.components;

import pokeapi.model.Pokemon;
import pokeapi.ui.theme.PixelFonts;
import javax.swing.*;
import java.awt.*;
import java.net.URL;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

/**
 *
 * @author Jesùs
 */



public class PokemonCard extends JPanel {
    private final Pokemon pokemon;
    private BufferedImage sprite;

    public PokemonCard(Pokemon p) {
        this.pokemon = p;
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(220, 70));
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        setBackground(Color.WHITE);
        loadSprite();
        initContent();
    }

    private void loadSprite() {
        try {
            String url = pokemon.getImageUrl();
            if (url != null && !url.isBlank()) {
                sprite = ImageIO.read(new URL(url));
            }
        } catch (Exception e) {
            sprite = null;
        }
    }

    private void initContent() {
        JLabel name = new JLabel("#" + pokemon.getId() + " " + pokemon.getName());
        name.setFont(PixelFonts.small());
        add(name, BorderLayout.CENTER);

        if (sprite != null) {
            ImageIcon ic = new ImageIcon(sprite.getScaledInstance(64, 64, Image.SCALE_FAST));
            JLabel img = new JLabel(ic);
            add(img, BorderLayout.WEST);
        }
    }

    public Pokemon getPokemon() {
        return pokemon;
    }
}
