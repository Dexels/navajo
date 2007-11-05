package com.dexels.navajo.tribe;

import java.net.Inet4Address;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import org.jgroups.Address;
import org.jgroups.ChannelClosedException;
import org.jgroups.ChannelNotConnectedException;
import org.jgroups.JChannel;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;
import org.jgroups.stack.IpAddress;
import org.jgroups.util.Util;

import com.dexels.navajo.adapter.queue.RequestResponseQueue;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.Dispatcher;
import com.dexels.navajo.server.GenericThread;
import com.dexels.navajo.server.NavajoConfig;
import com.dexels.navajo.server.Parameters;
import com.dexels.navajo.server.UserException;
import com.dexels.navajo.server.enterprise.tribe.TribeManagerInterface;
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
 *  - Routing of async mappable webservices to correct server.
 *  - Task store should be local?? Triggered events should be global??
 *  - Integrity worker can be global.
 *  - Workflow definitions should be global?? Workflow instances should be local??
 *  
 * NOTE: IPv6 BUG 
 * http://wiki.jboss.org/wiki/Wiki.jsp?page=IPv6
 * Use java command line argument: -Djava.net.preferIPv4Stack=true
 * 
 * @author arjen
 *
 */
public class TribeManager extends ReceiverAdapter implements Mappable, TribeManagerInterface {

	public String setChief;
	public String chiefName;
	public boolean isChief;
	
	JChannel channel;
	View previousView = null;
	TribeMember myMembership = null;
	TribeMember theChief = null;
	
	static Set<Request> answerWaiters = Collections.synchronizedSet(new HashSet<Request>());
	private final static ClusterState state = new ClusterState();
	
	private static String myName;
	public static boolean initializing = false;
	private static TribeManager instance = null;
	private static Object semaphore = new Object();
	
	public TribeMember [] members = null;
	
	public static TribeManager getInstance() {
		
		if ( instance != null && !initializing ) {
			return instance;
		}
		
		while ( initializing ) {
			System.err.println(Dispatcher.getInstance().getNavajoConfig().getInstanceName() + ": Tribemanager is still initializing...");
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
		}
		
		initializing = true;
		
		if (instance == null) {

			synchronized (semaphore) {

				if ( instance == null ) {

					myName = Dispatcher.getInstance().getNavajoConfig().getInstanceName();
					instance = new TribeManager();
					state.firstMember = myName;
					
					try {
						System.err.println("=================================== SETTING UP JGROUPS CHANNEL ==================================");
						System.setProperty("java.net.preferIPv4Stack", "true");
						instance.channel=new JChannel();
						instance.channel.setReceiver(instance);
						instance.channel.connect("Navajo Tribe");
						instance.channel.getState(null, 1000);
						System.err.println("MyAddress = " + ((IpAddress) instance.channel.getLocalAddress()).getIpAddress() + ", port = " + 
								((IpAddress) instance.channel.getLocalAddress()).getPort());
						System.err.println("=================================================================================================");
					} catch (Exception e) {
						e.printStackTrace(System.err);
					}
				}
				
				// Broadcast alive status and get registered web services from the chief (TODO)
				IpAddress ip = (IpAddress) instance.channel.getLocalAddress();
				TribeMember candidate = new TribeMember(myName, ip);
				instance.myMembership = candidate;
				instance.broadcast(new SmokeSignal(myName, SmokeSignal.OBJECT_MEMBERSHIP, SmokeSignal.KEY_INTRODUCTION, candidate));
				initializing = false;
				semaphore.notify();
				TribalStatusCollector.getInstance();
			}
		}
		
		
		
		return instance;
	}
	
	private final void removeWaitingRequest(Request q) {
		
		HashSet<Request> hs = new HashSet<Request>(answerWaiters);
		Iterator<Request> iter = hs.iterator();
		while ( iter.hasNext() ) {
			Request q1 = iter.next();
			if ( q1.getGuid().equals(q.getGuid() ) ) {
				answerWaiters.remove(q1);
				return;
			}
		}
	}
	
	private final boolean containsWaitingRequest(Request q) {
		Iterator<Request> iter = answerWaiters.iterator();
		while ( iter.hasNext() ) {
			if ( iter.next().getGuid().equals(q.getGuid() ) ) {
				return true;
			}
		}
		return false;
	}
	
	private final Request getWaitingRequest(Request q) {
		Iterator<Request> iter = answerWaiters.iterator();
		while ( iter.hasNext() ) {
			Request q1 = iter.next();
			if ( q1.getGuid().equals(q.getGuid() ) ) {
				return q1;
			}
		}
		return null;
	}
	
