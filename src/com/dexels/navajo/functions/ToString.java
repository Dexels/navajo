package com.dexels.navajo.functions;

import com.dexels.navajo.parser.*;
import com.dexels.navajo.document.types.Binary;
import java.io.StringWriter;


/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version 1.0
 *
 * $Id$
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

public final class ToString extends FunctionInterface {

   public final Object evaluate() throws com.dexels.navajo.parser.TMLExpressionException {
        Object s = this.getOperands().get(0);

        if (s == null) {
          return null;
        }

        if (s instanceof com.dexels.navajo.document.types.Binary) {
          Binary b = (Binary) s;
          byte [] data = b.getData();
          StringWriter w = new StringWriter();
          for (int i = 0; i < data.length; i++) {
            w.write(data[i]);
          }
          return w.toString();
        }

        if (s != null)
          return s.toString();
        else
          return null;
    }

    public String usage() {
        return "ToString(Object)";
    }

    public String remarks() {
        return "Returns a string representation of the supplied object.";
    }
}