package com.dexels.navajo.scheduler;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.jabber.JabberWorker;
import com.dexels.navajo.server.GenericThread;

public class JabberTrigger extends Trigger {

	public final static String TYPE_PRESENCE = "presence";
	public final static String TYPE_MESSAGE = "message";
	
	public final static String STATUS_AVAILABLE = "available";
	public final static String STATUS_UNAVAILABLE = "unavailable";
	
	private static final long serialVersionUID = 7170372898641074480L;
	private String type;
	private String status = null;
	private String from;
	private String description;
	
	/**
	 * jabber:type=[presence"("[unavailable|available]")"|message],from=*
	 */
	public JabberTrigger(String description) throws InstantiationException {
		this.description = description;
		String [] fields = this.description.split(",");
		if ( fields.length != 2 ) {
			throw new InstantiationException("Invalid Jabber Trigger: " + description);
		}
		for (int i = 0; i < 2; i++) {
			String [] pms = fields[i].split("=");
			
			if ( pms.length != 2 ) {
				throw new InstantiationException("Invalid Jabber Trigger: " + description);
			}
			if ( fields[i].startsWith("type") ) {
				if ( pms[1].equals(TYPE_MESSAGE) ) {
					type = TYPE_MESSAGE;
				} else {
					type = TYPE_PRESENCE;
					String [] s = fields[i].split(":");
					if ( s.length > 1 ) {
						status = s[1];
					}
				}
			} else if ( fields[i].startsWith("from") ) {
				from = pms[1];
			}
		}
	}
	
	@Override
	public void activateTrigger() {
		JabberWorker.getInstance().addTrigger(this);
	}

	@Override
	public String getDescription() {
		return Trigger.JABBER_TRIGGER + ":" + description;
	}

	@Override
	public boolean isSingleEvent() {
		return false;
	}

	@Override
	public void removeTrigger() {
		JabberWorker.getInstance().removeTrigger(this);
	}

	@Override
	public void setSingleEvent(boolean b) {
		
	}

	public String getType() {
		return type;
	}
	
	public String getStatus() {
		return status;
	}
	
	/**
	 * Perform task asynchronously.
	 */
	public Navajo perform() {
		System.err.println("JABBERTRIGGER: ABOUT TO PERFORM: " + getTask().getId());
		GenericThread taskThread = new GenericThread("task:" + getTask().getId()) {

			public void run() {
				try {
					worker();
				} catch (Throwable t) {
					t.printStackTrace(System.err);
					//System.err.println("REALLY COULD NOT PEFORM: " + getListenerId() );
				} finally {
					finishThread();
				}
			}

			public final void worker() {
				getTask().run();
			}

			@Override
			public void terminate() {
				// Nothing special.
			}
		};
		taskThread.startThread(taskThread);

		return null;
	}

	public String getFrom() {
		return from;
	}
	
	public static void main(String [] args) throws Exception {
		String description = "type=presence,from=.*";
		JabberTrigger jt = new JabberTrigger(description);
		System.err.println("type = " + jt.getType());
		System.err.println("status = " + jt.getStatus());
		System.err.println("from = " + jt.getFrom());
	}

}
