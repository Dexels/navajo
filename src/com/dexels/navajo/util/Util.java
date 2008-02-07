/**
 * Title:        Navajo<p>
 * Description:  <p>
 * Copyright:    Copyright (c) Arjen Schoneveld<p>
 * Company:      Dexels<p>
 * @author Arjen Schoneveld
 * @version $Id$
 */
package com.dexels.navajo.util;

import com.dexels.navajo.document.*;
import com.dexels.navajo.server.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.regex.*;

import sun.reflect.ReflectionFactory.GetReflectionFactoryAction;

public final class Util {

    @SuppressWarnings("unused")
	private static ResourceBundle rb = null;

    private final static Random random;
    private static String id;
    
    static {
    	random = new Random(System.currentTimeMillis());
    	try {
			id = InetAddress.getLocalHost().toString();
		} catch (UnknownHostException e) {
			id = "Navajo";
		}
    }
    
    public final static Property getProperty(Message in, String name, boolean required)
            throws SystemException {

        Property prop = in.getProperty(name);

        if (required && (prop == null))
            throw new SystemException(SystemException.MISSING_PROPERTY, name, new Exception());

        return prop;
    }

    public final static boolean isRegularExpression(String s) {

        if (s.startsWith(Navajo.PARENT_MESSAGE+Navajo.MESSAGE_SEPARATOR))
          return isRegularExpression(s.substring((Navajo.PARENT_MESSAGE +Navajo.MESSAGE_SEPARATOR).length()));
        if ((s.indexOf("*") != -1) || (s.indexOf(".") != -1)
                || (s.indexOf("\\") != -1) || (s.indexOf("?") != -1)
                || (s.indexOf("[") != -1) || (s.indexOf("]") != -1)
                )
            return true;
        else
            return false;

    }

    public final static Message getMessage(Navajo in, String name, boolean required)
            throws SystemException {
        Message msg = in.getMessage(name);

        if (required && (msg == null))
            throw new SystemException(SystemException.MISSING_MESSAGE, name, new Exception());

        return msg;
    }

    public final static String getPropertyValue(Property prop, boolean valueRequired)
            throws SystemException {
        if (prop == null)
            throw new SystemException(SystemException.MISSING_PROPERTY, "", new Exception());

        String value = prop.getValue();

        if (valueRequired && value.equals(""))
            throw new SystemException(SystemException.MISSING_ATTRIBUTE_VALUE, prop.getName(), new Exception());

        return value;
    }

    @SuppressWarnings("unchecked")
	public final static Object getHashKey(Hashtable hash, Object findValue) {

        Object key;

        Enumeration keys = hash.keys();

        while (keys.hasMoreElements()) {
            key = keys.nextElement();
            if (hash.get(key).equals(findValue))
                return key;
        }
        return null;
    }


    public static String formatObject(Object o) {
      if (o instanceof Date)
        return formatDate((Date) o);
      else
        return o.toString();
    }

    public static String formatDate(Date datum) {

        java.text.SimpleDateFormat formatter =
                new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SS");

        return formatter.format(datum);
    }

    /**
     * This function creates a date using year, month and day values.
     */
    public static Date getDate(String year, String month, String day) {

        Calendar datum = Calendar.getInstance();

        if ((year != null) && (month != null) && (day != null))
            datum.set(Integer.parseInt(year), Integer.parseInt(month) - 1, Integer.parseInt(day));

        return datum.getTime();
    }

    /**
     * Convert a String to a long. Throw an exception if the String cannot be parsed.
     */
    public static long getLong(String value) throws UserException {

        long l;

        try {
            l = Long.parseLong(value);
        } catch (java.lang.NumberFormatException pe) {
            throw new UserException(UserException.NUMBER_FORMAT_ERROR, "Ongeldig getal: " + value);
        }

        return l;
    }

    /**
     * Convert a String to an integer. Throw an exception if the String cannot be parsed.
     */
    public static int getInt(String value) throws UserException {

        int l;

        try {
            l = Integer.parseInt(value);
        } catch (java.lang.NumberFormatException pe) {
            throw new UserException(UserException.NUMBER_FORMAT_ERROR, "Ongeldig getal: " + value);
        }

        return l;
    }

