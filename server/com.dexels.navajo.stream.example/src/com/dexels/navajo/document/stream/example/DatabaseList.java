package com.dexels.navajo.document.stream.example;

import static com.dexels.navajo.document.stream.io.NavajoStreamOperators.inArray;

import com.dexels.navajo.adapters.stream.SQL;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.stream.api.SimpleScript;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;

import rx.Observable;

public class DatabaseList implements SimpleScript {

	@Override
	public Observable<NavajoStreamEvent> call(Navajo input) {
		return SQL.queryToMessage("KNVB","default", "select * from ORGANIZATION")
			.flatMap(m->m.stream())
			.compose(inArray("Organizations"));
	}

		
}
