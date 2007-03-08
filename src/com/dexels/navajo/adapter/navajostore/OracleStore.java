package com.dexels.navajo.adapter.navajostore;

import com.dexels.navajo.mapping.AsyncMappable;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.Dispatcher;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.*;
import java.io.StringWriter;
import java.io.PrintWriter;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.server.statistics.MapStatistics;
import com.dexels.navajo.server.statistics.StoreInterface;
import com.dexels.navajo.server.statistics.TodoItem;
import com.dexels.navajo.adapter.SQLMap;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2005</p>
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
	
	private static String version = "$Id$";
	
	/**
	 * Navajo store SQL queries.
	 */
	private static String existsAccessSQL = "select count(*) AS cnt from navajoaccess where access_id = ?";
	
	private static String insertAccessSQL = "insert into navajoaccess " +
	"(access_id, webservice, username, threadcount, totaltime, parsetime, authorisationtime, clienttime, requestsize, requestencoding, compressedrecv, compressedsnd, ip_address, hostname, created, clientid) " +
	"values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
	private static String updateEmbryoAccessSQL = "update navajoaccess " +
	"set webservice = ?, username = ?, threadcount = ?, totaltime = ?, parsetime = ?, " + 
	"authorisationtime = ?, requestsize = ?, requestencoding = ?, " + 
	"compressedrecv = ?, compressedsnd = ?, ip_address = ?, hostname = ?, " + 
	"created = ?, clientid = ? where access_id = ?";
	
	private static String updateAccessSQL = "update navajoaccess set clienttime = ? where access_id = ?";
	
	private static String insertEmbryoAccessSQL = "insert into navajoaccess " + "(access_id, clienttime, webservice, created) values (?, ?, ?, ?)";
	
	private static String insertLog =
		"insert into navajolog (access_id, exception, navajoin, navajoout) values (?, ?, ?, ?)";
	
	private static String insertAsyncLog =
		"insert into navajoasync ( access_id, ref_id, asyncmap, totaltime, exception, created ) values (?, ?, ?, ?, ?, ?)";
	
	private static String insertMapLog = 
		"insert into navajomap ( access_id, sequence_id, level_id, mapname, array, instancecount, totaltime, created) values " +
		"( ?, ?, ?, ?, ?, ?, ?, ?)";
		
	private String hostName = null;
	
	
	/**
	 * Set the database url (only for databases which are started by Navajo, e.g. HSQL).
	 * Required by StoreInterface
	 */
	public void setDatabaseUrl(String path) {
		//sqlMap = new SQLMap();
	}
	
	/**
	 * Required by StoreInterface.
	 */
	public void setDatabaseParameters(Map p) {
	}
	
	/**
	 * Create a connection to the Oracle store.
	 *
	 * @param nowait set if waiting for connection is not allowed (when initializing!)
	 * @param norestart set if restart is not allowed (when initializing!)
	 * @return
	 */
	private final SQLMap createConnection(boolean nowait, boolean norestart, boolean init) {
		
		SQLMap sqlMap = new SQLMap();
		//sqlMap.debug = true;
		try {
			sqlMap.load(null, null, null, Dispatcher.getInstance().getNavajoConfig());
			sqlMap.setDatasource("navajostore");
		}
		catch (Exception ex) {
			ex.printStackTrace(System.err);
			return null;
		}
		
		return sqlMap;
	}
	
	
	/**
	 * access_id, sequence_id, level_id, mapname, array, instancecount, totaltime, created
	 * @param a
	 */
