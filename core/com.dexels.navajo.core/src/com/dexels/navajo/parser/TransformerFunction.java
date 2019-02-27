package com.dexels.navajo.parser;

import java.util.List;
import java.util.function.Function;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.navajo.document.Operand;

public interface TransformerFunction {
	public Function<ImmutableMessage,ImmutableMessage> create(List<Operand> parameters, List<String> problems);
	
}
