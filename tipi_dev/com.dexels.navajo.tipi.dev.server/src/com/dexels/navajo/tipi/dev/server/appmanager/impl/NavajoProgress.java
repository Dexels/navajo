package com.dexels.navajo.tipi.dev.server.appmanager.impl;

import org.eclipse.jgit.lib.ProgressMonitor;

public class NavajoProgress implements ProgressMonitor {

	int total = -1;
	int progress = 0;

	@Override
	public void update(int x) {
		if(progress % 100 == 0) {
			System.err.println("Progress: " + progress + "/" + total);
			
		}
		progress += x;
	}

	@Override
	public void start(int total) {
		System.err.println("Total: " + total);
		this.total = total;
	}

	@Override
	public boolean isCancelled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void endTask() {
		System.err.println("Done!");
	}

	@Override
	public void beginTask(String title, int work) {
		System.err.println("Starting task: " + title + " total work: "
				+ work);
		total = work;
		progress = 0;
	}
}
