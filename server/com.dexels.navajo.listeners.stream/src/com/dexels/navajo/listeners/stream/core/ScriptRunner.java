package com.dexels.navajo.listeners.stream.core;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.stream.events.EventFactory;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent.NavajoEventTypes;

import rx.Observable;

public abstract class ScriptRunner {
	
	private BaseScriptInstance instance;
	private Map<String,Object> authorization = null;
	
	private final static Logger logger = LoggerFactory.getLogger(ScriptRunner.class);
	
	public Observable<NavajoStreamEvent> to(final NavajoStreamEvent event, String name, String username, String password) {

		if(event.type() == NavajoEventTypes.NAVAJO_STARTED) {
			Header h = NavajoFactory.getInstance().createHeader(null, name, username, password, -1);
			return Observable.<NavajoStreamEvent>just(event,EventFactory.header(h));
		}
//		if(event.type() == NavajoEventTypes.HEADER) {
//			// ignore 'old' headers;
//			return Observable.empty();
//		}
		// .removeAttribute("authorized") ?
		return Observable.<NavajoStreamEvent>just(event);
	}
	
	public Observable<NavajoStreamEvent> call(final NavajoStreamEvent event) {
		switch(event.type()) {
			case NAVAJO_STARTED:
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
				return Observable.<NavajoStreamEvent>just(event.withAttributes(authorization));
			default:
				if(instance==null) {
					logger.warn("Non-resolved script running: "+event);
					return Observable.<NavajoStreamEvent>error(new Exception("Not authorized"));
				} else {
					return instance.run(event.withAttributes(this.authorization));
				}
		}
	}
	
	protected abstract BaseScriptInstance resolveScript(Header h) throws Exception;
	
	
	public Observable<Navajo> runInitScript(String name, String username, String password) {
		Navajo init = NavajoFactory.getInstance().createNavajo();
		Header h = NavajoFactory.getInstance().createHeader(init, name, username, password, -1);
		init.addHeader(h);
		return Observable.<Navajo>just(init);
	}

	private Map<String,Object> authorize(Header header) {
		Map<String,Object> auth = new HashMap<>();
		auth.put("username", header.getRPCUser());
		auth.put("name", header.getRPCName());
		auth.put("authorized", true);
		if(header.getHeaderAttributes()!=null) {
			auth.putAll(header.getHeaderAttributes());
		}
		return Collections.unmodifiableMap(auth);
		// or call this access?
	}
}
