package main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

class PNGSaver {
	private String filename;

	PNGSaver(String filename) {
		this.filename = filename;
	}
	
	void save(BufferedImage b) {
		try {
			File outputfile = new File(filename);
			ImageIO.write(b, "png", outputfile);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}


}
