package com.dexels.navajo.tipi.dev.server.appmanager.impl;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.dev.server.appmanager.ApplicationManager;
import com.dexels.navajo.tipi.dev.server.appmanager.ApplicationStatus;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;

public class GitApplicationStatusImpl extends ApplicationStatusImpl implements
		ApplicationStatus {

	
	private final static Logger logger = LoggerFactory.getLogger(GitApplicationStatusImpl.class);
	
	
	private ApplicationManager applicationManager;
	private File applicationFolder;

	public ApplicationManager getApplicationManager() {
		return applicationManager;
	}

	public void setApplicationManager(ApplicationManager applicationManager) {
		this.applicationManager = applicationManager;
	}

	public void clearApplicationManager(ApplicationManager applicationManager) {
		this.applicationManager = null;
	}

	

	
	@Override
	public void activate(Map<String,Object> settings) throws IOException {
//		url=git@github.com:Dexels/com.sportlink.club.git
//		key=id_rsa
//		name=club
//		branch=origin/master
		File gitRepoFolder = new File(applicationManager.getStoreFolder(),"applications");
		String gitUrl = (String) settings.get("url");
		String name = (String) settings.get("name");
		String reponame = (String) settings.get("repositoryname");
		String key = (String) settings.get("key");
		String branch = (String) settings.get("branch");
		String combinedname = reponame + "-"+branch;
		applicationFolder = new File(gitRepoFolder,combinedname);
		File keyFolder = new File(applicationManager.getStoreFolder(),"gitssh");
		
		super.load(applicationFolder);
		File privateKey = null;
		File publicKey = null;
		
		privateKey = new File(keyFolder,key);
		publicKey = new File(keyFolder,key+".pub");
		
		//		String appFolder = (String) settings.get("path");
//		File appDir = new File(appFolder);
//		load(appDir);
		try {
			
//			String gitUrl = "git@github.com:Dexels/com.sportlink.club.git";
//			File dir = new File("/Users/frank/git/navajo/tipi_dev/com.dexels.navajo.tipi.dev.store/applications/bom");
			if(applicationFolder.exists()) {
				callPull(applicationFolder,publicKey,privateKey,branch);
			} else {
				GitApplicationStatusImpl ga = new GitApplicationStatusImpl();
				ga.callClone(gitUrl, branch, applicationFolder,publicKey,privateKey);
			}
		} catch (InvalidRemoteException e) {
			logger.error("Error: ", e);
		} catch (TransportException e) {
			logger.error("Error: ", e);
		} catch (GitAPIException e) {
			logger.error("Error: ", e);
		}

	}

    

	private void callPull(File dir, File publicKey, File privateKey, String branch) throws GitAPIException,
			IOException {
		File gitSubfolder = new File(dir,".git");
		if(!gitSubfolder.exists()) {
			logger.info("Folder: "+dir.getAbsolutePath()+" is not a git repo. Not pulling.");
		}
		Repository repository = getRepository(dir);
		Git git = new Git(repository);
		git.fetch().setRemote("origin").setProgressMonitor(new NavajoProgress()).call();
		if(!repository.getBranch().equals(branch)) {
			git.checkout().setName(branch).call();
		}
		git.clean().call();
	}

	private void callClone(String url, String branch, File path, File publicKey, File privateKey) throws GitAPIException,
			InvalidRemoteException, TransportException, IOException {


//		JschConfigSessionFactory jc = new JschConfigSessionFactory() {
			
//		    @Override
//		    protected void configure(OpenSshConfig.Host host, Session session) {
//		        session.setConfig("StrictHostKeyChecking", "yes");
//		    }
//		};
	    JSch jsch = new JSch();
	    JSch.setLogger(new com.jcraft.jsch.Logger() {
			
			@Override
			public void log(int arg0, String txt) {
//				System.err.println(">>>"+txt);
			}
			
			@Override
			public boolean isEnabled(int arg0) {
				return true;
			}
		});
	    try {
	        jsch.addIdentity(privateKey.getAbsolutePath(),publicKey.getAbsolutePath());
//	        jsch.setKnownHosts("/Users/frank/.ssh/known_hosts");
	    } catch (JSchException e) {
	        e.printStackTrace();  
	    }
//	    SshSessionFactory.setInstance(jc);
		
		CloneCommand clone = Git.cloneRepository().setProgressMonitor(new NavajoProgress()).setBare(false).setCloneAllBranches(true);
//		if(branch!=null) {
//			clone.setBranch(branch);
//		}
		clone.setDirectory(path).setURI(url);
		CredentialsProvider user = CredentialsProvider.getDefault(); // new
		clone.setCredentialsProvider(user);
		
		
		Git git = clone.call();
		StoredConfig config = git.getRepository().getConfig();
		config.setString("remote", "origin", "fetch", "+refs/*:refs/*");
		config.save();
		callPull(path,publicKey,privateKey,branch);
	}

	private Repository getRepository(File basePath) throws IOException {
		FileRepositoryBuilder builder = new FileRepositoryBuilder();
		Repository repository = builder.setGitDir(new File(basePath,".git"))
				.readEnvironment().findGitDir().build();
		return repository;
	}
}
