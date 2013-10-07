package com.dexels.navajo.tipi.dev.core.projectbuilder;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VersionResolver {
	
	private final static Logger logger = LoggerFactory
			.getLogger(VersionResolver.class);
	private Map<String,List<String>> repDefinition;
	String currentToken = null;
	public VersionResolver(String repository) throws IOException {
		load(repository);
	}
	public VersionResolver() {
		// TODO Auto-generated constructor stub
	}
	public void load(String repository) throws IOException {
		repDefinition = ClientActions.getExtensions(repository);

	}
	public Map<String,String> resolveVersion(String token) {
		String ext = null;
		String version = null;
	//	logger.info("repppp: "+repDefinition+" token: "+token);
		if(token.indexOf("/")!=-1) {
			StringTokenizer st2 = new StringTokenizer(token,"/");
			ext = st2.nextToken();
			version = st2.nextToken();
		} else {
			ext = token;
			List<String> versions = repDefinition.get(token);
			if(versions==null) {
				throw new IllegalArgumentException("Error building extension: No version found for token: "+token+"  Repository definition: "+repDefinition);
			}
			version=versions.get(versions.size()-1);
		}
		Map<String, String> m = new HashMap<String, String>();
		m.put("extension", ext);
		m.put("version", version);
		return m;
	}
	
	public String resultVersionPath(String token) {
		Map<String,String> m = resolveVersion(token);
		return m.get("extension")+"/"+m.get("version");
	}

	// Bean accessors for JSP:
	public String getVersionToken() {
		return currentToken;
	}
	
	public void setVersionToken(String token) {
		try {
			currentToken = resultVersionPath(token);
		} catch (IllegalArgumentException e) {
			currentToken = "Error resolving: "+token;
			logger.error("Error: ",e);
		}
	}

}
