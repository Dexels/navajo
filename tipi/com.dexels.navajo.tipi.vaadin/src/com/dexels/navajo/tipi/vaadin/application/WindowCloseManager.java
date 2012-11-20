package com.dexels.navajo.tipi.vaadin.application;

import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.TipiContext;
import com.vaadin.Application;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Window.CloseListener;

public class WindowCloseManager implements CloseListener {

	private static final long serialVersionUID = 1604878118381797590L;
	private transient Timer shutdownTimer = null;
	private final TipiContext tipiContext;
	private final Application application;

	private static final Logger logger = LoggerFactory.getLogger(WindowCloseManager.class);

	
	public WindowCloseManager(Application a, TipiContext context) {
		this.application = a;
		this.tipiContext = context;
	}
	
	
	@Override
	public void windowClose(CloseEvent e) {
		logger.debug("WINDOW CLOSE EVENT DETECTED!");
		handleWindowClose();
		
	}

	protected void handleWindowClose() {
		if(!ApplicationUtils.isRunningInGae()) {
			 shutdownTimer  = new Timer("DisconnectTimer",true);
			 shutdownTimer.schedule(new TimerTask(){

				@Override
				public void run() {
					logger.debug("Shutdown firing!");
					tipiContext.shutdown();
					application.close();
					logger.info("Shutting down instance. Goodbye");
				}}, 10000);
		}
	}
	
	public void cancelShutdownTimer() {
		if (shutdownTimer != null) {
			logger.info("Assuming reload. Cancelling shutdown.");
			shutdownTimer.cancel();
			shutdownTimer = null;
		}
	}

}
