package com.dexels.navajo.parser.compiled.api;

import java.util.Optional;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Selection;
import com.dexels.navajo.parser.TMLExpressionException;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.MappableTreeNode;
import com.dexels.navajo.tipilink.TipiLink;
import com.dexels.replication.api.ReplicationMessage;

public interface ContextExpression {
	public Object apply(Navajo doc, Message parentMsg, Message parentParamMsg, Selection parentSel, MappableTreeNode mapNode, TipiLink tipiLink, Access access, Optional<ReplicationMessage> immutableMessage) throws TMLExpressionException;
	public boolean isLiteral();
}