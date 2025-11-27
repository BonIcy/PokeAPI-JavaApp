/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pokeapi.ui.theme;

import javax.sound.sampled.*;
import java.io.InputStream;

/**
 *
 * @author Jesùs
 */



public final class SoundPlayer {
    private SoundPlayer(){}

    public static void play(String resourcePath) {
        try (InputStream is = SoundPlayer.class.getResourceAsStream(resourcePath)) {
            if (is == null) return;
            AudioInputStream ais = AudioSystem.getAudioInputStream(is);
            Clip clip = AudioSystem.getClip();
            clip.open(ais);
            clip.start();
        } catch (Exception e) {
        }
    }
}
