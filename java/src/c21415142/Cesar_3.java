package c21415142;

import ie.tudublin.Visual;
import ie.tudublin.VisualException;
import processing.core.PFont;
import processing.core.PGraphics;
import processing.core.PImage;

// - - - MAIN - - -
public class Cesar_3 extends Visual {
    // Values to be referenced and init

    static float delta_seconds; // how long each frame took to render
    PGraphics HUD;
    PFont font;
    float[] bands;

    Meteor metty;
    // Music
    static int song_position = 0; // miliseconds
    static boolean song_pause_toggle = false;

    public void settings() {
        size(800, 600, P2D);
    }

    public void keyReleased() {
        if (key == ' ') {
            if (song_pause_toggle == true) {
                getAudioPlayer().play();
                song_pause_toggle = false;
            } else {
                getAudioPlayer().pause();
                song_pause_toggle = true;
            }
        }
    }

    public void setup() {
        surface.setResizable(true);
        frameRate(30); // lock to 30 fps

        colorMode(HSB);
        setFrameSize(1024);

        font = createFont("Comic Sans MS", 64); // use the system's version of the arial font
        textFont(font);
        HUD = createGraphics(width, height);
        metty = new Meteor(width / 2, height / 2, 10, 10, 100, 5,
        30, 15, -9, 30);

        startMinim();
        loadAudio("endOfTheWorld.mp3");
        getAudioPlayer().cue(0); // go to 0 miliseconds into the song
        getAudioPlayer().play();
    }

    class Meteor {
        int origin_x;
        int origin_y;
        int initial_gap_x;
        int initial_gap_y;
        int max_height;
        int rect_width;
        int bands_to_do;
        int roundedness;
        int degrees_tilt;
        int particle_spawn_rate;
        int particle_distances_so_far[];
        int particle_alphas[];
        int particle_seeds[];
        float particle_rotations[];

        public PImage image;

        public Meteor(int met_origin_x, int met_origin_y, int met_initial_gap_x, int met_initial_gap_y,
                int met_max_height, int met_rect_width,
                int met_bands_to_do, int met_roundedness, int met_degrees_tilt, int met_particle_spawn_rate) {
            origin_x = met_origin_x;
            origin_y = met_origin_y;
            initial_gap_x = met_initial_gap_x;
            initial_gap_y = met_initial_gap_y;
            max_height = met_max_height;
            rect_width = met_rect_width;
            bands_to_do = met_bands_to_do;
            roundedness = met_roundedness;
            degrees_tilt = met_degrees_tilt;
            particle_spawn_rate = met_particle_spawn_rate;

            particle_distances_so_far = new int[met_particle_spawn_rate];
            particle_alphas = new int[met_particle_spawn_rate];
            particle_seeds = new int[met_particle_spawn_rate];
            particle_rotations = new float[met_particle_spawn_rate];

            for (int i = 0; i < met_particle_spawn_rate; i++) {
                particle_distances_so_far[i] = 0;
                particle_seeds[i] = i + 1;
                particle_alphas[i] = 255;
                particle_rotations[i] = i * 1.234f;
            }
        }

        public void draw_meteor() {

            pushMatrix();
            colorMode(RGB);
            translate(origin_x, origin_y); // set new origin
            // Rotate clockwise
            rotate((float) degrees_tilt * 0.01745f); // degrees to radians
            bands = getSmoothedBands();

            // top left
            float band_amp = bands[(bands.length / 4) * (1)];
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

            // top right
            band_amp = bands[(bands.length / 4) * (3)];
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

            // bottom right
            band_amp = bands[(bands.length / 4) * (2)];
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

            // bottom left
            band_amp = bands[(bands.length - 3)];
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
            calculateAverageAmplitude();
            for (int i = 0; i < particle_spawn_rate; i++) {
                rotate(particle_rotations[i]);

                if (particle_alphas[i] <= 0) {
                    particle_alphas[i] = 255;
                    particle_distances_so_far[i] = 0;
                }
                colorMode(HSB);
                
                fill(getAmplitude()*255, 255, particle_alphas[i], particle_alphas[i]);
                
                ellipse(0, particle_distances_so_far[i], particle_alphas[i] / 16f, particle_alphas[i] / 8f);
                
                colorMode(RGB);

                particle_distances_so_far[i] -= particle_seeds[i];
                particle_alphas[i] -= particle_seeds[i];

            }

            popMatrix();

        }

    }

    public void draw() {

        background(43);
        // Analyse Audio
        calculateAverageAmplitude();
        try {
            calculateFFT();
        } catch (VisualException e) {
            e.printStackTrace();
        }
        calculateFrequencyBands();

        metty.draw_meteor();

    }
}
