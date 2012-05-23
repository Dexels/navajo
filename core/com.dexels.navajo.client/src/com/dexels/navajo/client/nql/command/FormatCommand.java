package com.dexels.navajo.client.nql.command;

import java.io.IOException;

import com.dexels.navajo.client.ClientException;
import com.dexels.navajo.client.nql.NQLCommand;
import com.dexels.navajo.client.nql.NQLContext;
import com.dexels.navajo.document.NavajoException;

public class FormatCommand implements NQLCommand {

	private String format = null;
	public void execute(NQLContext context) throws ClientException,NavajoException, IOException {
		context.format(format);
	}

	public void parse(String raw) {
		String[] parts = raw.split(":");
		format = parts[1];
	}

}
