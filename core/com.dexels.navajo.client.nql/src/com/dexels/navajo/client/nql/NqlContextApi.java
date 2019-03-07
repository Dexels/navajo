package com.dexels.navajo.client.nql;

import java.io.IOException;
import java.util.List;

import com.dexels.navajo.client.ClientException;
import com.dexels.navajo.client.context.ClientContext;

public interface NqlContextApi {

	public List<NQLCommand> parseCommand(String nql);

	public void executeCommand(String nql, String tenant, String username, String password, OutputCallback ob) throws IOException;

	public void setNavajoContext(ClientContext nrc);

	public ClientContext getNavajoContext();

    void call(String service, String tenant, String username, String password, boolean force) throws ClientException;

    public void format(String format, OutputCallback callback) throws IOException;

    public void output(String path);

    public void set(String path, String value);

}