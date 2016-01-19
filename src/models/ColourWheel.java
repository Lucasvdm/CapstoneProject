package models;

import java.awt.Color;
import javax.swing.ImageIcon;

public class ColourWheel extends Instrument {

    public ImageIcon wheelShape = new ImageIcon(getClass().getResource("/images/wheel-3-rgb.png"));
    private Color cursorColour = null;

    public void setWheel(int index) {
        switch (index) {
            case 0:
                wheelShape = new ImageIcon(getClass().getResource("/images/wheel-3-rgb.png"));
                break;
            case 1:
                wheelShape = new ImageIcon(getClass().getResource("/images/Colour.png"));
                break;
            case 2:
                wheelShape = new ImageIcon(getClass().getResource("/images/Colour Wheel Background.png"));
                break;
        }
    }

    @Override
    public void calculateFrequency() {
        Color rgbColour = getColour();
        float[] hsbArr = Color.RGBtoHSB(rgbColour.getRed(), rgbColour.getGreen(), rgbColour.getBlue(), null);
        double adjustedHue = Math.sin(hsbArr[0] * (2 * Math.PI)); //Unit circle
        int hsbSum = 0;
        hsbSum += adjustedHue * 250;
        hsbSum += hsbArr[1] * 250;
        hsbSum += hsbArr[2] * 250;
        this.frequency = hsbSum;
    }

    public void setColour(Color clr) {
        this.cursorColour = clr;
    }

    public Color getColour() {
        return this.cursorColour;
    }
}
