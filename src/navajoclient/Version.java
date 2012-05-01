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
package navajoclient;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedServiceFactory;

import com.dexels.navajo.client.NavajoClientFactory;
import com.dexels.navajo.client.context.ClientContext;
import com.dexels.navajo.client.context.NavajoContext;
import com.dexels.navajo.client.sessiontoken.SessionTokenFactory;
import com.dexels.navajo.client.systeminfo.SystemInfoFactory;

/**
 * VERSION HISTORY
 * 
 * 3.0.0  -Added support for Flexible Load Balancing.
 *        - Async Runner is server agnostic. It does not need to send the request
 *        - to the server that actually runs the async thread. This is handled by
 *        - the Dispatcher (TAG: NavajoRelease6_5_5)
 * 2.0.3. -Added more verbose timing information
 * 2.1.0. Added support for setting unique request id.
 * 2.2.0. -Support for text nodes within binary properties
 *        -Support for f#%ckin huge binaries on java 1.5
 *        -HTML client now works with any document version
 *        -HTML client does not waste as much memory as it used to
 *        -The streaming binaries only work well on 1.5, under 1.4
 *         the HTTP implentation will store all data in memory before
 *         sending.
 * 2.2.1. Added debug data.
 * 2.2.2. Closing input stream when creatin Navajo (for SAXP).
 * 2.2.3. Added support for piggybacking of data.
 * 2.2.4. Removed evil keep-alive
 */
public class Version extends com.dexels.navajo.version.AbstractVersion implements ManagedServiceFactory {

	private final Map<String,NavajoContext> contextMap = new HashMap<String, NavajoContext>();
	private final Map<String,ServiceRegistration<ClientContext>> registryMap = new HashMap<String,ServiceRegistration<ClientContext>>();

	@Override
	public void shutdown() {
		NavajoClientFactory.resetClient();
		SessionTokenFactory.clearInstance();
		SystemInfoFactory.clearInstance();
		super.shutdown();
	}
	
	@Override
	public void start(BundleContext bc) throws Exception {
		super.start(bc);
		Dictionary<String,String> d = new Hashtable<String,String>();
		d.put(Constants.SERVICE_PID, "navajo.client.Factory");
		bc.registerService(ManagedServiceFactory.class.getName(), this, d);
	}
	
	
	@Override
	public void deleted(String pid) {
		logger.info("Shutting down instance: "+pid);
		NavajoContext nc = contextMap.get(pid);
		if(nc==null) {
			logger.warn("Strange: Deleting, but already gone.");
			return;
		}
		contextMap.remove(pid);
		ServiceRegistration<ClientContext> reg = registryMap.get(pid);
		reg.unregister();
	}

	@Override
	public String getName() {
		return "Navajo Client Factory";
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void updated(String pid, Dictionary settings)
			throws ConfigurationException {
		logger.info("CONFIG CHANGE DETECTED:");
		if(settings==null) {
			logger.info("Disabling Navajo client configuration: "+pid);
			return;
		}
		
		Enumeration en = settings.keys();
		while (en.hasMoreElements()) {
			Object o = en.nextElement();
			logger.info("Element: "+o+" : "+settings.get(o));
		}
		
		NavajoContext nc = new NavajoContext();
		nc.setupClient((String)settings.get("server"), (String)settings.get("username"), (String)settings.get("password"));
		
		ServiceRegistration reg = getBundleContext().registerService(ClientContext.class.getName(), nc, settings);

		registryMap.put(pid, reg);
		logger.info("Activating NavajoClient component: "+settings);
		contextMap.put(pid, nc);
	}
	
	
}