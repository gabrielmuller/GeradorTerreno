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
	public void iterate(MatrixProcessor processor, Processing processing) {
		for (int i = 0; i < elevation.length; i++) {
			for (int j = 0; j < elevation.length; j++) {
				processing.input = elevation[i][j];
				processing.i = i;
				processing.j = j;
				processor.process(processing);
				elevation[i][j] = processing.output;
			}
		}
	}
	
	public float largestValue() {
		float output = 0;
		ProcessingLargest processing = new ProcessingLargest();
		LargestProcessor processor = new LargestProcessor();
		iterate(processor, processing);
		output = processing.largest;
		return output;
	}
	
	public float smallestValue() {
		float output = 0;
		ProcessingSmallest processing = new ProcessingSmallest();
		SmallestProcessor processor = new SmallestProcessor();
		iterate(processor, processing);
		output = processing.smallest;
		return output;
	}
	
	public void normalize () {
		ProcessingNormalizer processing = new ProcessingNormalizer();
		NormalizerProcessor processor = new NormalizerProcessor(smallestValue(), largestValue());
		iterate(processor, processing);
	}
	
	public void level(float level) {
		ProcessingSeaLevel processing = new ProcessingSeaLevel();
		SeaLevelProcessor processor = new SeaLevelProcessor(level);
		iterate(processor, processing);
	}
	
	public void edit (float radius, float maxChange, Point center) {
		ProcessingEdit processing = new ProcessingEdit();
		EditProcessor processor = new EditProcessor(radius, maxChange, center);
		iterate(processor, processing);
	}
	public int size () {
		return elevation.length;
	}
	/*public float getHeightAt(int i, int j) {
		return elevation[i][j];
	}*/

}
