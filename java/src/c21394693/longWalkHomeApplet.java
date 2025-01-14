package c21394693;

import java.util.ArrayList;

import ddf.minim.AudioPlayer;
import ie.tudublin.Visual;
import processing.core.*;
import ie.tudublin.VisualException;

/*
 * This class creates "dude" and meteor sprites and waveforms
 * and runs a scrolling background and lamps to make it look like hes moving through a town
 * Music to be added and other events to make it actually fun to watch
 */

public class longWalkHomeApplet extends Visual {

    private static final int FIREBALL_RADIUS = 15;
    private static final int FIREBALL_SPEED = 10;
    private float black_screen_alpha = WINDOW_HEIGHT;
    private boolean start_song = true;
    private boolean end_song = false;
    private PFont font;

    private ArrayList<Fireball> fireballs;
    private static final int WINDOW_WIDTH = 1200; // Width of game window
    private static final int WINDOW_HEIGHT = 600; // Height of game window
    private static final int GROUND_HEIGHT = 50; // Height of the ground
    private static float[] smooth_bands;

    /* - - - Images and repeat-sprites - - - */
    PImage niceBackgroundImage;
    PImage riotBackgroundImage;
    PImage dudeImage;
    PImage streetLampImage;
    Repeatable_sprite streetLampRepeat = new Repeatable_sprite(0, 380, 500, 3.5f); // start at X: 0, Y: 369, Gap size:
                                                                                   // 500, Scale: 0.5
    Repeatable_sprite BackgroundNiceRepeat = new Repeatable_sprite(0, 0, 0, 1f);
    Repeatable_sprite BackgroundRiotRepeat = new Repeatable_sprite(0, 0, 0, 1f);
    /* - - - - - - - - - - - - - - - - - - - */

    Dude the_dude = new Dude((WINDOW_HEIGHT - GROUND_HEIGHT - 120), // Y-Axis
            2, // Animation-rate
            150, 150); // Dude width, height

    Meteor the_meteor = new Meteor(200, 100, // X, Y position
            10, 10, 80, 8, 5, 30, 5, // parametres for the waveform bands
            -10, 50, 10, // tilt amount (degrees), particle spawn rate, particle max speed
            6, 150, 150); // frame rate, image width and height

    Curtain curtains = new Curtain(0, 2, WINDOW_WIDTH, WINDOW_HEIGHT);

    public void settings() {
        size(WINDOW_WIDTH, WINDOW_HEIGHT, P2D);
    }

    public void setup() {
        println("Setting up scene now");

        // load our font
        font = createFont("pixel.otf", 42);
        textFont(font, 42);
        textAlign(CENTER, TOP);

        surface.setTitle("The End of the World");
        surface.setResizable(false);

        frameRate(40); // 40 allows for minor frame rate dips to 30

        /* - - - Setup the Audio - - - */
        setFrameSize(1024); // "Frame" here refers to the audio buffer, increase if audio issues
        startMinim();
        loadAudio("endOfTheWorld.mp3");

        /* - - - - - - - - - - - - - - - */

        /* - - - Setup the Images - - - */
        niceBackgroundImage = loadImage("Shapes_and_Sprites/streetNoSky.png");
        riotBackgroundImage = loadImage("Shapes_and_Sprites/darkerStreet.png");
        streetLampImage = loadImage("Shapes_and_Sprites/lamp.png");
        dudeImage = loadImage("Shapes_and_Sprites/dude.png");

        // The dude sprites[3]
        the_dude.sprite_sheet_run[0] = loadImage("Shapes_and_Sprites/dude_sprites/l0_dudeFinal1.png");
        the_dude.sprite_sheet_run[1] = loadImage("Shapes_and_Sprites/dude_sprites/l2_dudeFinal1.png");
        the_dude.sprite_sheet_run[2] = loadImage("Shapes_and_Sprites/dude_sprites/l1_dudeFinal1.png");

        // Meteor sprites
        the_meteor.sprite_sheet_fly[0] = loadImage("Shapes_and_Sprites/meteorLayers/l0_sprite_1.png");
        the_meteor.sprite_sheet_fly[1] = loadImage("Shapes_and_Sprites/meteorLayers/l1_sprite_1.png");
        the_meteor.sprite_sheet_fly[2] = loadImage("Shapes_and_Sprites/meteorLayers/l2_sprite_1.png");
        the_meteor.sprite_sheet_fly[3] = loadImage("Shapes_and_Sprites/meteorLayers/l3_sprite_1.png");
        /* - - - - - - - - - - - - - */

        // Curatin sprites
        curtains.sprite_sheet_curtain[0] = loadImage("Shapes_and_Sprites/curtainLayers/l0_curtains1.png");
        curtains.sprite_sheet_curtain[1] = loadImage("Shapes_and_Sprites/curtainLayers/l1_curtains1.png");
        curtains.sprite_sheet_curtain[2] = loadImage("Shapes_and_Sprites/curtainLayers/l2_curtains1.png");
        curtains.sprite_sheet_curtain[3] = loadImage("Shapes_and_Sprites/curtainLayers/l3_curtains1.png");
        curtains.sprite_sheet_curtain[4] = loadImage("Shapes_and_Sprites/curtainLayers/l4_curtains1.png");
        curtains.sprite_sheet_curtain[5] = loadImage("Shapes_and_Sprites/curtainLayers/l5_curtains1.png");

        fireballs = new ArrayList<>();
        noStroke();
        ellipseMode(RADIUS);

        println("Starting drawing now!");
    }

