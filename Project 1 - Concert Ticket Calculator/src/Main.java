import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

/**
 * Seat Ticket Calculator
 * 
 * @author Joe Bollong
 */
public class Main {

	// Forces the complier to use the nimbus theme, and if it can't, then it forces it to quits
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(new NimbusLookAndFeel());
		} catch (UnsupportedLookAndFeelException ex) {
			Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
		}

		// Creates a new instance of the Adapter, GUI, and Engine
		@SuppressWarnings("unused")
		//Adapter _adapter = new Adapter(new GUI(), new Engine());
		GUI gui;
		gui = new GUI();
		gui.initializeFrame();
	}

}