package com.dexels.navajo.osgi.runtime;

import java.io.IOException;
import java.util.Dictionary;

public interface ConfigurationInjectionInterface {
	public void addConfiguration(String name, Dictionary data) throws IOException;
	public String addFactoryConfiguration(String name, Dictionary data) throws IOException;
	public void removeConfigutation(String name) throws IOException;
}