    /*
     * A class which takes in a PImage reference and makes sure that any loading and
     * unloading takes place off-screen, allowing for seamless reptition
     */
    class Repeatable_sprite {
        public float scale = 1f; // how much to scale the image by
        public int distance_between_sprites;

        private int starting_x_position = 0;
        private int starting_y_position = 0;

        public Repeatable_sprite(int Starting_x, int Starting_y, int Gap_size, float scale_factor) {
            starting_x_position = Starting_x;
            starting_y_position = Starting_y;
            distance_between_sprites = Gap_size;
            scale = scale_factor;
        }

        public void repeat(PImage image_ref, int scroll_speed, boolean stop, boolean visable) {

            int width_taken_up_so_far = 0;
            int sprites_required = 0;
            int new_x_position = starting_x_position;

            // Figure out how many sprites are required for seamless repetiton horizontally
            while (width_taken_up_so_far <= (WINDOW_WIDTH * 1.5)) {
                width_taken_up_so_far += image_ref.width * scale;
                width_taken_up_so_far += distance_between_sprites;
                sprites_required++;
            }

            // Draw all X amounts of sprites to fill screen horizontally
            for (int i = 0; i < sprites_required; i++) {
                if (visable == true) {
                    image(image_ref, new_x_position, starting_y_position, image_ref.width * scale,
                            image_ref.height * scale);
                }

                // Update x-position for the next sprite
                new_x_position += image_ref.width * scale;
                new_x_position += distance_between_sprites;
            }

            starting_x_position -= scroll_speed;

            // Have we gone too far off screen? Can we update the x-position?
            if ((starting_x_position + (image_ref.width * scale)) < 0 && stop == false) {
                // Start displaying all images starting from the NEXT sprite
                starting_x_position += (image_ref.width * scale);
                starting_x_position += distance_between_sprites;
            }
        }
    }

    // Our dude character
    class Dude {
        public int starting_Y;

        // set images in setup()
        public PImage[] sprite_sheet_run = new PImage[sprite_sheet_size];
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
        public int bands_to_do; // how bands many should be printed
        public int roundedness; // how round should each band be
        public int degrees_tilt; // how much to tilt waveform
        public int particle_spawn_rate;
        public int max_speed; // max speed of particles
        public int sensitivity; // sensitivity of the bands to audio
        public int frame_rate; // Animatation rate

        // set images in setup()
        public PImage[] sprite_sheet_fly = new PImage[4];

        private int meteorWidth; // size of meteor image
        private int meteorHeight;
        private int meteor_Index = 0; // which meteor sprite to use
        private int particle_distances_so_far[]; // particles distance
        private int particle_alphas[]; // particles transparancy
        private int particle_seeds[]; // particles seeds, used to vary each one, no uniformity
        private float particle_rotations[]; // how much is each particle rotated
        static private long last_change_time; // time since last sprite change
        static private final int meteor_spriteSheet_size = 4; // meteor flipbook size

