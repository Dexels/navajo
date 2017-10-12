package com.dexels.navajo.reactive.api;

import java.util.Optional;

import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.replication.api.ReplicationMessage;

import io.reactivex.FlowableTransformer;

public interface ReactiveTransformer {
	public FlowableTransformer<DataItem,DataItem> execute(StreamScriptContext context,Optional<ReplicationMessage> current);
}
