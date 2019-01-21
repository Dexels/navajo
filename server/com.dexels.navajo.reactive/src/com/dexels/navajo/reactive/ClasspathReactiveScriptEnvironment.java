package com.dexels.navajo.reactive;

import java.io.InputStream;
import java.util.Optional;

public class ClasspathReactiveScriptEnvironment extends ReactiveScriptEnvironment {

	private Class<?> clazz;
	
	public ClasspathReactiveScriptEnvironment(Class<?> clz) {
		this.clazz = clz;
	}
	@Override
	protected Optional<InputStream> resolveFile(String serviceName) {
		InputStream resourceAsStream = clazz.getResourceAsStream(serviceName+".rr");
		return Optional.ofNullable(resourceAsStream);
	}

}
