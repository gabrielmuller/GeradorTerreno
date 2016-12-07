package main;

class Terrain {
	float[][] elevation;
	
	Terrain(int size) {
		elevation = new float[size][size];
	}
	
	boolean setHeightAt(float height, int i, int j) {
		boolean output = false;
		if (height >= -1 && height <= 1) {
			elevation[i][j] = height;
			output = true;
		}
		return output;
	}
	
	float getHeightAt(int i, int j) {
		return elevation[i][j];
	}
	private void iterate(MatrixProcessor processor) {
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
	
	private float largestValue() {
		PLargest processor = new PLargest();
		iterate(processor);
		return processor.largest;
	}
	
	private float smallestValue() {
		PSmallest processor = new PSmallest();
		iterate(processor);
		return processor.smallest;
	}
	
	void normalize () {
		PNormalizer processor = new PNormalizer(smallestValue(), largestValue());
		iterate(processor);
	}
	
	void level(float level) {
		PSeaLevel processor = new PSeaLevel(level);
		iterate(processor);
	}
	
	void edit (float radius, float maxChange, Point center) {
		PEdit processor = new PEdit(radius, maxChange, center);
		iterate(processor);
	}
	int size () {
		return elevation.length;
	}
	/*float getHeightAt(int i, int j) {
		return elevation[i][j];
	}*/

}
