package main;

import java.awt.Color;

public class Spectrum {

    private final Color[] positiveColors;
    private final Color[] negativeColors;
    public float seaLevel;

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
        if (index+1 >= colors.length) {
        	index = colors.length - 2;
        }
        if (index+1 == colors.length) {
        	System.out.println("size: " + size + " height: " + height + " index: " + index);
        }
        float fraction = size * height - index;
        Color resultado;

        if (interpolate) {
            resultado = Spectrum.colorInterpolate(colors[index], colors[index + 1], fraction);
        } else {
            if (fraction > 0.5) {
                index = index + 1;
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
        float[] aInFloats = Spectrum.colorToArray(a);
        float[] bInFloats = Spectrum.colorToArray(b);
        float[] resultInFloats = new float[3];
        for (int i = 0; i < resultInFloats.length; i++) {
            resultInFloats[i] = (float) Math.sqrt(Math.pow(aInFloats[i], 2) * (1-f) + Math.pow(bInFloats[i], 2) * f);
        }

        return Spectrum.arrayToColor(resultInFloats);
    }

    private static float[] colorToArray(Color a) {
        float[] result = new float[3];
        result[0] = a.getRed() / 255f;
        result[1] = a.getGreen() / 255f;
        result[2] = a.getBlue() / 255f;
        return result;
    }
    
    private static Color arrayToColor (float[] a) {
        return new Color (a[0], a[1], a[2]);
    }
}
