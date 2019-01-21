package com.dexels.navajo.reactive.source.ndjson;

import java.util.Optional;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.DataItem.Type;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.reactive.api.ReactiveSource;

import io.reactivex.Flowable;

public class NDJsonSource implements ReactiveSource {

	@Override
	public Flowable<DataItem> execute(StreamScriptContext context, Optional<ImmutableMessage> current, ImmutableMessage param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean streamInput() {
		return false;
	}

	@Override
	public Type sourceType() {
		return null;
	}

}
