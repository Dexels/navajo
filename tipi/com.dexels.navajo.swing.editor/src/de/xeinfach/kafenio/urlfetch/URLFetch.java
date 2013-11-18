/*
 *-------------------------------------------------------------------------
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * The GNU Lesser General Public License may be found at the following URL:
 * http://www.opensource.org/licenses/lgpl-license.php
 *-------------------------------------------------------------------------
 */
package de.xeinfach.kafenio.urlfetch;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.StringTokenizer;

import de.xeinfach.kafenio.util.Base64Codec;
import de.xeinfach.kafenio.util.LeanLogger;
/**
 * Description: Fetches content from URLs
 * 
 * @author	Todd Wilson, modified by Karsten Pawlik 
 */
public class URLFetch {	

	private static LeanLogger log = new LeanLogger("URLFetch.class");
	
	private String postData = null;
	private String proxyHost = null;
	private int proxyPort = -1;
	private String proxyPassword = null;
	private URL url = null;
	private HashSet requestHeaders = new HashSet();
	private boolean isBinary = false;
	private byte[] responseBody = null;
	private HashSet responseHeaders = new HashSet();
	private String responseStatus = null;
  	private HashSet cookies = new HashSet();
  	private String authorizationUsername = null;
 	private String authorizationPassword = null;
	private String referer = null;
	
	/**
	 * Issues the request to the server.
	 * @throws IOException an IOException
	 */
	public void fetch() throws IOException {
		String key = null;
		String value = null;
		HttpURLConnection connection = null;
		Iterator it;
		StringBuffer sbCookies = new StringBuffer();
		HTTPHeader header = null;

		connection = (HttpURLConnection)url.openConnection();
		HttpURLConnection.setFollowRedirects(false);
		connection.setAllowUserInteraction(true);
		
		
		if(url.getProtocol().equals("https")) {
			log.debug("HTTPS is not supported.");
		}
		
		if(authorizationUsername!=null) {
			addHeader(new HTTPHeader(	"Authorization", 
										"BASIC " 
										+ Base64Codec.encode(authorizationUsername + ":" + authorizationPassword)));
		}
			
		if(proxyHost!=null) {					
			System.getProperties().put("proxySet", "true");
			System.getProperties().put("proxyHost", proxyHost);
			System.getProperties().put("proxyPort", String.valueOf(proxyPort));	
			if(proxyPassword!=null	&& proxyPassword.length() > 0) {
				connection.setRequestProperty("Proxy-Authorization", "Basic " + Base64Codec.encode(proxyPassword));	
			}
		}
		
		it = cookies.iterator();
		while(it.hasNext()) {
			Cookie cookie = (Cookie)it.next();
			if(	url.getHost().indexOf(cookie.getDomain())!=-1	&& url.getFile().indexOf(cookie.getPath())!=-1) {
				sbCookies.append(cookie.getKeyValue());
			}
		}

		if(sbCookies.length() > 0) {
			requestHeaders.add(new HTTPHeader("Cookie", sbCookies.substring(0, sbCookies.length() - 2)));
		}

		if(	referer!=null && getRequestHeader("Referer")==null) {
			addHeader(new HTTPHeader("Referer", referer));
		}

		it = requestHeaders.iterator();
		while(it.hasNext()) {
			header = (HTTPHeader)it.next();
			connection.setRequestProperty(header.getKey(), header.getValue());
		}
			
		if(postData!=null) {
			connection.setDoOutput(true);
			PrintWriter out = new PrintWriter(connection.getOutputStream());
			out.print(postData);
			out.close();
		}

		setResponseStatus(connection.getHeaderField(0));
		
		int i = 1;
		while(connection.getHeaderField(i)!=null) {
			responseHeaders.add(new HTTPHeader(connection.getHeaderFieldKey(i), connection.getHeaderField(i)));
			i++;			
			if(connection.getHeaderField(i)!=null) {
				if(	connection.getHeaderFieldKey(i)!=null
					&& connection.getHeaderFieldKey(i).compareToIgnoreCase("Set-Cookie")==0)
				{
					addHeader(new HTTPHeader("Cookie", connection.getHeaderField(i)));
				}
			}
		}

		byte[] buffer = new byte[128];
		DataInputStream in = new DataInputStream(connection.getInputStream());
		int bytesRead = 0;
		int startPos = 0;
		responseBody = new byte[0];
		byte[] bTemp;

		while((bytesRead = in.read(buffer)) != -1) {
			startPos = responseBody.length;
			bTemp = responseBody;
			responseBody = new byte[responseBody.length + bytesRead];
			System.arraycopy(bTemp, 0, responseBody, 0, bTemp.length);
			System.arraycopy(buffer, 0, responseBody, startPos, bytesRead);
		}

		if(	referer==null
			|| (referer!=null && getHTTPStatusCode()>=300 && getHTTPStatusCode() < 400))
		{
			setReferer(getURL().toString());
		}
	}

