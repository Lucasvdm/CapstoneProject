package viewcontroller;

import java.awt.Color;
import java.io.IOException;
import java.awt.image.*;
import javax.swing.ImageIcon;

/**
 * Write methods in this class for displaying data in the DrawnView.
 *
 * You can use all the public instance variables you defined in AllModelsForView
 * and DrawnView as though they were part of this class! This is due to the
 * magic of subclassing (i.e. using the extends keyword).
 *
 * The methods for displaying data in the DrawnView are written as methods in
 * this class.
 *
 * Make sure to use these methods in the ViewUserActions class though, or else
 * they will be defined but never used!
 *
 * @author cheng
 */
public class ViewOutputs extends DrawnView {

    public void updateColourWheelSound() {
        if (colourWheel.getColour() != null) {
            colourWheel.startSound();
        } else {
            colourWheel.stopSound();
        }
    }

    public void updateThereminSound() {
        if (theremin.getOn() == true) {
            theremin.startSound();
        } else {
            theremin.stopSound();
        }
    }

    public void updateColourDisplay() {
        Color colour = colourWheel.getColour();
        if (colour != null) {
            BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
            for (int r = 0; r < 100; r++) {
                for (int c = 0; c < 100; c++) {
                    image.setRGB(r, c, colour.getRGB());
                }
            }
            ImageIcon icon = new ImageIcon(image);
            colourDisplayLabel.setIcon(icon);
        } else {
            BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
            for (int r = 0; r < 100; r++) {
                for (int c = 0; c < 100; c++) {
                    Color backgroundColour = new Color(255 - 224, 255 - 224, 255 - 224);
                    image.setRGB(r, c, backgroundColour.getRGB());
                }
            }
            ImageIcon icon = new ImageIcon(image);
            colourDisplayLabel.setIcon(icon);
        }
    }

    public void updateColourWheel() throws IOException {
        wheelLabel.setIcon(colourWheel.wheelShape);
    }

    public void updateColourWheelVolumeLabel() {
        colourWheelVolumeLabel.setText("" + (int) (colourWheel.getVolume() * 100) + "%");
    }

    public void updateThereminVolume(int prox) {
        thereminVolumeLabel.setText("" + (int) (theremin.getVolume() * 100) + "%");
        thereminDepthLabel.setIcon(theremin.volumeImage);
    }

    public void recordColourWheel(boolean toggled) {
        if (toggled) {
            colourWheel.startRecording();
        } else {
            colourWheel.stopRecording();
        }
    }

    public void recordTheremin(boolean toggled) {
        if (toggled) {
            theremin.startRecording();
        } else {
            theremin.stopRecording();
        }
    }

    public void saveColourWheel() {
        colourWheel.saveMusic();
    }

    public void saveTheremin() {
        theremin.saveMusic();
    }

    public void playBackColourWheel(boolean toggled) {
        if (toggled) {
            colourWheel.startPlayBack();
        } else {
            colourWheel.stopPlayBack();
        }
    }

    public void playBackTheremin(boolean toggled) {
        if (toggled) {
            theremin.startPlayBack();
        } else {
            theremin.stopPlayBack();
        }
    }

    public void uploadSong() {
        musicPlayer.openRecording();
    }

    public void play() {
        musicPlayer.play();
    }

    public void stop() {
        musicPlayer.stop();
    }

    public void updateSongLabel() {
        uploadSongLabel.setText("Song uploaded!");
    }
}
