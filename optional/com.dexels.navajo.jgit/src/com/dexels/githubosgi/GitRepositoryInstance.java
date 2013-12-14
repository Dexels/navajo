package com.dexels.githubosgi;

import java.io.IOException;
import java.util.List;

import org.eclipse.jgit.api.errors.CheckoutConflictException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRefNameException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.RefAlreadyExistsException;
import org.eclipse.jgit.api.errors.RefNotFoundException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.diff.DiffEntry;

import com.dexels.navajo.repository.api.RepositoryInstance;

public interface GitRepositoryInstance extends RepositoryInstance {

	public void callClone() throws GitAPIException, InvalidRemoteException,
			TransportException, IOException;

	public void callPull() throws GitAPIException, IOException;


	public void callCheckout(String objectId, String branchname) throws IOException,
			RefAlreadyExistsException, RefNotFoundException,
			InvalidRefNameException, CheckoutConflictException, GitAPIException;

	public String getUrl();

	public String getHttpUrl();

	public List<DiffEntry> diff(String oldHash) throws IOException, GitAPIException;

	public String getLastCommitVersion();

	public int refreshApplication() throws IOException;

	public void callClean() throws GitAPIException, IOException;


}