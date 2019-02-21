package com.dexels.navajo.functions;

import com.dexels.navajo.document.Message;
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

public class GetPropertySubType extends FunctionInterface {
  public GetPropertySubType() {
  }
  @Override
public String remarks() {
    return "Gets the subtype of property as a string";
  }
  @Override
public Object evaluate() throws com.dexels.navajo.expression.api.TMLExpressionException {
    if (getOperands().size() != 2) {
      throw new TMLExpressionException(this, "Invalid function call");
    }
   Object o = operand(0).value;
//   Object subtypeObject = getOperand(1);
//   if(!(subtypeObject instanceof String)) {
//	      throw new TMLExpressionException(this, "Invalid function call");
//   }
   String subType = getStringOperand(1);
   if (o instanceof Property) {
	   Property p = (Property)o;
	   return p.getSubType(subType);
   } else {
		   if (!(o instanceof String)) {
			     throw new TMLExpressionException(this, "String argument expected");
			   }
			   Message curMsg = getCurrentMessage();
			   String propertyName = (String) o;
			   Property p = (curMsg != null ? curMsg.getProperty(propertyName) : this.getNavajo().getProperty(propertyName));
			   if (p == null) {
			     throw new TMLExpressionException(this, "Property " + propertyName + " not found");
			   }
			   return p.getSubType(subType);
	}
  }
  @Override
public String usage() {
   return "GetPropertySubType(String propertyName, string subtypeName) OR GetPropertySubType(Property p, String subtypeName)";
  }

}