package com.dexels.navajo.camel.http;

import org.apache.camel.component.http.HttpClientConfigurer;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;

public class AcceptSelfSignCertHttpClientConfigure implements HttpClientConfigurer {

	@Override
	public void configureHttpClient(HttpClient client) {
		// register the customer SSLFactory
		ProtocolSocketFactory easy = new EasySSLProtocolSocketFactory();
		Protocol protocol = new Protocol("https", easy, 443);
		Protocol.registerProtocol("https", protocol);

	}

}