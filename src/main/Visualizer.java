package main;


import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

@SuppressWarnings("serial") 
class Visualizer extends JPanel {
	private Image img;
	private int margin;
	
	Terrain terrain;
	final Spectrum spectrum;
	
	
	Visualizer(Spectrum spectrum) {
		margin = 20;
		this.spectrum = spectrum;
		setRenderingMode(true);
	}
	
	void addClickListener(Editor cc) {
		ClickTerrain list = new ClickTerrain(cc, this);
		addMouseListener(list);


	}
	void setRenderingMode(boolean b) {
		spectrum.interpolate = b;
	}
	
	BufferedImage preview() {
		int size = terrain.size();
		BufferedImage bufferedImage = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				float value = terrain.getHeightAt(i, j);
				Color thisColor = spectrum.colorAtHeight(value).color;
				bufferedImage.setRGB(i, j, thisColor.getRGB());
				
			}
		}
		img = bufferedImage;
		repaint();
		return bufferedImage;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int size = terrain.size()*2;
		
		final BufferedImage resizedImage = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);

		final Graphics2D g2 = resizedImage.createGraphics();

		g2.setComposite(AlphaComposite.Src);
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g2.drawImage(img, 0, 0, size, size, null);
		g2.dispose();
		g.drawImage(resizedImage, margin, margin, this);


	}

}
