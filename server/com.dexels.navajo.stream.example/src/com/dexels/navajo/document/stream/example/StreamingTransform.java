package com.dexels.navajo.document.stream.example;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.dexels.navajo.document.stream.api.Msg;
import com.dexels.navajo.document.stream.api.Prop;
import com.dexels.navajo.document.stream.api.Script;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent.NavajoEventTypes;
import com.dexels.navajo.document.stream.io.NavajoStreamOperators;

import rx.Observable;

public class StreamingTransform implements Script {

	Executor exe = Executors.newFixedThreadPool(10);

	@Override
	public Observable<NavajoStreamEvent> call(Observable<NavajoStreamEvent> input) {
		return input
			.filter(e->e.type()==NavajoEventTypes.ARRAY_ELEMENT)
			.map(e->Msg.createElement((List)e.body()))
			.map(m->m.value("NAME"))
			.filter(element->element!=null)
			.cast(String.class)
			.map(name->Msg.createElement()
					.with(Prop.create("Name").withValue(name.toUpperCase()))
					.with(Prop.create(name).withBinaryFromFile("/Users/frank/Downloads/sharknado1.jpg")))
			.flatMap(m->m.stream())
			.compose(NavajoStreamOperators.inArray("Organizations"));
	}
}
