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

import org.jivesoftware.smack.*;
import org.jivesoftware.smackx.*;
import org.jivesoftware.smackx.muc.*;
import org.jivesoftware.smackx.packet.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.events.*;
import com.dexels.navajo.events.types.*;
import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.scheduler.*;
import com.dexels.navajo.server.*;
import com.dexels.navajo.server.enterprise.jabber.*;

import com.dexels.navajo.util.*;

/**
 * Workin' da Jabba 
 * @author Frank
 *
 */
public class JabberWorker extends GenericThread implements JabberInterface, NavajoListener, Mappable {



	/**
	 * Public fields (used as getters for mappable).
	 */
	private BlockingQueue<Runnable> myWaitingQueue   = null;
	
	
	public static final String VERSION = "$Id$";
	
	private static volatile JabberWorker instance = null;
	//private static String responseDir = null;
	private static Object semaphore = new Object();

	private static Message myConfigMessage = null;
	
//	private final static String id = "Navajo Jabber Interface";
	
	private NavajoJabberAgent myJabber;
	private String myRoom;
	private boolean isInstantiated = false;
	private int sendCount = 0;


	private int maxRequestRate = 20;
	private int maxQueueSize = 100;

	private Access myAccess;
	
	public void configJabber(Message jabberMessage) throws UserException  {
		Property serverProperty = jabberMessage.getProperty("server");
		Property domainProperty = jabberMessage.getProperty("domain");
		Property portProperty = jabberMessage.getProperty("port");
		Property usernameProperty = jabberMessage.getProperty("username");
		Property passwordProperty = jabberMessage.getProperty("password");
		Property roomNameProperty = jabberMessage.getProperty("chatroom");
		Property conferenceProperty = jabberMessage.getProperty("conferenceService");

		Property maxRequestRateProperty = jabberMessage.getProperty("maxRequestRate");
		Property maxQueueSizeProperty = jabberMessage.getProperty("maxQueueSize");

		String server = serverProperty.getValue();
		String domain = domainProperty.getValue();
		String port = portProperty.getValue();
		String username = usernameProperty.getValue();
		String password = passwordProperty.getValue();
		String roomname = roomNameProperty.getValue();
		String conference = conferenceProperty.getValue();
		
		maxRequestRate = (Integer)maxRequestRateProperty.getTypedValue();
		maxQueueSize = (Integer)maxQueueSizeProperty.getTypedValue();
		myWaitingQueue = new LinkedBlockingQueue<Runnable>(maxQueueSize);
		
		initialize(server, Integer.parseInt(port), domain,username, password);
		try {
			joinRoom(roomname,username,conference+"."+domain);
		} catch (XMPPException e) {
			throw new UserException(-99,"Jabber problem: ",e);
		}
		isInstantiated = true;
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
			//<c.chatclient id="jabber" password="'xxxxxx'" server="'talk.google.com'" username="'dexels'" domain="'gmail.com'" password="'xxxxxxxxx'">
			 
			instance.setSleepTime(1000);
			instance.startThread(instance);
			
		}

