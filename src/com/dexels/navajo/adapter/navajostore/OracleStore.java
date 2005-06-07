/*
 * Created on Jun 7, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dexels.navajo.adapter.navajostore;

import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.Dispatcher;
import java.sql.*;
import java.io.StringWriter;
import java.io.PrintWriter;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.server.statistics.StoreInterface;
import com.dexels.navajo.adapter.SQLMap;
import java.util.Map;


/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
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

public final class OracleStore implements StoreInterface {

	private static boolean ready = false;
	private static boolean restartInProgress = false;
	private SQLMap sqlMap = null;
	private static String version = "$Id$";
	  
	/**
	 * Navajo store SQL queries.
	 */
	private static String insertAccessSQL = "insert into navajoaccess " +
	"(access_id, webservice, username, threadcount, totaltime, parsetime, authorisationtime, requestsize, requestencoding, compressedrecv, compressedsnd, ip_address, hostname, created) " +
	"values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
	private static String insertLog =
		"insert into navajolog (access_id, exception, navajoin, navajoout) values (?, ?, ?, ?)";

	public void setDatabaseUrl(String path) {
	    sqlMap = new SQLMap();
	  }

	 public void setDatabaseParameters(Map p) {
	  }
	 
	/**
	 * Create a connection to the Oracle store.
	 *
	 * @param nowait set if waiting for connection is not allowed (when initializing!)
	 * @param norestart set if restart is not allowed (when initializing!)
	 * @return
	 */
	private final Connection createConnection(boolean nowait, boolean norestart, boolean init) {
	
		Connection myConnection = null;
		
		try {
			sqlMap.load(null, null, null, Dispatcher.getNavajoConfig());
			sqlMap.setDatasource("navajostore");
			myConnection = sqlMap.getConnection();
			ready = true;
		}
		catch (Exception ex) {
			ex.printStackTrace(System.err);
			ready = false;
			return null;
		}
		
		return myConnection;
	}
	
	/**
	 * Add a new access object to the persistent Navajo store.
	 *
	 * @param a
	 */
	private final void addAccess(final Access a) {
		if (Dispatcher.getNavajoConfig().dbPath != null) {
			Connection con = createConnection(false, false, false);
			if (con != null) {
				try {
					PreparedStatement ps = con.prepareStatement(insertAccessSQL);
					ps.setString(1, a.accessID);
					ps.setString(2, a.rpcName);
					ps.setString(3, a.rpcUser);
					ps.setInt(4, a.getThreadCount());
					ps.setInt(5, a.getTotaltime());
					ps.setInt(6, a.parseTime);
					ps.setInt(7, a.authorisationTime);
					ps.setInt(8, a.contentLength);
					ps.setString(9, a.requestEncoding);
					ps.setBoolean(10, a.compressedReceive);
					ps.setBoolean(11, a.compressedSend);
					ps.setString(12, a.ipAddress);
					ps.setString(13, a.hostName);
					ps.setTimestamp(14, new java.sql.Timestamp(a.created.getTime()));
					ps.executeUpdate();
					ps.close();
					// Only log details if exception occured or if full accesslog monitoring is enabled.
					if (a.getException() != null || Dispatcher.getNavajoConfig().needsFullAccessLog(a) ) {
						addLog(con, a);
					}
				}
				catch (SQLException ex) {
					ex.printStackTrace(System.err);
				} finally {
					if (con != null) {
						try {
							sqlMap.store();
						}
						catch (Exception ex1) {
							ex1.printStackTrace(System.err);
						}
					}
				}
			}
		}
	  }

	  /**
	   * Add access log detail: exception, navajo request, navajo response.
	   *
	   * @param a
	   */
	  private final void addLog(Connection con, Access a) {
	    try {
	      PreparedStatement ps = con.prepareStatement(insertLog);
	      ps.setString(1, a.accessID);
	      StringWriter w = new StringWriter();
	      if (a.getException() != null) {
	        PrintWriter pw = new PrintWriter(w);
	        a.getException().printStackTrace(pw);
	      }
	      ps.setString(2, (w != null && w.toString().length() > 1 ? w.toString() : "No Exception"));
	      java.io.ByteArrayOutputStream bosIn = new java.io.ByteArrayOutputStream();
	      java.io.ByteArrayOutputStream bosOut = new java.io.ByteArrayOutputStream();
	      Navajo inDoc = (a.getInDoc() != null ? a.getInDoc() : null);
	      Navajo outDoc = a.getOutputDoc();
	      if (inDoc != null) {
	        inDoc.write(bosIn);
	        bosIn.close();
	      }
	      if (outDoc != null) {
	        outDoc.write(bosOut);
	        bosOut.close();
	      }
	      ps.setBytes(3, (bosIn != null ? bosIn.toByteArray() : null));
	      ps.setBytes(4, (bosOut != null ? bosOut.toByteArray() : null));
	      ps.executeUpdate();
	      ps.close();
	    }
	    catch (Exception e) {
	      e.printStackTrace(System.err);
	    }
	  }

	  /**
	   * Interface method to persist an Access object.
	   *
	   * @param a
	   */
	  public final synchronized void storeAccess(Access a) {
	    addAccess(a);
	  }
}
