package pokeapi.ui.components;

import pokeapi.ui.theme.Colors;
import pokeapi.ui.theme.UIConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;

public class PixelPanel extends JPanel {
    private BufferedImage bg;

    public PixelPanel() {
        setBackground(Colors.SCREEN_GREEN);
        setBorder(BorderFactory.createLineBorder(Colors.SHADOW_BLACK, UIConstants.BORDER_THICKNESS));
        loadBackground();
    }

    private void loadBackground() {
        try {
            bg = ImageIO.read(getClass().getResourceAsStream(UIConstants.ASSETS_PATH + "ui/screen_texture.png"));
        } catch (Exception e) {
            bg = null;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (bg != null) {
            // tile the background for pixel feel
            for (int x = 0; x < getWidth(); x += bg.getWidth()) {
                for (int y = 0; y < getHeight(); y += bg.getHeight()) {
                    g.drawImage(bg, x, y, null);
                }
            }
        } else {
            super.paintComponent(g);
        }
    }
}
