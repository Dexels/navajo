/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.swingclient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class NavajoTypeParser {

	private static SimpleDateFormat displayDateFormat = new SimpleDateFormat(
			"dd-MMM-yyyy", Locale.getDefault());
	private static SimpleDateFormat inputFormat1 = new SimpleDateFormat(
			"dd-MM-yy");
	private static SimpleDateFormat inputFormat2 = new SimpleDateFormat(
			"dd/MM/yy");
	private static SimpleDateFormat inputFormat3 = new SimpleDateFormat(
			"ddMMyy");
	private static SimpleDateFormat inputFormat4 = new SimpleDateFormat("ddMM");
	private static SimpleDateFormat inputFormat5 = new SimpleDateFormat("dd");

	public final Date getDate(String text) throws ParseException {
		if (text == null || text.equals("")) {
			return null;
		}

		// see if there is only one separator provided; if so, add the current
		// year to string
		if (text.indexOf('-') >= 0
				&& text.indexOf('-') == text.lastIndexOf('-')) {
			text = text + "-" + Calendar.getInstance().get(Calendar.YEAR);
		}
		if (text.indexOf('/') >= 0
				&& text.indexOf('/') == text.lastIndexOf('/')) {
			text = text + "/" + Calendar.getInstance().get(Calendar.YEAR);

			// Try different parsers:
		}
		try {
			return inputFormat1.parse(text);
		} catch (ParseException pe1) {
			try {
				return inputFormat2.parse(text);
			} catch (ParseException pe2) {
				try {
					return inputFormat3.parse(text);
				} catch (ParseException pe6) {
					try {
						return displayDateFormat.parse(text);
					} catch (ParseException pe4) {
						try {
							// There is a bug in SimpleDateFormat causing the
							// date to reset to 01-01-1970
							// So we make sure the right month and year are set
							// again.
							Date wrong1 = inputFormat4.parse(text);
							Calendar c = Calendar.getInstance();
							c.setTime(wrong1);
							c.set(Calendar.YEAR,
									Calendar.getInstance().get(Calendar.YEAR));
							return c.getTime();
						} catch (ParseException pe5) {
							try {
								// There is a bug in SimpleDateFormat causing
								// the date to reset to 01-01-1970
								// So we make sure the right month and year are
								// set again.
								Date wrong2 = inputFormat5.parse(text);
								Calendar x = Calendar.getInstance();
								x.setTime(wrong2);
								x.set(Calendar.MONTH, Calendar.getInstance()
										.get(Calendar.MONTH));
								x.set(Calendar.YEAR, Calendar.getInstance()
										.get(Calendar.YEAR));
								return x.getTime();
							} catch (ParseException pe3) {
								return displayDateFormat.parse(text);
								// If this one fails, data entry person should
								// get an other job (person is too creative!);
							}
						}
					}
				}
			}
		}
	}
}
