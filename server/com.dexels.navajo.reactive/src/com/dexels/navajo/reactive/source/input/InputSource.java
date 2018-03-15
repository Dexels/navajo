package com.dexels.navajo.reactive.source.input;

import java.util.Optional;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.DataItem.Type;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.reactive.api.ReactiveSource;

import io.reactivex.Flowable;

public class InputSource implements ReactiveSource {
	
	private Type finalType;

	public InputSource(DataItem.Type finalType) {
		this.finalType = finalType;
	}

	@Override
	public Flowable<DataItem> execute(StreamScriptContext context, Optional<ImmutableMessage> current) {
		return context.inputFlowable().map(DataItem::of);
	}

	@Override
	public Type finalType() {
		return finalType;
	}

}
