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
package navajofunctions;

import java.util.Date;
import java.util.Properties;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;


import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.functions.StandardFunctionDefinitions;
import com.dexels.navajo.functions.util.FunctionDefinition;
import com.dexels.navajo.functions.util.FunctionFactoryFactory;
import com.dexels.navajo.functions.util.FunctionFactoryInterface;
import com.dexels.navajo.functions.util.JarFunctionFactory;
import com.dexels.navajo.parser.DefaultExpressionEvaluator;
import com.dexels.navajo.parser.Expression;
import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;
import com.dexels.navajo.server.SystemException;

/**
 * VERSION HISTORY
 * 
 * 2.0.0. Now using function type signatures and XML definitions.
 * 
 * 1.0.1. Added GetVersionInfo function.
 * 
 * 1.1.0. Added various new functions (GetProperty, FromSeconds, ToSeconds, Base64Encode).
 * 
 * 1.1.1. Added IsNull function.
 * 
 * 1.1.2. Fix in ToClockTime
 */
public class Version extends dexels.Version {


	//Included packages.
	
	public Version() {
		setReleaseDate(RELEASEDATE);
		
	}
	
	

	@Override
	public void start(BundleContext bc) throws Exception {
		super.start(bc);
//		FunctionFactoryInterface fi= FunctionFactoryFactory.getInstance();
//		fi.init();
//		fi.clearFunctionNames();
//		fi.injectExtension(new StandardFunctionDefinitions());
////		System.err.println("Detected functions: "+fi.getFunctionNames());
//		for (String functionName : fi.getFunctionNames()) {
//			FunctionDefinition fd = fi.getDef(functionName);
//			 Properties props = new Properties();
//			 System.err.println("Registering: "+functionName);
//			 props.put("functionName", functionName);
//			 props.put("functionDefinition", fd);
//			context.registerService(FunctionInterface.class.getName(), fi.instantiateFunctionClass(fd,getClass().getClassLoader()), props);
//		}
//		
////	        props.put("Language", "English");
////	        context.registerService(
////	            DictionaryService.class.getName(), new DictionaryImpl(), props);
	}



	@Override
	public void stop(BundleContext arg0) throws Exception {
		// TODO Auto-generated method stub
		super.stop(arg0);
	}



}