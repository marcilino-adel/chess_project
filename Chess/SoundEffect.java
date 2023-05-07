package Chess;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class SoundEffect {
    private Clip clip;

    public SoundEffect(String fileName) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("Chess/Media/Sounds/" + fileName));
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (Exception ignored) {}
    }

    public void play() {
        if (clip.isRunning()) {
            clip.stop();
        }
        clip.setFramePosition(0);
        clip.start();
    }
}