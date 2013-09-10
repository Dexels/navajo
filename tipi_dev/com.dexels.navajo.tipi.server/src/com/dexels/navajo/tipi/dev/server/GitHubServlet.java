package com.dexels.navajo.tipi.dev.server;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.dev.server.appmanager.AppStoreOperation;
import com.dexels.navajo.tipi.dev.server.appmanager.ApplicationManager;
import com.dexels.navajo.tipi.dev.server.appmanager.ApplicationStatus;



public class GitHubServlet extends HttpServlet implements Servlet {


private final static Logger logger = LoggerFactory
		.getLogger(GitHubServlet.class);

	private static final long serialVersionUID = -4415777130543523033L;

	private final Map<String,AppStoreOperation> operations = new HashMap<String,AppStoreOperation>();
	private final Map<String,ApplicationStatus> applications = new HashMap<String,ApplicationStatus>();
	private final Map<ApplicationStatus,Map<String,Object>> applicationSettings = new HashMap<ApplicationStatus,Map<String,Object>>();

	private ApplicationManager applicationManager;
	
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


	public void addApplicationStatus(ApplicationStatus a,Map<String,Object> settings) {
		applications.put(a.getApplicationName(), a);
		applicationSettings.put(a, settings);
	}
	
	public void removeApplicationStatus(ApplicationStatus a) {
		applications.remove(a.getApplicationName());
		applicationSettings.remove(a);
	}
	
	public void addOperation(AppStoreOperation a,Map<String,Object> settings) {
		operations.put((String) settings.get("component.name"), a);
	}
	
	public void removeOperation(ApplicationStatus a,Map<String,Object> settings) {
		operations.remove((String) settings.get("component.name"));
	}
	
	public void setApplicationManager(ApplicationManager am) {
		this.applicationManager = am;
	}

	public void clearApplicationManager(ApplicationManager am) {
		this.applicationManager = null;
	}
	private void process(ObjectMapper mapper,
			JsonNode node) throws IOException, JsonGenerationException,
			JsonMappingException {
		String name = node.get("repository").get("name").asText();
		String ref = node.get("ref").asText();
		String branch = ref.substring(ref.lastIndexOf("/")+1,ref.length());
		System.err.println("branch: "+branch);

		System.err.println(">>>>> "+name);
		Iterator<JsonNode> commits = node.get("commits").getElements();

//		ObjectWriter writer = mapper.writer().withDefaultPrettyPrinter();
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

//			writer.writeValue(System.err, commit);
			
		}
//		for (JsonNode commit : commits) {
//			paths.addAll(commit.findValuesAsText("added"));
//			paths.addAll(commit.findValuesAsText("removed"));
//			paths.addAll(commit.findValuesAsText("modified"));
////			System.err.println(">>>>>>>>>>>>>>>>>>><<>>"+commit.findValues("modified"));
//		}
		System.err.println("nodes: "+paths);
		boolean tipiChanged = tipiResourcesChanged(paths);
		boolean settingsChanged = settingsChanged(paths);
		if(settingsChanged) {
			logger.info("Settings changed for application: "+name);
		}
		if(tipiChanged) {
			logger.info("Tipi changed for application: "+name);
		}
//		writer.writeValue(System.err, node);
		String applicationName = name+"-"+branch;
		File applicationFolder = new File(applicationManager.getAppsFolder(),applicationName);
		if(applicationFolder.exists()) {
			logger.info("Found application folder");
		}
		AppStoreOperation buildJnlp = operations.get("tipi.dev.operation.build");
		if(buildJnlp!=null) {
			logger.info("jnlp build found");
		}
		AppStoreOperation buildxsd = operations.get("tipi.dev.operation.xsdbuild");
		if(buildxsd!=null) {
			logger.info("buildxsd build found");
		}
		AppStoreOperation cachebuild = operations.get("tipi.dev.operation.cachebuild");
		if(cachebuild!=null) {
			logger.info("cachebuild build found");
		}

		final ApplicationStatus application = findApplication(name,branch);

		if(settingsChanged) {
			buildJnlp.build(application);
			buildxsd.build(application);
		}
		if(tipiChanged) {
			cachebuild.build(application);
		}
	}
	
	private ApplicationStatus findApplication(String repo, String branch) {
		for (Map.Entry<ApplicationStatus, Map<String,Object>> e : applicationSettings.entrySet()) {
			Map<String,Object> s = e.getValue();
			if(s!=null) {
				if(repo.equals(s.get("repositoryname"))) {
					if(branch.equals(s.get("branch"))) {
						return e.getKey();
					}
				}
 			}
		}
		return null;
	}
	
	private boolean tipiResourcesChanged(List<String> p) {
		for (String path : p) {
			if(path.startsWith("tipi/")) {
				return true;
			}
			if(path.startsWith("resource/")) {
				return true;
			}
		}
		return false;
	}

	private boolean settingsChanged(List<String> p) {
		for (String path : p) {
			if(path.startsWith("settings/")) {
				return true;
			}
		}
		return false;
	}

	
	public static void main(String[] args) throws IOException {
		
//		Reader fis = new FileReader("/Users/frank/git/navajo/tipi_dev/com.dexels.navajo.tipi.dev.store/json_test/single_tipi_edit.json");
		Reader fis = new FileReader("/Users/frank/git/navajo/tipi_dev/com.dexels.navajo.tipi.dev.store/json_test/acceptance_settings_edit.json");
		ObjectMapper mapper = new ObjectMapper();
		JsonFactory factory = mapper.getJsonFactory(); // since 2.1 use mapper.getFactory() instead
		JsonParser jp = factory.createJsonParser(fis);
		JsonNode node = mapper.readTree(jp);		

		GitHubServlet ghs = new GitHubServlet();
		ghs.setApplicationManager(new ApplicationManager() {
			
			@Override
			public Set<String> listApplications() {
				return null;
			}
			
			@Override
			public File getStoreFolder() {
				return new File("/Users/frank/git/navajo/tipi_dev/com.dexels.navajo.tipi.dev.store");
			}
			
			@Override
			public File getAppsFolder() {
				return new File("/Users/frank/git/navajo/tipi_dev/com.dexels.navajo.tipi.dev.store/applications");
			}
		});
		ghs.process(mapper, node);
		fis.close();
	}

	private boolean checkGitHubIpRange(HttpServletRequest req) {
		//The Public IP addresses for these hooks are: 204.232.175.64/27, 192.30.252.0/22.
		return true;
	}
}
