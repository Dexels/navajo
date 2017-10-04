package com.dexels.navajo.reactive;

import java.util.Optional;

import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.replication.api.ReplicationMessage;

import io.reactivex.Flowable;

public interface ReactiveSource {
	public Flowable<ReplicationMessage> execute(StreamScriptContext context,Optional<ReplicationMessage> current);
}
