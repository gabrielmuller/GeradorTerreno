package main;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class TerrainFileChooser extends JFileChooser {

	public TerrainFileChooser(boolean open) {
		super();
		setFileFilter(new FileNameExtensionFilter("Terreno (.ter)", "ter"));
		
		String approvalText = open ? "Abrir" : "Salvar";
		setApproveButtonText(approvalText);


	}

}
