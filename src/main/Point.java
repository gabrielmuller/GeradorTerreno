package main;

class Point {
	private float x, y;

	Point(float x, float y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	float distanceTo(Point p) {
		return (float) Math.sqrt((x-p.x)*(x-p.x) + (y-p.y)*(y-p.y));
	}

}
