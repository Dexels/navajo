/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.reactive.api;

import java.util.Optional;
import java.util.function.Function;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.DataItem.Type;
import com.dexels.navajo.document.stream.api.StreamScriptContext;

import io.reactivex.Flowable;

public interface ReactiveSource {
	public Flowable<DataItem> execute(StreamScriptContext context,Optional<ImmutableMessage> current, ImmutableMessage paramMessage);
//	public DataItem.Type finalType();
	public boolean streamInput();
	public final Function<String, ReactiveMerger> emptyReducerSupplier = (e->null);
	public Type sourceType();
	public ReactiveParameters parameters();
}
