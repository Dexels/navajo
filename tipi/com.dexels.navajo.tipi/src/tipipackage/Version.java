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
package tipipackage;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import tipi.TipiCoreExtension;

public class Version extends com.dexels.navajo.version.AbstractVersion {

	private ServiceRegistration registration = null;
	// Included packages.
	public Version() {
		// addIncludes(includes);
	}

	public void start(BundleContext bc) throws Exception {
		super.start(bc);
		ITipiExtensionRegistry ter = new TipiOSGiWhiteboardExtensionProvider(bc);
		// TODO save and deregister
		registration = context.registerService(ITipiExtensionRegistry.class.getName(), ter,
				null);
		TipiCoreExtension tce = new TipiCoreExtension();
		tce.start(bc);
	}

	@Override
	public void stop(BundleContext bc) throws Exception {
		if(registration!=null) {
			registration.unregister();
		}
		super.stop(bc);
	}
	
	

}
