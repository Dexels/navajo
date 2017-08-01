package com.dexels.navajo.document.json.conversion.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.json.conversion.JsonTmlFactory;
import com.dexels.replication.api.ReplicationMessage;
import com.dexels.replication.factory.ReplicationFactory;
import com.dexels.replication.impl.json.JSONReplicationMessageParserImpl;
import com.dexels.replication.impl.protobuf.FallbackReplicationMessageParser;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class TestProtobufConversion {

	@Before
	public void setup() {
		ReplicationFactory.setInstance(new FallbackReplicationMessageParser());
	}
	

	@Test
	public void testConversion() throws JsonGenerationException, JsonMappingException, IOException {
		InputStream resource = TestProtobufConversion.class.getResourceAsStream("prototest.proto");
//		System.err.println("MMM: "+m.toString());
		ReplicationMessage rmsg = ReplicationFactory.getInstance().parseStream(resource);
		System.err.println("Rescribe: "+new String(rmsg.toBytes(new JSONReplicationMessageParserImpl())));
		Navajo rr = JsonTmlFactory.getInstance().toReplicationNavajo(rmsg, "Tenant", "Table",Optional.of("Datasource"));
		rr.write(System.out);
		Assert.assertEquals(18, rr.getMessage("Transaction/Columns").getArraySize());
	}

	

}
