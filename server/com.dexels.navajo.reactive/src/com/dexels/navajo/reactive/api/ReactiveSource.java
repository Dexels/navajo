package com.dexels.navajo.reactive.api;

import java.util.Optional;

import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;
import com.dexels.replication.api.ReplicationMessage;

import io.reactivex.Flowable;

public interface ReactiveSource {
	public String getOutputName();
	public boolean isOutputArray();
	public Flowable<ReplicationMessage> execute(StreamScriptContext context,Optional<ReplicationMessage> current);
}
