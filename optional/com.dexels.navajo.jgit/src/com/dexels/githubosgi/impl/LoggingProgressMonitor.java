package com.dexels.githubosgi.impl;

import org.eclipse.jgit.lib.ProgressMonitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingProgressMonitor implements ProgressMonitor {

	int total = -1;
	int progress = 0;

	
	private final static Logger logger = LoggerFactory
			.getLogger(LoggingProgressMonitor.class);
	
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
