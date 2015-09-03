package com.dexels.navajo.elasticsearch.impl;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.elasticsearch.ElasticSearchFactory;
import com.dexels.navajo.elasticsearch.ElasticSearchService;

public class ElasticSearchComponent implements ElasticSearchService {

	private final static Logger logger = LoggerFactory
			.getLogger(ElasticSearchComponent.class);
	
	private CloseableHttpClient httpclient;

	private String url;
	private String index;
	private String id_property;

	private String type;
	private final ObjectMapper objectMapper = new ObjectMapper();
	
	public void activate(Map<String,Object> settings) {
		httpclient = HttpClients.createDefault();
		this.url = (String)settings.get("url");
		this.index = (String)settings.get("index");
		this.type = (String)settings.get("type");
		this.id_property = (String)settings.get("id_property");
		ElasticSearchFactory.setInstance(this);
	}
	
	public void deactivate() {
		ElasticSearchFactory.setInstance(null);
		if(httpclient!=null) {
			try {
				httpclient.close();
			} catch (IOException e) {
				logger.error("Error: ", e);
			}
		}

	}
	@Override
	public void insert(Message m) throws IOException {
		ObjectNode mm = (ObjectNode) messageToJSON(m);
		try {
			putJSON(mm);
		} catch (URISyntaxException e) {
			throw new IOException("Error putting to URI",e);
		}
	}
	


	public JsonNode messageToJSON(Message message) {
		if (Message.MSG_TYPE_ARRAY.equals(message.getType())) {
			List<Message> msgs = message.getAllMessages();
			ArrayNode an = objectMapper.createArrayNode();
			for (Message m : msgs) {
				an.add(messageToJSON(m));
			}
			return an;
		} else {
			List<Message> msgs = message.getAllMessages();
			ObjectNode an = objectMapper.createObjectNode();
			for (Message m : msgs) {
				an.put(m.getName(), messageToJSON(m));
			}
			List<Property> properties = message.getAllProperties();
			for (Property property : properties) {
				setPropertyValue(an,property,objectMapper);
			}
			return an;
		}

	}

	private void setPropertyValue(ObjectNode an, Property property,ObjectMapper objectMapper) {
		if(Property.DATE_PROPERTY.equals(property.getType())) {
			Date d = (Date) property.getTypedValue();
			
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

	
	private void putJSON(ObjectNode node) throws ClientProtocolException, IOException, URISyntaxException {
		String id = node.get(this.id_property).asText();
		HttpPut httpPut = new HttpPut(assembleURI(id));
		byte[] requestBytes = objectMapper.writer().withDefaultPrettyPrinter().writeValueAsBytes(node);
		System.err.println("Request: \n"+new String(requestBytes));
		HttpEntity he = new ByteArrayEntity(requestBytes);
		httpPut.setEntity(he);
		CloseableHttpResponse response1 = httpclient.execute(httpPut);
		HttpEntity respe = response1.getEntity();
		String result = EntityUtils.toString(respe);
		System.err.println("Response: " + result);
		// response1.getEntity().getContent()
		response1.close();
	}

	private URI assembleURI(String id) throws URISyntaxException {
		StringBuilder sb = new StringBuilder(url);
		if(!url.endsWith("/")) {
			sb.append("/");
		}
		sb.append(index);
		if(!index.endsWith("/")) {
			sb.append("/");
		}
		sb.append(type);
		if(!type.endsWith("/")) {
			sb.append("/");
		}
		sb.append(id);
		System.err.println("URI: "+sb.toString());
		return new URI(sb.toString());
	}

}
