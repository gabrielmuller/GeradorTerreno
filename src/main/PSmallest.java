package main;

class PSmallest extends MatrixProcessor {
	float smallest;
	PSmallest() {
		smallest = 1;
	}

	@Override
	void process() {
		output = input;
		if (input < smallest) {
			smallest = input;
		}

	}

}
