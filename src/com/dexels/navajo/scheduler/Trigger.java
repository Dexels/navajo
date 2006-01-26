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
	 * Supported Trigger URLs:
	 * 
	 * Trigger on time event:
	 * time:xyz, e.g. time:*|*|10|10|*
	 * crontab format is used
	 * 1. month of year (1-12) or *
	 * 2. day of month (1-31) or *
	 * 3. hour of day (0-23) or *
	 * 4. minute of hour (0-59)
	 * 5. day of week (SAT,SUN,MON,TUE,WED,THU,FRI) or *
	 * 
	 * Trigger on webservice event:
	 * webservice:xyz[?doc=out], e.g. webservice:relation/ProcessUpdatePerson?doc=out
	 * xyz can be regular expression.
	 * optional parameter doc, if doc=out, the response document of the webservice is passed
	 * as a request document to the trigger webservices. Default is the request document that
	 * is passed to the trigger webservice.
	 * 
	 */
	public final static String TIME_TRIGGER = "time";
	public final static String WS_TRIGGER = "webservice";
	
	/**
	 * @return true if alarm is set.
	 */
	public abstract boolean alarm();
	/**
	 * Resets the alarm, cleanup or other stuff can take place.
	 */
	public abstract void resetAlarm();
	/**
	 * String representation of the trigger URL.
	 */
	public abstract String getDescription();
	/**
	 * Need to be called when trigger is removed, due to task removal, for cleanup purposes.
	 */
	public abstract void removeTrigger();
	
	/**
	 * Trigger factory, creates proper trigger based upon URL definition.
	 * 
	 * @param s the trigger URL
	 * @return the proper Trigger object
	 */
	public final static Trigger parseTrigger(String s) throws IllegalTrigger {
		try {
			if (s.startsWith(TIME_TRIGGER)) {
				String v = s.substring(5);
				TimeTrigger t = new TimeTrigger(v);
				return t;
			} else if (s.startsWith(WS_TRIGGER)) {
				String v = s.substring(11);
				WebserviceTrigger t = new WebserviceTrigger(v);
				return t;
			} else {
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
}
