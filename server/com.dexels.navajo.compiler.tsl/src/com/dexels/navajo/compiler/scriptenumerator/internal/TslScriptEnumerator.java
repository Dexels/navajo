package com.dexels.navajo.compiler.scriptenumerator.internal;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.compiler.ScriptEnumerator;
import com.dexels.navajo.server.NavajoIOConfig;

public class TslScriptEnumerator implements ScriptEnumerator {

	@SuppressWarnings("unused")
	private NavajoIOConfig navajoIOConfig = null;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TslScriptEnumerator.class);
	

	public void setIOConfig(NavajoIOConfig config) {
		this.navajoIOConfig = config;
	}
	
	public void clearIOConfig(NavajoIOConfig config) {
		this.navajoIOConfig = null;
	}
	
	public void activate() {
		logger.info("ACTIVATE");
	}
	
	public static void main(String[] args) {
		String script = "club/InitUpdateClub";
		String pack = script.substring(0,script.lastIndexOf('/'));
		System.err.println("pack: "+pack);
//		this.navajoIOConfig;
	}

	@Override
	public List<String> enumerateScripts() {
		return new ArrayList<String>();
	}
}
