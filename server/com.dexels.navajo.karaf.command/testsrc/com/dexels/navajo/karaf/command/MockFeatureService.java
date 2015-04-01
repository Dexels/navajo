package com.dexels.navajo.karaf.command;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.EnumSet;
import java.util.Set;

import org.apache.karaf.features.Feature;
import org.apache.karaf.features.FeaturesService;
import org.apache.karaf.features.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MockFeatureService implements FeaturesService {

	
	private final static Logger logger = LoggerFactory
			.getLogger(MockFeatureService.class);
	public MockFeatureService() {
	}

	@Override
	public void addRepository(URI arg0) throws Exception {

	}

	@Override
	public void addRepository(URI arg0, boolean arg1) throws Exception {

	}

	@Override
	public Feature getFeature(String arg0) throws Exception {
		return null;
	}

	@Override
	public Feature getFeature(String arg0, String arg1) throws Exception {
		return null;
	}

	@Override
	public Repository getRepository(String arg0) {
		return null;
	}

	@Override
	public void installFeature(String arg0) throws Exception {

	}

	@Override
	public void installFeature(String arg0, EnumSet<Option> arg1)
			throws Exception {

	}

	@Override
	public void installFeature(String arg0, String arg1) throws Exception {

	}

	@Override
	public void installFeature(Feature arg0, EnumSet<Option> arg1)
			throws Exception {

	}

	@Override
	public void installFeature(String arg0, String arg1, EnumSet<Option> arg2)
			throws Exception {

	}

	@Override
	public void installFeatures(Set<Feature> arg0, EnumSet<Option> arg1)
			throws Exception {

	}

	@Override
	public boolean isInstalled(Feature arg0) {
		return false;
	}

	@Override
	public Feature[] listFeatures() throws Exception {
		return null;
	}

	@Override
	public Feature[] listInstalledFeatures() {
		return null;
	}

	@Override
	public Repository[] listRepositories() {
		Repository rep1 = new Repository() {
			
			@Override
			public boolean isValid() {
				return true;
			}
			
			@Override
			public URI getURI() {
				try {
					return new URI("uri://someuri");
				} catch (URISyntaxException e) {
					logger.error("Error: ", e);
					return null;
				}
			}
			
			@Override
			public URI[] getRepositories() throws Exception {
				return null;
			}
			
			@Override
			public String getName() {
				return "preset.repo" ;
			}
			
			@Override
			public Feature[] getFeatures() throws Exception {
				return new Feature[]{};
			}
		};
		return new Repository[]{rep1};
	}

	@Override
	public void refreshRepository(URI arg0) throws Exception {

	}

	@Override
	public void removeRepository(URI u) throws Exception {
		System.err.println("removing: "+u);
	}

	@Override
	public void removeRepository(URI u, boolean arg1) throws Exception {
		System.err.println("removing: "+u);

	}

	@Override
	public void restoreRepository(URI arg0) throws Exception {

	}

	@Override
	public void uninstallFeature(String arg0) throws Exception {

	}

	@Override
	public void uninstallFeature(String arg0, EnumSet<Option> arg1)
			throws Exception {
	}

	@Override
	public void uninstallFeature(String arg0, String arg1) throws Exception {
	}

	@Override
	public void uninstallFeature(String arg0, String arg1, EnumSet<Option> arg2)
			throws Exception {

	}

	@Override
	public void validateRepository(URI arg0) throws Exception {

	}

}
