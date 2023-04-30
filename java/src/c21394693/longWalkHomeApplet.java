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

    public void settings()
    {
        println("In settings");
    }
    
    public void setup()
    {
        frameRate(5);
        println("In setup");

    }

    public void draw()
    {
        println("Draw!");
    }
}