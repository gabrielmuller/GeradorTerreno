
import java.awt.BorderLayout;
import javax.swing.JColorChooser;
import javax.swing.JPanel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author 10505053950
 */


public class EscolheCor extends JPanel {
    public EscolheCor() {
        super(new BorderLayout());
        JColorChooser c = new JColorChooser();
        
        //Terreno t = new Terreno();
        
        add(c, BorderLayout.PAGE_END);
        
        

    }
}
