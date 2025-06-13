package util;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class SoundManager {
    public static void playCorrect() {
        playSound("/util/sounds/correct.wav");
    }
    public static void playWrong() {
        playSound("/util/sounds/wrong.wav");
    }
    private static void playSound(String path) {
        try {
            URL url = SoundManager.class.getResource(path);
            if (url == null) return;
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
} 