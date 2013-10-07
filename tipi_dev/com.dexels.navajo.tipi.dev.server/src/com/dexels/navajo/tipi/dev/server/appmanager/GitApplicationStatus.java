package com.dexels.navajo.tipi.dev.server.appmanager;

import java.io.IOException;

import org.eclipse.jgit.api.errors.CheckoutConflictException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRefNameException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.RefAlreadyExistsException;
import org.eclipse.jgit.api.errors.RefNotFoundException;
import org.eclipse.jgit.api.errors.TransportException;

public interface GitApplicationStatus extends ApplicationStatus {

	public void callClone() throws GitAPIException, InvalidRemoteException,
			TransportException, IOException;

	public void callPull() throws GitAPIException, IOException;


	public void callCheckout(String objectId, String branchname) throws IOException,
			RefAlreadyExistsException, RefNotFoundException,
			InvalidRefNameException, CheckoutConflictException, GitAPIException;


}