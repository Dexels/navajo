package com.dexels.navajo.integrationtest.entity;

import java.io.IOException;
import java.io.StringWriter;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.entity.EntityException;


public abstract class HttpTestSetup {
	protected HttpClient httpclient;
	protected static final String HTTP_REST_URL = "http://localhost:9099/entity/";
	protected static final String URL_PARAM = "?username=ROOT&password=ROOT&";

	//static Message entity;
	protected Message player1; 
	protected Message player2;
	protected Message club1;
	protected Message club2;
	
	@Before
	public void setup() throws Exception {
		
		httpclient = HttpClientBuilder.create().build();		
		setupEntityInstances();

		insertPlayer1();
		insertPlayer2();
		insertClub1();
		insertClub2();
		
	}

	private void setupEntityInstances() throws EntityException {
		NavajoFactory f = NavajoFactory.getInstance();
		Navajo n = f.createNavajo();
		player1 = f.createMessage(n, "Player");
		player1.addProperty(f.createProperty(n, "PersonId", Property.STRING_PROPERTY, "AA", 0, "", ""));
		player1.addProperty(f.createProperty(n, "LastName", Property.STRING_PROPERTY, "Jansen", 0, "", ""));
		
		player2 = f.createMessage(n, "Player");
		player2.addProperty(f.createProperty(n, "PersonId", Property.STRING_PROPERTY, "BB", 0, "", ""));
		player2.addProperty(f.createProperty(n, "LastName", Property.STRING_PROPERTY, "Pieters", 0, "", ""));
		
		club1 = f.createMessage(n, "Club");
		club1.addProperty(f.createProperty(n, "OrganizationId", Property.STRING_PROPERTY, "AJAX", 0, "", ""));
		club1.addProperty(f.createProperty(n, "Country", Property.STRING_PROPERTY, "Netherlands", 0, "", ""));
		
		club2 = f.createMessage(n, "Club");
		club2.addProperty(f.createProperty(n, "OrganizationId", Property.STRING_PROPERTY, "AZ", 0, "", ""));
		club2.addProperty(f.createProperty(n, "Country", Property.STRING_PROPERTY, "Netherlands", 0, "", ""));
		
	

	}
	
	@After
	public void tearDown() throws ClientProtocolException, IOException {
		removePlayer1();
		removePlayer2();
		removeClub1();
		removeClub2();
	}

	public void insertPlayer1() throws Exception {
		HttpPut request = new HttpPut(HTTP_REST_URL + "Player" + URL_PARAM);
		request.addHeader("Accept", "application/json");
		request.addHeader("Content-Type", "application/json");
		
		StringWriter writer = new StringWriter();
		player1.writeSimpleJSON(writer);
		StringEntity entity = new StringEntity(writer.toString(), "UTF-8");
		entity.setContentType("application/json");
		request.setEntity(entity);
		HttpResponse response = httpclient.execute(request);
		Assert.assertEquals(200, response.getStatusLine().getStatusCode());
		request.releaseConnection();
	}

	public void removePlayer1() throws ClientProtocolException, IOException {
		HttpDelete request = new HttpDelete(HTTP_REST_URL + "Player" + URL_PARAM + "PersonId="
				+ player1.getProperty("PersonId").toString());
		request.addHeader("Accept", "application/json");
		request.addHeader("Content-Type", "application/json");
		HttpResponse response = httpclient.execute(request);

		Assert.assertTrue(response.getStatusLine().getStatusCode() == 200
				|| response.getStatusLine().getStatusCode() == EntityException.ENTITY_NOT_FOUND);

		request.releaseConnection();
	}

	public void insertPlayer2() throws ClientProtocolException, IOException {
		HttpPut request = new HttpPut(HTTP_REST_URL + "Player" + URL_PARAM);
		request.addHeader("Accept", "application/json");
		request.addHeader("Content-Type", "application/json");
		StringWriter writer = new StringWriter();
		player2.writeSimpleJSON(writer);
		StringEntity entity = new StringEntity(writer.toString(), "UTF-8");
		entity.setContentType("application/json");
		request.setEntity(entity);
		HttpResponse response = httpclient.execute(request);
		Assert.assertEquals(200, response.getStatusLine().getStatusCode());
		request.releaseConnection();
	}

	public void removePlayer2() throws ClientProtocolException, IOException {
		HttpDelete request = new HttpDelete(HTTP_REST_URL + "Player" + URL_PARAM + "PersonId="
				+ player2.getProperty("PersonId").toString());
		request.addHeader("Accept", "application/json");
		request.addHeader("Content-Type", "application/json");

		HttpResponse response = httpclient.execute(request);

		Assert.assertTrue(response.getStatusLine().getStatusCode() == 200
				|| response.getStatusLine().getStatusCode() == EntityException.ENTITY_NOT_FOUND);

		request.releaseConnection();
	}

	public void insertClub1() throws ClientProtocolException, IOException {
		HttpPut request = new HttpPut(HTTP_REST_URL + "Club" + URL_PARAM);
		request.addHeader("Accept", "application/json");
		request.addHeader("Content-Type", "application/json");
		StringEntity entity = new StringEntity(
				"{\"Club\": {\"OrganizationId\": \"ORG1\", \"Country\": \"Netherlands\" , \"OrganizationType\": \"Org1\" }}",
				"UTF-8");
		entity.setContentType("application/json");
		request.setEntity(entity);
		HttpResponse response = httpclient.execute(request);
		Assert.assertEquals(200, response.getStatusLine().getStatusCode());
		request.releaseConnection();
	}

	public void removeClub1() throws ClientProtocolException, IOException {
		HttpDelete request = new HttpDelete(HTTP_REST_URL + "Club" + URL_PARAM
				+ "OrganizationId=ORG1");
		request.addHeader("Accept", "application/json");
		request.addHeader("Content-Type", "application/json");
		HttpResponse response = httpclient.execute(request);

		Assert.assertTrue(response.getStatusLine().getStatusCode() == 200
				|| response.getStatusLine().getStatusCode() == EntityException.ENTITY_NOT_FOUND);

		request.releaseConnection();
	}

	public void insertClub2() throws ClientProtocolException, IOException {
		HttpPut request = new HttpPut(HTTP_REST_URL + "Club" + URL_PARAM);
		request.addHeader("Accept", "application/json");
		request.addHeader("Content-Type", "application/json");
		StringEntity entity = new StringEntity(
				"{\"Club\": {\"OrganizationId\": \"ORG2\", \"Country\": \"Netherlands\" , \"OrganizationType\": \"Org2\" }}",
				"UTF-8");
		entity.setContentType("application/json");
		request.setEntity(entity);
		HttpResponse response = httpclient.execute(request);
		Assert.assertTrue(response.getStatusLine().getStatusCode() == 200);
		request.releaseConnection();
	}

	public void removeClub2() throws ClientProtocolException, IOException {
		HttpDelete request = new HttpDelete(HTTP_REST_URL + "Club" + URL_PARAM
				+ "OrganizationId=ORG2");
		request.addHeader("Accept", "application/json");
		request.addHeader("Content-Type", "application/json");
		HttpResponse response = httpclient.execute(request);

		Assert.assertTrue(response.getStatusLine().getStatusCode() == 200
				|| response.getStatusLine().getStatusCode() == EntityException.ENTITY_NOT_FOUND);

		request.releaseConnection();
	}

}
