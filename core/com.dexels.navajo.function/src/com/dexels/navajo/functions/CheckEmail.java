package com.dexels.navajo.functions;

import java.util.regex.Pattern;

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

public class CheckEmail extends FunctionInterface {
	private static final String EMAIL_PATTERN = "^[\\w!#$%&�*+/=?`{|}~^-]+(?:\\.[\\w!#$%&�*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,9}$";
	
	
  @Override
public String remarks() {
   return "This functions checks the syntactic validity of email adressess";
  }
  
  @Override
	public boolean isPure() {
  		return true;
  }

  @Override
public Object evaluate() throws com.dexels.navajo.expression.api.TMLExpressionException {

    Object o = getOperand(0);
    if (!(o instanceof String)) {
    	return Boolean.FALSE;
      //throw new TMLExpressionException(this, "Invalid email address, string expected");
    }

    String email = (String) o;

    try {
     Pattern re = Pattern.compile(EMAIL_PATTERN);
     boolean isMatch = re.matcher(email).matches();
     if(!isMatch) {
       return Boolean.FALSE;
     } else
       return Boolean.TRUE;
   }
   catch (Exception ree) {
     return Boolean.FALSE;
   }

  }

  public static void main(String [] args ) throws TMLExpressionException {
	Boolean b = null;
	CheckEmail ce = new CheckEmail();
    ce.reset();
    ce.insertStringOperand("stefan@awesomo.amsterdam");
    b = (Boolean) ce.evaluate();
    System.err.println("result = " + b);
  }

}