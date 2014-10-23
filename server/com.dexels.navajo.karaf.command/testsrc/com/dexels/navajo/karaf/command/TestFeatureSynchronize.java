package com.dexels.navajo.karaf.command;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

public class TestFeatureSynchronize {

	private FeatureSynchronizer fs;

	@Before
	public void setUp() throws Exception {
		fs = new FeatureSynchronizer();
		fs.setFeaturesService(new MockFeatureService());
	    Properties properties = new Properties();
	    Map<String,Object> settings = new HashMap<String,Object>();
	      properties.load(TestFeatureSynchronize.class.getResourceAsStream("karaf.feature.synchronizer.cfg"));
	      for (final Entry<Object, Object> entry : properties.entrySet()) {
	    	  settings.put((String) entry.getKey(),  entry.getValue());
	      }
		  fs.activate(settings);
	    System.err.println("Activation complete");
	}

	@Test
	public void test() throws IOException {
		fs.updateFeatures();
	}

}
