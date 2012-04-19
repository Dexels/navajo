package com.dexels.navajo.adapter.mt940;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormatSymbols;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MT940Utils implements Serializable {
	private static final long serialVersionUID = 1797998199210134841L;

    
    /**
     * used to get the right bigdecimal
     * @param formattedString
     * @param locale
     * @return BigDecimal
     */
    public static BigDecimal stringToBigDecimal(final String formattedString, final Locale locale) {
		final DecimalFormatSymbols symbols;
		final char groupSeparatorChar;
		final String groupSeparator;
		final char decimalSeparatorChar;
		final String decimalSeparator;
		String fixedString;
		final BigDecimal number;

		symbols = new DecimalFormatSymbols(locale);
		groupSeparatorChar = symbols.getGroupingSeparator();
		decimalSeparatorChar = symbols.getDecimalSeparator();

		if (groupSeparatorChar == '.') {
			groupSeparator = "\\" + groupSeparatorChar;
		} else {
			groupSeparator = Character.toString(groupSeparatorChar);
		}

		if (decimalSeparatorChar == '.') {
			decimalSeparator = "\\" + decimalSeparatorChar;
		} else {
			decimalSeparator = Character.toString(decimalSeparatorChar);
		}

		fixedString = formattedString.replaceAll(groupSeparator, "");
		fixedString = fixedString.replaceAll(decimalSeparator, ".");
		number = new BigDecimal(fixedString);

		return (number);
	}
	
	/**
	 * Method to parse the swift string to a java.util.Date
	 * @param value
	 * @return Date
	 * @throws Exception 
	 */
	public static Date getDateFromTagString(Calendar cal, String value) throws Exception {
    	try {
    		if (cal == null) {
    			cal = Calendar.getInstance(Locale.getDefault());
    		}
    		cal.set(Integer.parseInt(MT940Utils.getYear(value.substring(0, 2))), Integer.parseInt(value.substring(2, 4)), Integer.parseInt(value.substring(4, 6)));
    		return cal.getTime();
    	} catch (Exception e) {
			String message = "Error when converting date from value: " + value + " -> " + "Errormessage: " + e.getMessage();
			throw new Exception(message);
    	}
	}
    
    /**
     * Adds the century to the swift year
     * @param year
     * @return String
     */
    public static String getYear(String year) {
    	if (year.length() < 4) {
    		return "20" + year;
    	}
    	return year;
    }

	/**
	 * Read the headerinfo to see if we can get the bankname from it.
	 * Bankname is used for specific MT940 layout settings.
	 * @return String
	 * @throws Exception 
	 */
	public static String getBankNameFromFile(BufferedReader reader) throws Exception {
		String bankName = MT940Constants.UNDEFINEDBANK;
		if (reader != null) {
			String input;
			try {
				input = reader.readLine();
				if (input.contains("ABNANL")) {
					bankName = MT940Constants.ABNAMRO;
				} else if (input.contains("INGBNL")) {
					bankName = MT940Constants.ING;
				} else if (input.contains("PSTBNL")) {
					bankName = MT940Constants.POSTBANK;
				} else if (input.contains("SNSBNL")) {
					bankName = MT940Constants.SNS;
				} else if (input.contains("RABONL")) {
					bankName = MT940Constants.RABOBANK;
				} else {
					bankName += " -> HEADER = (" + input + ")";
				}
			} catch (IOException e) {
				throw new Exception("Error getting the bankname from the filecontent: " + e.getMessage());
			}
		}
		return bankName;
	}
	
	/**
	 * Gets the stringvalue from the param string using the tag array position
	 * @param tagPositions
	 * @param value
	 * @return String
	 * @throws Exception 
	 */
	public static String getTagValue(int[] tagPositions, String value) throws Exception {
		String result = "";
		try {
			int start = tagPositions[0];
			int end = tagPositions[1];
			// length can not be bigger then the actual length of the string
			end = (end > value.length()) ? value.length() : end;
			result = value.substring((start - 1), end);
		} catch (Exception e) {
			throw new Exception("Error when processing value: " + value + " -> " + "Errormessage: " + e.getMessage());
		}
		return result;
	}
	
	/**
	 * Methode to filter a subfield from a string
	 * @param input
	 * @param subfield
	 * @return String
	 */
	public static String getTransactionSubfield(String input, String subfield) {
		String result = "";
		
		// assuming that the part ends at the next < entry or just read the rest of the line
		if (input != null && subfield != null) {
    		int i = input.indexOf(subfield);
    		int j = input.indexOf("<", (i + 1));
    		if (j == -1) { j = input.length(); }
    		if (i != -1) {
    			result = input.substring((i + subfield.length()), j);
    		}
		}
		return result;
	}
}
