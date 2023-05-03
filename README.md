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

2. César - Here's how the meteor works and stuff

3. Stephen - How the sprites and animations work. I designed all of the sprites myself so the art would be unique. I created seperate frames for the city, curtains, meteor and our protagonist. These images were fed into their own array lists that we could cycle through them during the runtime of our project to create movment.
	- For the curtains: The curtains have their own class and when called by the draw method will initialise all of our drawings as well as positional data we then slow down the animation by only allowing sprite_index to be incremented every two seconds rather than everytime the draw method class the function. This creates a less rushed animation. We then render the elements of the array list in order using sprite_index which creates an animation of opening and closing curtains.
	- For the dude: We named our protagonist the dude to make it easy to decipher what variables we're working with and also the name felt fitting. For his animation he also has his own class in which their are nested if statements to check if enough time has passed to move to the next frame and another if statemnt to check if our sprite_index is about to go out of bounds and then resetting it to 0 so the animation can conintue for the duration of our song and give the illusion of him walking through the city.
	- For the city: The city has two different versions that seemlessly blend into eahcother during the runtime of the song, to give the illusion of it gradually being destroyed as our meteor gets closer and citizens start to panic. Within our draw method there are if statements that handle these changes, so within the first 5 seconds there's only a nice clean city and then after those five seconds our two city get melded together to create the illusion of the destruction beginning and after 40 seconds the nice city is removed from the rotation leaving only the destroyed city graphic. The city is given the illusion of going on forever by using our repeatable sprite class. This is done by passing our reference images and calling the repeat function which takes our parameters and calculates the number of sprites required to fill the screen, the method then loops through the required number of sprites and draws them at an appropriate x position to fill the screen. The method then updates the starting x position to simulate scrolling.
	- For the StreetLamps: Our street lamps use the same class as our city images to simulate movement so that when the lamp is not visible we get another one drawn in to simulate movement.





# What I am most proud of in the assignment
1. César - blah blah blah
2. Stephen - I am most proud of the assets I designed and how seamlessly everything came togehter. All of the assets and animations compliment eachother and the whole experience feels cohesive and I think all of our collaboration does a great job of telling the story of the song.

# Markdown Tutorial

The Sprite images for our animations:
 - [Dude 1](images/dude1.PNG)
 - [Dude 2](images/dude2.PNG)
 - [Dude 3](images/dude3.PNG)
 - [City 1](images/streetNoSky.png)
 - [City 2](images/dude1.png)
 - [Lamp](images/lamp.PNG) 
 - [Curtains 1](images/curtains1.PNG)
 - [Curtains 2](images/curtains2.PNG)
 - [Meteor 1](images/meteor1.PNG)
 - [Meteor 2](images/meteor2.PNG)





This is *emphasis*

This is a bulleted list

- Item
- Item

This is a numbered list

1. Item
1. Item

This is a [hyperlink](http://bryanduggan.org)

# Headings
## Headings
#### Headings
##### Headings

This is code:

```Java
public void render()
{
	ui.noFill();
	ui.stroke(255);
	ui.rect(x, y, width, height);
	ui.textAlign(PApplet.CENTER, PApplet.CENTER);
	ui.text(text, x + width * 0.5f, y + height * 0.5f);
}
```

So is this without specifying the language:

```
public void render()
{
	ui.noFill();
	ui.stroke(255);
	ui.rect(x, y, width, height);
	ui.textAlign(PApplet.CENTER, PApplet.CENTER);
	ui.text(text, x + width * 0.5f, y + height * 0.5f);
}
```

This is an image using a relative URL:

![An image](images/p8.png)

This is an image using an absolute URL:

![A different image](https://bryanduggandotorg.files.wordpress.com/2019/02/infinite-forms-00045.png?w=595&h=&zoom=2)

This is a youtube video:

[![YouTube](http://img.youtube.com/vi/J2kHSSFA4NU/0.jpg)](https://www.youtube.com/watch?v=J2kHSSFA4NU)

This is a table:

| Heading 1 | Heading 2 |
|-----------|-----------|
|Some stuff | Some more stuff in this column |
|Some stuff | Some more stuff in this column |
|Some stuff | Some more stuff in this column |
|Some stuff | Some more stuff in this column |

