package com.dexels.navajo.functions;

import java.util.List;

import com.dexels.navajo.document.Selection;
import com.dexels.navajo.parser.FunctionInterface;
import com.dexels.navajo.parser.TMLExpressionException;

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

public class GetSelectedName extends FunctionInterface {
	public GetSelectedName() {
	}

	public String remarks() {
		return "Gets the name of a selected property";
	}

	@SuppressWarnings("unchecked")
	public Object evaluate()
			throws com.dexels.navajo.parser.TMLExpressionException {
		if (getOperands().size() != 1) {
			throw new TMLExpressionException(this,
					"Invalid function call, need one parameter");
		}
		Object o = getOperand(0);
		if (o == null) {
			throw new TMLExpressionException(this,
					"Invalid function call in GetSelectedName: Parameter null");
		}
		if (!(o instanceof List)) {
			throw new TMLExpressionException(this,
					"Invalid function call in GetSelectedName: Not a selection property");

		}
		List<Selection> l = (List<Selection>) o;
		for (Selection selection : l) {
			if (selection.isSelected()) {
				return selection.getName();
			}
		}
		return null;
	}

	public String usage() {
		return "GetSelectedName(<value of selection property>) -> will return the first name of a selected selection";
	}

}