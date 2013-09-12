package com.dexels.navajo.tipi.dev.server.appmanager;

import java.io.IOException;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;

public interface GitApplicationStatus extends ApplicationStatus {

	void callClone() throws GitAPIException, InvalidRemoteException,
			TransportException, IOException;

	void callPull() throws GitAPIException, IOException;


}