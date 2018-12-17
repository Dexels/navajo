package com.dexels.navajo.functions;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.expression.api.FunctionInterface;
import com.dexels.navajo.expression.api.TMLExpressionException;

/**
 * <p>
 * Title: Navajo Product Project
 * </p>
 * <p>
 * Description: This is the official source for the Navajo server
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Dexels BV
 * </p>
 * 
 * @author Arjen Schoneveld
 * @version $Id$
 * 
 *          DISCLAIMER
 * 
 *          THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 *          WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 *          MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 *          IN NO EVENT SHALL DEXELS BV OR ITS CONTRIBUTORS BE LIABLE FOR ANY
 *          DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 *          DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE
 *          GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 *          INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER
 *          IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 *          OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN
 *          IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *          ====================================================================
 */

public class GetProperty extends FunctionInterface {
	public GetProperty() {
	}

	@Override
	public String remarks() {
		return "Gets a property from a string";
	}

	@Override
	public Object evaluate() throws com.dexels.navajo.expression.api.TMLExpressionException {
		if (getOperands().size() != 2) {
			throw new TMLExpressionException(this, "Invalid function call");
		}
		Object o = getOperand(1);
		if (!(o instanceof String)) {
			throw new TMLExpressionException(this, "String argument expected");
		}

		String propertyName = (String) o;

		Object m = getOperand(0);
		if (m == null) {
			throw new TMLExpressionException(this, "Message or Navajo argument expected. This one is null");
		}
		if (!(m instanceof Message) && !(m instanceof Navajo)) {
			throw new TMLExpressionException(this, "Message argument expected");
		}
		if (m instanceof Message) {
			Message message = (Message) m;
			return message.getProperty(propertyName);
		}
		if (m instanceof Navajo) {
			Navajo message = (Navajo) m;
			return message.getProperty(propertyName);
		}
		return null;
	}

	@Override
	public String usage() {
		return "GetProperty(Message,propertyname). Returns a property (not its value.) Built for tipi";
	}

}