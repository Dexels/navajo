import java.io.File;
import java.io.IOException;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.article.ArticleException;
import com.dexels.navajo.article.test.TestContextImpl;
import com.dexels.navajo.article.test.TestRuntimeImpl;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.script.api.FatalException;


public class TestRuntime {
	
	private final static Logger logger = LoggerFactory
			.getLogger(TestRuntime.class);
	
	TestContextImpl context = null;
	@Before
	public void setUp() throws Exception {
		this.context = new TestContextImpl("testresources/") {
			
			@Override
			public Navajo call(Navajo n) throws FatalException {
				logger.info("Calling: "+n.getHeader().getRPCName());
//				n.write(System.err);
				Navajo res = NavajoFactory.getInstance().createNavajo();
				
				return res;
			}
		};
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	
	public void testSearchClub() throws IOException, ArticleException {
		// this test is a bit outdated
		File art =context.resolveArticle("/searchclub");
		TestRuntimeImpl tr = new TestRuntimeImpl("searchclub",art);
		context.interpretArticle(art,tr);
		String out =tr.getOutput();
		System.err.println(out);
		  ObjectMapper mapper = new ObjectMapper();
		  
		  // (note: can also use more specific type, like ArrayNode or ObjectNode!)
		  JsonNode rootNode = mapper.readValue(out, JsonNode.class); 
//		  Assert.assertEquals(8, rootNode.get("result").get("Club").size());
		}

	@Test
	public void testVerjaardag() throws IOException, ArticleException {
		File art =context.resolveArticle("/verjaardagen");
		TestRuntimeImpl tr = new TestRuntimeImpl("verjaardagen",art);
		context.interpretArticle(art,tr);
		String out =tr.getOutput();
		System.err.println(out);
		  ObjectMapper mapper = new ObjectMapper();
		  
		  // (note: can also use more specific type, like ArrayNode or ObjectNode!)
		  JsonNode rootNode = mapper.readValue(out, JsonNode.class); 
		    ObjectWriter writer = mapper.writer().withDefaultPrettyPrinter();
		    System.out.println(writer.writeValueAsString(rootNode));
			  Assert.assertEquals(24, rootNode.get("data").get("verjaardagen").size());

	}

	@Test
	public void testTeams() throws IOException, ArticleException {
		File art =context.resolveArticle("/teams");
		TestRuntimeImpl tr = new TestRuntimeImpl("teams",art);
		context.interpretArticle(art,tr);
		String out =tr.getOutput();
		System.err.println(out);
		  ObjectMapper mapper = new ObjectMapper();
		  
		  // (note: can also use more specific type, like ArrayNode or ObjectNode!)
		  JsonNode rootNode = mapper.readValue(out, JsonNode.class); 
		    ObjectWriter writer = mapper.writer().withDefaultPrettyPrinter();
		    System.out.println(writer.writeValueAsString(rootNode));
//			  Assert.assertEquals(24, rootNode.get("data").get("verjaardagen").size());

	}

	//	public void
}
