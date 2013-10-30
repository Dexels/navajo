package com.dexels.navajo.functions;

import java.util.List;

import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.Selection;
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
 * @author Marte Koning
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

public class ExistsSelectionValue extends FunctionInterface {
	public ExistsSelectionValue() {
	}

	@Override
	public String remarks() {
		return "Checks if the given value is available as one of the options in the given selection property";
	}

	@Override
	@SuppressWarnings("unchecked")
	public Object evaluate()
			throws com.dexels.navajo.parser.TMLExpressionException {
		if (getOperands().size() != 2) {
			throw new TMLExpressionException(this,
					"Invalid function call, need two parameters");
		}
		Object o = getOperand(0);
		if (o == null) {
			throw new TMLExpressionException(this,
					"Invalid function call in ExistsSelectionValue: First parameter null");
		}
		Object v = getOperand(1);
		if (v == null) {
			throw new TMLExpressionException(this,
					"Invalid function call in ExistsSelectionValue: Second parameter null");
		}
		
		if (!(v instanceof String)) {
			throw new TMLExpressionException(this,
					"Invalid function call in ExistsSelectionValue: Second parameter not a string");
		}

		if ( o instanceof Property ) {
			Property p = (Property) o;
			try
			{
				Selection s = p.getSelectionByValue((String) v);
				return s.getValue() != Selection.DUMMY_ELEMENT;
			}
			catch(NavajoException ne)
			{
				throw new TMLExpressionException(this,
						"Invalid function call in ExistsSelectionValue: First parameter not a selection property");
			}
		}
		
		if (!(o instanceof List)) {
			throw new TMLExpressionException(this,
					"Invalid function call in ExistsSelectionValue: First parameter not a selection property");

		}
		List<Selection> l = (List<Selection>) o;
		for (Selection selection : l) {
			if (v.equals(selection.getValue())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String usage() {
		return "ExistsSelectionValue(<selection property>, <string>) -> will return true if and only if the string is a value for one of the selections";
	}

}