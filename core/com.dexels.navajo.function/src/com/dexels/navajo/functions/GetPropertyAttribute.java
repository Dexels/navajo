package com.dexels.navajo.functions;

import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;


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

abstract class GetPropertyAttribute extends FunctionInterface {


	public GetPropertyAttribute() {
	}

	public String remarks() {
		return "Gets the type of property as a string";
	}

	public Object getAttribute(String propertyName, String attribute) throws com.dexels.navajo.parser.TMLExpressionException {


		Property p = (currentMessage != null ? currentMessage.getProperty(propertyName) : this.getNavajo().getProperty(propertyName));
		if (p == null) {
			throw new TMLExpressionException(this, "Property " + propertyName + " not found");
		}
		if ( attribute.equals("direction") ) {
			return p.getDirection();
		} 
		if ( attribute.equals("type") ) {
			return p.getType();
		} 
		try {
			throw new TMLExpressionException(this, "attribute " + attribute + " not found for property " + p.getFullPropertyName());
		} catch (NavajoException e) {
			throw new TMLExpressionException(this, "attribute " + attribute + " not found for unknown property ");
		}
	}

	public Object evaluate() throws com.dexels.navajo.parser.TMLExpressionException {
		if (getOperands().size() != 2) {
			throw new TMLExpressionException(this, "Invalid function call");
		}
		Object o = getOperand(0);
		if (!(o instanceof String)) {
			throw new TMLExpressionException(this, "String argument expected");
		}
		Object o2 = getOperand(1);
		if (!(o2 instanceof String)) {
			throw new TMLExpressionException(this, "String argument expected");
		}

		String propertyName = (String) o;
		String attribute = (String) o2;

		return getAttribute(propertyName, attribute);
	}

	public String usage() {
		return "GetPropertyAttribute(property name, attribute name)";
	}

}