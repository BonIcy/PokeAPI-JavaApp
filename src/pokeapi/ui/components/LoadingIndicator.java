/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pokeapi.ui.components;

import javax.swing.*;
import java.awt.*;
/**
 *
 * @author Jesùs
 */


public class LoadingIndicator extends JPanel {
    private final Timer timer;
    private int step = 0;

    public LoadingIndicator() {
        setPreferredSize(new Dimension(120, 24));
        timer = new Timer(300, e -> {
            step = (step + 1) % 4;
            repaint();
        });
    }

    public void start() { timer.start(); }
    public void stop()  { timer.stop(); }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        int baseX = 10;
        for (int i = 0; i < 4; i++) {
            int h = (i == step) ? 12 : 6;
            g.fillRect(baseX + i * 20, (getHeight() - h) / 2, 10, h);
        }
    }
}
