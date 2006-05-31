package com.dexels.navajo.server.statistics;

import java.util.HashSet;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.util.AuditLog;

import java.util.Set;
import java.util.Iterator;
import java.util.Map;
import java.util.Collections;

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

public final class StatisticsRunner implements Runnable {

  private static StatisticsRunner instance = null;
  private StoreInterface myStore = null;
  private Set todo = Collections.synchronizedSet(new HashSet());

  private boolean killed = false;
  private Thread thread;
  
  public final static StatisticsRunner getInstance(String storePath, Map parameters) {
  	return getInstance(storePath, parameters, "com.dexels.navajo.adapter.navajostore.HSQLStore");
  }
  
  /**
   * Get an instance of the StatisticsRunner (singleton).
   *
   * @param storePath the file (or something else, e.g. JDBC url, datasource name, ...) location of the Navajo store.
   *
   * @return
   */
  public final synchronized static StatisticsRunner getInstance(String storePath, Map parameters, String storeClass) {

    if (instance == null) {
     
        instance = new StatisticsRunner();
        Class si = null;
        try {
          si = Class.forName(storeClass);
          instance.myStore = (StoreInterface) si.newInstance();
          instance.myStore.setDatabaseParameters(parameters);
          instance.myStore.setDatabaseUrl(storePath);
        }
        catch (Exception ex) {
        }
        instance.thread = new Thread(instance);
        instance.thread.start();
        System.err.println("Started StatisticsRunner version $Id$ using store: " + instance.myStore.getClass().getName());
      }
    
    return instance;
  }

  /**
   * Main thread. Responsible for persisting queued access objects.
   *
   */
  public void run() {

    while (!killed) {
      try {
        Thread.sleep(2000);
        // Check for new access objects.
        //System.err.println(">> StatisticsRunner TODO list size: " + todo.size());
        synchronized (todo) {
          Iterator iter = todo.iterator();
          while (iter.hasNext()) {
            Access tb = (Access) iter.next();
            myStore.storeAccess(tb);
            iter.remove();
            tb = null;
            if (todo.size() > 50) {
              System.err.println("WARNING: StatisticsRunner TODO list size:  " + todo.size());
            }

          }
        }
      }
      catch (InterruptedException ex) {
      }
    }
  }

  /**
   * Add an access object to the to-be-persisted-queue.
   *
   * @param a
   */
  public final void addAccess(final Access a, final Exception e) {
    todo.add(a);
    // Add to webserviceaccesslistener.
    WebserviceAccessListener.getInstance().addAccess(a, e);
  }
  
  public void kill() {
	  
	  if ( thread != null ) {
		  killed = true;
		  thread.interrupt();
		  instance = null;
		  AuditLog.log(AuditLog.AUDIT_MESSAGE_STAT_RUNNER, "Killed");
	  }
  }

}