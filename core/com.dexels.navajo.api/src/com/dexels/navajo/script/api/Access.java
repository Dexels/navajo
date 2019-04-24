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

import java.io.IOException;
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
import com.dexels.navajo.document.Operation;
import com.dexels.navajo.document.Selection;
import com.dexels.navajo.document.types.Binary;

/**
 * An Access object is created for each web service access to the Navajo server.
 * An Access object is a handle for all relevant information relating to a
 * specific web service access: the request Navajo, the response Navajo, but
 * also basic information like the username, web service name, time of creation,
 * processing times, etc.
 * 
 * @author arjen
 *
 */
public final class Access implements java.io.Serializable, Mappable {

    /**
	 * 
	 */
    private static final long serialVersionUID = -7782160335447961196L;

    private static final Logger logger = LoggerFactory.getLogger(Access.class.getName());

    public static final int EXIT_OK = 1;
    public static final int EXIT_VALIDATION_ERR = 2;
    public static final int EXIT_BREAK = 3;
    public static final int EXIT_USEREXCEPTION = 4;
    public static final int EXIT_EXCEPTION = 5;
    public static final int EXIT_ENTITY_CONFLICT = 10;
    public static final int EXIT_ENTITY_ETAG = 11;
    
    
    // The following exit codes can be ignored in statistics
    public static final int EXIT_SCRIPT_NOT_FOUND = 6;
    public static final int EXIT_AUTH_EXECPTION = 21;
    
    public static final String LEGACY_APPLICATION = "legacy";
    
    @SuppressWarnings("unused")
    private static final String VERSION = "$Id$";

    public java.util.Date created = new java.util.Date();
    private static int accessCount = 0;
    
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
    public String application = LEGACY_APPLICATION;
    public String organization;
    public String clientDescription;
    public boolean betaUser = false;
    public transient CompiledScriptInterface myScript = null;
    public transient Operation myOperation = null;
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
    public int transferTime;
    public int processingTime;
    public int beforeServiceTime;
    public int afterServiceTime;
    public String requestEncoding;
    public boolean compressedReceive = false;
    public boolean compressedSend = false;
    public boolean isFinished = false;
    public int contentLength;
    public boolean debugAll;

    // Flag to indicate that during the execution of the webservice, break was called.
    private boolean breakWasSet = false;

    private transient Object scriptEnvironment = null;
    private String requestUrl;
    private int exitCode;
    private transient Throwable myException;
    private Navajo outputDoc;
    private Navajo inDoc;
    // The mergedDoc can be used to merge a previously set Navajo with the
    // outputDoc.
    // If the mergeDoc is not empty, it will ALWAYS be merged when setOutputDoc
    // is called.
    private transient Navajo mergedDoc;
    private transient Message currentOutMessage;
    private transient Object userCertificate;
    private transient Set<Map<String,String>> piggyBackData = null;
    private String clientToken = null;
    private String clientInfo = null;
    private String tenant;
    private String scriptLogging = null;
    
    /**
     * Create a private logging console for this access object. Maybe
     * restrict maximum size of console... or use Binary...
     */
    private transient StringWriter consoleContent = new StringWriter();
    private transient PrintWriter consoleOutput = new PrintWriter(consoleContent);

    private transient String waitingForPreviousRequest = null;
    private transient Thread myThread = null;

    private transient HashMap<Integer, MapStatistics> mapStatistics = null;

    // In order to manage continuations, I might need the original runnable.
    // This service (and it's Access object) may be used by many different
    // threads during its execution, but only the original knows how to 
    // commit the data and finalise the network connection.
    private transient TmlRunnable originalRunnable;
  
    public Access(int userID, int serviceID, String rpcUser, String rpcName, String userAgent, String ipAddress, String hostName, Object certificate,
            boolean betaUser, String accessID) {

        this();

        this.accessID = accessID;
        if (accessID == null) {
            synchronized (Access.class) {
            	accessCount++;
                this.accessID = created.getTime() + "-" + accessCount;
            }
        }
        this.userID = userID;
        this.serviceID = serviceID;
        this.rpcName = rpcName;
        this.rpcUser = rpcUser;
        this.userAgent = userAgent;
        this.hostName = hostName;
        this.ipAddress = ipAddress;
        this.betaUser = betaUser;
        userCertificate = certificate;

    }


    public MapStatistics createStatistics() {
        MapStatistics ms = new MapStatistics();
        if (mapStatistics == null) { // First map.
            mapStatistics = new HashMap<>();
        }
        Integer count = Integer.valueOf(mapStatistics.size());
        mapStatistics.put(count, ms);

        return ms;
    }

    public boolean isBreakWasSet() {
        return breakWasSet;
    }

    public void setBreakWasSet(boolean breakWasSet) {
        this.breakWasSet = breakWasSet;
    }

