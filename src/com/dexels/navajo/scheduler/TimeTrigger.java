/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Dexels BV</p>
 * @author 
 * @version $Id$.
 *
 * DISCLAIMER
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL DEXELS BV OR ITS CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
 * OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
 * OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
 * TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 */
package com.dexels.navajo.scheduler;

import java.util.Calendar;
import java.util.StringTokenizer;

public class TimeTrigger extends Trigger {

	/**
	 * Specification of a time trigger:
	 * 
	 * 1. Minute of the hour (0-59).
	 * 2. Hour of the day (0-23).
	 * 3. Day of the month (1-31).
	 * 4. Month of the year (1-12).
	 * 5. Day of the week. (SAT,SUN.MON,TUE,WED,FRI).
	 * 
	 */
	private int month = -1;
	private int monthday = -1;
	private int hour = -1;
	private int minute = -1;
	private String day = null; /* SAT,SUN.MON,TUE,WED,FRI */
	private String description = null;
	
	public TimeTrigger(String s) {
		description = s;
		StringTokenizer tokens = new StringTokenizer(s, "|");
		if (tokens.hasMoreTokens()) {
			String ms = tokens.nextToken();
			if (ms != null && !ms.equals("*")) {
				month = new Integer(ms).intValue();
			}
		}
		if (tokens.hasMoreTokens()) {
			String ms = tokens.nextToken();
			if (ms != null && !ms.equals("*")) {
				monthday = new Integer(ms).intValue();
			}
		}
		if (tokens.hasMoreTokens()) {
			String ms = tokens.nextToken();
			if (ms != null && !ms.equals("*")) {
				hour = new Integer(ms).intValue();
			}
		}
		if (tokens.hasMoreTokens()) {
			String ms = tokens.nextToken();
			if (ms != null && !ms.equals("*")) {
				minute = new Integer(ms).intValue();
			}
		}
		if (tokens.hasMoreTokens()) {
			String ms = tokens.nextToken();
			if (ms != null && !ms.equals("*")) {
				day = ms;
			}
		}
		System.err.println(month + "|" + monthday + "|" + hour + "|" + minute + "|" + day);
	}
	
	public TimeTrigger(int month, int monthday, int hour, int minute, String day) throws Exception {
		this.month = month - 1;
		this.monthday = monthday;
		this.hour = hour;
		this.minute = minute;
		this.day = day;
		
		if ( minute == -1) {
			throw new Exception("Minute is required field");
		}
	}
	
	public boolean alarm() {
		
		Calendar c = Calendar.getInstance();
		
		int currentMonth = c.get(Calendar.MONTH);
		int currentMonthDay = c.get(Calendar.DAY_OF_MONTH);
		int currentHour = c.get(Calendar.HOUR_OF_DAY);
		int currentMinute = c.get(Calendar.MINUTE);
		int currentDay = c.get(Calendar.DAY_OF_WEEK);
		
		if (month != -1) {
			if ( currentMonth != month ) {
				return false;
			}
		}
		
		if (monthday != -1) {
			if ( currentMonthDay != monthday ) {
				return false;
			}
		}
		
		if (hour != -1) {
			if ( currentHour != hour ) {
				return false;
			}
		}
		
		if (minute != -1) {
			if ( currentMinute != minute ) {
				return false;
			}
		}
		
		if (day != null) {
			if ( day.equals("SAT") && currentDay != 7 ) {
				return false;
			}
			if ( day.equals("SUN") && currentDay != 1 ) {
				return false;
			}
			if ( day.equals("MON") && currentDay != 2 ) {
				return false;
			}
			if ( day.equals("TUE") && currentDay != 3 ) {
				return false;
			}
			if ( day.equals("WED") && currentDay != 4 ) {
				return false;
			}
			if ( day.equals("THU") && currentDay != 5 ) {
				return false;
			}
			if ( day.equals("FRI") && currentDay != 6 ) {
				return false;
			}
		}
		
		return true;
		
	}
	
	public String getDescription() {
		return Trigger.TIME_TRIGGER + ":" + description;
	}
	
	public void resetAlarm()  {
		try {
			Thread.sleep(1000000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String [] args) throws Exception {
		
		TimeTrigger t = new TimeTrigger("*|*|10|10|*");
		
	}

	public void removeTrigger() {
		// TODO Auto-generated method stub	
	}
}
