
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
public class AlteraCor {
   // private JColorChooser colorChooser;
    private Spectrum spectrum;
    private Terreno terreno;

    public AlteraCor(/*JColorChooser colorChooser,*/ Spectrum spectrum, Terreno terreno) {
        //this.colorChooser = colorChooser;
        this.spectrum = spectrum;
        this.terreno = terreno;
    }
    
   
    public void alterarCor(int i, int j) {
        i -= terreno.margem;
        j -= terreno.margem;
        float value = terreno.valorEmPonto(i, j);
        int index = spectrum.colorAtHeight(value).index;
        spectrum.alterarCor(index, Color.red);
        terreno.createTerrain();
        System.out.println(value);
    }
}
