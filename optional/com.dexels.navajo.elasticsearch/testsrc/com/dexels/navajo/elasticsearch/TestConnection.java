package com.dexels.navajo.elasticsearch;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.elasticsearch.impl.ElasticSearchComponent;

public class TestConnection {

	private static Navajo tmlDoc;
	@BeforeClass
	public static void parseTml() throws IOException {
		System.err.println("parsing tml..");
		try(InputStream resourceAsStream = TestConnection.class.getClassLoader().getResourceAsStream("tmlexample.xml")) {
			tmlDoc = NavajoFactory.getInstance().createNavajo(resourceAsStream);
		}
	}
	@Test
	public void testInsert() throws IOException {
		ElasticSearchComponent esc = new ElasticSearchComponent();
		Map<String,Object> settings = new HashMap<>();
		settings.put("url", "http://cloud.sendrato.com:9200");
		settings.put("index", "sendrato");
		settings.put("type", "cashless");
		settings.put("id_property", "_id");

		esc.activate(settings);
		Message m = tmlDoc.getMessage("Transaction");
		for (Message e : m.getAllMessages()) {
			ElasticSearchFactory.getInstance().insert(e);
		}
	}

	@Test
	public void testMessageToJSON() throws JsonGenerationException, JsonMappingException, IOException {
		Message m = tmlDoc.getMessage("Transaction");
		ObjectMapper objectMapper = new ObjectMapper();
		ElasticSearchComponent e = new ElasticSearchComponent();
		ArrayNode nn = (ArrayNode) e.messageToJSON(m);
		objectMapper.writer().withDefaultPrettyPrinter().writeValue(System.err, nn);
		Assert.assertEquals(m.getArraySize(), nn.size());
	}
}
