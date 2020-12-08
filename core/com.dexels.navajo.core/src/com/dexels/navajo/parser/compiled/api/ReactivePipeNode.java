/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.parser.compiled.api;

import java.util.List;
import java.util.Optional;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.Selection;
import com.dexels.navajo.expression.api.ContextExpression;
import com.dexels.navajo.expression.api.TipiLink;
import com.dexels.navajo.reactive.api.Reactive;
import com.dexels.navajo.reactive.api.ReactivePipe;
import com.dexels.navajo.reactive.api.ReactiveSource;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.MappableTreeNode;

public class ReactivePipeNode implements ContextExpression {
	private final Operand actual;

	public ReactivePipeNode(Optional<ReactiveSource> source, List<Object> transformers) {
		this.actual = source.isPresent() ? 
			new Operand(new ReactivePipe(source.get(), transformers),Reactive.ReactiveItemType.REACTIVE_PIPE.toString()) 
				:
				new Operand(transformers,Reactive.ReactiveItemType.REACTIVE_PARTIAL_PIPE.toString());
		
	}

	@Override
	public Operand apply(Navajo doc, Message parentMsg, Message parentParamMsg, Selection parentSel,
			MappableTreeNode mapNode, TipiLink tipiLink, Access access, Optional<ImmutableMessage> immutableMessage,
			Optional<ImmutableMessage> paramMessage) {
		return actual;
	}

	public boolean isStreamInput() {
		return ((ReactivePipe)this.actual.value).source.streamInput();
	}
	@Override
	public boolean isLiteral() {
		return true;
	}

	@Override
	public Optional<String> returnType() {
		return Optional.of(Reactive.ReactiveItemType.REACTIVE_PIPE.toString());
	}

	@Override
	public String expression() {
		return "some_reactive_expression";
	}
}
