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
package navajofunctionspdf;

import java.util.Dictionary;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import com.dexels.navajo.functions.util.FunctionDefinition;
import com.dexels.navajo.functions.util.FunctionFactoryFactory;
import com.dexels.navajo.functions.util.FunctionFactoryInterface;
import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.pdf.functions.PDFFunctionDefinitions;


public class Version extends com.dexels.navajo.version.AbstractVersion {

	@SuppressWarnings("rawtypes")
	private final Set<ServiceRegistration> registrations = new HashSet<ServiceRegistration>();
	//Included packages.
	@SuppressWarnings("rawtypes")
	private ServiceRegistration registration;
	
	public Version() {
	}
	
	

	@Override
	public void start(BundleContext bc) throws Exception {
		super.start(bc);
		FunctionFactoryInterface fi= FunctionFactoryFactory.getInstance();
		fi.init();
		fi.clearFunctionNames();
		PDFFunctionDefinitions extensionDef = new PDFFunctionDefinitions();
		fi.injectExtension(extensionDef);
//		System.err.println("Detected functions: "+fi.getFunctionNames());
		for (String functionName : fi.getFunctionNames(extensionDef)) {
			FunctionDefinition fd = fi.getDef(extensionDef,functionName);
			 Dictionary<String, Object> props = new Hashtable<String, Object>();
			 props.put("functionName", functionName);
			 props.put("functionDefinition", fd);
			 props.put("type", "function");
			 registration = context.registerService(FunctionInterface.class.getName(), fi.instantiateFunctionClass(fd,getClass().getClassLoader()), props);
			 registrations.add(registration);
		}
		
//	        props.put("Language", "English");
//	        context.registerService(
//	            DictionaryService.class.getName(), new DictionaryImpl(), props);
	}



	@Override
	public void stop(BundleContext arg0) throws Exception {
		super.stop(arg0);
		if(registration!=null) {
			registration.unregister();
		}
	}



	public static void main(String [] args) {
		Version v = new Version();
		System.err.println(v.toString());
		com.dexels.navajo.version.AbstractVersion [] d = v.getIncludePackages();
		for (int i = 0; i < d.length; i++) {
			System.err.println("\t"+d[i].toString());
		}
	}
}