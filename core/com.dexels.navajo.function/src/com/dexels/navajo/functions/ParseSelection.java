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

import java.util.StringTokenizer;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Selection;
import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;

public class ParseSelection extends FunctionInterface {

	@Override
	public String remarks() {
		return "This function creates a selection out of a semi-colon separated string of options";
	}

	@Override
	public String usage() {
		return "ParseSelection(String)";
	}

	@Override
	public Object evaluate() throws TMLExpressionException {
		if ( getOperands().size() == 0 ) {
			throw new TMLExpressionException(this, "Operand expected");
		}
		Object o = getOperand(0);
		if ( o instanceof String ) {
			StringTokenizer st = new StringTokenizer((String) o, ";");
			Navajo dummy = NavajoFactory.getInstance().createNavajo();
			Selection [] allSelections = new Selection[st.countTokens()];
			int index = 0;
			while (st.hasMoreTokens()) {
				String value = st.nextToken();
				allSelections[index++] = NavajoFactory.getInstance().createSelection(dummy, value, value, true);
			}
			return allSelections;
		} else {
			throw new TMLExpressionException(this, "String expected");
		}
	}

	public static void main(String [] args) throws Exception {
		ParseSelection p = new ParseSelection();
		p.reset();
		p.insertStringOperand("A;B;C");
		Object o = p.evaluate();
		System.err.println("o = " + ((Selection []) o)[2]);
	}
}
