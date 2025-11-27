/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pokeapi.ui.components;

import pokeapi.ui.theme.PixelFonts;
import java.awt.Font;
import javax.swing.JLabel;

/**
 *
 * @author Jesùs
 */


/**
 * JLabel con fuente pixel.
 */
public class PixelLabel extends JLabel {
    public PixelLabel(String text) {
        super(text);
        setFont(PixelFonts.medium());
    }

    public PixelLabel(String text, Font f) {
        super(text);
        setFont(f);
    }
}
