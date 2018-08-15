package com.dexels.navajo.document.stream;

import org.reactivestreams.Publisher;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;

public class EventToImmutable implements FlowableTransformer<NavajoStreamEvent,ImmutableMessage> {

	private final String path;

	public EventToImmutable(String path) {
		this.path = path;
	}

	
	@Override
	public Publisher<ImmutableMessage> apply(Flowable<NavajoStreamEvent> flow) {
			return flow
					.doOnNext(e->System.err.println("Input: "+e))
					.lift(NavajoStreamToMutableMessageStream.toMutable(this.path))
					.concatMap(e->e)
					.map(StreamDocument::messageToReplication);
	}


}
