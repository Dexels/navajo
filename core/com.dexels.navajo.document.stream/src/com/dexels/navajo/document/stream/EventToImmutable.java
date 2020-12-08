/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.document.stream;

import java.util.Optional;

import org.reactivestreams.Publisher;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;

public class EventToImmutable implements FlowableTransformer<NavajoStreamEvent,ImmutableMessage> {

	private final Optional<String> path;

	public EventToImmutable(Optional<String> path) {
		this.path = path;
	}

	
	@Override
	public Publisher<ImmutableMessage> apply(Flowable<NavajoStreamEvent> flow) {
			return flow
					.lift(NavajoStreamToMutableMessageStream.toMutable(this.path))
					.concatMap(e->e)
					.map(StreamDocument::messageToReplication);
	}


}
