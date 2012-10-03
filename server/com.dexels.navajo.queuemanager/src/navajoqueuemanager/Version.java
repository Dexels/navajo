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
package navajoqueuemanager;

import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;




public class Version extends com.dexels.navajo.version.AbstractVersion {

	
	@SuppressWarnings("rawtypes")
	private ServiceRegistration registration;

	@Override
	public void start(BundleContext bc) throws Exception {
		super.start(bc);
		try {
			if(bc!=null) {
				Dictionary<String, Object> wb = new Hashtable<String, Object>();
				wb.put("schedulerClass", "com.dexels.navajo.server.listener.http.schedulers.priority.PriorityThreadPoolScheduler");
				 wb.put("service.ranking", 10);
//				 registration = bc.registerService(TmlScheduler.class.getName(), new ServiceFactory<TmlScheduler>() {
//
//					@Override
//					public TmlScheduler getService(Bundle bundle,
//							ServiceRegistration<TmlScheduler> registration) {
//						PriorityThreadPoolScheduler ptps = new PriorityThreadPoolScheduler();
//						return ptps;
//					}
//
//					@Override
//					public void ungetService(Bundle bundle,
//							ServiceRegistration<TmlScheduler> registration,
//							TmlScheduler service) {
//						
//					}
//				},wb);
			}
		} catch (Throwable e) {
			logger.error("Activation start failed: ",e);
		}

	}

	@Override
	public void stop(BundleContext arg0) throws Exception {
		if(registration!=null) {
			registration.unregister();
		}
		super.stop(arg0);
	}
	
	

}

