package com.dexels.navajo.functions;

import com.dexels.navajo.parser.*;
import com.dexels.navajo.document.Property;


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

  public String remarks() {
   return "CheckInteger checks whether argument is valid integer";
  }

  public Object evaluate() throws com.dexels.navajo.parser.TMLExpressionException {

    boolean force = false;
    Object o = getOperand(0);

    // If strict flag is set, properties can be passed as string values.
    if (getOperands().size() > 1) {
      Object o2 = getOperand(1);
      if (o2 instanceof Boolean) {
        force = ((Boolean) o2).booleanValue();
      }
      String propertyName = (String) o;
      Property p = (currentMessage != null ? currentMessage.getProperty(propertyName) : this.getNavajo().getProperty(propertyName));
      if (p != null) {
        o = p.getValue();
      }
    }

    try {
      Integer.parseInt(o+"");
      return new Boolean(true);
    } catch (Exception e) {
      return new Boolean(false);
    }
  }

  public String usage() {
    return "CheckInteger(argument)";
  }

  public static void main(String [] args) throws Exception {
    CheckInteger ci = new CheckInteger();
    ci.reset();
    ci.insertOperand(new String("aap"));
    Object result = ci.evaluate();
    System.err.println("result = " + result);

    ci.reset();
    ci.insertOperand(new String("3432"));
    result = ci.evaluate();
    System.err.println("result = " + result);

  }
}