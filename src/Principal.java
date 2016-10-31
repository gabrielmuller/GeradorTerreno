import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JFrame;

public class Principal {

	public static void main(String[] args) {
              
	      JFrame frame = new JFrame();
              GridLayout layout = new GridLayout(1,2);
              
              frame.setLayout(layout);
	      frame.getContentPane().add(new Terreno());
	      frame.getContentPane().add(new EscolheCor());
	      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	      frame.setSize(1500, 800);
              //frame.pack();
	      frame.setVisible(true);

	}

}
