package main;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

@SuppressWarnings("serial") 
class TerrainFileChooser extends JFileChooser {

	TerrainFileChooser(boolean open) {
		super();
		setFileFilter(new FileNameExtensionFilter("Terreno (.ter)", "ter"));
		
		String approvalText = open ? "Abrir" : "Salvar";
		setApproveButtonText(approvalText);


	}

}
