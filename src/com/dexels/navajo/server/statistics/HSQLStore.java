package com.dexels.navajo.server.statistics;

import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.Dispatcher;
import java.sql.*;
import java.io.StringWriter;
import java.io.PrintWriter;
import com.dexels.navajo.document.Navajo;

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

public class HSQLStore implements StoreInterface {

  private static boolean ready = false;

  /**
   * Create a new Navajo store. Starts up HSQL server on localhost.
   *
   * @param path
   */
  public HSQLStore(final String path) {

    boolean hsqlRunning = false;
    // Check if instance already running.
    try {
      Connection c = createConnection(path, true);
      if (c != null) {
        System.err.println("HSQL is already running...");
        hsqlRunning = true;
        c.close();
      }
    }
    catch (Exception e) {

    }

    if (!hsqlRunning) {
      System.err.println("Starting up HSQL....");
      try {
        Class.forName("org.hsqldb.jdbcDriver");
        new Thread(
            new Runnable() {
          public void run() {
            org.hsqldb.Server hsqlServer = new org.hsqldb.Server();
            System.err.println("HSQL URL = " + path);
            String[] arguments = {
                "-database", path};
            HSQLStore.ready = true;
            hsqlServer.main(arguments);
          }
        }
        ).start();
      }
      catch (ClassNotFoundException ex) {
        ex.printStackTrace(System.err);
      }
    }
  }

  /**
   * Navajo store SQL queries.
   */
  private static final String insertAccessSQL = "insert into access " +
      "(access_id, webservice, username, totaltime, parsetime, authorisationtime, requestsize, requestencoding, compressedrecv, compressedsnd, ip_address, hostname, created) " +
      "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
  private static final String insertLog = "insert into log " +
      "(access_id, exception, navajoin, navajoout) values (?, ?, ?, ?)";

  /**
   * Create a connection to the Navajo store.
   *
   * @param db
   * @return
   */
  private final Connection createConnection(String db, boolean nowait) {

    while (!ready && !nowait) {
      try {
        System.err.println("Waiting for store to become ready.....");
        Thread.sleep(5000);
      }
      catch (InterruptedException ex1) {
      }
    }

    Connection myConnection = null;
    try {
      if (myConnection == null || !myConnection.isClosed()) {
        myConnection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost", "sa", "");
      }
    }
     catch (Exception ex) {
       System.err.println("Could not connect to HSQL store...");
     }
     return myConnection;
   }

   /**
    * Add a new access object to the persistent Navajo store.
    *
    * @param a
    */
  private final void addAccess(Access a) {
    if (Dispatcher.getNavajoConfig().dbPath != null) {
      Connection con = createConnection(Dispatcher.getNavajoConfig().dbPath, false);
      if (con != null) {
        try {
          PreparedStatement ps = con.prepareStatement(insertAccessSQL);
          ps.setString(1, a.accessID);
          ps.setString(2, a.rpcName);
          ps.setString(3, a.rpcUser);
          ps.setInt(4, a.getTotaltime());
          ps.setInt(5, a.parseTime);
          ps.setInt(6, a.authorisationTime);
          ps.setInt(7, a.contentLength);
          ps.setString(8, a.requestEncoding);
          ps.setBoolean(9, a.compressedReceive);
          ps.setBoolean(10, a.compressedSend);
          ps.setString(11, a.ipAddress);
          ps.setString(12, a.hostName);
          ps.setTimestamp(13, new java.sql.Timestamp(a.created.getTime()));
          ps.executeUpdate();
          ps.close();
          // Only log details if exception occured.
          if (a.getException() != null) {
            addLog(con, a);
          }
          con.close();
        }
        catch (SQLException ex) {
          ex.printStackTrace(System.err);
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
      ps.setString(2, ( w != null && w.toString().length() > 1 ? w.toString() : null ));
      java.io.ByteArrayOutputStream bosIn = new java.io.ByteArrayOutputStream();
      java.io.ByteArrayOutputStream bosOut = new java.io.ByteArrayOutputStream();
      Navajo inDoc = ( a.getCompiledScript() != null ? a.getCompiledScript().inDoc : null );
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
    } catch (Exception e) {
      e.printStackTrace(System.err);
    }
  }

  /**
   * Interface method to persist an Access object.
   *
   * @param a
   */
  public void storeAccess(Access a) {
    addAccess(a);
  }

}