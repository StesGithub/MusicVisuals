package c21501436;

import processing.core.PApplet;

public class LightningStorm extends PApplet {

  private static final int WINDOW_WIDTH = 1200;
  private static final int WINDOW_HEIGHT = 600;

  private int color1, color2, color3, color4;

  public void settings() {
    size(WINDOW_WIDTH, WINDOW_HEIGHT);
  }

  public void setup() {
    color1 = color(13, 14, 14);
    color2 = color(255, 0, 0);
    color3 = color(200, 50, 200);
    color4 = color(50, 50, 100);
    
    background(color1);
  }

  public void draw() {
    int duration1 = 5000;
    int duration2 = 10000;
    int duration3 = 15000;

    int t = millis() % (duration1 + duration2 + duration3);

    if (t < duration1) {
      float p = (float)t / duration1;
      background(lerpColor(color1, color2, p));
    } else if (t < duration1 + duration2) {
      float p = (float)(t - duration1) / duration2;
      background(lerpColor(color2, color3, p));
    } else {
      float p = (float)(t - duration1 - duration2) / duration3;
      background(lerpColor(color3, color4, p));
    }
  }


  /*package c21501436;

import processing.core.PApplet;

public class LightningStorm extends PApplet {

  private static final int WINDOW_WIDTH = 1200;
  private static final int WINDOW_HEIGHT = 600;

  private int color1, color2, color3, color4, color5, color6, color7, color8, color9, color10, color11, color12, color13;

  public void settings() {
    size(WINDOW_WIDTH, WINDOW_HEIGHT);
  }

  public void setup() {
    color1 = color(13, 14, 14);
    color2 = color(0,255,255);
    color3 = color(0,204,204);
    color4 = color(255,255,0);
    color5 = color(255,153,51);
    color6 = color(255,102,102);
    color7 = color(255,0,0);
    color8 = color(255,51,255);
    color9 = color(204,0,102);
    color10 = color(102,0,51);
    color11 = color(0,0,0);
    color12 = color(0,0,102);
    color13 = color(0,0,153);
    
    background(color1);
  }

  public void draw() {
    int duration1 = 5000;
    int duration2 = 10000;
    int duration3 = 15000;
    int duration4 = 20000;
    int duration5 = 25000;
    int duration6 = 30000;
    int duration7 = 35000;
    int duration8 = 40000;
    int duration9 = 45000;
    int duration10 = 50000;
    int duration11 = 55000;
    int duration12 = 60000;
    int duration13 = 65000;

    int t = millis() % (duration1 + duration2 + duration3 + duration4 + duration5 + duration6 + duration7 + duration8 + duration9 + duration10 + duration11 + duration12 + duration13);

    if (t < duration1) {
      float p = (float)t / duration1;
      background(lerpColor(color1, color2, p));
    } else if (t < duration1 + duration2) {
      float p = (float)(t - duration1) / duration2;
      background(lerpColor(color2, color3, p));
    } else if (t < duration1 + duration2 + duration3) {
      float p = (float)(t - duration1 - duration2) / duration3;
      background(lerpColor(color3, color4, p));
    } else if (t < duration1 + duration2 + duration3 + duration4) {
      float p = (float)(t - duration1 - duration2 - duration3) / duration4;
      background(lerpColor(color4, color5, p));
    } else if (t < duration1 + duration2 + duration3 + duration4 + duration5) {
      float p = (float)(t - duration1 - duration2 - duration3 - duration4) / duration5;
      background(lerpColor(color5, color6, p));
    } else if (t < duration1 + duration2 + duration3 + duration4 + duration5 + duration6) {
      float p = (float)(t - duration1 - duration2 - duration3 - duration4 - duration5) / duration6;
      background(lerpColor
 */

}
