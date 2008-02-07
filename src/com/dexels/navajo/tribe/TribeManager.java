/**
 * Title:        Navajo<p>
 * Description:  This file is part of the Navajo Service Oriented Application Framework<p>
 * Copyright:    Copyright 2002-2008 (c) Dexels BV<p>
 * Company:      Dexels<p>
 * @author Arjen Schoneveld
 * @version $Id$
 *
 * DISCLAIMER
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIESmy
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
package com.dexels.navajo.tribe;

import java.net.Inet4Address;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jgroups.Address;
import org.jgroups.ChannelClosedException;
import org.jgroups.ChannelNotConnectedException;
import org.jgroups.JChannel;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;
import org.jgroups.stack.IpAddress;
import org.jgroups.util.Util;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.events.NavajoEventRegistry;
import com.dexels.navajo.events.types.TribeMemberDownEvent;
import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.Dispatcher;
import com.dexels.navajo.server.NavajoConfig;
import com.dexels.navajo.server.Parameters;
import com.dexels.navajo.server.UserException;
import com.dexels.navajo.server.enterprise.tribe.SmokeSignal;
import com.dexels.navajo.server.enterprise.tribe.TribeManagerInterface;
import com.dexels.navajo.util.AuditLog;

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
public final class TribeManager extends ReceiverAdapter implements Mappable, TribeManagerInterface {

	public String setChief;
	public String chiefName;
	public boolean isChief;
	
	JChannel channel;
	View previousView = null;
	TribeMember myMembership = null;
	TribeMember theChief = null;
	
	private final static Set<Request> answerWaiters = Collections.synchronizedSet(new HashSet<Request>());
	private final static ClusterState state = new ClusterState();
	
	private static String myName;
	private static boolean initializing = false;
	private static TribeManager instance = null;
	private final static Object semaphore = new Object();
	
	public TribeMember [] members = null;
	
	
	public static TribeManager getInstance() {
	
		if ( instance != null && !initializing ) {
			return instance;
		}
		
		// Switch of warning messages (to prevent WARNING messages when different JChannel names are used.
		Logger.getLogger("org.jgroups.protocols").setLevel(Level.SEVERE);
		
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
						AuditLog.log(AuditLog.AUDIT_MESSAGE_TRIBEMANAGER, "=================================== SETTING UP JGROUPS CHANNEL ==================================");
						System.setProperty("java.net.preferIPv4Stack", "true");
						instance.channel=new JChannel();
						instance.channel.setReceiver(instance);
						// TODO:
						// GET GROUP NAME TO SPECIFY SPECIFIC TRIBE GROUP..
						instance.channel.connect( Dispatcher.getInstance().getNavajoConfig().getInstanceGroup() );
						instance.channel.getState(null, 1000);
						AuditLog.log(AuditLog.AUDIT_MESSAGE_TRIBEMANAGER, "MyAddress = " + ((IpAddress) instance.channel.getLocalAddress()).getIpAddress() + ", port = " + 
								((IpAddress) instance.channel.getLocalAddress()).getPort());
						AuditLog.log(AuditLog.AUDIT_MESSAGE_TRIBEMANAGER, "=================================================================================================");
					} catch (Exception e) {
						e.printStackTrace(System.err);
					}
				}
				
				// Broadcast alive status and get registered web services from the chief (TODO)
				IpAddress ip = (IpAddress) instance.channel.getLocalAddress();
				TribeMember candidate = new TribeMember(myName, ip);
				instance.myMembership = candidate;
				instance.broadcast(new MembershipSmokeSignal(myName, MembershipSmokeSignal.INTRODUCTION, candidate));
				initializing = false;
				semaphore.notify();
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
			HashSet<TribeMember> tribalMap= cs.clusterMembers;
			synchronized (state) {
				state.clusterMembers.clear();
				state.clusterMembers.addAll(tribalMap);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void addTribeMember(TribeMember tm) {
		AuditLog.log(AuditLog.AUDIT_MESSAGE_TRIBEMANAGER, "In addTribeMember");
		synchronized (state) {
			state.clusterMembers.add(tm);
		}
		updateState();
	}
	
	private final void updateState() {
		AuditLog.log(AuditLog.AUDIT_MESSAGE_TRIBEMANAGER, "In updatestate....");
		synchronized (state) {
			// Set chief correctly and myMembership correctly.
			View w = channel.getView();
			Address chiefAddress = w.getMembers().get(0);
			AuditLog.log(AuditLog.AUDIT_MESSAGE_TRIBEMANAGER, "Chief address: " + chiefAddress);
			Iterator<TribeMember> iter = state.clusterMembers.iterator();
			while ( iter.hasNext() ) {
				TribeMember mbr = iter.next();
				AuditLog.log(AuditLog.AUDIT_MESSAGE_TRIBEMANAGER, "Processing: " + mbr.getAddress());
				if ( mbr.getAddress().equals(chiefAddress)) {
					mbr.setChief(true);
					theChief = mbr;
					state.notifyAll();
				} else {
					mbr.setChief(false);
				}
				if ( mbr.getAddress().equals(channel.getLocalAddress() ) ) {
					myMembership = mbr;
				}
				AuditLog.log(AuditLog.AUDIT_MESSAGE_TRIBEMANAGER,"Current cluster view: " + mbr.getMemberName() + ": " + mbr.getAddress() + ": " + mbr.isChief() + 
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
			}
			
			// Check whether members have died.
			Vector<Address> pv = previousView.getMembers();
			for ( int i = 0; i < pv.size(); i++ ) {
				if (!new_view.containsMember( pv.get(i) ) ) {
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
	
	private final void deActivateMember(TribeMember ptm) {
		System.err.println("DEACTIVATING MEMBER: " + ptm.getAddress() );
		if ( myMembership.isChief() ) { // Emit TribeMemberDownEvent if I am the chief.
			NavajoEventRegistry.getInstance().publishEvent(new TribeMemberDownEvent(ptm));
		}
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
	public void broadcast(SmokeSignal m) {
		try {
			channel.send(null, channel.getLocalAddress(), m);
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
		} else if ( msg.getObject() instanceof Request && 
				    ( !((Request) msg.getObject()).isIgnoreRequestOnSender() || 
				      !channel.getLocalAddress().equals(msg.getSrc())
				    )
		       ) {
			Request q = (Request) msg.getObject();
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
		} else {
			AuditLog.log(AuditLog.AUDIT_MESSAGE_TRIBEMANAGER, "Received unknown or irrelevant message: " + msg);
		}
	}

	/**
	 * Send a request on a specific server identified by the address a.
	 * If a happens to be null, the request is send to each server in the cluster (askAnyBody!). In this case, if the
	 * request was blocking (isBlocking() == true), the sender only receives the first answer that was send by any server.
	 * 
	 * @param q the Request
	 * @param a the Address of somebody
	 * @return the Answer
	 */
	public Answer askSomebody(Request q, Address a) {
		
		// If it's myself.
		if ( a != null && channel.getLocalAddress().equals(a) && !q.isIgnoreRequestOnSender()) {
			return q.getAnswer();
		}
		try {
			channel.send(a, channel.getLocalAddress(), q);
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
	 * 
	 * @param q the Request
	 * @return the Answer
	 */
	public Answer askChief(Request q) {

		if ( initializing ) {
			synchronized (semaphore) {
				while (initializing ) {
					if ( initializing ) {
						try {
							semaphore.wait();
						} catch (InterruptedException e) {
						}
					}
				}
			}
		}
		
		if ( theChief == null ) {
			synchronized (state) {
				while (theChief == null) {
					try {
						AuditLog.log(AuditLog.AUDIT_MESSAGE_TRIBEMANAGER, "Waiting for the Chief...");
						state.wait();
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
			return askSomebody(q, theChief.getAddress());
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

	private final Answer waitForAnswer(Request q) {
		synchronized (answerWaiters) {
			while (containsWaitingRequest(q) ) {
				try {
					answerWaiters.wait();
				} catch (InterruptedException e) {
				}
			}

		}
		//System.err.println("Finished waitForAnswer()");
		return q.getPredefinedAnswer();
	}
	
	/**
	 * Terminate the membership of this tribe member.
	 * 
	 */
	public void terminate() {
		AuditLog.log(AuditLog.AUDIT_MESSAGE_TRIBEMANAGER, "Closing JGROUPS channel...");
		instance.channel.disconnect();
		instance.channel.close();
	}
	
	/**
	 * Get the current state of the cluster.
	 * 
	 * @return the ClusterState
	 */ 
	public ClusterState getClusterState() {
		return state;
	}
	
	/**
	 * Get all member states.
	 * 
	 * @return array with TribeMember instances
	 */
	public TribeMember[] getMembers() {
		members = new TribeMember[state.clusterMembers.size()];
		members = state.clusterMembers.toArray(members);
		return members.clone();
	}
	
	public static void main(String [] args) throws Exception {
		System.err.println("Determining IP address...");
		System.setProperty("java.net.preferIPv4Stack", "true");
	  System.err.println("ip address = " + Inet4Address.getLocalHost().getHostAddress());
	  
	}

	/**
	 * Broadcasts a Navajo service to other servers. Do NOT wait for the response.
	 * 
	 * @param in the request document
	 * @throws Exception
	 */
	public void broadcast(Navajo in) throws Exception {

		String origin = in.getHeader().getHeaderAttribute("origin");
		if ( origin == null || origin.equals("")) { // Set origin attribute to prevent broadcast ping-pong....
			System.err.println("Going to broadcast service to other members....");
			in.getHeader().setHeaderAttribute("origin", Dispatcher.getInstance().getNavajoConfig().getInstanceName());
			ServiceRequest sr = new ServiceRequest(in);
			sr.setIgnoreRequestOnSender(true);
			sr.setBlocking(false);
			askSomebody(sr, null);
		}
	}
	
	/**
	 * Forward a Navajo service request to the least busy tribe member.
	 * 
	 * @param in the request document
	 * @return the response document
	 */
	public Navajo forward(Navajo in) throws Exception {
		TribeMember alt =  getClusterState().getLeastBusyTribalMember();
		if ( alt != null ) {
			ServiceAnswer sa = (ServiceAnswer) askSomebody(new ServiceRequest(in), alt.getAddress());
			return sa.getResponse();
		} else {
			throw new Exception("No available tribe member");
		}
	}

	/**
	 * Get my instance name.
	 * 
	 * @return
	 */
	public String getMyName() {
		return myName;
	}

	public TribeMember getMyMembership() {
		return myMembership;
	}
	
}
