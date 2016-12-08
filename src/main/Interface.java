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
	private JSlider seaLevelSlider, brushSizeSlider, brushWeightSlider;
	private JLabel sliderLabel;
	JButton updateButton, openButton, saveButton, pngButton;
	private JButton generateSeedButton;
	private JTextField seedInput;
	//private float minZoom = 0.3f;
	//private float maxZoom = 3f;
	private String oldInput;
	private int sliderSize = 100;

	public Interface() {
		super(new GridBagLayout());
		GridBagConstraints grid = new GridBagConstraints();

		colorChooser = new JColorChooser();
		
		changeColorCheckbox = new JCheckBox();
		islandCheckbox = new JCheckBox();

		seaLevelSlider = new JSlider(-sliderSize, sliderSize, 0);
		brushSizeSlider = new JSlider(-sliderSize, sliderSize, 0);
		brushWeightSlider = new JSlider(-sliderSize, sliderSize, 0);
		
		saveButton = new JButton("Salvar");
		openButton = new JButton("Abrir");
		pngButton = new JButton("Exportar em PNG");
		updateButton = new JButton("Atualizar");
		generateSeedButton = new JButton("Gerar seed");

		seedInput = new JTextField();

		brushSizeSlider.setValue(-50);
		brushWeightSlider.setValue(-50);
		
		seedInput.setText("seed");
		oldInput = seedInput.getText();

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

		/* 
		 * LAYOUT CONFIG 
		 */
		
		/*		coluna 0		*/
		grid.fill = GridBagConstraints.HORIZONTAL;
		grid.gridx = 0; 
		grid.gridy = 0; //linha 0
		add(changeColorCheckbox, grid);

		grid.gridy = 1; //linha 1
		add(islandCheckbox, grid);
		
		grid.gridy = 2; 
		add(Box.createRigidArea(new Dimension(50, 20)), grid);
		
		grid.gridy = 3; 
		sliderLabel = new JLabel("Tamanho do pincel", JLabel.CENTER);
		add(sliderLabel, grid);
		
		grid.gridy = 4;
		add(brushSizeSlider, grid);
		
		add(Box.createRigidArea(new Dimension(50, 50)), grid);
		grid.gridy = 5;
		sliderLabel = new JLabel("Intensidade do pincel", JLabel.CENTER);
		add(sliderLabel, grid);
		
		grid.gridy = 6;
		add(brushWeightSlider, grid);
		
		grid.gridx = 1; 
		grid.gridy = 0; 
		add(Box.createRigidArea(new Dimension(50, 50)), grid);
		
		/*		coluna 2		*/
		grid.gridx = 2; //coluna 1
		grid.gridy = 0; //linha 0
		add(updateButton, grid);
		
		grid.gridy = 1; 
		add(generateSeedButton, grid);
		
		grid.gridy = 2; 
		add(Box.createRigidArea(new Dimension(50, 20)), grid);
		
		grid.gridy = 3;
		sliderLabel = new JLabel("Nivel do mar", JLabel.CENTER);
		add(sliderLabel, grid);
		
		grid.gridy = 4;
		add(seaLevelSlider, grid);
		
			
		grid.gridwidth = 0;
		grid.gridx = 0;
		grid.gridy = 6;
		add(Box.createRigidArea(new Dimension(20, 50)), grid);
		grid.gridy = 7;
		add(seedInput, grid);
		add(Box.createRigidArea(new Dimension(200, 50)), grid);
		grid.gridy = 8;
		add(colorChooser, grid);
		
		grid.gridy = 9;
		add(Box.createRigidArea(new Dimension(200, 50)), grid);
		
		grid.gridwidth = 1;
		grid.gridx = 0;
		grid.gridy = 10;
		add(openButton, grid);
		grid.gridx = 2;
		add(saveButton, grid);
		grid.gridy = 11;
		add(pngButton, grid);

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
}
