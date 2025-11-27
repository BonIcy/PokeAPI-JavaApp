package pokeapi.ui.theme;

import java.awt.Font;
import java.io.InputStream;
import java.awt.GraphicsEnvironment;

public final class PixelFonts {
    private PixelFonts(){}

    private static Font pixelSmall;
    private static Font pixelMedium;
    private static Font pixelLarge;

    public static void loadFonts() {
        if (pixelSmall != null) return;
        try {

            InputStream is = PixelFonts.class.getResourceAsStream("/ui/assets/fonts/PixelFont.ttf");
            if (is == null) is = PixelFonts.class.getResourceAsStream("/ui/assets/fonts/PressStart2P.ttf");
            if (is != null) {
                Font base = Font.createFont(Font.TRUETYPE_FONT, is);
                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                ge.registerFont(base);
                pixelSmall = base.deriveFont(Font.PLAIN, 12f);
                pixelMedium = base.deriveFont(Font.PLAIN, 16f);
                pixelLarge = base.deriveFont(Font.BOLD, 22f);
            } else {
                pixelSmall = new Font(Font.MONOSPACED, Font.PLAIN, 12);
                pixelMedium = new Font(Font.MONOSPACED, Font.PLAIN, 16);
                pixelLarge = new Font(Font.MONOSPACED, Font.BOLD, 20);
            }
        } catch (Exception e) {
            pixelSmall = new Font(Font.MONOSPACED, Font.PLAIN, 12);
            pixelMedium = new Font(Font.MONOSPACED, Font.PLAIN, 16);
            pixelLarge = new Font(Font.MONOSPACED, Font.BOLD, 20);
        }
    }

    public static Font small() { loadFonts(); return pixelSmall; }
    public static Font medium(){ loadFonts(); return pixelMedium; }
    public static Font large() { loadFonts(); return pixelLarge; }
}
