package pokeapi.ui;

import pokeapi.ui.windows.AppFrame;
import pokeapi.ui.theme.PixelFonts;
import javax.swing.SwingUtilities;


public class UIStarter {
    public static void main(String[] args) {
        PixelFonts.loadFonts();
        SwingUtilities.invokeLater(() -> {
            AppFrame frame = new AppFrame();
            frame.showApp();
        });
    }
}
