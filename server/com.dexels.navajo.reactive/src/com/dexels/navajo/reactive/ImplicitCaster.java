/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.reactive;

import java.util.Optional;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.DataItem.Type;
import com.dexels.navajo.document.stream.StreamDocument;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;
import com.dexels.navajo.reactive.api.ReactiveParseException;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;

public class ImplicitCaster {
	private static final String DEFAULT_PATH="Item";
	
	public static Optional<FlowableTransformer<DataItem, DataItem>> createTransformer(Type from, Type to) {
		return Optional.of(implicitCastTransformer(from, to));
	}
	
	public static  FlowableTransformer<DataItem,DataItem> implicitCastTransformer(Type parentType, Type destinationType) {
		return flow->implicitCast(flow, parentType, destinationType);
	}

	public static  Flowable<DataItem> implicitCast(Flowable<DataItem> flow, Type parentType, Type destinationType) {
		if(parentType.equals(destinationType)) {
			return flow;
		}
		switch(destinationType) {
		case MESSAGE:
			return implicitCastToMessage(flow, parentType)
					.map(d->DataItem.of(d));
		case MSGSTREAM:
			return implicitCastToMessage(flow, parentType)
					.map(d->DataItem.of(Flowable.just(d)));
		case SINGLEMESSAGE:
			return implicitCastToMessage(flow, parentType)
					.map(d->DataItem.of(d));
		case EVENT:
			return implicitCastToEvent(flow, parentType)
				.map(d->DataItem.of(d));
		case EVENTSTREAM:
			return implicitCastToEvent(flow, parentType)
					.map(d->DataItem.ofEventStream(Flowable.just(d)));
		default:
			throw new ReactiveParseException("Can not autocast from type: "+parentType+" to type: "+destinationType);
		}
	}
	
	public static  Flowable<NavajoStreamEvent> implicitCastToEvent(Flowable<DataItem> flow, Type type) {
		switch (type) {
		case EVENT:
			return flow.map(e->e.event());
		case EVENTSTREAM:
			return flow.concatMap(e->e.eventStream());
		case MESSAGE:
			return flow.map(e->e.message())
				.compose(StreamDocument.toMessageEvent(DEFAULT_PATH,true));
		case MSGLIST:
			return flow.flatMap(e->Flowable.fromIterable(e.messageList()))
					.compose(StreamDocument.toMessageEvent(DEFAULT_PATH,true));
		case MSGSTREAM:
			return flow.flatMap(e->e.messageStream())
					.compose(StreamDocument.toMessageEvent(DEFAULT_PATH,true));
		case SINGLEMESSAGE:
			return flow.map(e->e.message())
					.compose(StreamDocument.toMessageEvent(DEFAULT_PATH,false));
		case ANY:
		case DATA:
		case EMPTY:
		default:
			break;
		}
		throw new ReactiveParseException("Can not autocast from type: "+type+" to event");

	}
	
	
	public static  Flowable<ImmutableMessage> implicitCastToMessage(Flowable<DataItem> flow, Type type) {
		System.err.println("Impicit casting to: "+type);
		switch (type) {
		case EVENT:
			return flow.map(di->di.event())
					.compose(StreamDocument.eventsToImmutable(Optional.empty()));
		case EVENTSTREAM:
			return flow.map(di->di.eventStream())
					.concatMap(e->e)
					.compose(StreamDocument.eventsToImmutable(Optional.empty()));
		case MESSAGE:
			return flow.map(e->e.message());
		case MSGLIST:
			return flow.map(di->di.messageList())
					.map(l->Flowable.fromIterable(l))
					.concatMap(e->e);
		case MSGSTREAM:
			return flow.map(di->di.messageStream())
					.concatMap(e->e);
		case SINGLEMESSAGE:
			return flow.map(e->e.message());
		case ANY:
		case DATA:
		case EMPTY:
			break;
		}
		throw new ReactiveParseException("Can not autocast from type: "+type+" to message");

	}

}
