package com.dexels.navajo.reactive.source.ndjson;

import java.util.Optional;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.DataItem.Type;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveSource;
import com.dexels.navajo.reactive.api.SourceMetadata;

import io.reactivex.Flowable;

public class NDJsonSource implements ReactiveSource {

	private final ReactiveParameters params;
	private final SourceMetadata metadata;
	
	public NDJsonSource(SourceMetadata metadata, ReactiveParameters params) {
		this.metadata = metadata;
		this.params = params;
	}

	@Override
	public Flowable<DataItem> execute(StreamScriptContext context, Optional<ImmutableMessage> current, ImmutableMessage param) {
		return Flowable.empty();
	}

	@Override
	public boolean streamInput() {
		return false;
	}

	@Override
	public Type sourceType() {
		return null;
	}
	
	@Override
	public ReactiveParameters parameters() {
		return params;
	}


}
