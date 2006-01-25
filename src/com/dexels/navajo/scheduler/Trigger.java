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

	protected Access myAccess;
	private boolean swapInOut = false;
	
	/**
	 * Trigger URL:
	 * 
	 * time:xyz, e.g. time:*|*|10|10|*
	 * 
	 */
	public final static String TIME_TRIGGER = "time";
	public final static String WS_TRIGGER = "webservice";
	
	public abstract boolean alarm();
	public abstract void resetAlarm();
	public abstract String getDescription();
	public abstract void removeTrigger();
	
	public final static Trigger parseTrigger(String s) {
		if (s.startsWith(TIME_TRIGGER)) {
			String v = s.substring(5);
			TimeTrigger t = new TimeTrigger(v);
			return t;
		} else if (s.startsWith(WS_TRIGGER)) {
			
			String v = s.substring(11);
			WebserviceListener listener = WebserviceListener.getInstance();
			WebserviceTrigger t = new WebserviceTrigger(v, listener);
			listener.registerTrigger(t);
			return t;
		} else {
			return null;
		}
	}
	
	public void setSwapInOut(boolean b) {
		this.swapInOut = b;
	}
	
	public boolean swapInOut() {
		return this.swapInOut;
	}
	
	public Access getAccess() {
		return myAccess;
	}
	
	public void setAccess(Access a) {
		System.err.println("Setting access object: " + a);
		myAccess = a;
	}
}
