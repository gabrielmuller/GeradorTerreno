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
public class ColorChanger {
    private Interface interf;
    private Spectrum spectrum;
    private Terrain terrain;

    public ColorChanger(Interface interf, Spectrum spectrum, Terrain terreno) {
        //this.colorChooser = colorChooser;
        this.spectrum = spectrum;
        this.terrain = terreno;
        this.interf = interf;
    }
    
   
    public void changeColor(int i, int j) {
    	if (interf.isChangingColor()) {
	        i -= terrain.margin;
	        j -= terrain.margin;
	        float value = terrain.valueAtPoint(i, j);
	        int index = spectrum.colorAtHeight(value).index;
	        spectrum.changeColor(index, interf.getSelectedColor());
	        terrain.createTerrain();
    	}
    }
}
