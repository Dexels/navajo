package com.dexels.navajo.adapter.mt940;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

public class BookingKeys implements Serializable {
	private static final long serialVersionUID = -8229935373897006729L;
	private Map<String, String> bookingKeys = new TreeMap<String, String>();
	
	public BookingKeys() {
		this.fillBookingKeysMap();
	}

	public static void main(String[] args) throws Exception {
		BookingKeys bookingKeys = new BookingKeys();
		System.out.println("Code (AAA): " + bookingKeys.getBookingKeyDescription("AAA"));
		System.out.println("Code (BOE): " + bookingKeys.getBookingKeyDescription("BOE"));
		System.out.println("Code (TCK): " + bookingKeys.getBookingKeyDescription("TCK"));
	}

	/**
	 * Gets the description from the TreeMap
	 * Returns null if not in the list
	 * @param bookingKey
	 * @return String
	 */
	public String getBookingKeyDescription(String bookingKey) {
		return this.getBookingKeys().get(bookingKey);
	}
	
	private void fillBookingKeysMap() {
		this.bookingKeys.put("BOE", "Bill of exchange");
		this.bookingKeys.put("BRF", "Brokerage fee");
		this.bookingKeys.put("CHG", "Charges and other expenses");
		this.bookingKeys.put("CHK", "Cheques");
		this.bookingKeys.put("CLR", "Cash letter/presentation of cheque");
		this.bookingKeys.put("COL", "Collections (in the case of specification of a main amount)");
		this.bookingKeys.put("COM", "Commission");
		this.bookingKeys.put("DCR", "Documentary credit (in the case of specification of a main amount)");
		this.bookingKeys.put("DIV", "Stock rights dividends");
		this.bookingKeys.put("EQA", "Equivalent amount");
		this.bookingKeys.put("ECK", "Eurocheques");
		this.bookingKeys.put("FEX", "Foreign exchange");
		this.bookingKeys.put("INT", "Interest");
		this.bookingKeys.put("LBX", "Lock Box");
		this.bookingKeys.put("LDP", "Loan deposit");
		this.bookingKeys.put("MSC", "Miscellaneous");
		this.bookingKeys.put("RTI", "Returned item");
		this.bookingKeys.put("SEC", "Security (when specifying a main amount)");
		this.bookingKeys.put("STO", "Standing order");
		this.bookingKeys.put("TCK", "Travellers' cheques");
		this.bookingKeys.put("TRF", "Transfer");
		this.bookingKeys.put("VDA", "Value date amendment");
	}

	public Map<String, String> getBookingKeys() {
		return bookingKeys;
	}

	public void setBookingKeys(Map<String, String> bookingKeys) {
		this.bookingKeys = bookingKeys;
	}
}
