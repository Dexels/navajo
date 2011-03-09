package com.dexels.navajo.functions;

import com.dexels.navajo.document.Property;
import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;

/**
 * <p>
 * Title: Navajo Product Project
 * </p>
 * <p>
 * Description: This is the official source for the Navajo server
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Dexels BV
 * </p>
 * 
 * @author Arjen Schoneveld
 * @version $Id$
 * 
 *          DISCLAIMER
 * 
 *          THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 *          WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 *          MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 *          IN NO EVENT SHALL DEXELS BV OR ITS CONTRIBUTORS BE LIABLE FOR ANY
 *          DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 *          DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE
 *          GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 *          INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER
 *          IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 *          OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN
 *          IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *          ====================================================================
 */

public class GetValue extends FunctionInterface {
	public GetValue() {
	}

	public String remarks() {
		return "Gets the value of a selected property";
	}

	public Object evaluate()
			throws com.dexels.navajo.parser.TMLExpressionException {
		if (getOperands().size() != 1) {
			throw new TMLExpressionException(this,
					"Invalid function call, need one parameter");
		}
		Object o = getOperand(0);
		if (o == null) {
			throw new TMLExpressionException(this,
					"Invalid function call in GetSelectedValue: Parameter null");
		}
		if (!(o instanceof Property)) {
			throw new TMLExpressionException(this,
					"Invalid function call in GetValue: Not a  property, this is a: "+o.getClass()+" and it goes like this:  "+o);

		}
		Property p = (Property)o;
		return p.getTypedValue();
	}

	public String usage() {
		return "GetValue(<property>) -> will return the typed value of a property";
	}

}