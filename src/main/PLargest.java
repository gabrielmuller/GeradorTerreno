package main;

public class PLargest extends MatrixProcessor {
	float largest;
	public PLargest() {
		largest = -1;
	}

	@Override
	public void process() {
		output = input;
		if (input > largest) {
			largest = input;
		}
	}

}
