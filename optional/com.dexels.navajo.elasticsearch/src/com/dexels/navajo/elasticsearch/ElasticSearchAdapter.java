package com.dexels.navajo.elasticsearch;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.types.Binary;

public class ElasticSearchAdapter {

	public static void main(String[] args) throws ClientProtocolException,
			IOException {
		CloseableHttpClient httpclient = HttpClients.createDefault();

		callPut(httpclient);
		callGet(httpclient);
		httpclient.close();
		// try {
		// System.out.println(response1.getStatusLine());
		// HttpEntity entity1 = response1.getEntity();
		// // do something useful with the response body
		// // and ensure it is fully consumed
		// EntityUtils.consume(entity1);
		// } finally {
		// response1.close();
		// }

	}

	private static void callGet(CloseableHttpClient httpclient)
			throws IOException, ClientProtocolException {
		HttpGet httpGet = new HttpGet(
				"http://docker.local:9200/sendrato/cashless/1");

		CloseableHttpResponse response1 = httpclient.execute(httpGet);
		HttpEntity he = response1.getEntity();
		String result = EntityUtils.toString(he);
		System.err.println("Response: " + result);
		// response1.getEntity().getContent()
		response1.close();
	}

	private static void callPut(CloseableHttpClient httpclient)
			throws IOException, ClientProtocolException {
		HttpPut httpPut = new HttpPut(
				"http://docker.local:9200/test/cashless/1");

		String json = "{\"aap\": \"noot\",\"post_date\" : \"2014-11-15T14:12:12\"}";
		HttpEntity he = new ByteArrayEntity(json.getBytes());
		httpPut.setEntity(he);
		CloseableHttpResponse response1 = httpclient.execute(httpPut);
		HttpEntity respe = response1.getEntity();
		String result = EntityUtils.toString(respe);
		System.err.println("Response: " + result);
		// response1.getEntity().getContent()
		response1.close();
	}


	public static JsonNode messageToJSON(Message message,
			ObjectMapper objectMapper) {
		if (Message.MSG_TYPE_ARRAY.equals(message.getType())) {
			List<Message> msgs = message.getAllMessages();
			ArrayNode an = objectMapper.createArrayNode();
			for (Message m : msgs) {
				an.add(messageToJSON(m, objectMapper));
			}
			return an;
		} else {
			List<Message> msgs = message.getAllMessages();
			ObjectNode an = objectMapper.createObjectNode();
			for (Message m : msgs) {
				an.put(m.getName(), messageToJSON(m, objectMapper));
			}
			List<Property> properties = message.getAllProperties();
			for (Property property : properties) {
				setPropertyValue(an,property,objectMapper);
			}
			return an;
		}

	}

	private static void setPropertyValue(ObjectNode an, Property property,ObjectMapper objectMapper) {
		if(Property.DATE_PROPERTY.equals(property.getType())) {
			Date d = (Date) property.getTypedValue();
//			DateTimeFormatter fmt = ISODateTimeFormat.dateTime();
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
			an.put(property.getName(),df.format(d));
		} else if(Property.INTEGER_PROPERTY.equals(property.getType())) {
			Integer d = (Integer) property.getTypedValue();
			an.put(property.getName(),d);
		} else if(Property.BOOLEAN_PROPERTY.equals(property.getType())) {
			Boolean d = (Boolean) property.getTypedValue();
			an.put(property.getName(),d);
		} else if(Property.BINARY_PROPERTY.equals(property.getType())) {
			Binary b = (Binary) property.getTypedValue();
			an.put(property.getName(),b.getData());
		} else {
			an.put(property.getName(), property.getValue());
		}
		
	}

	private static void insert(CloseableHttpClient httpclient, String baseurl,
			String index, String type, String id, JsonNode content)
			throws JsonGenerationException, JsonMappingException, IOException {
		String url = baseurl + index + type + id;
		HttpPut httpPut = new HttpPut(url);
		ObjectMapper om = new ObjectMapper();
		byte[] requestBody = om.writeValueAsBytes(content);
		HttpEntity he = new ByteArrayEntity(requestBody);
		httpPut.setEntity(he);
		CloseableHttpResponse response1 = httpclient.execute(httpPut);
		HttpEntity respe = response1.getEntity();
		String result = EntityUtils.toString(respe);
		System.err.println("Response: " + result);
		// response1.getEntity().getContent()
		response1.close();
	}
}
