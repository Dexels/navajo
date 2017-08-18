package com.dexels.navajo.document.stream.example;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.dexels.navajo.adapters.stream.SQL;
import com.dexels.navajo.document.stream.api.Script;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent.NavajoEventTypes;
import com.dexels.navajo.document.stream.io.NavajoReactiveOperators;

import io.reactivex.Flowable;

public class StreamingInput implements Script {

	Executor exe = Executors.newFixedThreadPool(10);

	@Override
	public Flowable<NavajoStreamEvent> call(Flowable<NavajoStreamEvent> input) {
		return input.filter(e->e.type()==NavajoEventTypes.ARRAY_ELEMENT)
			.map(e->e.message())
			.map(m->m.stringValue("ORGANIZATIONID"))
			.flatMap(clubId->SQL.queryToMessage("","dummy", "SELECT * FROM ORGANIZATION WHERE ORGANIZATIONID=?",clubId))
			.flatMap(m->m.streamFlowable())
			.compose(NavajoReactiveOperators.inArray("Organizations"));
	}

}
