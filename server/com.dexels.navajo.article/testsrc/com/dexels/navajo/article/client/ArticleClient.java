package com.dexels.navajo.article.client;

import java.io.IOException;
import java.io.StringWriter;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class ArticleClient {
	
	
	private final static Logger logger = LoggerFactory
			.getLogger(ArticleClient.class);
	private URL base;
	private ObjectNode meta;
	private ObjectMapper mapper = new ObjectMapper();
//	private String baseurl = "http://sportlink.com/api/";
	private String baseurl = "http://localhost:9090/article/";
	private String username = "@BBFW06E";
	private String token = "epPm/1NY+hygYpOpGDHB2aMEjSJ558CNywPLwd/KA4nOV4Fkh9vtuSJGQZqsEJDpMdf/MhkOcqNO6LGdYH4tWxBIZo3niZKOkK+6gD61U8g=";
//	private long started = System.currentTimeMillis();
//	private int count = 0;
	private ExecutorService executor = Executors.newSingleThreadExecutor();
//	private ExecutorService executor = Executors.newCachedThreadPool();
	
	public ArticleClient() throws IOException {
		base = new URL(baseurl);
		meta = (ObjectNode)getJSONFromURL("list");
	}
	
//	private Iterator<String> getArticleNames() {
//		return meta.fieldNames();
//	}

	private JsonNode getJSONFromURL(String item) throws IOException {
		return getJSONFromURL(item, Collections.<String, String> emptyMap());
	}

	public ObjectNode getArticleMeta(String name) {
		return (ObjectNode) meta.get(name);
	}

	public List<ObjectNode> getArticleArguments(String name) {
		ObjectNode art = getArticleMeta(name);
		ArrayNode input =  (ArrayNode) art.get("input");
		if(input == null) {
			return Collections.emptyList();
		}
		List<ObjectNode> lo = new LinkedList<ObjectNode>();
		for (JsonNode jsonNode : input) {
			lo.add((ObjectNode) jsonNode);
		}
		return lo;
	}
	
	
	private JsonNode getJSONFromURL(String item, Map<String,String> params) throws IOException {
		StringBuffer paramString = new StringBuffer("?username="+username+"&token="+token);
		if(!params.isEmpty()) {
			paramString.append("&");
		}
		for (Entry<String,String> e : params.entrySet()) {
			paramString.append(e.getKey());
			paramString.append("=");
			paramString.append(e.getValue());
			
		}
		final URL url = new URL(base,item+paramString);
//		count++;
		return mapper.readTree(url);
	}
	
	
	public Set<String> getParameterValues(String article, String key) throws IOException {
		ArrayNode an = (ArrayNode) getJSONFromURL(article);
		Set<String> result = new HashSet<String>();
		log(an);
		for (JsonNode jsonNode : an) {

			checkResult(article,jsonNode);
			
			
			String value = ((ObjectNode)jsonNode).get(key).textValue();
			result.add(value);
		}
		return result;
	}

	protected void log(JsonNode an) throws IOException,
			JsonGenerationException, JsonMappingException {
		ObjectWriter writer = mapper.writer().withDefaultPrettyPrinter();
		StringWriter sw = new StringWriter();
		writer.writeValue(sw, an);
	}
	

	protected void testArticle(final String article) throws IOException {
		Map<String,Set<String>> res = getArticleParameters(article);
		if(res.size()>1) {
			throw new RuntimeException("whoops");
		}
		
		for (Entry<String,Set<String>> argposs : res.entrySet()) {
			String name = argposs.getKey();
			Set<String> list = argposs.getValue();
			for (String possibility : list) {
				final Map<String,String> params = new HashMap<String, String>();
				params.put(name, possibility);
				Runnable r = new Runnable() {

					@Override
					public void run() {
						try {
							JsonNode articleResult = getJSONFromURL(article,params);
							checkResult(article,articleResult);
						} catch (JsonGenerationException e) {
							logger.error("Error: ", e);
						} catch (JsonMappingException e) {
							logger.error("Error: ", e);
						} catch (IOException e) {
							logger.error("Error: ", e);
						}
					}
					
				};
				executor.submit(r);
			}
		}
	}

	protected void checkResult(String article, JsonNode articleResult) throws IOException,
			JsonGenerationException, JsonMappingException {
		log(articleResult);
	}

	protected Map<String,Set<String>> getArticleParameters(String c) throws IOException {
		final List<ObjectNode> args = getArticleArguments(c);
		if(args.isEmpty()) {
			return Collections.emptyMap();
		} else {
			Map<String, Set<String>> result = new HashMap<String, Set<String>>();
			for (ObjectNode arg : args) {
				JsonNode source = arg.get("sourcearticle");
				JsonNode sourcekey = arg.get("sourcekey");
				String argumentName = arg.get("name").asText();
				if(source!=null) {
					Set<String> ss = getParameterValues(source.asText(), sourcekey.asText());
					result.put(argumentName, ss);
				}
			}
			return result;
		}
	}
}
