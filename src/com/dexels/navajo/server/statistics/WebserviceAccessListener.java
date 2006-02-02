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
package com.dexels.navajo.server.statistics;

import java.util.HashMap;

import com.dexels.navajo.server.Access;

public class WebserviceAccessListener {

	private static WebserviceAccessListener instance = null;
	private HashMap serviceMap;
	
	public static WebserviceAccessListener getInstance() {
		if ( instance == null ) {
			instance = new WebserviceAccessListener();
			instance.serviceMap = new HashMap();
		}
		return instance;
	}
	
	public void addAccess(Access a, Exception e) {
		WebserviceAccess wa = (WebserviceAccess) serviceMap.get(a.rpcName);
		if ( wa == null ) {
			wa = new WebserviceAccess(a.rpcName);
			serviceMap.put(a.rpcName, wa);
		}
		wa.nextAccess(a.getTotaltime());
		if ( e != null ) {
			wa.registerError(e);
		}
	}
	
	public WebserviceAccess getAccessInfo(String webservice) {
		return (WebserviceAccess) serviceMap.get(webservice);
	}
}
