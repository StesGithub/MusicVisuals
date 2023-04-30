package c21447352;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.*;
import java.awt.image.*;

public class FireWaveform extends JPanel implements ActionListener {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int WAVE_LENGTH = 200;
    private static final int AMPLITUDE = 50;
    private static final double SPEED = 0.1;
    private static final int NUM_WAVES = 5;
    private static final int SPARK_SIZE = 10;
    private static final int SPARK_LIFE = 20;
    
    private Timer timer;
    private double[] waveValues;
    private int[] pixels;
    private List<Spark> sparks;
    private Random random;
    
    public FireWaveform() {
        waveValues = new double[WAVE_LENGTH];
        pixels = new int[WIDTH * HEIGHT];
        sparks = new ArrayList<Spark>();
        random = new Random();
        
        setBackground(Color.BLACK);
        
        timer = new Timer(20, this);
        timer.start();
    }
    
    public void actionPerformed(ActionEvent e) {
        updateWaveValues();
        updateSparks();
        repaint();
    }
    
    private void updateWaveValues() {
        double t = System.currentTimeMillis() * SPEED;
        
        for (int i = 0; i < WAVE_LENGTH; i++) {
            double x = (double)i / WAVE_LENGTH * 2 * Math.PI;
            double y = 0;
            
            for (int j = 0; j < NUM_WAVES; j++) {
                double frequency = Math.pow(2, j);
                double amplitude = Math.pow(0.5, j);
                
                y += amplitude * Math.sin(frequency * x + t);
            }
            
            waveValues[i] = y;
        }
    }
    
    private void updateSparks() {
        if (random.nextDouble() < 0.02) {
            int x = random.nextInt(WIDTH);
            int y = 0;
            int vx = random.nextInt(5) - 2;
            int vy = random.nextInt(5) + 2;
            int life = SPARK_LIFE;
            
            sparks.add(new Spark(x, y, vx, vy, life));
        }
        
        for (int i = 0; i < sparks.size(); i++) {
            Spark spark = sparks.get(i);
            
            spark.x += spark.vx;
            spark.y += spark.vy;
            spark.life--;
            
            if (spark.life <= 0) {
                sparks.remove(i);
                i--;
            }
        }
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        int centerX = WIDTH / 2;
        int centerY = HEIGHT / 2;
        
        for (int i = 0; i < WAVE_LENGTH; i++) {
            int x = i * WIDTH / WAVE_LENGTH;
            int y = (int)(centerY + waveValues[i] * AMPLITUDE);
            
            for (int j = -5; j <= 5; j++) {
                int pixelIndex = (y + j) * WIDTH + x;
                
                if (pixelIndex >= 0 && pixelIndex < pixels.length) {
                    int intensity = Math.abs(j) * 50;
                    int r = Math.min (intensity, 255);
                    int gVal = Math.max(255 - intensity * 2, 0);
                    int bVal = Math.max(255 - intensity * 2, 0);
                    
                    Color color = new Color(r, gVal, bVal);
                    pixels[pixelIndex] = color.getRGB();
                }
            }
        }
        
        for (Spark spark : sparks) {
            for (int i = -SPARK_SIZE; i <= SPARK_SIZE; i++) {
                for (int j = -SPARK_SIZE; j <= SPARK_SIZE; j++) {
                    int x = spark.x + i;
                    int y = spark.y + j;
                    
                    if (x >= 0 && x < WIDTH && y >= 0 && y < HEIGHT) {
                        int pixelIndex = y * WIDTH + x;
                        int intensity = (int)(spark.life / (double)SPARK_LIFE * 255);
                        
                        if (pixelIndex >= 0 && pixelIndex < pixels.length) {
                            Color color = new Color(255, intensity, 0);
                            pixels[pixelIndex] = color.getRGB();
                        }
                    }
                }
            }
        }
        
        g.drawImage(createImage(new MemoryImageSource(WIDTH, HEIGHT, pixels, 0, WIDTH)), 0, 0, this);
    }
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("Fire Waveform");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        
        FireWaveform waveform = new FireWaveform();
        frame.add(waveform);
        
        frame.setVisible(true);
    }
    
    private class Spark {
        public int x;
        public int y;
        public int vx;
        public int vy;
        public int life;
        
        public Spark(int x, int y, int vx, int vy, int life) {
            this.x = x;
            this.y = y;
            this.vx = vx;
            this.vy = vy;
            this.life = life;
        }
    }
}    
