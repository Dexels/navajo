package com.dexels.navajo.tipi.dev.server.appmanager.impl;

import org.eclipse.jgit.lib.ProgressMonitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NavajoProgress implements ProgressMonitor {

	
	private final static Logger logger = LoggerFactory
			.getLogger(NavajoProgress.class);
	int total = -1;
	int progress = 0;

	@Override
	public void update(int x) {
		if(progress % 100 == 0) {
			logger.debug("Progress: " + progress + "/" + total);
			
		}
		progress += x;
	}

	@Override
	public void start(int total) {
		logger.debug("Total: " + total);
		this.total = total;
	}

	@Override
	public boolean isCancelled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void endTask() {
		logger.debug("Done!");
	}

	@Override
	public void beginTask(String title, int work) {
		logger.debug("Starting task: " + title + " total work: "
				+ work);
		total = work;
		progress = 0;
	}
}
