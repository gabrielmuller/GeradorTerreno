package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.JColorChooser;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Terrain extends JPanel {

    Interface interf;

    float zoom;
    long seed;
    Image img;
    PerlinNoise map;
    int size;
    boolean island;
    public int margin;
    public Spectrum spectrum;

    public Terrain(Interface i) {
        this.interf = i;
        interf.changeColorCheckbox.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent event) {
                update();

            }

        });

        interf.updateButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                update();
            }

        });
        
        interf.islandCheckbox.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                update();
            }

        });

        size = 600;
        margin = 20;
        

        Color[] pos = {new Color(239, 235, 201), new Color(115, 196, 78), new Color(237, 247, 255), Color.white};
        Color[] neg = {new Color(110, 173, 221), new Color(22, 58, 86)};
        spectrum = new Spectrum(neg, pos);

        ColorChanger cc = new ColorChanger(i, spectrum, this);
        ClickTerrain list = new ClickTerrain(cc, this);
        addMouseListener(list);

        createTerrain();

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, margin, margin, this);
        // g.drawOval(50, 50, 500, 500);
    }

    public void createTerrain() {
        seed = interf.getSeed();
        island = interf.isIsland();
        zoom = interf.getZoom();
        map = new PerlinNoise(size, size, seed);
        spectrum.interpolate = !interf.isChangingColor();
        spectrum.seaLevel = interf.getSeaLevel();
        BufferedImage bufferedImage = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
        Graphics g = bufferedImage.getGraphics();
        Color c = Color.red;
        bufferedImage.setRGB(0, 0, c.getRGB());

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                float value = valueAtPoint(i, j);
                Color thisColor = spectrum.colorAtHeight(value).color;
                bufferedImage.setRGB(i, j, thisColor.getRGB());
            }
        }

        img = bufferedImage;
    }

    public void update() {
        createTerrain();
        repaint();
    }

    private Color floatToColor(float f) {
        f = (f + 1f) / 2f;
        Color c = new Color(f, f, f);
        return c;
    }
    
    public float valueAtPoint(int i, int j) {
        float output;
        if (island) {
            output = islandPoint(i, j);
        } else {
            output = continentPoint(i, j);
        }
        return output;
    }
    
    private float continentPoint(int i, int j) {
        return map.fractalNoise(i, j, zoom);
    }
    private float islandPoint(int i, int j) {
        //return mapa.fractalNoise(i, j);
        int center = size/2;
        int distI = i - center;
        int distJ = j - center;
        float distanceToCenter = (float) Math.sqrt(distI*distI + distJ*distJ);
        distanceToCenter /= center;
        distanceToCenter = clamp(distanceToCenter);
        float centerWeight = 1f;
        return ((map.fractalNoise(i, j, zoom)+1)/2 - distanceToCenter * centerWeight);
    }
    
    private float clamp(float f) {
        if (f > 1) {
            f = 1;
        } else if (f < -1) {
            f = -1;
        }
        return f;
    }

}
