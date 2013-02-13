package com.dexels.navajo.client.nql;

import java.io.IOException;

import com.dexels.navajo.client.ClientException;
import com.dexels.navajo.client.nql.internal.NQLContext;
import com.dexels.navajo.document.NavajoException;

public interface NQLCommand {
	public void execute(NQLContext context, OutputCallback callback) throws ClientException, NavajoException,IOException;
	public void parse(String raw);
}
