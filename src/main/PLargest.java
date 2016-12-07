package main;

class PLargest extends MatrixProcessor {
	float largest;
	PLargest() {
		largest = -1;
	}

	@Override
	void process() {
		output = input;
		if (input > largest) {
			largest = input;
		}
	}

}
