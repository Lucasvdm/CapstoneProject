package models;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import net.beadsproject.beads.core.AudioContext;
import net.beadsproject.beads.data.Buffer;
import net.beadsproject.beads.data.Sample;
import net.beadsproject.beads.data.audiofile.AudioFileType;
import net.beadsproject.beads.ugens.Gain;
import net.beadsproject.beads.ugens.Glide;
import net.beadsproject.beads.ugens.RecordToSample;
import net.beadsproject.beads.ugens.SamplePlayer;
import net.beadsproject.beads.ugens.WavePlayer;

public abstract class Instrument {

    private float volume = (float) 0.5;
    public int frequency = 0;
    public AudioContext ac = new AudioContext();
    public WavePlayer wp;
    public Gain g;
    public Glide frequencyGlide;
    public Glide volumeGlide;
    public Sample sample;
    public RecordToSample rts;
    public SamplePlayer sp;
    private boolean running = false;
    private boolean recording = false;
    private boolean playback = false;
    private boolean startedRecording = false;

    public void startRecording() {
        if (this.sample != null) {
            this.sample.clear();
            this.sample = null;
        }
        this.sample = new Sample(60000d);
        this.rts = new RecordToSample(this.ac, sample, RecordToSample.Mode.INFINITE);
        rts.addInput(ac.out);
        ac.out.addDependent(rts);
        rts.pause(true);
        this.recording = true;
    }

    public void stopRecording() {
        if (this.recording == true) {
            this.rts.pause(true);
            this.rts.clip();
            this.sample = rts.getSample();
            this.rts.kill();
            this.recording = false;
            this.startedRecording = false;
        }
    }

    public void startPlayBack() {
        this.sp = new SamplePlayer(this.ac, this.sample);
        this.g = new Gain(this.ac, 1, 0.5f);
        this.g.addInput(this.sp);
        this.ac.out.addInput(this.g);
        this.ac.start();
        this.sp.setKillOnEnd(true);
        this.sp.start();
        this.playback = true;
    }

    public void stopPlayBack() {
        this.sp.kill();
        this.g.kill();
        this.ac.stop();
        this.playback = false;
    }

    public void saveMusic() {
        if (this.recording == false) {
            JFileChooser fileChooser = new JFileChooser("C:\\Users\\Lucas\\Documents\\NetBeansProjects\\CapstoneProject\\saved music");
            if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try {
                    this.sample.write(file.getAbsolutePath() + ".wav", AudioFileType.WAV);
                } catch (Exception ex) {
                    Logger.getLogger(Instrument.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public abstract void calculateFrequency();

    public void startSound() {
        if (recording && !startedRecording) {
            this.rts.start();
            this.startedRecording = true;
        }
        if (!playback) {
            if (running) {
                calculateFrequency();
                this.frequencyGlide.setValue(this.frequency);
                this.volumeGlide.setValue(this.volume);
            } else {
                calculateFrequency();
                this.frequencyGlide = new Glide(this.ac, this.frequency, 0);
                this.volumeGlide = new Glide(this.ac, this.volume, 0);
                this.wp = new WavePlayer(this.ac, this.frequencyGlide, Buffer.SINE);
                this.g = new Gain(this.ac, 1, this.volumeGlide);
                this.g.addInput(this.wp);
                this.ac.out.addInput(this.g);
                this.ac.start();
                this.running = true;
            }
        }
    }

    public void stopSound() {
        if (this.running) {
            this.g.kill();
            this.wp.kill();
            this.ac.stop();
            this.running = false;
        }
    }

    public void setVolume(int vol) {
        this.volume = (float) (vol / 100.0);
    }

    public float getVolume() {
        return this.volume;
    }
}
