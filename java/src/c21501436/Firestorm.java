package c21501436;

import processing.core.PApplet;
import java.util.ArrayList;

public class Firestorm extends PApplet {

    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 600;
    private static final int GROUND_HEIGHT = 50;
    private static final int BACKGROUND_COLOR = 0;
    private static final int FIREBALL_RADIUS = 15;
    private static final int FIREBALL_SPEED = 10;
    private static final int SMOOTH_TRANSITION_TIME = 3000;
    private int FIREBALL_COLOR;


    private int currentColor;
    private int nextColor;
    private int transitionStartTime;
    private ArrayList<Fireball> fireballs;
    private float x, y, speed, size;
    private int color;

    public void settings() {
        size(WINDOW_WIDTH, WINDOW_HEIGHT, P2D);
    }

    public void setup() {
        currentColor = color(255, 0, 0);
        nextColor = color(148, 0, 211);
        transitionStartTime = millis();
        fireballs = new ArrayList<>();
        noStroke();
        ellipseMode(RADIUS);
        FIREBALL_COLOR = color(255, 165, 0); // Initialize FIREBALL_COLOR in setup()
    }
    

    public void draw() {
        // Calculate the current background color based on the transition time
        float t = (float) (millis() - transitionStartTime) / SMOOTH_TRANSITION_TIME;
        t = constrain(t, 0, 1);
        int bgColor = lerpColor(currentColor, nextColor, t);
        background(bgColor);

        // Create new fireballs at the top of the screen
        if (random(1) < 0.05) {
            fireballs.add(new Fireball());
        }

        // Move and draw the fireballs
        for (int i = fireballs.size() - 1; i >= 0; i--) {
            Fireball fireball = fireballs.get(i);
            fireball.move();
            fireball.draw();
            // Remove the fireball if it goes off the bottom of the screen
            if (fireball.y > WINDOW_HEIGHT) {
                fireballs.remove(i);
            }
        }

        // If the transition is complete, start a new transition
        if (t == 1) {
            currentColor = nextColor;
            nextColor = color(75, 0, 130);
            transitionStartTime = millis();
        }
    }

    private class Fireball {
        float x, y;
        float speedX, speedY;
        int color;

        public Fireball() {
            x = random(WINDOW_WIDTH);
            y = -FIREBALL_RADIUS;
            speedX = random(-2, 2);
            speedY = FIREBALL_SPEED;
            color = FIREBALL_COLOR;
        }

        public void move() {
            x += speedX;
            y += speedY;
        }

        public void draw() {
            fill(color);
            ellipse(x, y, FIREBALL_RADIUS, FIREBALL_RADIUS);
        }
    }
}
