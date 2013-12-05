package com.dexels.navajo.functions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;

public final class ParseDate extends FunctionInterface {

    @Override
	public String remarks() {
        return "Tries to transform the (string) object to a date by guessing its format or use the given format.";
    }

    @Override
	public String usage() {
        return "ParseDate(String s|Date d [, pattern])";
    }

    @Override
	public final Object evaluate() throws com.dexels.navajo.parser.TMLExpressionException {
        if (this.getOperand(0) == null) {
            throw new TMLExpressionException(this, "error: null value in first operand of ParseDate(String s|Date d [, pattern]).");
        }

        Object o = this.getOperand(0);
        if (o instanceof java.util.Date) {
            return o;
        } else if (o instanceof String) {
            // Check if a format is given else try to guess
            String dateFormat = null;
            if (this.getOperands().size() > 1 && this.getOperand(1) != null) {
                dateFormat = (String) this.getOperands().get(1);
            } else {
                // Going to guess some formats now
                dateFormat = determineDateFormat((String)o);
            }
            
            if (dateFormat == null) {
                return null;
            } else {
                if (getDate((String)o, new java.text.SimpleDateFormat(dateFormat)) != null) {
                    return getDate((String)o, new java.text.SimpleDateFormat(dateFormat));
                } else {
                    if (this.getOperands().size() > 1 && this.getOperand(1) != null) {
                        throw new TMLExpressionException(this, "Invalid date format: " + o + " for given date pattern: " + dateFormat);
                    } else {
                        return null;
                    }
                }
            }
        } else {
            // Feel free to implement here
            return null;
        }
    }

