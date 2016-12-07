package main;

class PSeaLevel extends MatrixProcessor{
	private float seaLevel;
	PSeaLevel(float seaLevel) {
		this.seaLevel = seaLevel;
	}

	void process() {
		output = Utility.changeZero(input, seaLevel);

	}

}
