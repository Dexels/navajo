/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.document.types;

import java.util.Calendar;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Property;

/**
 * <p>
 * Title: ClockTime class
 * </p>
 * <p>
 * Description: Datatype class to represent 24 hour clock times (hours, minutes
 * and seconds)
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */

public final class StopwatchTime extends NavajoType implements Comparable<StopwatchTime> {

	private static final long serialVersionUID = -6257975668104174868L;

	private static final Logger logger = LoggerFactory.getLogger(StopwatchTime.class);
	long myMillis = 0;

	private static final long HOURS_MILLIS = 3600000;
	private static final long MINUTE_MILLIS = 60000;
	private static final long SECOND_MILLIS = 1000;
	private String myFormat = "HH:mm:SS:MMM";

	/**
	 * Constructor that construct StopwatchTime from millis.
	 *
	 * @param i
	 */
	public StopwatchTime(int i) {
		super(Property.STOPWATCHTIME_PROPERTY);
		myMillis = i;
	}

	public StopwatchTime(long i) {
		super(Property.STOPWATCHTIME_PROPERTY);
		myMillis = i;
	}

	/**
	 * Create a new ClockTime object from a given String
	 * 
	 * @param value String
	 */
	public StopwatchTime(String value) {
		this(value, null);
	}

	public Calendar calendarValue() {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(myMillis);
		return c;
	}

	/**
	 * Create a new ClockTime object from a given String and with a given subtype
	 * 
	 * @param s       String
	 * @param subtype String
	 */
	public StopwatchTime(String s, String subtype) {
		super(Property.STOPWATCHTIME_PROPERTY, subtype);
		constructCalValue(s);
		setupSubtypes();
	}

	private void setupSubtypes() {
		String format = getSubType("format");
		if (format != null) {
			myFormat = format;

		}
	}

	/**
	 * Format 1. HH:mm:SS[:MMM] 2. SS[:MMM] 3. mm:SS[:MMM]
	 *
	 * @param s
	 */
	private void constructCalValue(String s) {

		if (s == null) {
			return;
		}
		try {

			if (s.indexOf(',') > 0) {
				int pre = Integer.parseInt(s.substring(0, s.indexOf(',')));
				int post = Integer.parseInt(s.substring(s.indexOf(',') + 1));
				int mpost = 0;
				if (post < 10) {
					mpost = 100 * post;
				}
				if (post > 9 && post < 100) {
					mpost = 10 * post;
				}

				myMillis = (long)(1000 * pre + mpost);
				return;
			}

			StringTokenizer tokens = new StringTokenizer(s, ":");
			if (tokens.countTokens() == 1) { // Format 2a
				int h = Integer.parseInt(s);
				myMillis = SECOND_MILLIS * h;
			} else if (tokens.countTokens() == 2) { // Format 2b, 3a
				int t1 = Integer.parseInt(tokens.nextToken());
				String s2 = tokens.nextToken();
				int t2 = Integer.parseInt(s2);
				if (s2.length() == 3) { // Second and millis
					myMillis = t1 * SECOND_MILLIS + t2;
				} else { // Minute and seconds
					myMillis = t1 * MINUTE_MILLIS + t2 * SECOND_MILLIS;
				}
			} else if (tokens.countTokens() == 3) { // Format 3b, 1a
				int t1 = Integer.parseInt(tokens.nextToken());
				int t2 = Integer.parseInt(tokens.nextToken());
				String s3 = tokens.nextToken();
				int t3 = Integer.parseInt(s3);
				if (s3.length() == 3) {
					myMillis = t1 * MINUTE_MILLIS + t2 * SECOND_MILLIS + t3;
				} else {
					myMillis = t1 * HOURS_MILLIS + t2 * MINUTE_MILLIS + t3 * SECOND_MILLIS;
				}
			} else if (tokens.countTokens() == 4) { // Format 1b
				int t1 = Integer.parseInt(tokens.nextToken());
				int t2 = Integer.parseInt(tokens.nextToken());
				int t3 = Integer.parseInt(tokens.nextToken());
				int t4 = Integer.parseInt(tokens.nextToken());
				myMillis = t1 * HOURS_MILLIS + t2 * MINUTE_MILLIS + t3 * SECOND_MILLIS + t4;
			}

		} catch (Throwable e1) {
			logger.error("Error: ", e1);
		}
	}

	/**
	 * Get stopwatchtime in millis.
	 * 
	 * @return
	 */
	public long getMillis() {
		return myMillis;
	}

