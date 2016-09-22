# CapstoneProject
Grade 12 Computer Science AP Capstone Project

Java program with a GUI for music creation, recording, and playback.
Two different "instruments": the colour wheel, and the theremin.

The colour wheel has 3 different shapes which can be selected from the drop-down menu.  Volume is controlled with the slider.  Press
"record" to record the following sounds (does not include silence), "play back" to play back the most recently recorded music, and
"save" to save the most recently recorded music.  Click and drag on the colour wheel to produce a sound based on the colour.

Similar controls for the theremin.  Volume is controlled with the mouse wheel, and can be changed regardless of where the mouse is in
the window, and while the song is recording.  Click and drag in the box to produce a sound based on the proximity of the mouse to the
center of the ball.

Music player buttons are fairly self-explanatory: "Upload song" lets you upload an audio file, "play" lets you play that audio file,
and "stop" lets you stop the audio.  Note: Stop must be pressed before the song can be replayed.

Runnable .jar available in the dist.zip file.
Almost entirely written by me, with some templates given by my teacher for some of the ViewController files.

Uses the Beads audio library for Java.
