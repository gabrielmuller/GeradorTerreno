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
@SuppressWarnings("serial") class Interface extends JPanel {

	JCheckBox changeColorCheckbox, islandCheckbox;
	private JColorChooser colorChooser;
	private JSlider seaLevelSlider, zoomSlider, brushSizeSlider, brushWeightSlider;
	private JLabel sliderLabel;
	JButton updateButton, saveButton, openButton;
	private JButton generateSeedButton;
	private JTextField seedInput;
	private float minZoom = 0.3f;
	private float maxZoom = 3f;
	private String oldInput;
	private int sliderSize = 100;

	Interface() {
		super(new GridBagLayout());
		GridBagConstraints gridBag = new GridBagConstraints();

		colorChooser = new JColorChooser();
		//colorChooser.setPreviewPanel(new JPanel());
		
		changeColorCheckbox = new JCheckBox();
		islandCheckbox = new JCheckBox();

		seaLevelSlider = new JSlider(-sliderSize, sliderSize, 0);
		zoomSlider = new JSlider(-sliderSize, sliderSize, 0);
		brushSizeSlider = new JSlider(-sliderSize, sliderSize, 0);
		brushWeightSlider = new JSlider(-sliderSize, sliderSize, 0);
		
		saveButton = new JButton();
		openButton = new JButton();
		updateButton = new JButton();
		generateSeedButton = new JButton();

		seedInput = new JTextField();

		brushSizeSlider.setValue(-50);
		brushWeightSlider.setValue(-50);
		
		openButton.setText("Abrir");
		saveButton.setText("Salvar");
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

			void fix() {
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

		/* 
		 * LAYOUT CONFIG 
		 */
		
		/*		coluna 0		*/
		gridBag.fill = GridBagConstraints.HORIZONTAL;
		gridBag.gridx = 0; 
		gridBag.gridy = 0; //linha 0
		add(changeColorCheckbox, gridBag);

		gridBag.gridy = 1; //linha 1
		add(islandCheckbox, gridBag);
		
		gridBag.gridy = 2; 
		add(Box.createRigidArea(new Dimension(50, 20)), gridBag);
		
		gridBag.gridy = 3; 
		sliderLabel = new JLabel("Tamanho do pincel", JLabel.CENTER);
		add(sliderLabel, gridBag);
		
		gridBag.gridy = 4;
		add(brushSizeSlider, gridBag);
		
		add(Box.createRigidArea(new Dimension(50, 50)), gridBag);
		gridBag.gridy = 5;
		sliderLabel = new JLabel("Intensidade do pincel", JLabel.CENTER);
		add(sliderLabel, gridBag);
		
		gridBag.gridy = 6;
		add(brushWeightSlider, gridBag);
		
		gridBag.gridx = 1; 
		gridBag.gridy = 0; 
		add(Box.createRigidArea(new Dimension(50, 50)), gridBag);
		
		/*		coluna 2		*/
		gridBag.gridx = 1; //coluna 1
		gridBag.gridy = 0; //linha 0
		add(updateButton, gridBag);
		
		gridBag.gridy = 1; 
		add(generateSeedButton, gridBag);
		
		gridBag.gridy = 2; 
		add(Box.createRigidArea(new Dimension(50, 20)), gridBag);
		
		gridBag.gridy = 3;
		sliderLabel = new JLabel("Nível do mar", JLabel.CENTER);
		add(sliderLabel, gridBag);
		
		gridBag.gridy = 4;
		add(seaLevelSlider, gridBag);
		
		gridBag.gridy = 5;
		sliderLabel = new JLabel("Zoom", JLabel.CENTER);
		add(sliderLabel, gridBag);
		
		gridBag.gridy = 6;
		add(zoomSlider, gridBag);
		
		gridBag.gridwidth = 0;
		gridBag.gridx = 0;
		gridBag.gridy = 6;
		add(Box.createRigidArea(new Dimension(20, 50)), gridBag);
		gridBag.gridy = 7;
		add(seedInput, gridBag);
		add(Box.createRigidArea(new Dimension(200, 50)), gridBag);
		gridBag.gridy = 8;
		add(colorChooser, gridBag);
		
		gridBag.gridy = 9;
		add(Box.createRigidArea(new Dimension(200, 50)), gridBag);
		
		gridBag.gridwidth = 1;
		gridBag.gridx = 0;
		gridBag.gridy = 10;
		add(openButton, gridBag);
		gridBag.gridx = 1;
		add(saveButton, gridBag);

	}

	boolean isChangingColor() {
		return changeColorCheckbox.isSelected();
	}

	boolean isIsland() {
		return islandCheckbox.isSelected();
	}

	Color getSelectedColor() {
		return colorChooser.getColor();
	}

	float getSeaLevel() {
		return seaLevelSlider.getValue() / ((float) sliderSize);
	}

	float getBrushSize() {
		float temp = (brushSizeSlider.getValue() + sliderSize) / ((float) sliderSize * 2);
		temp *= 20;
		temp = temp * temp;
		return temp;

	}

	float getBrushWeight() {
		return ((brushWeightSlider.getValue() + sliderSize) / ((float) sliderSize));
	}

	long getSeed() {
		String input = seedInput.getText();
		return Long.parseLong(input, 36);
	}

	float getZoom() {
		float zeroToOne = (zoomSlider.getValue() / (float) (sliderSize * 2)) + 0.5f;
		zeroToOne = (float) Math.pow(zeroToOne, 3);
		float range = maxZoom - minZoom;
		zeroToOne *= range;
		zeroToOne += minZoom;
		return zeroToOne;
	}
}
