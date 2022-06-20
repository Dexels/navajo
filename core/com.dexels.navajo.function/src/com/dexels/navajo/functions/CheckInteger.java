/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.functions;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.expression.api.FunctionInterface;


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

public class CheckInteger extends FunctionInterface {

  @Override
public String remarks() {
   return "CheckInteger checks whether argument is valid integer";
  }

  @Override
public Object evaluate() throws com.dexels.navajo.expression.api.TMLExpressionException {

//    boolean force = false;
    Object o = operand(0).value;

    // If strict flag is set, properties can be passed as string values.
    if (getOperands().size() > 1) {
//      Object o2 = getOperand(1);
//      if (o2 instanceof Boolean) {
//        force = ((Boolean) o2).booleanValue();
//      }
    	  final Message curMsg = getCurrentMessage();
      String propertyName = (String) o;
      Property p = (curMsg != null ? curMsg.getProperty(propertyName) : this.getNavajo().getProperty(propertyName));
      if (p != null) {
        o = p.getValue();
      }
    }

    try {
      Integer.parseInt(o+"");
      return Boolean.TRUE;
    } catch (Exception e) {
      return Boolean.FALSE;
    }
  }

  @Override
public String usage() {
    return "CheckInteger(argument)";
  }


}