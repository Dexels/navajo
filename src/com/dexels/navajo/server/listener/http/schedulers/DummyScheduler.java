package com.dexels.navajo.server.listener.http.schedulers;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.listeners.RequestQueue;
import com.dexels.navajo.listeners.TmlRunnable;
import com.dexels.navajo.server.listener.http.TmlScheduler;

public class DummyScheduler implements TmlScheduler {

	@Override
	public void initialize(HttpServlet servlet) {

	}

	@Override
	public boolean checkNavajo(Navajo input) {
		return true;
	}

	@Override
	public int getTimeout() {
		return 0;
	}

	@Override
	public boolean preCheckRequest(HttpServletRequest request) {
		return true;
	}

	@Override
	public void run(TmlRunnable myRunner) {
		myRunner.run();
	}

	@Override
	public void submit(TmlRunnable myRunner, boolean retry) {
		throw new UnsupportedOperationException(
				"Can not schedule using DummyScheduler");
	}

	@Override
	public String getSchedulingStatus() {
		return "DummyScheduler: I know nothing";
	}

	@Override
	public void shutdownScheduler() {

	}

	@Override
	public RequestQueue getDefaultQueue() {
		throw new UnsupportedOperationException(
				"Can not getGenericPool using DummyScheduler");

	}

}
