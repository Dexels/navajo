/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.expression.api;

import java.util.Optional;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.Selection;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.MappableTreeNode;

public interface ContextExpression {

	public default Operand apply() {
		return apply(null,Optional.empty(),Optional.empty());
	}
	public default Operand apply(Navajo doc, Optional<ImmutableMessage> immutableMessage, Optional<ImmutableMessage> paramMessage) {
		return apply(doc,null,null,null,null,null,null,immutableMessage,paramMessage);
	}

	public Operand apply(Navajo doc, Message parentMsg, Message parentParamMsg, Selection parentSel, MappableTreeNode mapNode, TipiLink tipiLink, Access access, Optional<ImmutableMessage> immutableMessage, Optional<ImmutableMessage> paramMessage);
	public boolean isLiteral();
	public Optional<String> returnType();
	public String expression();

}
