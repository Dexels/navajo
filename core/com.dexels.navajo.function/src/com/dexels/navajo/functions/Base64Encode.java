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

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;

public class Base64Encode extends FunctionInterface {

	
	@SuppressWarnings("unused")
	private final static Logger logger = LoggerFactory
			.getLogger(Base64Encode.class);
	
	@Override
	public String remarks() {
		return "Get a Base64 representation of a given string or binary.";
	}

	@Override
	public String usage() {
		return "Base64Encode(Binary|String)";
	}
	
	@Override
	public boolean isPure() {
		return true;
	}

	@Override
	public Object evaluate() throws TMLExpressionException {
		Object o = operand(0).value;
		String data = null;
		if ( o instanceof Binary ) {
			Binary b = (Binary) o;
			data = Base64.getEncoder().encodeToString(b.getData());
		} else if ( o instanceof String ) {
			data = Base64.getEncoder().encodeToString(((String)o).getBytes(StandardCharsets.UTF_8));
		} else {
			throw new TMLExpressionException("Can not Base64Encode null data");
		}
		
		return data;
	}
}
