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
package com.dexels.navajo.functions;

import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;
import com.dexels.navajo.server.DispatcherFactory;
import com.dexels.navajo.version.AbstractVersion;

public final class GetVersionInfo extends FunctionInterface {

	@Override
	public String remarks() {
		return "Gets the version info of a specific Version object";
	}

	@Override
	public String usage() {
		return "GetVersionInfo([package name])";
	}

	@SuppressWarnings("unchecked")
	@Override
	public final Object evaluate() throws TMLExpressionException {
		Object o = getOperand(0);
		String packageName = o+"";
		try {
			
			Class<AbstractVersion> c = null;
			if (DispatcherFactory.getInstance().getNavajoConfig().getClassloader() ==null) {
				c = (Class<AbstractVersion>) Class.forName(packageName+".Version");
			} else {
				c = (Class<AbstractVersion>) DispatcherFactory.getInstance().getNavajoConfig().getClassloader().getClass(packageName+".Version");
			}
//			return "DUMMY FUNCTION, FIX ME!";
			AbstractVersion v = c.newInstance();
			return v.toString();
		} catch (Exception e) {
			throw new TMLExpressionException(this, "Could not find version object for package: " + packageName);
		}
	}
	
	public static void main(String [] args) throws Exception {
		GetVersionInfo v = new GetVersionInfo();
		v.reset();
		v.insertOperand("navajo");
		System.err.println(v.evaluate());
	}

}
