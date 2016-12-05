package main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author 10505053950
 */
@SuppressWarnings("serial")
public class Interface extends JPanel {

	JCheckBox changeColorCheckbox, islandCheckbox;
	JColorChooser colorChooser;
	JSlider seaLevelSlider, zoomSlider, brushSizeSlider, brushWeightSlider;
	JButton updateButton, generateSeedButton;
	JButton temp;
	JTextField seedInput;
	float minZoom = 0.3f;
	float maxZoom = 3f;
	String oldInput;
	int sliderSize = 100;

	public Interface() {
		super(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		colorChooser = new JColorChooser();

		changeColorCheckbox = new JCheckBox();
		islandCheckbox = new JCheckBox();

		seaLevelSlider = new JSlider(-sliderSize, sliderSize, 0);
		zoomSlider = new JSlider(-sliderSize, sliderSize, 0);
		brushSizeSlider = new JSlider(-sliderSize, sliderSize, 0);
		brushWeightSlider = new JSlider(-sliderSize, sliderSize, 0);

		updateButton = new JButton();
		generateSeedButton = new JButton();

		seedInput = new JTextField();

		brushSizeSlider.setValue(-50);
		brushWeightSlider.setValue(-50);

		updateButton.setText("Atualizar");
		seedInput.setText("seed");
		oldInput = seedInput.getText();
		generateSeedButton.setText("Gerar seed");

		changeColorCheckbox.setText("Mudar cores");
		islandCheckbox.setText("Modo ilha");

		updateButton.setPreferredSize(new Dimension(50, 50));
		generateSeedButton.setPreferredSize(new Dimension(50, 50));

		generateSeedButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Random r = new Random();
				seedInput.setText(r.nextInt(100000000) + "");
			}

		});

		seedInput.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				fix();
			}

			public void removeUpdate(DocumentEvent e) {
				fix();
			}

			public void insertUpdate(DocumentEvent e) {
				fix();
			}

			public void fix() {
				Runnable fixer = new Runnable() {
					@Override
					public void run() {
						String input = seedInput.getText();
						if (input.length() > 8 || !input.matches("^[a-zA-Z0-9]*$")) {
							seedInput.setText(oldInput);
						} else {
							oldInput = input;
						}
					}
				};
				SwingUtilities.invokeLater(fixer);

			}
		});
		temp = new JButton();
		
		c.gridx = 0;
		c.gridy = 0;
		add(changeColorCheckbox, c);

		c.gridy = 1;
		add(islandCheckbox, c);

		c.fill = GridBagConstraints.HORIZONTAL;

		c.gridy = 2;
		add(updateButton, c);

		c.gridy = 3;
		add(generateSeedButton, c);

		c.gridy = 4;
		add(seaLevelSlider, c);

		c.gridy = 5;
		add(zoomSlider, c);

		c.gridy = 6;
		add(seedInput, c);

		c.gridy = 7;
		add(colorChooser, c);

		c.gridy = 8;
		add(brushSizeSlider, c);

		c.gridy = 9;
		add(brushWeightSlider, c);
		
		c.gridy = 10;
		add(temp, c);

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
		return seaLevelSlider.getValue() / ((float) sliderSize);
	}

	public float getBrushSize() {
		float temp = (brushSizeSlider.getValue() + sliderSize) / ((float) sliderSize * 2);
		temp *= 20;
		temp = temp * temp;
		return temp;

	}

	public float getBrushWeight() {
		return ((brushWeightSlider.getValue() + sliderSize) / ((float) sliderSize));
	}

	public long getSeed() {
		String input = seedInput.getText();
		return Long.parseLong(input, 36);
	}

	public float getZoom() {
		float zeroToOne = (zoomSlider.getValue() / (float) (sliderSize * 2)) + 0.5f;
		zeroToOne = (float) Math.pow(zeroToOne, 3);
		float range = maxZoom - minZoom;
		zeroToOne *= range;
		zeroToOne += minZoom;
		return zeroToOne;
	}
}
