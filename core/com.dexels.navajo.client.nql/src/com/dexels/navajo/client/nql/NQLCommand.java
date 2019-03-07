package com.dexels.navajo.client.nql;

import java.io.IOException;

public interface NQLCommand {
	public void execute(NqlContextApi context, String tenant, String username, String password, OutputCallback callback) throws IOException;
	public void parse(String raw);
}
