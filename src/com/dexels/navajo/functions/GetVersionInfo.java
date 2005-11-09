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

import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;
import com.dexels.navajo.server.Dispatcher;

import dexels.Version;

public class GetVersionInfo extends FunctionInterface {

	public String remarks() {
		return "Gets the version info of a specific Version object";
	}

	public String usage() {
		return "GetVersionInfo([package name])";
	}

	public Object evaluate() throws TMLExpressionException {
		Object o = getOperand(0);
		String packageName = o+"";
		try {
			Class c = Class.forName(packageName+".Version", true, Dispatcher.getNavajoClassLoader());
			Version v = (Version) c.newInstance();
			return v.toString();
		} catch (Exception e) {
			throw new TMLExpressionException(this, "Could not find version object for package: " + packageName);
		}
	}
	
	public static void main(String [] args) throws Exception {
		GetVersionInfo v = new GetVersionInfo();
		v.reset();
		v.insertOperand("sportlinkadapters");
		System.err.println(v.evaluate());
	}

}
