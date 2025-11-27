package pokeapi.ui.components;

import pokeapi.ui.theme.Colors;
import pokeapi.ui.theme.PixelFonts;
import pokeapi.ui.theme.UIConstants;

import javax.swing.*;
import java.awt.*;

public class PixelButton extends JButton {
    public PixelButton(String text) {
        super(text);
        setFont(PixelFonts.medium());
        setFocusable(false);
        setBorder(BorderFactory.createLineBorder(Colors.SHADOW_BLACK, UIConstants.BORDER_THICKNESS));
        setBackground(Colors.POKEDEX_RED);
        setForeground(Colors.PIXEL_YELLOW);
        setPreferredSize(new Dimension(160, 36));
        setOpaque(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        // disable anti-alias hints for pixel look
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
        super.paintComponent(g2);
        g2.dispose();
    }
}
