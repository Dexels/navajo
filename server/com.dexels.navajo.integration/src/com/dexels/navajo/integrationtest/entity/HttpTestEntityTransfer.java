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
import org.junit.Before;
import org.junit.Test;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.json.JSONTML;
import com.dexels.navajo.document.json.JSONTMLFactory;




public class HttpTestEntityTransfer extends HttpTestSetup{
	
	@Test
	public void testPutTransfer1() throws IOException {
		NavajoFactory f = NavajoFactory.getInstance();
		Navajo n = f.createNavajo();
		Message transfer1 = f.createMessage(n, "Transfer");
		transfer1.addProperty(f.createProperty(n, "TransferId", Property.STRING_PROPERTY, "T1", 0, "", ""));
		transfer1.addProperty(f.createProperty(n, "Player", Property.STRING_PROPERTY, player1.getProperty("PersonId").toString(), 0, "", ""));
		transfer1.addProperty(f.createProperty(n, "ClubOld", Property.STRING_PROPERTY, club1.getProperty("OrganizationId").toString(), 0, "", ""));
		transfer1.addProperty(f.createProperty(n, "ClubNew", Property.STRING_PROPERTY, club2.getProperty("OrganizationId").toString(), 0, "", ""));

		
		HttpPut request = new HttpPut(HTTP_REST_URL + "Transfer" + URL_PARAM);
		

		StringWriter writer = new StringWriter();
		transfer1.writeSimpleJSON(writer);
		StringEntity entity = new StringEntity(writer.toString(), "UTF-8");
		entity.setContentType("application/json");
		request.setEntity(entity);
		try {
			HttpResponse response = httpclient.execute(request);
			Assert.assertEquals(200, response.getStatusLine().getStatusCode());
			request.releaseConnection();
		} finally {
			HttpDelete request2 = new HttpDelete(HTTP_REST_URL + "Transfer" + URL_PARAM + "TransferId="
					+ transfer1.getProperty("TransferId").toString());
			request.addHeader("Accept", "application/json");
			request.addHeader("Content-Type", "application/json");
			HttpResponse response = httpclient.execute(request2);
			Assert.assertTrue(response.getStatusLine().getStatusCode() == 200);
			request2.releaseConnection();
		}
		
	}

	
	
	
	
}

