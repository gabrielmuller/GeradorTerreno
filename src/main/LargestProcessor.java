package main;

public class LargestProcessor implements MatrixProcessor {

	public LargestProcessor() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void process(Processing p) {
		ProcessingLargest l = (ProcessingLargest) p;
		l.output = l.input;
		if (l.input > l.largest) {
			l.largest = l.input;
		}
		p = l;

	}

}
