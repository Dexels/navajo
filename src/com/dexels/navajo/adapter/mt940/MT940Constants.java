package com.dexels.navajo.adapter.mt940;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public abstract class MT940Constants implements Serializable {
	private static final long serialVersionUID = -4450510279507967504L;
    // Define the supported banks
	public static final String UNDEFINEDBANK = "UNDEFINED";
	public static final String ABNAMRO = "ABNAMRO";
	public static final String RABOBANK = "RABOBANK";
	public static final String ING = "ING";
	public static final String POSTBANK = "POSTBANK";
	public static final String SNS = "SNS";
    public static final NumberFormat SWIFTNUMBERFORMAT = NumberFormat.getInstance(Locale.getDefault());
    public static final DateFormat SWIFTDATEFORMAT = new SimpleDateFormat( "yyMMdd" );

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
    
    public static String getYear(String year) {
    	if (year.length() < 4) {
    		return "20" + year;
    	}
    	return year;
    }
}
