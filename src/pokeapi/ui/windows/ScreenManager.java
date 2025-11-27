package pokeapi.ui.windows;

import pokeapi.ui.screens.*;
import pokeapi.ui.components.PixelPanel;
import pokeapi.ui.screens.SplashScreen;
import pokeapi.ui.screens.RandomPokemonScreen;
import pokeapi.ui.screens.GenerationSelectorScreen;
import pokeapi.ui.screens.GenerationListScreen;
import javax.swing.*;
import java.awt.*;
import pokeapi.api.PokemonAPIClient;
import pokeapi.model.Pokemon;

public class ScreenManager {
    private final JPanel container;
    private final CardLayout layout;
    private GenerationListScreen generationListScreen;
    private RandomPokemonScreen randomPokemonScreen;
    private GenerationSelectorScreen generationSelectorScreen;

    public static final String CARD_MAIN_MENU = "MAIN_MENU";
    public static final String CARD_SEARCH = "SEARCH";
    public static final String CARD_DETAIL = "DETAIL";
    public static final String CARD_TYPE = "TYPE";
    public static final String CARD_GEN = "GEN";
    public static final String CARD_SPLASH = "SPLASH";

    public ScreenManager(Container parent) {
        this.container = new JPanel();
        this.layout = new CardLayout();
        container.setLayout(layout);
        container.setBackground(null);
        parent.add(container, BorderLayout.CENTER);

        addScreens();
    }

    private void addScreens() {

        container.add(new MainMenuScreen(this), CARD_MAIN_MENU);
        container.add(new SearchScreen(this), CARD_SEARCH);
        container.add(new PokemonDetailScreen(this), CARD_DETAIL);
        container.add(new TypeListScreen(this), CARD_TYPE);

        generationListScreen = new GenerationListScreen(this);
        container.add(generationListScreen, CARD_GEN);

        randomPokemonScreen = new RandomPokemonScreen(this);
        container.add(randomPokemonScreen, "RANDOM");

        generationSelectorScreen = new GenerationSelectorScreen(this);
        container.add(generationSelectorScreen, "GEN_SELECT");

        container.add(new SplashScreen(this), CARD_SPLASH);
    }
    public void openDetail(Pokemon p) {
    for (Component c : container.getComponents()) {
        if (c instanceof PokemonDetailScreen detail) {
            detail.setPokemon(p);
            layout.show(container, CARD_DETAIL);
            return;
        }
    }
}

    public void showMainMenu() {
        layout.show(container, CARD_MAIN_MENU);
    }

    public void showSearch() {
        layout.show(container, CARD_SEARCH);
    }

    public void showDetail() {
        layout.show(container, CARD_DETAIL);
    }

    public void showTypeList() {
        layout.show(container, CARD_TYPE);
    }

    public void showGenerationList() {
        layout.show(container, CARD_GEN);
    }

    public void showSplash() {
        layout.show(container, CARD_SPLASH);
    }
    public void showRandomPokemon() {
        randomPokemonScreen.reload();
        layout.show(container, "RANDOM");
    }

    public void showGenerationSelector() {
        layout.show(container, "GEN_SELECT");
    }

    public void showGenerationList(int gen) {
        generationListScreen.loadGeneration(gen);
        layout.show(container, CARD_GEN);
    }


    public Component getCurrentPanel() {
        for (Component c : container.getComponents()) {
            if (c.isVisible()) return c;
        }
        return null;
    }
}
