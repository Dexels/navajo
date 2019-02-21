package com.dexels.navajo.functions;

import com.dexels.navajo.document.Message;
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

public class GetMessage extends FunctionInterface {
  public GetMessage() {
  }
  @Override
public String remarks() {
    return "Gets a message from an array message";
  }

  @Override
public Object evaluate() throws com.dexels.navajo.expression.api.TMLExpressionException {
    if (getOperands().size() == 1) {
   	 return getMessageFromNavajo(getStringOperand(0));
    }
    if (getOperands().size() != 2) {
      throw new TMLExpressionException(this, "Invalid function call");
    }
    Object m = operand(0).value;
    if (m==null) {
        throw new TMLExpressionException(this, "Message argument expected. This one is null");
    }
    if (!(m instanceof Message)) {
      throw new TMLExpressionException(this, "Message argument expected");
    }

   Integer index = getIntegerOperand(1);
   Message message = (Message)m;
   return message.getMessage(index.intValue());
  }


  private Message getMessageFromNavajo(String path) {
	  return inMessage.getMessage(path);
}

@Override
public String usage() {
    return "GetMessage(Message,int index). Returns array message element Built for tipi. OR: GetMessage('MessagePath') will retrieve the message from the current navajo (Used for scripts)";
  }

}