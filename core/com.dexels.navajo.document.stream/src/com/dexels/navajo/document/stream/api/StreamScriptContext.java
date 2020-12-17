/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.document.stream.api;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.StreamDocument;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;

public class StreamScriptContext {
//    private final Access access;
	
	private final Optional<Flowable<DataItem>> inputFlowable;
	private final Navajo collectedInput;
	private final Optional<Navajo> authNavajo;
	public final Map<String, Object> attributes;

	private final Optional<RunningReactiveScripts> runningScripts;
	private final Optional<ReactiveScriptRunner> runner;
	private Optional<String> deployment;
	private final List<String> methods;
	private final String uuid;
	private final Optional<Runnable> onDispose;
    private final String tenant;
    private final String service;
    private final long started;
	private Optional<String> username;
	
	
	private final static Logger logger = LoggerFactory.getLogger(StreamScriptContext.class);

	public Map<String,String> createMDCMap(int linenr) {
		Map<String,String> result = new HashMap<>();
		result.put("tenant", tenant);
		result.put("service", service);
		if(deployment.isPresent()) {
			result.put("deployment", deployment.get());
		}
		result.put("uuid", uuid);
		result.put("started", ""+ started);
		result.put("linenr", ""+linenr);

		return Collections.unmodifiableMap(result);
	}
	//	private final Optional<AsyncContext> asyncContext;
	
	// mostly for testing
	public StreamScriptContext(String tenant, String service, String deployment) {
		this(UUID.randomUUID().toString(),System.currentTimeMillis(),tenant,service,Optional.empty(),NavajoFactory.getInstance().createNavajo(),Collections.emptyMap(),Optional.empty(),Optional.empty(),Collections.emptyList(),Optional.empty(),Optional.empty(),Optional.empty());
		this.deployment = Optional.ofNullable(deployment);
	}

	public StreamScriptContext(String tenant, String service, Optional<String> username, Optional<String> password,Navajo inputNavajo, Map<String,Object> attributes,Optional<Flowable<DataItem>> input, Optional<ReactiveScriptRunner> runner, List<String> addedMethods, Optional<Runnable> onDispose, Optional<RunningReactiveScripts> running) {
       this(UUID.randomUUID().toString(),System.currentTimeMillis(), tenant,service,username,inputNavajo,attributes,input, runner,addedMethods,onDispose,running, Optional.empty());	
	}

	public StreamScriptContext(String tenant, String service, Navajo inputNavajo, Map<String, Object> attributes, Optional<Flowable<DataItem>> input,
            Optional<ReactiveScriptRunner> runner, List<String> addedMethods, Optional<Runnable> onDispose,
            Optional<RunningReactiveScripts> running, Optional<Navajo> authNavajo) {
		this(UUID.randomUUID().toString(),System.currentTimeMillis(),tenant,service,Optional.empty(),inputNavajo,attributes,input,runner,addedMethods,onDispose,running,authNavajo);
	}
	private StreamScriptContext(String uuid, long started, String tenant, String service, Optional<String> username,
            Navajo inputNavajo, Map<String, Object> attributes, Optional<Flowable<DataItem>> input,
            Optional<ReactiveScriptRunner> runner, List<String> addedMethods, Optional<Runnable> onDispose,
            Optional<RunningReactiveScripts> running, Optional<Navajo> authNavajo) {
        this.uuid = uuid;
        this.started = started;
        this.tenant = tenant;
        this.service = service;
        this.username = username;
//        this.access = new Access(-1, -1, username.orElse("placeholder"), service, "stream", "ip", "hostname", null, false, null);
        this.attributes = attributes;
        this.inputFlowable = input;
        this.collectedInput = mergeNavajo(inputNavajo,authNavajo);
        this.runner = runner;
        this.deployment = runner.flatMap(r->r.deployment());
        this.methods = addedMethods;
        this.onDispose = onDispose;
        this.runningScripts = running;
        this.authNavajo = authNavajo;
//      this.asyncContext = asyncContext;
    }
	
//	in = context.inputFlowable().get().map(e->e.event()).toObservable().compose(StreamDocument.domStreamCollector()).blockingFirst();

	
	private Navajo mergeNavajo(Navajo inputNavajo, Optional<Navajo> merging) {
		Navajo in = inputNavajo.copy();
		if(merging.isPresent()) {
			List<Message> msgs = merging.get().getAllMessages();
			msgs.forEach(e->{
				// TODO is this copy necessary?
				in.addMessage(e.copy());
			});
			
		}
		return in;
	}
	
