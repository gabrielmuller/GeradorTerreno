package main;
import java.awt.Color;

public class Spectrum {
	private Color[] positiveColors;
	private Color[] negativeColors;
	public boolean interpolate;

	public Spectrum(Color[] n, Color[] p) {
		negativeColors = n;
		positiveColors = p;
	}

	public ColorInfo colorAtHeight(float height) {
		Color[] colors;
		if (height < 0) {
			height *= -1;
			colors = negativeColors;
		} else {
			colors = positiveColors;
		}
		int size = colors.length - 1;
		int index = (int) Math.floor(size * height);
		float fraction = size * height - index;
		Color resultado;
		
		if (interpolate) {
			resultado = Spectrum.colorInterpolate(colors[index], colors[index + 1], fraction);
		} else {
			if (fraction > 0.2) {
				index = index+1;
			}
			resultado = colors[index];
		}

		if (colors == negativeColors) {
			//transforma index positivo para negativo
			//para passar info que eh abaixo do mar
			index = Utility.twosComplement(index);
		}
		return new ColorInfo(resultado, index);
	}

	public void changeColor(int index, Color cor) {
		Color[] colors;
		if (index < 0) {
			index = Utility.twosComplement(index);
			colors = negativeColors;
		} else {
			colors = positiveColors;
		}

		colors[index] = cor;
	}

	public static Color colorInterpolate(Color a, Color b, float f) {
		float ar = a.getRed() / 255f;
		float ag = a.getGreen() / 255f;
		float ab = a.getBlue() / 255f;
		float br = b.getRed() / 255f;
		float bg = b.getGreen() / 255f;
		float bb = b.getBlue() / 255f;

		float red = ar * (1 - f) + br * f;
		float green = ag * (1 - f) + bg * f;
		float blue = ab * (1 - f) + bb * f;

		Color c = new Color(red, green, blue);
		return c;
	}
}
