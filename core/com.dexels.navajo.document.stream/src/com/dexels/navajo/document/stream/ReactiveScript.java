/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.document.stream;

import java.util.List;
import java.util.Optional;

import com.dexels.navajo.document.stream.api.StreamScriptContext;

import io.reactivex.Flowable;

public interface ReactiveScript {
	public Flowable<Flowable<DataItem>> execute(StreamScriptContext context);
	public DataItem.Type dataType();
	public Optional<String> binaryMimeType();
	public boolean streamInput();
//	public Optional<String> streamMessage();
	public List<ReactiveParseProblem> problems();
	public List<String> methods();
	
}
