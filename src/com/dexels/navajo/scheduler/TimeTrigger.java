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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.StringTokenizer;
import com.dexels.navajo.server.GenericThread;

public class TimeTrigger extends Trigger implements ClockListener, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2959238255950798212L;
	/**
	 * Specification of a time trigger:
	 * 
	 * 1. Month of the year (1-12).
	 * 2. Day of the month (1-31).
	 * 3. Hour of the day (0-23).
	 * 4. Minute of the hour (0-59).
	 * 5. Day of the week. (SAT,SUN.MON,TUE,WED,THU,FRI). Comma separated list is possible, e.g. SAT,SUN
	 * 6. Year
	 * 
	 */
	private int year = -1;
	private int month = -1;
	private int monthday = -1;
	private int hour = -1;
	private int minute = -1;
	private int fromMinute = -1;
	private int toMinute = -1;
	private ArrayList minutes = null;
	private ArrayList day = null; /* SAT,SUN.MON,TUE,WED,FRI */
	private String description = null;
	private boolean singleEvent = false;
	private boolean runImmediate = false;
	private long lastRan = -1;
	
	private static final String NOW = "now";
	
	public TimeTrigger(int i, int field) {
		Calendar c = Calendar.getInstance();
		c.add(field, i);
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH) + 1;
		monthday = c.get(Calendar.DAY_OF_MONTH);
		hour = c.get(Calendar.HOUR_OF_DAY);
		minute = c.get(Calendar.MINUTE);
		description = month + "|" + monthday + "|" + hour + "|" + minute + "|*|" + year;
		singleEvent = true;
	}
	
	public TimeTrigger(String s) {
		
		description = s;
		
		if ( s.equals(NOW) ) {
			runImmediate = true;
			singleEvent = true;
			return;
		}
		
		runImmediate = false;
		singleEvent = false;
		
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
				if ( ms.indexOf(",") == -1 ) {
					day = new ArrayList();
					day.add(ms);
				} else {
					StringTokenizer days = new StringTokenizer(ms, ",");
					day = new ArrayList();
					while ( days.hasMoreTokens() ) {
						day.add(days.nextToken());
					}
				}
			} else {
				day = null;
			}
		}
		if (tokens.hasMoreTokens()) {
			String ms = tokens.nextToken();
			if (ms != null && !ms.equals("*")) {
				year = new Integer(ms).intValue();
			}
		}
		
		singleEvent = ( year != -1 && month != -1 && monthday != -1 && hour != -1 && minute != -1 );

	}
	
	public void setSingleEvent(boolean b) {
		singleEvent = b;
	}
	
	public boolean isSingleEvent() {
		return singleEvent;
	}
	
	public TimeTrigger(int year, int month, int monthday, int hour, int minute, ArrayList day) throws Exception {
		this.year = year;
		this.month = month - 1;
		this.monthday = monthday;
		this.hour = hour;
		this.minute = minute;
		this.day = day;
		
		if ( minute == -1) {
			throw new Exception("Minute is required field");
		}
	}
	
	public String getDescription() {
		return Trigger.TIME_TRIGGER + ":" + description;
	}
	
	public void removeTrigger() {
		Clock.getInstance().removeClockListener(this);
	}
	
	public static void main(String [] args) {
		java.util.Date d =  new java.util.Date();
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		System.err.println("y = " + c.get(Calendar.DAY_OF_MONTH));
	}

	private final boolean checkAlarm(Calendar c) {
		
		if ( runImmediate ) {
			return true;
		}
		
		if ( lastRan != -1 && ( c.getTimeInMillis() - lastRan ) < 60000 ) {
			return false;
		}
		
		int currentYear = c.get(Calendar.YEAR);
		int currentMonth = c.get(Calendar.MONTH);
		int currentMonthDay = c.get(Calendar.DAY_OF_MONTH);
		int currentHour = c.get(Calendar.HOUR_OF_DAY);
		int currentMinute = c.get(Calendar.MINUTE);
		int currentDay = c.get(Calendar.DAY_OF_WEEK);
		
		if (year != -1) {
			if ( currentYear != year ) {
				return false;
			}
		}
		
		if (month != -1) {
			if ( currentMonth != ( month - 1 ) ) {
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
		
		boolean isday = false;
		
		if (day != null) {
			if ( day.contains("SAT") && currentDay == 7 ) {
				isday = true;
			}
			if ( day.contains("SUN") && currentDay == 1 ) {
				isday = true;
			}
			if ( day.contains("MON") && currentDay == 2 ) {
				isday = true;
			}
			if ( day.contains("TUE") && currentDay == 3 ) {
				isday = true;
			}
			if ( day.contains("WED") && currentDay == 4 ) {
				isday = true;
			}
			if ( day.contains("THU") && currentDay == 5 ) {
				isday = true;
			}
			if ( day.contains("FRI") && currentDay == 6 ) {
				isday = true;
			}
		} else {
			isday = true;
		}
		
		if ( isday ) {
			return true;
		} else {
			return false;
		}
	}
	
	public void timetick(final Calendar c) {
		if ( checkAlarm(c) ) {
			// Spawn thread.
			GenericThread taskThread = new GenericThread("task:" + getTask().getId()) {
				
				public void run() {
					try {
						worker();
					} finally {
						finishThread();
					}
				}
				
				public final void worker() {
					lastRan = c.getTimeInMillis();
					getTask().run();
				}
			};
			taskThread.startThread(taskThread);
			
		} 
	}

	public void activateTrigger() {
		Clock.getInstance().addClockListener(this);
	}
}
