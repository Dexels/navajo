package com.dexels.navajo.tipi.jgit;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.FetchCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.CredentialsProvider;

import com.jcraft.jsch.JSchException;

public class Main {

	private static final String PATH = "./cloned/";
	private static final String SOURCE = "git@github.com:Dexels/com.sportlink.club.git";
//	private static final String SOURCE = "https://github.com/Dexels/tipi-example.git";
//	"https://github.com/Dexels/com.sportlink.club.git";
//	"https://github.com/Dexels/tipi-example.git"
	
	/**
	 * @param args
	 * @throws GitAPIException
	 * @throws TransportException
	 * @throws InvalidRemoteException
	 * @throws IOException
	 * @throws JSchException 
	 */
	public static void main(String[] args) throws InvalidRemoteException,
			TransportException, GitAPIException, IOException, JSchException {
//		new JSch();
//		JSch js = new JSch();
//		js.addIdentity("/Users/frank/.ssh/id_rsa");
		long start = System.currentTimeMillis();
		File f = new File(PATH);
		if (f.exists()) {
			callPull();
		} else {
			callClone(SOURCE,"Production");
		}
		long elapsed = System.currentTimeMillis() - start;
		System.err.println("Elapsed: "+elapsed);
	}



	protected static void callPull() throws GitAPIException,
			IOException {
		Repository repository = getRepository();
		Git git = new Git(repository);
		FetchCommand pc = git.fetch();
		pc.setRemote("origin");
		//		pc.
		pc.setProgressMonitor(new NavajoProgress());
		pc.call();
	}

	protected static void callClone(String url, String branch) throws GitAPIException,
			InvalidRemoteException, TransportException, IOException {
		CloneCommand clone = Git.cloneRepository();
		clone.setProgressMonitor(new NavajoProgress());
		clone.setBare(false);
		clone.setCloneAllBranches(false);
		if(branch!=null) {
			clone.setBranch(branch);
		}
//		clone.setCloneAllBranches(false);
		
		clone.setDirectory(new File(PATH)).setURI(url);
		CredentialsProvider user = CredentialsProvider.getDefault(); // new
		clone.setCredentialsProvider(user);
		Git git = clone.call();
		StoredConfig config = git.getRepository().getConfig();
		config.setString("remote", "origin", "fetch", "+refs/*:refs/*");
		config.save();		
//		Ref ref = git.checkout().
//		        setCreateBranch(true).
//		        setName(branch).
//		        setUpstreamMode(CreateBranchCommand.SetupUpstreamMode.TRACK).
//		        setStartPoint("origin/" + branch).
//		        call();
	}

	protected static Repository getRepository() throws IOException {
		FileRepositoryBuilder builder = new FileRepositoryBuilder();
		Repository repository = builder.setGitDir(new File(PATH+"/.git"))
				.readEnvironment().findGitDir().build();
		return repository;
	}

}
