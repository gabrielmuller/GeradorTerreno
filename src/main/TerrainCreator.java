package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class TerrainCreator {

	Interface interf;
	Visualizer visualizer;
	float zoom;
	long seed;
	Image img;
	float seaLevel;
	PerlinNoise map;
	int size;
	boolean island;
	public int margin;
	public Spectrum spectrum;

	boolean fixed;
	Terrain fixedTerrain;
	
	public TerrainCreator (Interface i) {
		this.interf = i;
		interf.changeColorCheckbox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				visualizer.setRenderingMode(!interf.isChangingColor());
				visualizer.preview();

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
		
		fixed = false;
		size = 300;
		margin = 20;
		seaLevel = 0;
		Color[] pos = { new Color(239, 235, 201), new Color(115, 196, 78), new Color(237, 247, 255)};
		Color[] neg = { new Color(110, 173, 221), new Color(22, 58, 86)};
		spectrum = new Spectrum(neg, pos);

		Editor cc = new Editor(i, spectrum, this);


        visualizer = new Visualizer(spectrum);
        visualizer.addClickListener(cc);
        update();

	}



	public void createTerrainPreview() {
		seed = interf.getSeed();
		island = interf.isIsland();
		zoom = interf.getZoom();
		map = new PerlinNoise(size, size, seed);
		spectrum.interpolate = !interf.isChangingColor();
		seaLevel = interf.getSeaLevel();
		BufferedImage bufferedImage = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
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

	Terrain createTerrain() {
        seed = interf.getSeed();
        island = interf.isIsland();
        zoom = interf.getZoom();
        map = new PerlinNoise(size, size, seed);
		seaLevel = interf.getSeaLevel();

        Terrain t = new Terrain (size);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
            	t.setHeightAt(valueAtPoint(i,j), i, j);
            }
        }
        t.normalize();
        t.level(interf.getSeaLevel());
        
        return t;

    }

	public void update() {
		update(createTerrain());
	}
	
	public void update(Terrain t) {
		fixedTerrain = t;
		visualizer.terrain = fixedTerrain;
		visualizer.preview();

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
		// return mapa.fractalNoise(i, j);
		int center = size / 2;
		int distI = i - center;
		int distJ = j - center;
		float distanceToCenter = (float) Math.sqrt(distI * distI + distJ * distJ);
		distanceToCenter /= center;
		distanceToCenter = clamp(distanceToCenter);
		float centerWeight = 1f;
		return ((map.fractalNoise(i, j, zoom) + 1) / 2 - distanceToCenter * centerWeight);
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