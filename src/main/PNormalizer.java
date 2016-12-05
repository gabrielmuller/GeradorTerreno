package main;

public class PNormalizer extends MatrixProcessor{
	float smallest, largest;
	public PNormalizer(float smallest, float largest) {
		this.largest = Math.abs(largest);
		this.smallest = Math.abs(smallest);
	}

	@Override
	public void process() {
		float ratio = input > 0 ? largest : smallest;

		output = input / ratio;
		if (output > 1) {
			//System.out.println(input + " and " + ratio);
		}

	}

}
