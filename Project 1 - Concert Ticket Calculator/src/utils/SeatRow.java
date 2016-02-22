package utils;

import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * Seat Rows for building the GUI
 * 
 * @author Joe Bollong
 */

// contents of a seat row object
public class SeatRow {
	JLabel label;
	JTextField count;
	JTextField price;

	// initializing it
	public SeatRow(String _name) {
		label = new JLabel(_name);
		count = new JTextField();
		// count.setColumns(6);
		price = new JTextField();
		// price.setColumns(6);
	}

	public String getName() {
		return label.getText();
	}

	public JLabel getLabel() {
		return label;
	}

	public JTextField getCount() {
		return count;
	}

	public JTextField getPrice() {
		return price;
	}

	public void reset() {
		count.setText("");
		price.setText("");
	}
}
