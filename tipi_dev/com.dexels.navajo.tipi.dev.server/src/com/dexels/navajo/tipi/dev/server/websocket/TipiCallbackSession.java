package com.dexels.navajo.tipi.dev.server.websocket;

import java.io.IOException;

public interface TipiCallbackSession {

	public void sendMessage(String data) throws IOException;

}
