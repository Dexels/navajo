/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
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

import io.reactivex.Flowable;

public class CompiledReactiveScript implements ReactiveScript{
	public final List<ReactivePipe> pipes;
	public final List<String> methods;
	private final Type type;
	private final boolean streamInput;

	public CompiledReactiveScript(List<ReactivePipe> pipes, List<String> methods) {
		this.pipes = pipes;
		this.methods = methods;
		Set<Type> dataTypes = pipes.stream().map(p->p.finalType()).collect(Collectors.toSet());
		if(dataTypes.size()>1) {
			throw new RuntimeException("Can't combine different final types to different pipes in one script. TODO maybe think of some type inference");
		}
		if(pipes.size()==0) {
			throw new RuntimeException("No actual pipes found in a compiled reactive script");
		}
		this.type = dataTypes.stream().findFirst().orElseThrow(()->new RuntimeException("Huh?"));
		this.streamInput = pipes.stream().anyMatch(e->e.streamInput());
	}

	public void typecheck() {
		for (ReactivePipe pipe : pipes) {
			pipe.typecheck();
		}
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
		for (ReactivePipe p : pipes) {
			if(p.finalType()==Type.DATA) {
				if(p.mimeType().isPresent()) {
					return p.mimeType();
				}
			}
		}
		return Optional.empty();
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
