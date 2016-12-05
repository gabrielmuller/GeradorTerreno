package main;

public class PSmallest extends MatrixProcessor {
	float smallest;
	public PSmallest() {
		smallest = 1;
	}

	@Override
	public void process() {
		output = input;
		if (input < smallest) {
			smallest = input;
		}

	}

}
