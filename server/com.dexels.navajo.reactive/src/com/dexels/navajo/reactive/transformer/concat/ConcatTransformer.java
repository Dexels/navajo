package com.dexels.navajo.reactive.transformer.concat;

import java.util.Optional;

import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.reactive.api.ReactiveTransformer;
import com.dexels.replication.api.ReplicationMessage;

import io.reactivex.FlowableTransformer;

public class ConcatTransformer implements ReactiveTransformer {

	public ConcatTransformer() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public FlowableTransformer<DataItem, DataItem> execute(StreamScriptContext context,
			Optional<ReplicationMessage> current) {
		// TODO Auto-generated method stub
		return null;
	}

}
