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

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.dexels.navajo.document.*;
import com.dexels.navajo.mapping.CompiledScript;
import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;

public final class Access implements java.io.Serializable, Mappable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7782160335447961196L;

	private static final String VERSION = "$Id$";

	public java.util.Date created = new java.util.Date();
	protected static int accessCount = 0;
	public int threadCount = 0;
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
	private transient Dispatcher myDispatcher;
	public transient CompiledScript myScript = null;
	private int totaltime;
	public int parseTime;
	public int authorisationTime;
	public int clientTime;
	//public int processingTime;
	public String requestEncoding;
	public boolean compressedReceive = false;
	public boolean compressedSend = false;
	public boolean isFinished = false;
	public int contentLength;

	private Throwable myException;
	private Navajo outputDoc;
	private Navajo inDoc;
	private LazyMessageImpl lazyMap;
	private Message currentOutMessage;
	private Object userCertificate;
	private static Object mutex = new Object();
	private Set piggyBackData = null;
	private String clientToken = null;

	private String waitingForPreviousRequest = null;
	private transient Thread myThread = null;


	//private HashMap mapStatistics = null;

//	public MapStatistics createStatistics() {
//	MapStatistics ms = new MapStatistics();
//	if ( mapStatistics == null ) { // First map.
//	mapStatistics = new HashMap();
//	}
//	Integer count = new Integer(mapStatistics.size());
//	mapStatistics.put(count, ms);

//	return ms;
//	}

//	public void updateStatistics(MapStatistics ms, int levelId, String mapName, long totalTime, int elementCount, boolean isArrayElement) {

//	ms.levelId = levelId;
//	ms.mapName = mapName;
//	ms.elementCount = elementCount;
//	ms.totalTime = totalTime;
//	ms.isArrayElement = isArrayElement;

//	}

	public Navajo getOutputDoc() {
		return outputDoc;
	}

	public boolean hasCertificate() {
		return (userCertificate != null);
	}

	public void setOutputDoc(Navajo n) {
		outputDoc = n;
	}

	public void setWaitingForPreviousResponse(String id) {
		this.waitingForPreviousRequest = id;
	}

	public String getWaitingForPreviousResponse() {
		return this.waitingForPreviousRequest;
	}

	public void setCompiledScript(CompiledScript cs) {
		this.myScript = cs;
	}

	public CompiledScript getMyScript() {
		System.err.println("In getMyScript: " + myScript);
		return myScript;
	}

	public CompiledScript getCompiledScript() {
		return myScript;
	}

	public void setException(Throwable e) {
		this.myException = e;
	}

	public Throwable getException() {
		return this.myException;
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

	}

	protected final void setUserCertificate(Object cert) {
		userCertificate = cert;
	}

	public final Object getUserCertificate() {
		return userCertificate;
	}

	protected final void setMyDispatcher(Dispatcher d) {
		this.myDispatcher = d;
	}

	public final Dispatcher getDispatcher() {
		return this.myDispatcher;
	}

	public final void setLazyMessages(LazyMessageImpl h) {
		this.lazyMap = h;
	}

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
			h.setAttribute("serverTime",""+getTotaltime());
			h.setAttribute("accessId", this.accessID);
			h.setAttribute("requestParseTime",""+parseTime);
		}
	}

//	public HashMap getMapStatistics() {
//	return mapStatistics;
//	}

	public void addPiggybackData(Map element) {
		if (piggyBackData==null) {
			piggyBackData = new HashSet();
		}
		piggyBackData.add(element);
	}

	public Set getPiggybackData() {
		return piggyBackData;
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
		myScript.kill();
	}

	public void load(Parameters parms, Navajo inMessage, Access access, NavajoConfig config) throws MappableException, UserException {
		myScript.load(null, null, null, null);
	}

	public void store() throws MappableException, UserException {
		myScript.store();
	}

}