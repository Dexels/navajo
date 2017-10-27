package com.dexels.navajo.reactive.source;

import java.util.Optional;

import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.DataItem.Type;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.reactive.api.ReactiveSource;
import com.dexels.replication.api.ReplicationMessage;

import io.reactivex.Flowable;

public class SourceSource implements ReactiveSource {
	
	private Type finalType;

	public SourceSource(DataItem.Type finalType) {
		this.finalType = finalType;
	}

	@Override
	public Flowable<DataItem> execute(StreamScriptContext context, Optional<ReplicationMessage> current) {
		return context.inputFlowable().map(DataItem::of);
	}

	@Override
	public Type dataType() {
		return Type.EVENT;
	}

	@Override
	public Type finalType() {
		return finalType;
	}

}
