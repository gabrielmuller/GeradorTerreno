package main;

public class PSeaLevel extends MatrixProcessor{
	float seaLevel;
	public PSeaLevel(float seaLevel) {
		this.seaLevel = seaLevel;
	}

	public void process() {
		output = Utility.changeZero(input, seaLevel);

	}

}
