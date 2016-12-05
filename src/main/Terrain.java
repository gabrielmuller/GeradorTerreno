package main;

public class Terrain {
	float[][] elevation;
	
	public Terrain(int size) {
		elevation = new float[size][size];
	}
	
	public boolean setHeightAt(float height, int i, int j) {
		boolean output = false;
		if (height >= -1 && height <= 1) {
			elevation[i][j] = height;
			output = true;
		}
		return output;
	}
	
	public float getHeightAt(int i, int j) {
		return elevation[i][j];
	}
	public void iterate(MatrixProcessor processor) {
		for (int i = 0; i < elevation.length; i++) {
			for (int j = 0; j < elevation.length; j++) {
				processor.input = elevation[i][j];
				processor.i = i;
				processor.j = j;
				processor.process();
				elevation[i][j] = processor.output;
			}
		}
	}
	
	public float largestValue() {
		PLargest processor = new PLargest();
		iterate(processor);
		return processor.largest;
	}
	
	public float smallestValue() {
		PSmallest processor = new PSmallest();
		iterate(processor);
		return processor.smallest;
	}
	
	public void normalize () {
		PNormalizer processor = new PNormalizer(smallestValue(), largestValue());
		iterate(processor);
	}
	
	public void level(float level) {
		PSeaLevel processor = new PSeaLevel(level);
		iterate(processor);
	}
	
	public void edit (float radius, float maxChange, Point center) {
		PEdit processor = new PEdit(radius, maxChange, center);
		iterate(processor);
	}
	public int size () {
		return elevation.length;
	}
	/*public float getHeightAt(int i, int j) {
		return elevation[i][j];
	}*/

}
