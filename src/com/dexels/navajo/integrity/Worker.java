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
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.FileNameMap;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.Dispatcher;
import com.dexels.navajo.server.GenericThread;
import com.dexels.navajo.server.NavajoConfig;
import com.dexels.navajo.server.Parameters;
import com.dexels.navajo.server.UserException;
import com.dexels.navajo.util.AuditLog;

public class Worker extends GenericThread {

	/**
	 * Public fields (used as getters for mappable).
	 */
	public int cacheSize;
	public int workSize;
	public int notWrittenSize;
	private int fileCount = 0;
	public int violationCount = 0;
	
	public static final String VERSION = "$Id$";
	
	private static Worker instance = null;
	private static String responseDir = null;
	
	
	
	private static Object semaphore = new Object();
	
	// Worklist containst responses that still need to be written to a file.
	private Map workList = new HashMap();
	// Integrity cache contains mapping between unique request id and response file.
	private Map integrityCache = new HashMap();
	// Contains all unique request ids that still need to be handled by the worker thread.
	private Set notWrittenReponses = Collections.synchronizedSet(new HashSet());
	// Contains all currently running request ids.
	private Map runningRequestIds = Collections.synchronizedMap(new HashMap());
	
	private final static String RESPONSE_PREFIX = "navajoresponse_";
	
	public Worker() {
		super("Navajo Integrity Worker");
	}
	