	/**
	 * Clone this Clocktime object
	 * 
	 * @return Object
	 */
	@Override
	public final Object clone() {
		return new StopwatchTime(myMillis);
	}

	/**
	 * Get the String representation of this StopwatchTime object
	 * 
	 * @return String
	 */
	@Override
	public final String toString() {
		long millis = myMillis;
		long hours = millis / HOURS_MILLIS;
		millis = millis % HOURS_MILLIS;
		long minutes = millis / MINUTE_MILLIS;
		millis = millis % MINUTE_MILLIS;
		long seconds = millis / SECOND_MILLIS;
		millis = millis % SECOND_MILLIS;

		String sep = "";
		if (myFormat.indexOf(':') > 0) {
			sep = ":";
		} else if (myFormat.indexOf('.') > 0) {
			sep = ",";
		}


		String delim = sep;
		if (",".equals(sep)) {
			delim = ".";
		}
		StringTokenizer tok = new StringTokenizer(myFormat, delim);

		if (",".equals(sep) && tok.countTokens() > 2) {
			logger.info("WARNING: Invalid format subtype!");
		}
		boolean firstToken = true;
		StringBuilder result = new StringBuilder();
		while (tok.hasMoreTokens()) {
			String token = tok.nextToken();

			// hours
			if ("HH".equals(token)) {
				if (hours == 0 && ":".equals(sep)) {
					result.append("00" + sep);
				} else if (hours < 10 && ":".equals(sep)) {
					result.append( "0" + hours + sep);
				} else if (hours < 10) {
					result.append( hours + sep);
				}

				else {
					result.append( hours + sep);
				}
			}

			// minutes
			if ("mm".equals(token)) {
				if (firstToken) {
					minutes = hours * 60 + minutes;
				}

				if (minutes == 0 && ":".equals(sep)) {
					result.append( "00" + sep);
				} else if (minutes < 10 && ":".equals(sep)) {
					result.append( "0" + minutes + sep);
				} else if (minutes < 10) {
					result.append( minutes + sep);
				}

				else {
					result.append( minutes + sep);
				}
			}

			// seconds
			if ("SS".equals(token)) {
				if (firstToken) {
					seconds = hours * 3600 + minutes * 60 + seconds;
				}
				if (seconds == 0 && ":".equals(sep)) {
					result.append( "00" + sep);
				} else if (seconds < 10 && ":".equals(sep)) {
					result.append( "0" + seconds + sep);
				} else if (seconds < 10) {
					result.append( seconds + sep);
				} else {
					result.append( seconds + sep);
				}
			}

			// millis 1
			if ("M".equals(token)) {
				if (millis == 0) {
					result.append( "0");
				} else if (millis < 10) {
					result.append( millis);
				} else if (millis < 100) {
					result.append( (int) millis / 10);
				} else {
					result.append( (int) millis / 100);
				}
			}

			// millis 2
			if ("MM".equals(token)) {
				if (millis == 0) {
					result.append( "00");
				} else if (millis < 10) {
					result.append( "0" + millis);
				} else if (millis < 100) {
					result.append( millis);
				} else {
					result.append( (int) millis / 10);
				}
			}

			// millis 3
			if ("MMM".equals(token)) {
				if (millis == 0) {
					result.append( "000");
				} else if (millis < 10) {
					result.append( "00" + millis);
				} else if (millis < 100) {
					result.append( "0" + millis);
				} else {
					result.append( millis);
				}
			}
			firstToken = false;
		}
		String res = result.toString();
		if (res.endsWith(",") || res.endsWith(":")) {
			res = res.substring(0, res.length() - 1);
		}
		return res;
	}

	public final StopwatchTime subtract(StopwatchTime other) {

		long myMillis = getMillis();
		long otherMillis = other.getMillis();

		return new StopwatchTime(Math.abs(myMillis - otherMillis));
	}

	@Override
	public final int compareTo(StopwatchTime o) {

		if (o == null) {
			return 0;
		}

		StopwatchTime other = o;

		return ((int) (this.getMillis() - other.getMillis()));
	}

	@Override
	public int hashCode() {
		return (this.getMillis() + "").hashCode();
	}

	@Override
	public boolean equals(Object obj) {

		if (obj instanceof StopwatchTime) {
			StopwatchTime other = (StopwatchTime) obj;
			return (other.getMillis() == this.getMillis());
		} else {
			return false;
		}
	}

	@Override
	public boolean isEmpty() {

		return myMillis != 0;
	}

}
