package c21501436;

import processing.core.PApplet;

public class ChangingScreen extends PApplet {

  int[] colors = {color(255, 242, 0), color(128, 0, 128), color(255, 69, 0), color(30, 144, 255), color(255, 99, 71), color(218, 165, 32)};
  int currentColor = 0;
  Sun sun; 

  public void settings() {
    size(500, 500);
  }

  public void setup() {
      size(500, 500);
      sun = new Sun(this, frameRate);
      background(colors[currentColor]);
  }

  public void draw() {
    int nextColor = (currentColor + 1) % colors.length;
    float t = (float) frameCount / 300.0f; // Cycle every 2 seconds
  
    // Interpolate between the current and next colors using a sine function
    int r = (int) (red(colors[currentColor]) + (red(colors[nextColor]) - red(colors[currentColor])) * 0.5f * (1.0f + sin(TWO_PI * t)));
    int g = (int) (green(colors[currentColor]) + (green(colors[nextColor]) - green(colors[currentColor])) * 0.5f * (1.0f + sin(TWO_PI * t)));
    int b = (int) (blue(colors[currentColor]) + (blue(colors[nextColor]) - blue(colors[currentColor])) * 0.5f * (1.0f + sin(TWO_PI * t)));
  
    // Keep the RGB values within the valid range of 0 to 255
    r = constrain(r, 0, 255);
    g = constrain(g, 0, 255);
    b = constrain(b, 0, 255);
  
    // Set the background color to the interpolated color
    background(r, g, b);
  
    // Update the current color if enough time has passed
    if (frameCount % 560 == 0) {
      currentColor = nextColor;
    }
  
    // Add some Van Gogh-like swirls
    noStroke();
    fill(255, 128, 192, 30); // Vivid pink color with alpha value of 30
    for (int i = 0; i < 10; i++) {
      float x = random(width);
      float y = random(height);
      float radius = random(50, 100);
      float angleOffset = random(TWO_PI);
      beginShape();
      for (float angle = 0; angle < TWO_PI; angle += 0.1) {
        float px = x + radius * cos(angle + angleOffset);
        float py = y + radius * sin(angle + angleOffset);
        vertex(px, py);
      }
      endShape(CLOSE);
    }
  
    // Display the sun
    sun.display();
  }
  

  public static void main(String[] args) {
    PApplet.main("c21501436.ChangingScreen");
  }
}