    @SuppressWarnings("serial")
    private static final Map<String, String> DATE_FORMAT_REGEXPS = new HashMap<String, String>() {{
        put("^\\d{8}$", "yyyyMMdd");
        put("^\\d{1,2}-\\d{1,2}-\\d{4}$", "dd-MM-yyyy");
        put("^\\d{1,2}-\\d{1,2}-\\d{2}$", "dd-MM-yy");
        put("^\\d{4}-\\d{1,2}-\\d{1,2}$", "yyyy-MM-dd");
        put("^\\d{2}-\\d{1,2}-\\d{1,2}$", "yy-MM-dd");
        put("^\\d{1,2}/\\d{1,2}/\\d{4}$", "MM/dd/yyyy");
        put("^[a-zA-Z]{3}/\\d{1,2}/\\d{4}$", "MMM/dd/yyyy");
        put("^\\d{1,2}/\\d{1,2}/\\d{2}$", "MM/dd/yy");
        put("^\\d{4}/\\d{1,2}/\\d{1,2}$", "yyyy/MM/dd");
        put("^\\d{2}/\\d{1,2}/\\d{1,2}$", "yy/MM/dd");
        put("^\\d{1}\\s[a-zA-Z]{3}\\s\\d{4}$", "d MMM yyyy");
        put("^\\d{1}-[a-zA-Z]{3}-\\d{4}$", "d-MMM-yyyy");
        put("^\\d{2}-[a-zA-Z]{3}-\\d{4}$", "dd-MMM-yyyy");
        put("^\\d{1,2}\\s[a-zA-Z]{3}\\s\\d{4}$", "dd MMM yyyy");
        put("^\\d{1}\\s[a-zA-Z]{4,}\\s\\d{4}$", "d MMMM yyyy");
        put("^\\d{1}-\\s[a-zA-Z]{4,}-\\s\\d{4}$", "d-MMMM-yyyy");
        put("^\\d{1,2}\\s[a-zA-Z]{4,}\\s\\d{4}$", "dd MMMM yyyy");
        put("^\\d{12}$", "yyyyMMddHHmm");
        put("^\\d{8}\\s\\d{4}$", "yyyyMMdd HHmm");
        put("^\\d{1,2}-\\d{1,2}-\\d{4}\\s\\d{1,2}:\\d{2}$", "dd-MM-yyyy HH:mm");
        put("^\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{2}$", "yyyy-MM-dd HH:mm");
        put("^\\d{1,2}/\\d{1,2}/\\d{4}\\s\\d{1,2}:\\d{2}$", "MM/dd/yyyy HH:mm");
        put("^\\d{4}/\\d{1,2}/\\d{1,2}\\s\\d{1,2}:\\d{2}$", "yyyy/MM/dd HH:mm");
        put("^\\d{1,2}\\s[a-zA-Z]{3}\\s\\d{4}\\s\\d{1,2}:\\d{2}$", "dd MMM yyyy HH:mm");
        put("^\\d{1,2}\\s[a-zA-Z]{4,}\\s\\d{4}\\s\\d{1,2}:\\d{2}$", "dd MMMM yyyy HH:mm");
        put("^\\d{14}$", "yyyyMMddHHmmss");
        put("^\\d{8}\\s\\d{6}$", "yyyyMMdd HHmmss");
        put("^\\d{1,2}-\\d{1,2}-\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "dd-MM-yyyy HH:mm:ss");
        put("^\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{2}:\\d{2}$", "yyyy-MM-dd HH:mm:ss");
        put("^\\d{1,2}/\\d{1,2}/\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "MM/dd/yyyy HH:mm:ss");
        put("^\\d{4}/\\d{1,2}/\\d{1,2}\\s\\d{1,2}:\\d{2}:\\d{2}$", "yyyy/MM/dd HH:mm:ss");
        put("^\\d{1}\\s[a-zA-Z]{3}\\s\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "d MMM yyyy HH:mm:ss");
        put("^\\d{1,2}\\s[a-zA-Z]{3}\\s\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "dd MMM yyyy HH:mm:ss");
        put("^\\d{1,2}\\s[a-zA-Z]{4,}\\s\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "dd MMMM yyyy HH:mm:ss");
        put("^\\d{1}\\s[a-zA-Z]{4,}\\s\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "d MMMM yyyy HH:mm:ss");
    }};

    /**
     * Determine SimpleDateFormat pattern matching with the given date string. Returns null if
     * format is unknown. You can simply extend DateUtil with more formats if needed.
     * @param dateString The date string to determine the SimpleDateFormat pattern for.
     * @return The matching SimpleDateFormat pattern, or null if format is unknown.
     * @see SimpleDateFormat
     */
    public static String determineDateFormat(String dateString) {
        for (String regexp : DATE_FORMAT_REGEXPS.keySet()) {
            if (dateString.toLowerCase().matches(regexp)) {
                return DATE_FORMAT_REGEXPS.get(regexp);
            }
        }
        return null; // Unknown format.
    }
    
    // Returns a valid date if able
    private java.util.Date getDate(String input, java.text.SimpleDateFormat format) {
        java.util.Date date = null;
        try {
            date = format.parse(input);
        } catch (Exception e) {
            return null;
        }
        return date;
    }

    public static void main(String[] args) throws Exception {
        ParseDate pd = new ParseDate();
        pd.reset();
        pd.insertOperand("01/01/1978");
//        pd.insertOperand("");
        pd.insertOperand("dd/MM/yy");
        java.util.Date d1 = (java.util.Date) pd.evaluate();
        System.err.println(d1);

        List<String> dates = new ArrayList<String>();
        dates.add("1997-02-05");
        dates.add("01-01-2001");
        dates.add("01-02-2007");
        dates.add("11/03/1985");
        dates.add("12/06/84");
        dates.add("06/14/1982");
        dates.add("06-05-87");
        dates.add("06-05-187");
        dates.add("2-may-2000");
        dates.add("2 mei 2000");
        dates.add("2-mei-2000");
        dates.add("31 jan 2000");
        dates.add("31-jan-2000");
        dates.add("30-jun-2000");
        dates.add("Jun/30/2000");
        ParseDate td = new ParseDate();
        for (String date : dates) {
            td.reset();
            td.insertOperand(date);
            java.util.Date d = (java.util.Date)td.evaluate();
            System.err.println("Date = " + d == null ? "null" : d);
        }
}
}