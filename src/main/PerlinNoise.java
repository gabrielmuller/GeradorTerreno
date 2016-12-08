package main;

import java.util.Random;

class PerlinNoise {

	// Just a Random class object so I can fill my noise map with random
	// directions.
	private final Random random;

	// Width and Height of the map.
	private int width, height;

	// Random directions of length 1.
	private vec2[] values;

	// Contrast value.
	private float contrast = 1;

	private int canvasWidth = 0, canvasHeight = 0;
	
	private Point[] randomLocations;
	private int iters;
	/**
	 * Creates a noise map with specified dimensions.
	 * 
	 * @param width
	 *            of the noise map.
	 * @param height
	 *            of the noise map.
	 */
	PerlinNoise(int width, int height, long seed) {
		random = new Random(seed);
		this.width = width;
		this.height = height;

		values = new vec2[(width + 1) * (height + 1)]; // Create an array to
														// store random
														// directions.

		for (int y = 0; y < height + 1; y++) {
			for (int x = 0; x < width + 1; x++) {
				int rot = random.nextInt(359); // Random direction.

				// Store random direction of length 1 to our directions array.
				values[x + y * width] = Rotation.point(new vec2(0, 0), new vec2(0, -1), rot);
			}
		}

		// If you're wondering why "width + 1" "height + 1", it is because map
		// looks blurry
		// at right and bottom edges of the image without it. Try removing it,
		// you will see.
		
		//codigo adicionado
		iters = (int) Math.floor(Math.log(width) / Math.log(2))-2;
		randomLocations = new Point[iters];
		for (int i = 0; i < randomLocations.length; i++) {
			randomLocations[i] = new Point(random.nextInt(this.width), random.nextInt(this.width));
		}

	}

	//codigo adicionado
	float fractalNoise(float x, float y) {
		float output = 0;

		for (int i = 0; i < iters - 1; i++) {
			float tx = x + randomLocations[i].x;
			float ty = y + randomLocations[i].y;
			float scale = (float) Math.pow(2, iters - i);
			float ratio = (float) Math.pow(2, -i);
			output += noise(tx / scale, ty / scale) * ratio;
		}
		return output;
	}

	float noise(float x, float y) {

		if (canvasWidth != 0 && canvasHeight != 0) {
			x /= canvasWidth * width;
			y /= canvasHeight * height;
		}

		if (x >= this.width)
			x = this.width - x;
		if (y >= this.height)
			y = this.height - y;

		// Grid cell coordinates in integer values.
		int gx0 = (int) (Math.floor(x)); // Top-Left
		int gy0 = (int) (Math.floor(y)); // Top-Left
		int gx1 = gx0 + 1; // Down-Right
		int gy1 = gy0 + 1; // Down-Right

		// Random directions.
		vec2 g00 = g(gx0, gy0); // Top-Left
		vec2 g10 = g(gx1, gy0); // Top-Right
		vec2 g11 = g(gx1, gy1); // Down-Right
		vec2 g01 = g(gx0, gy1); // Down-Left

		// Subtract grid cells values from the point specified.
		vec2 delta00 = new vec2(x - gx0, y - gy0); // Top-Left
		vec2 delta10 = new vec2(x - gx1, y - gy0); // Top-Right
		vec2 delta11 = new vec2(x - gx1, y - gy1); // Down-Right
		vec2 delta01 = new vec2(x - gx0, y - gy1); // Down-Left

		// Compute a dot product between random directions and corresponding
		// delta values.
		float s = dot(g00, new vec2(delta00.x, delta00.y)); // Top-Left
		float t = dot(g10, new vec2(delta10.x, delta10.y)); // Top-Right
		float u = dot(g11, new vec2(delta11.x, delta11.y)); // Down-Right
		float v = dot(g01, new vec2(delta01.x, delta01.y)); // Down-Left

		// Compute the weights for x and y axis.
		float sx = weigh(delta00.x);
		float sy = weigh(delta00.y);

		// Interpolate between values.
		float a = lerp(sy, s, v); // Interpolate Top-Left(s) and Down-Left(v).
									// We can also call this LEFT
		float b = lerp(sy, t, u); // Interpolate Top-Right(t) and Down-Right(u)
									// We can also call this RIGHT
		float h = lerp(sx, a, b); // Interpolate LEFT(a) and RIGHT(b). We can
									// call this height(h)

		h *= contrast; // Multiply here so adjust contrast.

		// Make sure it is -1 to 1. If you don't change contrast, you don't have
		// to do this.
		if (h > 1)
			h = 1;
		if (h < -1)
			h = -1;

		return h;
	}

	/**
	 * Computes a weight using S-curve function f(x) = 3 * (x * x) - 2 * (x * x
	 * * x).
	 * 
	 * @param x
	 *            NOT as in axis, but as a variable.
	 */
	private float weigh(float x) {
		return 3 * (x * x) - 2 * (x * x * x);
	}

	/**
	 * Interpolate between 2 values, using weight.
	 */
	private float lerp(float weight, float a, float b) {
		float result = a + weight * (b - a);
		return result;
	}

	/**
	 * Compute a dot product. Example: dot product between (a, b) and (c, d) is:
	 * a * c + b * d
	 */
	private float dot(vec2 v0, vec2 v1) {
		return (v0.x * v1.x) + (v0.y * v1.y);
	}

	/**
	 * Get the random direction.
	 */
	private vec2 g(int x, int y) {
		if (x < 0)
			x = 0;
		if (y < 0)
			y = 0;
		if (x >= width)
			x = width;
		if (y >= height)
			y = height;
		return values[x + y * width];
	}

	private static class vec2 {

		float x, y;

		/**
		 * Just holds some float values.
		 * 
		 * @param x
		 * @param y
		 */
		private vec2(float x, float y) {
			this.x = x;
			this.y = y;
		}

	}

	private static class Rotation {

		/**
		 * Rotates specified point around pivot.
		 * 
		 * @param pivot
		 *            to rotate around.
		 * @param point
		 *            to rotate around pivot.
		 * @param rotation
		 *            - how many degrees to rotate.
		 * @return a new point, which was created by rotating given point around
		 *         pivot by some degrees.
		 */
		private static vec2 point(vec2 pivot, vec2 point, float rotation) {

			float rot = (float) (1f / 180 * rotation * Math.PI);

			float x = point.x - pivot.x;
			float y = point.y - pivot.y;

			float newx = (float) (x * Math.cos(rot) - y * Math.sin(rot));
			float newy = (float) (x * Math.sin(rot) + y * Math.cos(rot));

			newx += pivot.x;
			newy += pivot.y;

			return new vec2(newx, newy);
		}

	}

}