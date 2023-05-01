package c21394693;

import ddf.minim.AudioBuffer;
import ddf.minim.AudioPlayer;
import ie.tudublin.Visual;
import processing.core.*;
import ie.tudublin.VisualException;
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
    PImage niceBackgroundImage;
    PImage riotBackgroundImage;
    PImage dudeImage;
    PImage streetLampImage;

    Repeatable_sprite streetLampRepeat = new Repeatable_sprite(0, 369, 500, 0.5f);
    Repeatable_sprite BackgroundNiceRepeat = new Repeatable_sprite(0, 0, 0, 1f);
    Repeatable_sprite BackgroundRiotRepeat = new Repeatable_sprite(0, 0, 0, 1f);
    Dude the_dude = new Dude((WINDOW_HEIGHT - GROUND_HEIGHT - 120), 2, 150, 150);
    Meteor the_new_meteor = new Meteor(200, 100, // X, Y position
            10, 10, 50, 5, 200, 30, 15, // parametres for the waveform bands
            -10, 30, 10, // tilt amount (degrees), particle spawn rate, particle max speed
            6, 150, 150); // frame rate, image width and height

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
        niceBackgroundImage = loadImage("Shapes_and_Sprites/streetNoSky.png");
        riotBackgroundImage = loadImage("Shapes_and_Sprites/darkerStreet.png");
        dudeImage = loadImage("Shapes_and_Sprites/dude.png");
        streetLampImage = loadImage("Shapes_and_Sprites/trans_streetlamp.png");

        // The dude sprites[3]
        the_dude.sprite_sheet_run[0] = loadImage("Shapes_and_Sprites/dude_sprites/l0_dudeFinal1.png");
        the_dude.sprite_sheet_run[1] = loadImage("Shapes_and_Sprites/dude_sprites/l2_dudeFinal1.png");
        the_dude.sprite_sheet_run[2] = loadImage("Shapes_and_Sprites/dude_sprites/l1_dudeFinal1.png");

        the_dude.sprite_sheet_jump[0] = loadImage("Shapes_and_Sprites/dude_sprites/dude_run_1.jpg");
        the_dude.sprite_sheet_jump[1] = loadImage("Shapes_and_Sprites/dude_sprites/dude_run_2.jpg");
        the_dude.sprite_sheet_jump[2] = loadImage("Shapes_and_Sprites/dude_sprites/dude_run_3.jpg");

        // Meteor sprites
        the_new_meteor.sprite_sheet_fly[0] = loadImage("Shapes_and_Sprites/meteorLayers/l0_sprite_1.png");
        the_new_meteor.sprite_sheet_fly[1] = loadImage("Shapes_and_Sprites/meteorLayers/l1_sprite_1.png");
        the_new_meteor.sprite_sheet_fly[2] = loadImage("Shapes_and_Sprites/meteorLayers/l2_sprite_1.png");
        the_new_meteor.sprite_sheet_fly[3] = loadImage("Shapes_and_Sprites/meteorLayers/l3_sprite_1.png");
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

        public void repeat(PImage image_ref, int scroll_speed, boolean stop, boolean visable) {

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
                if (visable == true) 
                {
                    image(image_ref, new_x_position, starting_y_position, image_ref.width * scale,
                            image_ref.height * scale);
                }
                new_x_position += image_ref.width * scale;
                new_x_position += distance_between_sprites;
            }

            starting_x_position -= scroll_speed;

            if ((starting_x_position + (image_ref.width * scale)) < 0 && stop == false) {

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

        private int X = 100;
        private int dudeWidth;
        private int dudeHeight;
        public int sprite_index = 0;

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
            if ((System.currentTimeMillis() - last_change_time) > 1000 / frame_rate) {
                sprite_index++;
                // Loop back to 0 if needed
                if (sprite_index >= sprite_sheet_size) {
                    sprite_index = 0;
                }
                last_change_time = System.currentTimeMillis();
            }

            image(sprite_sheet_run[sprite_index], X, starting_Y, dudeWidth, dudeHeight);
        }
    }

    // The meteor class, draws waveform and particles off incoming meteor
    class Meteor {
        public int origin_x; // new scene origin
        public int origin_y;
        public int initial_gap_x; // how far away should the corner waveforms be from the meteor
        public int initial_gap_y;
        public int max_height; // max height of each rectangle in those waveforms
        public int rect_width; // max width of each rectangle
        public int bands_to_do; // how many should be printed
        public int roundedness;
        public int degrees_tilt; // how much to tilt waveform
        public int particle_spawn_rate;
        public int max_speed;
        public int sensitivity;

        // set images in setup()
        public PImage[] sprite_sheet_fly = new PImage[4];
        public int frame_rate;

        private int meteorWidth;
        private int meteorHeight;
        private int meteor_Index = 0;
        private int particle_distances_so_far[];
        private int particle_alphas[];
        private int particle_seeds[];
        private float particle_rotations[];
        static private long last_change_time;

        static private final int meteor_spriteSheet_size = 4;

        public Meteor(int met_origin_x, int met_origin_y, int band_initial_gap_x, int band_initial_gap_y,
                int band_max_height, int band_rect_width, int band_sensitivity,
                int met_bands_to_do, int met_roundedness, int met_degrees_tilt, int met_particle_spawn_rate,
                int particle_max_speed, int animation_rate, int met_width, int met_height) {
            origin_x = met_origin_x;
            origin_y = met_origin_y;
            initial_gap_x = band_initial_gap_x;
            initial_gap_y = band_initial_gap_y;
            max_height = band_max_height;
            rect_width = band_rect_width;
            sensitivity = band_sensitivity;
            bands_to_do = met_bands_to_do;
            roundedness = met_roundedness;
            degrees_tilt = met_degrees_tilt;
            particle_spawn_rate = met_particle_spawn_rate;
            max_speed = particle_max_speed;
            frame_rate = animation_rate;
            meteorWidth = met_width;
            meteorHeight = met_height;

            particle_distances_so_far = new int[met_particle_spawn_rate];
            particle_alphas = new int[met_particle_spawn_rate];
            particle_seeds = new int[met_particle_spawn_rate];
            particle_rotations = new float[met_particle_spawn_rate];

            for (int i = 0; i < met_particle_spawn_rate; i++) {
                particle_distances_so_far[i] = 0;
                particle_seeds[i] = (i % max_speed) + 1;
                particle_alphas[i] = 255;
                particle_rotations[i] = i * 1.234f;
            }
        }

        public void draw_meteor(float scale_amount) // how much should everthing scale as the song goes on?
        {
            pushMatrix();
            noStroke();
            colorMode(RGB);
            translate(origin_x, origin_y); // set new origin
            rotate((float) degrees_tilt * 0.01745f); // degrees to radians... clockwise

            // top left of mini waveform
            float band_amp = (getSmoothedBands()[(getSmoothedBands().length / 4) * (1)] + 1) * sensitivity;
            int start_x = -initial_gap_x - rect_width;
            for (int i = 1; i <= bands_to_do; i++) {
                fill(band_amp, band_amp / 4, band_amp / 4, 255 / i);
                rect(
                        start_x,
                        -initial_gap_y,
                        rect_width,
                        constrain(-band_amp / i, -max_height, 0),
                        roundedness);

                start_x -= rect_width;
            }

            // top right of mini waveform
            band_amp = (getSmoothedBands()[(getSmoothedBands().length / 4) * (3)] + 1) * sensitivity;
            start_x = initial_gap_x;

            for (int i = 1; i <= bands_to_do; i++) {
                fill(band_amp, band_amp / 4, band_amp / 4, 255 / i);
                rect(
                        start_x,
                        -initial_gap_y,
                        rect_width,
                        constrain(-band_amp / i, -max_height, 0),
                        roundedness);
                start_x += rect_width;
            }

            // bottom right of mini waveform
            band_amp = (getSmoothedBands()[(getSmoothedBands().length / 4) * (2)] + 1) * sensitivity;
            start_x = initial_gap_x;
            fill(43, 200, 200);
            for (int i = 1; i <= bands_to_do; i++) {
                fill(band_amp / 2, band_amp / 10, band_amp / 10, 222 / i);
                rect(
                        start_x,
                        initial_gap_y,
                        rect_width,
                        constrain(band_amp / i, 0, max_height),
                        roundedness);
                start_x += rect_width;
            }

            // bottom left of mini waveform
            band_amp = (getSmoothedBands()[(getSmoothedBands().length - 3)] + 1) * sensitivity;
            start_x = -initial_gap_x - rect_width;
            fill(43, 43, 200);
            for (int i = 1; i <= bands_to_do; i++) {
                fill(band_amp / 2, band_amp / 10, band_amp / 10, 222 / i);
                rect(
                        start_x,
                        initial_gap_y,
                        rect_width,
                        constrain(band_amp / i, 0, max_height),
                        roundedness);
                start_x -= rect_width;
            }

            rotate(-((float) degrees_tilt * 0.01745f)); // rotate back to normal

            // time to draw particles
            ellipseMode(CENTER);

            colorMode(HSB);
            for (int i = 0; i < particle_spawn_rate; i++) {
                rotate(particle_rotations[i]);

                if (particle_alphas[i] <= 0) {
                    particle_alphas[i] = 255;
                    particle_distances_so_far[i] = 0;
                }

                fill(135 - particle_distances_so_far[i], 255, particle_alphas[i], particle_alphas[i]);

                ellipse(0, particle_distances_so_far[i], particle_alphas[i] / 24f, particle_alphas[i] / 8f);

                particle_distances_so_far[i] -= particle_seeds[i];
                particle_alphas[i] -= particle_seeds[i];

            }
            colorMode(RGB);
            popMatrix();

            // draw meteor
            pushMatrix();

            translate(origin_x, origin_y);

            // Have enough miliseconds passsed
            if ((System.currentTimeMillis() - last_change_time) > 1000 / frame_rate) {
                meteor_Index++;
                // Loop back to 0 if needed
                if (meteor_Index >= meteor_spriteSheet_size) {
                    meteor_Index = 0;
                }
                last_change_time = System.currentTimeMillis();
            }
            int new_width = meteorWidth + (int) ((getAudioPlayer().position() / 1000) * scale_amount);
            int new_height = meteorHeight + (int) ((getAudioPlayer().position() / 1000) * scale_amount);
            image(sprite_sheet_fly[meteor_Index], -new_width / 2, -new_height / 2, new_width, new_height);
            popMatrix();

        }

    }

    /* Liam's Waveform visual */
    public void Draw_Waveform() {
        // Set the stroke color to red and stroke weight to 2
        stroke(255, 0, 0);
        strokeWeight(2);

        // Calculate the step size for the x-axis, +0.02 so that it reaches the end of
        // the screen
        float xStep = width / (float) waveform.length + 0.02f;

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

    public void draw() {

        // Sky gradually turns more red
        background(100 + getAudioPlayer().position() / 1000, 150 - getAudioPlayer().position() / 2000,
                220 - getAudioPlayer().position() / 1000);

        // Get the waveform data from the audio buffer
        buffer = getAudioBuffer();
        waveform = buffer.toArray();

        the_new_meteor.draw_meteor(1.2f);

        // Liams waveform
        Draw_Waveform();

        // background town image
        if (getAudioPlayer().position() <= 5000) {
            //show JUST the nice street
            BackgroundRiotRepeat.repeat(riotBackgroundImage, 2, false, false);
            BackgroundNiceRepeat.repeat(niceBackgroundImage, 2, false, true);
        } else if (getAudioPlayer().position() <= 40000) {
            //start to switch to rioted street
            BackgroundRiotRepeat.repeat(riotBackgroundImage, 2, false, true);
            BackgroundNiceRepeat.repeat(niceBackgroundImage, 2, true, true);
        } else {
            //turn off nice street
            BackgroundRiotRepeat.repeat(riotBackgroundImage, 2, false, true);
            BackgroundNiceRepeat.repeat(niceBackgroundImage, 2, true, false);

        }

        // Draw ground
        calculateAverageAmplitude();
        fill(100 + getAmplitude()*200); // Light gray;
        rect(0, WINDOW_HEIGHT - GROUND_HEIGHT, WINDOW_WIDTH, GROUND_HEIGHT);

        // Street lamp
        streetLampRepeat.repeat(streetLampImage, 5, false, true);

        // Draw dude
        the_dude.draw_dude();

        // the_meteor.Draw_Meteor();

        print("\rFPS: " + frameRate + "    Song position (ms): " + getAudioPlayer().position());

    }

}