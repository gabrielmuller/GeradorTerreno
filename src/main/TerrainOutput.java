package main;

import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class TerrainOutput {
	FileOutputStream fos;
	DataOutputStream dos;
	ObjectOutputStream oos;
	
	public TerrainOutput(String filename) {
		try {
			fos = new FileOutputStream(filename);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		dos = new DataOutputStream(fos);
		try {
			oos = new ObjectOutputStream(fos);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void writeFile(float[][] elevation, Spectrum palette) {
		try {
			for (int i = 0; i < elevation.length; i++) {
				for (int j = 0; j < elevation[0].length; j++) {

					dos.writeFloat(elevation[i][j]);

				}
			}
			
			oos.writeObject(palette);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
