package com.dexels.navajo.tipi.dev.server;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URLDecoder;
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
import org.eclipse.jgit.api.errors.GitAPIException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.dev.server.appmanager.AppStoreOperation;
import com.dexels.navajo.tipi.dev.server.appmanager.ApplicationManager;
import com.dexels.navajo.tipi.dev.server.appmanager.ApplicationStatus;
import com.dexels.navajo.tipi.dev.server.appmanager.GitApplicationStatus;



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
		String p = req.getParameter("payload");
//		ByteArrayOutputStream baos = new ByteArrayOutputStream();
//		copyResource(baos, p.getInputStream()); 
//		final byte[] byteArray = baos.toByteArray();
//		application/x-www-form-urlencoded
		String decoded = URLDecoder.decode(p,"UTF-8");
		logger.info("Received: \n"+p);
		ObjectMapper mapper = new ObjectMapper();
		JsonFactory factory = mapper.getJsonFactory(); // since 2.1 use mapper.getFactory() instead
		JsonParser jp = factory.createJsonParser(decoded);
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
	
	public void removeOperation(AppStoreOperation a,Map<String,Object> settings) {
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
		final String name = node.get("repository").get("name").asText();
		String ref = node.get("ref").asText();
		final String branch = ref.substring(ref.lastIndexOf("/")+1,ref.length());
		Iterator<JsonNode> commits = node.get("commits").getElements();

//		ObjectWriter writer = mapper.writer().withDefaultPrettyPrinter();
		List<String> paths = new ArrayList<String>();
		while (commits.hasNext()) {
			JsonNode commit = commits.next();
			final Iterator<JsonNode> modified = commit.get("modified").getElements();
			while(modified.hasNext()) {
				final String text = modified.next().asText();
				paths.add(text);
			}
			final Iterator<JsonNode> added = commit.get("added").getElements();
			while(added.hasNext()) {
				final String text = added.next().asText();
				paths.add(text);
			}
			final Iterator<JsonNode> removed = commit.get("removed").getElements();
			while(removed.hasNext()) {
				final String text = removed.next().asText();
				paths.add(text);
			}

//			writer.writeValue(System.err, commit);
			
		}

		logger.info("nodes: "+paths);
		final boolean tipiChanged = tipiResourcesChanged(paths);
		final boolean settingsChanged = settingsChanged(paths);
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
		final AppStoreOperation buildJnlp = operations.get("tipi.dev.operation.build");
		final AppStoreOperation buildxsd = operations.get("tipi.dev.operation.xsdbuild");
		final AppStoreOperation cachebuild = operations.get("tipi.dev.operation.cachebuild");

		Runnable r = new Runnable() {

			@Override
			public void run() {
				final ApplicationStatus application = findApplication(name,
						branch);
				try {
					Thread.sleep(12000);
				} catch (InterruptedException e1) {
					logger.error("Error: ", e1);
				}
				try {
					if (application instanceof GitApplicationStatus) {
						GitApplicationStatus ga = (GitApplicationStatus) application;
						try {
							ga.callPull();
						} catch (GitAPIException e) {
							logger.error("Error: ", e);
						}
					}
					logger.info("pull complete");
					if (settingsChanged) {
						logger.info("settings changed. building jnlp");
						buildJnlp.build(application);
						buildxsd.build(application);
					}
					if (tipiChanged) {
						logger.info("tii changed, rebuilding cache");
						cachebuild.build(application);
					}
				} catch (IOException e) {
					logger.error("Error: ", e);
				}
			}
		};
		Thread t = new Thread(r);
		t.start();
	}
	
	private ApplicationStatus findApplication(String repo, String branch) {
		for (Map.Entry<ApplicationStatus, Map<String,Object>> e : applicationSettings.entrySet()) {
			Map<String,Object> s = e.getValue();
			if(s!=null) {
				if(repo.equals(s.get("repositoryname"))) {
					if(branch.equals(s.get("branch"))) {
						final ApplicationStatus key = e.getKey();
						logger.info("Found application for repo: "+repo+" and branch: "+branch);
						return key;
					}
				}
 			}
		}
		logger.info("Did not find application for repo: "+repo+" branch: "+branch);
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

			@Override
			public String getCodeBase() {
				return null;
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
