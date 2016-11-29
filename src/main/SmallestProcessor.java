package main;

public class SmallestProcessor implements MatrixProcessor {

	public SmallestProcessor() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void process(Processing p) {
		ProcessingSmallest s = (ProcessingSmallest) p;
		s.output = s.input;
		if (s.input < s.smallest) {
			s.smallest = s.input;
		}
		p = s;

	}

}
