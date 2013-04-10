package com.dexels.navajo.adapter.sqlmap;

/**
 * <p>Title: SQL Batch Update Helper</p>
 * <p>Description:
 * assists in running multiple SQL Statements within an SQLMap in the case
 * where in Sybase and Oracle, multiple statements cannot be used in a single
 * prepared statement</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Dexels B.V.</p>
 * @author Matthew Eichler
 * @version $Id$
 *
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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.util.ArrayList;
import java.util.StringTokenizer;

import com.dexels.navajo.server.Access;

@SuppressWarnings({"rawtypes", "unchecked"})
public class SQLBatchUpdateHelper {

	public static final String DELIMITER = ";";
	public static final char PLACEHOLDER = '?';

	public static final String vcIdent = "$Id$";
	private boolean debug = false;
	private String sql = null;
	private Connection conn = null;
	private ArrayList<Object> params = null;
	private ArrayList parsed = new ArrayList();
	private ArrayList preparedList = new ArrayList();
	private int updateCount = 0;
	private int pptr = 0;
	private ResultSet rs = null;
	private final boolean updateOnly;
	private Access myAccess = null;
	private boolean isLegacyMode = false;
	private String dbIdentifier = "";

	// --------------------------------------------------------------
	// constructors

	public SQLBatchUpdateHelper(final String sql, 
								final Connection conn, 
								final ArrayList<Object> params,
								Access myAccess,
								String dbIdentifier,
								StreamClosable callback,
								boolean isLegacyMode) throws SQLException {
		this.sql = sql;
		this.conn = conn;
		this.params = params;
		this.myAccess = myAccess;
		this.dbIdentifier = dbIdentifier;
		this.isLegacyMode = isLegacyMode;
		this.updateOnly = false;
		this.parseStatements(callback);
		this.executeStatements();

	}

	public SQLBatchUpdateHelper(final String sql, 
								final Connection conn,
								final ArrayList<Object> params, 
								Access myAccess,
								String dbIdentifier,
								StreamClosable callback,
								boolean isLegacyMode,
								boolean debug, 
								boolean updateOnly) throws SQLException {
		this.sql = sql;
		this.conn = conn;
		this.params = params;
		this.myAccess = myAccess;
		this.dbIdentifier = dbIdentifier;
		this.isLegacyMode = isLegacyMode;
		this.debug = debug;
		this.updateOnly = updateOnly;

		this.parseStatements(callback);
		this.executeStatements();
	}

	// ------------------------------------------------------------ public
	// methods

	public int getUpdateCount() {
		return (this.updateCount);
	}

	public ResultSet getResultSet() {
		return (this.rs);
	}

	public void closeLast() {
		final int last = this.preparedList.size() - 1;
		final PreparedStatement pre = (PreparedStatement) this.preparedList.get(last);
		try {
			pre.close();
		} catch (Exception e) { /* don't care */
		}
	}

	// ----------------------------------------------------------- private
	// methods

	private final void parseStatements(StreamClosable callback) throws SQLException {
		final StringTokenizer tok = new StringTokenizer(this.sql, DELIMITER);
		if (tok.countTokens() == 0) {
			throw new SQLException("tried to pass empty SQL statement batch");
		}
		if (this.debug) {
		    Access.writeToConsole(this.myAccess, "Amount of tokens: " + tok.countTokens());
		}
		while (tok.hasMoreElements()) {
			final String s = tok.nextToken();
			if (s.length() > 0 && !s.matches("\\s*")) {
				if (this.debug) {
				    Access.writeToConsole(this.myAccess, "parsed statement: " + s);
				}

				prepareStatement(s, callback);
			} else {
		        if (this.debug) {
		            Access.writeToConsole(this.myAccess, "Did not qualify");
		        }
			}

		}
        if (this.debug) {
            Access.writeToConsole(this.myAccess, "No more tokents.");
        }

	}

	protected void prepareStatement(final String s, StreamClosable callback) throws SQLException {
		this.parsed.add(s);
		final PreparedStatement prepared = this.conn.prepareStatement(s);
		final int required = this.countRequiredParameters(s);
		if (this.debug) {
		    Access.writeToConsole(this.myAccess, "required number of parameters = " + required);
		}
		this.setStatementParameters(prepared, required, callback);
		this.preparedList.add(prepared);
	}

	private final void executeStatements() throws SQLException {
		for (int i = 0; i < this.parsed.size(); i++) {

			final boolean last = i == (this.parsed.size() - 1);
			final String s = (String) parsed.get(i);
			PreparedStatement prepared = (PreparedStatement) this.preparedList.get(i);
			this.rs = null;

			if (!last || updateOnly) {
				prepared.executeUpdate();
				if (this.debug) {
				    Access.writeToConsole(this.myAccess, "successful execution of SQL '" + s + "'");
				}
			} else {
				try {
					this.rs = prepared.executeQuery();
					if (this.debug) {
					    Access.writeToConsole(this.myAccess, "executed last SQL '" + s + "' as query");
					}
				} catch (SQLException e) {
					if (rs != null) {
						this.rs.close();
					}
					prepared.close();
					this.rs = null;
					// For Sybase compatibility: sybase does not like to be
					// called using executeQuery() if query does not return a
					// resultset.
					if (e.getMessage().indexOf("JZ0R2") == -1) {
						throw e;
					}
				}
			}
			this.logWarnings(prepared);
			this.updateCount = this.updateCount + prepared.getUpdateCount();
			if (this.debug) {
			    Access.writeToConsole(this.myAccess, "cummulative update count is " + this.updateCount);
			}
			if (!last) {
				if (rs != null) {
					rs.close();
				}
				prepared.close();
				/**************************************************************
				 * closing does't seem to be enough, so we kill it completely
				 * otherwise there may be complaints about too many open cursors
				 * meichler@dexels.com 21.oct 2003
				 **************************************************************/
				prepared = null;
			}
		}

	}

	private final int countRequiredParameters(final String statement) {
		final char[] a = statement.toCharArray();
		int cnt = 0;
		for (int i = 0; i < a.length; i++) {
			if (a[i] == PLACEHOLDER) {
				cnt++;
			}
		}
		return (cnt);

	}

	private final void setStatementParameters(final PreparedStatement pre,
											  final int n,
											  StreamClosable callback) throws SQLException {
		if (this.params != null) {
			for (int i = 0; i < n; i++, this.pptr++) {
				if (this.pptr >= this.params.size()) {
					throw new SQLException("not enough parameters provided for multiple SQL statements");
				}

				final Object param = this.params.get(this.pptr);
				this.setParameter(i, param, pre, callback);
			}
		}

	}

	private final void setParameter(final int idx, 
									final Object param,
									final PreparedStatement pre,
									StreamClosable callback) throws SQLException {
		if (this.debug) {
		    Access.writeToConsole(this.myAccess, "parameter " + this.pptr + " = " + param);
		}
		
		SQLMapHelper.setParameter(pre, 
								  param, 
								  idx, 
								  callback, 
								  this.dbIdentifier, 
								  this.isLegacyMode, 
								  this.debug, 
								  this.myAccess);
	}

	private final void logWarnings(final PreparedStatement pre) throws SQLException {
		if (this.debug) {
			SQLWarning warning = pre.getWarnings();
			while (warning != null) {
			    Access.writeToConsole(this.myAccess, "SQL warning: " + warning.getMessage());
				warning = warning.getNextWarning();
			}
		}
	}
}