	public Maybe<Navajo> blockingNavajo() {
		if(!this.inputFlowable.isPresent()) {
			return Maybe.just(this.collectedInput);
		}
		Maybe<Navajo> collect = inputFlowable().get().map(e->e.event()).toObservable().compose(StreamDocument.domStreamCollector()).firstElement();
		return collect.map(e->mergeNavajo(collectedInput, Optional.of(e))) ;
	}
	public String uuid() {
		return uuid;
	}
	public StreamScriptContext withService(String service) {
		return new StreamScriptContext(this.uuid, this.started, this.tenant, service, Optional.empty(),this.collectedInput, this.attributes, this.inputFlowable, this.runner, this.methods, this.onDispose, this.runningScripts, this.authNavajo);
	}

	public StreamScriptContext copyWithNewUUID() {
		return new StreamScriptContext(UUID.randomUUID().toString(), this.started, this.tenant, this.service, Optional.empty(), this.collectedInput, this.attributes, this.inputFlowable, this.runner, this.methods, this.onDispose, this.runningScripts, this.authNavajo);
	}

	public StreamScriptContext withMethods(List<String> methods) {
		return new StreamScriptContext(this.uuid, this.started, this.tenant, service, Optional.empty(),this.collectedInput, this.attributes, this.inputFlowable, this.runner, methods, this.onDispose, this.runningScripts, this.authNavajo);
	}


	public StreamScriptContext withInput(Flowable<DataItem> input) {
		return new StreamScriptContext(this.uuid, this.started, this.tenant, service, Optional.empty(), this.collectedInput, this.attributes, Optional.of(input), this.runner, this.methods, this.onDispose, this.runningScripts, this.authNavajo);
	}
	public StreamScriptContext withRunner(ReactiveScriptRunner runner) {
		return new StreamScriptContext(this.uuid, this.started, this.tenant, service, Optional.empty(), this.collectedInput, this.attributes, this.inputFlowable, Optional.of(runner), this.methods, this.onDispose, this.runningScripts, this.authNavajo);
	}

	public StreamScriptContext withTenant(String tenant) {
		return new StreamScriptContext(this.uuid, this.started, tenant, service, Optional.empty(), this.collectedInput, this.attributes, this.inputFlowable, this.runner, this.methods, this.onDispose, this.runningScripts, this.authNavajo);
	}
	
	public StreamScriptContext withDispose(Runnable disposer) {
		return new StreamScriptContext(this.uuid, this.started, this.tenant, service, Optional.empty(),this.collectedInput, this.attributes, this.inputFlowable, this.runner, this.methods, Optional.of(disposer), this.runningScripts, this.authNavajo);
	}

	public ReactiveScriptRunner runner() {
		return runner.orElseThrow(()->new RuntimeException("StreamScriptContext has no runner, so sub runners aren't allowed"));
	}

	public Optional<RunningReactiveScripts> runningScripts() {
		return this.runningScripts;
	}

	public Optional<String> deployment() {
		return deployment;
	}
	
	public Optional<Flowable<DataItem>> inputFlowable() {
		return inputFlowable;
	}
	
	public Flowable<DataItem> input() {
		return inputFlowable.orElseGet(()->
		{
			Flowable<NavajoStreamEvent> flowable = Single.just(resolvedNavajo())
				.compose(StreamDocument
				.domStreamTransformer())
				.flatMapObservable(r->r)
//				.map(e->DataItem.of((NavajoStreamEvent)e))
				.toFlowable(BackpressureStrategy.BUFFER);
			
			return Flowable.just(DataItem.ofEventStream(flowable));
		}
			);
	}
	
//	private static Flowable<Flowable>
	public void cancel() {
	    
		if(this.onDispose.isPresent()) {
			this.onDispose.get().run();
		}
		if(this.runningScripts.isPresent()) {
            this.runningScripts.get().cancel(uuid);
        }
	}

	public void logEvent(String message) {
		logger.info("Stream Context event service: {} uuid: {} message: {}",service,uuid,message);
	}

    public String getService() {
        return service;
    }
    
    public String getTenant() {
        return tenant;
    }
    
    public long getStarted() {
        return this.started;
    }

	public Navajo resolvedNavajo() {
		return this.collectedInput;
	}

	public StreamScriptContext withInputNavajo(Navajo createNavajo) {
		return new StreamScriptContext(this.uuid, this.started, this.tenant, service, Optional.empty(), createNavajo, this.attributes, this.inputFlowable, this.runner, this.methods, this.onDispose, this.runningScripts, this.authNavajo);
	}

	public String getUsername() {
		return this.username.orElse("");
	}

	public Object complete() {
		// TODO Auto-generated method stub
		return null;
	}


}
