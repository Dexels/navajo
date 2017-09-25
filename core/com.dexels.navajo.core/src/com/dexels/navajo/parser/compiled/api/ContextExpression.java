package com.dexels.navajo.parser.compiled.api;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Selection;
import com.dexels.navajo.parser.TMLExpressionException;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.MappableTreeNode;
import com.dexels.navajo.tipilink.TipiLink;

public interface ContextExpression {
	public Object apply(Navajo doc, Message parentMsg, Message parentParamMsg, Selection parentSel, String selectionOption, MappableTreeNode mapNode, TipiLink tipiLink, Access access) throws TMLExpressionException;
	public boolean isLiteral();
}
