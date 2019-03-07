package com.dexels.navajo.client.nql.internal.command;

import java.io.IOException;

import com.dexels.navajo.client.nql.NQLCommand;
import com.dexels.navajo.client.nql.NqlContextApi;
import com.dexels.navajo.client.nql.OutputCallback;

public class FormatCommand implements NQLCommand {

	private String format = null;
	@Override
	public void execute(NqlContextApi context,String tenant, String username, String password, OutputCallback callback) throws IOException {
		context.format(format,callback);
	}

	@Override
	public void parse(String raw) {
		String[] parts = raw.split(":");
		format = parts[1];
	}

}