    public void updateStatistics(MapStatistics ms, int levelId, String mapName, int totalTime, int elementCount,
            boolean isArrayElement, int navajoLineNr) {
        ms.levelId = levelId;
        ms.mapName = mapName;
        ms.elementCount = elementCount;
        ms.totalTime = totalTime;
        ms.isArrayElement = isArrayElement;
        ms.linenr = navajoLineNr;
    }

    /**
     * Gets the Navajo that is associated with the so called merged document. A
     * merged document will ALWAYS be merged with the Navajo outputDoc. If the
     * current mergedDoc is null, a new Navajo will be returned.
     * 
     * @return
     */
    public Navajo getMergedDoc() {
        if (mergedDoc != null) {
            return mergedDoc;
        } else {
            mergedDoc = NavajoFactory.getInstance().createNavajo();
            return mergedDoc;
        }
    }

    /**
     * Sets the Navajo that is going to be used as a merged document, i.e. a
     * Navajo document that will be merged with the outDoc Navajo.
     * 
     * @param mergedDoc
     * @param append
     *            , if set to true Navajo mergedDoc is appended, messages of
     *            original are overwritten. if set to false Navajo mergedDoc is
     *            merged, messages of original are merged with mergedDoc,
     *            properties of mergedDoc have precedence over original.
     */
    public void setMergedDoc(Navajo mergedDoc, boolean append) {
        if (mergedDoc == null) {
            this.mergedDoc = null;
            return;
        }

        if (outputDoc != null) {
            try {
                if (append) {
                    outputDoc.appendDocBuffer(mergedDoc);
                } else {
                    outputDoc.merge(mergedDoc);
                }
            } catch (Exception e) {
                logger.error("Exception on merging documents: {}", e);
            }
            return;
        }
        if (this.mergedDoc == null) {
            this.mergedDoc = mergedDoc;
        } else {
            try {
                if (append) {
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
     * Return the Navajo response (being) created. Return empty Navajo if no
     * current output doc is present yet.
     * 
     * @return
     */
    public Navajo getOutputDoc() {
        return outputDoc;
    }

    /**
     * Checks whether a security token was used for this access.
     * 
     * @return
     */
    public boolean hasCertificate() {
        return (userCertificate != null);
    }

    /**
     * Sets the response Navajo to be used for generating the response.
     * 
     * @param n
     */
    public void setOutputDoc(Navajo n) {
        if (mergedDoc != null) {
            try {
                if (n != null) {
                    mergedDoc.appendDocBuffer(n);
                }
                outputDoc = mergedDoc;
            } catch (NavajoException e) {
                logger.error("Error: ", e);
            }
        } else {
            outputDoc = n;
        }
    }

    /**
     * Inform the access object that it is waiting for the termination of a
     * previous access.
     * 
     * @param id
     *            , a unique access id
     */
    public void setWaitingForPreviousResponse(String id) {
        waitingForPreviousRequest = id;
    }

    /**
     * Returns the access id for which this access is waiting
     * 
     * @return, a unique access id
     */
    public String getWaitingForPreviousResponse() {
        return waitingForPreviousRequest;
    }

    /**
     * Sets the compiled script that is used to handle this access.
     * 
     * @param cs
     */
    public void setCompiledScript(CompiledScriptInterface cs) {
        myScript = cs;
    }

    /**
     * Get the compiled script used for handling this access. (Used from within
     * scripts)
     * 
     * @return
     */
    public CompiledScriptInterface getMyScript() {
        return myScript;
    }

    /**
     * Get the compiled script used for handling this access.
     * 
     * @return
     */
    public CompiledScriptInterface getCompiledScript() {
        return myScript;
    }
    
    public Operation getOperation() {
        return myOperation;
    }
    
    public void setOperation(Operation op) {
        myOperation = op;
    }
    
    

    /**
     * Method to be used when an exception has occurred while processing this
     * access.
     * 
     * @param e
     */
    public void setException(Throwable e) {
        myException = e;
    }

    /**
     * Optionally returns an exception if one occurred.
     * 
     * @return
     */
    public Throwable getException() {
        return myException;
    }

    /**
     * Clone an Access object without cloning requestNavajo and responseNavajo.
     * 
     * @return
     */
    public Access cloneWithoutNavajos() {
        Access a = new Access();

        a.created = created;
        a.threadCount = threadCount;
        a.cpuload = cpuload;
        a.accessID = accessID;
        a.userID = userID;
        a.serviceID = serviceID;
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
        a.exitCode = this.exitCode;
        a.tenant = this.tenant;
        a.application = this.application;
        a.organization = this.organization;
        a.clientDescription = this.clientDescription;
        a.scriptLogging = this.scriptLogging;
        return a;
    }

    /**
     * Dummy access.
     */
    public Access() {
        myThread = Thread.currentThread();
    }

    public void setUserCertificate(Object cert) {
        userCertificate = cert;
    }

    public Object getUserCertificate() {
        return userCertificate;
    }

    public Message getCurrentOutMessage() {
        return currentOutMessage;
    }

    public Message getCurrentInMessage() {
        if (myScript != null) {
            return myScript.getCurrentInMsg();
        } else {
            return null;
        }
    }

    public Selection getCurrentInSelection() {
        if (myScript != null) {
            return myScript.getCurrentSelection();
        } else {
            return null;
        }
    }

    public void setCurrentOutMessage(Message currentOutMessage) {
        this.currentOutMessage = currentOutMessage;
    }

    public void setFinished() {
        isFinished = true;
        totaltime = (int) (System.currentTimeMillis() - created.getTime());
    }

    public boolean isFinished() {
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

        if (in == null) {
            return;
        }

        this.inDoc = in;
        if (in.getHeader() != null) {
            // Check for parent access id header.
            String s = in.getHeader().getHeaderAttribute("parentaccessid");
            if (s != null && !s.equals("")) {
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
            	logger.error("Error: ", e);
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
        if (h != null) {
            h.setHeaderAttribute("accessId", this.accessID);
            h.setHeaderAttribute("serverTime", "" + getTotaltime());
            h.setHeaderAttribute("authorisationTime", "" + authorisationTime);
            h.setHeaderAttribute("requestParseTime", "" + parseTime);
            h.setHeaderAttribute("queueTime", "" + queueTime);
            h.setHeaderAttribute("queueId", "" + queueId);
            h.setHeaderAttribute("queueSize", "" + queueSize);
            h.setHeaderAttribute("processingTime", "" + processingTime);
            h.setHeaderAttribute("beforeServiceTime", "" + beforeServiceTime);
            h.setHeaderAttribute("afterServiceTime", "" + afterServiceTime);
            h.setHeaderAttribute("threadCount", this.threadCount + "");
            h.setHeaderAttribute("cpuload", cpuload + "");
        }
    }

    public Map<Integer, MapStatistics> getMapStatistics() {
        return mapStatistics;
    }

    public void addPiggybackData(Map<String, String> element) {
        if (piggyBackData == null) {
            piggyBackData = new HashSet<>();
        }
        piggyBackData.add(element);
    }

    public Set<Map<String,String>> getPiggybackData() {
        return piggyBackData;
    }

    public String getClientInfo() {
        return this.clientInfo;
    }

    public String getClientToken() {
        return clientToken;
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
        if (myScript != null) {
            myScript.kill();
        }
    }

    @Override
    public void load(Access access) throws MappableException, UserException {

    }

    @Override
    public void store() throws MappableException, UserException {
        if (myScript != null) {
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
    

    public String getApplication() {
        return application;
    }


    public void setApplication(String application) {
        if (application == null || "".equals(application.trim()) || application.equals(this.application) ) {
            return;
        }

        this.application = application;
        
    }
    
    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }
    
    public String getClientDescription() {
        return clientDescription;
    }

    public void setClientDescription(String clientDescription) {
        this.clientDescription = clientDescription;
    }


    public Binary getRequestNavajo() throws UserException {
        Binary b = new Binary();
        if (inDoc != null) {
            try {
                OutputStream os = b.getOutputStream();
                inDoc.write(os);
                os.close();
            } catch (IOException t) {
                throw new UserException(-1, t.getMessage(), t);
            }
        }
        return b;
    }

    public Binary getResponseNavajo() throws UserException {
        Binary b = new Binary();
        if (outputDoc != null) {
            try {
                OutputStream os = b.getOutputStream();
                outputDoc.write(os);
                os.close();
            } catch (IOException t) {
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
        consoleOutput.write("\n");
    }

    /**
     * Static method that does not check for existence of Access object.
     * 
     */
    public static final void writeToConsole(final Access a, final String s) {
        if (a != null) {
            a.writeToConsole(s);
        }
        if (s != null) {
            logger.debug(s.trim());
        }
    }

    public static final PrintWriter getConsoleWriter(final Access a) {
        if (a != null) {
            return a.consoleOutput;
        }
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

    public boolean logFullAccessLog() {
        return isDebugAll() || (getCompiledScript() != null && getCompiledScript().isDebugAll());
    }
    
    public boolean logRequestAccessLog() {
        return getCompiledScript() != null && getCompiledScript().debugRequest();
    }
    
    public boolean logResponseAccessLog() {
        return getCompiledScript() != null && getCompiledScript().debugResponse();
    }

    public void setQueueId(String queueId) {
        this.queueId = queueId;
    }

    public void setTenant(String instance) {
        this.tenant = instance;
    }

    public String getTenant() {
        return this.tenant;
    }

    public void addScriptLogging(String log) {
        if (scriptLogging == null) {
            this.scriptLogging = log;
            return;
        }
        this.scriptLogging += "\n";
        this.scriptLogging += log;
    }
    
    public String getScriptLogging() {
        return this.scriptLogging;
    }
 

    public int getExitCode() {
        return exitCode;
    }

    public void setExitCode(int exitCode) {
        this.exitCode = exitCode;
    }

}
