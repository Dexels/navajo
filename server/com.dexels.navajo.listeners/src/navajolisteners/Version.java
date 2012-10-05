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
package navajolisteners;

import java.util.Dictionary;
import java.util.Hashtable;

import javax.servlet.Servlet;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import com.dexels.navajo.script.api.SchedulerRegistry;
import com.dexels.navajo.server.listener.http.TmlHttpServlet;

public class Version extends com.dexels.navajo.version.AbstractVersion {

	// Included packages.

	@SuppressWarnings("rawtypes")
	private ServiceRegistration reference;

	private ServiceRegistration<?> legacyPostman;

	private static BundleContext bundleContext;

	public Version() {
		// javax.mail.Address a;
		// setReleaseDate(RELEASEDATE);

	}

	
	@Override
	public void start(BundleContext bc) {
		try {
			super.start(bc);
			bundleContext = bc;
			if(bc!=null) {
//				DummyScheduler ds = new DummyScheduler();
//				 Dictionary<String, Object> wb = new Hashtable<String, Object>();
//				 wb.put("schedulerClass", "com.dexels.navajo.server.listener.http.schedulers.DummyScheduler");
//				 wb.put("ranking", 0);
//				reference = bc.registerService(TmlScheduler.class.getName(), ds, wb);

				
				 Dictionary<String, Object> listener = new Hashtable<String, Object>();
				 listener.put("alias", "/PostmanLegacy");
				TmlHttpServlet tmlHttpServlet = new TmlHttpServlet();
				tmlHttpServlet.setBundleContext(bc);
				legacyPostman = bc.registerService(Servlet.class.getName(), tmlHttpServlet, listener);

			}
			} catch (Throwable e) {
				logger.error("Error: ", e);
			}
	}


	public void stop(BundleContext bc) throws Exception {
		if(reference!=null) {
			reference.unregister();
		}
		if(legacyPostman!=null) {
			legacyPostman.unregister();
		}
		bundleContext = null;

	}
	
	@Override
	public void shutdown() {
		super.shutdown();
		SchedulerRegistry.setScheduler(null);
	}

	public static BundleContext getDefaultBundleContext() {
		return bundleContext;
	}


	public static boolean hasOSGiBundleContext() {
		return bundleContext!=null;
	}

}
