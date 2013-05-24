package com.dexels.navajo.article.client;

import java.io.IOException;
import java.io.StringWriter;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;

public class ArticleClient {
	
	private URL base;
	private ObjectNode meta;
	private ObjectMapper mapper = new ObjectMapper();
	private String username = "@BBFW06E";
	private String token = "epPm/1NY+hygYpOpGDHB2aMEjSJ558CNywPLwd/KA4nOV4Fkh9vtuSJGQZqsEJDpMdf/MhkOcqNO6LGdYH4tWxBIZo3niZKOkK+6gD61U8g=";
	private long started = System.currentTimeMillis();
	private int count = 0;
	private ExecutorService executor = Executors.newSingleThreadExecutor();
//	private ExecutorService executor = Executors.newCachedThreadPool();
	
	public ArticleClient() throws IOException {
		base = new URL("http://sportlink.com/api/");
		meta = (ObjectNode)getJSONFromURL("list");
		System.err.println("# of articles: "+meta.size());
	}
	
	private Iterator<String> getArticleNames() {
		return meta.getFieldNames();
	}

	private JsonNode getJSONFromURL(String item) throws IOException {
		return getJSONFromURL(item, Collections.EMPTY_MAP);
	}

	public ObjectNode getArticleMeta(String name) {
		return (ObjectNode) meta.get(name);
	}

	public List<ObjectNode> getArticleArguments(String name) {
		ObjectNode art = getArticleMeta(name);
		ArrayNode input =  (ArrayNode) art.get("input");
		if(input == null) {
			return Collections.EMPTY_LIST;
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
		count++;
		return mapper.readTree(url);
	}
	
	
	public Set<String> getParameterValues(String article, String key) throws IOException {
		ArrayNode an = (ArrayNode) getJSONFromURL(article);
		Set<String> result = new HashSet<String>();
		log(an);
		System.err.println("article: "+article+" key: "+key);
		for (JsonNode jsonNode : an) {

			checkResult(article,jsonNode);
			
			
			String value = ((ObjectNode)jsonNode).get(key).getTextValue();
			result.add(value);
		}
		return result;
	}

	protected void log(JsonNode an) throws IOException,
			JsonGenerationException, JsonMappingException {
		ObjectWriter writer = mapper.writer().withDefaultPrettyPrinter();
		StringWriter sw = new StringWriter();
		writer.writeValue(sw, an);
		System.err.println(sw.toString());
	}
	
	private void testAll() throws IOException {
		Iterator<String> names = getArticleNames();
		while (names.hasNext()) {
			String c = names.next();
			System.err.println(">> "+c);
			testArticle(c);
			
		}
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
							e.printStackTrace();
						} catch (JsonMappingException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					
				};
				executor.submit(r);
			}
		}
	}
	

//	private List<Map<String,String>> flatten(Map<String,Set<String>> res) {
//		return flatten(res,new HashSet<String>());
//	}
//	
//	private List<Map<String,String>> flatten(Map<String,Set<String>> res, Set<String> fixed) {
//		List<Map<String,String>> result = new LinkedList<Map<String,String>>();
//		Map<String,String> current = new HashMap<String, String>();
//		Set<String> params = res.keySet();
//		for (String param : params) {
//			Set<String> options = res.get(param);
//		}
//		return result;
//	}

	protected void checkResult(String article, JsonNode articleResult) throws IOException,
			JsonGenerationException, JsonMappingException {
		log(articleResult);
	}

	protected Map<String,Set<String>> getArticleParameters(String c) throws IOException {
		final List<ObjectNode> args = getArticleArguments(c);
		if(args.isEmpty()) {
			return Collections.EMPTY_MAP;
		} else {
			Map<String, Set<String>> result = new HashMap<String, Set<String>>();
			for (ObjectNode arg : args) {
				JsonNode source = arg.get("sourcearticle");
				JsonNode sourcekey = arg.get("sourcekey");
				String argumentName = arg.get("name").asText();
				if(source!=null) {
					Set<String> ss = getParameterValues(source.asText(), sourcekey.asText());
					System.err.println("source possibilities: "+ss);
					result.put(argumentName, ss);
				}
			}
			return result;
		}
	}
	public static void main(String[] args) throws IOException, InterruptedException {
		ArticleClient ac = new ArticleClient();
		ac.testAll();
		ac.executor.shutdown();
		ac.executor.awaitTermination(1, TimeUnit.HOURS);
		long time = System.currentTimeMillis() - ac.started;
		System.err.println("time: "+(time)+" count: "+ac.count+" speed: "+(1.0*ac.count/time*1000)+" transactions / s");
	}
}
