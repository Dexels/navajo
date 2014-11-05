package com.dexels.githubosgi.impl;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.CreateBranchCommand.SetupUpstreamMode;
import org.eclipse.jgit.api.DiffCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullResult;
import org.eclipse.jgit.api.ResetCommand.ResetType;
import org.eclipse.jgit.api.errors.CheckoutConflictException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRefNameException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.api.errors.RefAlreadyExistsException;
import org.eclipse.jgit.api.errors.RefNotFoundException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffEntry.ChangeType;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.githubosgi.GitRepositoryInstance;
import com.dexels.navajo.repository.api.diff.EntryChangeType;
import com.dexels.navajo.repository.api.diff.RepositoryChange;
import com.dexels.navajo.repository.core.impl.RepositoryInstanceImpl;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;

public class GitRepositoryInstanceImpl extends RepositoryInstanceImpl implements GitRepositoryInstance, Runnable {
	
	private final static Logger logger = LoggerFactory.getLogger(GitRepositoryInstanceImpl.class);

	private File privateKey;
	private File publicKey;
	private String branch;
	private String gitUrl;

	private Git git;
	private RevCommit lastCommit;
	private String name;
	private String httpUrl;
	private EventAdmin eventAdmin = null;

	private Thread updateThread = null;

	private int sleepTime = -1;
	private boolean running;
	
