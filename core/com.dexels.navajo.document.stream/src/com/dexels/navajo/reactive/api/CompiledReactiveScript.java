package com.dexels.navajo.reactive.api;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.dexels.immutable.factory.ImmutableFactory;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.DataItem.Type;
import com.dexels.navajo.document.stream.ReactiveParseProblem;
import com.dexels.navajo.document.stream.ReactiveScript;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.reactive.api.ReactivePipe;

import io.reactivex.Flowable;

public class CompiledReactiveScript implements ReactiveScript{
	public final List<ReactivePipe> pipes;
	public final List<String> methods;
	private final Type type;
	private final boolean streamInput;
	private Optional<String> binaryMime;

	public CompiledReactiveScript(List<ReactivePipe> pipes, List<String> methods, Optional<String> binaryMime) {
		this.pipes = pipes;
		this.methods = methods;
		Set<Type> dataTypes = pipes.stream().map(p->p.finalType()).collect(Collectors.toSet());
		if(dataTypes.size()>1) {
			throw new RuntimeException("Can't combine different final types to different pipes in one script. TODO maybe think of some type inference");
		}
		this.type = dataTypes.stream().findFirst().orElseThrow(()->new RuntimeException("Huh?"));
		this.streamInput = pipes.stream().anyMatch(e->e.streamInput());
		this.binaryMime = binaryMime;
	}

	@Override
	public Flowable<Flowable<DataItem>> execute(StreamScriptContext context) {
		return Flowable.fromIterable(pipes).map(e->e.execute(context, Optional.empty(), ImmutableFactory.empty()));
	}

	@Override
	public Type dataType() {
		return type;
	}

	@Override
	public Optional<String> binaryMimeType() {
		return binaryMime;
	}

	@Override
	public boolean streamInput() {
		return streamInput;
	}

	@Override
	public List<ReactiveParseProblem> problems() {
		return Collections.emptyList();
	}

	@Override
	public List<String> methods() {
		return methods;
	}
}