        public Meteor(int met_origin_x, int met_origin_y, int band_initial_gap_x, int band_initial_gap_y,
                int band_height, int band_width, int band_sensitivity, int met_bands_to_do,
                int corner_radius, int tilt_degrees, int max_particle_count, int particle_max_speed,
                int animation_rate, int met_width, int met_height) {

            origin_x = met_origin_x;
            origin_y = met_origin_y;
            initial_gap_x = band_initial_gap_x;
            initial_gap_y = band_initial_gap_y;
            max_height = band_height;
            rect_width = band_width;
            sensitivity = band_sensitivity;
            bands_to_do = met_bands_to_do;
            roundedness = corner_radius;
            degrees_tilt = tilt_degrees;
            particle_spawn_rate = max_particle_count;
            max_speed = particle_max_speed;
            frame_rate = animation_rate;
            meteorWidth = met_width;
            meteorHeight = met_height;

            particle_distances_so_far = new int[max_particle_count];
            particle_alphas = new int[max_particle_count];
            particle_seeds = new int[max_particle_count];
            particle_rotations = new float[max_particle_count];

            // initialise meteor particles arrays
            for (int i = 0; i < max_particle_count; i++) {
                particle_distances_so_far[i] = 0;
                particle_seeds[i] = (i % max_speed) + 1;
                particle_alphas[i] = 255;
                particle_rotations[i] = i * 1.234f; // 1.234 chosen at random
            }
        }

        public void draw_meteor(float scale_amount) // how much should everthing scale as the song goes on?
        {
            pushMatrix();
            noStroke();
            colorMode(RGB);
            translate(origin_x, origin_y); // set new origin
            rotate((float) degrees_tilt * 0.01745f); // degrees to radians... clockwise

            /* - - - top left of mini waveform - - - */
            // set band_amp to a band value from the lowest quartile of smoothed bands
            float band_amp = (smooth_bands[smooth_bands.length / 4] + 1) * sensitivity;
            int start_x = -initial_gap_x - rect_width;
            for (int i = 1; i <= bands_to_do; i++) {
                fill(band_amp, band_amp / 4, band_amp / 4, 255 / i);
                rect(start_x,
                        -initial_gap_y,
                        rect_width,
                        constrain(-band_amp / i, -max_height, 0),
                        roundedness);

                start_x -= rect_width;
            }

            /* - - - top right of mini waveform - - - */
            band_amp = smooth_bands[(smooth_bands.length / 4) * 3] * sensitivity;
            start_x = initial_gap_x;
            for (int i = 1; i <= bands_to_do; i++) {
                fill(band_amp, band_amp / 4, band_amp / 4, 255 / i);
                rect(start_x,
                        -initial_gap_y,
                        rect_width,
                        constrain(-band_amp / i, -max_height, 0),
                        roundedness);

                start_x += rect_width;
            }

            /* - - - bottom right of mini waveform - - - */
            band_amp = smooth_bands[(smooth_bands.length / 4) * 2] * sensitivity;
            start_x = initial_gap_x;
            for (int i = 1; i <= bands_to_do; i++) {
                fill(band_amp / 2, band_amp / 10, band_amp / 10, 222 / i);
                rect(start_x,
                        initial_gap_y,
                        rect_width,
                        constrain(band_amp / i, 0, max_height),
                        roundedness);

                start_x += rect_width;
            }

            /* - - - bottom left of mini waveform - - - */
            band_amp = smooth_bands[(smooth_bands.length - 3)] * sensitivity;
            start_x = -initial_gap_x - rect_width;
            for (int i = 1; i <= bands_to_do; i++) {
                fill(band_amp / 2, band_amp / 10, band_amp / 10, 222 / i);
                rect(start_x,
                        initial_gap_y,
                        rect_width,
                        constrain(band_amp / i, 0, max_height),
                        roundedness);

                start_x -= rect_width;
            }

            rotate(-((float) degrees_tilt * 0.01745f)); // rotate back to normal (convert to degrees)

            /* - - - - Draw Particles - - - - */
            ellipseMode(CENTER);
            colorMode(HSB);
            for (int i = 0; i < particle_spawn_rate; i++) {
                rotate(particle_rotations[i]); // Rotate start point

                // has the particle fully disapperead?
                if (particle_alphas[i] <= 0) {
                    // reset opacity and position
                    particle_alphas[i] = 255;
                    particle_distances_so_far[i] = 0;
                }

                fill(135 - particle_distances_so_far[i], 255, particle_alphas[i], particle_alphas[i]);

                // make a oval, and move it upwards, make it smaller the less visable it is
                ellipse(0, particle_distances_so_far[i], particle_alphas[i] / 24f, particle_alphas[i] / 8f);

                // update distance and opacity
                particle_distances_so_far[i] -= particle_seeds[i];
                particle_alphas[i] -= particle_seeds[i];

            }

            // back to RGB
            colorMode(RGB);
            popMatrix();
            /* - - - - - - - - - - - - - - - - - */

            /* - - - - - Draw Meteor - - - - - */
            pushMatrix();

            translate(origin_x, origin_y);

            // Have enough miliseconds passsed to change sprite?
            if ((System.currentTimeMillis() - last_change_time) > 1000 / frame_rate) {
                meteor_Index++;
                // Loop back to 0 if needed
                if (meteor_Index >= meteor_spriteSheet_size) {
                    meteor_Index = 0;
                }
                last_change_time = System.currentTimeMillis();
            }

            // gets slightly bigger as the song goes on
            int new_width = meteorWidth + (int) ((getAudioPlayer().position() / 1000) * scale_amount);
            int new_height = meteorHeight + (int) ((getAudioPlayer().position() / 1000) * scale_amount);
            image(sprite_sheet_fly[meteor_Index], -new_width / 2, -new_height / 2, new_width, new_height);
            popMatrix();
            /* - - - - - - - - - - - - - - - - - */
        }
    }

