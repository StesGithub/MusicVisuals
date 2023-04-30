package c21394693;

import ddf.minim.AudioBuffer;
import ie.tudublin.Visual;
import processing.core.*;

import java.util.ArrayList;

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

    AudioBuffer buffer;
    float[] waveform;

    // Images and Sprites
    PImage backgroundImage;
    PImage dudeImage;
    PImage streetLampImage;
    Repeatable_sprite streetLampRepeat = new Repeatable_sprite(0, 200, 500);
    Repeatable_sprite BackgroundRepeat = new Repeatable_sprite(0, 0, 0);

    public void settings() {
        size(WINDOW_WIDTH, WINDOW_HEIGHT, P2D);

    }

    public void setup() {
        println("Setting up scene now");

        frameRate(40); // 40 allows for minor frame rate dips to 30

        /* - - - Setup the Audio - - - */
        setFrameSize(1024); // "Frame" here refers to the audio buffer, increase if audio issues
        startMinim();
        loadAudio("endOfTheWorld.mp3");
        getAudioPlayer().cue(0);
        getAudioPlayer().play();
        /* - - - Finished Audio Setup - - - */

        /* - - - Setup the Images - - - */
        backgroundImage = loadImage("Shapes_and_Sprites/street.png");
        dudeImage = loadImage("Shapes_and_Sprites/dude-20230428-195756.piskel");
        streetLampImage = loadImage("trans_streetlamp.png");
        /* - - - Finished Image Setup - - - */

        println("Starting drawing now!");
    }

    class Repeatable_sprite {
        public int distance_between_sprites;
        private int starting_x_position = 0;
        private int starting_y_position = 0;

        public Repeatable_sprite(int Starting_x, int Starting_y, int Gap_size) {
            starting_x_position = Starting_x;
            starting_y_position = Starting_y;
            distance_between_sprites = Gap_size;
        }

        //transparent images dont have a width or height, have to input manually
        public void repeat(PImage image_ref, int scroll_speed, int image_width, int image_height)
        {
            // Figure out how many sprites are required for seamless repetiton horizontally
            int width_taken_up_so_far = 0;
            int sprites_required = 0;
            int new_x_position = starting_x_position;

            while (width_taken_up_so_far <= (width * 2)) {
                width_taken_up_so_far += image_width;
                width_taken_up_so_far += distance_between_sprites;
                sprites_required++;
                /*
                 * println("Gap: " + distance_between_sprites + "\tX: " + starting_x_position
                 * + "\tNeed " + sprites_required + " sprites\tNew X: " + new_x_position
                 * + "\tFPS: "+ frameRate);
                 */
            }

            for (int i = 0; i < sprites_required; i++) 
            {
                beginShape(); // TL, TR, BR, BL
                
                textureMode(NORMAL); // corners are 0-1
                texture(image_ref);
                vertex(new_x_position,              starting_y_position,                0,0 ); //Top left
                vertex(new_x_position+image_width,  starting_y_position,                1,0 ); //Top right
                vertex(new_x_position,              starting_y_position+image_width,    0,1 ); //Bottom left
                vertex(new_x_position+image_width,  starting_y_position+image_width,    1,1 ); //Bottom right
                endShape();
                
                new_x_position += image_width;
                new_x_position += distance_between_sprites;
            
            }

            starting_x_position -= scroll_speed;

            if ((starting_x_position + image_width) < 0) {

                starting_x_position += image_width;
                starting_x_position += distance_between_sprites;
            }
        }

        public void repeat(PImage image_ref, int scroll_speed) 
        {
            repeat(image_ref, scroll_speed, image_ref.width, image_ref.height);
        }


    }

    /* Liam's Waveform visual */
    public void Draw_Waveform() {
        // Get the waveform data from the audio buffer
        buffer = getAudioBuffer();
        waveform = buffer.toArray();

        // Set the stroke color to red and stroke weight to 2
        stroke(255, 0, 0);
        strokeWeight(2);

        // Calculate the step size for the x-axis
        float xStep = width / (float) waveform.length;

        // Draw the waveform as a series of connected points
        beginShape();

        // i += 1 = 1024 vertex's to sample (90+% performance loss)
        // i += 8 = 128 vertex's to sample (1-2 fps loss)
        for (int i = 0; i < waveform.length; i += 8) 
        {
            float x = i * xStep;
            float y = map(waveform[i], -1, 1, height, 0);
            vertex(x, y);
        }
        endShape();
    }

    /* Oisin background */
    public void Draw_Background() {
        /* background visual code */
    }

    /* César Meteor */
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

        background(0, 255, 255); //Cyan makes gaps easy to spot

        // background town image
        BackgroundRepeat.repeat(backgroundImage, 2);

        // Draw ground
        fill(200); // Light gray;
        rect(0, WINDOW_HEIGHT - GROUND_HEIGHT, WINDOW_WIDTH, GROUND_HEIGHT);
        // Liams waveform, just calling it so it can be seen on the screen
        Draw_Waveform();

        //Street lamp
        streetLampRepeat.repeat(streetLampImage, 5, 380, 380);
        
        print("\rFPS: "+ frameRate);

    }

}