package c21394693;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import ddf.minim.AudioBuffer;
import ddf.minim.AudioInput;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import ie.tudublin.Visual;
import processing.core.*;

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

    private int lampX = WINDOW_WIDTH; // X-coordinate of the lamp

    PImage backgroundImage;
    PImage dudeImage;
    PImage streetLampImage;



    public void settings() {
        size(WINDOW_WIDTH, WINDOW_HEIGHT, P2D);

    }

    public void setup() {
        println("Setting up scene now");

        frameRate(30); // A nice choppy animation

        /* - - - Setup the Audio - - - */
        setFrameSize(512); // "Frame" here refers to the audio buffer
        startMinim();
        loadAudio("endOfTheWorld.mp3");
        getAudioPlayer().cue(0);
        getAudioPlayer().play();
        /* - - - Finished Audio Setup - - - */



        /* - - - Setup the Images - - - */
        backgroundImage =   loadImage("Shapes_and_Sprites/street.png");
        dudeImage       =   loadImage("Shapes_and_Sprites/dude-20230428-195756.piskel");
        streetLampImage =   loadImage("Shapes_and_Sprites/streetlamp.png");
        /* - - - Finished Image Setup - - - */

        println("Starting drawing now!");
    }


    /*  Liam's Waveform visual  */
    public void Draw_Waveform() {
        /* Waveform visual code */
    }

    /*  Oisin background    */
    public void Draw_Background() {
        /* background visual code */
    }

    /*  César Meteor    */
    public void Draw_Meteor() {
        /* meteor visual code */
    }

    // Stephen Meteor
    /*
     * public void Draw_Meteor()
     * {
     * 
     * }
     */

    public void draw() {
        // background town image
        image(backgroundImage, 0, 0);

        // Draw ground
        fill(200); //Light gray;
        rect(0, WINDOW_HEIGHT-GROUND_HEIGHT, WINDOW_WIDTH, GROUND_HEIGHT);

        // Draw "lamp"
        image(streetLampImage, lampX, (WINDOW_HEIGHT - GROUND_HEIGHT - streetLampImage.height) );


        // Update lamp position
        lampX -= 2; // Move lamp towards left

        // Check if lamp is out of screen, reset its position
        if (lampX + 20 < 0) {
            lampX = WINDOW_WIDTH;
        }
    }
}