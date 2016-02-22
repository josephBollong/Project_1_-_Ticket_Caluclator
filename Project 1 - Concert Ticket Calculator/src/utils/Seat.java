package utils;


/**
 * Seat Object
 * 
 * @author Joe Bollong
 */
// this contains the seat object properties and is used for the actual calculations
public class Seat {
	private final String name;
	private final Double price;
	private final Integer count;

	public Seat(String name, Double price, Integer count) {
		this.name = name;
		this.price = price;
		this.count = count;
	}

	// methods for the seat object
	public String getName() {
		return name;
	}

	public Double get_price() {
		return price;
	}

	public Integer get_count() {
		return count;
	}

}
