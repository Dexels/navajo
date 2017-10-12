package com.dexels.navajo.reactive.transformer.tml;

import java.util.Optional;

import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.StreamDocument;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.replication.api.ReplicationMessage;

import io.reactivex.FlowableTransformer;

public class TMLSerializer implements ReactiveTransformer {

	@Override
	public FlowableTransformer<DataItem, DataItem> execute(StreamScriptContext context,
			Optional<ReplicationMessage> current) {
		
//		StreamDocument.replicationMessageToStreamEvents(name, msg, isArrayElement)
		return null;
	}

}
