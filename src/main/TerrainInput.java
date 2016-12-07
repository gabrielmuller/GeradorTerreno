package main;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

class TerrainInput {
	private InputStream is;
	private DataInputStream dis;
	private ObjectInputStream ois;

	TerrainInput(String filename) {
		try {
			is = new FileInputStream(filename);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		dis = new DataInputStream(is);
		try {
			ois = new ObjectInputStream(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	TerrainInfo fileToFloatMatrix(int size) {
		float[][] elevation = new float[size][size];
		Spectrum spectrum = null;
		try {
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					elevation[i][j] = dis.readFloat();
				}
			}
			spectrum = (Spectrum) ois.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return new TerrainInfo(elevation, spectrum);
	}
}
