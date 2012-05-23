package com.dexels.navajo.client.nql.command;

import com.dexels.navajo.client.ClientException;
import com.dexels.navajo.client.nql.NQLCommand;
import com.dexels.navajo.client.nql.NQLContext;
import com.dexels.navajo.document.NavajoException;

public class OutputCommand implements NQLCommand {

	private String path = null;
	public void execute(NQLContext context) throws ClientException,NavajoException {
		context.output(path);
	}

	public void parse(String raw) {
		String[] parts = raw.split(":");
		path = parts[1];
	}

}
