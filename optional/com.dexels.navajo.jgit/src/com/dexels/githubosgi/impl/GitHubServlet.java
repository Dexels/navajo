package com.dexels.githubosgi.impl;

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
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffEntry.ChangeType;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.githubosgi.GitRepositoryInstance;
import com.dexels.githubosgi.RepositoryInstance;
import com.dexels.githubosgi.RepositoryManager;

public class GitHubServlet extends HttpServlet implements Servlet {

	private final static Logger logger = LoggerFactory
			.getLogger(GitHubServlet.class);

	private static final long serialVersionUID = -4415777130543523033L;

	private final Map<String, RepositoryInstance> repositories = new HashMap<String, RepositoryInstance>();
	private final Map<RepositoryInstance, Map<String, Object>> repositorySettings = new HashMap<RepositoryInstance, Map<String, Object>>();

	private RepositoryManager repositoryManager;

	private EventAdmin eventAdmin = null;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String pathInfo = req.getPathInfo();
		System.err.println("><>" + pathInfo);
		if (pathInfo == null) {
			resp.sendError(400, "Bad request path");
			return;
		}
		if (pathInfo.startsWith("/")) {
			pathInfo = pathInfo.substring(1);
		}
		String[] parts = pathInfo.split("/");
		if (parts.length < 2) {
			logger.info("Bad path: " + pathInfo);
			resp.sendError(400, "Bad request path");
			return;
		}
		String name = parts[0];
		String branch = parts[1];
		GitRepositoryInstance r = findApplicationByName(name, branch);
		if(r==null) {
			resp.sendError(400, "No repo");
			return;
		}
		int eventCount = refreshApplication(r);
		resp.getWriter().write(""+eventCount+"events sent");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		if (!checkGitHubIpRange(req)) {
			resp.sendError(400);
		}
		String p = req.getParameter("payload");
		// ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// copyResource(baos, p.getInputStream());
		// final byte[] byteArray = baos.toByteArray();
		// application/x-www-form-urlencoded
		String decoded = URLDecoder.decode(p, "UTF-8");
		logger.info("Received: \n" + p);
		ObjectMapper mapper = new ObjectMapper();
		JsonFactory factory = mapper.getJsonFactory(); // since 2.1 use
														// mapper.getFactory()
														// instead
		JsonParser jp = factory.createJsonParser(decoded);
		JsonNode node = mapper.readTree(jp);

