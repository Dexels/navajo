/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.document.stream.api;

import java.util.Collections;
import java.util.function.Function;

import com.dexels.navajo.document.stream.events.Events;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;

import io.reactivex.Observable;

public class ArrayMessage {
	private final String name;
	private final Function<ArrayMessage,Observable<NavajoStreamEvent>> body;
	
	public ArrayMessage(String name, Function<ArrayMessage,Observable<NavajoStreamEvent>> body) {
		this.name = name;
		this.body = body;
	}

	public static ArrayMessage createArray(String name,Function<ArrayMessage,Observable<NavajoStreamEvent>> body) {
		return new ArrayMessage(name,body);
	}
	
	public Observable<NavajoStreamEvent> stream() {
		try {
			return body.apply(this).startWith(before()).concatWith(after());
		} catch (Exception e) {
			return Observable.error(e);
		}
	}
	
	private Observable<NavajoStreamEvent> before() {
		return Observable.<NavajoStreamEvent>just(Events.arrayStarted(name,Collections.emptyMap()));
	}
	
	private Observable<NavajoStreamEvent> after() {
		return Observable.<NavajoStreamEvent>just(Events.arrayDone(name));
	}
}
