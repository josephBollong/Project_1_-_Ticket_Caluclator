package utils;

public enum Padding {

	// used to add padding to the text when generating reports
	NAME(10), COUNT(15), PRICE(10), TOTAL(10), FINAL_TOTAL_LABEL(10), FINAL_TOTAL_VALUE(10);
	private int spaces;

	private Padding(int _spaces) {
		spaces = _spaces;
	}

	public int getSpaces() {
		return spaces;
	}
}
