package com.dexels.navajo.reactive.source.test;

import java.io.InputStream;
import java.util.Optional;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.immutable.factory.ImmutableFactory;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.DataItem.Type;
import com.dexels.navajo.document.stream.StreamDocument;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.document.stream.xml.XML;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveResolvedParameters;
import com.dexels.navajo.reactive.api.ReactiveSource;
import com.dexels.navajo.reactive.api.SourceMetadata;

import io.reactivex.Flowable;

public class EventStreamSource implements ReactiveSource {

	private final SourceMetadata metadata;
	private final ReactiveParameters parameters;


	public EventStreamSource(SourceMetadata metadata,ReactiveParameters parameters) {
		this.metadata = metadata;
		this.parameters = parameters;
	}

	@Override
	public Flowable<DataItem> execute(StreamScriptContext context, Optional<ImmutableMessage> current,
			ImmutableMessage paramMessage) {

//	public Flowable<DataItem> execute(StreamScriptContext context, Optional<ImmutableMessage> current) {
		ReactiveResolvedParameters parameters = this.parameters.resolve(context, current, ImmutableFactory.empty(), metadata);
		Optional<String> classpath = parameters.optionalString("classpath");
		if(classpath.isPresent()) { // TODO other sources
			InputStream is = getClass().getClassLoader().getResourceAsStream(classpath.get());
			Flowable<DataItem> flow = StreamDocument.dataFromInputStream(is)
					.lift(XML.parseFlowable(10))
					.concatMap(e->e)
					.lift(StreamDocument.parse())
//					.concatMap(e->e)
					.map(DataItem::ofEventStream);
			return flow;
					//			Bytes/ b;
		}
		return null;
	}

	@Override
	public boolean streamInput() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Type sourceType() {
		// TODO Auto-generated method stub
		return null;
	}

}
