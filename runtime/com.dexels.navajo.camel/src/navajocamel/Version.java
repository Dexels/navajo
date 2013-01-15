package navajocamel;

import navajoextension.AbstractCoreExtension;

import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.camel.adapter.CamelAdapterLibrary;


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


public class Version extends AbstractCoreExtension {


	
	private final static Logger logger = LoggerFactory.getLogger(Version.class);
	
	public Version() {
	}


	@Override
	public void start(BundleContext bc) throws Exception {
		super.start(bc);
		try {
			CamelAdapterLibrary library = new CamelAdapterLibrary();
			registerAll(library);
//			FunctionFactoryInterface fi= FunctionFactoryFactory.getInstance();
//			fi.init();
//			
//			fi.clearFunctionNames();
//
//
//			fi.injectExtension(library);
//			
//			for(String adapterName: fi.getAdapterNames(library)) {
////			FunctionDefinition fd = fi.getAdapterDefinition(adapterName,extensionDef);
//				fi.getAdapterConfig(library).get(adapterName);
////			FunctionDefinition fd = fi.getDef(extensionDef, adapterName);
//				
//				String adapterClass = fi.getAdapterClass(adapterName,library);
//				Class<?> c = null;
//				
//				try {
//					c = Class.forName(adapterClass);
//					 Dictionary<String, Object> props = new Hashtable<String, Object>();
//					 props.put("adapterName", adapterName);
//					 props.put("adapterClass", c.getName());
//
//					if(adapterClass!=null) {
//						context.registerService(Class.class.getName(), c, props);
//					}
//				} catch (Exception e) {
//					logger.error("Error loading class for adapterClass: "+adapterClass,e);
//				}
//				
//			}
		} catch (Throwable e) {
			logger.error("Trouble starting NavajoAdapters bundle",e);
			e.printStackTrace();
		}
	}
	
	@Override
	public void shutdown() {
	}


	public static BundleContext getDefaultBundleContext() {
		return FrameworkUtil.getBundle(Version.class).getBundleContext();
	}
	

}
