package com.dexels.navajo.parser;

import java.util.List;
import java.util.function.Function;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.navajo.document.Operand;

public class RenameTransformerFunction implements TransformerFunction {

	@Override
	public Function<ImmutableMessage, ImmutableMessage> create(List<Operand> parameters, List<String> problems) {
		int size = parameters.size();
		if(size!=2) {
			problems.add("Transformer: rename needs 2 parameters");
			return r->r;
		}
		String fromKey = (String) parameters.get(0).value;
		String to = (String) parameters.get(1).value;

		return in->{
			Object oldValue = in.columnValue(fromKey);
			String oldType = in.columnType(fromKey);
			return in.without(fromKey ).with(to,oldValue, oldType);
		};
	}

}
