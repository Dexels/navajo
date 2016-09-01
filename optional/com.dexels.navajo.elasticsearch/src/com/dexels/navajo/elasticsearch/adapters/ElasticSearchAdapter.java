package com.dexels.navajo.elasticsearch.adapters;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.http.client.ClientProtocolException;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.index.api.IndexingServiceFactory;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.Mappable;
import com.dexels.navajo.script.api.MappableException;
import com.dexels.navajo.script.api.UserException;

public class ElasticSearchAdapter implements Mappable {

//	private String name = null;
	private String messagePath = null;
	private Access access = null;
	
	private String id_property = "_id";
	private final ObjectMapper objectMapper = new ObjectMapper();
	DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
	
	private final static Logger logger = LoggerFactory.getLogger(ElasticSearchAdapter.class);

	
	public void setMessagePath(String messagePath) {
		this.messagePath = messagePath;
	}
//
//	public void setName(String name) {
//		this.name = name;
//	}

	@Override
	public void load(Access access) throws MappableException, UserException {
		this.access = access;
	}

	@Override
	public void store() throws MappableException, UserException {
		Message m = access.getInDoc().getMessage(messagePath);
		try {
			insert(m);
		} catch (IOException e) {
			throw new UserException("Trouble pushing to elasticsearch", e);
		}
		
	}

	private void insert(Message m) throws IOException {
		ObjectNode mm = (ObjectNode) messageToJSON(m);
		mm.put("@timestamp", df.format(new Date()));

		String id = m.getProperty(this.id_property).getValue();
		try {
			putJSON(id,mm);
		} catch (URISyntaxException e) {
			throw new IOException("Error putting to URI",e);
		}
	}
	
	private JsonNode messageToJSON(Message message) {
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
				if(!property.getName().equals(id_property)) {
					setPropertyValue(an,property,objectMapper);
				} else {
					Property copy = property.copy(null);
					copy.setName("id");
					setPropertyValue(an,copy,objectMapper);
					
				}
			}
			
			an.put("@timestamp", df.format(new Date()));
			return an;
		}

	}

	private void setPropertyValue(ObjectNode an, Property property,ObjectMapper objectMapper) {
		if(Property.DATE_PROPERTY.equals(property.getType())) {
			Date d = (Date) property.getTypedValue();
			
			
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

	
	private void putJSON(String id, ObjectNode node) throws ClientProtocolException, IOException, URISyntaxException {
	    replaceDots(node);
	    String requestString = objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(node);
	    logger.info("Inserted into redis: \n{}",requestString);
		IndexingServiceFactory.getInstance().insertJson("sendrato",requestString);
	}
	
	 private void replaceDots(ObjectNode mm) {
	        Iterator<Entry<String, JsonNode>> it = mm.getFields();
	        Set<String> fieldsWithDots = new HashSet<>();
	        Set<String> nestedFields = new HashSet<>();
	        
	        while (it.hasNext()) {
	            Entry<String, JsonNode> entry = it.next();
	            if (entry.getValue().size() > 0)  {
	                nestedFields.add(entry.getKey());
	            }
	            if (entry.getKey().contains(".")) {
	                fieldsWithDots.add(entry.getKey());
	            }
	        }
	        for (String key : nestedFields) {
	            replaceDots((ObjectNode) mm.get(key));
	        }
	        for (String key : fieldsWithDots) {
	            String newKey = key.replace(".", "_");
	            JsonNode value = mm.get(key);
	            mm.put(newKey, value);
	            mm.remove(key);
	        }
	    }

	@Override
	public void kill() {
		// TODO Auto-generated method stub
		
	}
}
