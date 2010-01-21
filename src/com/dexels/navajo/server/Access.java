/**
 * Title:        Navajo<p>
 * Description:  <p>
 * Copyright:    Copyright (c) Arjen Schoneveld<p>
 * Company:      Dexels<p>
 * @author Arjen Schoneveld
 * @version $Id$
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

package com.dexels.navajo.server;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.dexels.navajo.document.*;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.mapping.CompiledScript;
import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.enterprise.statistics.MapStatistics;
import com.dexels.navajo.server.enterprise.xmpp.JabberWorkerFactory;

/**
 * An Access object is created for each web service access to the Navajo server.
 * An Access object is a handle for all relevant information relating to a specific web service access:
 * the request Navajo, the response Navajo, but also basic information like the username, web service name,
 * time of creation, processing times, etc.
 * 
 * @author arjen
 *
 */
public final class Access implements java.io.Serializable, Mappable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7782160335447961196L;

	@SuppressWarnings("unused")
	private static final String VERSION = "$Id$";

	public java.util.Date created = new java.util.Date();
	protected static int accessCount = 0;
	public int threadCount = 0;
	public double cpuload = -1.0;
	public String accessID = "";
	public int userID;
	public int serviceID;
	public String rpcName = "";
	public String rpcPwd = "";
	public String rpcUser = "";
	public String userAgent;
	public String ipAddress;
	public String hostName;
	public boolean betaUser = false;
	public transient CompiledScript myScript = null;
	private int totaltime;
	public int parseTime;
	public int authorisationTime;
	public int clientTime;
	public int processingTime;
	public String requestEncoding;
	public boolean compressedReceive = false;
	public boolean compressedSend = false;
	public boolean isFinished = false;
	public int contentLength;
	public Binary requestNavajo;
	public Binary responseNavajo;
	public boolean debugAll;
	// Flag to indicate that during the execution of the webservice, break was called.
	private boolean breakWasSet = false;
	
	public boolean isBreakWasSet() {
		return breakWasSet;
	}

	public void setBreakWasSet(boolean breakWasSet) {
		this.breakWasSet = breakWasSet;
	}

	private Throwable myException;
	private Navajo outputDoc;
	private Navajo inDoc;
	// The mergedDoc can be used to merge a previously set Navajo with the outputDoc.
	// If the mergeDoc is not empty, it will ALWAYS be merged when setOutputDoc is called.
	private Navajo mergedDoc;

	private LazyMessageImpl lazyMap;
	private Message currentOutMessage;
	private Object userCertificate;
	private static Object mutex = new Object();
	private Set<Map<?,?>> piggyBackData = null;
	private String clientToken = null;
	private String clientInfo = null;
	
	/**
	 * Create a private logging console for this access object.
	 * TODO: Maybe restrict maximum size of console... or use Binary...
	 */
	private transient StringWriter consoleContent = new StringWriter();
	private transient PrintWriter consoleOutput = new PrintWriter(consoleContent);

	private String waitingForPreviousRequest = null;
	private transient Thread myThread = null;

	private HashMap<Integer, MapStatistics> mapStatistics = null;

	public MapStatistics createStatistics() {
		MapStatistics ms = new MapStatistics();
		if ( mapStatistics == null ) { // First map.
			mapStatistics = new HashMap<Integer, MapStatistics>();
		}
		Integer count = new Integer(mapStatistics.size());
		mapStatistics.put(count, ms);

		return ms;
	}

	public void updateStatistics(MapStatistics ms, int levelId, String mapName, long totalTime, int elementCount, boolean isArrayElement) {

		ms.levelId = levelId;
		ms.mapName = mapName;
		ms.elementCount = elementCount;
		ms.totalTime = totalTime;
		ms.isArrayElement = isArrayElement;

	}

	/**
	 * Gets the Navajo that is associated with the so called merged document.
	 * A merged document will ALWAYS be merged with the Navajo outputDoc. If the current mergedDoc is null, 
	 * a new Navajo will be returned.
	 * 
	 * @return
	 */
	public Navajo getMergedDoc() {
		if ( mergedDoc != null ) {
			return mergedDoc;
		} else {
			mergedDoc = NavajoFactory.getInstance().createNavajo();
			return mergedDoc;
		}
	}

	/**
	 * Sets the Navajo that is going to be used as a merged document, i.e. a Navajo document
	 * that will be merged with the outDoc Navajo.
	 * 
	 * @param mergedDoc
	 * @param append, if set to true Navajo mergedDoc is appended, messages of original are overwritten.
	 *                if set to false Navajo mergedDoc is merged, messages of original are merged with mergedDoc, properties
	 *                of mergedDoc have precedence over original.
	 */
	public void setMergedDoc(Navajo mergedDoc, boolean append) {
		
		if ( this.outputDoc != null ) {
			try {
				if ( append ) {
					this.outputDoc.appendDocBuffer(mergedDoc);
				} else {
					this.outputDoc.merge(mergedDoc);
				}
		    } catch (Exception e) {}
			return;
		}
		if ( mergedDoc == null ) {
			this.mergedDoc = null;
			return;
		}
		if ( this.mergedDoc == null ) {
			this.mergedDoc = mergedDoc;
		} else {
			try {
				if ( mergedDoc != null ) {
					if ( append ) {
						this.mergedDoc.appendDocBuffer(mergedDoc);
					} else {
						this.mergedDoc.merge(mergedDoc);
					}
				} else {
					this.mergedDoc = null;
				}
			} catch (NavajoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Return the Navajo response (being) created. Return empty Navajo if no current output doc is present yet.
	 * @return
	 */
	public Navajo getOutputDoc() {
		return outputDoc;
	}

	/**
	 * Checks whether a security token was used for this access.
	 * @return
	 */
	public boolean hasCertificate() {
		return (userCertificate != null);
	}

	/**
	 * Sets the response Navajo to be used for generating the response.
	 * @param n
	 */
	public void setOutputDoc(Navajo n) {
		if ( this.mergedDoc != null ) {
			try {
				if ( n != null ) {
					this.mergedDoc.appendDocBuffer(n);
				}
				outputDoc = this.mergedDoc;
			} catch (NavajoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			outputDoc = n;
		}
	}

	/**
	 * Inform the access object that it is waiting for the termination of a previous access.
	 * @param id, a unique access id
	 */
	public void setWaitingForPreviousResponse(String id) {
		this.waitingForPreviousRequest = id;
	}

	/**
	 * Returns the access id for which this access is waiting
	 * @return, a unique access id
	 */
	public String getWaitingForPreviousResponse() {
		return this.waitingForPreviousRequest;
	}

	/**
	 * Sets the compiled script that is used to handle this access.
	 * @param cs
	 */
	public void setCompiledScript(CompiledScript cs) {
		this.myScript = cs;
	}

	/**
	 * Get the compiled script used for handling this access.
	 * (Used from within scripts)
	 * @return
	 */
	public CompiledScript getMyScript() {
		return myScript;
	}

	/**
	 * Get the compiled script used for handling this access.
	 * @return
	 */
	public CompiledScript getCompiledScript() {
		return myScript;
	}

	/**
	 * Method to be used when an exception has occurred while processing this access.
	 * @param e
	 */
	public void setException(Throwable e) {
		this.myException = e;
	}

	/**
	 * Optionally returns an exception if one occurred.
	 * @return
	 */
	public Throwable getException() {
		return this.myException;
	}

	private final void setCurrentSystemLoads() {
		try {
			this.cpuload = DispatcherFactory.getInstance().getNavajoConfig().getCurrentCPUload();
			this.threadCount = DispatcherFactory.getInstance().getAccessSet().size();
		} catch (Throwable t) {
			// Do nothing...
		}
	}
	
	public Access(int accessID, int userID, int serviceID, String rpcUser,
			String rpcName, String userAgent, String ipAddress,
			String hostName,
			boolean betaUser, Object certificate) {

		
		myThread = Thread.currentThread();
		
		synchronized (mutex) {
			accessCount++;
			this.accessID = created.getTime() + "-" + accessCount;
			//System.err.println("accessID " + this.accessID + ", WS = " + rpcName + ", USER = " + rpcUser);
		}
		this.userID = userID;
		this.serviceID = serviceID;
		this.rpcName = rpcName;
		this.rpcUser = rpcUser;
		this.userAgent = userAgent;
		this.hostName = hostName;
		this.ipAddress = ipAddress;
		this.betaUser = betaUser;
		this.userCertificate = certificate;
		setCurrentSystemLoads();
	}
	
	public Access(int userID, int serviceID, String rpcUser,
			String rpcName, String userAgent, String ipAddress,
			String hostName, Object certificate) {
		this(0, userID, serviceID, rpcUser, rpcName, userAgent, ipAddress, hostName, certificate);
	}
	
	public Access(int accessID, int userID, int serviceID, String rpcUser,
			String rpcName, String userAgent, String ipAddress,
			String hostName, Object certificate) {

		
		myThread = Thread.currentThread();
		
		synchronized (mutex) {
			accessCount++;
			this.accessID = created.getTime() + "-" + accessCount;
			//System.err.println("accessID " + this.accessID + ", WS = " + rpcName + ", USER = " + rpcUser);
		}
		this.userID = userID;
		this.serviceID = serviceID;
		this.rpcName = rpcName;
		this.rpcUser = rpcUser;
		this.userAgent = userAgent;
		this.hostName = hostName;
		this.ipAddress = ipAddress;
		this.betaUser = false;
		this.userCertificate = certificate;
		setCurrentSystemLoads();
		
	}

	
	/**
	 * Clone an Access object without cloning requestNavajo and responseNavajo.
	 * 
	 * @return
	 */
	public Access cloneWithoutNavajos() {
		Access a = new Access();
		
		a.created = this.created;
		a.threadCount = this.threadCount;
		a.cpuload = this.cpuload;
		a.accessID = this.accessID;
		a.userID = this.userID;
		a.serviceID = this.serviceID;
		a.rpcName = this.rpcName;
		a.rpcPwd = this.rpcPwd;
		a.rpcUser = this.rpcUser;
		a.userAgent = this.userAgent;
		a.ipAddress = this.ipAddress;
		a.hostName = this.hostName;
		a.betaUser = this.betaUser;
		a.totaltime = this.totaltime;
		a.parseTime = this.parseTime;
		a.authorisationTime = this.authorisationTime;
		a.clientTime = this.clientTime;
		a.processingTime = this.processingTime;
		a.requestEncoding = this.requestEncoding;
		a.compressedReceive = this.compressedReceive;
		a.compressedSend = this.compressedSend;
		a.isFinished = this.isFinished;
		a.contentLength = this.contentLength;
		a.clientToken = this.clientToken;
		a.clientInfo = this.clientInfo;
		a.userCertificate = this.userCertificate;
		a.piggyBackData = this.piggyBackData;
		a.myException = this.myException;
		a.mapStatistics = this.mapStatistics;
		a.consoleOutput = this.consoleOutput;
		a.consoleContent = this.consoleContent;
		
		return a;
	}
	/**
	 * Dummy access.
	 */
	public Access() {
		myThread = Thread.currentThread();
	}
	
	protected final void setUserCertificate(Object cert) {
		userCertificate = cert;
	}

	public final Object getUserCertificate() {
		return userCertificate;
	}

	/*
	 * 
	 */
	@Deprecated
	public final void setLazyMessages(LazyMessageImpl h) {
		this.lazyMap = h;
	}

	@Deprecated
	public final LazyMessageImpl getLazyMessages() {
		return this.lazyMap;
	}

	public final Message getCurrentOutMessage() {
		return currentOutMessage;
	}

	public final void setCurrentOutMessage(Message currentOutMessage) {
		this.currentOutMessage = currentOutMessage;
	}

	public final void setFinished() {
		isFinished = true;
		totaltime = (int) (System.currentTimeMillis() - created.getTime());
	}

	public final boolean isFinished() {
		return isFinished;
	}

	public int getTotaltime() {
		return totaltime;
	}

	public Navajo getInDoc() {
		return inDoc;
	}

	public void setInDoc(Navajo inDoc) {
		this.inDoc = inDoc;
	}

	public int getThreadCount() {
		return threadCount;
	}

	public void setThreadCount(int threadCount) {
		this.threadCount = threadCount;
	}

	public void storeStatistics(Header h) {
		if (h!=null) {
			h.setHeaderAttribute("accessId", this.accessID);
			h.setHeaderAttribute("serverTime",""+getTotaltime());
			h.setHeaderAttribute("authorisationTime",""+authorisationTime);
			h.setHeaderAttribute("requestParseTime",""+parseTime);
			h.setHeaderAttribute("processingTime",""+processingTime);
			h.setHeaderAttribute("threadCount", this.threadCount+"");
			h.setHeaderAttribute("cpuload", cpuload+"");
		}
	}

	public HashMap<Integer, MapStatistics> getMapStatistics() {
		return mapStatistics;
	}

	public void addPiggybackData(Map<?,?> element) {
		if (piggyBackData==null) {
			piggyBackData = new HashSet<Map<?,?>>();
		}
		piggyBackData.add(element);
	}

	public Set<?> getPiggybackData() {
		return piggyBackData;
	}

	public String getClientInfo() {
		return this.clientInfo;
	}
	
	public String getAgentId() {
		// First try Jabber.
		String agentid = JabberWorkerFactory.getInstance().getAgentId(rpcUser + "-" + clientToken);
		if ( agentid != null && !agentid.equals("") ) {
			return agentid;
		} else {
			// Try using webservice navajo/ProcessGetAgent.
			try {
				Navajo doc = NavajoFactory.getInstance().createNavajo();
				Message m = NavajoFactory.getInstance().createMessage(doc, "Access");
				doc.addMessage(m);
				Property p = NavajoFactory.getInstance().createProperty(doc, "AccessId", Property.STRING_PROPERTY, 
						rpcUser + "-" + clientToken, 0, "", "out");
				m.addProperty(p);
				Header h = NavajoFactory.getInstance().createHeader(doc, "navajo/ProcessGetAgent", "ME", "ME", -1);
				doc.addHeader(h);
				Navajo result = DispatcherFactory.getInstance().handle(doc, true);
				if ( result != null ) {
					return result.getProperty("/Access/AgentId").getValue();
				}
			} catch (Throwable t) {
			}
		}
		return "[unknown]";
	}
	
	public String getClientToken() {
		return clientToken ;
	}

	public void setClientToken(String clientToken) {
		this.clientToken = clientToken;
	}

	public Thread getThread() {
		return myThread;
	}

	public String getAccessID() {
		return this.accessID;
	}

	public void kill() {
		if ( myScript != null ) {
			myScript.kill();
		}
	}

	public void load(Access access) throws MappableException, UserException {
		
	}

	public void store() throws MappableException, UserException {
		if ( myScript != null ) {
			myScript.store();
		}
	}

	public String getRpcUser() {
		return rpcUser;
	}

	public int getProcessingTime() {
		return processingTime;
	}

	public String getRequestEncoding() {
		return requestEncoding;
	}

	public String getRpcName() {
		return rpcName;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public java.util.Date getCreated() {
		return created;
	}

	public String getHostName() {
		return hostName;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public double getCpuload() {
		return cpuload;
	}

	public void setCpuload(double cpuload) {
		this.cpuload = cpuload;
	}

	public Binary getRequestNavajo() throws UserException {
		Binary b = new Binary();
		if ( inDoc != null ) {
			try { 
				OutputStream os = b.getOutputStream();
				inDoc.write(os);
				os.close();
			} catch (Throwable t) {
				throw new UserException(-1, t.getMessage(), t);
			}
		}
		return b;
	}

	public Binary getResponseNavajo() throws UserException {
		Binary b = new Binary();
		if ( outputDoc != null ) {
			try { 
				OutputStream os = b.getOutputStream();
				outputDoc.write(os);
				os.close();
			} catch (Throwable t) {
				throw new UserException(-1, t.getMessage(), t);
			}
		}
		return b;
	}

	/**
	 * Gets the current console buffer.
	 * 
	 * @return
	 */
	public String getConsoleOutput() {
		return consoleContent.toString();
	}

	/**
	 * Writes a string to the access' object private console.
	 * 
	 * @param s
	 */
	private final void writeToConsole(String s) {
		consoleOutput.write(s);
	}
	
	/**
	 * Returns the access' object private console writer.
	 * 
	 * @return
	 */
	private final PrintWriter getConsoleWriter() {
		return consoleOutput;
	}
	
	/**
	 * Static method that does not check for existence of Access object.
	 * If Access is null, the output is written to System.err
	 * 
	 * @param a
	 * @param s
	 */
	public final static void writeToConsole(final Access a, final String s) {
		if ( a != null ) {
			a.writeToConsole(s);
		} 
		System.err.print(s);
	}
	
	/**
	 * Static method that checks for existence of Access object.
	 * If Access is null, the output is written to System.err
	 * 
	 * @param a
	 * @return
	 */
	public final static PrintWriter getConsoleWriter(final Access a) {
		if ( DispatcherFactory.getInstance().getNavajoConfig().isEnableStatisticsRunner() && a != null ) {
			return a.getConsoleWriter();
		} else {
			return new PrintWriter(System.err);
		}
	}

	/**
	 * Checks whether this Access objects needs full debug.
	 * 
	 * @return
	 */
	public boolean isDebugAll() {
		return debugAll;
	}

	/**
	 * Setter to indicate whether this Access object needs full debug.
	 * 
	 * @param debugAll
	 */
	public void setDebugAll(boolean debugAll) {
		this.debugAll = debugAll;
	}

	public void setClientInfo(String clientInfo) {
		this.clientInfo = clientInfo;
	}
	
}