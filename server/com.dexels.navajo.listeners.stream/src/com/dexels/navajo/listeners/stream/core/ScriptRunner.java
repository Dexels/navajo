package com.dexels.navajo.listeners.stream.core;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent.NavajoEventTypes;

import rx.Observable;

public abstract class ScriptRunner {
	
	private BaseScriptInstance instance;
	private Map<String,Object> authorization = null;
	
	private final static Logger logger = LoggerFactory.getLogger(ScriptRunner.class);
	
	public Observable<NavajoStreamEvent> call(final NavajoStreamEvent event) {
			if(event.type() == NavajoEventTypes.HEADER) {
				Header h = (Header)event.body();
				this.authorization = authorize(h);
				try {
					this.instance = resolveScript(h);
				} catch (Exception e) {
					return Observable.<NavajoStreamEvent>error(new Exception("script resolution error"));
				}

				if(authorization==null) {
					System.err.println("Authorization failed!");
					return Observable.<NavajoStreamEvent>error(new Exception("authorization error"));
				}
				// TODO: Should I pass the header to the resolved script?
				return Observable.<NavajoStreamEvent>empty();
			} else if(instance==null) {
				if(event.type()!=NavajoEventTypes.NAVAJO_STARTED) {
					logger.warn("Non-authorized script running");
				}
				return Observable.<NavajoStreamEvent>empty();
			}  
			return instance.run(event.withAttributes(this.authorization));
	}
	
	protected abstract BaseScriptInstance resolveScript(Header h) throws Exception;
	
	private Map<String,Object> authorize(Header header) {
		Map<String,Object> auth = new HashMap<>();
		auth.put("username", header.getRPCUser());
		auth.put("name", header.getRPCName());
		if(header.getHeaderAttributes()!=null) {
			auth.putAll(header.getHeaderAttributes());
		}
		return Collections.unmodifiableMap(auth);
		// or call this access?
	}
}
