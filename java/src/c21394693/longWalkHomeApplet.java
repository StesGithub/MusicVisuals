package c21394693;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import ddf.minim.AudioBuffer;
import ddf.minim.AudioInput;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import ie.tudublin.Visual;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


/*
 * This class creates a "dude" sprite 
 * and runs a background image to make it look like hes moving through a town
 * Background image has been loaded in 
 * dude can jump and we have a lamp that scrolls across the screen to simulate movement
 * Hopefully I can replace with a scrolling background
 * Music to be added and other events to make it actually fun to watch
 * Jump works pretty well though
 * Still trying to get the song to play
 */

public class longWalkHomeApplet extends Visual {

    private static final int WINDOW_WIDTH = 1200; // Width of game window
    private static final int WINDOW_HEIGHT = 600; // Height of game window
    private static final int GROUND_HEIGHT = 50; // Height of the ground


    public void settings()
    {
        println("In settings");
        size(WINDOW_WIDTH, WINDOW_HEIGHT, P2D);
    }
    
    public void setup()
    {
        println("In setup");
        
        frameRate(5);
        setFrameSize(512);
        startMinim();
        loadAudio("endOfTheWorld.mp3");
        getAudioPlayer().cue(0);
        getAudioPlayer().play();
    }

    public void draw()
    {
        println("Draw!");
    }
}