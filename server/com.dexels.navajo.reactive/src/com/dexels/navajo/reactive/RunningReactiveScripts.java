package com.dexels.navajo.reactive;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class RunningReactiveScripts {
	
	
	private final static Logger logger = LoggerFactory.getLogger(RunningReactiveScripts.class);

	private final static ObjectMapper objectMapper = new ObjectMapper();

	private Map<String,StreamScriptContext> scriptsInProgress = new HashMap<>();

	public RunningReactiveScripts() {
	}

	public void submit(StreamScriptContext context) {
		scriptsInProgress.put(context.uuid(),context);		
	}

	public List<String> services() {
		return scriptsInProgress.values().stream().map(e->e.service).collect(Collectors.toList());
	}
	
	public void completed(StreamScriptContext context) {
		long started = context.started;
		long elapsed = System.currentTimeMillis() - started;
		logger.info("Script: {} ran for: {} millis",context.service,elapsed);
		scriptsInProgress.remove(context.uuid());
	}

	public void cancel(String uuid) {
		StreamScriptContext ctx = scriptsInProgress.get(uuid);
		if(ctx!=null) {
			ctx.cancel();
		}
		completed(ctx);
	}
	
	public Collection<StreamScriptContext> contexts() {
		return scriptsInProgress.values();
	}
	
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
}