		return instance;
	}


	public void initialize(String server, int port, String domain, String username, String password) {
		try {
			myJabber.initialize(server, port,domain, username, password);
			//System.err.println("Initialized");
		} catch (XMPPException e) {
			e.printStackTrace();
		}		
		AfterWebserviceTrigger afterWebserviceTrigger = new AfterWebserviceTrigger("Jabba");
		afterWebserviceTrigger.setTask(new Task(){

			public void run() {
				//System.err.println("Do some shit!");
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
//		WebserviceListenerRegistry.getInstance().registerTrigger(afterWebserviceTrigger);
		//System.err.println("WebserviceListenerRegistry.getInstance() hash: "+WebserviceListenerRegistry.getInstance().hashCode());

		  NavajoEventRegistry.getInstance().addListener(NavajoRequestEvent.class, this);
		  NavajoEventRegistry.getInstance().addListener(NavajoResponseEvent.class,this);
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
		
		// Limit the max # of processed..
		try {
			Thread.sleep(1000/maxRequestRate);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void fireTail(final String service,final  Navajo n) {
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
	
	public void broadcastIntoRoom(String text) throws XMPPException {
		myJabber.broadcastIntoRoom(myRoom,text);
	
	}
	
	public void joinRoom(String roomName, String nickName, String conferenceName) throws XMPPException {
		myJabber.joinRoom(roomName, nickName, conferenceName);
		myRoom = roomName;
	}

	public void setNickName(String nickName) {
		try {
			myJabber.setNickName(nickName);
		} catch (XMPPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sendAlert() {
			myJabber.sendAlert();
		
	}

	public static void setConfig(Message jabberMessage) {
		myConfigMessage  = jabberMessage;
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
	public static void  main(String[] args) throws XMPPException, NavajoException {
		JabberWorker tjc = new JabberWorker();
		tjc.myJabber = new NavajoJabberAgent();
		
		tjc.myJabber.initialize("hermes1.dexels.com",5222,"sportlink.com","admin","xxxxxx");
	
		Navajo nn = NavajoFactory.getInstance().createNavajo();
		//for (int i = 0; i < 80; i++) {
		//	tjc.postRoomMembers(nn,"knvb");
		//}
			MultiUserChat muc = new MultiUserChat(tjc.myJabber.connection, "knvb" + "@" + "conference.sportlink.com");
			Iterator<String> is = muc.getOccupants();
			while ( is.hasNext() ) {
				System.err.println(">>>: " + is.next());
				
			}
		nn.write(System.err);
  

	}
	private void postRoomMembers(Navajo rootDoc,String roomName) throws XMPPException, NavajoException {
//	      ServiceDiscoveryManager discoManager = ServiceDiscoveryManager.getInstanceFor(myJabber.connection);
		long stamp2 = System.currentTimeMillis();
		Message participants = NavajoFactory.getInstance().createMessage(rootDoc, "Participants", Message.MSG_TYPE_ARRAY);
		rootDoc.addMessage(participants);
		List<String> members = new ArrayList<String>();
		MultiUserChat muc = new MultiUserChat(myJabber.connection, roomName + "@" + "conference.sportlink.com");
		//System.err.println("Trying to join room " +  roomName + "@" + "conference.sportlink.com AS " + Dispatcher.getInstance().getNavajoConfig().getInstanceName());
		muc.join( Dispatcher.getInstance().getNavajoConfig().getInstanceName() );
		Collection<Occupant> aa = muc.getParticipants();
		for (Occupant occupant : aa) {
			Message m = NavajoFactory.getInstance().createMessage(rootDoc,  "Participants", Message.MSG_TYPE_ARRAY_ELEMENT);
			participants.addMessage(m);
			Property jid = NavajoFactory.getInstance().createProperty(rootDoc,"Jid",Property.STRING_PROPERTY,occupant.getJid(),0,"",Property.DIR_OUT);
			m.addProperty(jid);
			Property affiliation = NavajoFactory.getInstance().createProperty(rootDoc,"Affiliation",Property.STRING_PROPERTY,occupant.getAffiliation(),0,"",Property.DIR_OUT);
			m.addProperty(affiliation);
			Property nickName = NavajoFactory.getInstance().createProperty(rootDoc,"Nickname",Property.STRING_PROPERTY,occupant.getNick(),0,"",Property.DIR_OUT);
			m.addProperty(nickName);
			Property role = NavajoFactory.getInstance().createProperty(rootDoc,"Role",Property.STRING_PROPERTY,occupant.getRole(),0,"",Property.DIR_OUT);
			m.addProperty(role);
			
			members.add(occupant.getJid());
		}
//		muc.leave();
		long stamp = System.currentTimeMillis();
		System.err.println("Time taken: "+(stamp2 - stamp));
//        DiscoverItems items = discoManager.discoverItems("knvb@conference.sportlink.com");
//        for (Iterator it = items.getItems(); it.hasNext();) {
//            DiscoverItems.Item item = (DiscoverItems.Item) it.next();
//            System.out.println("Room occupant: " + item.getEntityID());
//        }
//		System.err.println("Time taken2: "+(stamp - System.currentTimeMillis()));

	}


	private void postRooms(String roomName) {
		try {
			Navajo n = NavajoFactory.getInstance().createNavajo();
			com.dexels.navajo.document.Message m = NavajoFactory.getInstance().createMessage(n, "Rooms",
					com.dexels.navajo.document.Message.MSG_TYPE_ARRAY);
			n.addMessage(m);
			Collection<HostedRoom> aa = MultiUserChat.getHostedRooms(myJabber.connection, "conference.sportlink.com");
			for (HostedRoom hostedRoom : aa) {
				if(!hostedRoom.getName().equals(roomName)) {
					continue;
				}
				
				System.err.println("DESCRIPTION: " + hostedRoom.getName() + " # of occupants: " + hostedRoom.getJid());
				com.dexels.navajo.document.Message e = NavajoFactory.getInstance().createMessage(n, "Rooms",
						com.dexels.navajo.document.Message.MSG_TYPE_ARRAY_ELEMENT);
				// NavajoFactory.getInstance().createProperty(n,)
				Property user = NavajoFactory.getInstance().createProperty(n, "Name", Property.STRING_PROPERTY, hostedRoom.getName(), 0,"", Property.DIR_OUT, null);
				e.addProperty(user);
				Property name = NavajoFactory.getInstance().createProperty(n, "Jid", Property.STRING_PROPERTY, hostedRoom.getJid(), 0, "",Property.DIR_OUT, null);
				e.addProperty(name);
				m.addMessage(e);
			}
			n.write(System.err);
		} catch (NavajoException e1) {
			e1.printStackTrace();
		} catch (XMPPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void load(Parameters parms, Navajo inMessage, Access access, NavajoConfig config) throws MappableException, UserException {
		this.myAccess = access;
	}

	public void store() throws MappableException, UserException {
		String room = myAccess.getInDoc().getProperty("/Jabber/Chatroom").getValue();
		try {
			getInstance().postRoomMembers(myAccess.getOutputDoc(), room);
		} catch (XMPPException e) {
			throw new UserException(-1, e.getMessage(), e);
		} catch (NavajoException e) {
			throw new UserException(-1, e.getMessage(), e);
		}
	}
	
	
}
