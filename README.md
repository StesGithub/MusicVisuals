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




# What I am most proud of in the assignment
1. César - blah blah blah

# Markdown Tutorial

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

