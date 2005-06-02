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


import com.dexels.navajo.document.*;
import com.dexels.navajo.document.LazyMessageImpl;
import com.dexels.navajo.mapping.CompiledScript;

public final class Access
    implements java.io.Serializable {

  public java.util.Date created = new java.util.Date();
  public static int accessCount = 0;
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
  private Dispatcher myDispatcher;
  private CompiledScript myScript = null;
  private int totaltime;
  public int parseTime;
  public int authorisationTime;
  public int processingTime;
  public String requestEncoding;
  public boolean compressedReceive = false;
  public boolean compressedSend = false;
  public int contentLength;

  private Throwable myException;
  private Navajo outputDoc;
  private Navajo inDoc;
  private LazyMessageImpl lazyMap;
  private Message currentOutMessage;
  private Object userCertificate;
  private static Object mutex = new Object();

  public Navajo getOutputDoc() {
    return outputDoc;
  }

  public boolean hasCertificate() {
    return (userCertificate != null);
  }

  public void setOutputDoc(Navajo n) {
    outputDoc = n;
  }

  public void setCompiledScript(CompiledScript cs) {
    this.myScript = cs;
  }

  public CompiledScript getCompiledScript() {
    return this.myScript;
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

    synchronized (mutex) {
      accessCount++;
      this.accessID = System.currentTimeMillis() + "-" + accessCount;
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
    synchronized (mutex) {
      accessCount++;
      this.accessID = System.currentTimeMillis() + "-" + accessCount;
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
    totaltime = (int) (System.currentTimeMillis() - created.getTime());
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
}