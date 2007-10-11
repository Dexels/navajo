package com.dexels.navajo.tribe;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import com.dexels.navajo.adapter.queue.RequestResponseQueue;
import com.dexels.navajo.client.ClientException;
import com.dexels.navajo.client.ClientInterface;
import com.dexels.navajo.client.NavajoClientFactory;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.Dispatcher;
import com.dexels.navajo.server.MaintainanceRequest;
import com.dexels.navajo.server.NavajoConfig;
import com.dexels.navajo.server.Parameters;
import com.dexels.navajo.server.UserException;
import com.dexels.navajo.workflow.WorkFlowManager;

/**
 * Roles of Chief Tribemanager:
 * 
 *  - Provide global lock store
 *  - Provide global clock
 *  - Take care of migrating workflow instances of died member to other member.
 *  - Take care of migrating queued adapters of died member to other member.
 *  
 * Think about current Navajo functionality consequences for Tribe:
 * 
 *  - Queued adapter store, could be central store is now local.
 *  - Routing of async mappable webservices to correct server.
 *  - Task store should be local?? Triggered events should be global??
 *  - Integrity worker can be global.
 *  - Workflow definitions should be global?? Workflow instances should be local??
 *  
 * @author arjen
 *
 */
public class TribeManager implements Mappable {

	public String setChief;
	public String chiefName;
	public boolean isChief;
	public String fromMember;
	public Binary request;
	public Binary answer;
	public Binary broadcast;
	
	class PotentialTribeMember {
		
		public String name = null;
		public String url = null;
		public String username = "ROOT";
		public String password = "";
		public int position;
		public boolean alive = true;
		public boolean chief = false;
		public boolean timedout = false;
	}
	
	private int myPosition;
	private boolean theChiefIsMe = false;
	private boolean isChiefAvailable = false;
	private PotentialTribeMember chief = null;
	private ArrayList<PotentialTribeMember> tribeMembers = new ArrayList<PotentialTribeMember>();
	
	private static String myName;
	public static boolean initializing = false;
	private boolean loaded = false;
	private static TribeManager instance = null;
	private static Object semaphore = new Object();
	private static Object semaphore2 = new Object();
	
	private static final String PROCESS_TRIBE = "navajo/ProcessTribe";
	private static final String PROCESS_ASK_CHIEF = "navajo/ProcessAskChief";
	private static final String PROCESS_BROADCAST = "navajo/ProcessBroadcastTribe";
	
	private static final int DEFAULT_ALIVE_TIMEOUT = 1500;
	
	
	// Read tribe.xml.
	/*
	 * <message name="tribemembers" type="array">
	 *    <message name="tribemembers">
	 *       <property name="ServerName" value="server1"/>
	 *       <property name="Position" value="1"/>
	 *    </message>
	 *     <message name="tribemembers">
	 *       <property name="ServerName" value="server1"/>
	 *       <property name="Position" value="1"/>
	 *    </message>
	 *   <message name="tribemembers">
	 *       <property name="ServerName" value="server2"/>
	 *       <property name="Position" value="2"/>
	 *    </message>
	 *   <message name="tribemembers">
	 *       <property name="ServerName" value="server3"/>
	 *       <property name="Position" value="3"/>
	 *    </message>
	 * </message>
	 * 
	 * In principle position1 tribe manager is lock manager.
	 * 
	 */
	
	/**
	 * 
	 * Assumption:
	 * Network always works.
	 * Assume unique position in tribe.xml and assume tribe.xml is same for all servers.
	 * 
	 * A. Initialization/rebirth/missing chief (pow-wow):
	 *  
	 * (if not missing chief) Try to join Navajo tribe. 
	 * (if not missing chief) If there is a current Chief, respect this Chief.
	 * If there is currently no Chief:
	 *    1. For each found NavajoManager, determine other rank, if rank is higher, accept
	 *       higher rank as Chief, until no higher ranks found. 
	 *    2. Determine consensus about selected Chief.
	 * 
	 * B. Ask Chief, if Chief does not respond within time-out period: go to pow-wow.
	 * 
	 */
	
