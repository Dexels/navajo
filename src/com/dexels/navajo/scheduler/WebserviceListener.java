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

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.HashSet;



public class WebserviceListener {

	static WebserviceListener instance = null;
	
	private Set triggers = null;
	
	public static WebserviceListener getInstance() {
		if ( instance == null ) {
			instance = new WebserviceListener();
			instance.triggers = Collections.synchronizedSet(new HashSet());
		}
		return instance;
	}
	
	public void registerTrigger(WebserviceTrigger t) {
		triggers.add(t);
		System.err.println("Added webservice trigger " + t.getDescription());
	}
	
	public void removeTrigger(WebserviceTrigger t) {
		triggers.remove(t);
		System.err.println("Removed webservice trigger " + t.getDescription());
	}
	
	public void invocation(String webservice) {
		Iterator iter = triggers.iterator();
		while ( iter.hasNext() ) {
			WebserviceTrigger t = (WebserviceTrigger) iter.next();
			if ( t.getWebservice().equals(webservice) ) {
				t.setAlarm();
			}	
		}
	}
}
