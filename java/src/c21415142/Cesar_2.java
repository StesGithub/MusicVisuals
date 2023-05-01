package c21415142;

import ie.tudublin.Visual;
import ie.tudublin.VisualException;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PGraphics;

import java.io.File;
import java.util.Random;

import c21415142.vector3;
import c21415142.misc_math;




// - - - MAIN - - -
public class Cesar_2 extends Visual
{
    // Values to be referenced and init

    static float delta_seconds; // how long each frame took to render
    PGraphics HUD;
    PFont font;
    float[] bands;

    // - Camera
    static Cesar_Camera scene_camera;
    static vector3[] camera_offset_to_apply = { new vector3(0,0, 0),  //location offsets
                                                new vector3(0, -1, 0)}; //axis

    static vector3 scene_origin             = new vector3(0, 0, 0);
    static float camera_movement_speed = 2f;
    static float camera_movement_horizontal = 50f;
    static float camera_movement_up_down = 30f;
    static float camera_movement_front_back = 1f;
    static float camera_FOV = 80f;
    
    //Music
    static int song_position = 0; // miliseconds
    static boolean song_pause_toggle = false;
    
    public SCENE scene_1 = new SCENE_1();

    
    public void settings()
    {
        size(800, 600, P3D);
    }

    public void keyReleased()
    {
        if (key == ' ')
        {
            if(song_pause_toggle == true)
            {
                getAudioPlayer().play();
                song_pause_toggle = false;
            }
            else
            {
                getAudioPlayer().pause();
                song_pause_toggle = true;
            }
        }
    }

    public void keyPressed() // Maybe a switch would be better? does java do switches???
    {
        // CAMERA CONTROLS
        // - Rotate left and right
        if (key == 'd')
        {
            camera_offset_to_apply[0].x += (camera_movement_horizontal * delta_seconds * camera_movement_speed);
        }
        else if (key == 'a')
        {
            camera_offset_to_apply[0].x -= (camera_movement_horizontal * delta_seconds * camera_movement_speed);
        }
        
        // - Go back and forth
        if (key == 'w')
        {
            camera_offset_to_apply[0].z += (camera_movement_front_back * delta_seconds * camera_movement_speed);
        }
        else if (key == 's')
        {
            camera_offset_to_apply[0].z -= (camera_movement_front_back * delta_seconds * camera_movement_speed);
        }
        
        // - Go Up and Down
        if (key == 'e')
        {
            camera_offset_to_apply[0].y += (camera_movement_up_down * delta_seconds * camera_movement_speed);
        }
        else if (key == 'q')
        {
            camera_offset_to_apply[0].y -= (camera_movement_up_down * delta_seconds * camera_movement_speed);
        }
        
        if (key == 'x') //increase FOV
        {
            camera_FOV = misc_math.Clamp(1, 179.9f, camera_FOV += (10f * delta_seconds) );
        }
        else if (key == 'z') //decrease FOV
        {
            camera_FOV = misc_math.Clamp(1, 179.9f, camera_FOV -= (10f * delta_seconds) );
        }
    }



    public void setup()
    {
        surface.setResizable(true);
        frameRate(30); //lock to 30 fps
        
        colorMode(HSB);
        setFrameSize(512); 
        
        scene_camera = new Cesar_Camera(new vector3(0, 0, 0));
        scene_camera.end_vision_distance = 999990;

        font = createFont("Comic Sans MS", 64); // use the system's version of the arial font
        textFont(font);
        HUD = createGraphics(width, height);

        startMinim();
        loadAudio("endOfTheWorld.mp3");     
    }

    

    // Camera Class
    public class Cesar_Camera 
    {
        //public
        public vector3 location;
        public vector3 axis;
        public float aspect_ratio = (float)width / (float)height; 
        public float start_vision_distance = 0.01f; // Near-clip plane
        public float end_vision_distance = 8192f;   // far-clip plane
        public float FOV;
        
        //Methods
        public Cesar_Camera(vector3 starting_location)
        {
            location = starting_location;
            
            update_camera(false);
        }

        public float get_camera_fov_rad()
        {
            return FOV * 0.0174533f;
        }

        //updates
        public void update_camera(boolean reset_offset)
        {
            //apply movement - location
            location.rotate_around_point(scene_origin, camera_offset_to_apply[0].x);
            location.move_to_point(scene_origin, camera_offset_to_apply[0].z);
            location.y += camera_offset_to_apply[0].y;

            if(reset_offset == true)
            {
                camera_offset_to_apply[0].reset();  
            }

            // axis change
            axis = camera_offset_to_apply[1];

            this.FOV = camera_FOV;

            aspect_ratio = (float)width / (float)height;

            camera( location.x, location.y, location.z,  // Location
                    scene_origin.x, scene_origin.y, scene_origin.z,  // centre of scene
                    axis.x, axis.y, axis.z );// Y-Axis is up
            
            perspective(get_camera_fov_rad(), aspect_ratio, start_vision_distance, end_vision_distance);
        }
    }// end camera class


    public class SCENE
    {
        private boolean T_init = true;
        private String scene_name = "Untitled";
        
        public SCENE(String title)
        {
            scene_name = title;
        }
        
        public void scene_setup()
        {
            if(T_init == true)
            {
                this.init();
                System.out.println("Switching to and setting up scene: "+ scene_name);
                T_init = false;
            }
        }
        