	public GitRepositoryInstanceImpl() {
		logger.debug("Instance created!");
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


	public String getGitUrl() {
		return gitUrl;
	}

	@Override
	public String getHttpUrl() {
		return httpUrl;
	}

	
	@Override
	public String getLastCommitVersion() {
		if(lastCommit!=null) {
			return lastCommit.getId().name();
		}
		return null;
	}

	
	public String getLastCommitMessage() {
		if(lastCommit!=null) {
			return lastCommit.getFullMessage();
		}
		return null;
	}

	public String getLastCommitDate() {
		if(lastCommit!=null) {
			PersonIdent authorIdent = lastCommit.getAuthorIdent();
			if(authorIdent!=null) {
				final Date when = authorIdent.getWhen();

				if(when!=null) {
					return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(when);
				}
				return null;
			}
		}
		return null;
	}
	public String getLastCommitAuthor() {
		if(lastCommit!=null) {
			PersonIdent authorIdent = lastCommit.getAuthorIdent();
			if(authorIdent!=null) {
				return authorIdent.getName();
			}
		}
		return null;
	}

	public String getLastCommitEmail() {
		if(lastCommit!=null) {
			PersonIdent authorIdent = lastCommit.getAuthorIdent();
			if(authorIdent!=null) {
				return authorIdent.getEmailAddress();
			}
		}
		return null;
	}

	public String getBranch() {
		return branch;
	}
	

	
	public void deactivate() {
		super.deregisterFileInstallLocations();
		this.running = false;
		if(updateThread!=null) {
			updateThread.interrupt();
		}
	}
	
	
	public void activate(Map<String,Object> settings) throws IOException {
		File gitRepoFolder = repositoryManager.getRepositoryFolder();
		setSettings(settings);
		
		gitUrl = (String) settings.get("url");
		httpUrl = extractHttpUriFromGitUri(gitUrl);
		if (settings.containsKey("httpUrl")) {
			// overwrite
			httpUrl = (String) settings.get("httpUrl");
		}
		String key = (String) settings.get("key");
		branch = (String) settings.get("branch");
		name = (String) settings.get("name");
		
		this.type = (String) settings.get("repository.type");
		if(this.type==null) {
			logger.warn("type key in GitRepo configuration is deprecated, use repository.type");
			this.type = (String) settings.get("type");
		}
		deployment = (String) settings.get("repository.deployment");
		repositoryName = name + "-"+branch;
		applicationFolder = new File(gitRepoFolder,repositoryName);
		logger.info("Activating git repo. Folder: {} Deployment {} Branch: {} GitUrl: {}",applicationFolder.getAbsolutePath(),deployment,branch,gitUrl);
		super.setSettings(settings);
		File keyFolder = repositoryManager.getSshFolder();
		// Not pretty..
		if(keyFolder!=null && keyFolder.exists() && key!=null) {
			privateKey = null;
			privateKey = new File(keyFolder,key);
			if(!privateKey.exists() || !privateKey.isFile()) {
				privateKey = null;
			}
			publicKey = null;
			publicKey = new File(keyFolder,key+".pub");
			if(!publicKey.exists() || !publicKey.isFile()) {
				publicKey = null;
			}
		}
		try {
			
			if(applicationFolder.exists()) {
				Repository repository = getRepository(applicationFolder);
				git = new Git(repository);
				try {
					Iterable<RevCommit> log = git.log().call();
					lastCommit = log.iterator().next();
					repository.close();
					refreshApplication();
				} catch (NoHeadException e) {
					// there was no head. That unually means a clone failure in a previous run. Delete and retry.
					// Maybe also use for other failures.
					FileUtils.deleteDirectory(applicationFolder);
					callClone();
				}
			} else {
				callClone();
			}
			
		} catch (InvalidRemoteException e) {
			logger.error("Error: ", e);
		} catch (TransportException e) {
			logger.error("Error: ", e);
		} catch (GitAPIException e) {
			logger.error("Error: ", e);
		} catch(Throwable t) {
			logger.error("Error: ", t);
			
		}
		
		registerFileInstallLocations();
		initializeThread(settings);
	}

	
	private int parseSleepTime(Object value) {
		if(value==null) {
			return -1;
		}
		if(value instanceof Integer) {
			return (Integer)value;
		}
		if(value instanceof String) {
			return Integer.parseInt((String) value);
					
		}
		return -1;
	}

	private String extractHttpUriFromGitUri(String githubUri) {
		// githubUri = git@github.com:user/project-name.git
		// httpUri = https://github.com/user/project-name
		if (githubUri.indexOf(":") < 1) {
			logger.warn("Unexepcted format of Git URI - cannot extract Http uri!");
			return "";
		}
		String userProject = githubUri.split(":")[1];
		String user = userProject.substring(0, userProject.indexOf('/'));
		String project = userProject.substring(userProject.indexOf("/") + 1, userProject.length() -4);
		return "https://github.com/" + user + "/" + project;
	}

	@Override
	public void callClean() throws GitAPIException, IOException {
		File gitSubfolder = new File(applicationFolder, ".git");
		if (!gitSubfolder.exists()) {
			logger.info("Folder: " + applicationFolder.getAbsolutePath()
					+ " is not a git repo. Not pulling.");
		}
		Repository repository = null;
		try {
			repository = getRepository(applicationFolder);
			git = new Git(repository);
			git.clean().call();
			logger.info("Git clean complete.");
		} finally {
			if(repository!=null) {
				repository.close();
			}
		}
	}
	
	@Override
	public void callPull() throws GitAPIException, IOException {
		long now = System.currentTimeMillis();

		File gitSubfolder = new File(applicationFolder, ".git");
		if (!gitSubfolder.exists()) {
			logger.info("Folder: " + applicationFolder.getAbsolutePath()
					+ " is not a git repo. Not pulling.");
		}
		Repository repository = null;
		try {
			repository = getRepository(applicationFolder);
			git = new Git(repository);
			String currentBranch = repository.getBranch();
			if(!currentBranch.equals(branch)) {
				logger.warn("Wrong branch seems to be checked out");
			}
			git.checkout().setName(branch).call();
			// TODO check local changes, see if a hard reset is necessary
			git.reset().setMode(ResetType.HARD).call();
			int merged = git.pull().setProgressMonitor(new LoggingProgressMonitor()).call().getMergeResult().getMergedCommits().length;
			
			logger.info("Current branch: " + repository.getBranch());
//			git.clean().call();
			Iterable<RevCommit> log = git.log().call();
			lastCommit = log.iterator().next();

			logger.info("Git pull of branch: {} took: {} millis, resulting in {} merged commits",repository.getBranch(), (System.currentTimeMillis() - now),merged);
		} finally {
			if(repository!=null) {
				repository.close();
			}
		}
	}

	@Override
	public void callCheckout(String objectId, String branchname) throws IOException, RefAlreadyExistsException, RefNotFoundException, InvalidRefNameException, CheckoutConflictException, GitAPIException {
		Repository repository = null;
		try {
//			git.checkout().
//	        setCreateBranch(true).
//	        setName("branchName").
//	        setUpstreamMode(CreateBranchCommand.SetupUpstreamMode.TRACK).
//	        setStartPoint("origin/" + branchName).
//	        call();
			
			
			repository = getRepository(applicationFolder);
			git = new Git(repository);
//			git.checkout().setName("Production").setForce(true).call();
			git.checkout().setCreateBranch(true).setName(branchname).setStartPoint(objectId).setForce(true).setUpstreamMode(SetupUpstreamMode.TRACK).setStartPoint(branchname).call();
			logger.info("Current branch: " + repository.getBranch());
			git.clean().call();
			git.reset().setMode(ResetType.HARD).call();
			Iterable<RevCommit> log = git.log().call();
			lastCommit = log.iterator().next();
			this.branch = branchname;
		} finally {
			if(repository!=null) {
				repository.close();
			}
		}
	}
	
	@Override
	public void callClone() throws GitAPIException,
			InvalidRemoteException, TransportException, IOException {
	    JSch jsch = new JSch();
	    if(privateKey!=null && publicKey!=null) {
		    JSch.setLogger(new com.jcraft.jsch.Logger() {
				@Override
				public void log(int level, String txt) {
					logger.debug(txt);
				}
				
				@Override
				public boolean isEnabled(int arg0) {
					return true;
				}
			});
		    try {
		        jsch.addIdentity(privateKey.getAbsolutePath(),publicKey.getAbsolutePath());
		    } catch (JSchException e) {
		    	logger.error("Error: ", e);
		    }
			
	    }
//	    CredentialsProvider user = CredentialsProvider.getDefault(); 
	    UsernamePasswordCredentialsProvider upc = new UsernamePasswordCredentialsProvider("******", "********");
	    // new
	    Repository repository = null;
	    try {
			repository = getRepository(applicationFolder);
			git = Git.cloneRepository().setProgressMonitor(new LoggingProgressMonitor()).
					setBare(false).setCloneAllBranches(true).setDirectory(applicationFolder).
					setURI(gitUrl).setCredentialsProvider(upc).call();
			repository = git.getRepository();
			git.branchCreate() 
			   .setName(branch)
			   .setUpstreamMode(SetupUpstreamMode.SET_UPSTREAM)
			   .setStartPoint("origin/"+branch)
			   .setForce(true)
			   .call();
			git.checkout().setName(branch).call();
			StoredConfig config = git.getRepository().getConfig();
			config.setString("remote", "origin", "fetch", "+refs/*:refs/*");
			config.setString("remote", "origin", "fetch", "+refs/heads/*:refs/remotes/origin/*");
			config.setString("branch",branch,"merge","refs/heads/"+branch);
			config.setString("branch",branch,"remote","origin");
			config.save();
			callPull();
		} finally {
			if(repository!=null) {
				repository.close();
			}
		}

	}

	private Repository getRepository(File basePath) throws IOException {
		FileRepositoryBuilder builder = new FileRepositoryBuilder();
		Repository repository = builder.setGitDir(new File(basePath,".git"))
				.readEnvironment().findGitDir().build();
		return repository;
	}

	public static void main(String[] args) throws IOException, GitAPIException {
//		FileRepositoryBuilder builder = new FileRepositoryBuilder();
		Git git = Git.open(new File("/Users/frank/git/com.sportlink.serv"));
		List<Ref> aa = git.branchList().call();
		System.err.println("aa: "+aa+" size: "+aa.size());
//		git.branchCreate().setName("Acceptance").call();
//		git.reset().setMode(ResetType.HARD).call();
		PullResult pr = git.pull().call();
		System.err.println("From: "+pr.getFetchedFrom());
		System.err.println("Success: "+pr.isSuccessful());
		System.err.println("C/O conf: "+pr.getMergeResult().getCheckoutConflicts());
		System.err.println("Fetch: "+pr.getFetchResult().getMessages());
		System.err.println("failed: "+pr.getMergeResult().getFailingPaths());
		ObjectId[] merged = pr.getMergeResult().getMergedCommits();
		if(merged==null) {
			System.err.println("Merged seems null.");
		} else {
			System.err.println("# of merged refs: "+merged.length);
			for (ObjectId objectId : merged) {
				System.err.println("ObjectId: "+objectId.toString());
			}
		}
		ObjectId base = pr.getMergeResult().getBase();
		System.err.println("base: "+base.toString());
		ObjectId head = pr.getMergeResult().getNewHead();
		System.err.println("base: "+head.toString());

		//		git.checkout().setName("Production").setForce(true).call();
//		git.checkout().setStartPoint("25f17284bc94236a9f921e08aebf36b3c143f2e0").setName("Production").setCreateBranch(true).setForce(true).call();
	}


	@Override
	public List<DiffEntry> diff(String oldHash) throws IOException, GitAPIException {
		Repository repository = getRepository(applicationFolder);
		Git git = new Git(repository);
		DiffCommand diff = git.diff().setShowNameAndStatusOnly(true).setOldTree(getTreeIterator(repository, oldHash));
		diff.setNewTree(getTreeIterator(repository, "HEAD"));
		List<DiffEntry> entries = diff.call();
		logger.debug(">>> entries: "+entries);
		for (DiffEntry diffEntry : entries) {

			String newPath = diffEntry.getNewPath();
			logger.debug(">> "+newPath);
		}
		return entries;
		
	}

	private AbstractTreeIterator getTreeIterator(Repository repository, String name)
			throws IOException {
		final ObjectId id = repository.resolve(name);
		if (id == null)
			throw new IllegalArgumentException(name);
		final CanonicalTreeParser p = new CanonicalTreeParser();
		final ObjectReader or = repository.newObjectReader();
		try {
			p.reset(or, new RevWalk(repository).parseTree(id));
			return p;
		} finally {
			or.release();
		}
	}
	@Override
	public String getUrl() {
		return gitUrl;
	}
	

	@Override
	public synchronized void refreshApplication() throws IOException {
//		RevCommit last = lastCommit;
		String oldVersion = getLastCommitVersion();
		logger.debug(">>> last commit version: " + oldVersion);
		try {
			callPull();
			String newVersion = getLastCommitVersion();
			
			List<String> added = new ArrayList<String>();
			List<String> modified = new ArrayList<String>();
			List<String> copied = new ArrayList<String>();
			List<String> deleted = new ArrayList<String>();
			
			List<DiffEntry> diffEntries = diff(oldVersion);
			if(newVersion.equals(oldVersion)) {
				logger.info("Identical versions. Nothing pulled");
				return;
			}
			if(diffEntries.isEmpty()) {
				logger.info("Empty changeset (but there was a commit). Maybe empty commit?");
				return;
			}

			for (DiffEntry diffEntry : diffEntries) {
				
				if (diffEntry.getChangeType().equals(ChangeType.ADD)) {
					added.add(diffEntry.getNewPath());
				} else if (diffEntry.getChangeType().equals(ChangeType.MODIFY)) {
					modified.add(diffEntry.getNewPath());
				} else if (diffEntry.getChangeType().equals(ChangeType.COPY)) {
					copied.add(diffEntry.getOldPath());
				} else if (diffEntry.getChangeType().equals(ChangeType.DELETE)) {
					deleted.add(diffEntry.getOldPath());
				} else if (diffEntry.getChangeType().equals(ChangeType.RENAME)) {
					added.add(diffEntry.getNewPath());
					deleted.add(diffEntry.getOldPath());
				}
				
			}
			Map<String, Object> properties = new HashMap<String, Object>();
			properties.put(EntryChangeType.ADD.name(), added);
			properties.put(EntryChangeType.MODIFY.name(), modified);
			properties.put(EntryChangeType.COPY.name(), copied);
			properties.put(EntryChangeType.DELETE.name(), deleted);
			if (oldVersion != null) {
				properties.put("oldCommit", oldVersion);
			}
			properties.put("newCommit", newVersion);
			String url = getUrl();
			if (url != null) {
				properties.put("url", url);
			}
			sendChangeEvent(RepositoryChange.TOPIC, properties);
		} catch (GitAPIException e) {
			logger.error("Error: ", e);
			throw new IOException("Trouble updating git repository.", e);
		}

	}

	private void sendChangeEvent(String topic, Map<String, Object> properties) {
		if (eventAdmin == null) {
			logger.warn("No event administrator, not sending any events");
			return;
		}
		properties.put("repository", this);
		 properties.put("repositoryName", getRepositoryName());
		Event event = new Event(topic, properties);

		eventAdmin.postEvent(event);

	}

	@Override
	public Map<String, Object> getSettings() {
		Map<String, Object> result = super.getSettings();
		result.put("gitUrl", getGitUrl());
		result.put("httpUrl", getHttpUrl());
		result.put("lastCommitAuthor", getLastCommitAuthor());
		result.put("lastCommitDate", getLastCommitDate());
		result.put("lastCommitEmail", getLastCommitEmail());
		result.put("lastCommitMessage", getLastCommitMessage());
		result.put("lastCommitVersion", getLastCommitVersion());
		return result;
	}

	@Override
	public String repositoryType() {
		return "git";
	}

	@Override
	public String applicationType() {
		return type;
	}

	@Override
	public String getDeployment() {
		String settingsDeployment = (String)getSettings().get("deployment");
		if(settingsDeployment!=null && !"".equals(settingsDeployment)) {
			return settingsDeployment;
		}
		String envDeployment = System.getProperty("DEPLOYMENT");
		return envDeployment;
	}

	public void initializeThread(Map<String,Object> settings) {
		this.sleepTime = parseSleepTime(settings.get("sleepTime"));

		if(sleepTime<0) {
			logger.info("No sleepTime specified, not starting autopull.");
			return;
		}
		updateThread = new Thread(this);
		this.running = true;
		updateThread.start();
		logger.info("Auto pull sync started!");
	}

	@Override
	public void run() {
		while(running) {
			try {
				long now = System.currentTimeMillis();
				callPull();
				long elapsed = System.currentTimeMillis() - now;
				logger.debug("Refresh app iteration took: "+elapsed+" millis.");
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
			} catch (Throwable e) {
				logger.error("Error: ", e);
			}
		}
	}

}
