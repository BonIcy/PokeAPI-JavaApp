/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pokeapi.ui.components;

import pokeapi.ui.theme.PixelFonts;
import pokeapi.ui.theme.Colors;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Jesùs
 */


public class PixelTextField extends JTextField {
    public PixelTextField(int columns) {
        super(columns);
        setFont(PixelFonts.medium());
        setBackground(Colors.PANEL_BG);
        setForeground(Colors.TEXT);
        setBorder(BorderFactory.createLineBorder(Colors.SHADOW_BLACK, 3));
        setOpaque(true);
    }
}
