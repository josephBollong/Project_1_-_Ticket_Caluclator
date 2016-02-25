import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import net.java.dev.designgridlayout.DesignGridLayout;
import utils.Seat;
import utils.SeatRow;

/**
 * GUI
 * 
 * @author Joe Bollong
 */
public class GUI extends JFrame {
	// properties of the GUI
	private JFrame frame;
	//private static final int FRAME_WIDTH = 450;
	//private static final int FRAME_HEIGHT = 500;
	private static final int FRAME_X = 150;
	private static final int FRAME_Y = 250;
	private static final int DEFAULT_ROWS = 3;

	private ArrayList<SeatRow> rows;

	private JScrollPane pneScroll;
	private JTextArea txtOutput;

	DesignGridLayout rowLayout;
	Engine engine = new Engine();

	private JButton btnCancel;
	private JButton btnOk;
	private JButton btnReset;
	private JButton btnChange;
	private JPanel pnlRows;

	private DesignGridLayout layout;

	public GUI() {
		// initializes the form
		frame = new JFrame("Concert Ticket Calculator");

		// set form objects and object properties

		//frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		frame.setLocation(FRAME_X, FRAME_Y);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		layout = new DesignGridLayout(frame);

		rows = new ArrayList<>();

		btnOk = new JButton("Calculate!");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				ArrayList<Seat> _seats = getSeats();
				engine.addSeats(_seats);
				txtOutput.setText("");
				setReportOutput(engine.getReport());
				engine.clearSeats();

			}});
		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				disposeFrame();

			}});
		btnReset = new JButton("Reset");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				resetFrame();	

			}});
		btnChange = new JButton("Change");
		btnChange.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				changeRows((String) JOptionPane.showInputDialog(null, "Enter number of rows." + "\n" + "Caution: Changing the number of rows will result in loss of data.", "Enter Number", JOptionPane.QUESTION_MESSAGE, null, null, Main.seatCount));

			}});

		JLabel lblCount = new JLabel(
				"                                    Seat Count");
		JLabel lblPrice = new JLabel("                          Price ($)");

		pnlRows = new JPanel();
		pnlRows.setBorder(BorderFactory.createTitledBorder("Enter data"));
		rowLayout = new DesignGridLayout(pnlRows);

		txtOutput = new JTextArea();
		txtOutput.setColumns(50);
		txtOutput.setRows(8);
		txtOutput.setBorder(BorderFactory.createTitledBorder("Report"));
		txtOutput.setFont(new Font("Courier", Font.PLAIN, 14));
		txtOutput.setLineWrap(true);
		txtOutput.setWrapStyleWord(true);
		txtOutput.setEditable(false);

		pneScroll = new JScrollPane(txtOutput);
		pneScroll.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		JPanel pneButton = new JPanel();
		pneButton.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		pneButton.add(Box.createHorizontalGlue());
		pneButton.add(btnReset);
		pneButton.add(Box.createRigidArea(new Dimension(10, 0)));
		pneButton.add(btnCancel);
		pneButton.add(Box.createRigidArea(new Dimension(10, 0)));
		pneButton.add(btnOk);

		layout.row().grid().add(lblCount).add(lblPrice);
		setRowCount(Main.seatCount);
		layout.row().grid().add(pnlRows);
		layout.row().grid().add(pneScroll);
		// layout.row().grid().add(pneButton);
		layout.row().right().add(btnChange).add(btnReset).add(btnCancel).add(btnOk);
	}

	// ------------------------------------------------------------------------------------

	public void initializeFrame() {
		initializeFrame(true);
	}

	public void initializeFrame(boolean _visible) {
		frame.pack();
		frame.setTitle("Concert Ticket Calculator");
		//frame.setSize(500, 600);
		frame.setResizable(false);
		frame.setVisible(_visible);

	}
	
	// create a dialog that allows the user to specify the number of rows that is to be calculated

	public void changeRows(String input) {
		

		if (input != null) {
			try {
				Main.seatCount = Integer.parseInt(input);
				disposeFrame();
				Main.main(null);
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "\"" + input + "\"" + " is not a valid number. Please enter a valid number.");
				changeRows((String) JOptionPane.showInputDialog(null, "Enter number of rows." + "\n" + "Caution: Changing the number of rows will result in loss of data.", "Enter Number", JOptionPane.QUESTION_MESSAGE, null, null, Main.seatCount));
			}

		} else {
			return;
		}
	}
	public void changeRows() {
		changeRows(String.valueOf(DEFAULT_ROWS));
	}

	// method for building the specified # of rows

	public void setRowCount(int _rows) {

		int x = 1;

		Main.seatCount = _rows;
		while (x <= _rows) {
			SeatRow _row = new SeatRow(getSeatName(x));
			rowLayout.row().grid(_row.getLabel()).add(_row.getCount())
			.add(_row.getPrice());
			rows.add(_row);
			x++;
		}
	}

	private String getSeatName(int _number) {
		return "Row " + _number + ":";
	}


	public void setReportOutput(String _output) {
		txtOutput.setText(_output);

	}


	// iterates through the seat rows to fetch the input and stores it in a
	// array list

	public ArrayList<Seat> getSeats() {
		ArrayList<Seat> _seats = new ArrayList<>();
		Iterator<SeatRow> itr = rows.iterator();

		while (itr.hasNext()) {
			SeatRow _row = itr.next();
			String _name = _row.getName();
			String _price = _row.getPrice().getText();
			String _count = _row.getCount().getText();

			Integer _countValue;
			// if count is a non-negative number otherwise return -1
			if (_count.matches("[0-9]+")) {
				_countValue = Integer.valueOf(_count);
			} else {
				_countValue = 0;

			}
			// if price is a any series of non-negative numbers followed with a decimal,
			// then, followed by any series of non-negative numbers
			Double _priceValue;
			if (_price.matches("[0-9]+(\\.[0-9]+)?")) {
				_priceValue = Double.valueOf(_price);
			} else {
				_priceValue = 0.0;
			}
			Seat _seat = new Seat(_name, _priceValue, _countValue);

			_seats.add(_seat);
		}

		return _seats;
	}

	public void disposeFrame() {
		frame.dispose();
	}


	public void resetFrame() {
		
		String[] options = new String[] {"Clear", "Reset", "Cancel"};
		int n = JOptionPane.showOptionDialog(this, "Would you like to clear input, or reset to defaults?", "Reset", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		
		if (n == JOptionPane.YES_OPTION) {
			Iterator<SeatRow> itr = rows.iterator();
			while (itr.hasNext()) {
				itr.next().reset();
			}
			engine.clearSeats();
			txtOutput.setText("");
		} else if (n == JOptionPane.NO_OPTION) {
			Iterator<SeatRow> itr = rows.iterator();
			while (itr.hasNext()) {
				itr.next().reset();
			}
			engine.clearSeats();
			txtOutput.setText("");
			
			changeRows();	
		} else if (n == JOptionPane.CANCEL_OPTION) {
			return;
		}
		
	}

}
