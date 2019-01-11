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
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.StreamDocument;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;
import com.dexels.navajo.script.api.Access;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;

public class StreamScriptContext {
    private final Access access;
	
	private final Optional<Flowable<DataItem>> inputFlowable;
	private final Optional<Single<Navajo>> collectedInput;
	public final Map<String, Object> attributes;

	private final Optional<RunningReactiveScripts> runningScripts;
	private final Optional<ReactiveScriptRunner> runner;
	private Optional<String> deployment;
	private final List<String> methods;
	private final String uuid;
	private final Optional<Runnable> onDispose;
    
	
	
	private final static Logger logger = LoggerFactory.getLogger(StreamScriptContext.class);

	public Map<String,String> createMDCMap(int linenr) {
		Map<String,String> result = new HashMap<>();
		result.put("tenant", access.getTenant());
		result.put("service", access.getRpcName());
		if(deployment.isPresent()) {
			result.put("deployment", deployment.get());
		}
		result.put("uuid", uuid);
		result.put("username", access.getRpcUser());
		result.put("started", ""+ access.getCreated());
		result.put("linenr", ""+linenr);

		return Collections.unmodifiableMap(result);
	}
	//	private final Optional<AsyncContext> asyncContext;
	
	// mostly for testing
	public StreamScriptContext(String tenant, String service, String deployment) {
		this(UUID.randomUUID().toString(),System.currentTimeMillis(),tenant,service,Optional.empty(),Optional.empty(),null,Collections.emptyMap(),Optional.empty(),Optional.empty(),Optional.empty(),Collections.emptyList(),Optional.empty(),Optional.empty());
		this.deployment = Optional.ofNullable(deployment);
	}

	public StreamScriptContext(String tenant, String service, Optional<String> username, Optional<String> password,Navajo authNavajo, Map<String,Object> attributes,Optional<Flowable<DataItem>> input, Optional<ReactiveScriptRunner> runner, List<String> addedMethods, Optional<Runnable> onDispose, Optional<RunningReactiveScripts> running) {
       this(UUID.randomUUID().toString(),System.currentTimeMillis(), tenant,service,username,password,authNavajo,attributes,input,Optional.empty(), runner,addedMethods,onDispose,running);	
	}

	public StreamScriptContext(String uuid, Access access, Map<String, Object> attributes,
			Optional<Flowable<DataItem>> input,
			Optional<Single<Navajo>> collectedInput,
	            Optional<ReactiveScriptRunner> runner, List<String> addedMethods, Optional<Runnable> onDispose,
	            Optional<RunningReactiveScripts> running) {
	        this.uuid = uuid;
	        this.access = access;
	        this.attributes = attributes;
	        this.inputFlowable = input;
	        this.runner = runner;
	        this.deployment = runner.flatMap(r->r.deployment());
	        this.methods = addedMethods;
	        this.onDispose = onDispose;
	        this.runningScripts = running;
	        this.collectedInput = collectedInput;
//	      this.asyncContext = asyncContext;
	    }
	
   private StreamScriptContext(String uuid, long started, String tenant, String service, Optional<String> username,
            Optional<String> password, Navajo authNavajo, Map<String, Object> attributes, Optional<Flowable<DataItem>> input,
            Optional<Single<Navajo>> collectedInput,
            Optional<ReactiveScriptRunner> runner, List<String> addedMethods, Optional<Runnable> onDispose,
            Optional<RunningReactiveScripts> running) {
        this.uuid = uuid;
        this.access = new Access(-1, -1, username.orElse("placeholder"), service, "stream", "ip", "hostname", null, false, null);
        access.rpcPwd = password.orElse("placeholder");
        access.setInDoc(authNavajo);
        access.setTenant(tenant);
        this.attributes = attributes;
        this.inputFlowable = input;
        this.collectedInput = collectedInput;
        this.runner = runner;
        this.deployment = runner.flatMap(r->r.deployment());
        this.methods = addedMethods;
        this.onDispose = onDispose;
        this.runningScripts = running;
//      this.asyncContext = asyncContext;
    }
	
	
	
