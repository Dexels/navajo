/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Dexels BV</p>
 * @author 
 * @version $Id$.
 *
 * DISCLAIMER
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL DEXELS BV OR ITS CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
 * OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
 * OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
 * TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 */
package com.dexels.navajo.jabber;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.logging.Level;

import org.jivesoftware.smack.*;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.muc.ParticipantStatusListener;

import com.dexels.navajo.document.*;
import com.dexels.navajo.events.*;
import com.dexels.navajo.events.types.*;
import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.scheduler.*;
import com.dexels.navajo.scheduler.triggers.AfterWebserviceTrigger;
import com.dexels.navajo.server.*;
import com.dexels.navajo.sharedstore.map.SharedTribalMap;
import com.dexels.navajo.util.*;

/**
 * Workin' da Jabba 
 * @author Frank
 *
 */
public class JabberWorker extends GenericThread implements NavajoListener, Mappable {

	/**
	 * Public fields (used as getters for mappable).
	 */
	private BlockingQueue<Runnable> myWaitingQueue   = null;
	
	
	public static final String VERSION = "$Id$";
	
	private static volatile JabberWorker instance = null;
	//private static String responseDir = null;
	private static Object semaphore = new Object();

//	private final static String id = "Navajo Jabber Interface";
	
	private NavajoJabberAgent myJabber;
	private boolean isInstantiated = false;
	private int maxQueueSize = 100;

	private final static String myId = "Navajo Jabber Worker";
	
	private Access myAccess;

	private String jabberServer;
	private String jabberPort;
	private String jabberService;
	
	public String joinRoom;
	public String registerCallback;
	
	// Registered JabberTriggers..
	private Set<JabberTrigger> triggers = null;
	
	// Registered Callbacks.
	SharedTribalMap<String, String> registeredCallbacks;
	
	public JabberWorker() {
		super(myId);
	}
	
	public void configJabber(String server, String port, String service, String postmanUrl)   {
		
		if ( server == null || server.equals("") ) {
			AuditLog.log("JABBER", "Jabber not started, not configured.");
			return;
		}
		
		this.jabberServer= server;
		this.jabberPort = port;
		
		myWaitingQueue = new LinkedBlockingQueue<Runnable>(100);
		
		initialize(server, Integer.parseInt(port), service, postmanUrl);
		
		this.jabberService = myJabber.getConferenceName();
	
	}
	
	public boolean isInstantiated() {
		return isInstantiated;
	}
	/**
	 * Beware, this functions should only be called from the authorized class that can enable this thread(!).
	 * 
	 * @return
	 */
	public static JabberWorker getInstance() {
		
		if ( instance != null ) {
			return instance;
		}
		
		synchronized ( semaphore ) {
			
			if ( instance != null ) {
				return instance;
			}
			
			instance = new JabberWorker();
			instance.myJabber =  new NavajoJabberAgent();
//			JMXHelper.registerMXBean(instance, JMXHelper.NAVAJO_DOMAIN, id);
			AuditLog.log("Jabber", "Started jabber connection worker $Id$");
			//<c.chatclient id="jabber" password="'xxxxxx'" server="'talk.google.com'" username="'dexels'" domain="'gmail.com'" password="'xxxxxxx'">
			
			instance.registeredCallbacks = new SharedTribalMap("jabber-callbacks");
			instance.registeredCallbacks = SharedTribalMap.registerMap(instance.registeredCallbacks, false);
			
			instance.triggers = new HashSet();
			instance.setSleepTime(1000);
			instance.startThread(instance);
			
			
		}

		return instance;
	}


	public void initialize(final String server, final int port, final String domain, final String postmanUrl) {

		// Initialize asynchronously.


		try {
			myJabber.initialize(server, port,domain, postmanUrl);
			//System.err.println("Initialized");
		} catch (XMPPException e) {
			e.printStackTrace();
			AuditLog.log("JABBER", "Could not initialize Jabber", Level.SEVERE);
			return;
		}		
		
		AfterWebserviceTrigger afterWebserviceTrigger = new AfterWebserviceTrigger("Jabba");
		afterWebserviceTrigger.setTask(new Task(){

			public void run() {
				Navajo n = getNavajo();
				if(n==null) {
					System.err.println("Whoops");
				} else {
					//try {
					//n.write(System.err);
					performTail(n);
					//} catch (NavajoException e) {
					//	e.printStackTrace();
					//}
				}
			}});

		NavajoEventRegistry.getInstance().addListener(NavajoRequestEvent.class, instance);
		NavajoEventRegistry.getInstance().addListener(NavajoResponseEvent.class,instance);

		isInstantiated = true;

	}
	
