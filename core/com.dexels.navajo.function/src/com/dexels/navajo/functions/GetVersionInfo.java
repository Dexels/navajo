/*
This file is part of the Navajo Project.
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt.
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
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

	@Override
	public final Object evaluate() throws TMLExpressionException {

	    Object o = getOperand(0);
		String packageName = o+"";

		try {
			Class<?> c = null;
			if (DispatcherFactory.getInstance().getNavajoConfig().getClassloader() ==null) {
				c = Class.forName(packageName+".Version");
			} else {
				c = Class.forName(packageName+".Version",true,DispatcherFactory.getInstance().getNavajoConfig().getClassloader());
			}
			AbstractVersion v = (AbstractVersion) c.getDeclaredConstructor().newInstance();

			return v.toString();
		} catch (Exception e) {
			throw new TMLExpressionException(this, "Could not find version object for package: " + packageName);
		}
	}

	public static void main(String [] args) throws Exception {

		GetVersionInfo v = new GetVersionInfo();
		v.reset();
		v.insertStringOperand("navajo");
		System.err.println(v.evaluate());
	}

}