	public String uuid() {
		return uuid;
	}
	public StreamScriptContext withService(String service) {
		Access a = this.access;
		Access acc = new Access(a.userID, a.serviceID, a.getRpcUser(), service, a.userAgent, a.ipAddress, a.hostName, a.getUserCertificate(), a.betaUser, a.accessID);
		acc.setInDoc(a.getInDoc());
		acc.setTenant(a.getTenant());
//		acc.setI
		return new StreamScriptContext(this.uuid, acc, attributes, inputFlowable,this.collectedInput, runner, methods,onDispose,this.runningScripts);
	}

	public StreamScriptContext copyWithNewUUID() {
		String uuid = UUID.randomUUID().toString();
		StreamScriptContext streamScriptContext = new StreamScriptContext(uuid, access, attributes, inputFlowable,this.collectedInput,runner, methods,Optional.empty(),this.runningScripts);
//		this.runningScripts.get().submit(streamScriptContext);
		return streamScriptContext;
	}

	public StreamScriptContext withMethods(List<String> methods) {
		return new StreamScriptContext(this.uuid, access, attributes, inputFlowable,this.collectedInput,runner, methods,onDispose,this.runningScripts);
	}


	public StreamScriptContext withInput(Flowable<DataItem> input) {
		return new StreamScriptContext(this.uuid, access, attributes, Optional.of(input),this.collectedInput,runner, methods, onDispose,this.runningScripts);
	}
	public StreamScriptContext withRunner(ReactiveScriptRunner runner) {
		return new StreamScriptContext(this.uuid, access, attributes, this.inputFlowable,this.collectedInput,Optional.of(runner), methods, onDispose,this.runningScripts);
	}

	public StreamScriptContext withoutInputNavajo() {
	    Access newAccess = access.cloneWithoutNavajos();
		return new StreamScriptContext(this.uuid, newAccess, attributes, Optional.empty(),this.collectedInput,runner, methods, onDispose,this.runningScripts);
	}
	public StreamScriptContext withInputNavajo(Navajo input) {
		if(input==null) {
			throw new NullPointerException("Can't pass null navajo into withInputNavajo");
		}
	    Access newAccess = access.cloneWithoutNavajos();
	    newAccess.setInDoc(input);
		return new StreamScriptContext(this.uuid, newAccess, attributes, Optional.empty(),this.collectedInput,runner, methods, onDispose,this.runningScripts);
	}

	public StreamScriptContext withTenant(String tenant) {
	    Access newAccess = access.cloneWithoutNavajos();
	    newAccess.setInDoc(access.getInDoc());
	    newAccess.setTenant(tenant);
		return new StreamScriptContext(this.uuid, newAccess, attributes, Optional.empty(),this.collectedInput,runner, methods, onDispose,this.runningScripts);
	}
	
	public StreamScriptContext withDispose(Runnable disposer) {
		return new StreamScriptContext(this.uuid, access, attributes, inputFlowable,this.collectedInput,runner, methods, Optional.of(disposer),this.runningScripts);
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
	
//	public Single<Navajo> getInput() {
//
//		return collect().toSingle(NavajoFactory.getInstance().createNavajo())
//		        .map(e-> {
//		            e.getAllMessages().forEach(message->{
//	                    access.getInDoc().addMessage(message);
//	                });
//		            return access.getInDoc();
//		        });
//				
//	}
	
	public Maybe<Navajo> collect() {
		System.err.println("Service = "+this.getService());
		if(inputFlowable.isPresent()) {
			return inputFlowable.get()
					.toObservable()
					.map(e->e.event())
					.compose(StreamDocument.domStreamCollector())
					.firstElement();
		}
	    if(this.access!=null && this.access.getInDoc()!=null) {
	    	return Maybe.just(this.access.getInDoc());
	    }
//		if(collectedInput ) {
//			return Maybe.empty();
//		}
		return Maybe.empty();
//		collectedInput = true;
//		if(!inputFlowable.isPresent()) {
//			return Maybe.just(NavajoFactory.getInstance().createNavajo());
//		}
//		return inputFlowable.get()
//				.toObservable()
//				.map(e->e.event())
//				.compose(StreamDocument.domStreamCollector())
//				.firstElement();
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
	    access.setExitCode(Access.EXIT_OK);
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

    public void error(Throwable e) {
        access.setException(e);
        access.setExitCode(Access.EXIT_EXCEPTION);

        if(this.runningScripts.isPresent()) {
            this.runningScripts.get().completed(this);
        }
    }

    public String getAccessId() {
        return access.getAccessID();
    }

}
