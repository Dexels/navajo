package com.dexels.navajo.document.json.conversion;

import java.util.Optional;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.replication.api.ReplicationMessage;
import com.fasterxml.jackson.databind.node.ObjectNode;

public interface JsonTmlConverter {

	public Navajo toReplicationNavajo(ReplicationMessage message, String tenant, String table, Optional<String> datasource);

	public ObjectNode toNode(Message m, String primaryKeys);
	
	public Message toMessage(String messageName, ImmutableMessage message, Navajo rootNavajo);

	public Navajo toFlatNavajo(String name, ReplicationMessage message);
	public Navajo toFlatNavajo(String name,ImmutableMessage message);

}