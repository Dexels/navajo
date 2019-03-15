package com.dexels.navajo.document.json.conversion.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.immutable.api.ImmutableMessage;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.json.conversion.JsonTmlFactory;
import com.dexels.replication.api.ReplicationMessage;
import com.dexels.replication.factory.ReplicationFactory;
import com.dexels.replication.impl.json.JSONReplicationMessageParserImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class TestJsonConversion {

	
	private static final Logger logger = LoggerFactory.getLogger(TestJsonConversion.class);

	@Before
	public void setup() {
		ReplicationFactory.setInstance(new JSONReplicationMessageParserImpl());
	}
	

	@Test
	public void testConversion() throws IOException {
		InputStream resource = TestJsonConversion.class.getResourceAsStream("testtml.xml");
		Navajo base = NavajoFactory.getInstance().createNavajo(resource);
		Message m = base.getMessage("Pool");
		ObjectNode on = JsonTmlFactory.getInstance().toNode(m, "ble");
		JSONReplicationMessageParserImpl parser = new JSONReplicationMessageParserImpl();
		ReplicationMessage rmsg = parser.parseJson(Optional.empty(), on);
		ObjectMapper mapper  = new ObjectMapper();
		logger.info("Before:\n");
		StringWriter sw = new StringWriter();
		mapper.writerWithDefaultPrettyPrinter().writeValue(sw, on);
		logger.info("Value: {}",sw);
		Optional<List<ImmutableMessage>> r = rmsg.subMessages("Standings");
		Assert.assertTrue(r.isPresent());
		Assert.assertEquals(12, r.get().size());
		Navajo rr = JsonTmlFactory.getInstance().toReplicationNavajo(rmsg, "Tenant", "Table",Optional.of("Datasource"));
		Assert.assertEquals(10, rr.getMessage("Transaction/Columns").getArraySize());
	}

	

}
