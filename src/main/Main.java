package main;

import java.awt.GridLayout;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {

		JFrame frame = new JFrame();
		GridLayout layout = new GridLayout(1, 2);

		frame.setLayout(layout);
		Interface i = new Interface();
		frame.getContentPane().add(i);
		frame.getContentPane().add(new Terrain(i));

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1500, 800);
		// frame.pack();
		frame.setVisible(true);

	}

}