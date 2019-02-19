package com.dexels.navajo.document.json.conversion.impl;

import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.json.conversion.JsonTmlConverter;
import com.dexels.navajo.document.types.ClockTime;
import com.dexels.replication.api.ReplicationMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class JsonTmlConverterImpl implements JsonTmlConverter {

	
	private final static Logger logger = LoggerFactory.getLogger(JsonTmlConverterImpl.class);
	private final ObjectMapper objectMapper = new ObjectMapper();
	

	@Override
	public Navajo toFlatNavajo(String name,ImmutableMessage message) {
		Navajo rootNavajo = NavajoFactory.getInstance().createNavajo();
		Message root = toMessage(name, message, rootNavajo);
		rootNavajo.addMessage(root);
		return rootNavajo;
	}
	
	@Override
    public Navajo toFlatNavajo(String name, ReplicationMessage message) {
        Navajo rootNavajo = NavajoFactory.getInstance().createNavajo();
        Message root = toMessage(name, message.message(), rootNavajo);
        Property timestamp = NavajoFactory.getInstance().createProperty(rootNavajo, "Timestamp", 
                Property.LONG_PROPERTY,  message.timestamp()+"", 0, "", Property.DIR_OUT);
        root.addProperty(timestamp);
        rootNavajo.addMessage(root);
        return rootNavajo;
    }

	@Override
	public Message toMessage(String messageName, ImmutableMessage message, Navajo rootNavajo) {
		Message cV =  NavajoFactory.getInstance().createMessage(rootNavajo, messageName);
				
		for (String columnName : message.columnNames()) {
			String type = message.columnType(columnName);
			Object value = message.value(columnName).orElse(null);
			Property colProp = NavajoFactory.getInstance().createProperty(rootNavajo, columnName, 
					type, null, 0, "", Property.DIR_OUT);
			switch (type) {
			case Property.CLOCKTIME_PROPERTY:
				if (value != null) {
					ClockTime ct = new ClockTime((Date)value);
					colProp.setAnyValue(ct);
				}
				colProp.setType(type);
				break;

			default:
				colProp.setAnyValue(value);
				colProp.setType(type);
				break;
			}
			cV.addProperty(colProp);
		}
		for (Entry<String,List<ImmutableMessage>> e : message.subMessageListMap().entrySet()) {
			Message subArrayMessage =  NavajoFactory.getInstance().createMessage(rootNavajo, e.getKey(),Message.MSG_TYPE_ARRAY);
			cV.addMessage(subArrayMessage);
			for (ImmutableMessage elt : e.getValue()) {
				Message msgElt =  toMessage(e.getKey(), elt, rootNavajo);
				subArrayMessage.addElement(msgElt);
			}
		} 
		for (Entry<String,ImmutableMessage> e : message.subMessageMap().entrySet()) {
			Message msgElt =  toMessage(e.getKey(), e.getValue(), rootNavajo);
			cV.addMessage(msgElt);
		}
		return cV;
		
	}
	/* (non-Javadoc)
	 * @see com.dexels.navajo.document.json.impl.JsonTmlConferter#toNavajo(com.dexels.replication.api.ReplicationMessage, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public Navajo toReplicationNavajo(ReplicationMessage message, String tenant, String table, Optional<String> datasource) {
			try {
				Navajo n = NavajoFactory.getInstance().createNavajo();
				Message msg = createTransactionMessage(message, tenant, table, datasource, n);
				
				n.addMessage(msg);
				
				// Add PK values.
				Message pM = createPrimaryKeyMessage(message, n);
				msg.addMessage(pM);
				
				// Add column values.
				Message cV = createColumnsMessage(message, n);
				msg.addMessage(cV);

				
				// Do we need submessages here?
//				Message subMsg =  NavajoFactory.getInstance().createMessage(n, "SubMessage");
//				n.addMessage(subMsg);
//				Map<String,List<ReplicationMessage>> subMessages = message.subMessageListMap();
//				for (Entry<String,List<ReplicationMessage>> e : subMessages.entrySet()) {
//					Message msgg =  NavajoFactory.getInstance().createMessage(n, e.getKey(),Message.MSG_TYPE_ARRAY);
//					for (ReplicationMessage rr : e.getValue()) {
//					}
//				}
				return n;
			} catch (Exception e) {
				logger.error("Error: ",e);
			}
			return null;
	}


	private Message createColumnsMessage(ReplicationMessage message, Navajo n) {
		Message cV =  NavajoFactory.getInstance().createMessage(n, "Columns",Message.MSG_TYPE_ARRAY);
		
		for (String columnName : message.columnNames()) {
			
			Message vM =  NavajoFactory.getInstance().createMessage(n, "Columns");
			cV.addElement(vM);
			
			Object value = message.columnValue(columnName);
			Property colPropName = NavajoFactory.getInstance().createProperty(n, "Name", 
					Property.STRING_PROPERTY, columnName, 0, "", Property.DIR_OUT);
			Property colPropValue = NavajoFactory.getInstance().createProperty(n, "Value", 
					Property.STRING_PROPERTY, "", 0, "", Property.DIR_OUT);
			colPropValue.setAnyValue(value);
			
			vM.addProperty(colPropName);
			vM.addProperty(colPropValue);
		}
		return cV;
	}


	private Message createPrimaryKeyMessage(ReplicationMessage message, Navajo n) {
		Message pM =  NavajoFactory.getInstance().createMessage(n, "PrimaryKey");
		pM.setType(Message.MSG_TYPE_ARRAY);
		
		
		for (String primaryKey  : message.primaryKeys()) {
			
			Message vM =  NavajoFactory.getInstance().createMessage(n, "PrimaryKey");
			pM.addElement(vM);
			
			Object value = message.columnValue(primaryKey);
			Property colPropName = NavajoFactory.getInstance().createProperty(n, "Name", 
					Property.STRING_PROPERTY, primaryKey, 0, "", Property.DIR_OUT);
			Property colPropValue = NavajoFactory.getInstance().createProperty(n, "Value", 
					Property.STRING_PROPERTY, "", 0, "", Property.DIR_OUT);
			colPropValue.setAnyValue(value);
			
			vM.addProperty(colPropName);
			vM.addProperty(colPropValue);
			
		}
		return pM;
	}


	private Message createTransactionMessage(ReplicationMessage message, String tenant, String table, Optional<String> datasource,
			Navajo n) {
		String transactionId = message.transactionId();
		Message msg = NavajoFactory.getInstance().createMessage(n, "Transaction");
		Property transactionIdProp = NavajoFactory.getInstance().createProperty(n, "TransactionId", 
				Property.STRING_PROPERTY, transactionId, 0, "", Property.DIR_OUT);
		Property timestamp = NavajoFactory.getInstance().createProperty(n, "Timestamp", 
				Property.LONG_PROPERTY,  message.timestamp()+"", 0, "", Property.DIR_OUT);
		Property dbOperation = NavajoFactory.getInstance().createProperty(n, "Operation", 
				Property.STRING_PROPERTY, message.operation().toString(), 0, "", Property.DIR_OUT);
		Property tenantProp = NavajoFactory.getInstance().createProperty(n, "Tenant", 
				Property.STRING_PROPERTY, tenant, 0, "", Property.DIR_OUT);
		Property tableProp = NavajoFactory.getInstance().createProperty(n, "SourceTable", 
				Property.STRING_PROPERTY, table, 0, "", Property.DIR_OUT);

		
		Property status = NavajoFactory.getInstance().createProperty(n, "Status", 
				Property.STRING_PROPERTY, "PENDING", 0, "", Property.DIR_OUT);
		
		msg.addProperty(transactionIdProp);
		msg.addProperty(tenantProp);
		msg.addProperty(tableProp);
		if(datasource.isPresent()) {
			Property source = NavajoFactory.getInstance().createProperty(n, "DataSource", 
					Property.STRING_PROPERTY, datasource.get(), 0, "", Property.DIR_OUT);			
			msg.addProperty(source);
		}
		msg.addProperty(timestamp);
		msg.addProperty(dbOperation);
		msg.addProperty(status);
		return msg;
	}

	private ObjectNode createTopLevel(String primaryKeys) {
		ObjectNode result = objectMapper.createObjectNode();
		result.put("Timestamp", new Date().getTime());

		ArrayNode pks = objectMapper.createArrayNode();
		result.set("PrimaryKeys", pks);
		if(primaryKeys!=null) {
			StringTokenizer st = new StringTokenizer(primaryKeys,",");
			while (st.hasMoreTokens()) {
				pks.add(st.nextToken());
			}		
		}
		return result;
	}

	private ObjectNode ensureSub(String name, ObjectNode result) {
		ObjectNode nn = (ObjectNode) result.get(name);
		if(nn==null) {
			ObjectNode submessages = objectMapper.createObjectNode();
			result.set(name, submessages);
			return submessages;
		} else {
			return nn;
		}
	}
	
	
	/* (non-Javadoc)
	 * @see com.dexels.navajo.document.json.impl.JsonTmlConferter#toNode(com.dexels.navajo.document.Message, java.lang.String)
	 */
	@Override
	public ObjectNode toNode(Message m, final String primaryKeys) {
		ObjectNode result = createTopLevel(primaryKeys);
		ObjectNode columnValuesList = objectMapper.createObjectNode();
		result.set("Columns", columnValuesList);
		List<Property> allProperties = m.getAllProperties();
		if(allProperties!=null) {
			for (Property p : allProperties) {
				ObjectNode o = objectMapper.createObjectNode();
				o.put("Type", p.getType());
				o.put("Value",p.getValue());
				columnValuesList.set(p.getName(),o);
			}
		}
		for (Message submessage : m.getAllMessages()) {
			String name = submessage.getName();
			if (Message.MSG_TYPE_ARRAY.equals((submessage.getType()))) {
				ArrayNode node = objectMapper.createArrayNode();
				for (Message element : submessage.getAllMessages()) {
					node.add(toNode(element,primaryKeys));
				}
				ObjectNode submessages = ensureSub("SubMessage",result);
				submessages.set(name, node);
			} else {
				ObjectNode submsg = ensureSub("SubMessage",result);
				submsg.set(name,toNode(submessage,primaryKeys));
			}
		}
		return result;
	}




}