    class Curtain {

        public PImage[] sprite_sheet_curtain = new PImage[6];
        private int curtainHeight;
        private int curtainWidth;
        private int frame_rate;
        private int starting_Y;
        public int sprite_index = 0;

        public Curtain(int starting_y_pos, int animation_rate, int curtain_width, int curtain_height) {
            starting_Y = starting_y_pos;
            frame_rate = animation_rate;
            curtainWidth = curtain_width;
            curtainHeight = curtain_height;
        }

        public void draw_curtains() {
            if (millis() % 2000 < 50) {
                sprite_index++;
            }

            if (getAudioPlayer().length() - getAudioPlayer().position() <= 5000 && millis() % 2000 < 50) {
                sprite_index--;
            }

            if (sprite_index <= 5 && sprite_index >= 0) {
                image(sprite_sheet_curtain[sprite_index], X, starting_Y, curtainWidth, curtainHeight);

            }

        }

    }

    /* Liam's Waveform visual */
    public void Draw_Waveform(int Y_Position, int max_height) {
        // Set the stroke color to red and stroke weight to 2
        stroke(255, 0, 0);
        strokeWeight(10);

        // Calculate the step size for the x-axis, +0.02 so that it reaches the end of
        // the screen
        int xStep = (int) (width / (float) smooth_bands.length);

        // i += 1 = 1024 vertex's to sample (90+% performance loss)
        // i += 8 = 128 vertex's to sample (1-2 fps loss)
        rectMode(CENTER);

        for (int i = 0; i < smooth_bands.length; i++) {
            fill(255, 100, 100, 255);
            int x = i * xStep + (xStep / 2);
            int y = (int) (smooth_bands[i] * 5);

            rect(x, Y_Position, xStep, constrain(y, 100, max_height), 50);
        }
        rectMode(CORNER);
        noStroke();
    }

