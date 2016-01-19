package models;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import net.beadsproject.beads.data.Sample;

public class MusicPlayer extends Instrument {

    public boolean songUploaded = false;
    public boolean playing = false;
    public Color color;

    public void play() {
        if (this.playing == false) {
            this.playing = true;
            startPlayBack();
        }
    }

    public void stop() {
        this.playing = false;
        stopPlayBack();
    }

    public void openRecording() {
        if (this.playing == false) {
            JFileChooser fileChooser = new JFileChooser("C:\\Users\\Lucas\\Documents\\NetBeansProjects\\CapstoneProject\\saved music");
            if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try {
                    this.sample = new Sample(file.getAbsolutePath());
                    this.songUploaded = true;
                } catch (IOException ex) {
                    Logger.getLogger(MusicPlayer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @Override
    public void calculateFrequency() {
        //Ignore this - it has to be overwritten in order to inherit from Instrument,
        //but it is not needed for this class.
    }
}
