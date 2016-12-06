package main;

public class Editor {
	private Interface interf;
	private Spectrum spectrum;
	private TerrainCreator tc;

	public Editor(Interface interf, Spectrum spectrum, TerrainCreator tc) {
		this.spectrum = spectrum;
		this.tc = tc;
		this.interf = interf;
	}

	public void edit(int i, int j, boolean negative) {
		i -= tc.margin;
		j -= tc.margin;
		i /= 2;
		j /= 2;
		if (i >= 0 && i < tc.size && j >= 0 && j < tc.size) {
			if (interf.isChangingColor()) {
				float value = tc.fixedTerrain.getHeightAt(i, j);
				int index = spectrum.colorAtHeight(value).index;
				System.out.println(index);
				spectrum.changeColor(index, interf.getSelectedColor());
			} else {
				Terrain t = tc.fixedTerrain;
				float size = interf.getBrushSize();
				float weight = interf.getBrushWeight();
				int multiplier = negative ? -1 : 1;
				t.edit(size, weight * multiplier, new Point(i, j));
				tc.update(t);
			}
		}
	}
}
