package com.dexels.navajo.document.stream.example;

import static com.dexels.navajo.document.stream.io.NavajoReactiveOperators.inArray;

import com.dexels.navajo.adapters.stream.SQL;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.stream.api.SimpleScript;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;

import io.reactivex.Flowable;

public class DatabaseList implements SimpleScript {

	@Override
	public Flowable<NavajoStreamEvent> call(Navajo input) {
		return SQL.queryToMessage("KNVB","dummy", "select * from ORGANIZATION")
			.flatMap(m->m.streamFlowable())
			.compose(inArray("Organizations"));
	}

		
}
