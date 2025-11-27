/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package pokeapi.ui.screens;

import pokeapi.ui.windows.ScreenManager;
import pokeapi.ui.components.PixelPanel;
import pokeapi.ui.components.PixelButton;
import pokeapi.ui.components.PokemonCard;
import pokeapi.api.PokemonAPIClient;
import pokeapi.model.Pokemon;

import javax.swing.*;
import java.awt.*;
import java.util.List;


/**
 *
 * @author Jesùs
 */




public class TypeListScreen extends PixelPanel {

    private final ScreenManager manager;
    private final PokemonAPIClient client = new PokemonAPIClient();
    private final JPanel listPanel;

    private String currentType = null;
    private int currentLimit = 20;
    private final int PAGE_SIZE = 20;

    private static final String[] TYPES = {
            "normal","fire","water","grass","electric","ice",
            "fighting","poison","ground","flying","psychic",
            "bug","rock","ghost","dragon","dark","steel","fairy"
    };

    public TypeListScreen(ScreenManager manager) {
        this.manager = manager;
        setLayout(new BorderLayout());
        initTop();
        listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        add(new JScrollPane(listPanel), BorderLayout.CENTER);
    }

    private void initTop() {
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        PixelButton back = new PixelButton("⟵ Back");
        back.addActionListener(e -> manager.showMainMenu());
        top.add(back);

        JPanel typeButtons = new JPanel(new GridLayout(0, 3, 8, 8)); 

        for (String type : TYPES) {
            ImageIcon icon = loadTypeIcon(type);

            PixelButton b = new PixelButton(type.substring(0,1).toUpperCase() + type.substring(1));
            if (icon != null) {
                b.setIcon(icon);
                b.setHorizontalTextPosition(SwingConstants.RIGHT); 
                b.setIconTextGap(10);
            }

            b.addActionListener(e -> {
                currentType = type;
                currentLimit = PAGE_SIZE;
                loadType(type, currentLimit);
            });

            typeButtons.add(b);
        }

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.add(top, BorderLayout.NORTH);
        wrapper.add(typeButtons, BorderLayout.CENTER);

        add(wrapper, BorderLayout.NORTH);
    }


    private ImageIcon loadTypeIcon(String type) {
        try {
            java.net.URL url = getClass().getClassLoader()
                    .getResource("resources/icons/types/" + type + ".png");

            if (url == null)
                return null;
            Image img = new ImageIcon(url).getImage()
                    .getScaledInstance(26, 26, Image.SCALE_SMOOTH); 
            return new ImageIcon(img);

        } catch (Exception ex) {
            return null;
        }
    }

    private void loadType(String type, int limit) {
        listPanel.removeAll();
        listPanel.add(new JLabel("Loading type " + type + " ..."));
        revalidate(); repaint();

        SwingWorker<List<Pokemon>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<Pokemon> doInBackground() throws Exception {
                return client.getPokemonsByType(type, limit);
            }

            @Override
            protected void done() {
                listPanel.removeAll();
                try {
                    List<Pokemon> pokes = get();
                    if (pokes == null || pokes.isEmpty()) {
                        listPanel.add(new JLabel("Not found for : " + type));
                    } else {
                        for (Pokemon p : pokes) {
                            PokemonCard card = new PokemonCard(p);
                            card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

                            card.addMouseListener(new java.awt.event.MouseAdapter() {
                                @Override
                                public void mouseClicked(java.awt.event.MouseEvent e) {
                                    manager.openDetail(p);
                                }
                            });

                            listPanel.add(card);
                        }

                        if (pokes.size() >= limit) {
                            PixelButton more = new PixelButton("More...");
                            more.addActionListener(ev -> {
                                currentLimit += PAGE_SIZE;
                                loadType(currentType, currentLimit);
                            });
                            JPanel morePanel = new JPanel();
                            morePanel.add(more);
                            listPanel.add(morePanel);
                        }
                    }
                } catch (Exception ex) {
                    listPanel.add(new JLabel("Error: " + ex.getMessage()));
                } finally {
                    revalidate(); repaint();
                }
            }
        };
        worker.execute();
    }
}
