package com.dexels.navajo.client.nql.command;

import com.dexels.navajo.client.ClientException;
import com.dexels.navajo.client.nql.NQLCommand;
import com.dexels.navajo.client.nql.NQLContext;
import com.dexels.navajo.document.NavajoException;

public class CallCommand implements NQLCommand {

	private String service = null;
	public void execute(NQLContext context) throws ClientException,NavajoException {
		context.call(service,true);
	}

	public void parse(String raw) {
		String[] parts = raw.split(":");
		service = parts[1];
	}

}
