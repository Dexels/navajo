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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;

import com.dexels.navajo.scheduler.Listener;
import com.dexels.navajo.scheduler.Task;
import com.dexels.navajo.server.Access;

public abstract class Trigger implements Listener, Serializable {

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
	public final static String TIME_TRIGGER         = "time";
	public final static String OFFSETTIME_TRIGGER   = "offsettime";
	public final static String WS_TRIGGER           = "navajo";  // or afternavajo.
	public final static String WS_BEFORE_TRIGGER    = "beforenavajo";
	public final static String AFTER_TASK_TRIGGER   = "aftertask";
	public final static String SERVER_EVENT_TRIGGER = "serverevent";
	public final static String IMMEDIATE_TRIGGER    = "immediate";
	public final static String JABBER_TRIGGER       = "jabber";
	
	private final static HashMap<String,Class<? extends Trigger>> userDefinedTriggers = new HashMap<String, Class<? extends Trigger>>();
	
	/**
	 * IMPORTANT NOTES:
	 * 
	 * The following triggers may occur simultaneously at different tribal members, but should actually be interpreted as
	 * a "single" trigger. For those trigger it is IMPORTANT that only the chief accepts them. Actually performing an
	 * associated Task with those kinds of trigger may be propagated to SOME tribal member:
	 * 
	 * TYPE I: TIME_TRIGGER, OFFSETTIME_TRIGGER, JABBER_TRIGGER
	 * 
	 * The following triggers have different meanings when they occur simultaneously at different tribal members. Hence,
	 * they SHOULD be interpreted as different triggers:
	 * 
	 * TYPE II: WS_TRIGGER, WS_BEFORE_TRIGGER, SERVER_EVENT_TRIGGER
	 * 
	 * The following triggers can ONLY occur at once at a single tribal member:
	 * 
	 * Type III: AFTER_TASK_TRIGGER, IMMEDIATE_TRIGGER
	 * 
	 * TODO: Create different Trigger Interfaces to denote this important difference. A single Trigger Registry should be
	 * enough instead of registries and other methods per trigger.
	 * 
	 * WORKFLOW ASPECTS:
	 * A trigger of Type II may have to be propagated to another tribal member if the handling member does not own an associated workflow.
	 * 
	 */
	private Task myTask = null;

	private String owner = null;
	
	private static Object semaphore = new Object();
	
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
			if (s.startsWith(TIME_TRIGGER + ":")) {
				String v = s.substring(TIME_TRIGGER.length()+1);
				t = new TimeTrigger(v);
				return t;
			} else if (s.startsWith(OFFSETTIME_TRIGGER + ":")) {
				return TimeTrigger.createOffsetTimeTrigger(s);
			}  
			else if (s.startsWith(WS_TRIGGER + ":")) {
				String v = s.substring(WS_TRIGGER.length()+1);
				t = new AfterWebserviceTrigger(v);
				return t;
			} else if (s.startsWith(AFTER_TASK_TRIGGER + ":")) {
				String v = s.substring(AFTER_TASK_TRIGGER.length()+1);
				t = new AfterTaskTrigger(v);
				return t;
			} else if (s.startsWith(WS_BEFORE_TRIGGER + ":")) {
				String v = s.substring(WS_BEFORE_TRIGGER.length()+1);
				t = new BeforeWebserviceTrigger(v);
				return t;
			}  else if (s.startsWith(SERVER_EVENT_TRIGGER + ":")) {
				String v = s.substring(SERVER_EVENT_TRIGGER.length()+1);
				t = new NavajoEventTrigger(v);
				return t;
			} else if ( s.startsWith(IMMEDIATE_TRIGGER) ) {
				t = new ImmediateTrigger();
				return t;
			} else { // Locate in user defined triggers.
			  	Iterator<String> urls = userDefinedTriggers.keySet().iterator();
			  	while ( urls.hasNext() ) {
			  		String urlPrefix = urls.next();
			  		if ( s.startsWith(urlPrefix + ":") ) {
			  			String v = s.substring(urlPrefix.length()+1);
			  			Class<? extends Trigger> tc = userDefinedTriggers.get(urlPrefix);
			  			Constructor<? extends Trigger> c = tc.getDeclaredConstructor(new Class[]{String.class});
			  			t = c.newInstance(v);
			  			return t;
			  		}
			  	}
			  	throw new IllegalTrigger(s);
			} 
		} catch (Exception e) {
			throw new IllegalTrigger(s);
		} 
	}
	
	/**
	 * Register a user defined trigger class and associate it with an urlPrefix.
	 * An user defined trigger class should ALWAYS have a constructor of the form
	 * public Trigger(String url).
	 * 
	 * @param urlPrefix
	 * @param className
	 */
	public static void registerTrigger(String urlPrefix, Class<? extends Trigger> className) {
		userDefinedTriggers.put(urlPrefix, className);
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
	
	/**
	 * Gets the task associated with this trigger.
	 * 
	 * @return
	 */
	public Task getTask() {
		return myTask;
	}
	
	/**
	 * Sets the task associated with this trigger.
	 * 
	 * @param t
	 */
	public void setTask(Task t) {
		this.myTask = t;
	}
	
	
	public String getOwnerHost() {
		synchronized (semaphore) {
			return owner;
		}
	}
		
	/**
	 * Listener id must be unique.
	 */
	public String getListenerId() {
		if ( this.myTask != null ) {
			return this.getClass().getName() + "-" + this.myTask.getId();
		} else {
			return this.getClass().getName() + "-null";
		}
	}
	
	public Trigger clone() {
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(this);
			oos.reset();
			oos.close();
			bos.close();
			ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));
			Trigger bwt = (Trigger) ois.readObject();
			return bwt;
		} catch (Exception e) {
			e.printStackTrace(System.err);
			return null;
		}
	}
	
}
