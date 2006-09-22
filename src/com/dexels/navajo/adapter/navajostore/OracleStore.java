package com.dexels.navajo.adapter.navajostore;

import com.dexels.navajo.mapping.AsyncMappable;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.Dispatcher;

import java.net.InetAddress;
import java.sql.*;
import java.io.StringWriter;
import java.io.PrintWriter;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.server.statistics.MapStatistics;
import com.dexels.navajo.server.statistics.StoreInterface;
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
	private static String existsAccessSQL = "select count(*) from navajoaccess where access_id = ?";
	
	private static String insertAccessSQL = "insert into navajoaccess " +
	"(access_id, webservice, username, threadcount, totaltime, parsetime, authorisationtime, requestsize, requestencoding, compressedrecv, compressedsnd, ip_address, hostname, created, clientid) " +
	"values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
	private static String updateEmbryoAccessSQL = "update navajoaccess " +
	"set webservice = ?, set username = ?, set threadcount = ?, set totaltime = ?,set parsetime = ?" + 
	",set authorisationtime = ?, set requestsize = ?, set requestencoding = ?, " + 
	" set compressedrecv = ?, set compressedsnd = ?, set ip_address = ?, set hostname = ?" + 
	",set created = ? where access_id = ? ";
	
	private static String insertEmbryoAccessSQL = "insert into navajoaccess " +
	"(access_id, clienttime) values (?, ?)";
	
	private static String insertLog =
		"insert into navajolog (access_id, exception, navajoin, navajoout) values (?, ?, ?, ?)";
	
	private static String insertAsyncLog =
		"insert into navajoasync ( access_id, ref_id, asyncmap, totaltime, exception, created ) values (?, ?, ?, ?, ?, ?)";
	
	private static String insertMapLog = 
		"insert into navajomap ( access_id, sequence_id, level_id, mapname, array, instancecount, totaltime, created) values " +
		"( ?, ?, ?, ?, ?, ?, ?, ?)";

	private static String updateAccessSQL = "update navajoaccess " +
	"set clienttime = ? where access_id = ?";
		
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
		try {
			sqlMap.load(null, null, null, Dispatcher.getInstance().getNavajoConfig());
			sqlMap.setDatasource("navajostore");
		}
		catch (Exception ex) {
			//ex.printStackTrace(System.err);
			return null;
		}
		
		return sqlMap;
	}
	
	
	/**
	 * access_id, sequence_id, level_id, mapname, array, instancecount, totaltime, created
	 * @param a
	 */
	private final void addMapLog(final Connection con, final Access a) {
		if (Dispatcher.getInstance().getNavajoConfig().dbPath != null) {
			if (con != null) {
				PreparedStatement ps = null;
				try {
					ps = con.prepareStatement(insertMapLog);
					HashMap mapLogs = a.getMapStatistics();
					if ( mapLogs != null ) {
						// Loop over all map log.
						Iterator iter = mapLogs.keySet().iterator();
						while ( iter.hasNext() ) {
							Integer id = (Integer) iter.next();
							MapStatistics ms = (MapStatistics) mapLogs.get(id);
							ps.setString(1, a.accessID);
							ps.setInt(2, id.intValue());
							ps.setInt(3, ms.levelId);
							ps.setString(4, ( ms.mapName != null ? ms.mapName : "empty" ) );
							ps.setString(5, ( ms.isArrayElement ? "1" : "0" ) );
							ps.setInt(6, ms.elementCount);
							ps.setString(7, ms.totalTime+"");
							ps.setTimestamp(8, new java.sql.Timestamp(a.created.getTime()));
							ps.executeUpdate();
						}
					}
					ps.close();
					ps = null;
				} catch (SQLException ex) {
					ex.printStackTrace(System.err);
				} finally {
					if (ps != null) {
						try {
							ps.close();
						} catch (SQLException e) {
						}
					}
				}
			}
		}
	}
	
	private final void addAsync(final Access a, final AsyncMappable am) {
		if (Dispatcher.getInstance().getNavajoConfig().dbPath != null) {
			SQLMap sqlMap = createConnection(false, false, false);
			Connection con = null;
			try {
				con = sqlMap.getConnection();
			} catch (SQLException e) {
				e.printStackTrace(System.err);
			}
			if (con != null) {
				PreparedStatement ps = null;
				try {
					ps = con.prepareStatement(insertAsyncLog);
					ps.setString(1, a.accessID);
					ps.setString(2, am.pointer);
					ps.setString(3, am.getClass().getName());
					ps.setInt(4, a.getTotaltime());
					ps.setString(5, ( a.getException() != null ? a.getException().getMessage() : "" ) );
					ps.setTimestamp(6, new java.sql.Timestamp(a.created.getTime()));
					ps.executeUpdate();
					ps.close();
					ps = null;
				} catch (SQLException ex) {
					ex.printStackTrace(System.err);
				} finally {
					if (ps != null) {
						try {
							ps.close();
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
	
	/**
	 * Add a new access object to the persistent Navajo store.
	 *
	 * @param a
	 */
	private final void addAccess(final Access a) {
		if (Dispatcher.getInstance().getNavajoConfig().dbPath != null) {
			SQLMap sqlMap = createConnection(false, false, false);
			Connection con = null;
			try {
				con = sqlMap.getConnection();
			} catch (SQLException e) {
				e.printStackTrace(System.err);
			}
			if (con != null) {
				PreparedStatement ps = null;
				try {
					
					String hostName = InetAddress.getLocalHost().getHostName()+" - "+Dispatcher.getInstance().getNavajoConfig().getInstanceName();
					ps = con.prepareStatement(insertAccessSQL);
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
					ps.setString(13, hostName);
					ps.setTimestamp(14, new java.sql.Timestamp(a.created.getTime()));
					ps.setString(15, a.getClientToken());
					ps.executeUpdate();
					ps.close();
					ps = null;
					// Only log details if exception occured or if full accesslog monitoring is enabled.
					if (a.getException() != null || Dispatcher.getInstance().getNavajoConfig().needsFullAccessLog(a) ) {
						addLog(con, a);
					}
					if (a.getMapStatistics() != null) {
						addMapLog(con, a);
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
		if ( am == null ) {
			addAccess(a);
			if (a.getPiggybackData()!=null) {
				updatePiggybackData(a.getPiggybackData());
			}
		} else {
			addAsync(a, am);
		}
	}

	private void updatePiggybackData(Set piggybackData) {
		for (Iterator iter = piggybackData.iterator(); iter.hasNext();) {
			Map element = (Map) iter.next();
			String type = (String)element.get("type");
			if ("performanceStats".equals(type)) {
				addPerformanceStats(element);
			}
		}
	}

	private void addPerformanceStats(Map element) {
		//System.err.println("OracleStore: storing: "+element);
		String accessId = (String)element.get("accessId");
		String clnt = (String)element.get("clientTime");
		if (accessId==null || clnt==null) {
			return;
		}
		int clientTime = Integer.parseInt(clnt);
		updatePerformanceStats(accessId, clientTime);
		
	}

	private final void updatePerformanceStats(String accessId, int clientTime) {
		if (Dispatcher.getInstance().getNavajoConfig().dbPath != null) {
			SQLMap sqlMap = createConnection(false, false, false);
			Connection con = null;
			try {
				con = sqlMap.getConnection();
			} catch (SQLException e) {
				e.printStackTrace(System.err);
			}
			if (con != null) {
				PreparedStatement ps = null;
				try {
					ps = con.prepareStatement(updateAccessSQL);
					ps.setInt(1, clientTime);		
					ps.setString(2, accessId);
					ps.executeUpdate();
					ps.close();
				}
				catch (SQLException ex) {
					ex.printStackTrace(System.err);
				} finally {
					if (ps != null) {
						try {
							ps.close();
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