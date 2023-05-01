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

    // Images and repeat-sprites
    PImage backgroundImage;
    PImage dudeImage;
    PImage streetLampImage;
    Repeatable_sprite streetLampRepeat = new Repeatable_sprite(0, 369, 500, 0.5f);
    Repeatable_sprite BackgroundRepeat = new Repeatable_sprite(0, 0, 0, 1f);
    Dude the_dude = new Dude((WINDOW_HEIGHT - GROUND_HEIGHT - 100), 1, 100, 100);

    public void keyPressed() {
        if (key == ' ') {
            the_dude.jump();
        }
    }

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
        backgroundImage = loadImage("Shapes_and_Sprites/darkerStreet.png");
        dudeImage = loadImage("Shapes_and_Sprites/dude.png");
        streetLampImage = loadImage("Shapes_and_Sprites/trans_streetlamp.png");

        // The dude sprites[3]
        the_dude.sprite_sheet_run[0] = loadImage("Shapes_and_Sprites/dude_sprites/l0_dudeFinal1.png");
        the_dude.sprite_sheet_run[1] = loadImage("Shapes_and_Sprites/dude_sprites/l2_dudeFinal1.png");
        the_dude.sprite_sheet_run[2] = loadImage("Shapes_and_Sprites/dude_sprites/l1_dudeFinal1.png");

        the_dude.sprite_sheet_jump[0] = loadImage("Shapes_and_Sprites/dude_sprites/dude_run_1.jpg");
        the_dude.sprite_sheet_jump[1] = loadImage("Shapes_and_Sprites/dude_sprites/dude_run_2.jpg");
        the_dude.sprite_sheet_jump[2] = loadImage("Shapes_and_Sprites/dude_sprites/dude_run_3.jpg");
        /* - - - Finished Image Setup - - - */

        println("Starting drawing now!");
    }

    /*
     * A class which takes in a PImage reference and makes sure that any loading and
     * unloading
     * takes place off-screen, allowing for seamless reptition
     */
    class Repeatable_sprite {
        public int distance_between_sprites;
        public float scale = 1f; // how much to scale the image by
        private int starting_x_position = 0;
        private int starting_y_position = 0;

        public Repeatable_sprite(int Starting_x, int Starting_y, int Gap_size, float scale_factor) {
            starting_x_position = Starting_x;
            starting_y_position = Starting_y;
            distance_between_sprites = Gap_size;
            scale = scale_factor;
        }

        public void repeat(PImage image_ref, int scroll_speed) {

            // Figure out how many sprites are required for seamless repetiton horizontally
            int width_taken_up_so_far = 0;
            int sprites_required = 0;
            int new_x_position = starting_x_position;

            while (width_taken_up_so_far <= (width * 1.5)) {
                width_taken_up_so_far += image_ref.width * scale;
                width_taken_up_so_far += distance_between_sprites;
                sprites_required++;
                /*
                 * println("Gap: " + distance_between_sprites + "\tX: " + starting_x_position
                 * + "\tNeed " + sprites_required + " sprites\tNew X: " + new_x_position
                 * + "\tFPS: "+ frameRate);
                 */
            }

            for (int i = 0; i < sprites_required; i++) {

                image(image_ref, new_x_position, starting_y_position, image_ref.width*scale, image_ref.height*scale);
                new_x_position += image_ref.width * scale;
                new_x_position += distance_between_sprites;
            }

            starting_x_position -= scroll_speed;

            if ((starting_x_position + (image_ref.width*scale)) < 0) {

                starting_x_position += (image_ref.width * scale);
                starting_x_position += distance_between_sprites;
            }
        }

    }

    class Dude {
        public int starting_Y;
        // set images in setup()
        public PImage[] sprite_sheet_run = new PImage[sprite_sheet_size];
        public PImage[] sprite_sheet_jump = new PImage[sprite_sheet_size];
        public int frame_rate;

        private int Y; // Y-coordinate of the dude
        private int X = 100;
        private int dudeWidth;
        private int dudeHeight;
        public int sprite_index = 0;
        private int dudeYSpeed = 0; // Vertical speed of the dude
        private boolean isJumping = false;
        static private long last_change_time;
        static private final int sprite_sheet_size = 3;

        public Dude(int starting_y_pos, int animation_rate, int dude_width, int dude_height) {
            starting_Y = starting_y_pos;
            frame_rate = animation_rate;
            dudeWidth = dude_width;
            dudeHeight = dude_height;
        }

        public void draw_dude() {
            // Have enough miliseconds passsed
            if ((System.currentTimeMillis() - last_change_time) > 1000 / frame_rate) 
            {
                sprite_index++;
                // Loop back to 0 if needed
                if (sprite_index >= sprite_sheet_size) {
                    sprite_index = 0;
                }
                last_change_time = System.currentTimeMillis();
            }

            Y += dudeYSpeed;
            if (isJumping == true) {
                image(sprite_sheet_jump[sprite_index], X, Y, dudeWidth, dudeHeight);
            } else {
                image(sprite_sheet_run[sprite_index], X, Y, dudeWidth, dudeHeight);
            }

            // Check for collision with ground
            if (Y >= WINDOW_HEIGHT - GROUND_HEIGHT - dudeHeight) {
                Y = WINDOW_HEIGHT - GROUND_HEIGHT - dudeHeight; // Set dude back on ground
                isJumping = false; // Reset jumping flag
            } else {
                dudeYSpeed += 1; // Apply gravity
            }
        }

        public void jump() {
            if (isJumping == false) {
                // Space key is pressed and dude is not already jumping
                isJumping = true;
                dudeYSpeed = -10; // Set vertical speed to negative value to make the dude jump
            }
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
        for (int i = 0; i < waveform.length; i += 16) {
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

        background(102, 153, 204); // Cyan makes gaps easy to spot

        // background town image
        BackgroundRepeat.repeat(backgroundImage, 2);

        // Draw ground
        fill(200); // Light gray;
        rect(0, WINDOW_HEIGHT - GROUND_HEIGHT, WINDOW_WIDTH, GROUND_HEIGHT);
        // Liams waveform, just calling it so it can be seen on the screen
       // Draw_Waveform();

        
        // Draw dude
        the_dude.draw_dude();
        
        // Street lamp
        streetLampRepeat.repeat(streetLampImage, 5);



        print("\rFPS: " + frameRate);

    }

}