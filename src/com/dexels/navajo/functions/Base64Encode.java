/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Dexels BV</p>
 * @author 
 * @version $Id$.
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
package com.dexels.navajo.functions;

import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;

public class Base64Encode extends FunctionInterface {

	public String remarks() {
		return "Get a Base64 representation of a given string or binary(deprecated).";
	}

	public String usage() {
		return "Base64Encode(Binary|String)";
	}
    
    // I think this function does not work when you pass a Binary into it.
    // It is a bit pointless anyway, it is more like a 'clone' then.

	public Object evaluate() throws TMLExpressionException {
		Object o = getOperand(0);
		String data = null;
		if ( o instanceof Binary ) {
			Binary b = (Binary) o;
            // HUH?! Why?! Making a string out of 'random' binary data is stupid
            //
			data = new String(b.getData());
		} else if ( o instanceof String ) {
			data = (String) o;
		}
		sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();	
		String encoded = enc.encode(data.getBytes());
		
		Binary b = new Binary(encoded.getBytes());
		b.setMimeType("application/octet-stream");
		return b;
		
	}

}
