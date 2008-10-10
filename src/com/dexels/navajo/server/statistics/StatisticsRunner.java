package com.dexels.navajo.server.statistics;

import com.dexels.navajo.events.NavajoEvent;
import com.dexels.navajo.events.NavajoEventRegistry;
import com.dexels.navajo.events.types.AuditLogEvent;
import com.dexels.navajo.events.types.ChangeNotificationEvent;
import com.dexels.navajo.events.types.NavajoRequestEvent;
import com.dexels.navajo.events.types.NavajoResponseEvent;
import com.dexels.navajo.mapping.AsyncMappable;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.Dispatcher;
import com.dexels.navajo.server.DispatcherFactory;
import com.dexels.navajo.server.GenericThread;
import com.dexels.navajo.server.enterprise.statistics.StatisticsRunnerInterface;
import com.dexels.navajo.server.enterprise.statistics.StoreInterface;
import com.dexels.navajo.server.jmx.JMXHelper;
import com.dexels.navajo.util.AuditLog;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import javax.management.Notification;
import javax.management.NotificationListener;
import javax.management.monitor.MonitorNotification;

/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
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

/**
 * TODO:
 * Statistic Runner is responsible for storing Access objects AND for cleaning up Access, e.g. helping the Garbage Collector
 * a bit by e.g. nullifying unused objects.
 * 
 */
public final class StatisticsRunner extends GenericThread implements StatisticsRunnerMXBean, StatisticsRunnerInterface, NotificationListener {

  public int todoCount;
  public String storeClass;
  public static final String VERSION = "$Id$";
  
  private static volatile StatisticsRunner instance = null;
  private StoreInterface myStore = null;
  @SuppressWarnings("unchecked")
  private Map todo = new HashMap();
  private Set auditlogs = new HashSet();
  private int minAuditLevel = Level.WARNING.intValue();
  private static String id = "Navajo StatisticsRunner";
  
  private static Object semaphore = new Object();
  private boolean enabled = false;
  
  private int previousSize = 0;
  
  public StatisticsRunner() {
	 super(id);
  }
  
  public final static StatisticsRunner getInstance() {
	  return instance;
  }
  
  /**
   * Get an instance of the StatisticsRunner (singleton).
   *
   * @param storePath the file (or something else, e.g. JDBC url, datasource name, ...) location of the Navajo store.
   *
   * @return
   */
  @SuppressWarnings("unchecked")
  public final static StatisticsRunner getInstance(String storePath, Map parameters, String storeClass) throws Throwable {
	  
	  if ( storeClass == null ) {
		  throw new Exception("No store class specified");
	  }
	  
	  if ( instance != null ) {
		  return instance;
	  }
	  
	  synchronized (semaphore) {
		  if (instance == null) {

			  instance = new StatisticsRunner();
			  instance.enabled = true;
			  JMXHelper.registerMXBean(instance, JMXHelper.NAVAJO_DOMAIN, id);
			  JMXHelper.addGaugeMonitor(instance, JMXHelper.NAVAJO_DOMAIN, id, "TodoCount", new Integer(0), new Integer(30), 10000L);
			  
			  Class si = null;

			  si = Class.forName(storeClass);
			  instance.myStore = (StoreInterface) si.newInstance();
			  instance.myStore.setDatabaseParameters((parameters == null ? new HashMap() : parameters));
			  instance.myStore.setDatabaseUrl(storePath);
			  instance.storeClass = storeClass;
			  // Determine initial audit level.
			  String level = (String) parameters.get("auditlevel");
			  if ( level == null || level.equals("") ) {
				  level = Level.WARNING.getName();
			  }
			  instance.minAuditLevel =  Level.parse(level).intValue();
			  
			  instance.setSleepTime(100);
			  instance.startThread(instance);
			  
			  NavajoEventRegistry.getInstance().addListener(NavajoRequestEvent.class, instance);
			  NavajoEventRegistry.getInstance().addListener(NavajoResponseEvent.class, instance);
			  NavajoEventRegistry.getInstance().addListener(AuditLogEvent.class, instance);
			  
			  System.err.println("Started StatisticsRunner version $Id$ using store: " + instance.myStore.getClass().getName());
		  }
	  }
	  return instance;
  }

  /**
   * Override the inactive() method of GenericThread. Use wait/notify mechanism for activation instead of default sleep.
   * 
   */
  public synchronized void inactive() {
	  try {
		wait(60000);
	} catch (InterruptedException e) {
		
	}
  }
    
  /**
   * Main thread. Responsible for persisting queued access objects AND cleaning up
   * Access objects and explicitly activating GC.
   *
   */
  @SuppressWarnings("unchecked")
  public final void worker() {

	  HashMap copyOfTodo = null;
	  HashSet copyOfAuditLogs = null;
	  synchronized ( semaphore ) {
		  if ( todo.size() == 0 ) {
			  return;
		  }
		  int level = todo.size();
		  if ( level != previousSize && level > 30 ) {
			  sendHealthCheck(level, 30, Level.WARNING, "Todo list of statistics is rather large");
		  }
		  previousSize = level;
		  copyOfTodo = new HashMap(todo);
		  copyOfAuditLogs = new HashSet(auditlogs);
		  todo.clear();
		  auditlogs.clear();
	  }
	  myStore.storeAccess(copyOfTodo);
	  myStore.storeAuditLogs(copyOfAuditLogs);
	  copyOfTodo.clear();
	  copyOfAuditLogs.clear();
	
  }

