package main;

public class NormalizerProcessor implements MatrixProcessor{
	float smallest, largest;
	public NormalizerProcessor(float smallest, float largest) {
		this.largest = Math.abs(largest);
		this.smallest = Math.abs(smallest);
	}

	@Override
	public void process(Processing p) {
		ProcessingNormalizer n = (ProcessingNormalizer) p;
		float ratio = n.input > 0 ? largest : smallest;

		n.output = n.input / ratio;

	}

}
