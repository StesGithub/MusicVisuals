# Music Visualiser Project

## Group Members
| Student Number | Student Name |
|-----------|-----------|
|C21415142 | César Hannin |
|C21447352 | Liam Tuite |
|C21501436 | Oisin Keily |
|C21394693 | Stephen Thompson |
 

## Description
This audio visualisation of Skeeter Davises' (1962) "The End of the World" follows the story of a lone individual walking down the endless street as there is a meteor in the sky. As he walks down the street, we can see how his town and suffered more damage from people panicking than the meteor itself. The background changes slowly as the song progresses, while the city skyscrapers in the background red in fire, as well as the trail behind the meteor, reacting to the amplitude of the song.

## Quickstart Guide
- Navigate to the **java/** folder
- Run **Compile_and_Run.bat** (or **Compile_and_Run.sh** if using a UNIX system)
- Enjoy
- Feel free to run the other **.bat** scripts, as these were visuals which were later incorporated themselves into the main visual

## How it works
1. We initialised all of the variables and objects to be used throughout the project
```Java
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
```

### César - How the Meteor class works
	- The Meteor class first starts by being initiallised with an admititadly large constructor, with 16 required parametres, however, due to the use of **pushmatrix()** and **popmatrix()** the meteor class and object had to be retained within the PApplet, otherwise compilation and runtime errors would occur. The whole constructor is as shown:
```Java
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
```

	- The meteor consists of 3 main parts, these are:
		1. The miniature waveform behind everything
		2. The meteor flipbook
		3. The Particle system

	In the code shown above, the meteor is given an X and Y position to start drawing everything from. Waveforms will be made in each of the 4 corners, with a custom X and Y gap, and each waveform band's width and max height can be passed here, aswell as the other parametres.

	- We start by pushing a new matrix. This allows us to do rotations and translations without affecting the rest of the scene. We preform a minor rotation of the new origion. We then make one part of the total waveform in each corner, with the appropiate initial gap. Here is an example of one corner:
```Java 
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
```

	- We then rotate back to normal and draw our particles. A for loop goes from 0 up to the max number of particles that we specified. Arrays for each particles position, transparency, and rotations exist and we make miniture elipses based on these array values at whatever index we're currently on. 

	- After each particle is drawn we then decrement the opacity a bit. If the tranparency is below 0, we then reset the particular particle's position and size.

	- After drawing the particles, we then draw our Meteor. We reset our matrix and transform as it would've been afected by the particles and their rotations.

	- We see if we need to update our current sprite so that the animation rate remains consistent. The Meteor has a base size, which increases as the song progresses 

### Stephen - How the sprites and animations work. 
	- I designed all of the sprites myself so the art would be unique. I created seperate frames for the city, curtains and our protagonist. These images were fed into their own array lists that we could cycle through during the runtime of our project to create movment.
	- For the curtains: The curtains have their own class and when called by the draw method will initialise all of our drawings as well as positional data we then slow down the animation by only allowing sprite_index to be incremented every two seconds rather than everytime the draw method class the function. This creates a less rushed animation. We then render the elements of the array list in order using sprite_index which creates an animation of opening and closing curtains.
	```Java

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

	```
	- For the dude: We named our protagonist the dude to make it easy to decipher what variables we're working with and also the name felt fitting. For his animation he also has his own class in which their are nested if statements to check if enough time has passed to move to the next frame and another if statemnt to check if our sprite_index is about to go out of bounds and then resetting it to 0 so the animation can conintue for the duration of our song and give the illusion of him walking through the city.
	- For the city: The city has two different versions that seemlessly blend into eahcother during the runtime of the song, to give the illusion of it gradually being destroyed as our meteor gets closer and citizens start to panic. Within our draw method there are if statements that handle these changes, so within the first 5 seconds there's only a nice clean city and then after those five seconds our two city get melded together to create the illusion of the destruction beginning and after 40 seconds the nice city is removed from the rotation leaving only the destroyed city graphic. The city is given the illusion of going on forever by using our repeatable sprite class. This is done by passing our reference images and calling the repeat function which takes our parameters and calculates the number of sprites required to fill the screen, the method then loops through the required number of sprites and draws them at an appropriate x position to fill the screen. The method then updates the starting x position to simulate scrolling.
	- For the StreetLamps: Our street lamps use the same class as our city images to simulate movement so that when the lamp is not visible we get another one drawn in to simulate movement.

### César - How the Repeating sprite class works

The aim of the repeating sprite is to achieve a scrolling effect on a sprite with seamless repetion. You can't see where one sprite spawns and despawns. The width of the sprite is passed, aswell as the gap that should be present inbetween.

a for loop will analyse the width of the sprite, the gap inbetween, and figure out how many sprites are need for seamless scrolling. There is a starting X point where the first image gets drawn. It then goes the width of the image, as well as the gap, and then draws the next. It does this untill we've drawn enough sprites as calculated from before.

When a sprite has scrolled offscreen, if we want it to repeat, it will reset the starting X position to that of the next sprite, AFTER it's already moved, keeping the motion and illusion alive


# What I am most proud of in the assignment
1. César - I'm particularly proud of the ellagance of the repeating image class. In very few lines of code, we achieve a scolling image affect which is perfect in its look. The checks for visability also allows us to have new images to be repeated instead, this allows us to seamlessly have different backgrounds scoll in.
2. Stephen - I am most proud of the assets I designed and how seamlessly everything came togehter. All of the assets and animations compliment eachother and the whole experience feels cohesive and I think all of our collaboration does a great job of telling the story of the song.

# The Sprites

The Sprite images for our animations:
 - [Dude 1](images/dude1.PNG)
 - [Dude 2](images/dude2.PNG)
 - [Dude 3](images/dude3.PNG)
 - [City 1](images/streetNoSky.png)
 - [City 2](images/darkerStreet.png)
 - [Lamp](images/lamp.PNG) 
 - [Curtains 1](images/curtains1.PNG)
 - [Curtains 2](images/curtains2.PNG)
 - [Meteor 1](images/meteor1.PNG)
 - [Meteor 2](images/meteor2.PNG)


# Screenshots of the visual



This is a youtube video:

[![YouTube](http://img.youtube.com/vi/J2kHSSFA4NU/0.jpg)](https://www.youtube.com/watch?v=J2kHSSFA4NU)

This is a table:

| Heading 1 | Heading 2 |
|-----------|-----------|
|Some stuff | Some more stuff in this column |
|Some stuff | Some more stuff in this column |
|Some stuff | Some more stuff in this column |
|Some stuff | Some more stuff in this column |

