package com.dexels.navajo.client.nql;

import java.io.IOException;

import com.dexels.navajo.client.ClientException;
import com.dexels.navajo.document.NavajoException;

public interface NQLCommand {
	public void execute(NqlContextApi context, String tenant, String username, String password, OutputCallback callback) throws ClientException, NavajoException,IOException;
	public void parse(String raw);
}
