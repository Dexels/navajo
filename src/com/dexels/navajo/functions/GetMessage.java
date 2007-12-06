package com.dexels.navajo.functions;

import com.dexels.navajo.parser.*;
import com.dexels.navajo.document.Message;
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

public class GetMessage extends FunctionInterface {
  public GetMessage() {
  }
  public String remarks() {
    return "Gets a message from an array message";
  }

  public Object evaluate() throws com.dexels.navajo.parser.TMLExpressionException {
    if (getOperands().size() != 2) {
      throw new TMLExpressionException(this, "Invalid function call");
    }
    Object m = getOperand(0);
    if (m==null) {
        throw new TMLExpressionException(this, "Message argument expected. This one is null");
    }
    if (!(m instanceof Message)) {
      throw new TMLExpressionException(this, "Message argument expected");
    }

    Object o = getOperand(1);
   if (!(o instanceof Integer)) {
     throw new TMLExpressionException(this, "Integer argument expected");
   }

   Integer index = (Integer) o;
   Message message = (Message)m;
   return message.getMessage(index.intValue());
  }
  public String usage() {
    return "GetMessage(Message,int index). Returns array message element Built for tipi";
  }

}