package com.dexels.navajo.tipi.dev.server;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;


public class GitHubServlet extends HttpServlet implements Servlet {


	private static final long serialVersionUID = -4415777130543523033L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		if(!checkGitHubIpRange(req)) {
			resp.sendError(400);
		}
		ObjectMapper mapper = new ObjectMapper();
		JsonFactory factory = mapper.getJsonFactory(); // since 2.1 use mapper.getFactory() instead
		JsonParser jp = factory.createJsonParser(req.getReader());
		JsonNode node = mapper.readTree(jp);		

		process(mapper, node);
	}

	private void process(ObjectMapper mapper,
			JsonNode node) throws IOException, JsonGenerationException,
			JsonMappingException {
		String name = node.get("repository").get("name").asText();
		System.err.println(">>>>> "+name);
		Iterator<JsonNode> commits = node.get("commits").getElements();

		ObjectWriter writer = mapper.writer().withDefaultPrettyPrinter();
		List<String> paths = new ArrayList<String>();
		while (commits.hasNext()) {
			JsonNode commit = commits.next();
			final Iterator<JsonNode> modified = commit.get("modified").getElements();
			while(modified.hasNext()) {
				final String text = modified.next().asText();
				System.err.println("it: "+text);
				paths.add(text);
			}
			final Iterator<JsonNode> added = commit.get("added").getElements();
			while(added.hasNext()) {
				final String text = added.next().asText();
				System.err.println("added: "+text);
				paths.add(text);
			}
			final Iterator<JsonNode> removed = commit.get("removed").getElements();
			while(removed.hasNext()) {
				final String text = removed.next().asText();
				System.err.println("removed: "+text);
				paths.add(text);
			}

			writer.writeValue(System.err, commit);
			
		}
//		for (JsonNode commit : commits) {
//			paths.addAll(commit.findValuesAsText("added"));
//			paths.addAll(commit.findValuesAsText("removed"));
//			paths.addAll(commit.findValuesAsText("modified"));
////			System.err.println(">>>>>>>>>>>>>>>>>>><<>>"+commit.findValues("modified"));
//		}
		System.err.println("nodes: "+paths);
//		writer.writeValue(System.err, node);
	}
	
	public static void main(String[] args) throws IOException {
		
//		Reader fis = new FileReader("/Users/frank/git/navajo/tipi_dev/com.dexels.navajo.tipi.dev.store/json_test/single_tipi_edit.json");
		Reader fis = new FileReader("/Users/frank/git/navajo/tipi_dev/com.dexels.navajo.tipi.dev.store/json_test/add_many_files.json");
		ObjectMapper mapper = new ObjectMapper();
		JsonFactory factory = mapper.getJsonFactory(); // since 2.1 use mapper.getFactory() instead
		JsonParser jp = factory.createJsonParser(fis);
		JsonNode node = mapper.readTree(jp);		

		GitHubServlet ghs = new GitHubServlet();
		ghs.process(mapper, node);
		fis.close();
	}

	private boolean checkGitHubIpRange(HttpServletRequest req) {
		//The Public IP addresses for these hooks are: 204.232.175.64/27, 192.30.252.0/22.
		return true;
	}
}
