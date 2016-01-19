/*
 * The controller classes (like the ViewUserActions class) provides actions
 * that the user can trigger through the view classes.  Those actions are 
 * written in this class as private inner classes (i.e. classes 
 * declared inside another class).
 *
 * You can use all the public instance variables you defined in AllModelsForView, 
 * DrawnView, and ViewOutputs as though they were part of this class! This is 
 * due to the magic of subclassing (i.e. using the extends keyword).
 */
package viewcontroller;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JToggleButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * ViewUserActions is a class that contains actions users can trigger.
 *
 * User actions are written as private inner classes that implements the
 * ActionListener interface. These actions must be "wired" into the DrawnView in
 * the ViewUserActions constructor, or else they won't be triggered by the user.
 *
 * Actions that the user can trigger are meant to manipulate some model classes
 * by sending messages to them to tell them to update their data members.
 *
 * Actions that the user can trigger can also be used to manipulate the GUI by
 * sending messages to the view classes (e.g. the DrawnView class) to tell them
 * to update themselves (e.g. to redraw themselves on the screen).
 *
 * @author cheng
 */
public class ViewUserActions extends ViewOutputs {

    /*
     * Step 1 of 2 for writing user actions.
     * -------------------------------------
     *
     * User actions are written as private inner classes that implement
     * ActionListener, and override the actionPerformed method.
     *
     */
    private class setColourWheelShape implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                JComboBox source = (JComboBox) e.getSource();
                int index = source.getSelectedIndex();
                colourWheel.setWheel(index);
                updateColourWheel();
            } catch (IOException ex) {
                Logger.getLogger(ViewUserActions.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private class setColourWheelVolume implements ChangeListener {

        @Override
        public void stateChanged(ChangeEvent ce) {
            JSlider source = (JSlider) ce.getSource();
            int volume = source.getValue();
            colourWheel.setVolume(volume);
            updateColourWheelVolumeLabel();
        }
    }

    private class setThereminVolume implements MouseWheelListener {

        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            int clicks = e.getWheelRotation(); //Negative if wheel is rotated "up", positive if rotated "down"
            theremin.setProximity(clicks);
            updateThereminVolume(theremin.getProximity());
            if (theremin.getOn() == true) {
                updateThereminSound();
            }
        }
    }

    private class trackColourWheelMouse implements MouseListener, MouseMotionListener {

        private Color mouseColour;

        @Override
        public void mouseEntered(MouseEvent e) {
            //This method should probably stay empty
        }

        @Override
        public void mouseExited(MouseEvent e) {
            if (colourWheel.getColour() != null) {
                colourWheel.setColour(null);
                updateColourWheelSound();
            }
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            //LEAVE THIS METHOD EMPTY
            //PLEASE
            //FOR THE LOVE OF GOD
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if (inColourWheel(e)) {
                updateColour(e);
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            colourWheel.setColour(null);
            updateColourWheelSound();
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if (inColourWheel(e)) {
                updateColour(e);
            }
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            //This method should probably stay empty
        }

        public void updateColour(MouseEvent e) {
            JLabel label = (JLabel) e.getSource();
            Image image = colourWheel.wheelShape.getImage();
            BufferedImage img = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
            Graphics2D bufferGraphics = img.createGraphics();
            bufferGraphics.drawImage(image, 0, 0, null);
            bufferGraphics.dispose();
            Color backgroundColour = new Color(224, 224, 224);
            Point cursor = e.getPoint();
            int wheelWidth = colourWheel.wheelShape.getIconWidth();
            int wheelHeight = colourWheel.wheelShape.getIconHeight();
            int imageX = cursor.x - (label.getWidth() - wheelWidth) / 2;
            int imageY = cursor.y - (label.getHeight() - wheelHeight) / 2;
            mouseColour = new Color(img.getRGB(imageX, imageY));
            if (mouseColour != backgroundColour) {
                colourWheel.setColour(mouseColour);
                updateColourWheelSound();
                updateColourDisplay();
            }
        }

        public boolean inColourWheel(MouseEvent e) {
            JLabel label = (JLabel) e.getSource();
            Point cursor = e.getPoint();
            int wheelWidth = colourWheel.wheelShape.getIconWidth();
            int wheelHeight = colourWheel.wheelShape.getIconHeight();
            int imageX = cursor.x - (label.getWidth() - wheelWidth) / 2;
            int imageY = cursor.y - (label.getHeight() - wheelHeight) / 2;
            return imageX > 0 && imageX < wheelWidth && imageY > 0 && imageY < wheelHeight;
        }
    }

    private class trackThereminMouse implements MouseListener, MouseMotionListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            //LEAVE THIS EMPTY
        }

        @Override
        public void mousePressed(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();
            theremin.setMouseX(x);
            theremin.setMouseY(y);
            theremin.setOn(true);
            updateThereminSound();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            theremin.setOn(false);
            updateThereminSound();
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            //LEAVE THIS EMPTY
        }

        @Override
        public void mouseExited(MouseEvent e) {
            if (theremin.getOn() == true) {
                theremin.setOn(false);
                updateThereminSound();
            }
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if (e.getX() < 448 && e.getY() < 448) {
                int x = e.getX();
                int y = e.getY();
                theremin.setMouseX(x);
                theremin.setMouseY(y);
                theremin.setOn(true);
                updateThereminSound();
            }
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            //LEAVE THIS EMPTY
        }
    }

    private class colourWheelRecord implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JToggleButton source = (JToggleButton) e.getSource();
            if (colourWheelPlayBackToggle.isSelected()) {
                source.setSelected(false);
                JOptionPane.showMessageDialog(new JPanel(), "Please stop playing back first.");
            }
            boolean toggled = source.isSelected();
            recordColourWheel(toggled);
        }
    }

    private class thereminRecord implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JToggleButton source = (JToggleButton) e.getSource();
            if (thereminPlayBackToggle.isSelected()) {
                source.setSelected(false);
                JOptionPane.showMessageDialog(new JPanel(), "Please stop playing back first.");
            }
            boolean toggled = source.isSelected();
            recordTheremin(toggled);
        }
    }

    private class colourWheelSave implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (colourWheelRecordToggle.isSelected()) {
                JOptionPane.showMessageDialog(new JPanel(), "Please stop recording first.");
            } else if (colourWheelPlayBackToggle.isSelected()) {
                JOptionPane.showMessageDialog(new JPanel(), "Please stop playing back first.");
            } else if (colourWheel.sample == null) {
                JOptionPane.showMessageDialog(new JPanel(), "Please record something first.");
            } else {
                saveColourWheel();
            }
        }
    }

    private class thereminSave implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (thereminRecordToggle.isSelected()) {
                JOptionPane.showMessageDialog(new JPanel(), "Please stop recording first.");
            } else if (thereminPlayBackToggle.isSelected()) {
                JOptionPane.showMessageDialog(new JPanel(), "Please stop playing back first.");
            } else if (theremin.sample == null) {
                JOptionPane.showMessageDialog(new JPanel(), "Please record something first.");
            } else {
                saveTheremin();
            }
        }
    }

    private class colourWheelPlayBack implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JToggleButton source = (JToggleButton) e.getSource();
            if (colourWheel.sample == null) {
                source.setSelected(false);
                JOptionPane.showMessageDialog(new JPanel(), "Please record something first.");
            } else if (colourWheelRecordToggle.isSelected()) {
                source.setSelected(false);
                JOptionPane.showMessageDialog(new JPanel(), "Please stop recording first.");
                boolean toggled = source.isSelected();
                playBackColourWheel(toggled);
            } else {
                boolean toggled = source.isSelected();
                playBackColourWheel(toggled);
            }
        }
    }

    private class thereminPlayBack implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JToggleButton source = (JToggleButton) e.getSource();
            if (theremin.sample == null) {
                source.setSelected(false);
                JOptionPane.showMessageDialog(new JPanel(), "Please record something first.");
            } else if (thereminRecordToggle.isSelected()) {
                source.setSelected(false);
                JOptionPane.showMessageDialog(new JPanel(), "Please stop recording first.");
                boolean toggled = source.isSelected();
                playBackTheremin(toggled);
            } else {
                boolean toggled = source.isSelected();
                playBackTheremin(toggled);
            }
        }
    }

    private class musicPlayerUploadSong implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (musicPlayer.playing && musicPlayer.sp.isDeleted()) {
                stop();
            }
            uploadSong();
            updateSongLabel();
        }
    }

    private class Play implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            if (musicPlayer.songUploaded == true) {
                play();
            } else {
                JOptionPane.showMessageDialog(new JPanel(), "Please upload a song first.");
            }
        }
    }

    private class Stop implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            if (musicPlayer.playing == true) {
                stop();
            } else {
                JOptionPane.showMessageDialog(new JPanel(), "You may only stop a song that is playing.");
            }
        }
    }

    /*
     * ViewUserActions constructor used for wiring user actions to GUI elements
     */
    public ViewUserActions() {
        /*
         * Step 2 of 2 for writing user actions.
         * -------------------------------------
         *
         * Once a private inner class has been written for a user action, you
         * can wire it to a GUI element (i.e. one of GUI elements you drew in
         * the DrawnView class and which you gave a memorable public variable
         * name!).
         *
         */
        wheelLabel.addMouseListener(new trackColourWheelMouse());
        wheelLabel.addMouseMotionListener(new trackColourWheelMouse());
        colourWheelList.addActionListener(new setColourWheelShape());
        colourWheelVolumeSlider.addChangeListener(new setColourWheelVolume());
        thereminTabPanel.addMouseWheelListener(new setThereminVolume());
        thereminLabel.addMouseListener(new trackThereminMouse());
        thereminLabel.addMouseMotionListener(new trackThereminMouse());
        colourWheelRecordToggle.addActionListener(new colourWheelRecord());
        thereminRecordToggle.addActionListener(new thereminRecord());
        colourWheelSaveButton.addActionListener(new colourWheelSave());
        thereminSaveButton.addActionListener(new thereminSave());
        colourWheelPlayBackToggle.addActionListener(new colourWheelPlayBack());
        thereminPlayBackToggle.addActionListener(new thereminPlayBack());
        songUploadButton.addActionListener(new musicPlayerUploadSong());
        playButton.addActionListener(new Play());
        stopButton.addActionListener(new Stop());
    }
}
