package models;

import javax.swing.ImageIcon;

public class Theremin extends Instrument {

    private int mouseX = 0;
    private int mouseY = 0;
    private int volumeProximity = 5;
    private boolean on = false;
    public ImageIcon volumeImage = new ImageIcon(getClass().getResource("/images/metal ball depth_5.png"));

    @Override
    public void calculateFrequency() {
        //Calculate frequency based on relative distance of the mouse from the center of the ball
        int x = this.mouseX;
        int y = this.mouseY;
        if (x == 223 || x == 224) {
            if (y < 224) {
                int distance = 224 - y;
                if (distance < 100) {
                    this.frequency = 750;
                } else {
                    this.frequency = (int) (750 * (y / 124.0));
                }
            } else {
                int distance = y - 224;
                if (distance < 100) {
                    this.frequency = 750;
                } else {
                    this.frequency = (int) (750 * ((448 - y) / 124.0));
                }
            }
        } else if (y == 223 || y == 224) {
            if (x < 224) {
                int distance = 224 - x;
                if (distance < 100) {
                    this.frequency = 750;
                } else {
                    this.frequency = (int) (750 * (x / 124.0));
                }
            } else {
                int distance = x - 224;
                if (distance < 100) {
                    this.frequency = 750;
                } else {
                    this.frequency = (int) (750 * ((448 - x) / 124.0));
                }
            }
        } else {
            int xDistance;
            int yDistance;
            if (x < 224) {
                xDistance = 224 - x;
                if (xDistance < 100) {
                    this.frequency = 750;
                }
            } else {
                xDistance = x - 224;
                if (xDistance < 100) {
                    this.frequency = 750;
                }
            }

            if (y < 224) {
                yDistance = 224 - y;
                if (yDistance < 100) {
                    this.frequency = 750;
                }
            } else {
                yDistance = y - 224;
                if (yDistance < 100) {
                    this.frequency = 750;
                }
            }
            int diagonalLength = (int) Math.round(Math.sqrt(xDistance * xDistance + yDistance * yDistance));
            if (224 - diagonalLength >= 0) {
                this.frequency = (int) (750 * ((224 - diagonalLength) / 124.0));
            }
        }
    }

    public int getProximity() {
        return this.volumeProximity;
    }

    public void setProximity(int clicks) {
        int newTotal = this.volumeProximity + clicks;
        if (newTotal > 10) {
            newTotal = 10;
        }
        if (newTotal < 0) {
            newTotal = 0;
        }
        this.volumeProximity = newTotal;
        setVolume(100 - 10 * this.volumeProximity);
        setVolumeImage(getProximity());
    }

    public void setVolumeImage(int prox) {
        this.volumeImage = new ImageIcon(getClass().getResource("/images/metal ball depth_" + (prox + 1) + ".png"));
    }

    public void setMouseX(int x) {
        this.mouseX = x;
    }

    public void setMouseY(int y) {
        this.mouseY = y;
    }

    public void setOn(boolean on) {
        this.on = on;
    }

    public boolean getOn() {
        return this.on;
    }

}
