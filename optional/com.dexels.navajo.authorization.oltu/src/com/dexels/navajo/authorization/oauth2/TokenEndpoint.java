package com.dexels.navajo.authorization.oauth2;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.as.request.OAuthTokenRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.GrantType;

public class TokenEndpoint extends HttpServlet {

	private static final long serialVersionUID = 3838542062065960623L;
	private static final String INVALID_CLIENT_DESCRIPTION = "Invalid client";

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			authorize(request, response);
		} catch (OAuthSystemException e) {
			e.printStackTrace();
			response.sendError(400, e.getMessage());
		}
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			authorize(request, response);
		} catch (OAuthSystemException e) {
			e.printStackTrace();
			response.sendError(400, e.getMessage());
		}
	}

	private boolean verifyClientId(String clientId) {
		return true;
	}

	private boolean verifyClientSecret(String clientSecret) {
		return true;
	}

	private boolean verifyAuthorizationCode(String authorizationId) {
		return true;
	}

	private boolean verifyCredentials(String username, String password) {
		return true;
	}

	public void authorize(HttpServletRequest request, HttpServletResponse resp)
			throws OAuthSystemException, IOException {

		OAuthTokenRequest oauthRequest = null;

		OAuthIssuer oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());

		try {
			oauthRequest = new OAuthTokenRequest(request);

			// check if clientid is valid
			if (!verifyClientId(oauthRequest.getClientId())) {
				OAuthResponse response = OAuthASResponse
						.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
						.setError(OAuthError.TokenResponse.INVALID_CLIENT)
						.setErrorDescription(INVALID_CLIENT_DESCRIPTION)
						.buildJSONMessage();

				// return
				// Response.status(response.getResponseStatus()).entity(response.getBody()).build();
				resp.setStatus(response.getResponseStatus());
				PrintWriter pw = resp.getWriter();
				pw.print(response.getBody());
				pw.flush();
				pw.close();
				return;
			}

			// check if client_secret is valid
			if (!verifyClientSecret(oauthRequest.getClientSecret())) {
				OAuthResponse response = OAuthASResponse
						.errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
						.setError(OAuthError.TokenResponse.UNAUTHORIZED_CLIENT)
						.setErrorDescription(INVALID_CLIENT_DESCRIPTION)
						.buildJSONMessage();
				resp.setStatus(response.getResponseStatus());
				PrintWriter pw = resp.getWriter();
				pw.print(response.getBody());
				pw.flush();
				pw.close();
				return;
			}

			// do checking for different grant types
			if (oauthRequest.getParam(OAuth.OAUTH_GRANT_TYPE).equals(
					GrantType.AUTHORIZATION_CODE.toString())) {
				if (!verifyAuthorizationCode(oauthRequest
						.getParam(OAuth.OAUTH_CODE))) {
					OAuthResponse response = OAuthASResponse
							.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
							.setError(OAuthError.TokenResponse.INVALID_GRANT)
							.setErrorDescription("invalid authorization code")
							.buildJSONMessage();
					resp.setStatus(response.getResponseStatus());
					PrintWriter pw = resp.getWriter();
					pw.print(response.getBody());
					pw.flush();
					pw.close();
					return;
				}
			} else if (oauthRequest.getParam(OAuth.OAUTH_GRANT_TYPE).equals(
					GrantType.PASSWORD.toString())) {
				if (!verifyCredentials(oauthRequest.getUsername(),
						oauthRequest.getPassword())) {
					OAuthResponse response = OAuthASResponse
							.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
							.setError(OAuthError.TokenResponse.INVALID_GRANT)
							.setErrorDescription("invalid username or password")
							.buildJSONMessage();
					resp.setStatus(response.getResponseStatus());
					PrintWriter pw = resp.getWriter();
					pw.print(response.getBody());
					pw.flush();
					pw.close();
					return;
				}
			} else if (oauthRequest.getParam(OAuth.OAUTH_GRANT_TYPE).equals(
					GrantType.REFRESH_TOKEN.toString())) {
				// refresh token is not supported in this implementation
				OAuthResponse response = OAuthASResponse
						.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
						.setError(OAuthError.TokenResponse.INVALID_GRANT)
						.setErrorDescription("invalid username or password")
						.buildJSONMessage();
				resp.setStatus(response.getResponseStatus());
				PrintWriter pw = resp.getWriter();
				pw.print(response.getBody());
				pw.flush();
				pw.close();
				return;
			}

			OAuthResponse response = OAuthASResponse
					.tokenResponse(HttpServletResponse.SC_OK)
					.setAccessToken(oauthIssuerImpl.accessToken())
					.setExpiresIn("3600").buildJSONMessage();
			resp.setStatus(response.getResponseStatus());
			PrintWriter pw = resp.getWriter();
			pw.print(response.getBody());
			pw.flush();
			pw.close();
			return;

		} catch (OAuthProblemException e) {
			OAuthResponse response = OAuthASResponse
					.errorResponse(HttpServletResponse.SC_BAD_REQUEST).error(e)
					.buildJSONMessage();
			resp.setStatus(response.getResponseStatus());
			PrintWriter pw = resp.getWriter();
			pw.print(response.getBody());
			pw.flush();
			pw.close();
			return;
		}
	}
}