        public void init()
        {
            getAudioPlayer().cue(0); // go to 0 miliseconds into the song
            getAudioPlayer().play(); // initiate Death Grips
        }


        public void draw_scene()
        {
            song_position = getAudioPlayer().position();
        }
    }
   
    public class SCENE_1 extends SCENE
    {
        public float rotation = 0f;
        public float Y_offset;
        public float mini_offset;
        public int max_height;
        public float sensitivity;
        public float radius;
        public int rings;
        public float theta;
        public float h;
        public boolean draw_2nd = true;
        public float x;
        public float z;

        public SCENE_1()
        {
            super("Scene 1");
        }

       
        public void init()
        {
            super.init();

            mini_offset = 50;
            max_height = 200;
            sensitivity = 0.05f;
            radius = 15;
            rings = 150;

            getAudioPlayer().cue(0); // go to 0 miliseconds into the song
            getAudioPlayer().play(); // initiate Death Grips

            scene_camera.location = new vector3(0, -max_height - (mini_offset/2), -2000);
            camera_offset_to_apply[0] = new vector3(0,0,0);
        }
        
        
        public void draw_scene()
        {     
            super.draw_scene();

            scene_origin.y = -max_height - (mini_offset/2);
            rotation += getAmplitude() / 8.0f; // increase rotation based on song

            rotateY(rotation);

            background(0);

            //Geometry Time
            //SPIRAL        
            bands = getSmoothedBands();

            for(int i = 0 ; i < bands.length ; i ++)
            {
                // Mathmatical Black Magic
                Y_offset = 0;
                
                
                theta = map(i, 0, bands.length, 0, TWO_PI);
                h = misc_math.Clamp(1, 30, bands[i] / 5);
                x = sin(theta) * (radius/1.3f) ;
                z = cos(theta) * (radius/1.3f) ;
                
                for (int r = 1; r < rings; r++) // 75 rings... overkill
                {
                    fill(map(i, 0, bands.length, 0, 255), 200, 200, (255 / (r) +50) );
                    stroke(map(i, 0, bands.length, 0, 255), 255, 255); // Change colour depending on which band we're on
                    
                    pushMatrix();
                                 
                    Y_offset = misc_math.Clamp(-150, max_height, h * (h / (r * sensitivity)) );

                    
                    translate( x * (r * 0.4f), -max_height - (-Y_offset / 2), z * (r * 0.4f) );
                    rotateY(theta);
                    box( (20 * r) / getBands().length, Y_offset, 2); 
                    if(draw_2nd == true)
                    {
                        fill(0);
                        stroke(255);
                        translate( 0,  -mini_offset-Y_offset, 200 );
                        
                        box( (20 * r) / getBands().length, Y_offset, -2); 
                    }
                    
                    popMatrix();  
                }//end for-loop for ring parts for this particular band
            }
            ShowHUD(150, -100);
                  
        }//end draw
    }

    public void ShowHUD()
    {
        ShowHUD(0,0);
    }


    public void ShowHUD(float offset_x, float offset_y)
    {
        hint(DISABLE_DEPTH_TEST); // place ON TOP of everything
        
        camera(); // Reset the camera, wont work otherwise
        ortho(-width/2 + offset_x, width/2 + offset_x, -height/2 + offset_y, height/2 + offset_y, 0.001f, 900f);
        HUD.beginDraw();
        HUD.clear(); //clear everything out first
        HUD.textAlign(LEFT, TOP);

        HUD.mask(HUD); // Make a graphics mask for JUST the hud
        textSize(20);
        fill(255);
        text(("FPS: " + (int)frameRate),  width/3.2f, (height/3));
        text(("\nWindow Width: " + width + " | Height: " + height), width/3.2f, (height/3) );
        text(("\n\nCamera location: " + scene_camera.location.string_version() ), width/3.2f, (height/3) );
        text(("\n\n\nCamera Offsets: " + camera_offset_to_apply[0].string_version(2) ), width/3.2f, (height/3) );
        text(("\n\n\n\nCamera FOV: " + scene_camera.FOV ), width/3.2f, (height/3) );
        text(("\n\n\n\n\nAmplitude(): " + getAmplitude() ), width/3.2f, (height/3) );
        
        HUD.endDraw();
        image(HUD, 0, 0); // Draw the HUD graphics onto the main canvas at position (0, 0)

        hint(ENABLE_DEPTH_TEST);
    }



    public void draw()
    {
        delta_seconds = 1f/frameRate;
        
        // Are any keys being pressed?
        if(keyPressed == true)  { keyPressed(); }
        
        
        //Analyse Audio
        calculateAverageAmplitude();
        try { calculateFFT(); }
        catch(VisualException e)    { e.printStackTrace(); }
        calculateFrequencyBands();
        
        //debug
        String camera_debug = " :: Camera Position = " + scene_camera.location.string_version(1);
        String FPS_debug = " :: FPS = " + Float.toString(frameRate);
        String DS_debug = " :: Delta Seconds = " + Float.toString(delta_seconds);
        String FOV_debug = " :: FOV  = " + Float.toString(scene_camera.FOV);
        //System.out.printf("\n" + getAmplitude() );
        
        // Try change scenes later
        scene_1.scene_setup();
        scene_camera.update_camera(true);
        scene_1.draw_scene();

    }
}
