package com.dexels.navajo.client.nql.internal.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.client.ClientException;
import com.dexels.navajo.client.nql.NQLCommand;
import com.dexels.navajo.client.nql.NqlContextApi;
import com.dexels.navajo.client.nql.OutputCallback;
import com.dexels.navajo.document.NavajoException;

public class SetValueCommand implements NQLCommand {

	private String path = null;
	private String value = null;
	
	private final static Logger logger = LoggerFactory
			.getLogger(SetValueCommand.class);
	
	@Override
	public void execute(NqlContextApi context,String tenant, String username, String password, OutputCallback callback) throws ClientException,NavajoException {
		context.set(path, value);
	}

	@Override
	public void parse(String raw) {
		String[] parts = raw.split(":");
		logger.info("part1: "+parts[0]);
		path = parts[0];
		value = parts[1];
	}

}
