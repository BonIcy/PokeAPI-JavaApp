package pokeapi.ui.screens;

import pokeapi.ui.windows.ScreenManager;
import pokeapi.ui.components.PixelPanel;
import pokeapi.ui.components.PixelButton;
import pokeapi.ui.theme.SoundPlayer;
import pokeapi.ui.data.PokemonProvider;
import javax.swing.*;
import java.awt.*;

public class MainMenuScreen extends PixelPanel {
    private final ScreenManager manager;

    public MainMenuScreen(ScreenManager manager) {
        this.manager = manager;
        setLayout(new GridBagLayout());
        init();
    }

    private void init() {
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(8, 8, 8, 8);
        c.gridx = 0;
        c.gridy = 0;

        PixelButton bSearch = new PixelButton("Search Pokemon");
        bSearch.addActionListener(e -> {
            SoundPlayer.play("/ui/assets/sounds/click.wav");
            manager.showSearch();
        });
        add(bSearch, c);


        c.gridy++;
        PixelButton bRandom = new PixelButton("Random Pokemon");
        bRandom.addActionListener(e -> {
            SoundPlayer.play("/ui/assets/sounds/click.wav");
            manager.showRandomPokemon();
        });
        add(bRandom, c);   


        c.gridy++;
        PixelButton bByType = new PixelButton("List by type");
        bByType.addActionListener(e -> manager.showTypeList());
        add(bByType, c);


        c.gridy++;
        PixelButton bByGen = new PixelButton("List by Generation");
        bByGen.addActionListener(e -> {
            SoundPlayer.play("/ui/assets/sounds/click.wav");
            manager.showGenerationSelector();
        });
        add(bByGen, c);  

        // ----------------------------------------------------
        c.gridy++;
        PixelButton bExit = new PixelButton("Exit");
        bExit.addActionListener(e -> System.exit(0));
        add(bExit, c);
    }
}
