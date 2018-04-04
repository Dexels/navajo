package com.dexels.navajo.parser.compiled.api;

import java.util.Optional;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Selection;
import com.dexels.navajo.parser.TMLExpressionException;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.MappableTreeNode;
import com.dexels.navajo.tipilink.TipiLink;

public interface ContextExpression {
	public Object apply(Navajo doc, Message parentMsg, Message parentParamMsg, Selection parentSel, MappableTreeNode mapNode, TipiLink tipiLink, Access access, Optional<ImmutableMessage> immutableMessage, Optional<ImmutableMessage> paramMessage) throws TMLExpressionException;
	public boolean isLiteral();
	public Optional<String> returnType();
}
