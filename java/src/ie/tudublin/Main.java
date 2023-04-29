package ie.tudublin;

import c21394693.longWalkHome;
import example.CubeVisual;
import example.MyVisual;
import example.RotatingAudioBands;
//import ie.tudublin.Sketch_Select;

public class Main {
	public static int visual_to_run = -1; // -1 just means nothing has been selected
	public static String[] arg = { "MAIN" };


	public void startUI() {
		String[] a = { "MAIN" };
		processing.core.PApplet.runSketch(a, new MyVisual());
	}

	public void RotatingAudioBands() {
		String[] a = { "MAIN" };
		processing.core.PApplet.runSketch(a, new RotatingAudioBands());
	}

	public static void main(String[] args) throws InterruptedException {


		longWalkHome game = new longWalkHome();
		System.out.println("Start");
	}
}