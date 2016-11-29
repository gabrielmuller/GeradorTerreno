package main;

public class Point {
	float x, y;

	public Point(float x, float y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	public float distanceTo(Point p) {
		return (float) Math.sqrt((x-p.x)*(x-p.x) + (y-p.y)*(y-p.y));
	}

}
