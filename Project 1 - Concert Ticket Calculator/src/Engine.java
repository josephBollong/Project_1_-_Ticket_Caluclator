import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;

import utils.Padding;
import utils.Seat;

/**
 * Engine
 * 
 * @author Joe Bollong
 */
public class Engine {
	private static final String CRLF = System.getProperty("line.seperator",
			"\n\r");

	private final ArrayList<Seat> arySeat;

	public Engine() {
		arySeat = new ArrayList<>();
	}

	// Returns an ArrayList of Strings, with each entry containing a the name,
	// sales, price, and final total of the row
	private ArrayList<String> getRowTotals() {
		// throw new UnsupportedOperationException();
		Locale locale = new Locale("en", "US");
		NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
		NumberFormat cnt = NumberFormat.getIntegerInstance(locale);
		Iterator<Seat> itr = arySeat.iterator();
		ArrayList<String> _outputs = new ArrayList<>();

		// iterating through the array
		while (itr.hasNext()) {
			Seat _seat = itr.next();
			String _name = _seat.getName();
			Double _price = _seat.get_price();
			Double _count = _seat.get_count().doubleValue();
			Double _sales = _price * _count;

			// building a string with the contents of the the returned values
			// from the array
			StringBuilder _output = new StringBuilder();
			_output.append(padString(_name, Padding.NAME));
			_output.append(padString((cnt.format(_count)).toString(), Padding.COUNT));
			_output.append(padString((fmt.format(_price)).toString(), Padding.PRICE));
			_output.append(padString((fmt.format(_sales)).toString(), Padding.TOTAL));

			_outputs.add(_output.toString());

		}
		return _outputs;
	}

	private String padString(String _string, Padding _padding) {
		return StringUtils.rightPad(_string, _padding.getSpaces());
	}

	// Returns a string containing the final total of all seat rows.
	private String getFinalTotal() {
		// throw new UnsupportedOperationException();
		Locale locale = new Locale("en", "US");
		NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
		Iterator<Seat> itr = arySeat.iterator();

		Double _finalTotal = 0.0;

		while (itr.hasNext()) {
			Seat _seat = itr.next();
			Double _price = _seat.get_price();
			Double _count = _seat.get_count().doubleValue();
			Double _sales = _price * _count;

			_finalTotal += _sales;

		}

		StringBuilder _output = new StringBuilder();
		_output.append(padString("Total Sales: ", Padding.FINAL_TOTAL_LABEL));
		_output.append(padString((fmt.format(_finalTotal)).toString(),
				Padding.FINAL_TOTAL_VALUE));

		return _output.toString();

	}

	// Clears the Seat Array
	public void clearSeats() {
		arySeat.clear();

	}

	// Adds a seat to the array
	public void addSeat(Seat _seat) {
		arySeat.add(_seat);
		//onSeatAdded.event();
	}

	// Used for adding multiple seats at once
	public void addSeats(ArrayList<Seat> _seats) {
		arySeat.addAll(_seats);
		//onSeatAdded.event();

	}

	// Returns the final report. Should generate the report using the results of
	// getRowTotals() and getFinalTotal()
	public String getReport() {
		ArrayList<String> _report = new ArrayList<>();
		_report.add(getHeader());
		_report.add(getDivider());
		_report.addAll(getRowTotals());
		_report.add("");
		_report.add(getFinalTotal());

		String _output = generateReport(_report);
		return _output;
	}

	// Generates the Report String to be sent to the GUI
	private String generateReport(ArrayList<String> _rows) {
		StringBuilder _output = new StringBuilder();
		Iterator<String> _itr = _rows.iterator();
		while (_itr.hasNext()) {
			_output.append(_itr.next());
			_output.append(CRLF);

		}
		return _output.toString();
	}

	private String getHeader() {

		// builds the report header
		StringBuilder _header = new StringBuilder();
		_header.append(padString("", Padding.NAME));
		_header.append(padString("Tickets sold", Padding.COUNT));
		_header.append(padString("Price", Padding.PRICE));
		_header.append(padString("Total", Padding.TOTAL));
		return _header.toString();
	}

	// builds the divider to separate the header from the report contents
	private String getDivider() {
		StringBuilder _divider = new StringBuilder();
		_divider.append(addDivider(' ', Padding.NAME));
		_divider.append(addDivider('-', Padding.COUNT));
		_divider.append(addDivider('-', Padding.PRICE));
		_divider.append(addDivider('-', Padding.TOTAL));
		return _divider.toString();
	}

	// add the divider the the report string
	private String addDivider(char _char, Padding _count) {
		return StringUtils.repeat(_char, _count.getSpaces());
	}

	// Sets the event to run whenever a seat is added. The Adapter should use
	// this to get the report, then update it in the view
	/*
	public void onSeatAdded(IEvent _event) {
		onSeatAdded = _event;

	}
	*/

}