	public static TribeManager getInstance() {
		
		if ( instance != null && !initializing ) {
			return instance;
		}
		
		if ( initializing ) {
			System.err.println(Dispatcher.getInstance().getNavajoConfig().getInstanceName() + ": Tribemanager is still initializing...");
			return null;
		}
		
		initializing = true;
		boolean init = false;
		
		if (instance == null) {

			synchronized (semaphore) {

				if ( instance == null ) {

					myName = Dispatcher.getInstance().getNavajoConfig().getInstanceName();
					instance = new TribeManager();
					try {
						instance.readConfiguration();
						// Check existence of lock store.
						init = true;
						instance.powWow(5000);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						System.err.println("Could not read tribe.xml, assuming no tribe.");
						//e.printStackTrace();
						instance.theChiefIsMe = true;
					}
				}
				
				// Broadcast alive status and get registered web services from the chief (TODO)
				instance.broadcast(new SmokeSignal(myName, SmokeSignal.OBJECT_INTRODUCTION, SmokeSignal.KEY_INTRODUCTION, myName));
			}
		}
		
		initializing = false;
		
		return instance;
	}
	
	private void readConfiguration() throws IOException, NavajoException {

		//System.err.println("IN readConfiguration(), I am: " + myName );
		Navajo tribe = Dispatcher.getInstance().getNavajoConfig().readConfig("tribe.xml");
		ArrayList messages = tribe.getMessages("tribemembers");
		for ( int i = 0; i < messages.size(); i++ ) {
			Message m = (Message) messages.get(i);
			PotentialTribeMember ptm = new PotentialTribeMember();
			ptm.name = m.getProperty("ServerId").getValue();
			ptm.url = m.getProperty("ServerURL").getValue();
			ptm.position = Integer.valueOf(m.getProperty("Position").getValue());
			//System.err.println("Reading: " + ptm.name + " - " + ptm.url + " - " + ptm.position);
			if ( !ptm.name.equalsIgnoreCase(myName) ) {
				//System.err.println("ADDING TO TRIBE: " + ptm.name);
				tribeMembers.add(ptm);
			} else if ( ptm.name.equalsIgnoreCase(myName) ) {
				//System.err.println("FOUND MYSELF");
				myPosition = ptm.position;
			}
		}
	}
	
	private void deActivateMember(PotentialTribeMember ptm) {
		ptm.alive = false;
		ptm.chief = false;
		if ( chief != null && chief.name.equals(ptm.name) ) {
			//System.err.println("RESETTING CHIEF");
			chief = null;
		} else {
			// Move persisted workflows from dead server(s)
			moveSharedStoreData();
		}
	}
	
	/**
	 * Check whether member is alive.
	 * Member should give alive sign within a specified time-out to prevent cascading slowness of badly performing servers.
	 * 
	 * @param ptm
	 * @return
	 */
	private boolean isAlive(final PotentialTribeMember ptm, long timeout) {

		ptm.alive = false;
		ptm.timedout = false;
		
		new Thread(){

			public void run() {
			
				ClientInterface client = NavajoClientFactory.getClient();
				client.setRetryAttempts(1);
				try {
					//System.err.println(myName+  ":" + " TRYING TO PING: " + ptm.url );
					Navajo res = client.doSimpleSend(NavajoFactory.getInstance().createNavajo(), ptm.url, MaintainanceRequest.METHOD_NAVAJO_PING, ptm.username, ptm.password, -1);
					// Only continue if something has not timed-out yet.
					if (!ptm.timedout) {
						ptm.alive = true;
						if ( res != null && res.getMessage("ConditionErrors") == null ) {
							//System.err.println(myName+  ":" + "FROM " + myName + ": " + ptm + " IS ALIVE");
						} else {
							deActivateMember(ptm);
							System.err.println(myName+  ":" + "FROM " + myName + ": " + ptm + " IS NOT YET ALIVE");
						}
					} else {
						//System.err.println("TIMED-OUT>>>>>");
					}
					
				} catch (ClientException e) {
					e.printStackTrace(System.err);
					// TODO Auto-generated catch block
					deActivateMember(ptm);
					System.err.println(myName+  ":" + "FROM " + myName + ": " + ptm + " IS NOT YET ALIVE (IN CLIENTEXCEPTIO) " + e.getMessage());
				}
			}
		}.start();
		
		long waitUntil = System.currentTimeMillis() + timeout;
		
		while ( !ptm.alive && System.currentTimeMillis() < waitUntil ) {
			Thread.yield();
		}
		
		if (!ptm.alive) {
			ptm.timedout = true;
		}
		
		//System.err.println(myName+  ":" + "RESULT FROM PING TO " + ptm.url + "IS>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> : " + ptm.alive + ", timeout = " + timeout);
		return ptm.alive;
	}
	
