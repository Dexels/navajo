/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.functions;

import java.util.ArrayList;
import java.util.List;

import com.dexels.navajo.document.Property;
import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;

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
 * @author Erik Versteeg
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

public class GetSelectedValues extends FunctionInterface {
	public GetSelectedValues() {
	}

	@Override
	public String remarks() {
		return "Gets the values of a selection property";
	}

	@Override
	@SuppressWarnings("unchecked")
	public Object evaluate()
			throws com.dexels.navajo.expression.api.TMLExpressionException {
		if (getOperands().size() > 2) {
			throw new TMLExpressionException(this,
					"Invalid function call, need one or two parameters");
		}
		Object o = operand(0).value;
		if (o == null) {
			throw new TMLExpressionException(this,
					"Invalid function call in GetSelectedValues: Parameter null");
		}

		boolean outputAsStringList = false;
		if (getOperands().size() > 1) {
			outputAsStringList = getBooleanOperand(1);
		}

		List<Object> values = new ArrayList<Object>();
		if ( o instanceof Property ) {
			Property p = (Property) o;
			if ( p.getSelected() != null ) {
				if (p.getAllSelectedSelections().size() > 0) {
					values.add(p.getAllSelectedSelections());
				}
			}
		}
		else if (o instanceof String) {
			values.add(o);
		} else {
			if (!(o instanceof List)) {
				throw new TMLExpressionException(this,
						"Invalid function call in GetSelectedValues: Not a selection property");
	
			}
			if (((List<Object>)o).size() != 0) {
				List<Object> l = (List<Object>) o;
				if (outputAsStringList) {
					for (Object selection : l) {
						values.add(selection.toString());
					}
				} else {
					values.add(l);
				}
			}
		}

		// Outputtype...
		if (outputAsStringList && values.size() != 0) {
			String output = "";
			for ( Object item : values) {
				output += item.toString() + ";";
			}
			return output;
		} else {
			return values;
		}
	}

	@Override
	public String usage() {
		return "GetSelectedValues(<selection property>) -> will return the selected values of a selection";
	}

}