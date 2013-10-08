package com.dexels.navajo.client.nql.internal.command;

import java.io.IOException;

import com.dexels.navajo.client.ClientException;
import com.dexels.navajo.client.nql.NQLCommand;
import com.dexels.navajo.client.nql.OutputCallback;
import com.dexels.navajo.client.nql.internal.NQLContext;
import com.dexels.navajo.document.NavajoException;

public class FormatCommand implements NQLCommand {

	private String format = null;
	@Override
	public void execute(NQLContext context, OutputCallback callback) throws ClientException,NavajoException, IOException {
		context.format(format,callback);
	}

	@Override
	public void parse(String raw) {
		String[] parts = raw.split(":");
		format = parts[1];
	}

}