    /**
     * Convert a String to a double. Throw an exception if the String cannot be parsed.
     */
    public static double getDouble(String value) throws UserException {

        double l;

        try {
            l = Double.parseDouble(value);
        } catch (java.lang.NumberFormatException pe) {
            throw new UserException(UserException.NUMBER_FORMAT_ERROR, "Ongeldig getal: " + value);
        }

        return l;
    }

    /**
     * Convert a String to a Date. Throw an exception if the String cannot be parsed.
     */
    public static Date getDate(String datum) throws UserException {

        Date d = null;

        java.text.SimpleDateFormat parser = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SS");

        try {
            d = parser.parse(datum);
            return d;
        } catch (java.text.ParseException pe) {
            try {
              parser = new java.text.SimpleDateFormat("yyyy-MM-dd");
              d = parser.parse(datum);
              return d;
            } catch (Exception pe2) {
              throw new UserException(UserException.DATE_FORMAT_ERROR, "Ongeldige datum: " + datum);
            }
        }
    }

    /**
     * Log a message.
     */
    public static void logMessage(String message) {
        System.out.println(message);
    }

    public static String formatDouble(double d) {

        java.text.DecimalFormat decFive = new java.text.DecimalFormat("###.##");

        return decFive.format(d);

    }

    public static boolean regMatch(String regularExpression, String a) throws UserException {
        try {
            Pattern re = Pattern.compile(regularExpression);

            return re.matcher(a).matches();
        } catch (Exception re) {
            throw new UserException(-1, re.getMessage());
        }
    }

    /**
     * Method to convert object value to proper TML attribute value
     *
     * @param o
     * @param type
     * @return
     */
    public static String toString(Object o, String type) {
      if (type.equals(Property.DATE_PROPERTY)) {
        if ((o == null) || o.equals(""))
          return "";
        else {
          if (o instanceof Date)
            return formatDate((Date) o);
          else
            return o.toString();
        }
      }
      else {
        return o.toString();
      }
    }

    /**
     * Method to generate the random GUID.
     */
    public static String getRandomGuid() {
            MessageDigest md5 = null;
            StringBuffer sbValueBeforeMD5 = new StringBuffer();

            try {
                    md5 = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
                    throw new RuntimeException(e);
            }

            long time = System.currentTimeMillis();
            long rand = 0;

          
            rand = random.nextLong();
         

            // This StringBuffer can be a long as you need; the MD5
            // hash will always return 128 bits. You can change
            // the seed to include anything you want here.
            // You could even stream a file through the MD5 making
            // the odds of guessing it at least as great as that
            // of guessing the contents of the file!
            sbValueBeforeMD5.append(id);
            sbValueBeforeMD5.append(":");
            sbValueBeforeMD5.append(Long.toString(time));
            sbValueBeforeMD5.append(":");
            sbValueBeforeMD5.append(Long.toString(rand));

            String valueBeforeMD5 = sbValueBeforeMD5.toString();
            md5.update(valueBeforeMD5.getBytes());

            byte[] array = md5.digest();
            StringBuffer sb = new StringBuffer();
            for (int j = 0; j < array.length; ++j) {
                    int b = array[j] & 0xFF;
                    if (b < 0x10)
                            sb.append('0');
                    sb.append(Integer.toHexString(b));
            }
            
            return sb.toString();
    }

//    /**
//     * Convert to the standard format for GUID (Useful for SQL Server UniqueIdentifiers, etc). Example:
//     * "C2FEEEAC-CFCD-11D1-8B05-00600806D9B6".
//     */
//    public String toString() {
//            String raw = guid.toUpperCase();
//            StringBuffer sb = new StringBuffer();
//            sb.append(raw.substring(0, 8));
//            sb.append("-");
//            sb.append(raw.substring(8, 12));
//            sb.append("-");
//            sb.append(raw.substring(12, 16));
//            sb.append("-");
//            sb.append(raw.substring(16, 20));
//            sb.append("-");
//            sb.append(raw.substring(20));
//            return sb.toString();
//    }

    public static void main(String args[]) throws Exception {
        java.util.Date d = new java.util.Date();
        System.out.println(formatDate(d));
        for (int i = 0; i < 10; i++)
        System.err.println(getRandomGuid());
    }

}

