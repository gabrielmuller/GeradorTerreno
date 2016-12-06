package main;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

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

		interf.saveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				saveTerrain();
			}

		});
		
		interf.pngButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				savePng();
			}
			
		});
		
		interf.openButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				loadTerrain();
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
		Spectrum spectrum = new Spectrum(neg, pos);



        visualizer = new Visualizer(spectrum);
		Editor cc = new Editor(i, visualizer.spectrum, this);

        visualizer.addClickListener(cc);
        update();

	}

	public void saveTerrain() {
		JFileChooser jfc = new TerrainFileChooser(false);
		
		if (jfc.showOpenDialog(null) != JFileChooser.APPROVE_OPTION)
			return;
		
		File input = jfc.getSelectedFile();
		
		
		String path = input.getAbsolutePath();
		
		if (!path.endsWith(".ter")) {
			path = path + ".ter";
		}
		TerrainOutput to = new TerrainOutput(path);
		to.writeFile(fixedTerrain.elevation, visualizer.spectrum);
	}
	
	public void savePng() {
		JFileChooser jfc = new TerrainFileChooser(false);
		
		if (jfc.showOpenDialog(null) != JFileChooser.APPROVE_OPTION)
			return;
		
		File input = jfc.getSelectedFile();
		
		String path = input.getAbsolutePath();

		if (path.contains(".")){
			int index = path.indexOf(".");
			path = path.substring(0, index);
			path = path.concat(".png");
		}
		
		PNGSaver p = new PNGSaver(path);
		p.save(visualizer.preview());
	}
	
	public void loadTerrain() {
		
		JFileChooser jfc = new TerrainFileChooser(true);
		
		if (jfc.showOpenDialog(null) != JFileChooser.APPROVE_OPTION)
			return;
		
		File input = jfc.getSelectedFile();
		

		if (input.toString().endsWith(".ter")) {
			TerrainInput ti = new TerrainInput(input.getAbsolutePath());
			Terrain t = new Terrain(size);
			TerrainInfo tinfo = ti.fileToFloatMatrix(fixedTerrain.size());
			t.elevation = tinfo.elevation;
			visualizer.spectrum.clone(tinfo.spectrum);
			update(t);
		} else {
			JOptionPane.showMessageDialog(null, "Arquivo inv�lido. Selecione um arquivo tipo .ter .");
		}
		
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