		process(mapper, node);
	}

	public void addRepositoryInstance(RepositoryInstance a,
			Map<String, Object> settings) {
		repositories.put(a.getApplicationName(), a);
		repositorySettings.put(a, settings);
	}

	public void removeRepositoryInstance(RepositoryInstance a) {
		repositories.remove(a.getApplicationName());
		repositorySettings.remove(a);
	}

	public void setRepositoryManager(RepositoryManager am) {
		this.repositoryManager = am;
	}

	public void clearRepositoryManager(RepositoryManager am) {
		this.repositoryManager = null;
	}

	private void process(ObjectMapper mapper, JsonNode node)
			throws IOException, JsonGenerationException, JsonMappingException {
		final String name = node.get("repository").get("name").asText();
		final String url = node.get("repository").get("url").asText();
		String ref = node.get("ref").asText();
		final String branch = ref.substring(ref.lastIndexOf("/") + 1,
				ref.length());
		Iterator<JsonNode> commits = node.get("commits").getElements();

		// ObjectWriter writer = mapper.writer().withDefaultPrettyPrinter();
		// writer.writeValue(System.err, node);
		List<String> paths = new ArrayList<String>();
		while (commits.hasNext()) {
			JsonNode commit = commits.next();
			final Iterator<JsonNode> modified = commit.get("modified")
					.getElements();
			while (modified.hasNext()) {
				final String text = modified.next().asText();
				paths.add(text);
			}
			final Iterator<JsonNode> added = commit.get("added").getElements();
			while (added.hasNext()) {
				final String text = added.next().asText();
				paths.add(text);
			}
			final Iterator<JsonNode> removed = commit.get("removed")
					.getElements();
			while (removed.hasNext()) {
				final String text = removed.next().asText();
				paths.add(text);
			}
		}

		logger.info("nodes: " + paths);

		// writer.writeValue(System.err, node);
		String applicationName = name + "-" + branch;
		File applicationFolder = new File(
				repositoryManager.getRepositoryFolder(), applicationName);
		if (applicationFolder.exists()) {
			logger.info("Found application folder");
		}

		final GitRepositoryInstance application = findApplication(url, branch);
		try {
			if (application != null) {
				int eventCount = refreshApplication(application);
				logger.info("pull complete: "+eventCount+" events sent!");
			} else {
				logger.warn("No repository found from url: " + url
						+ " with branch: " + branch);
			}

		} catch (IOException e) {
			logger.error("Error: ", e);
		}

	}

	private int refreshApplication(final GitRepositoryInstance application)
			throws IOException {
		int count = 0;
		String oldVersion = application.getLastCommitVersion();
		logger.debug(">>> last commit version: " + oldVersion);
		try {
			application.callPull();
			List<DiffEntry> diffEntries = application.diff(oldVersion);
			for (DiffEntry diffEntry : diffEntries) {
				if (diffEntry.getChangeType().equals(ChangeType.ADD)) {
					sendChangeEvent(application, oldVersion,
							diffEntry.getNewPath(),
							ChangeType.ADD.name());
					count++;
				} else if (diffEntry.getChangeType().equals(
						ChangeType.MODIFY)) {
					sendChangeEvent(application, oldVersion,
							diffEntry.getNewPath(),
							ChangeType.MODIFY.name());
					count++;
				} else if (diffEntry.getChangeType().equals(
						ChangeType.COPY)) {
					sendChangeEvent(application, oldVersion,
							diffEntry.getNewPath(),
							ChangeType.COPY.name());
					count++;
				} else if (diffEntry.getChangeType().equals(
						ChangeType.DELETE)) {
					sendChangeEvent(application, oldVersion,
							diffEntry.getOldPath(),
							ChangeType.DELETE.name());
					count++;
				} else if (diffEntry.getChangeType().equals(
						ChangeType.RENAME)) {
					sendChangeEvent(application, oldVersion,
							diffEntry.getOldPath(),
							ChangeType.RENAME.name());
					sendChangeEvent(application, oldVersion,
							diffEntry.getNewPath(),
							ChangeType.RENAME.name());
					count++;
					count++;
				}

			}

			return count;
		} catch (GitAPIException e) {
			logger.error("Error: ", e);
			return -1;
		}

	}

	private void sendChangeEvent(GitRepositoryInstance application,
			String oldHash, String path, String type) {
		if (eventAdmin == null) {
			logger.warn("No event administrator, not sending any events for changed path: "
					+ path);
			return;
		}
		Map<String, String> properties = new HashMap<String, String>();
		if (path != null) {
			properties.put("path", path);
		}
		properties.put("type", type);
		if (oldHash != null) {
			properties.put("oldCommit", oldHash);
		}
		String newHash = application.getLastCommitVersion();
		if (newHash != null) {
			properties.put("newCommit", newHash);
		}
		String url = application.getUrl();
		if (url != null) {
			properties.put("url", url);
		}
		// properties.put("repositoryName", application.getApplicationName());
		Event event = new Event("githubosgi/change", properties);

		eventAdmin.postEvent(event);

	}

	private GitRepositoryInstance findApplication(String gitUrl, String branch) {
		for (Map.Entry<RepositoryInstance, Map<String, Object>> e : repositorySettings
				.entrySet()) {
			final RepositoryInstance instance = e.getKey();
			if (!(instance instanceof GitRepositoryInstance)) {
				continue;
			}
			GitRepositoryInstance gi = (GitRepositoryInstance) instance;
			String url = gi.getUrl();
			Map<String, Object> s = e.getValue();
			if (s != null) {
				if (gitUrl.equals(url)) {
					if (branch.equals(s.get("branch"))) {
						logger.info("Found application for repo from url: "
								+ url + " and branch: " + branch);
						return gi;
					}
				}
			}
		}
		logger.info("Did not find application for repo from url : " + gitUrl
				+ " branch: " + branch);
		return null;
	}

	private GitRepositoryInstance findApplicationByName(String name,
			String branch) {
		for (Map.Entry<RepositoryInstance, Map<String, Object>> e : repositorySettings
				.entrySet()) {
			final RepositoryInstance instance = e.getKey();
			if (!(instance instanceof GitRepositoryInstance)) {
				continue;
			}
			GitRepositoryInstance gi = (GitRepositoryInstance) instance;
			Map<String, Object> s = e.getValue();
			if (s != null) {
				if (name.equals(s.get("name"))) {
					if (branch.equals(s.get("branch"))) {
						logger.info("Found application for repo with name: "
								+ name + " and branch: " + branch);
						return gi;
					}
				}
			}
		}
		logger.info("Did not find application for repo from url : " + name
				+ " branch: " + branch);
		return null;
	}

	public void setEventAdmin(EventAdmin eventAdmin) {
		this.eventAdmin = eventAdmin;
	}

	/**
	 * 
	 * @param eventAdmin
	 *            the eventadmin to clear
	 */
	public void clearEventAdmin(EventAdmin eventAdmin) {
		this.eventAdmin = null;
	}

	public static void main(String[] args) throws IOException {

		// Reader fis = new
		// FileReader("/Users/frank/git/navajo/tipi_dev/com.dexels.navajo.tipi.dev.store/json_test/single_tipi_edit.json");
		Reader fis = new FileReader(
				"/Users/frank/git/navajo/tipi_dev/com.dexels.navajo.tipi.dev.store/json_test/acceptance_settings_edit.json");
		ObjectMapper mapper = new ObjectMapper();
		JsonFactory factory = mapper.getJsonFactory(); // since 2.1 use
														// mapper.getFactory()
														// instead
		JsonParser jp = factory.createJsonParser(fis);
		JsonNode node = mapper.readTree(jp);

		GitHubServlet ghs = new GitHubServlet();
		ghs.setRepositoryManager(new RepositoryManager() {
			@Override
			public File getRepositoryFolder() {
				return new File(
						"/Users/frank/git/navajo/tipi_dev/com.dexels.navajo.tipi.dev.store/applications");
			}

			@Override
			public File getSshFolder() {
				return new File(
						"/Users/frank/git/navajo/tipi_dev/com.dexels.navajo.tipi.dev.store/gitssh");
			}

		});
		ghs.process(mapper, node);
		fis.close();
	}

	private boolean checkGitHubIpRange(HttpServletRequest req) {
		// The Public IP addresses for these hooks are: 204.232.175.64/27,
		// 192.30.252.0/22.
		return true;
	}
}
