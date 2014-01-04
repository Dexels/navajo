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

package com.dexels.navajo.script.api;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Header;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Selection;
import com.dexels.navajo.document.types.Binary;

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

	final static Logger logger = LoggerFactory.getLogger(Access.class.getName());

	
	@SuppressWarnings("unused")
	private static final String VERSION = "$Id$";

	public java.util.Date created = new java.util.Date();
	protected static int accessCount = 0;
	public int threadCount = 0;
	public double cpuload = -1.0;
	public String accessID = "";
	public String parentAccessId = "";
	public int userID;
	public int serviceID;
	public String rpcName = "";
	public String rpcPwd = "";
	public String rpcUser = "";
	public String userAgent;
	public String ipAddress;
	public String hostName;
	public boolean betaUser = false;
	public transient CompiledScriptInterface myScript = null;
	public int queueSize;
	public String queueId;
	
	/**
	 * Response time breakdown
	 */
	private int totaltime;
	public int parseTime;
	public int queueTime;
	public int authorisationTime;
	public int clientTime;
	public int processingTime;
	public int beforeServiceTime;
	public int afterServiceTime;
	
	
	public String requestEncoding;
	public boolean compressedReceive = false;
	public boolean compressedSend = false;
	public boolean isFinished = false;
	public int contentLength;
	public transient Binary requestNavajo;
	public transient Binary responseNavajo;
	public boolean debugAll;
	
	private String requestUrl;

	// Flag to indicate that during the execution of the webservice, break was called.
	private boolean breakWasSet = false;
	
	private transient Object scriptEnvironment = null;
	

	private transient Throwable myException;
	private Navajo outputDoc;
	private Navajo inDoc;
	// The mergedDoc can be used to merge a previously set Navajo with the outputDoc.
	// If the mergeDoc is not empty, it will ALWAYS be merged when setOutputDoc is called.
	private transient Navajo mergedDoc;

	private transient Message currentOutMessage;
	private transient Object userCertificate;
	private static Object mutex = new Object();
	private transient Set<Map<?,?>> piggyBackData = null;
	private String clientToken = null;
	private String clientInfo = null;
	
	/**
	 * Create a private logging console for this access object.
	 * TODO: Maybe restrict maximum size of console... or use Binary...
	 */
	private transient StringWriter consoleContent = new StringWriter();
	private transient PrintWriter consoleOutput = new PrintWriter(consoleContent);

	private transient String waitingForPreviousRequest = null;
	private transient Thread myThread = null;

	private transient HashMap<Integer, MapStatistics> mapStatistics = null;

	// In order to manage continuations, I might need the original runnable.
	// This service (and it's Access object) may be used by many different threads during its execution, but only
	// the original knows how to commit the data and finalize the network connection.
	protected transient TmlRunnable originalRunnable;

	private String instance;


	
	public MapStatistics createStatistics() {
		MapStatistics ms = new MapStatistics();
		if ( mapStatistics == null ) { // First map.
			mapStatistics = new HashMap<Integer, MapStatistics>();
		}
		Integer count = new Integer(mapStatistics.size());
		mapStatistics.put(count, ms);

		return ms;
	}

	

	public boolean isBreakWasSet() {
		return breakWasSet;
	}

	public void setBreakWasSet(boolean breakWasSet) {
		this.breakWasSet = breakWasSet;
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
				if ( append ) {
					this.mergedDoc.appendDocBuffer(mergedDoc);
				} else {
					this.mergedDoc.merge(mergedDoc);
				}
			} catch (NavajoException e) {
				logger.error("Error: ", e);
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
				logger.error("Error: ", e);
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
	public void setCompiledScript(CompiledScriptInterface cs) {
		this.myScript = cs;
	}

	/**
	 * Get the compiled script used for handling this access.
	 * (Used from within scripts)
	 * @return
	 */
	public CompiledScriptInterface getMyScript() {
		return myScript;
	}

	/**
	 * Get the compiled script used for handling this access.
	 * @return
	 */
	public CompiledScriptInterface getCompiledScript() {
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

	/**
	 * @param accessID  
	 */
	public Access(int accessID, int userID, int serviceID, String rpcUser,
			String rpcName, String userAgent, String ipAddress,
			String hostName,
			boolean betaUser, Object certificate) {

		
		this();
		accessCount++;
		this.accessID = created.getTime() + "-" + accessCount;
		//System.err.println("accessID " + this.accessID + ", WS = " + rpcName + ", USER = " + rpcUser);
		this.userID = userID;
		this.serviceID = serviceID;
		this.rpcName = rpcName;
		this.rpcUser = rpcUser;
		this.userAgent = userAgent;
		this.hostName = hostName;
		this.ipAddress = ipAddress;
		this.betaUser = betaUser;
		this.userCertificate = certificate;
	}
	
	public Access(int userID, int serviceID, String rpcUser,
			String rpcName, String userAgent, String ipAddress,
			String hostName, Object certificate) {
		this(0, userID, serviceID, rpcUser, rpcName, userAgent, ipAddress, hostName, certificate);
	}
	
	/**
	 * Nobody cares about any supplied access ids, so better not to supply them
	 */
	
	@Deprecated
	public Access(int accessID, int userID, int serviceID, String rpcUser,
			String rpcName, String userAgent, String ipAddress,
			String hostName, Object certificate) {

		this();
		accessCount++;
		this.accessID = created.getTime() + "-" + accessCount;
		this.userID = userID;
		this.serviceID = serviceID;
		this.rpcName = rpcName;
		this.rpcUser = rpcUser;
		this.userAgent = userAgent;
		this.hostName = hostName;
		this.ipAddress = ipAddress;
		this.betaUser = false;
		this.userCertificate = certificate;
		
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
		a.queueTime = this.queueTime;
		a.queueSize = this.queueSize;
		a.queueId = this.queueId;
		a.processingTime = this.processingTime;
		a.beforeServiceTime = this.beforeServiceTime;
		a.afterServiceTime = this.afterServiceTime;
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
		a.parentAccessId = this.parentAccessId;
		a.debugAll = this.debugAll;
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



	public final Message getCurrentOutMessage() {
		return currentOutMessage;
	}

	public final Message getCurrentInMessage() {
		if ( myScript != null ) {
			return myScript.getCurrentInMsg();
		} else {
			return null;
		}
	}
	
	public final Selection getCurrentInSelection() {
		if ( myScript != null ) {
			return myScript.getCurrentSelection();
		} else {
			return null;
		}
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

	public int getQueueTime() {
		return queueTime;
	}

	public int getBeforeServiceTime() {
		return beforeServiceTime;
	}

	public void setBeforeServiceTime(int beforeServiceTime) {
		this.beforeServiceTime = beforeServiceTime;
	}

	public int getAfterServiceTime() {
		return afterServiceTime;
	}

	public void setAfterServiceTime(int afterServiceTime) {
		this.afterServiceTime = afterServiceTime;
	}
	
	public Navajo getInDoc() {
		return inDoc;
	}

	public void setInDoc(Navajo in) {
		
		if ( in == null ) {
			return;
		}
		
		this.inDoc = in;
		if ( in.getHeader() != null ) {
			// Check for parent access id header.
			String s = in.getHeader().getHeaderAttribute("parentaccessid");
			if ( s != null && !s.equals("") ) {
				setParentAccessId(s);
			}
		}
		// Check if __parms__ exists.
		Message msg = inDoc.getMessage("__parms__");
		if (msg == null) {
			msg = NavajoFactory.getInstance().createMessage(inDoc, "__parms__");
			try {
				inDoc.addMessage(msg);
			} catch (NavajoException e) {
			}
		}
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
			h.setHeaderAttribute("queueTime",""+queueTime);
			h.setHeaderAttribute("queueId",""+queueId);
			h.setHeaderAttribute("queueSize",""+queueSize);
			h.setHeaderAttribute("processingTime",""+processingTime);
			h.setHeaderAttribute("beforeServiceTime",""+beforeServiceTime);
			h.setHeaderAttribute("afterServiceTime",""+afterServiceTime);
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
		return "[deprecated]";
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

	@Override
	public void kill() {
		if ( myScript != null ) {
			myScript.kill();
		}
	}

	@Override
	public void load(Access access) throws MappableException, UserException {
		
	}

	@Override
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
		if(s!=null) {
			logger.info(s.trim());
		}
//		System.err.print(s);
	}
	
	/**
	 * Static method that checks for existence of Access object.
	 * If Access is null, the output is written to System.err
	 * 
	 * @param a
	 * @return
	 */
	public final static PrintWriter getConsoleWriter(final Access a) {
		return new PrintWriter(System.err);
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
	
	public String getParentAccessId() {
		return parentAccessId;
	}

	public void setParentAccessId(String parentAccessId) {
		this.parentAccessId = parentAccessId;
	}
	
	public void setScriptEnvironment(Object scriptEnvironment) {
		this.scriptEnvironment = scriptEnvironment;
	}


	public Object getScriptEnvironment() {
		return this.scriptEnvironment;
	}

	
	public String getRequestUrl() {
		if(requestUrl==null) {
			String hardCoded = "http://spiritus.dexels.nl:9080/JsSportlink/Comet";
			System.err.println("\n\n\nWARNING HARDCODED URL:"+hardCoded+"!!!!\n\n");
			return hardCoded;
		}
		return requestUrl;
	}

	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}



	public TmlRunnable getOriginalRunnable() {
		return originalRunnable;
	}



	public void setOriginalRunnable(TmlRunnable originalRunnable) {
		this.originalRunnable = originalRunnable;
	}



	public int getQueueSize() {
		return queueSize;
	}



	public void setQueueSize(int queueSize) {
		this.queueSize = queueSize;
	}



	public String getQueueId() {
		return queueId;
	}

	public boolean needsFullAccessLog() {
		if ( isDebugAll() || ( getCompiledScript() != null && getCompiledScript().isDebugAll() ) ) {
			return true;
		} else {
			return false;
		}
	}


	public void setQueueId(String queueId) {
		this.queueId = queueId;
	}


	public void setInstance(String instance) {
		this.instance = instance;
	}

	public String getInstance() {
		return this.instance;
	}

}
