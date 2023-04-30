package c21501436;

import processing.core.PApplet;
import processing.core.PConstants;

public class Sun {
  private final PApplet sketch;
  private final float sunSize;
  private final float eyeSize;
  private final float mouthSize;
  private final float mouthAngle;
  
  public Sun(PApplet sketch, float sunSize) {
    this.sketch = sketch;
    this.sunSize = sunSize;
    this.eyeSize = sunSize / 8;
    this.mouthSize = sunSize / 2;
    this.mouthAngle = PApplet.radians(20);
  }

  public void display() {
    sketch.colorMode(PConstants.RGB, 255);
    
    // Flashing yellow color
    float t = sketch.millis() / 1000.0f;
    float brightness = 255 * (0.5f + 0.5f * PApplet.sin(2 * PConstants.PI * t / 5));
    sketch.fill(255, brightness, 0);
    
    // Draw the sun
    float x = sketch.width - sunSize/2 - 20;
    float y = sunSize/2 + 20;
    sketch.ellipse(x, y, sunSize, sunSize);

    // Draw the eyes
    sketch.fill(0);
    sketch.ellipse(x - sunSize/4, y - sunSize/4, eyeSize, eyeSize);
    sketch.ellipse(x + sunSize/4, y - sunSize/4, eyeSize, eyeSize);

    // Draw the mouth (smiling)
    sketch.arc(x, y + sunSize/8, mouthSize, mouthSize, mouthAngle, PConstants.PI-mouthAngle, PConstants.OPEN);
  }
}
