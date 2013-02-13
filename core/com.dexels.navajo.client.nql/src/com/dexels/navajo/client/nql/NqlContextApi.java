package com.dexels.navajo.client.nql;

import java.io.IOException;
import java.util.List;

import com.dexels.navajo.client.ClientException;
import com.dexels.navajo.client.context.ClientContext;
import com.dexels.navajo.document.NavajoException;

public interface NqlContextApi {

	public List<NQLCommand> parseCommand(String nql);

	public void executeCommand(String nql, OutputCallback ob) throws ClientException,
			NavajoException, IOException;

	public void setNavajoContext(ClientContext nrc);

	public ClientContext getNavajoContext();

}