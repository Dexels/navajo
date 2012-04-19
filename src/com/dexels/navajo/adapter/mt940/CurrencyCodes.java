package com.dexels.navajo.adapter.mt940;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

public class CurrencyCodes implements Serializable {
	private static final long serialVersionUID = 307723564119037835L;
	private Map<String, String> currency = new TreeMap<String, String>();

	public CurrencyCodes() {
		this.fillCurrencyMap();
	}

	public static void main(String[] args) throws Exception {
		CurrencyCodes currency = new CurrencyCodes();
		System.out.println("Code (AAA): " + currency.getCurrencyCountry("AAA"));
		System.out.println("Code (ANG): " + currency.getCurrencyCountry("ANG"));
		System.out.println("Code (EUR): " + currency.getCurrencyCountry("EUR"));
		System.out.println("Code (FIM): " + currency.getCurrencyCountry("FIM"));
		System.out.println("Code (NLG): " + currency.getCurrencyCountry("NLG"));
	}

	/**
	 * Gets the description from the TreeMap
	 * Returns null if not in the list
	 * @param bookingKey
	 * @return String
	 */
	public String getCurrencyCountry(String currencyISO) {
		return this.getCurrency().get(currencyISO);
	}
	
	/**
	 * This list is far from complete, but should have the important ones
	 */
	private void fillCurrencyMap() {
		this.currency.put("ANG", "Antilles, Dutch");
		this.currency.put("BEF", "Belgium");
		this.currency.put("BLG", "Bulgaria");
		this.currency.put("CAD", "Canada");
		this.currency.put("CHF", "Switzerland");
		this.currency.put("CYP", "Cyprus");
		this.currency.put("CZK", "Czech Republik");
		this.currency.put("DKK", "Denmark");
		this.currency.put("ESP", "Spain");
		this.currency.put("EUR", "European Union Currency");
		this.currency.put("FIM", "Finland");
		this.currency.put("FRF", "France");
		this.currency.put("GRD", "Greece");
		this.currency.put("GBP", "United Kingdom");
		this.currency.put("HRK", "Croatia");
		this.currency.put("ITL", "Italy");
		this.currency.put("YUM", "Yugoslavia, Fed. Rep.");
		this.currency.put("LUF", "Luxembourg");
		this.currency.put("MAD", "Morocco");
		this.currency.put("MDL", "Moldavia");
		this.currency.put("MKD", "Macedonia");
		this.currency.put("NLG", "Netherlands");
		this.currency.put("NOK", "Norway");
		this.currency.put("PLN", "Poland");
		this.currency.put("PTE", "Portugal");
		this.currency.put("SEK", "Sweden");
		this.currency.put("SKK", "Slovakia");
		this.currency.put("TRL", "Turkey");
		this.currency.put("USD", "United States of America");
	}

	public Map<String, String> getCurrency() {
		return currency;
	}

	public void setCurrency(Map<String, String> currency) {
		this.currency = currency;
	}
}
