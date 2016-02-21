import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import net.java.dev.designgridlayout.DesignGridLayout;

/**
 * GUI
 * 
 * @author Joe Bollong
 */
public class GUI extends JFrame {
	// properties of the GUI
	private final JFrame frame;
	//private static final int FRAME_WIDTH = 450;
	//private static final int FRAME_HEIGHT = 500;
	private static final int FRAME_X = 150;
	private static final int FRAME_Y = 250;

	//private final ArrayList<SeatRow> rows;

	private final JScrollPane pneScroll;
	private final JTextArea txtOutput;

	DesignGridLayout rowLayout;

	private final JButton btnCancel;
	private final JButton btnOk;
	private final JButton btnReset;

	private final DesignGridLayout layout;

	public GUI() {
		// initializes the form
		frame = new JFrame("Concert Ticket Calculator");

		// set form objects and object properties

		//frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		frame.setLocation(FRAME_X, FRAME_Y);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		//rows = new ArrayList<>();

		btnOk = new JButton("Calculate!");
		btnCancel = new JButton("Cancel");
		btnReset = new JButton("Reset");

		layout = new DesignGridLayout(frame);

		JLabel lblCount = new JLabel(
				"                                    Seat Count");
		JLabel lblPrice = new JLabel("                          Price ($)");

		JPanel pnlRows = new JPanel();
		pnlRows.setBorder(BorderFactory.createTitledBorder("Enter data"));
		rowLayout = new DesignGridLayout(pnlRows);

		txtOutput = new JTextArea();
		txtOutput.setColumns(20);
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
		layout.row().grid().add(pnlRows);
		layout.row().grid().add(pneScroll);
		// layout.row().grid().add(pneButton);
		layout.row().right().add(btnReset).add(btnCancel).add(btnOk);
	}
	
	public void initializeFrame() {
		initializeFrame(true);
	}

	public void initializeFrame(boolean _visible) {
		frame.pack();
		frame.setTitle("Concert Ticket Calculator");
		frame.setResizable(false);
		frame.setVisible(_visible);
	}
	
	

	// method for building the specified # of rows
	@Override
	public void setRowCount(int _rows) {

		int x = 1;

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

	@Override
	public void setReportOutput(String _output) {
		txtOutput.setText(_output);

	}
	/*

	// iterates through the seat rows to fetch the input and stores it in a
	// array list
	@Override
	public ArrayList<Seat> getSeats() {
		ArrayList<Seat> _seats = new ArrayList<>();
		Iterator<SeatRow> itr = rows.iterator();

		while (itr.hasNext()) {
			SeatRow _row = itr.next();
			String _name = _row.getName();
			String _price = _row.getPrice().getText();
			String _count = _row.getCount().getText();
			boolean _false = false;

			Integer _countValue;
			// if count is a non-negative number otherwise return -1
			if (_count.matches("[0-9]+")) {
				_countValue = Integer.valueOf(_count);
			} else {
				_countValue = -1;
			}
			// if price is a any series of non-negative numbers followed with a decimal,
			// then, followed by any series of non-negative numbers
			Double _priceValue;
			if (_price.matches("[0-9]+(\\.[0-9]+)?")) {
				_priceValue = Double.valueOf(_price);
			} else {
				_priceValue = Double.NaN;
			}
			Seat _seat = new Seat(_name, _priceValue, _countValue);

			_seats.add(_seat);
		}

		return _seats;
	}

	*/
	@Override
	public void onCalculate(IEvent _event) {
		btnOk.addActionListener(new ButtonActionListener(_event));

	}

	@Override
	public void displayFrame() {
		frame.pack();
		// frame.setResizable(false);
		frame.setVisible(true);

	}

	@Override
	public void onCancel(IEvent _event) {
		btnCancel.addActionListener(new ButtonActionListener(_event));

	}

	@Override
	public void onReset(IEvent _event) {
		btnReset.addActionListener(new ButtonActionListener(_event));

	}

	@Override
	public void disposeFrame() {
		frame.dispose();
	}

	@Override
	public void resetFrame() {
		Iterator<SeatRow> itr = rows.iterator();
		while (itr.hasNext()) {
			itr.next().reset();
		}
		txtOutput.setText("");
	}
	
}
