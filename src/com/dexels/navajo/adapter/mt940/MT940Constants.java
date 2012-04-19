package com.dexels.navajo.adapter.mt940;

import java.io.Serializable;
import java.text.DateFormat;
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
}
