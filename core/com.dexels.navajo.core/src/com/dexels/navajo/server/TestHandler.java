package com.dexels.navajo.server;

import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.AuthorizationException;
import com.dexels.navajo.script.api.SystemException;
import com.dexels.navajo.script.api.UserException;

public class TestHandler extends ServiceHandler {

	private final Function<Access, Navajo> handler;
	
	private static final Logger logger = LoggerFactory.getLogger(TestHandler.class);

	public TestHandler(Function<Access, Navajo> handler) {
		this.handler = handler;
	}
	@Override
	public String getIdentifier() {
		return "default";
	}

	@Override
	public Navajo doService(Access a) throws NavajoException, UserException, SystemException, AuthorizationException {
		return handler.apply(a);
	}

	@Override
	public boolean needsRecompile(Access a) throws Exception {
		return false;
	}

	@Override
	public void setNavajoConfig(NavajoConfigInterface navajoConfig) {
		logger.warn("TestHandler ignores navajo config");
	}

}
