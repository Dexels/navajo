package com.dexels.navajo.authorization.oauth2.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthAccessTokenResponse;
import org.apache.oltu.oauth2.common.OAuthProviderType;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;

public class TestLocal {

	private static final String CLIENT_ID = "4d8cf0e90a1ce66536b9";
	private static final String CLIENT_SECRET = "dcf2867488ba880348d037a4f45b28dc1d41117f";
	private static final String REDIRECT_URL = "http://localhost:8080/authorize";
	private static final String ACCESS_TOKEN_ENDPOINT = "http://localhost:8080/auth/client";

	public static void main(String[] args) throws OAuthSystemException,
			OAuthProblemException, IOException {
		// TODO Auto-generated method stub
//		grantCode(AUTHORIZATION_CODE);
		testLocal();
	}
	
	// /auth/client?response_type=code&client_id=123&redirect_url=aap.nl

	private static void testLocal() throws OAuthSystemException, OAuthProblemException, IOException {
		OAuthClientRequest request = OAuthClientRequest
				.tokenLocation(ACCESS_TOKEN_ENDPOINT)
				   .setClientId(CLIENT_ID)
				   .setRedirectURI(REDIRECT_URL)
				   .buildQueryMessage();
//		OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
		System.err.println("Request: " + request.getLocationUri()
				+ " headers: " + request.getHeaders() + " body: "
				+ request.getBody());
		
        //in web application you make redirection to uri:
        System.out.println("Visit: " + request.getLocationUri() + "\nand grant permission");

        System.out.print("Now enter the OAuth code you have received in redirect uri ");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String code = br.readLine();
        grantCode(code);
//		OAuthAccessTokenResponse response = oAuthClient.accessToken(request);
//		System.err.println("Response: " + response);

	}
	
	private static void grantCode(String code) throws OAuthSystemException,
			OAuthProblemException {
		OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
		OAuthClientRequest request = OAuthClientRequest
				.tokenProvider(OAuthProviderType.GITHUB)
				.setGrantType(GrantType.AUTHORIZATION_CODE)
				.setCode(code)
				.setRedirectURI(REDIRECT_URL).setClientId(CLIENT_ID)
				.setClientSecret(CLIENT_SECRET).buildQueryMessage();

		request.addHeader("Accept", "application/json");
//        request = OAuthClientRequest
//            	.tokenProvider(OAuthProviderType.GITHUB)
//                .setGrantType(GrantType.AUTHORIZATION_CODE)
//                .setClientId("4d8cf0e90a1ce66536b9")
//                .setClientSecret("dcf2867488ba880348d037a4f45b28dc1d41117f")
//                .setRedirectURI("http://localhost:8080/authorize")
//                .setCode(code)
//                .buildQueryMessage();
		
		System.err.println("Request: " + request.getLocationUri()
				+ " headers: " + request.getHeaders() + " body: "
				+ request.getBody());
		OAuthAccessTokenResponse response = oAuthClient.accessToken(request);
		System.err.println("Response: " + response.getAccessToken());
		System.err.println("scope: "+response.getScope());
		System.err.println("body: "+response.getBody());
		System.err.println("expiresin: "+response.getExpiresIn());
		System.err.println("refresh: "+response.getRefreshToken());
	}

}
