package pokeapi.ui.windows;

import pokeapi.ui.theme.UIConstants;
import pokeapi.ui.theme.Colors;
import pokeapi.ui.theme.PixelFonts;

import javax.swing.*;
import java.awt.*;


public class AppFrame extends JFrame {
    private final ScreenManager screenManager;

    public AppFrame() {
        super("PokeApi UI");
        PixelFonts.loadFonts();
        initFrame();
        screenManager = new ScreenManager(this.getContentPane());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(UIConstants.APP_SIZE);
        setResizable(false);
        setLocationRelativeTo(null);
    }

    private void initFrame() {
        getContentPane().setBackground(Colors.POKEDEX_RED);
        getContentPane().setLayout(new BorderLayout());
        JPanel top = new JPanel();
        top.setPreferredSize(new Dimension(UIConstants.APP_SIZE.width, 48));
        top.setBackground(Colors.SHADOW_BLACK);
        JLabel title = new JLabel("POKEAPI UI");
        title.setForeground(Colors.PIXEL_YELLOW);
        title.setFont(PixelFonts.large());
        top.add(title);
        getContentPane().add(top, BorderLayout.NORTH);
    }

    public ScreenManager getScreenManager() {
        return screenManager;
    }

    public void showApp() {
        SwingUtilities.invokeLater(() -> {
            setVisible(true);
            screenManager.showMainMenu();
        });
    }
}
