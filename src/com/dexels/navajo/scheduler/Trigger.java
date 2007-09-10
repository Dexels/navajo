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

import com.dexels.navajo.server.Access;

public abstract class Trigger {

	/**
	 * The access object (if available) if a webservice call fired a trigger.
	 */
	protected Access myAccess;
	/**
	 * If swapInOut is set to true, the trigger webservice gets the response document,
	 * else the request document (see comments below on webservice trigger).
	 */
	private boolean swapInOut = false;
	
	/**
	 * Currently supported Trigger URLs:
	 * 
	 * 1. Trigger on time event:
	 * time:xyz, e.g. time:*|*|10|10|*
	 * crontab format is used
	 * 1. month of year (1-12) or *
	 * 2. day of month (1-31) or *
	 * 3. hour of day (0-23) or *
	 * 4. minute of hour (0-59)
	 * 5. day of week (SAT,SUN,MON,TUE,WED,THU,FRI) or *
	 * 6. year
	 * 
	 * 2. Trigger on navajo webservice event:
	 * navajo:xyz[?doc=out], e.g. navajo:relation/ProcessUpdatePerson?doc=out
	 * xyz can be regular expression.
	 * optional parameter doc, if doc=out, the response document of the webservice is passed
	 * as a request document to the trigger webservices. Default is the request document that
	 * is passed to the trigger webservice.
	 * 
	 * FUTURE TRIGGERS:
	 * 
	 * 3. Trigger on exception event:
	 * exception:[*|system|user|authorization]
	 * in case of specific type of exception or any (*) exception.
	 * 
	 * 4. Offset time trigger event:
	 * offsettime:x[d|h|m]
	 * specify a trigger that alarms after x (h)ours or (m)inutes or (d)ays.
	 * 
	 * 5. Trigger on after-trigger, before-trigger
	 * triggerafter:taskid
	 * 
	 */
	public final static String TIME_TRIGGER = "time";
	public final static String OFFSETTIME_TRIGGER = "offsettime";
	public final static String WS_TRIGGER = "navajo";
	public final static String WS_BEFORE_TRIGGER = "beforenavajo";
	public final static String AFTER_TASK_TRIGGER = "aftertask";
	
	private Task myTask = null;

	/**
	 * String representation of the trigger URL.
	 */
	public abstract String getDescription();
	
	public abstract void activateTrigger();
	
	/**
	 * Need to be called when trigger is removed, due to task removal, for cleanup purposes.
	 */
	public abstract void removeTrigger();
	
	public abstract boolean isSingleEvent();
	
	public abstract void setSingleEvent(boolean b);
	
	/**
	 * Trigger factory, creates proper trigger based upon URL definition.
	 * 
	 * @param s the trigger URL
	 * @return the proper Trigger object
	 */
	public final static Trigger parseTrigger(String s) throws IllegalTrigger {
		
		Trigger t = null;
		try {
			if (s.startsWith(TIME_TRIGGER)) {
				String v = s.substring(TIME_TRIGGER.length()+1);
				t = new TimeTrigger(v);
				return t;
			} else if (s.startsWith(OFFSETTIME_TRIGGER)) {
				String v = s.substring(OFFSETTIME_TRIGGER.length()+1);
				String field = v.substring(v.length() - 1);
				String offset = v.substring(0, v.length() - 1);
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
			else if (s.startsWith(WS_TRIGGER)) {
				String v = s.substring(WS_TRIGGER.length()+1);
				t = new WebserviceTrigger(v);
				return t;
			} else if (s.startsWith(AFTER_TASK_TRIGGER)) {
				String v = s.substring(AFTER_TASK_TRIGGER.length()+1);
				t = new AfterTaskTrigger(v);
				return t;
			} else if (s.startsWith(WS_BEFORE_TRIGGER)) {
				String v = s.substring(WS_BEFORE_TRIGGER.length()+1);
				t = new BeforeWebserviceTrigger(v);
				return t;
			} 
			else {
				throw new IllegalTrigger(s);
			}
		} catch (Exception e) {
			throw new IllegalTrigger(s);
		} 
	}
	
	/**
	 * Sets the swap response/request document flag. Used by task for determining
	 * how to call trigger webservice.
	 * @param b
	 */
	public void setSwapInOut(boolean b) {
		this.swapInOut = b;
	}
	
	/**
	 * @return true if swap response/request document flag is set.
	 */
	public boolean swapInOut() {
		return this.swapInOut;
	}
	
	/**
	 * @return the access object if a webservice caused the trigger.
	 */
	public Access getAccess() {
		return myAccess;
	}
	
	/**
	 * Set the acces object.
	 * 
	 * @param a the access object.
	 */
	public void setAccess(Access a) {
		myAccess = a;
	}
	
	public Task getTask() {
		return myTask;
	}
	
	public void setTask(Task t) {
		this.myTask = t;
	}
}
