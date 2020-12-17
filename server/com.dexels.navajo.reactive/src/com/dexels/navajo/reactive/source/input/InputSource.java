/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.reactive.source.input;

import java.util.Optional;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.StreamDocument;
import com.dexels.navajo.document.stream.DataItem.Type;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveResolvedParameters;
import com.dexels.navajo.reactive.api.ReactiveSource;
import com.dexels.navajo.reactive.api.SourceMetadata;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;

public class InputSource implements ReactiveSource {

	private final ReactiveParameters params;
//	private final Optional<XMLElement> sourceElement;
//	private final String sourcePath;
	private final SourceMetadata metadata;

	public InputSource(SourceMetadata metadata, ReactiveParameters params) {
		this.metadata = metadata;
		this.params = params;
//		this.sourceElement = sourceElement;
//		this.sourcePath = sourcePath;
	}

	@SuppressWarnings("unused")
	@Override
	public Flowable<DataItem> execute(StreamScriptContext context, Optional<ImmutableMessage> current, ImmutableMessage param) {
		ReactiveResolvedParameters resolved = params.resolve(context, current, param, metadata);
//		Optional<String>s path = resolved.optionalString("path");

		Observable<NavajoStreamEvent> str = Single.just(context.resolvedNavajo()).compose(StreamDocument.domStreamTransformer()).flatMapObservable(r->r);

		return context.input()
				.doOnNext(
						e->{
							System.err.println("ITEM: "+e);
						}

						); // .orElseGet(()->context.inputFlowable().orElse(context.getInput().toFlowable().flatMap(e->NavajoDomStreamer.feedFlowable(e))))
//				.compose(StreamDocument.eventsToImmutable(path))
//				.map(DataItem::of);
	}


	@Override
	public boolean streamInput() {
		return true;
	}

	@Override
	public Type sourceType() {
		return Type.MESSAGE;
	}

	@Override
	public ReactiveParameters parameters() {
		return params;
	}


}
