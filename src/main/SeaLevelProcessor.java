package main;

public class SeaLevelProcessor implements MatrixProcessor{
	float seaLevel;
	public SeaLevelProcessor(float seaLevel) {
		this.seaLevel = seaLevel;
	}

	@Override
	public void process(Processing p) {
		ProcessingSeaLevel s = (ProcessingSeaLevel) p;
		s.output = Utility.changeZero(s.input, seaLevel);

	}

}