	private void powWow(int alivetimeout) {

		chief = null;
		theChiefIsMe = false;

		// Try to find rest of the gang within ALIVE_TIMEOUT secs.
		int total = 0;
		System.err.println(myName+  ":" + "IN POW-WOW. Tribemebers: " + tribeMembers.size() );

		//System.err.println(myName+  ":" + "TRYING ALIVE....");
		for ( int i = 0; i < tribeMembers.size(); i++ ) {
			if ( isAlive(tribeMembers.get(i), alivetimeout)) {
				total++;
			}
		}
		//System.err.println(myName+  ":" + " ALIVE TOTAL: " + total);

		// Determine if there is a current chief available.
		//System.err.println(myName + ": ABOUT TO CHECK OTHERS IF THEY HAVE A CHIEF AVAILABLE....");
		// Check others...
		for ( int i = 0; i < tribeMembers.size(); i++ ) {
			if ( tribeMembers.get(i).alive ) {
				try {
					ClientInterface client = NavajoClientFactory.getClient();
					client.setRetryAttempts(1);
					Navajo res = client.doSimpleSend(NavajoFactory.getInstance().createNavajo() , tribeMembers.get(i).url, PROCESS_TRIBE, tribeMembers.get(i).username, tribeMembers.get(i).password, -1);
					if ( ((Boolean) res.getMessage("Chief").getProperty("IsChief").getTypedValue()).booleanValue() ) {
						chief = tribeMembers.get(i);
						tribeMembers.get(i).chief = true;
						//System.err.println(myName + ": THERE IS CURRENTLY A CHIEF ACTIVE: " + chief.name );
						theChiefIsMe = false;
						return;
					} 
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		// Determine new chief.
		if ( !theChiefIsMe && chief == null ) {
			// If not found pick highest rank.
			int highestRank = myPosition;
			theChiefIsMe = true;
			for ( int i = 0; i < tribeMembers.size(); i++ ) {
				if ( tribeMembers.get(i).alive  ) {
					if ( tribeMembers.get(i).position < highestRank ) {
						highestRank = tribeMembers.get(i).position;
						chief = tribeMembers.get(i);
						tribeMembers.get(i).chief = true;
						theChiefIsMe = false;
					}
				}
			}
		}

		if ( chief != null ) {
			//System.err.println(myName+  ":" + "FOUND NEW CHIEF: " + chief.name );
		} else {
			//System.err.println(myName+  ":" + "I AM THE CHIEF");
			theChiefIsMe = true;
			chief = new PotentialTribeMember();
			chief.name = myName;
			chief.alive = true;
			chief.chief = true;
		}

	}
	
	/**
	 * This method is used to communicate a chief dispute, e.g. it tells other tribe manager that this tribe manager
	 * has no consensus about who is the chief.
	 * 
	 */
	private void communicateChiefDispute() {

		System.err.println(myName + ":" + " In communicateChiefDispute(), theChiefIsMe = " + theChiefIsMe);

		// Communicate chief to others.
		for ( int i = 0; i < tribeMembers.size(); i++ ) {
			if ( tribeMembers.get(i).alive ) {
				try {
					// Check others...
					ClientInterface client = NavajoClientFactory.getClient();
					client.setRetryAttempts(1);
					Navajo req = NavajoFactory.getInstance().createNavajo();
					Message m = NavajoFactory.getInstance().createMessage(req, "Chief");
					req.addMessage(m);
					Property p = NavajoFactory.getInstance().createProperty(req, "Name", Property.STRING_PROPERTY, "", 0, "", Property.DIR_OUT);
					m.addProperty(p);
					Navajo res = client.doSimpleSend(req, tribeMembers.get(i).url, PROCESS_TRIBE, tribeMembers.get(i).username, tribeMembers.get(i).password, -1);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	private boolean checkChiefConsensus() {

		int total = tribeMembers.size();
		boolean chiefIsDead = false;
		for ( int i = 0; i < tribeMembers.size(); i++ ) {
			if ( tribeMembers.get(i).alive == true ) {
				if (!isAlive(tribeMembers.get(i), 200)) {
					if ( !theChiefIsMe && chief != null && tribeMembers.get(i).name.equals(chief.name)) {
						chiefIsDead = true;
						tribeMembers.get(i).alive = false;
					} else {
						deActivateMember(tribeMembers.get(i));
					}

				} else {
					tribeMembers.get(i).alive = true;
					total--;
				}
			}
		}

		if ( total == 0 ) {
			//System.err.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> EVERYBODY IS LIVING...");
			return true;
		} else if (!chiefIsDead ){
			//System.err.println(">>>>>>>>>>>>>> SOMEBODY IS NOT LIVING.....BUT THE CHIEF IS.");
			return true;
		} else {
			System.err.println(">>>>>>>>>>>>>>>>>>>>>> THE CHIEF DIED.................");
			return false;
		}
	}
	
	private void moveSharedStoreData() {
		for (int i = 0; i < tribeMembers.size(); i++) {
			if ( tribeMembers.get(i).alive == false && theChiefIsMe ) {
				WorkFlowManager.getInstance().takeOverPersistedWorkFlows(tribeMembers.get(i).name);
				RequestResponseQueue.getInstance().getMyStore().takeOverPersistedAdapters(tribeMembers.get(i).name);
			}
		}
	}
	
	private void confirmChiefStatus() {

		//System.err.println(myName + ": " + "In confirmChiefStatus(), instance = " + instance);

		//System.err.println(myName + ": GOT BEYOND SEMAPHORE2...");
		/**
		 * First check whether assumed chief is really a chief, e.g. there is consensus among other servers.
		 */
		boolean dispute = false;

		if ( !checkChiefConsensus() ) {
			// Everything is uncertain...
			chief = null;
			theChiefIsMe = false;
			dispute = true;
			//communicateChiefDispute();
		}

		// Descartes axioma.
		if ( theChiefIsMe ) {
			return;
		}

		if ( chief == null || dispute || !isAlive(chief, DEFAULT_ALIVE_TIMEOUT)) {
			System.err.println(myName + "STARTING POW-POW TO RESOLVE DISPUTE..., chief is " + chief);
			if ( chief != null ) {
				System.err.println(myName + "Assumed chief is " + chief.name);
			}
			initializing = true;
			try {
				powWow(DEFAULT_ALIVE_TIMEOUT);
			    // Move persisted workflows from dead server(s)
				moveSharedStoreData();
			} finally {
				initializing = false;
			}
		}
	}
	
	/**
	 *  
	 */
	
	public void kill() {
		// TODO Auto-generated method stub
		
	}

	public void load(Parameters parms, Navajo inMessage, Access access, NavajoConfig config) throws MappableException, UserException {
		//System.err.println(myName + ": in load(), initializing = " + initializing);
//		if (!initializing) {
		
//		} else {
//			System.err.println(myName + ": IN LOAD(), STILL INITIALIZING......");
//		}
		//System.err.println(myName + ": " + "Leaving load() normally");
	}

	public void store() throws MappableException, UserException {
	}

	/**
	 * Broadcast a message to all tribe managers.
	 * 
	 * @param q
	 */
	public void broadcast(com.dexels.navajo.tribe.SmokeSignal m) {
		
		System.err.println(myName + "in broadcast()....., tribemembers: " + instance.tribeMembers.size());
		for (int i = 0; i < instance.tribeMembers.size(); i++) {
			if ( instance.tribeMembers.get(i).alive ) {
				// Send
				System.err.println(myName + ": about to broadcast to " + instance.tribeMembers.get(i).name );
				try {
					ClientInterface client = NavajoClientFactory.getClient();
					client.setRetryAttempts(1);
					Navajo req = NavajoFactory.getInstance().createNavajo();
					Message msg = NavajoFactory.getInstance().createMessage(req, "Broadcast");
					req.addMessage(msg);
					Property p = NavajoFactory.getInstance().createProperty(req, "Message", Property.STRING_PROPERTY, "", 0, "", Property.DIR_OUT);
					msg.addProperty(p);
					Binary b = new Binary();
					OutputStream os = b.getOutputStream();
					ObjectOutputStream oos = new ObjectOutputStream(os);
					oos.writeObject(m);
					oos.close();
					os.close();
					p.setValue(b);
					System.err.println(myName + ": BROADCAST to: " + tribeMembers.get(i).name + m.getKey() + "/" + m.getObject());
					Navajo res = client.doSimpleSend(req, tribeMembers.get(i).url, PROCESS_BROADCAST, tribeMembers.get(i).username, tribeMembers.get(i).password, -1);
					if ( res != null && res.getMessage("ConditionErrors") != null ) {
						System.err.println(">>>>>>>>>>>>>>>>>>>>>>>>> Could not reach tribal member , putting on dead list.");
						deActivateMember(instance.tribeMembers.get(i));
					}
				} catch (Throwable e) {
					//e.printStackTrace(System.err);
					System.err.println(">>>>>>>>>>>>>>>>>>>>>>>>> Could not reach tribal member " + e.getMessage() + ", putting on dead list.");
					deActivateMember(instance.tribeMembers.get(i));
				}
			}
		}
	}
	
	/**
	 * Process a broadcasted smokesignal.
	 * 
	 * @param broadcast
	 */
	public void setBroadcast(Binary broadcast) {

		try {
			ObjectInputStream ois = new ObjectInputStream(broadcast.getDataAsStream());
			SmokeSignal r = (SmokeSignal) ois.readObject();
			ois.close();
			// Received message from sender, reconfirm its alive status.
			setFromMember(r.getSender());
			System.err.println(myName + " RECEIVED BROADCAST MESSAGE: " + r.getObject() + "/" + r.getKey() + ", sender " + r.getSender() );
			r.processMessage();
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}

	}
	
	/**
	 * Ask a question to the chief.
	 * Ask Chief should be possible via webservice!!! Implement this....
	 * 
	 * 
	 * @param q
	 * @return
	 */
	public Answer askChief(Request q) {

		if ( instance != null && instance.chief != null && !initializing ) {
			instance.confirmChiefStatus();
			//System.err.println(myName + ": IN ASKCHIEF........" + q.getOwner() );
			if ( instance.theChiefIsMe ) {
				//System.err.println("I AM THE CHIEF");
				return q.getAnswer();
			} else {
				// Call webservice to ask the chief.
				try {
					ClientInterface client = NavajoClientFactory.getClient();
					client.setRetryAttempts(1);
					Navajo req = NavajoFactory.getInstance().createNavajo();
					Message m = NavajoFactory.getInstance().createMessage(req, "Request");
					Property p = NavajoFactory.getInstance().createProperty(req, "Object", Property.BINARY_PROPERTY, "", 0, "", Property.DIR_OUT);
					req.addMessage(m);
					m.addProperty(p);
					Binary b = new Binary();
					OutputStream os = b.getOutputStream();
					ObjectOutputStream oos = new ObjectOutputStream(os);
					oos.writeObject(q);
					oos.close();
					os.close();
					p.setValue(b);
//					System.err.println(myName + ": ABOUT TO SEND TO CHIEF: " + chief.url);
//					req.write(System.err);
					Navajo result = client.doSimpleSend(req, chief.url, PROCESS_ASK_CHIEF, chief.username, chief.password, -1);
				
					if ( result == null || result.getMessage("Answer") == null ) {
						// Asume dead chief.
						System.err.println(">>>>>>>>>>>>>>>>>>>>>>>>> DEAD CHIEF?????????????????/");
						askChief(q);
					} else {
						Property pr = result.getMessage("Answer").getProperty("Object");
						Binary br = (Binary) pr.getTypedValue();
						ObjectInputStream ois = new ObjectInputStream(br.getDataAsStream());
						Answer a = (Answer) ois.readObject();
						ois.close();
						System.err.println(myName + ": GOT ANSWER: " + a.acknowledged());
						return a;
					}
					
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return null;
	}

	/**
	 * This method is called by other to set the chief.
	 * 
	 * @param setChief
	 */
	public void setSetChief(String setChief) {
		
		//System.err.println(">>>>>>>>>> In setSetChief(" + setChief + ")");
		if ( instance != null ) {

			// If setChief is empty, there is a chief dispute.
			if ( setChief.equals("") ) {
				instance.chief = null;
				instance.theChiefIsMe = false;
				return;
			}

			instance.setChief = setChief;

			if ( setChief.equals(myName)) {
				instance.theChiefIsMe = true;
				instance.chief = new PotentialTribeMember();
				instance.chief.name = myName;
				instance.chief.alive = true;
				instance.chief.chief = true;
			} else {
				for (int i = 0; i < instance.tribeMembers.size(); i++) {
					if ( instance.tribeMembers.get(i).name.equals(setChief)  ) {
						instance.tribeMembers.get(i).chief = true;
						instance.chief = instance.tribeMembers.get(i);
						//System.err.println(">>>>>>>>>>>>> SETTING CHIEF TO: " + setChief);
					} else {
						instance.tribeMembers.get(i).chief = false;
					}
				}
			}
		}
		
		//System.err.println("Chief is: " + instance.chief.name);
	}
	
	/**
	 * Returns the name of the current chief.
	 * 
	 * @return
	 */
	public String getChiefName() {
		//System.err.println(myName + ": " + "in getChiefName()");
		
		if ( instance != null && !initializing && instance.chief != null ) {
			instance.confirmChiefStatus();
			return instance.chief.name;
		} else {
			return "unknown";
		}
	}

	/**
	 * The method returns true if tribe member is chief.
	 * 
	 * @return
	 */
	public boolean getIsChief() {
		//System.err.println(myName + ": " + "in getIsChief()");
		
		if ( instance != null && !initializing ) {
			instance.confirmChiefStatus();
			return instance.theChiefIsMe;
		} else {
			return false;
		}
	}

	/**
	 * This method can be used the confirm the alive status of another Tribe member.
	 * 
	 * @param fromMember
	 */
	public void setFromMember(String fromMember) {
		this.fromMember = fromMember;
		//System.err.println("In setFromMember(" + fromMember + "), tribe size: " + instance.tribeMembers.size());
		for (int i = 0; i < instance.tribeMembers.size(); i++) {
			if ( instance.tribeMembers.get(i).name.equals(fromMember) ) {
				instance.tribeMembers.get(i).alive = true;
				//System.err.println(fromMember + " is alive <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< ");
			}
		}
	}

	/**
	 * Receive a request object and perform the getAnswer() method.
	 * Only the Chief may perform such requests.
	 * 
	 * @param request
	 */
	public void setRequest(Binary request) {

		this.request = request;
		try {
			ObjectInputStream ois = new ObjectInputStream(request.getDataAsStream());
			Request r = (Request) ois.readObject();
			ois.close();
			System.err.println(myName + ": GOT REQUEST FROM: " + r.getOwner());
			// Reconfirm alive status for sender of request.
			setFromMember(r.getOwner());
			Answer a = r.getAnswer();
			answer = new Binary();
			ObjectOutputStream oos = new ObjectOutputStream(answer.getOutputStream());
			oos.writeObject(a);
			oos.close();
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
	}

	public Binary getAnswer() {
		return answer;
	}
	
	/**
	 * Chief should have an active Lock checker that checks if locks are still in use:
	 * 1. Check if owner server is alive.
	 * 2. Check if owner server is still using the lock.
	 */
}