//	private final void addMapLog(final Connection con, final Access a) {
//		if (Dispatcher.getInstance().getNavajoConfig().dbPath != null) {
//			if (con != null) {
//				PreparedStatement ps = null;
//				try {
//					ps = con.prepareStatement(insertMapLog);
//					HashMap mapLogs = a.getMapStatistics();
//					if ( mapLogs != null ) {
//						// Loop over all map log.
//						Iterator iter = mapLogs.keySet().iterator();
//						while ( iter.hasNext() ) {
//							Integer id = (Integer) iter.next();
//							MapStatistics ms = (MapStatistics) mapLogs.get(id);
//							ps.setString(1, a.accessID);
//							ps.setInt(2, id.intValue());
//							ps.setInt(3, ms.levelId);
//							ps.setString(4, ( ms.mapName != null ? ms.mapName : "empty" ) );
//							ps.setString(5, ( ms.isArrayElement ? "1" : "0" ) );
//							ps.setInt(6, ms.elementCount);
//							ps.setString(7, ms.totalTime+"");
//							ps.setTimestamp(8, new java.sql.Timestamp(a.created.getTime()));
//							ps.executeUpdate();
//						}
//					}
//					ps.close();
//					ps = null;
//				} catch (SQLException ex) {
//					ex.printStackTrace(System.err);
//				} finally {
//					if (ps != null) {
//						try {
//							ps.close();
//						} catch (SQLException e) {
//						}
//					}
//				}
//			}
//		}
//	}
	
	/**
	 * Add a new access object to the persistent Navajo store.
	 *
	 * @param a
	 */
	private final void addAccess(final Access a) {
		 throw new UnsupportedOperationException();
	}
	
	/**
	 * Add access log detail: exception, navajo request, navajo response.
	 *
	 * @param a
	 */
	private final void addLog(Connection con, Access a) {
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement(insertLog);
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
			ps = null;
		}
		catch (Exception e) {
			e.printStackTrace(System.err);
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
				}
			}
		}
	}
	
	/**
	 * Interface method to persist an Access object.
	 *
	 * @param a
	 */
	public final synchronized void storeAccess(Access a, AsyncMappable am) {
		throw new UnsupportedOperationException();
	}

	private void updatePiggybackData(Set piggybackData, Map accessMap, Connection con) throws SQLException {
		for (Iterator iter = piggybackData.iterator(); iter.hasNext();) {
			Map element = (Map) iter.next();
			String type = (String)element.get("type");
			if ("performanceStats".equals(type)) {
				addPerformanceStats(element, accessMap, con);
			}
		}
	}

	private final void addPerformanceStats(Map element, Map accessMap, Connection con) throws SQLException {
		
		String accessId = (String)element.get("accessId");
		String clnt = (String)element.get("clientTime");
		if (accessId==null || clnt==null) {
			return;
		}
		int clientTime = Integer.parseInt(clnt);
		
		TodoItem ti = (TodoItem) accessMap.get(accessId);
		if ( ti != null ) {
			
			if ( ti.access != null ) {
				ti.access.clientTime = clientTime;
			} else {
				System.err.println("EMPTY ACCESS FOUND IN TODO!");
			}
		} else {
			// Did not find in accessMap, creating embryo access.
			if (con != null) {
				
				PreparedStatement exists = null;
				PreparedStatement ps = null;
				PreparedStatement update = null;
				ResultSet rs = null;
				try {
					// Check if access already exists.
					exists = con.prepareStatement(existsAccessSQL);
					exists.setString(1, accessId);
					rs = exists.executeQuery();
					rs.next();
					int cnt = rs.getInt("cnt"); 
					if ( cnt == 0 ) {
						// Insert new embryo record.
						ps = con.prepareStatement(insertEmbryoAccessSQL);
						ps.setString(1, accessId);
						ps.setString(2, clnt);
						ps.setString(3, "embryo");
						ps.setTimestamp(4, new java.sql.Timestamp(new java.util.Date().getTime()));
						ps.executeUpdate();
					} else {
						// Update existing record.
						update = con.prepareStatement(updateAccessSQL);
						update.setString(1, clnt);
						update.setString(2, accessId);
						update.executeUpdate();
					}
				} catch (SQLException sqle) {
					// Could throw SQLException due to the fact that when trying to insert embryo original suddenly exists (due to insert by other app. server)
					// Silently catch this one.
				} finally {
					if ( rs != null ) {
						rs.close();
					}
					if ( ps != null ) {
						ps.close();
					}
					if ( update != null ) {
						update.close();
					}
					if ( exists != null ) {
						exists.close();
					}
				}
			}
		}
		
	}

	public void storeAccess(Map accessMap) {
	
		
		if ( accessMap == null || accessMap.isEmpty() ) {
			return;
		}
		
		if ( hostName == null ) {
			try {
				hostName = InetAddress.getLocalHost().getHostName()+" - "+Dispatcher.getInstance().getNavajoConfig().getInstanceName();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		
		if (Dispatcher.getInstance().getNavajoConfig().dbPath != null) {
			
			// Check for piggy back data.
			Iterator iter = accessMap.values().iterator();
			
			SQLMap sqlMap = createConnection(false, false, false);
			//sqlMap.debug = true;
			Connection con = null;
			try {
				con = sqlMap.getConnection();
				System.err.println("In OracleStore, connection is " + con.hashCode() );
			} catch (SQLException e) {
				e.printStackTrace(System.err);
			}
			
			while ( iter.hasNext() ) { 
				TodoItem ti = (TodoItem) iter.next();
				Access a = ti.access;
				  //System.err.println("Checking piggyback: "+a.accessID);

				if ( a.getPiggybackData() != null ) {
					try {
						updatePiggybackData(a.getPiggybackData(), accessMap, con);
					} catch (Exception e) {
						e.printStackTrace(System.err);
					}
				}
			}
			
		
			//System.err.println("I got a connection: "+con);
			if (con != null) {
				PreparedStatement ps = null;
				PreparedStatement psUpdate = null;
				PreparedStatement asyncps = null;
				PreparedStatement exists = null;
				try {
					
					ps = con.prepareStatement(insertAccessSQL);
					exists = con.prepareStatement(existsAccessSQL);
					psUpdate = con.prepareStatement(updateEmbryoAccessSQL);
					
					asyncps = null; 
					
					iter = accessMap.values().iterator();
					
					while ( iter.hasNext() ) { 
						TodoItem ti = (TodoItem) iter.next();
						Access a = ti.access;

						if ( ti.asyncobject == null ) {
							
							// Check if embryo exists.
							exists.setString(1, a.accessID);
							ResultSet rs = exists.executeQuery();
							rs.next();
							int cnt = rs.getInt("cnt"); 
							rs.close();
							if ( cnt == 0 ) {
								ps.setString(1, a.accessID);
								ps.setString(2, a.rpcName);
								ps.setString(3, a.rpcUser);
								ps.setInt(4,    a.getThreadCount());
								ps.setInt(5,    a.getTotaltime());
								ps.setInt(6,    a.parseTime);
								ps.setInt(7,    a.authorisationTime);
								ps.setInt(8,    a.clientTime);
								ps.setInt(9,    a.contentLength);
								ps.setString(10, a.requestEncoding);
								ps.setBoolean(11, a.compressedReceive);
								ps.setBoolean(12, a.compressedSend);
								ps.setString(13, a.ipAddress);
								ps.setString(14, hostName);
								ps.setTimestamp(15, new java.sql.Timestamp(a.created.getTime()));
								ps.setString(16, a.getClientToken());
								ps.executeUpdate();
							} else {
								// Update embryo.
								psUpdate.setString(1, a.rpcName);
								psUpdate.setString(2, a.rpcUser);
								psUpdate.setInt(3, a.getThreadCount());
								psUpdate.setInt(4, a.getTotaltime());
								psUpdate.setInt(5, a.parseTime);
								psUpdate.setInt(6, a.authorisationTime);
								psUpdate.setInt(7,    a.contentLength);							
								psUpdate.setString(8, a.requestEncoding);
								psUpdate.setBoolean(9, a.compressedReceive);
								psUpdate.setBoolean(10, a.compressedSend);
								psUpdate.setString(11, a.ipAddress);
								psUpdate.setString(12, hostName);
								psUpdate.setTimestamp(13, new java.sql.Timestamp(a.created.getTime()));
								psUpdate.setString(14, a.getClientToken());
								psUpdate.setString(15, a.accessID);
								int x = psUpdate.executeUpdate();
							}
							
//							 Only log details if exception occured or if full accesslog monitoring is enabled.
							if (a.getException() != null || Dispatcher.getInstance().getNavajoConfig().needsFullAccessLog(a) ) {
								addLog(con, a);
							}
							
						} else {
							
							if ( asyncps == null ) {
								asyncps = con.prepareStatement(insertAsyncLog);
							}
							
							AsyncMappable am = ti.asyncobject;
							
							asyncps.setString(1, a.accessID);
							asyncps.setString(2, am.pointer);
							asyncps.setString(3, am.getClass().getName());
							asyncps.setInt(4, a.getTotaltime());
							asyncps.setString(5, ( a.getException() != null ? a.getException().getMessage() : "" ) );
							asyncps.setTimestamp(6, new java.sql.Timestamp(a.created.getTime()));
							asyncps.executeUpdate();
							
						}
						
//						if (a.getMapStatistics() != null) {
//							addMapLog(con, a);
//						}
					}
					
				}
				catch (Exception ex) {
					ex.printStackTrace(System.err);
				} finally {
					if (ps != null) {
						try {
							ps.close();
						} catch (SQLException e) {
						}
					}
					if (psUpdate != null) {
						try {
							psUpdate.close();
						} catch (SQLException e) {
						}
					}
					if (exists != null) {
						try {
							exists.close();
						} catch (SQLException e) {
						}
					}
					if (asyncps != null) {
						try {
							asyncps.close();
						} catch (SQLException e) {
						}
					}
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
	


}