/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Dexels BV</p>
 * @author 
 * @version $Id$.
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
package com.dexels.navajo.integrity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.scheduler.TaskRunner;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.Dispatcher;
import com.dexels.navajo.server.NavajoConfig;
import com.dexels.navajo.util.AuditLog;

class Job {
	
	public String requestid;
	public Navajo response;
	
	public Job(String s, Navajo r) {
		requestid = s;
		response = r;
	}
}

public class Worker implements Runnable {

	private static Worker instance = null;
	
	private Dispatcher myDispatcher;
	
	private Set workList = Collections.synchronizedSet(new HashSet());
	private Map integrityCache = Collections.synchronizedMap(new HashMap());
	
	public static Worker getInstance(Dispatcher myDispatcher) {
		
		if ( instance != null ) {
			return instance;
		}
		
		instance = new Worker();
		instance.myDispatcher = myDispatcher;
		Thread thread = new Thread(instance);
		thread.setDaemon(true);
		thread.start();
		
		AuditLog.log(AuditLog.AUDIT_MESSAGE_INTEGRITY_WORKER, "Started task scheduler process $Id$");
		
		return instance;
	}

	private void writeFile(String id, Navajo response) {
		try {
			
			File f = File.createTempFile("navajoresponse_" + id, ".xml");
			f.deleteOnExit();
			integrityCache.put(id, f.getAbsolutePath());
			FileWriter fw = new FileWriter(f);
			response.write(fw);
			fw.close();
			System.err.println("putting in cache for id: " + id + " " + f.getAbsolutePath());
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void run() {
		while (true) {
			try {
				Thread.sleep(50);
				// Check for new access objects.
				//System.err.println(">> StatisticsRunner TODO list size: " + todo.size());
				synchronized (workList) {
					Iterator iter = workList.iterator();
					while (iter.hasNext()) {
						Job j = (Job) iter.next();
						writeFile( j.requestid, j.response );
						iter.remove();
						if (workList.size() > 50) {
							System.err.println("WARNING: Integrity Worker TODO list size:  " + workList.size());
						}
						
					}
				}
				// Remove garbage.
				Iterator i = integrityCache.entrySet().iterator();
				long now = System.currentTimeMillis();
				while ( i.hasNext() ) {
					String fileName = (String) i.next();
					File f = new File( fileName );
					long birth = f.lastModified();
					if ( now - birth > 60000 ) {
						// remove file.
						f.delete();
						System.err.println("Removed file: " + f.getAbsolutePath());
					}
				}
			}
			catch (InterruptedException ex) {
			}
		}
	}
	
	private Navajo readFile(String fileName) {
		try {
			System.err.println("reading previous response: " + fileName);
			FileInputStream fs = new FileInputStream( fileName );
			Navajo n = NavajoFactory.getInstance().createNavajo(fs);
			return n;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public Navajo getResponse(Navajo request) {

		if ( request.getHeader().getRequestId() == null || 
			 request.getHeader().getRequestId().trim().equals("") ) {
			return null;
		}
		if ( integrityCache.containsKey( request.getHeader().getRequestId() ) ) {
			return readFile( (String) integrityCache.get( request.getHeader().getRequestId() ) );
		} else {
			return null;
		}
	}
	
	/**
	 * Stores a response coupled to a request.
	 * 
	 * @param request
	 * @param response
	 */
	public void setResponse(Navajo request, Navajo response) {
		workList.add( new Job( request.getHeader().getRequestId(), response) );
	}

}
