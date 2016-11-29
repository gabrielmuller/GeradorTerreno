package main;

public class EditProcessor implements MatrixProcessor{
	float radius, maxChange;
	Point center;




	public EditProcessor(float radius, float maxChange, Point center) {
		this.radius = radius;
		this.maxChange = maxChange;
		this.center = center;
	}




	@Override
	public void process(Processing p) {
		ProcessingEdit e = (ProcessingEdit) p;
		float distFromCenter = center.distanceTo(new Point(p.i, p.j));
		float change = maxChange * Utility.clamp((radius-distFromCenter)/radius, 0, 1);
		e.output = Utility.clamp(e.input + change, -1, 1);
	}

}
