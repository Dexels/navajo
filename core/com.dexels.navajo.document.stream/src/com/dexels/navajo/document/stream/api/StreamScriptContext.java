package com.dexels.navajo.document.stream.api;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.stream.StreamDocument;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;
import com.dexels.navajo.events.NavajoEventRegistry;
import com.dexels.navajo.events.types.NavajoResponseEvent;
import com.dexels.navajo.script.api.Access;
import com.fasterxml.jackson.databind.JsonNode;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;

public class StreamScriptContext {
    private final Access access;
	
	private final Optional<Flowable<NavajoStreamEvent>> inputFlowable;
	public final Map<String, Object> attributes;

	private final Optional<RunningReactiveScripts> runningScripts;
	private final Optional<ReactiveScriptRunner> runner;
	private String deployment;
	private final List<String> methods;
	private final String uuid;
	private final Optional<Runnable> onDispose;
    private boolean collectedInput = false;
    
	
	
	private final static Logger logger = LoggerFactory.getLogger(StreamScriptContext.class);

	public Map<String,String> createMDCMap(int linenr) {
		Map<String,String> result = new HashMap<>();
		result.put("tenant", access.getTenant());
		result.put("service", access.getRpcName());
		result.put("deployment", deployment);
		result.put("uuid", uuid);
		result.put("username", access.getRpcUser());
		
		result.put("started", ""+ access.getCreated());
		result.put("linenr", ""+linenr);

		return Collections.unmodifiableMap(result);
	}
	//	private final Optional<AsyncContext> asyncContext;
	
	// mostly for testing
	public StreamScriptContext(String tenant, String service, String deployment) {
		this(UUID.randomUUID().toString(),System.currentTimeMillis(),tenant,service,Optional.empty(),Optional.empty(),null,Collections.emptyMap(),Optional.empty(),Optional.empty(),Collections.emptyList(),Optional.empty(),Optional.empty());
		this.deployment = deployment;
	}

	public StreamScriptContext(String tenant, String service, Optional<String> username, Optional<String> password,Navajo authNavajo, Map<String,Object> attributes,Optional<Flowable<NavajoStreamEvent>> input, Optional<ReactiveScriptRunner> runner, List<String> addedMethods, Optional<Runnable> onDispose, Optional<RunningReactiveScripts> running) {
       this(UUID.randomUUID().toString(),System.currentTimeMillis(), tenant,service,username,password,authNavajo,attributes,input,runner,addedMethods,onDispose,running);	
	}

	public StreamScriptContext(String uuid, Access access, Map<String, Object> attributes, Optional<Flowable<NavajoStreamEvent>> input,
	            Optional<ReactiveScriptRunner> runner, List<String> addedMethods, Optional<Runnable> onDispose,
	            Optional<RunningReactiveScripts> running) {
	        this.uuid = uuid;
	        this.access = access;
	        this.attributes = attributes;
	        this.inputFlowable = input;
	        this.runner = runner;
	        this.deployment = runner.map(r->r.deployment()).orElse("");
	        this.methods = addedMethods;
	        this.onDispose = onDispose;
	        this.runningScripts = running;
//	      this.asyncContext = asyncContext;
	    }
	
   private StreamScriptContext(String uuid, long started, String tenant, String service, Optional<String> username,
            Optional<String> password, Navajo authNavajo, Map<String, Object> attributes, Optional<Flowable<NavajoStreamEvent>> input,
            Optional<ReactiveScriptRunner> runner, List<String> addedMethods, Optional<Runnable> onDispose,
            Optional<RunningReactiveScripts> running) {
        this.uuid = uuid;
        this.access = new Access(-1, -1, username.orElse("placeholder"), service, "stream", "ip", "hostname", null, false, null);
        access.rpcPwd = password.orElse("placeholder");
        access.setInDoc(authNavajo);
        access.setTenant(tenant);
        this.attributes = attributes;
        this.inputFlowable = input;
        this.runner = runner;
        this.deployment = runner.map(r->r.deployment()).orElse("");
        this.methods = addedMethods;
        this.onDispose = onDispose;
        this.runningScripts = running;
//      this.asyncContext = asyncContext;
    }
	
	
	
	public String uuid() {
		return uuid;
	}
	public StreamScriptContext withService(String service) {
		return new StreamScriptContext(this.uuid, access, attributes, inputFlowable,runner, methods,onDispose,this.runningScripts);
	}

	public StreamScriptContext copyWithNewUUID() {
		String uuid = UUID.randomUUID().toString();
		StreamScriptContext streamScriptContext = new StreamScriptContext(uuid, access, attributes, inputFlowable,runner, methods,Optional.empty(),this.runningScripts);
//		this.runningScripts.get().submit(streamScriptContext);
		return streamScriptContext;
	}

	public StreamScriptContext withMethods(List<String> methods) {
		return new StreamScriptContext(this.uuid, access, attributes, inputFlowable,runner, methods,onDispose,this.runningScripts);
	}


	public StreamScriptContext withInput(Flowable<NavajoStreamEvent> input) {
		return new StreamScriptContext(this.uuid, access, attributes, Optional.of(input),runner, methods, onDispose,this.runningScripts);
	}

	public StreamScriptContext withInputNavajo(Navajo input) {
	    Access newAccess = access.cloneWithoutNavajos();
	    newAccess.setInDoc(input);
		return new StreamScriptContext(this.uuid, newAccess, attributes, Optional.empty(),runner, methods, onDispose,this.runningScripts);
	}

	public StreamScriptContext withDispose(Runnable disposer) {
		return new StreamScriptContext(this.uuid, access, attributes, inputFlowable,runner, methods, Optional.of(disposer),this.runningScripts);
	}

	public ReactiveScriptRunner runner() {
		return runner.orElseThrow(()->new RuntimeException("StreamScriptContext has no runner, so sub runners aren't allowed"));
	}

	public Optional<RunningReactiveScripts> runningScripts() {
		return this.runningScripts;
	}

	public String deployment() {
		return deployment;
	}
	
	public Flowable<NavajoStreamEvent> inputFlowable() {
		return inputFlowable.orElse(Flowable.empty());
	}
	
	public Single<Navajo> getInput() {

		return collect().toSingle(NavajoFactory.getInstance().createNavajo())
		        .map(e-> {
		            e.getAllMessages().forEach(message->{
	                    access.getInDoc().addMessage(message);
	                });
		            return access.getInDoc();
		        });
				
	}
	
	private Maybe<Navajo> collect() {
	    
		if(collectedInput ) {
			return Maybe.empty();
		}
		collectedInput = true;
		return inputFlowable.get()
				.toObservable()
				.compose(StreamDocument.domStreamCollector())
				.firstElement();
	}
	
	public void cancel() {
	    
		if(this.onDispose.isPresent()) {
			this.onDispose.get().run();
		}
		if(this.runningScripts.isPresent()) {
            this.runningScripts.get().cancel(uuid);
        }
	}
	
	public Navajo resolvedNavajo() {
		return access.getInDoc();
	}

	public void logEvent(String message) {
		logger.info("Stream Context event service: {} uuid: {} message: {}",access.getRpcName(),uuid,message);
	}

	public void complete() {
	    access.setFinished();
	    NavajoEventRegistry.getInstance().publishEvent(new NavajoResponseEvent(access));
		if(this.runningScripts.isPresent()) {
			this.runningScripts.get().completed(this);
		}
	}

	public String getUsername() {
        return access.getRpcUser();
    }
	
    public String getService() {
        return access.getRpcName();
    }
    
    public String getTenant() {
        return access.getTenant();
    }
    
    public long getStarted() {
        return access.getCreated().getTime();
    }
}
