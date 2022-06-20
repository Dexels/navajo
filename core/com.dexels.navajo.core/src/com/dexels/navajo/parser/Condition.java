/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/

/**
 * Title:        Navajo<p>
 * Description:  <p>
 * Copyright:    Copyright (c) Arjen Schoneveld<p>
 * Company:      Dexels<p>
 * @author Arjen Schoneveld
 * @version $Id$
 */
package com.dexels.navajo.parser;

import java.util.HashMap;
import java.util.Map;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Operand;
import com.dexels.navajo.expression.api.TMLExpressionException;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.MappableTreeNode;
import com.dexels.navajo.script.api.SystemException;

public final class Condition {

	public final static boolean evaluate(String clause, Navajo inMessage, MappableTreeNode o, Message parent,
		Message paramParent, Access access) throws TMLExpressionException, SystemException {
		Map<String,Object> params = new HashMap<>();
		params.put(Expression.ACCESS, access);
		Operand evaluate = Expression.evaluate(clause, inMessage, o, parent, paramParent, null, null, params); //valuate(clause, inMessage, o, parent, paramParent);
		return (boolean) evaluate.value;
	}

	public final static boolean evaluate(String clause, Navajo inMessage, Access a)
			throws TMLExpressionException, SystemException {
		return evaluate(clause, inMessage, null, null, null, a);
	}

	public final static boolean evaluate(String clause, Navajo inMessage, MappableTreeNode o, Message parent, Access a)
			throws TMLExpressionException, SystemException {
		return evaluate(clause, inMessage, o, parent, null, a);
	}
}
