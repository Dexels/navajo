package com.dexels.navajo.osgi.runtime;

import java.io.IOException;
import java.util.Dictionary;

public interface ConfigurationInjectionInterface {
	public void addConfiguration(String name, Dictionary<String, ?> data) throws IOException;
	public String addFactoryConfiguration(String name, Dictionary<String, ?> data) throws IOException;
	public void removeConfigutation(String name) throws IOException;
}
