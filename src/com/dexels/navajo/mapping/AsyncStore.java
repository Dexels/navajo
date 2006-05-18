package com.dexels.navajo.mapping;

import java.util.*;
import com.dexels.navajo.server.Access;

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

public final class AsyncStore
    implements Runnable {

  private static final String VERSION = "$Id$";
	
  private static AsyncStore instance = null;
  public Map objectStore = null;
  public Map accessStore = null;
  private float timeout;
  private long threadWait = 20000;

  /**
   * Get the singleton AsyncStore object instance.
   *
   * @return
   */
  public final static AsyncStore getInstance() {
    return instance;
  }

  /**
       * Get the singleton AsyncStore object instance given an async inactive timeout.
   *
   * @param timeout
   * @return
   */
  public final static AsyncStore getInstance(float timeout) {
    if (instance == null) {
      instance = new AsyncStore();
      instance.timeout = timeout;
      if (instance.timeout < instance.threadWait) {
        instance.threadWait = (long) (instance.timeout / 2);
      }
      instance.objectStore = Collections.synchronizedMap(new HashMap());
      instance.accessStore = Collections.synchronizedMap(new HashMap());
      Thread thread = new Thread(instance);
      thread.setDaemon(true);
      thread.start();
    }
    return instance;
  }

  /**
   * Start the main AsyncStore loop.
   */
  public final void run() {
    System.err.println("Started garbage collect thread for async store version $Id$, timeout = " +
                       instance.timeout + ", thread wait = " +
                       instance.threadWait);
    long maxAge;
    while (true) {
      try {
        Thread.sleep(threadWait);
        synchronized (instance) {
          Set s = new HashSet(objectStore.keySet());
          Iterator iter = s.iterator();
          while (iter.hasNext()) {
            String ref = (String) iter.next();
            AsyncMappable a = (AsyncMappable) objectStore.get(ref);
            long now = System.currentTimeMillis();
            if ( (now - a.getLastAccess()) > timeout || a.isKilled()) {
              if (!a.isKilled()) {
                System.err.println("REMOVED " + ref +
                                   " FROM OBJECT STORE DUE TO TIME-OUT, now = " +
                                   now + ", lastAccess() = " + a.getLastAccess() +
                                   ", timeout = " + timeout);
              }
              else {
                System.err.println("REMOVED " + ref +
                                   " FROM OBJECT STORE DUE TO KILLONFINNISH");
              }
              a.kill();
              objectStore.remove(ref);
              accessStore.remove(ref);
              a = null;
            }
          }
        }
      }
      catch (InterruptedException e) {
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
    Object o = accessStore.get(ref);
    if (o == null) {
      return null;
    }
    else {
      return (Access) o;
    }
  }

  /**
   * Get the async mappable object given it's unique reference.
   *
   * @param ref
   * @return
   */
  public final AsyncMappable getInstance(String ref) {
    Object o = objectStore.get(ref);
    if (o == null) {
      return null;
    }
    else {
      return (AsyncMappable) o;
    }
  }

  /**
   * Remove as async map ansd it's service access object given it's unique reference.
   *
   * @param ref
   */
  public final synchronized void removeInstance(String ref) {
    Object o = objectStore.get(ref);
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

}