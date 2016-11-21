package main;

import java.awt.*;

import javax.swing.*;

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

    JCheckBox changeColorCheckbox, islandCheckbox;
    JColorChooser colorChooser;
    JSlider seaLevelSlider, zoomSlider;
    JButton updateButton;
    JTextField seedInput;
    float minZoom = 0.3f;
    float maxZoom = 3f;
    int sliderSize = 100;

    public Interface() {
        super(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        colorChooser = new JColorChooser();
        changeColorCheckbox = new JCheckBox();
        islandCheckbox = new JCheckBox();

        seaLevelSlider = new JSlider(-sliderSize, sliderSize, 0);
        zoomSlider = new JSlider(-sliderSize, sliderSize, 0);
        updateButton = new JButton();
        seedInput = new JTextField();

        updateButton.setText("Atualizar");
        seedInput.setText("seed");

        changeColorCheckbox.setText("Mudar cores");
        islandCheckbox.setText("Modo ilha");
        updateButton.setPreferredSize(new Dimension(50, 50));
        //add(colorChooser, BorderLayout.PAGE_END);
        //add(changeColorCheckbox, BorderLayout.PAGE_START);
        
        // layout config
        
        c.gridx = 0;
        c.gridy = 0;
        add(changeColorCheckbox, c);
           
        c.gridx = 0;
        c.gridy = 1;
        add(islandCheckbox, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        //c.gridx = 1;
        
        c.gridy = 2;
        //c.gridwidth = 2;
        //c.gridheight = 2;
        add(updateButton, c);

        c.gridx = 0;
        c.gridy = 4;
        add(seaLevelSlider, c);

        c.gridx = 0;
        c.gridy = 5;
        add(zoomSlider, c);

        c.gridx = 0;
        c.gridy = 6;
        add(seedInput, c);

        c.gridx = 0;
        c.gridy = 7;
        add(colorChooser, c);

    }

    public boolean isChangingColor() {
        return changeColorCheckbox.isSelected();
    }

    public boolean isIsland() {
        return islandCheckbox.isSelected();
    }

    public Color getSelectedColor() {
        return colorChooser.getColor();
    }

    public float getSeaLevel() {
        return seaLevelSlider.getValue() / (float) sliderSize;
    }

    public long getSeed() {
        String input = seedInput.getText();
        input = input.replaceAll("\\s+", "");
        if (input.length() > 8) {
            input = input.substring(0, 8);
        }
        return Long.parseLong(input, 36);
    }

    public float getZoom() {
        float zeroToOne = (zoomSlider.getValue() / (float) (sliderSize*2)) + 0.5f;
        zeroToOne = (float) Math.pow(zeroToOne, 3);
        float range = maxZoom - minZoom;
        zeroToOne *= range;
        zeroToOne += minZoom;
        return zeroToOne;
    }
}
