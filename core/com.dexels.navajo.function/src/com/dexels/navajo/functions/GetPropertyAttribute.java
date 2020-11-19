package com.dexels.navajo.functions;

import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;


/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version $Id$
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

public class GetPropertyAttribute extends FunctionInterface {


	public GetPropertyAttribute() {
	}

	@Override
	public String remarks() {
		return "Gets the type of property as a string";
	}

	public Object getAttribute(Object operand, String attribute) throws com.dexels.navajo.expression.api.TMLExpressionException {
		Property p = operand instanceof Property ? (Property) operand : this.getNavajo().getProperty(operand.toString());
		if (p == null) {
			throw new TMLExpressionException(this, "Property " + operand.toString() + " not found");
		}
		if ( attribute.equals("direction") ) {
			return p.getDirection();
		} 
		if ( attribute.equals("type") ) {
			return p.getType();
		} 
		if ( attribute.equals("cardinality") ) {
			return p.getCardinality();
		} 
		try {
			throw new TMLExpressionException(this, "attribute " + attribute + " not found for property " + p.getFullPropertyName());
		} catch (NavajoException e) {
			throw new TMLExpressionException(this, "attribute " + attribute + " not found for unknown property ");
		}
	}

	@Override
	public Object evaluate() throws com.dexels.navajo.expression.api.TMLExpressionException {
		if (getOperands().size() != 2) {
			throw new TMLExpressionException(this, "Invalid function call");
		}
		Object operand = getOperands().get(0);
		String attribute = getStringOperand(1);

		return getAttribute(operand, attribute);
	}

	@Override
	public String usage() {
		return "GetPropertyAttribute(property name, attribute name)";
	}

}