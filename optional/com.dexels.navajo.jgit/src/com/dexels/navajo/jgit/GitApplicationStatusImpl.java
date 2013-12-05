package com.dexels.navajo.jgit;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.api.CreateBranchCommand.SetupUpstreamMode;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullResult;
import org.eclipse.jgit.api.ResetCommand.ResetType;
import org.eclipse.jgit.api.errors.CheckoutConflictException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRefNameException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.RefAlreadyExistsException;
import org.eclipse.jgit.api.errors.RefNotFoundException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;

public class GitApplicationStatusImpl  {

	
	private final static Logger logger = LoggerFactory.getLogger(GitApplicationStatusImpl.class);

	private File privateKey;
	private File publicKey;
	private String branch;
	private String gitUrl;

	private Git git;

	private RevCommit lastCommit;

	private String reponame;

	private String name;

	private String httpUrl;

	private File gitRepoFolder;

	private File storeFolder;


	public String getGitUrl() {
		return gitUrl;
	}
	public String getHttpUrl() {
		return httpUrl;
	}

	
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
	
	public String getRepositoryName() {
		return reponame;
	}
	
	public void deactivate() {

	}
	
	
	public void activate(Map<String,Object> settings) throws IOException {
		gitRepoFolder = new File((String) settings.get("repoFolder"));
		storeFolder = new File((String) settings.get("storeFolder"));
		gitUrl = (String) settings.get("url");
		httpUrl = (String) settings.get("httpUrl");
		reponame = (String) settings.get("repositoryname");
		String key = (String) settings.get("key");
		branch = (String) settings.get("branch");
		name = (String) settings.get("name");

		//		String combinedname = reponame + "-"+branch;
		File keyFolder = new File(storeFolder,"gitssh");
		
		privateKey = null;
		publicKey = null;
		privateKey = new File(keyFolder,key);
		publicKey = new File(keyFolder,key+".pub");
		try {
			
			if(gitRepoFolder.exists()) {
//				callPull();
				Repository repository = getRepository(gitRepoFolder);
				git = new Git(repository);
				Iterable<RevCommit> log = git.log().call();
				lastCommit = log.iterator().next();
				repository.close();
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

	}

    
	public void callPull() throws GitAPIException, IOException {
		File gitSubfolder = new File(gitRepoFolder, ".git");
		if (!gitSubfolder.exists()) {
			logger.info("Folder: " + gitRepoFolder.getAbsolutePath()
					+ " is not a git repo. Not pulling.");
		}
		Repository repository = null;
		try {
			repository = getRepository(gitRepoFolder);
			git = new Git(repository);
			git.checkout().setName(branch).call();
			git.reset().setMode(ResetType.HARD).call();
			git.pull().setProgressMonitor(new NavajoProgress()).call();
			logger.info("Current branch: " + repository.getBranch());
			git.clean().call();
			Iterable<RevCommit> log = git.log().call();
			lastCommit = log.iterator().next();
			// only will work when built:

//			xsdBuild.build(this);
//			cacheBuild.build(this);
			logger.info("Git pull complete.");
		} finally {
			repository.close();
		}
	}

	public void callCheckout(String objectId, String branchname) throws IOException, RefAlreadyExistsException, RefNotFoundException, InvalidRefNameException, CheckoutConflictException, GitAPIException {
		Repository repository = null;
		try {
			repository = getRepository(gitRepoFolder);
			git = new Git(repository);
//			git.checkout().setName("Production").setForce(true).call();
			git.checkout().setCreateBranch(true).setName(branchname).setStartPoint(objectId).setForce(true).setUpstreamMode(SetupUpstreamMode.NOTRACK).call();
			logger.info("Current branch: " + repository.getBranch());
			git.clean().call();
			git.reset().setMode(ResetType.HARD).call();
			Iterable<RevCommit> log = git.log().call();
			lastCommit = log.iterator().next();

		} finally {
			repository.close();
		}
	}
	
	public void callClone() throws GitAPIException,
			InvalidRemoteException, TransportException, IOException {
	    JSch jsch = new JSch();
	    JSch.setLogger(new com.jcraft.jsch.Logger() {
			@Override
			public void log(int arg0, String txt) {
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
		
	    CredentialsProvider user = CredentialsProvider.getDefault(); // new
	    Repository repository = null;
	    try {
			repository = getRepository(gitRepoFolder);
			git = Git.cloneRepository().setProgressMonitor(new NavajoProgress()).
					setBare(false).setCloneAllBranches(true).setDirectory(gitRepoFolder).
					setURI(gitUrl).setCredentialsProvider(user).call();
			repository = git.getRepository();
//		if(branch!=null) {
//			clone.setBranch(branch);
//		}
			
			git.branchCreate() 
			   .setName(branch)
			   .setUpstreamMode(SetupUpstreamMode.SET_UPSTREAM)
			   .setStartPoint("origin/"+branch)
			   .setForce(true)
			   .call();
//		git.branchCreate().setName(branch).call();
//		git.checkout().setName("Acceptance").call();

			git.checkout().setName(branch).call();
			StoredConfig config = git.getRepository().getConfig();
			config.setString("remote", "origin", "fetch", "+refs/*:refs/*");
			config.setString("remote", "origin", "fetch", "+refs/heads/*:refs/remotes/origin/*");
			
			//		[branch "Production"]
//				remote = origin
//				merge = refs/heads/Production
			config.setString("branch",branch,"merge","refs/heads/"+branch);
			config.setString("branch",branch,"remote","origin");
			config.save();
			callPull();
		} finally {
			repository.close();
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
		Git git = Git.open(new File("/Users/frank/git/tipi.appstore/appstore/applications/club"));
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
//		git.checkout().setName("Production").setForce(true).call();
//		git.checkout().setStartPoint("25f17284bc94236a9f921e08aebf36b3c143f2e0").setName("Production").setCreateBranch(true).setForce(true).call();
	}
}