	protected void performTail(Navajo n) {
		String service = n.getHeader().getRPCName();
		if(service==null) {
			//System.err.println("DONT know!");
		}
		try {
			myJabber.fireTail(service, n);
		} catch (XMPPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public final void worker() {
		if(!isInstantiated()) {
			return;
		}


		Runnable r;
		try {
			r = myWaitingQueue.take();
			r.run();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
			
	}
	
	public void fireTail(final String service,final  Navajo n) {
		
		if ( !myJabber.isRegisteredAsTail(service) ) {
			return;
		}
		
		Runnable r = new Runnable(){

			public void run() {
				try {

					myJabber.fireTail(service, n);

				} catch (XMPPException e) {
					e.printStackTrace();
				}
			}};
			boolean offered = myWaitingQueue.offer(r);

	}


	public String getVERSION() {
		return VERSION;
	}
	
	
	public void terminate() {
		myJabber.disconnect();
	}

	public boolean getIsRunning() {
		return myJabber.isConnected();
	}

	public void setPresenceStatus(String status) {
		myJabber.setPresenceStatus(status);
	}

	public void setText(String status) throws XMPPException {
		myJabber.broadcastMessage(status,null);
	}

	public void broadcastNavajo(Navajo navajo) throws XMPPException {
		// TODO Auto-generated method stub

		String service = navajo.getHeader().getRPCName();

		StringWriter sw = new StringWriter();
		try {
			navajo.write(sw);
			sw.flush();
			
			myJabber.broadcastMessage(sw.toString(), service);
			
		} catch (NavajoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}	
	
	public int getQueueSize() {
		return myWaitingQueue.size();
	}


	public void onNavajoEvent(NavajoEvent ne) {

		if(ne instanceof NavajoRequestEvent) {
			NavajoRequestEvent nre = (NavajoRequestEvent)ne;
			fireTail(nre.getNavajo().getHeader().getRPCName(), nre.getNavajo() );
		}
		
		if(ne instanceof NavajoResponseEvent) {
			NavajoResponseEvent nre = (NavajoResponseEvent)ne;
			fireTail(nre.getAccess().getRpcName(), nre.getAccess().getOutputDoc() );
		}
		
		if(ne instanceof NavajoHealthCheckEvent) {
			NavajoHealthCheckEvent nre = (NavajoHealthCheckEvent)ne;
			try {
				myJabber.broadcastMessage("Health warning: "+nre.getMessage(),null);
			} catch (XMPPException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
		if(ne instanceof ServerTooBusyEvent) {
			ServerTooBusyEvent nre = (ServerTooBusyEvent)ne;
			try {
				 myJabber.broadcastMessage("Server too busy!",null);
			} catch (XMPPException e) {
				e.printStackTrace();
			}
		}		

	}

	public void load(Access access) throws MappableException, UserException {
		this.myAccess = access;
	}

	public void store() throws MappableException, UserException {
		
	}
	
	public void addTrigger(JabberTrigger jt) {
		System.err.println("In addTrigger: " + jt);
		triggers.add(jt);
	}
	
	public void removeTrigger(JabberTrigger jt) {
		System.err.println("In removeTrigger: " + jt);
		triggers.remove(jt);
	}
	
	public Set<JabberTrigger> getTriggers() {
		return triggers;
	}

	public String getJabberServer() {
		return instance.jabberServer;
	}

	public String getJabberPort() {
		return instance.jabberPort;
	}

	public String getJabberService() {
		return instance.jabberService;
	}

	/**
	 * Server joins a room.
	 * 
	 * @param joinRoom
	 * @throws UserException
	 */
	public void setJoinRoom(String joinRoom) throws UserException {
		if ( instance != null && instance.myJabber != null ) {
			instance.myJabber.joinRoom(joinRoom);
		}
	}

	/**
	 * Register a callback string with the calling client id.
	 * 
	 * @param registerCallback
	 * @throws UserException
	 */
	public void setRegisterCallback(String registerCallback) throws UserException {
		this.registerCallback = registerCallback;
		if ( instance != null && instance.myJabber != null ) {
			String nickname = registerCallback.split("@")[0];
			String roomname = registerCallback.split("@")[1];
			instance.registeredCallbacks.put(nickname, registerCallback);

			if ( !instance.myJabber.hasJoinedRoom(roomname) ) {
				
				MultiUserChat muc = instance.myJabber.joinRoom(roomname);
				muc.addParticipantStatusListener(new ParticipantStatusListener() {

					public void adminGranted(String arg0) {
					}

					public void adminRevoked(String arg0) {
					}

					public void banned(String arg0, String arg1, String arg2) {
					}

					public void joined(String arg0) {
						System.err.println("THE FOLLOWING PARTICIPANT JOINED:" + arg0);
					}

					public void kicked(String arg0, String arg1, String arg2) {
					}

					public void left(String arg0) {
						System.err.println("THE FOLLOWING PARTICIPANT LEFT:" + arg0);
						String clientid = arg0.split("/")[1];
						System.err.println("Clientid: " + clientid);
						instance.registeredCallbacks.remove(clientid);
					}

					public void membershipGranted(String arg0) {
					}

					public void membershipRevoked(String arg0) {
					}

					public void moderatorGranted(String arg0) {
					}

					public void moderatorRevoked(String arg0) {
					}

					public void nicknameChanged(String arg0, String arg1) {
					}

					public void ownershipGranted(String arg0) {
					}

					public void ownershipRevoked(String arg0) {
					}

					public void voiceGranted(String arg0) {
					}

					public void voiceRevoked(String arg0) {
					}
				}
				);
			}
		}
	}
	
	public static void main(String [] args) {
		String arg0 = "mynavajogroup-dopeapp@conference.dexels.nl/frank|192.168.1.13|frank-lyaruus-macbook-pro.local|1231768833526";
		String clientid = arg0.split("/")[1];
		System.err.println(clientid);
	}
}
