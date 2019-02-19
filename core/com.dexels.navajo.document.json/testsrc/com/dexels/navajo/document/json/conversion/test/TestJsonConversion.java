package com.dexels.navajo.document.json.conversion.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.json.conversion.JsonTmlFactory;
import com.dexels.replication.api.ReplicationMessage;
import com.dexels.replication.factory.ReplicationFactory;
import com.dexels.replication.impl.json.JSONReplicationMessageParserImpl;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class TestJsonConversion {

	@Before
	public void setup() {
		ReplicationFactory.setInstance(new JSONReplicationMessageParserImpl());
	}
	

	@Test
	public void testConversion() throws JsonGenerationException, JsonMappingException, IOException {
		InputStream resource = TestJsonConversion.class.getResourceAsStream("testtml.xml");
		Navajo base = NavajoFactory.getInstance().createNavajo(resource);
		Message m = base.getMessage("Pool");
//		System.err.println("MMM: "+m.toString());
		ObjectNode on = JsonTmlFactory.getInstance().toNode(m, "ble");
		JSONReplicationMessageParserImpl parser = new JSONReplicationMessageParserImpl();
		ReplicationMessage rmsg = parser.parseJson(Optional.empty(), on);
		ObjectMapper mapper  = new ObjectMapper();
//		JsonNode n = rmsg.toJSON(mapper);
		System.err.println("Before:\n");
		mapper.writerWithDefaultPrettyPrinter().writeValue(System.err, on);
//		System.err.println("After:\n");
//		mapper.writerWithDefaultPrettyPrinter().writeValue(System.err, n);
		Optional<List<ImmutableMessage>> r = rmsg.subMessages("Standings");
//		System.err.println("Present? "+r.isPresent());
		Assert.assertTrue(r.isPresent());
		Assert.assertEquals(12, r.get().size());
		Navajo rr = JsonTmlFactory.getInstance().toReplicationNavajo(rmsg, "Tenant", "Table",Optional.of("Datasource"));
//		rr.write(System.out);
		Assert.assertEquals(10, rr.getMessage("Transaction/Columns").getArraySize());
	}

	

}
