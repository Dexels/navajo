package com.dexels.navajo.client.nql.internal.command;

import com.dexels.navajo.client.ClientException;
import com.dexels.navajo.client.nql.NQLCommand;
import com.dexels.navajo.client.nql.NqlContextApi;
import com.dexels.navajo.client.nql.OutputCallback;

public class OutputCommand implements NQLCommand {

	private String path = null;
	@Override
	public void execute(NqlContextApi context,String tenant, String username, String password, OutputCallback callback) throws ClientException {
		context.output(path);
	}

	@Override
	public void parse(String raw) {
		String[] parts = raw.split(":");
		path = parts[1];
	}

}