	public byte[] getState() {
		try {
			synchronized (state) {
				//System.err.println(myName + ": IN getState(), sending clusterMembers: " + state.clusterMembers.size() );
				return Util.objectToByteBuffer(state);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	 
	public void setState(byte[] new_state) {
		try {
			ClusterState cs = (ClusterState) Util.objectFromByteBuffer(new_state);
			//System.err.println(myName + ": FirstMember IS: " + cs.firstMember);
			//System.err.println(myName + ": IN setState(), received clusterMembers: " + cs.clusterMembers.size() );
			HashSet<TribeMember> tribalMap= cs.clusterMembers;
			synchronized (state) {
				//System.err.println(myName + ": CLEARING CLUSTERMEMBERS SET");
				state.clusterMembers.clear();
				state.clusterMembers.addAll(tribalMap);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void addTribeMember(TribeMember tm) {
		synchronized (state) {
			state.clusterMembers.add(tm);
		}
		updateState();
	}
	
	private final void updateState() {
		synchronized (state) {
			// Set chief correctly and myMembership correctly.
			View w = channel.getView();
			Address chiefAddress = w.getMembers().get(0);
			Iterator<TribeMember> iter = state.clusterMembers.iterator();
			while ( iter.hasNext() ) {
				TribeMember mbr = iter.next();
				if ( mbr.getAddress().equals(chiefAddress)) {
					mbr.setChief(true);
					theChief = mbr;
				} else {
					mbr.setChief(false);
				}
				if ( mbr.getAddress().equals(channel.getLocalAddress() ) ) {
					myMembership = mbr;
				}
				System.err.println(myName +  ">>>>VIEWVIEWVIEW>>>> " + mbr.getMemberName() + ": " + mbr.getAddress() + ": " + mbr.isChief() + 
						( mbr.getAddress().equals(channel.getLocalAddress()) ? " (ME) " : "") );
			}
			
		}
	}
	
	public void viewAccepted(View new_view) {
		
		// Check whether view has changed.
		if ( previousView != null ) {
			
			// Check if I have become the chief.
			if ( new_view.getMembers().get(0).equals(myMembership.getAddress() )) {
				myMembership.setChief(true);
				//System.err.println("I HAVE BECOME THE CHIEF!!!!!!");
			}
			
			// Check whether members have died.
			Vector pv = previousView.getMembers();
			for ( int i = 0; i < pv.size(); i++ ) {
				if (!new_view.containsMember((Address) pv.get(i) ) ) {
					System.err.println(myName + "THIS ONE DIED: " + ((IpAddress) pv.get(i)).getPort() );
					synchronized (state) {
						Set<TribeMember> copyOf = new HashSet<TribeMember>(state.clusterMembers);
						Iterator<TribeMember> iter = copyOf.iterator();
						while ( iter.hasNext() ) {
							TribeMember mbr = iter.next();
							System.err.println(myName + ": CHECKING: " + mbr.getMemberName());
							if ( mbr.getAddress().equals(pv.get(i))) {
								System.err.println(myName + ":FOUND DECEASED MEMBER IN CLUSTERMEMBERS LIST....");
								state.clusterMembers.remove(mbr);
								deActivateMember(mbr);
							}
						}
					}
				}
			}
			updateState();
		}
		
		previousView = (View) new_view.clone();
    }
	
	public TribeMember getChief() {
		return theChief;
	}
	
	private void deActivateMember(TribeMember ptm) {
		System.err.println("DEACTIVATING MEMBER: " + ptm.getAddress() );
		if ( myMembership.isChief() ) {
			System.err.println("MOVING DISTRIBUTED SHARED DATA!!!");
			moveSharedStoreData(ptm);
		}
	}
	
	private void moveSharedStoreData(TribeMember ptm) {
		WorkFlowManager.getInstance().takeOverPersistedWorkFlows(ptm.getMemberName());
		RequestResponseQueue.getInstance().getMyStore().takeOverPersistedAdapters(ptm.getMemberName());
	}
	
	public void kill() {
	}

	public void load(Parameters parms, Navajo inMessage, Access access, NavajoConfig config) throws MappableException, UserException {
	}

	public void store() throws MappableException, UserException {
	}

	/**
	 * Broadcast a message to all tribe members.
	 * 
	 * @param q
	 */
	public void broadcast(com.dexels.navajo.tribe.SmokeSignal m) {
		try {
			channel.send(null, null, m);
		} catch (ChannelNotConnectedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ChannelClosedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void receive(org.jgroups.Message msg) {
		if ( msg.getObject() instanceof SmokeSignal ) {
			SmokeSignal r = (SmokeSignal) msg.getObject();
			r.processMessage();
		} else if ( msg.getObject() instanceof Request ) {
			Request q = (Request) msg.getObject();
			//System.err.println(myName + "Received request " + q.getGuid());
			Answer a = q.getAnswer();
			//System.err.println(myName + "My Answer is " + a.acknowledged() );
			try {
				if ( q.isBlocking() ) { // Only send answer if it's a blocking request.
					channel.send(msg.getSrc(), null, a);
				}
			} catch (ChannelNotConnectedException e) {
				e.printStackTrace();
			} catch (ChannelClosedException e) {
				e.printStackTrace();
			}
		} else if ( msg.getObject() instanceof Answer ) {
			Answer a = (Answer) msg.getObject();
			//System.err.println(myName + "Received answer...: " + a.acknowledged() );
			synchronized (answerWaiters) {
				//System.err.println("About to remove request: " + a.getMyRequest().getGuid() );
				Request q = getWaitingRequest(a.getMyRequest());
				q.setPredefinedAnswer(a);
				removeWaitingRequest(a.getMyRequest());
				answerWaiters.notify();
			}
		}
	}

	public Answer askSomebody(Request q, Address a) {
		
		// If it's myself.
		if ( channel.getLocalAddress().equals(a)) {
			return q.getAnswer();
		}
		try {
			channel.send(a, null, q);
		} catch (ChannelNotConnectedException e) {
			e.printStackTrace();
		} catch (ChannelClosedException e) {
			e.printStackTrace();
		}
		if ( q.isBlocking() ) {
			answerWaiters.add(q);
			//System.err.println("Adding request in answerWaiters: " + q.getGuid() + ", size is: " + answerWaiters.size() );
			Answer w = waitForAnswer(q);
			return w;
		} else {
			return null;
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

		synchronized (semaphore) {
			while (initializing || theChief == null) {
				if ( initializing ) {
					try {
						semaphore.wait();
					} catch (InterruptedException e) {
					}
				}
				if (theChief == null) {
					try {
						Thread.sleep(100);
						System.err.println("Waiting for the Chief...");
					} catch (InterruptedException e) {
					}
				}
			}
		}
		
		//System.err.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>  In AskChief(), q = " + q);
		if ( instance != null && !initializing ) {
			if ( myMembership.isChief() ) {
				//System.err.println("I Am the Chief, hence I'll answer");
				return q.getAnswer();
			}
			try {
				channel.send(theChief.getAddress(), null, q);
			} catch (ChannelNotConnectedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ChannelClosedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			// Only wait for answer if request is blocking.
			if ( q.isBlocking() ) {
				answerWaiters.add(q);
				//System.err.println("Adding request in answerWaiters: " + q.getGuid() + ", size is: " + answerWaiters.size() );
				Answer w = waitForAnswer(q);
				//System.err.println(">>>>>>>>>>>>>>>>>>>>>>> Got answer from chief: " + w.acknowledged());
				return w;
			}
			
		}
		
		return null;
		
	}
	
	/**
	 * Returns the name of the current chief.
	 * 
	 * @return
	 */
	public String getChiefName() {
		return theChief.getMemberName();
	}

	/**
	 * The method returns true if tribe member is chief.
	 * 
	 * @return
	 */
	public boolean getIsChief() {
		return myMembership.isChief();
	}

	public Answer waitForAnswer(Request q) {
		while (containsWaitingRequest(q) ) {
			synchronized (answerWaiters) {
				try {
					answerWaiters.wait();
				} catch (InterruptedException e) {
				}
			}
		}
		//System.err.println("Finished waitForAnswer()");
		return q.getPredefinedAnswer();
	}
	
	public void terminate() {
		System.err.println("Closing JGROUPS channel...");
		instance.channel.disconnect();
		instance.channel.close();
	}
	
	public ClusterState getClusterState() {
		return state;
	}
	
	public TribeMember[] getMembers() {
		members = new TribeMember[state.clusterMembers.size()];
		members = state.clusterMembers.toArray(members);
		return members;
	}
	
	public static void main(String [] args) throws Exception {
		System.err.println("Determining IP address...");
		System.setProperty("java.net.preferIPv4Stack", "true");
	  System.err.println("ip address = " + Inet4Address.getLocalHost().getHostAddress());
	}

	
	
}
