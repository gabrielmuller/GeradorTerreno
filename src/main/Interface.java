package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
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


public class Interface extends JPanel {
	JCheckBox changeColorCheckbox;
	JColorChooser colorChooser;
	
    public Interface() {	
        super(new BorderLayout());

        colorChooser = new JColorChooser();
        changeColorCheckbox = new JCheckBox();
        changeColorCheckbox.setSelected(false);
        
        add(colorChooser, BorderLayout.PAGE_END);
        add(changeColorCheckbox, BorderLayout.PAGE_START);
    }
    
    public boolean isChangingColor() {
    	return changeColorCheckbox.isSelected();
    }
    
    public Color getSelectedColor() {
    	return colorChooser.getColor();
    }
}
