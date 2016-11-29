package main;

import java.awt.Color;
import javax.swing.JColorChooser;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author 10505053950
 */
public class Editor {
    private Interface interf;
    private Spectrum spectrum;
    private TerrainCreator tc;

    public Editor(Interface interf, Spectrum spectrum, TerrainCreator terreno) {
        //this.colorChooser = colorChooser;
        this.spectrum = spectrum;
        this.tc = terreno;
        this.interf = interf;
    }
    
   
    public void edit(int i, int j, boolean negative) {
        i -= tc.margin;
        j -= tc.margin;
        i/=2;
        j/=2;
    	if (interf.isChangingColor()) {
	        float value = tc.valueAtPoint(i, j);
	        int index = spectrum.colorAtHeight(value).index;
	        spectrum.changeColor(index, interf.getSelectedColor());
	        tc.createTerrainPreview();
    	} else {
    		Terrain t = tc.fixedTerrain;
    		float thickness = 20;
    		float intensity = 0.9f;
    		int multiplier = negative ? -1 : 1;
    		t.edit(thickness, intensity * multiplier, new Point (i, j));
    		tc.update(t);
    	}
    }
}