	/**
	 * Beware, this functions should only be called from the authorized class that can enable this thread(!).
	 * 
	 * @return
	 */
	public static Worker getInstance() {
		
		if ( instance != null ) {
			return instance;
		}
		
		synchronized ( semaphore ) {
			
			if ( instance != null ) {
				return instance;
			}
			
			instance = new Worker();
			instance.setSleepTime(50);
			instance.startThread(instance);
			// remove all previously stored response files.
			File f = null;
			try {
				f = Dispatcher.getInstance().createTempFile(RESPONSE_PREFIX, ".xml");
				File dir = f.getParentFile();
				File [] allResponses = dir.listFiles();
				for (int i = 0; i < allResponses.length; i++) {
					File pr = allResponses[i];
					if ( pr.getName().startsWith(RESPONSE_PREFIX) ) {
						if ( !pr.delete() ) {
							AuditLog.log(AuditLog.AUDIT_MESSAGE_INTEGRITY_WORKER, "getInstance(), Could not delete response file: " + pr.getName());
						}
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block	
				e.printStackTrace();
			} finally {
				if ( f != null ) {
					if ( f.delete() ) {
						// 
					}
				}
			}
			
			AuditLog.log(AuditLog.AUDIT_MESSAGE_INTEGRITY_WORKER, "Started integrity worker $Id$");
		}
		return instance;
	}

	/**
	 * Writes a file with a response to a unique file and couple it to it's unique id
	 * in the integrityCache. The unique id is removed from the notWrittenReponses set to
	 * indicate that it's ready to be read.
	 * 
	 * @param id
	 * @param response
	 */
	private void writeFile(String id, Navajo response) {
		File f = null;
		FileWriter fw = null;
		
		try {
			
			if ( integrityCache.containsKey(id) ) {
				AuditLog.log(AuditLog.AUDIT_MESSAGE_INTEGRITY_WORKER, "Response id " + id + " already cached!");
				return;
			}
			
			fileCount++;
		
			
			f = Dispatcher.getInstance().createTempFile(RESPONSE_PREFIX + id, ".xml");
			
			if ( f == null ) {
				AuditLog.log(AuditLog.AUDIT_MESSAGE_INTEGRITY_WORKER, "Could not create temp file");
				return;
			}
			
			fw = new FileWriter(f);
			response.write(fw);
			
			integrityCache.put(id, f );
			
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			if ( f != null ) {
				f.delete();
			}
			fileCount--;
			integrityCache.remove(id);
			e.printStackTrace();
		} finally {
            // Remove unique request id from notWrittenResponse set to indicate that it's ready to be read.
			try {
				if ( fw != null ) {
					fw.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			notWrittenReponses.remove( id );
		}
	}
	
	public final void worker() {
		
		// Check for new access objects in workList.
		HashMap copyOfWorkList = null;
		Set s = null;
		synchronized (workList) {
			copyOfWorkList = new HashMap(workList);
			workList.clear();
		}
		s = new HashSet(copyOfWorkList.keySet());
		Iterator iter = s.iterator();
		while (iter.hasNext()) {
			String id = (String) iter.next();
			if ( id != null && !id.trim().equals("") ) {
				writeFile( id, (Navajo) copyOfWorkList.get(id) );
			}
			if ( id != null ) {
				notWrittenReponses.remove( id );
			}
			//workList.remove(id);
//			if (workList.size() > 50) {
//				AuditLog.log(AuditLog.AUDIT_MESSAGE_INTEGRITY_WORKER, "WARNING: Integrity Worker TODO list size:  " + workList.size());
//			}
			
		}
		
		// Remove 'old' responses.
		Set s2 = new HashSet(integrityCache.keySet());
		Iterator i = s2.iterator();
		long now = System.currentTimeMillis();
		while ( i.hasNext() ) {
			String id = (String) i.next();
			File f = (File) integrityCache.get(id);
			if ( f != null ) {
				long birth = f.lastModified();
				if ( now - birth > 60000 ) {
					// remove file reference from integrity cache.
					if ( !f.delete() ) {
						AuditLog.log(AuditLog.AUDIT_MESSAGE_INTEGRITY_WORKER, "worker(): Could not delete response file: " + f.getName());
					} else {
						f = null;
						fileCount--;
						integrityCache.remove(id);
					}
				}
			} else {
				fileCount--;
				integrityCache.remove(id);
			}
		}
		
	}
	
	/**
	 * Clears the entire integrity cache.
	 */
	private void clearCache() {
		Set s = new HashSet(integrityCache.keySet());
		Iterator i = s.iterator();
		while ( i.hasNext() ) {
			String id = (String) i.next();
			File f = (File) integrityCache.get(id);
			// remove file reference from integrity cache.
			if ( !f.delete() ) {
				AuditLog.log(AuditLog.AUDIT_MESSAGE_INTEGRITY_WORKER, "clearCache(): Could not delete response file: " + f.getName());
			} else {
				fileCount--;
				integrityCache.remove(id);
			}
			// remove file itself.
		}
	}
	
	/**
	 * Read a file fileName containing a response XML and returns the corresponding Navajo object.
	 * 
	 * @param fileName
	 * @return
	 */
	private Navajo readFile(File f) {
		FileInputStream fs = null;
		try {
			AuditLog.log(AuditLog.AUDIT_MESSAGE_INTEGRITY_WORKER, "Integrity violation detected, returning previous response from: " + f.getName());
			violationCount++;
			// Set modification date to time of last read.
			f.setLastModified( System.currentTimeMillis() );
			fs = new FileInputStream( f );
			Navajo n = NavajoFactory.getInstance().createNavajo(fs);
			return n;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} finally {
			if ( fs != null ) {
				try {
					fs.close();
				} catch (IOException e) {
					// NOT INTERESTED.
				}
			}
		}
	}

	/**
	 * Check if there is a running request id.
	 * If so, return Access object of already running request.
	 * 
	 * @param a
	 * @param request
	 * @return
	 */
	private final boolean waitedForRunningRequest(Access a, Navajo request) {
		
		String id  = request.getHeader().getRequestId();
		if ( id == null || id.trim().equals("") ) {
			return false;
		}
		
		Access r = (Access) runningRequestIds.get(id);
		
		if ( r == null ) {
			System.err.println("Did not find request id " + id + " in running request list");
			runningRequestIds.put(id, a);
			return false;
		} else {
			
			try {
				// Wait until running request is ready.
				System.err.println("DID find request id " + id + " in running request list, wait until ready...");
				while ( !r.isFinished() ) {
					try {
						Thread.sleep(2000);
						System.err.println(a.accessID + ": Waiting for currently running access set to become ready.");
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} finally {
				runningRequestIds.remove(id);
			}
			
			a.setOutputDoc(r.getOutputDoc());
		}
		
		return true;
		
	}
	
	/**
	 * Get a possibly stored response from a previously received request.
	 * 
	 * @param request
	 * @return
	 */
	public Navajo getResponse(Access a, Navajo request) {

		String id  = request.getHeader().getRequestId();
		if ( id == null || id.trim().equals("") ) {
			return null;
		}

		// Check for running request first.
		if ( !waitedForRunningRequest(a, request) ) {
			// Check if request id is in worklist (still) or integreatyCache (already).
			if ( notWrittenReponses.contains( id ) || workList.containsKey( id ) || integrityCache.containsKey( id ) ) {
				// Response file write could be still pending, due to a large workList size and/or large responses.
				while ( notWrittenReponses.contains( id ) ) {
					//AuditLog.log(AuditLog.AUDIT_MESSAGE_INTEGRITY_WORKER,"Integrity violation detected: waiting for response file " + fileName + " to become written...");
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				return readFile( (File) integrityCache.get( id ) );
			} else {
				return null;
			}
		} else {
			// Return response from previously running request.
			return a.getOutputDoc();
		}
	}
	
	/**
	 * Adds a task to the workList. The response file is written in the worker thread.
	 * The request is immediately recorded as being not written.
	 * 
	 * @param request
	 * @param response
	 */
	public void setResponse(Navajo request, Navajo response) {
		
		String id  = request.getHeader().getRequestId();

		try {

			if ( id != null && !id.trim().equals("") ) {
				// Immediately add request id to notWrittenResponses.
				notWrittenReponses.add( id );
				//  Add response to workList.
				try {
					//ystem.err.println("Before synchronized in Worker.setResponse(): " + id );
					synchronized ( workList ) {
						workList.put( id, response );
					}
					//System.err.println("After synchronized in Worker.setResponse(): " + id);
				} catch (Throwable t) {
					notWrittenReponses.remove( id );
					System.err.println("COULD NOT ADD TO WORKLIST");
					t.printStackTrace(System.err);	
				}
			}
		} finally {
			// Return from running request id set.
			runningRequestIds.remove(id);
		}
	}
	
	public int getViolationCount() {
		if ( instance == null ) {
			return 0;
		}
		return instance.violationCount;
	}
	
	public int getCacheSize() {
		if ( instance == null ) {
			return 0;
		}
		return instance.integrityCache.size();
	}
	
	public int getWorkSize() {
		if ( instance == null ) {
			return 0;
		}
		return instance.workList.size();
	}
	
	public int getNotWrittenSize() {
		if ( instance == null ) {
			return 0;
		}
		return instance.notWrittenReponses.size();
	}
	
	public String getVERSION() {
		return this.VERSION;
	}
	
	public int getFileCount() {
		if ( instance == null ) {
			return 0;
		}
		return instance.fileCount;
	}
	
	public void terminate() {
		if ( instance != null ) {
			instance.workList.clear();
			instance.clearCache();
			instance.notWrittenReponses.clear();
			instance = null;
			AuditLog.log(AuditLog.AUDIT_MESSAGE_INTEGRITY_WORKER, "Killed");
		}
	}

	public static void main(String [] args) {
		String aap = "slwebsvr4.sportlink.enovation.net/sportlink/knvb/servlet/Postman";
		aap = aap.substring(aap.indexOf("/")+1, aap.length());
		aap = aap.replace('/', '_');
		try {
			File tempDir = new File(System.getProperty("java.io.tmpdir") + "/" + aap);
			tempDir.mkdirs();
			File f = Dispatcher.getInstance().createTempFile("tijdelijk", ".xml");
			FileWriter fw = new FileWriter(f);
			fw.write("apenoot");
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.err.println(aap);
		System.getProperties().list(System.err);
	}
}
