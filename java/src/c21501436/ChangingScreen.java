package c21501436;

import processing.core.PApplet;

public class ChangingScreen extends PApplet {

  int backgroundColor;

  public void settings() {
    size(500, 500);
  }

  public void setup() {
    backgroundColor = color(0, 0, 0); // Start with a black background
  }

  public void draw() {
    background(backgroundColor); // Set the background color

    // Randomly adjust the RGB values of the background color
    int r = (int) (red(backgroundColor) + (int) random(-5, 5));
    int g = (int) (green(backgroundColor) + (int) random(-5, 5));
    int b = (int) (blue(backgroundColor) + (int) random(-5, 5));

    // Keep the RGB values within the valid range of 0 to 255
    r = constrain(r, 0, 255);
    g = constrain(g, 0, 255);
    b = constrain(b, 0, 255);

    // Update the background color with the new RGB values
    backgroundColor = color(r, g, b);
  }

}
