package com.dexels.navajo.reactive.mappers;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.immutable.api.ImmutableMessageParser;
import com.dexels.immutable.factory.ImmutableFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.stream.DataItem;
import com.dexels.navajo.document.stream.api.StreamScriptContext;
import com.dexels.navajo.document.stream.events.NavajoStreamEvent;
import com.dexels.navajo.reactive.api.ReactiveMerger;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveResolvedParameters;


public class Log implements ReactiveMerger {

	
	private final static Logger logger = LoggerFactory.getLogger(Log.class);

	public Log() {
	}

	@Override
	public Function<StreamScriptContext, Function<DataItem, DataItem>> execute(ReactiveParameters params) {
		ImmutableMessageParser parser = ImmutableFactory.createParser();
		return context -> {
			
			return (item) -> {
				switch(item.type) {
				case ANY:
					break;
				case DATA:
					logData(item.data());
					break;
				case EMPTY:
					break;
				case EVENT:
					logEvent(item.event());
					break;
				case EVENTSTREAM:
					break;
				case MESSAGE:
					logMessage(parser, context,params, item.message(),item.stateMessage());
					break;
				case MSGLIST:
					break;
				case MSGSTREAM:
					break;
				case SINGLEMESSAGE:
					break;
				default:
					break;
				
				}

				return item;
			};
		};
	}
	
	private void logData(byte[] data) {
		logger.info("Data found #"+data.length+" bytes");
	}

	private void logEvent(NavajoStreamEvent event) {
		logger.info("Event: "+event);
	}

	private void logMessage(ImmutableMessageParser parser, StreamScriptContext context, ReactiveParameters params, ImmutableMessage message,ImmutableMessage stateMessage) {
		ReactiveResolvedParameters named = params.resolve(context,Optional.of(message), stateMessage, this);
		boolean condition = named.optionalBoolean("condition").orElse(true);
		if(!condition) {
			return;
		}
		
		byte[] serialized = parser.serialize(message);
		
		String data = serialized ==null? "<empty>" : new String(serialized);
		Map<String,String> previous = MDC.getCopyOfContextMap();
//		Map<String,String> map = context.createMDCMap(xml.map(e->e.getStartLineNr()).orElse(-1));
//		MDC.setContextMap(map);
		logger.info(data);
		if(previous!=null) {
			MDC.setContextMap(previous);
		} else {
			MDC.clear();
		}		
	}

	@Override
	public Optional<List<String>> allowedParameters() {
		return Optional.of(Arrays.asList(new String[]{"condition","field"}));
	}

	@Override
	public Optional<List<String>> requiredParameters() {
		return Optional.of(Arrays.asList(new String[]{}));
	}

	@Override
	public Optional<Map<String, String>> parameterTypes() {
		Map<String,String> r = new HashMap<>();
		r.put("condition", Property.BOOLEAN_PROPERTY);
		r.put("field", Property.STRING_PROPERTY);
		return Optional.of(Collections.unmodifiableMap(r));
	}
}