	/**
	 * Flushes any existing headers out, excluding cookies.
	 */
	public void flushHeaders() {
		requestHeaders.clear();
		responseHeaders.clear();
	}

	/**
	 * Gets the URL to be fetched.
	 *
	 * @return The URL.
	 */
	public URL getURL() {
		return url;
	}

	/**
	 * Sets the URL to be fetched.
	 *
	 * @param newUrl The URL.
	 */
	public void setURL(URL newUrl) {
		url = newUrl;
	}
	
	/**
	 * Sets the POST data.
	 *
	 * @param newPostData The POST data.
	 */
	public void setPOSTData(String newPostData) {
		postData = newPostData;
	}
	
	/**
	 * Gets the POST data.
	 *
	 * @return The POST data.
	 */
	public String getPOSTData() {
		return postData;
	}

	/**
	 * Gets a response header by name. 
	 *
	 * @param name The name of the desired header.
	 * @return returns a HTTPHeader object.
	 */
	public HTTPHeader getResponseHeader(String name) {
		Iterator it;
		HTTPHeader tmpHeader = null;
		
		it = responseHeaders.iterator();
		while(it.hasNext()) {
			tmpHeader = (HTTPHeader)it.next();

			if(tmpHeader.getKey().compareToIgnoreCase(name)==0) {
				return tmpHeader;
			}
		}
		return null;
	}

	/**
	 * In the case of a redirect, gets the URL to go to.  
	 * Often servers will send a relative path to redirect to.  
	 * This method handles turning that relative URL into an 
	 * absolute one.
	 *
	 * @return The <code>URL</code> to redirect to.
	 */
	public URL getRedirectURL() {
		if(getResponseHeader("Location")==null) {
			return null;
		}

		String redirectURL = getResponseHeader("Location").getValue();
		try {
			if(redirectURL.startsWith("http")) {
				return new URL(redirectURL);
			} else {
				return new URL(getURL().getProtocol(), getURL().getHost(), getURL().getPort(), redirectURL);
			}
		} catch(MalformedURLException mfue) {
			log.debug("not a URL" + mfue.fillInStackTrace());
			return null;
		}
	}
	
	/**
	 * Returns the HTTP request headers.
	 *
	 * @return The headers.
	 */
	public HashSet getRequestHeaders() {
		return requestHeaders;
	}

	/**
	 * Gets a request header by name. 
	 *
	 * @param name The name of the desired header.
	 * @return returns a HTTPHeader object.
	 */
	public HTTPHeader getRequestHeader(String name) {
		Iterator it;
		HTTPHeader tmpHeader = null;

		it = requestHeaders.iterator();
		while(it.hasNext()) {
			tmpHeader = (HTTPHeader)it.next();

			if(tmpHeader.getKey().compareToIgnoreCase(name)==0) {
				return tmpHeader;
			}
		}
		return null;
	}

