package com.dexels.navajo.functions;

import com.dexels.navajo.parser.*;
import java.util.regex.Pattern;


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

public class CheckEmail extends FunctionInterface {

  public String remarks() {
   return "This functions checks the syntactic validity of email adressess";
  }
  public Object evaluate() throws com.dexels.navajo.parser.TMLExpressionException {

    Object o = getOperand(0);
    if (!(o instanceof String)) {
    	return new Boolean(false);
      //throw new TMLExpressionException(this, "Invalid email address, string expected");
    }

    String email = (String) o;

    try {
     Pattern re = Pattern.compile("[A-z.\\-_0-9]+[@]{1}[A-z\\-_0-9]+[A-z.\\-_0-9]+[A-z\\-_0-9]{1}");
     boolean isMatch = re.matcher(email).matches();
     if(!isMatch) {
       return new Boolean(false);
     } else
       return new Boolean(true);
   }
   catch (Exception ree) {
     return new Boolean(false);
   }

  }
  public String usage() {
    return "CheckEmail(adress)";
  }

  public static void main(String [] args ) throws TMLExpressionException {
    CheckEmail ce = new CheckEmail();
    ce.reset();
    ce.insertOperand((String)null);
    Boolean b = (Boolean) ce.evaluate();
    System.err.println("result = " + b);
  }

}