package com.dexels.navajo.resource.http.stream;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.dexels.navajo.client.stream.jetty.JettyClient;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.stream.DataItem.Type;
import com.dexels.navajo.document.stream.ReactiveParseProblem;
import com.dexels.navajo.reactive.api.ReactiveMerger;
import com.dexels.navajo.reactive.api.ReactiveParameters;
import com.dexels.navajo.reactive.api.ReactiveSource;
import com.dexels.navajo.reactive.api.ReactiveSourceFactory;
import com.dexels.navajo.reactive.api.ReactiveTransformer;

import io.reactivex.functions.Function;

public class CallRemoteSourceFactory implements ReactiveSourceFactory {

	private JettyClient client;

	public CallRemoteSourceFactory() throws Exception {
		this.client = new JettyClient();
	}
	
	@Override
	public Type sourceType() {
		return Type.EVENTSTREAM;
	}

	@Override
	public Optional<List<String>> allowedParameters() {
		return Optional.of(Arrays.asList(new String[]{"service","debug","server","username","password"}));
	}

	@Override
	public Optional<List<String>> requiredParameters() {
		return Optional.of(Arrays.asList(new String[]{"service","server","username","password"}));
	}

	@Override
	public Optional<Map<String, String>> parameterTypes() {
		Map<String,String> r = new HashMap<String, String>();
		r.put("service", Property.STRING_PROPERTY);
		r.put("debug", Property.BOOLEAN_PROPERTY);
		r.put("server", Property.STRING_PROPERTY);
		r.put("username", Property.STRING_PROPERTY);
		r.put("password", Property.STRING_PROPERTY);
		return Optional.of(r);	}

	@Override
	public ReactiveSource build(ReactiveParameters parameters) {
		return new CallRemoteSource(this, client, parameters);
	}

}