	/**
	 * Returns the request method used.
	 *
	 * @return The method (only GET or POST).
	 */
	public String getRequestMethod() {
		if(getPOSTData()==null) {
			return "GET";
		} else {
			return "POST";
		}
	}

	/**
	 * Returns the first line of the request.
	 *
	 * @return	The first line.
	 */
	public String getRequestLine() {
		return getRequestMethod() + " " + getURL() + " HTTP/1.0";
	}
	
	/**
	 * Returns the HTTP headers received from the server.
	 *
	 * @return The headers.
	 */
	public HashSet getResponseHeaders() {
		return responseHeaders;
	}
	
	/**
	 * Sets authorization tokens in the case of BASIC authentication
	 *
	 * @param newAuthorizationUsername The username.
	 * @param newAuthorizationPassword The password.
	 */
	public void setAuthorization(String newAuthorizationUsername, String newAuthorizationPassword) {
		authorizationUsername = newAuthorizationUsername; 
		authorizationPassword = newAuthorizationPassword; 
	}	

	/**
	 * Adds a request header to be sent to the server.
	 *
	 * @param header The header to be sent in the request.
	 */
	public void addHeader(HTTPHeader header) {
	    if(header.getKey().compareToIgnoreCase("Cookie")==0) {
			Cookie newCookie = new Cookie(header.getValue());
			Cookie tmpCookie = null;
			Iterator it;

			if("".equals(newCookie.getDomain())) {
				newCookie.setDomain(url.getHost());
			}

			it = cookies.iterator();
			while(it.hasNext())	{
				tmpCookie = (Cookie)it.next();
				if(	tmpCookie.getKey().equals(newCookie.getKey())
					&& tmpCookie.getDomain().equals(newCookie.getDomain())
					&& tmpCookie.getPath().equals(newCookie.getPath()))
				{
					cookies.remove(tmpCookie);
					break;
				}
			}

			if(!newCookie.getValue().equals("")) {
				cookies.add(newCookie);
			}
	    } else {
			requestHeaders.add(header);
	    }		
	}
	
	/**
	 * If a binary file was fetched, this function will return it as a byte array.
	 * @return returns the response body as byte-array. 
	 */
	public byte[] getResponseBody() {
		return responseBody;
	}

	/**
	 * Returns the HTTP status of a response.
	 *
	 * @return The status.
	 */
	public String getResponseStatus() {
		return responseStatus;
	}

	/**
	 * Sets the HTTP status of a response.
	 *
	 * @param newResponseStatus The status.
	 */
	public void setResponseStatus(String newResponseStatus) {
		responseStatus = newResponseStatus;
	}

	/**
	 * Gets the code portion of the HTTP status.
	 *
	 * @return An int representing the status code.
	 */
	public int getHTTPStatusCode() {
		if(responseStatus==null) {
			return -1;
		}

		StringTokenizer tokenizer = new StringTokenizer(responseStatus, " ");
		tokenizer.nextToken();
		return Integer.parseInt(tokenizer.nextToken());
	}

	/**
	 * Sets the referer to be sent as an HTTP request header.
	 *
	 * @param referer The referer.
	 */
	private void setReferer(String newReferer) {
		referer = newReferer;
	}

	/**
	 * Sets the host of the proxy, if one is needed.
	 *
	 * @param newProxyHost The host.
	 */
	public void setProxyHost(String newProxyHost) {
		proxyHost = newProxyHost;
	}

	/**
	 * Sets the port of the proxy, if one is needed.
	 *
	 * @param newProxyPort The port.
	 */
	public void setProxyPort(int newProxyPort) {
		proxyPort = newProxyPort;
	}

	/**
	 * Sets the password of the proxy, if one is needed.
	 *
	 * @param newProxyPassword The password.
	 */
	public void setProxyPassword(String newProxyPassword) {
		proxyPassword = newProxyPassword;
	}
}
