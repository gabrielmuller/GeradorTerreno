package main;

public class PEdit extends MatrixProcessor{
	float radius, maxChange;
	Point center;




	public PEdit(float radius, float maxChange, Point center) {
		this.radius = radius;
		this.maxChange = maxChange;
		this.center = center;
	}




	@Override
	public void process() {
		float distFromCenter = center.distanceTo(new Point(i, j));
		float change = maxChange * Utility.clamp((radius-distFromCenter)/radius, 0, 1);
		output = Utility.clamp(input + change, -1, 1);
	}

}
