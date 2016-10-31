import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.JColorChooser;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Terreno extends JPanel {
        long seed;
        Image img;
        PerlinNoise mapa;
        int size;
        public int margem;
        public Spectrum spectrum;
        public Terreno () {
            size = 600;
            margem = 20;
            String input = JOptionPane.showInputDialog("seed:");
            input = input.replaceAll("\\s+","");
            if (input.length() > 8) {
                input = input.substring(0, 8);
            }
            
            Color[] pos = {new Color(239, 235, 201), new Color(115, 196, 78), new Color(237, 247, 255), Color.white};
            Color[] neg = {new Color(110, 173, 221), new Color(22, 58, 86)};
            spectrum = new Spectrum(neg, pos);

            AlteraCor alt = new AlteraCor(spectrum, this);
            CliqueTerreno list = new CliqueTerreno(alt);
            addMouseListener(list);

            
            seed = Long.parseLong(input, 36);
            mapa = new PerlinNoise(size, size, seed);
            createTerrain();
            
        }
        
	public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(img, margem, margem, this);
            //g.drawOval(50, 50, 500, 500);
	}

	public void createTerrain() {
		
		BufferedImage bufferedImage = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
		Graphics g = bufferedImage.getGraphics();
		Color c = Color.red;
		bufferedImage.setRGB(0, 0, c.getRGB());
		
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
                                float value = valorEmPonto(i, j);
				Color thisColor = spectrum.colorAtHeight(value).color;
				bufferedImage.setRGB(i, j, thisColor.getRGB());
			}
		}

		img =  bufferedImage;
	}
	
	private Color floatToColor (float f) {
		f = (f+1f)/2f;
		Color c = new Color(f, f, f);
		return c;
	}
	
	private float tratar(int i) {
		return i - 0.5f;
	}
        
        public float valorEmPonto (int i, int j) {
            float ii =  tratar(i);
            float jj = tratar(j);
            return mapa.fractalNoise(ii, jj);

        }

}