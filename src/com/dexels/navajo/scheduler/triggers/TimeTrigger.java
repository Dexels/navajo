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
package com.dexels.navajo.scheduler.triggers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.StringTokenizer;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.scheduler.Clock;
import com.dexels.navajo.scheduler.ClockListener;
import com.dexels.navajo.scheduler.Listener;
import com.dexels.navajo.scheduler.ListenerRunner;
import com.dexels.navajo.scheduler.Task;
import com.dexels.navajo.server.DispatcherFactory;
import com.dexels.navajo.server.GenericThread;
import com.dexels.navajo.server.test.TestDispatcher;
import com.dexels.navajo.server.test.TestNavajoConfig;
import com.sun.xml.internal.bind.v2.runtime.reflect.Lister;

public class TimeTrigger extends Trigger implements Serializable, ClockListener {

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
	private int offset = -1;
	private int offsetField = -1;
	private ArrayList<String> day = null; /* SAT,SUN.MON,TUE,WED,FRI */
	private String description = null;
	private boolean singleEvent = false;
	private boolean runImmediate = false;
	private boolean isOffsetTime = false;
	private long lastRan = -1;
	private Calendar nextOffsetTime;
	
	//private boolean fired = false;
	
	private static final String NOW = "now";
	
	public void setNextOffsetTime() {
		nextOffsetTime  = Calendar.getInstance();
		//nextOffsetTime.set(year, month-1, monthday, hour, minute);
		System.err.println("offsetField = " + offsetField + ", offset = " + offset);
		nextOffsetTime.add(offsetField, offset);
	}
	
	public static TimeTrigger createOffsetTimeTrigger(String description) {
		
		String v = ( description.indexOf(OFFSETTIME_TRIGGER) != -1 ? 
				                  description.substring(OFFSETTIME_TRIGGER.length() + 1) : description );
		
		String field = v.substring(v.length() - 1);
		String offset = v.substring(0, v.length() - 1);
		
		TimeTrigger t = null;
		
		if ( field.equals("s")) {
			t = new TimeTrigger(Integer.parseInt(offset), Calendar.SECOND);
		}
		if ( field.equals("m")) {
			t = new TimeTrigger(Integer.parseInt(offset), Calendar.MINUTE);
		}
		if ( field.equals("h")) {
			t = new TimeTrigger(Integer.parseInt(offset), Calendar.HOUR_OF_DAY);
		}
		if ( field.equals("d")) {
			t = new TimeTrigger(Integer.parseInt(offset), Calendar.DAY_OF_MONTH);
		}
		
		return t;
	}
	
	public TimeTrigger(int i, int field) {
		this.offset = i;
		this.offsetField = field;
		nextOffsetTime = Calendar.getInstance();
		nextOffsetTime.add(field, i);
		switch (field) {
		case Calendar.SECOND: description = i + "s";break;
		case Calendar.MINUTE: description = i + "m";break;
		case Calendar.HOUR_OF_DAY: description = i + "h";break;
		case Calendar.DAY_OF_MONTH: description = i + "d";break;
		}
		isOffsetTime = true;
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
					day = new ArrayList<String>();
					day.add(ms);
				} else {
					StringTokenizer days = new StringTokenizer(ms, ",");
					day = new ArrayList<String>();
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
	
	public TimeTrigger(int year, int month, int monthday, int hour, int minute, ArrayList<String> day) throws Exception {
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
		return ( isOffsetTime ? Trigger.OFFSETTIME_TRIGGER : Trigger.TIME_TRIGGER ) + ":" + description;
	}
	
	public void removeTrigger() {
		Clock.getInstance().removeClockListener(this);
	}
	
//	public static void main(String [] args) throws Exception {
//		DispatcherFactory df = new DispatcherFactory(new TestDispatcher(new TestNavajoConfig()));
//		Clock c = Clock.getInstance();
//		
//		
//		//c.startThread(c);
//		
//		//System.err.println("2. >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> LISTENERS: " + c.getListeners());
////		ListenerRunner runner = ListenerRunner.getInstance();
////		runner.startThread(runner);
//		
//		Trigger t = Trigger.parseTrigger("offsettime:5s");
//		c.addClockListener( (TimeTrigger) t);
//		
//		//System.err.println("3. >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> LISTENERS: " + c.getListeners());
//		while ( true ) {
//			Thread.sleep(1000);
//		}
//	}

	private final boolean checkAlarm(Calendar c) {
		
		if ( runImmediate ) {
			return true;
		}
		
		if ( isOffsetTime ) {
			//System.err.println("Is offsettime, c = " + c.getTime() + ", nextoffsettime = " + nextOffsetTime.getTime());
			if ( c.after(nextOffsetTime)) {
				return true;
			} else {
				return false;
			}
		}
		
		/**
		 * Resolution of time trigger is one minute. If trigger was active less then 60 seconds ago, do 
		 * not activate again.
		 * 
		 */
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
	
	public boolean timetick(final Calendar c) {
		if ( checkAlarm(c) ) {
			//fired = true;
			//System.err.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Alarm goes off!!" + c.getTime());
			lastRan = System.currentTimeMillis();
			// Set next offsettime if offsettime trigger.
			if ( this.isOffsetTime ) {
				setNextOffsetTime();
			}
			return true;
		}
		return false;
	}

	public void activateTrigger() {
		Clock.getInstance().addClockListener(this);
	}

//	public boolean isFired() {
//		return fired;
//	}

	public Navajo perform() {
		// Spawn thread.
		//if ( fired ) {
			//System.err.println("LOCK TIMETRIGGER, ABOUT TO PERFORM: " + getTask().getId());
			GenericThread taskThread = new GenericThread("task:" + getTask().getId()) {

				public void run() {
					try {
						worker();
					} catch (Throwable t) {
						t.printStackTrace(System.err);
						//System.err.println("REALLY COULD NOT PEFORM: " + getListenerId() );
					} finally {
						finishThread();
					}
				}

				public final void worker() {
					getTask().run();
				}

				@Override
				public void terminate() {
					// Nothing special.
				}
			};
			taskThread.startThread(taskThread);
	//		fired = false;
//		}
		return null;
	}

	public Calendar getNextOffsetTime() {
		return nextOffsetTime;
	}
	
}
