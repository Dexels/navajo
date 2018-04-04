package com.dexels.navajo.document.stream;

import java.util.List;
import java.util.Optional;

import com.dexels.navajo.document.stream.api.StreamScriptContext;

import io.reactivex.Flowable;

public interface ReactiveScript {
	public Flowable<DataItem> execute(StreamScriptContext context);
	public DataItem.Type dataType();
	public Optional<String> binaryMimeType();
//	public Optional<String> streamMessage();
	public List<ReactiveParseProblem> problems();
	
}