    public void draw() {

        // Analyse Audio
        calculateAverageAmplitude();
        try {
            calculateFFT();
        } catch (VisualException e) {
            e.printStackTrace();
        }
        calculateFrequencyBands();
        smooth_bands = getSmoothedBands();

        // Sky gradually turns more red
        background(100 + (getAudioPlayer().position() / 1000) + 100 * getSmoothedAmplitude(),
                150 - getAudioPlayer().position() / 2000,
                220 - getAudioPlayer().position() / 1000);

        the_meteor.draw_meteor(1.2f);

        // Liams waveform
        Draw_Waveform((int) (height), 800);

        // Move and draw the fireballs
        for (int i = fireballs.size() - 1; i >= 0; i--) {
            noStroke();
            fill(100 + (2000 * getSmoothedAmplitude()), 1000 * getSmoothedAmplitude(), 100 * getSmoothedAmplitude());
            Fireball fireball = fireballs.get(i);
            fireball.move();
            fireball.init();
            // Remove the fireball if it goes off the bottom of the screen
            if (fireball.y > WINDOW_HEIGHT) {
                fireballs.remove(i);
            }
        }

        // background town image
        if (getAudioPlayer().position() <= 5000) {
            // for first 5,000 miliseconds show JUST the nice street
            BackgroundRiotRepeat.repeat(riotBackgroundImage, 2, false, false);
            BackgroundNiceRepeat.repeat(niceBackgroundImage, 2, false, true);
        } else if (getAudioPlayer().position() <= 40000) {

            // Create new fireballs at the top of the screen
            if (random(1) < 0.05) {
                fireballs.add(new Fireball());
            }

            // start to switch to rioted street after 40,000 miliseconds (40 seconds)
            BackgroundRiotRepeat.repeat(riotBackgroundImage, 2, false, true);
            BackgroundNiceRepeat.repeat(niceBackgroundImage, 2, true, true);
        } else {
            // Create more fireballs
            if (random(1) < 0.12) {
                fireballs.add(new Fireball());
            }
            // turn off nice street all together after 40 seconds
            BackgroundRiotRepeat.repeat(riotBackgroundImage, 2, false, true);
            BackgroundNiceRepeat.repeat(niceBackgroundImage, 2, true, false);

        }

        // Draw ground
        calculateAverageAmplitude();
        fill(100 + getAmplitude() * 200); // Light gray + beat;
        rect(0, WINDOW_HEIGHT - GROUND_HEIGHT, WINDOW_WIDTH, GROUND_HEIGHT);

        // Street lamp
        streetLampRepeat.repeat(streetLampImage, 5, false, true);

        // Draw dude
        the_dude.draw_dude();

        // prevent integer underflow
        if (!(black_screen_alpha < -1000) && end_song == false) {
            black_screen_alpha -= 1; // used to control fade in speed
        }

        // Black screen, used for fade in
        fill(0, 0, 0, black_screen_alpha);
        rect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);

        if (end_song == false) {
            fill(240);
            text("\"SOME SAY THE WORLD WILL END IN FIRE, SOME SAY IN ICE,\n"
                    + "FROM WHAT I'VE TASTED OF DESIRE, I HOLD WITH THOSE WHO FAVOUR FIRE\n"
                    + "BUT IF IT HAD TO PERISH TWICE, I THINK I KNOW ENOUGH OF HATE\n"
                    + "TO SAY THAT FOR DESTRUCTION, ICE IS ALSO GREAT\n"
                    + "AND WOULD SUFFICE\"\n\n"
                    + " - ROBERT FROST",

                    width / 2, black_screen_alpha);
        } else {

            fill(240);
            text("Thank you", width / 2, black_screen_alpha);
        }

        // Start playing sound at a certain point in the fade in sequence
        if (start_song == true && black_screen_alpha <= 175) {
            println("\nPLAYING SONG NOW");
            getAudioPlayer().cue(0);
            getAudioPlayer().play();

            start_song = false;
        }

        curtains.draw_curtains();

        // start fade out 10 seconds before the end
        if (getAudioPlayer().length() - getAudioPlayer().position() <= 10000) {
            if (black_screen_alpha < -1) {
                black_screen_alpha = 0;
            }

            black_screen_alpha += 0.6f;
            start_song = false;
            end_song = true;
            println("\nENDING SONG NOW");
        }

        if (black_screen_alpha >= 300 && end_song == true) {
            println("\nThank you, Please have a good day,\n",
                    "A prosperous year,\nand a joyful century"); // end message for terminal
            exit();
        }

        print("\rFPS: " + frameRate + "\t\tSong position (ms): " + getAudioPlayer().position() + "\t\tblack alpha: "
                + black_screen_alpha);

    }

    // Oisins work
    private class Fireball {
        float x, y;
        float speedX, speedY;
        int extra; // get bigger as the song goes on

        public Fireball() {
            x = random(WINDOW_WIDTH);
            y = -FIREBALL_RADIUS;
            speedX = random(-2, 2);
            speedY = FIREBALL_SPEED;
        }

        public void move() {
            x += speedX;
            y += speedY;
        }

        public void init() {

            extra = getAudioPlayer().position() / 8000;
            ellipse(x, y, FIREBALL_RADIUS + extra, FIREBALL_RADIUS + extra);
        }

    }
}