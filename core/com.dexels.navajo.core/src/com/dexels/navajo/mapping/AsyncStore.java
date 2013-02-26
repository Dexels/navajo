package com.dexels.navajo.mapping;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.GenericThread;
import com.dexels.navajo.server.jmx.JMXHelper;
import com.dexels.navajo.util.AuditLog;

/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version $Id$
 *
 * This (singleton) object is used to store instances of (active) asynchronous mappable objects.
 * An instance is de-activated if the store() or kill() methods is called or if a certain time-out is reached.
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

public final class AsyncStore extends GenericThread implements AsyncStoreMXBean {

  private static final String VERSION = "$Id$";
	
  private static volatile AsyncStore instance = null;
  
  public final Map<String,AsyncMappable> objectStore = Collections.synchronizedMap(new HashMap<String,AsyncMappable>());
  public final Map<String,Access> accessStore = Collections.synchronizedMap(new HashMap<String,Access>());
  private float timeout = 3600000; 
  private final static int threadWait = 2000;
  private final static String id = "Navajo AsyncStore";
  
  private final static Logger logger = LoggerFactory.getLogger(AsyncStore.class);

  private static Object semaphore = new Object();
  
  /**
   * Get the singleton AsyncStore object instance.
   *
   * @return
   */
  public final static AsyncStore getInstance() {
    return instance;
  }

  public void activate() {
	  JMXHelper.registerMXBean(this, JMXHelper.NAVAJO_DOMAIN, id);
	  instance = this;
	  this.setSleepTime(threadWait);
	  this.startThread(this);
  }
  
  /**
   * Get the singleton AsyncStore object instance given an async inactive timeout.
   *
   * @param timeout
   * @return
   */
  public final static AsyncStore getInstance(float timeout) {
	
	  /** Make sure new instance determination is thread safe */
	  synchronized ( semaphore ) {
		  if (instance == null) {
			  instance = new AsyncStore();
			  instance.timeout = timeout;
			  instance.activate();
		  }
	  }
    return instance;
  }

  /**
   * Start the main AsyncStore loop.
   */
  public final void worker() {
	  
	  logger.info("In AsyncStore worker");
	  synchronized (instance) {
		  Set<String> s = new HashSet<String>(objectStore.keySet());
		  Iterator<String> iter = s.iterator();
		  while (iter.hasNext()) {
			  String ref = iter.next();
			  AsyncMappable a = objectStore.get(ref);
			  long now = System.currentTimeMillis();
			  logger.info("a: " + a + ", lastaccess: " + a.getLastAccess());
			  if ( (now - a.getLastAccess()) > timeout ) {
				 logger.info("About to kill: " + a);
				  a.kill();
				  objectStore.remove(ref);
				  accessStore.remove(ref);
				  a = null;
			  }
		  }
	  }
  }

  /**
   * Add an asynchronous mappable object and it's service access object.
   *
   * @param o
   * @param a
   * @return the unique reference to the async map
   */
  public final String addInstance(AsyncMappable o, Access a) {
    String ref = o.hashCode() + "";
    objectStore.put(ref + "", o);
    accessStore.put(ref + "", a);
    return ref;
  }

  /**
       * Get the access object of a async map's service given it's unique reference.
   *
   * @param ref
   * @return
   */
  public final Access getAccessObject(String ref) {
    Access o = accessStore.get(ref);
    if (o == null) {
      return null;
    }
    else {
      return o;
    }
  }

  /**
   * Get the async mappable object given it's unique reference.
   *
   * @param ref
   * @return
   */
  public final AsyncMappable getInstance(String ref) {
	AsyncMappable o = objectStore.get(ref);
    if (o == null) {
      return null;
    }
    else {
      // Always  set lastaccess timestamp when retrieving asyncmappable object.
      o.setLastAccess();
      return o;
    }
  }

  /**
   * Remove as async map ansd it's service access object given it's unique reference.
   *
   * @param ref
   */
  public final synchronized void removeInstance(String ref) {
	  AsyncMappable o = objectStore.get(ref);

	  if (o == null) {
		  return;
	  }
	  else {
		  objectStore.remove(ref);
		  if (accessStore.containsKey(ref)) {
			  accessStore.remove(ref);
		  }
		  o = null;
	  }
  }
  
  public String getVERSION() {
	  return VERSION;
  }
  
  public int getStoreSize() {
	  return objectStore.size();
  }
  
  private static final synchronized void resetInstance() {
	  instance = null;
  }
  
  public void deactivate() {
	  // Stop thread.
	  this.kill();
	  // Removed all async mappable instances.
	  for (Iterator<AsyncMappable> iter = objectStore.values().iterator(); iter.hasNext();) {
		  AsyncMappable element = iter.next();
		  try {
			  element.setKill(true);
		  } catch (Throwable e) {
			  logger.error("Error: ", e);
		  }
	  }
	  objectStore.clear();
	  accessStore.clear();
	  try {
		  JMXHelper.deregisterMXBean(JMXHelper.NAVAJO_DOMAIN, id);
	  } catch (Throwable e) {
		  logger.error("Error: ", e);
	  }
	  resetInstance();
	  AuditLog.log(AuditLog.AUDIT_MESSAGE_ASYNC_RUNNER, "Killed");
  }

}