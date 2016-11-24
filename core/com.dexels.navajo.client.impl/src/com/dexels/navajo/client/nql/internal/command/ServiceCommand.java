package com.dexels.navajo.client.nql.internal.command;

import com.dexels.navajo.client.ClientException;
import com.dexels.navajo.client.nql.NQLCommand;
import com.dexels.navajo.client.nql.NqlContextApi;
import com.dexels.navajo.client.nql.OutputCallback;
import com.dexels.navajo.document.NavajoException;

public class ServiceCommand implements NQLCommand {

	private String service = null;
	@Override
	public void execute(NqlContextApi context,String tenant, String username, String password, OutputCallback callback) throws ClientException,NavajoException {
		context.call(service,tenant,username, password, false);
	}

	@Override
	public void parse(String raw) {
		String[] parts = raw.split(":");
		service = parts[1];
	}

}
