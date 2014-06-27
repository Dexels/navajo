package com.dexels.navajo.integrationtest.entity;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.junit.Assert;
import org.junit.Test;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.json.JSONTML;
import com.dexels.navajo.document.json.JSONTMLFactory;




public class HttpTestEntityPerson extends HttpTestSetup{
	
	@Test
	public void testGetPlayer() throws IOException {
		testGetPlayer(player1);
		testGetPlayer(player2);
	}

	
	public Message testGetPlayer(Message player) throws IOException {
		HttpGet request = new HttpGet(HTTP_REST_URL + "Player" + URL_PARAM + "PersonId="
				+ player.getProperty("PersonId").getValue());
		Navajo result = getNavajo(request);

		Message resultPlayer = result.getMessage("Player");
		Assert.assertNotNull(resultPlayer);
		Assert.assertEquals(player.getProperty("LastName").toString(),
				resultPlayer.getProperty("LastName").toString());
		return resultPlayer;
	}
	
	@Test
	public void testDeletePlayer1() throws IOException {
		HttpDelete request = new HttpDelete(HTTP_REST_URL + "Player" + URL_PARAM + "PersonId=AA");
		request.addHeader("Accept", "application/json");
		request.addHeader("Content-Type", "application/json");
		HttpResponse response = httpclient.execute(request);
		Assert.assertTrue(response.getStatusLine().getStatusCode() == 200);
		request.releaseConnection();
	}
	
	@Test
	public void testPutPlayer1() throws IOException {
		HttpPut request = new HttpPut(HTTP_REST_URL + "Player" + URL_PARAM);
		
		NavajoFactory f = NavajoFactory.getInstance();
		Navajo n = f.createNavajo();
		Message player3 = f.createMessage(n, "Player");
		player3.addProperty(f.createProperty(n, "PersonId", Property.STRING_PROPERTY, "CC", 0, "", ""));
		player3.addProperty(f.createProperty(n, "LastName", Property.STRING_PROPERTY, "Dexels", 0, "", ""));
		player3.addProperty(f.createProperty(n, "DateOfBirth", Property.DATE_PROPERTY, "1262300400000", 0, "", ""));

		StringWriter writer = new StringWriter();
		player3.writeSimpleJSON(writer);
		StringEntity entity = new StringEntity(writer.toString(), "UTF-8");
		entity.setContentType("application/json");
		request.setEntity(entity);
		try {
			HttpResponse response = httpclient.execute(request);
			Assert.assertEquals(200, response.getStatusLine().getStatusCode());
			request.releaseConnection();

			Message resultPerson = testGetPlayer(player3);
			Assert.assertEquals("1262300400000", resultPerson.getProperty("DateOfBirth").toString());
		} finally {
			HttpDelete request2 = new HttpDelete(HTTP_REST_URL + "Player" + URL_PARAM + "PersonId="
					+ player3.getProperty("PersonId").toString());
			request.addHeader("Accept", "application/json");
			request.addHeader("Content-Type", "application/json");
			HttpResponse response = httpclient.execute(request2);
			Assert.assertTrue(response.getStatusLine().getStatusCode() == 200);
			request2.releaseConnection();
		}
	}
	
	@Test
	public void testPostPlayer1() throws IOException {
		HttpPost request = new HttpPost(HTTP_REST_URL + "Player" + URL_PARAM);
		request.addHeader("Accept", "application/json");
		request.addHeader("Content-Type", "application/json");
        StringEntity entity = new StringEntity("{\"Player\": {\"PersonId\": \"AA\", \"LastName\": \"Player12\" }}", "UTF-8");
        entity.setContentType("application/json");
        request.setEntity(entity);
		HttpResponse response = httpclient.execute(request);
		Assert.assertTrue(response.getStatusLine().getStatusCode() == 200);
		request.releaseConnection();
		
		// Perform a GET to check whether the results are updated
		HttpGet request2 = new HttpGet(HTTP_REST_URL+"Player" + URL_PARAM + "PersonId=AA");
		response = httpclient.execute(request2);
		JSONTML json = JSONTMLFactory.getInstance();
		Navajo input = null;
		try {
			input = json.parse(new InputStreamReader(response.getEntity().getContent()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		Assert.assertNotNull(input);
		Message player = input.getMessage("Player");
		Assert.assertNotNull(player);
		Assert.assertEquals("Player12", player.getProperty("LastName").toString());
		request2.releaseConnection();
	}
	
	private Navajo getNavajo(HttpGet request) throws IOException, ClientProtocolException {
		request.addHeader("Accept", "application/json");
		request.addHeader("Content-Type", "application/json");
		
		HttpResponse response = httpclient.execute(request);
		JSONTML json = JSONTMLFactory.getInstance();
		Navajo input = null;
		Assert.assertEquals(200, response.getStatusLine().getStatusCode());
		try {
			InputStream contentInput = response.getEntity().getContent();
			input = json.parse(new InputStreamReader(contentInput));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			request.releaseConnection();
		}
		Assert.assertNotNull(input);
		return input;

	}
	
}
 	