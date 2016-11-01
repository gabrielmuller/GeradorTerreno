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
	
	long seed;
	Image img;
	PerlinNoise mapa;
	int size;
	public int margin;
	public Spectrum spectrum;

	public Terrain(Interface i) {
		this.interf = i;
        interf.changeColorCheckbox.addActionListener(new ActionListener () {

			@Override
			public void actionPerformed(ActionEvent event) {
				update();
				
			}
        	
        });

		size = 600;
		margin = 20;
		String input = /*JOptionPane.showInputDialog("seed:")*/"kek";
		input = input.replaceAll("\\s+", "");
		if (input.length() > 8) {
			input = input.substring(0, 8);
		}

		Color[] pos = { new Color(239, 235, 201), new Color(115, 196, 78), new Color(237, 247, 255), Color.white };
		Color[] neg = { new Color(110, 173, 221), new Color(22, 58, 86) };
		spectrum = new Spectrum(neg, pos);

		ColorChanger cc = new ColorChanger(i, spectrum, this);
		ClickTerrain list = new ClickTerrain(cc, this);
		addMouseListener(list);

		seed = Long.parseLong(input, 36);
		mapa = new PerlinNoise(size, size, seed);
		createTerrain();

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(img, margin, margin, this);
		// g.drawOval(50, 50, 500, 500);
	}

	public void createTerrain() {
		spectrum.interpolate = !interf.isChangingColor();
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
	
	public void update () {
		createTerrain();
		repaint();
	}
	private Color floatToColor(float f) {
		f = (f + 1f) / 2f;
		Color c = new Color(f, f, f);
		return c;
	}

	private float tratar(int i) {
		return i - 0.5f;
	}

	public float valueAtPoint(int i, int j) {
		float ii = tratar(i);
		float jj = tratar(j);
		return mapa.fractalNoise(ii, jj);

	}

}