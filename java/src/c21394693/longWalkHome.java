package c21394693;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import ddf.minim.AudioBuffer;
import ddf.minim.AudioInput;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/*
 * This class creates a "dude" sprite 
 * and runs a background image to make it look like hes moving through a town
 * Background image has been loaded in 
 * dude can jump and we have a lamp that scrolls across the screen to simulate movement
 * Hopefully I can replace with a scrolling background
 * Music to be added and other events to make it actually fun to watch
 * Jump works pretty well though
 * Still trying to get the song to play
 */

public class longWalkHome extends JFrame {

    private static final int WINDOW_WIDTH = 1200; // Width of game window
    private static final int WINDOW_HEIGHT = 600; // Height of game window
    private static final int GROUND_HEIGHT = 50; // Height of the ground
    private Minim minim;
    //public AudioPlayer song;

    private boolean isJumping = false;
    private int dudeY = WINDOW_HEIGHT - GROUND_HEIGHT - 100; // Y-coordinate of the dude
    private int dudeYSpeed = 0; // Vertical speed of the dude
    private int lampX = WINDOW_WIDTH; // X-coordinate of the lamp
    Image backgroundImage;
    Image dudeImage;
    Image streetLampImage;
    int dudeWidth = 50; // Width of the dude image
    int dudeHeight = 50; // Height of the dude image

/*
    public void setup() 
    {
        minim = new Minim(this);
        song = minim.loadFile("endOfTheWorld.mp3");
        System.out.println("Step 1");
        song.play();
    }
    */
    public longWalkHome() 
    {
        System.out.println("Step 1");
        AudioPlayer song;
        minim = new Minim(this);
        song = minim.loadFile("endOfTheWorld.mp3");
        setTitle("Long Walk Home");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        System.out.println("Step 2");
        song.play();
        
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                backgroundImage = new ImageIcon("Shapes_and_Sprites/street.png").getImage();
                //be aware this currently isnt working
                //change it to dude.png if you want something to render
                dudeImage = new ImageIcon("Shapes_and_Sprites/dude-20230428-195756.piskel").getImage();
                streetLampImage = new ImageIcon("streetlamp.png").getImage();

                Graphics2D g2D = (Graphics2D) g;
                // background town image
                g2D.drawImage(backgroundImage, 0, 0, null);

                // Draw ground
                g.setColor(Color.LIGHT_GRAY);
                g.fillRect(0, WINDOW_HEIGHT - GROUND_HEIGHT, WINDOW_WIDTH, GROUND_HEIGHT);

                // Draw dude
                g2D.drawImage(dudeImage, 100, dudeY, dudeWidth, dudeHeight, null);

                // Draw "lamp"
                //g.setColor(Color.GRAY);
                g2D.drawImage(streetLampImage, lampX, WINDOW_HEIGHT - GROUND_HEIGHT - streetLampImage.getHeight(null), streetLampImage.getWidth(null), streetLampImage.getHeight(null), null);
                //g.fillRect(lampX, WINDOW_HEIGHT - GROUND_HEIGHT - 50, 20, 100);

            }
        };

        panel.setFocusable(true);
        panel.requestFocus();
        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE && !isJumping) {
                    // Space key is pressed and dude is not already jumping
                    isJumping = true;
                    dudeYSpeed = -10; // Set vertical speed to negative value to make the dude jump
                }
            }
        });

    

        Timer timer = new Timer(20, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {// Update dude position
                dudeY += dudeYSpeed;
                dudeYSpeed += 1; // Apply gravity

                // Check for collision with ground
                if (dudeY >= WINDOW_HEIGHT - GROUND_HEIGHT - dudeHeight) {
                    dudeY = WINDOW_HEIGHT - GROUND_HEIGHT - dudeHeight; // Set dude back on ground
                    isJumping = false; // Reset jumping flag
                }

                // Update lamp position
                lampX -= 5; // Move lamp towards left

                // Check if lamp is out of screen, reset its position
                if (lampX + 20 < 0) {
                    lampX = WINDOW_WIDTH;
                }

                panel.repaint(); // Redraw the panel
            }
        });
        timer.start();

        add(panel);
        setVisible(true);
    }

    
}