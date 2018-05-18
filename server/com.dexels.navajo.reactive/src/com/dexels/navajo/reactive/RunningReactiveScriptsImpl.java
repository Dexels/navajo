package com.dexels.navajo.reactive;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.stream.api.RunningReactiveScripts;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class RunningReactiveScriptsImpl implements RunningReactiveScripts {
	
	
	private final static Logger logger = LoggerFactory.getLogger(RunningReactiveScriptsImpl.class);
	private final static ObjectMapper objectMapper = new ObjectMapper();
	private Map<String,StreamScriptContext> scriptsInProgress = new ConcurrentHashMap<>();

	@Override
	public void submit(StreamScriptContext context) {
		scriptsInProgress.put(context.uuid(),context);		
	}

	@Override
	public List<String> services() {
		return scriptsInProgress.values().stream().map(e->e.service).collect(Collectors.toList());
	}
	
	@Override
	public void completed(StreamScriptContext context) {
		long started = context.started;
		long elapsed = System.currentTimeMillis() - started;
		logger.info("Script: {} ran for: {} millis",context.service,elapsed);
		scriptsInProgress.remove(context.uuid());
	}

	@Override
	public void cancel(String uuid) {
		StreamScriptContext ctx = scriptsInProgress.get(uuid);
		if(ctx!=null) {
			ctx.cancel();
		}
		completed(ctx);
	}
	
	@Override
	public Collection<StreamScriptContext> contexts() {
		return scriptsInProgress.values();
	}
	
	@Override
	public JsonNode asJson() {
		ArrayNode list = objectMapper.createArrayNode();
		long now = System.currentTimeMillis();
		this.scriptsInProgress.entrySet().forEach(e->{
			ObjectNode current = objectMapper.createObjectNode();
			current.put("id", e.getKey());
			StreamScriptContext ctx = e.getValue();
			current.put("service", ctx.service);
			current.put("tenant", ctx.tenant);
			current.put("deployment", ctx.deployment());
			current.put("username", ctx.username.orElse("<unknown>"));
			current.put("started", ctx.started);
			current.put("running", (now-ctx.started));
			list.add(current);
		});
		return list;
	}
	
	public void complete(String uuid) {
		StreamScriptContext ssc = this.scriptsInProgress.get(uuid);
		if(ssc!=null) {
			ssc.complete();
			this.scriptsInProgress.remove(uuid);
		}
	}

}