  /**
   * Add an access object to the to-be-persisted-queue.
   *
   * @param a
   */
  @SuppressWarnings("unchecked")
  public final void addAccess(final Access a, AsyncMappable am) {
	  
	  Access myAccess;
	  
	  if (a.getException() == null && !DispatcherFactory.getInstance().getNavajoConfig().needsFullAccessLog(a) ) {
		  // Clone Access, do not include Navajo Request and Navajo Response objects.
		  myAccess = a.cloneWithoutNavajos();
	  } else {
		  myAccess = a.cloneWithoutNavajos();
		  myAccess.setInDoc(a.getInDoc());
		  myAccess.setOutputDoc(a.getOutputDoc());
	  }
	  
	  synchronized ( semaphore ) {
		  try {
		  todo.put( myAccess.accessID, new TodoItem(myAccess, am) );
		  } catch (IOException ioe) {
			  //AuditLog.log("STATISTICS", "Could not wri)
			  System.err.println("Could not write todoitem..." + ioe.getMessage());
		  }
	  }
	  synchronized (this ) {
		  if ( todo.size() > 10 ) {
			  notifyAll();
		  }
	  }
  }
  
  @SuppressWarnings("unchecked")
  public final void addAuditLog(final AuditLogEvent ale) {
	  synchronized ( semaphore ) {
		  auditlogs.add( ale );
	  }
	  synchronized (this ) {
		  if ( auditlogs.size() > 1 ) {
			  notifyAll();
		  }
	  }
	 
  }
  
  /**
   * This method is used by JMX gauge to measure StatisticsRunner 'health'.
   * 
   */
  public int getTodoCount() {
	  if ( getInstance() != null ) {
		  return getInstance().todo.size();
	  } else {
		  return 0;
	  }
  }
  
  public String getStoreClass() {
	  if ( getInstance() != null ) {
		  return getInstance().storeClass;
	  } else {
		  return null;
	  }
  }
  
  public String getVERSION() {
	  return VERSION;
  }
  
  /**
   * Returns the minimum audit level that we want to log.
   * Default is WARNING.
   * 
   * @return
   */
  public int getAuditLevel() {
	  return minAuditLevel;
  }
  
  public void terminate() {
	  todo.clear();
	  instance.enabled = false;
	  instance = null;
	  try {
		  JMXHelper.deregisterMXBean(JMXHelper.NAVAJO_DOMAIN, id);
	  } catch (Throwable e) {
		  // TODO Auto-generated catch block
		  e.printStackTrace();
	  }
	  AuditLog.log(AuditLog.AUDIT_MESSAGE_STAT_RUNNER, "Killed");
  }

  /**
   * 
   */
  public void setEnabled(boolean b) {
	  boolean previousValue = enabled;
	  enabled = b;
	  
	  ChangeNotificationEvent cne = 
		  new ChangeNotificationEvent(AuditLog.AUDIT_MESSAGE_STAT_RUNNER,
				  (b ? "Statistics runner enabled" : "Statistics runner disabled"),
			      "enabled", "boolean", Boolean.valueOf(previousValue), Boolean.valueOf(enabled));
	  
	  NavajoEventRegistry.getInstance().publishEvent(cne);
	 
  }
  
  public boolean isEnabled() {
	  return enabled;
  }

  /**
   * Handle JMX notifications.
   * 
   * If todo size becomes too high, switch off statistics.
   */
  public void handleNotification(Notification notification, Object handback) {

	  if (notification instanceof MonitorNotification) { 

		  MonitorNotification mn = (MonitorNotification) notification;

		  if ( mn.getObservedObject().equals( JMXHelper.getObjectName(JMXHelper.NAVAJO_DOMAIN, id))) {
			  if ( mn.getType().equals("jmx.monitor.gauge.low") ) {
				  AuditLog.log(AuditLog.AUDIT_MESSAGE_STAT_RUNNER, "Switching ON Statistics Runner, todo size low again...");
				  StatisticsRunner.getInstance().setEnabled(true);
			  }
			  if ( mn.getType().equals("jmx.monitor.gauge.high") ) {
				  AuditLog.log(AuditLog.AUDIT_MESSAGE_STAT_RUNNER, "Switching OFF Statistics Runner, todo size too high");
				  StatisticsRunner.getInstance().setEnabled(false);
			  }
		  }
	  }

  }

  /**
   * Handle Navajo Events: request, response and auditlog events.
   */
  public void onNavajoEvent(NavajoEvent ne) {

	  if ( ne instanceof NavajoResponseEvent ) {
		  NavajoResponseEvent nre = (NavajoResponseEvent) ne;
		  if (  isEnabled() && !Dispatcher.isSpecialwebservice( nre.getAccess().getRpcName() )) {
			  addAccess(nre.getAccess(), null);
		  }
	  } else if ( ne instanceof AuditLogEvent ) {
		  if (  isEnabled() && ((AuditLogEvent) ne).getLevel().intValue() >= getAuditLevel() ) {
			  addAuditLog( (AuditLogEvent) ne);
		  }
	  } else if ( ne instanceof NavajoRequestEvent ) {
		  NavajoRequestEvent nre = (NavajoRequestEvent) ne;
	  }
  }

  public void setAuditLevel(int l) {
	  minAuditLevel = l;
  }
  
}