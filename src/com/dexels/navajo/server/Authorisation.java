

/**
 * Title:        Navajo<p>
 * Description:  <p>
 * Copyright:    Copyright (c) Arjen Schoneveld<p>
 * Company:      Dexels<p>
 * @author Arjen Schoneveld
 * @version $Id$
 */
package com.dexels.navajo.server;


import java.sql.*;
import java.util.ResourceBundle;
import java.util.Random;
import org.dexels.grus.DbConnectionBroker;
import com.dexels.navajo.document.*;
import java.util.*;
import java.util.Hashtable;
import com.dexels.navajo.util.*;


/**
 * This class implements a database interface to the SQL repository implementation.
 */
public class Authorisation {

    /**
     * Log levels.
     */
    public static int LOG_SUCCESS = 0;
    public static int LOG_USER_ERROR = 1;
    public static int LOG_SYSTEM_ERROR = 2;
    public static int LOG_INFO = 3;
    public static int LOG_ACCESS = 4;

    public static int DBMS_MYSQL = 0;
    public static int DBMS_MSSQL = 1;

    public int currentDBMS = 0;

    private static int retry = 0;
    private DbConnectionBroker connectionBroker = null;

    public Authorisation(int DBMS, DbConnectionBroker connectionBroker) {
        this.currentDBMS = DBMS;
        this.connectionBroker = connectionBroker;
    }

    /**
     * Return user ID or -1 if not authenticated.
     */
    private int isAuthenticated(Connection con, String user, String password) throws SQLException {
        String query = "SELECT id, name, password FROM users WHERE name = ?";

        // Util.debugLog(2, "in isAuthenticated()");

        PreparedStatement stmt = con.prepareStatement(query);

        stmt.setString(1, user);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            int id = rs.getInt(1);
            String usr = rs.getString(2);
            String pwd = rs.getString(3);

            // Util.debugLog(2, "Found: " + usr + ", " + pwd);
            rs.close();
            stmt.close();
            if (pwd.equalsIgnoreCase(password)) {
                return id;
            } else
                return -1;
        } else
            return -1;

    }

    /**
     * Add a (username, password) combination if the username does not yet exist.
     * Return -1 if the user was not added and the userId if the user was added.
     */
    public int addUser(Access access, String user, String password) throws UserException, SystemException {

        int userId = -1;
        Connection con = null;

        try {

            /*
             // First check permissions.
             if (!checkPermissions(access, "user_view") && !access.rpcUser.equals(user)) {
             // rpcUser is not allowed for this operation.
             throw new UserException(UserException.OPERATION_NOT_PERMITTED, "");
             }
             */
            con = connectionBroker.getConnection();

            // Check if user already exists.
            if (getUserId(con, user) != -1)
                return -1;

            String query4 = "SELECT MAX(id)+1 FROM users";
            // String query2 = "INSERT INTO users (id, name, password) VALUES (?, ?, ?)";

            // Set locks.
            PreparedStatement stmt = null;

            // stmt.executeUpdate();
            // stmt.close();
            this.lockTable(con, "users", this.currentDBMS, true);

            // Find next user id.
            stmt = con.prepareStatement(query4);
            ResultSet rs = stmt.executeQuery();

            if (rs.next())
                userId = rs.getInt(1);
            rs.close();
            stmt.close();

            // Insert new user.
            String query2 = "INSERT INTO users (id, name, password) VALUES ("
                    + userId + ", '" + user + "', '" + password + "')";

            stmt = con.prepareStatement(query2);
            // stmt.setInt(1, userId);
            // stmt.setString(2, user);
            // stmt.setString(3, password);
            stmt.executeUpdate();
            // int userId = getUserId(con, user);

            stmt.close();

            // Free locks.
            // stmt = con.prepareStatement(query3);
            // stmt.executeUpdate();
            // stmt.close();
            this.lockTable(con, "users", this.currentDBMS, false);

            // connectionBroker.freeConnection(con);

        } catch (SQLException sqle) {
            throw new SystemException(-1, sqle.getMessage());
        } finally {
            if (con != null)
                connectionBroker.freeConnection(con);
        }

        return userId;
    }

    /**
     * Delete a user and all of its references from relevant tables.
     */
    public int deleteUser(Access access, String user) throws SystemException {
        Connection con = null;

        int userId = -1;

        try {
            con = connectionBroker.getConnection();
            userId = getUserId(con, user);
            // Check if user already exists.
            // System.err.println("going to delete userId= "+userId);

            // if ((userId = getUserId(con, user)) == -1)
            // return -1;

            String query1 = "DELETE FROM parameters WHERE user_id = ?";
            // String query2 = "DELETE FROM authorisation WHERE user_id = ?";
            String query2 = "DELETE FROM conditions WHERE user_id = ?";
            String query3 = "DELETE FROM group_authorisation WHERE user_id = ?";
            String query4 = "DELETE FROM users WHERE id = ?";

            PreparedStatement stmt = con.prepareStatement(query1);

            stmt.setInt(1, userId);
            // System.err.println("query: " + stmt.toString());
            stmt.executeUpdate();

            stmt = con.prepareStatement(query2);
            stmt.setInt(1, userId);
            // System.err.println("query: " + stmt.toString());
            stmt.executeUpdate();

            stmt = con.prepareStatement(query3);
            stmt.setInt(1, userId);
            // System.err.println("query: " + stmt.toString());
            stmt.executeUpdate();

            stmt = con.prepareStatement(query4);
            stmt.setInt(1, userId);
            // System.err.println("query: " + stmt.toString());
            stmt.executeUpdate();

            stmt.close();
            // connectionBroker.freeConnection(con);

        } catch (SQLException sqle) {
            throw new SystemException(-1, sqle.getMessage());
        } finally {
            if (con != null) {
                connectionBroker.freeConnection(con);
                // System.err.println("Closed database connection");
            }
        }

        return userId;
    }

    /**
     * Delete a service and all of its references from relevant tables.
     */
    public void deleteService(Access access, String service) throws SystemException {
        Connection con = null;

        try {
            con = connectionBroker.getConnection();

            int serviceId = getServiceId(con, service);
            // Check if user already exists.

            String query1 = "DELETE FROM conditions WHERE service_id = ?";
            // String query2 = "DELETE FROM authorisation WHERE service_id = ?";
            String query3 = "DELETE FROM services WHERE id = ?";

            PreparedStatement stmt = con.prepareStatement(query1);

            stmt.setInt(1, serviceId);
            stmt.executeUpdate();
            // System.err.println("query1: "+query3);
            /*
             stmt = con.prepareStatement(query2);
             stmt.setInt(1, id);
             stmt.executeUpdate();
             System.err.println("query2: "+query2);
             */
            stmt = con.prepareStatement(query3);
            stmt.setInt(1, serviceId);
            stmt.executeUpdate();
            // System.err.println("query3: "+query3);

            stmt.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace(System.err);
            throw new SystemException(-1, sqle.getMessage());
        } finally {
            if (con != null) {
                connectionBroker.freeConnection(con);
                // System.err.println("Closed database connection");
            }
        }
    }

    /**
     * Delete a service group and all of its references from relevant tables.
     */
    public void deleteServiceGroup(Access access, String name) throws SystemException {
        Connection con = null;

        try {
            con = connectionBroker.getConnection();

            int serviceId = getServiceGroupId(con, name);
            // Check if user already exists.

            // String query1 = "DELETE FROM conditions WHERE service_id = ?";
            String query1 = "update services set group_id=0 where group_id=?";
            String query2 = "DELETE FROM group_authorisation WHERE group_id = ?";
            String query3 = "DELETE FROM service_group WHERE id = ?";

            PreparedStatement stmt = con.prepareStatement(query1);

            stmt.setInt(1, serviceId);
            stmt.executeUpdate();
            // System.err.println("query1: "+query1);

            stmt = con.prepareStatement(query2);
            stmt.setInt(1, serviceId);
            stmt.executeUpdate();
            // System.err.println("query2: "+query2);

            stmt = con.prepareStatement(query3);
            stmt.setInt(1, serviceId);
            stmt.executeUpdate();
            // System.err.println("query3: "+query3);

            stmt.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace(System.err);
            throw new SystemException(-1, sqle.getMessage());
        } finally {
            if (con != null) {
                connectionBroker.freeConnection(con);
                // System.err.println("Closed database connection");
            }
        }
    }

    public int deleteDefinition(Access access, String definition) throws SystemException {

        int id = -1;
        Connection con = null;

        try {
            con = connectionBroker.getConnection();
            id = getDefinitionId(con, definition);
            String query1 = "DELETE FROM parameters WHERE parameter_id = ?";
            String query2 = "DELETE FROM definitions WHERE parameter_id = ?";

            PreparedStatement stmt = con.prepareStatement(query1);

            stmt.setInt(1, id);
            System.err.println("query1: " + stmt.toString());
            stmt.executeUpdate();

            stmt = con.prepareStatement(query2);
            stmt.setInt(1, id);
            System.err.println("query2: " + stmt.toString());
            stmt.executeUpdate();

            stmt.close();
            // connectionBroker.freeConnection(con);
        } catch (SQLException sqle) {
            sqle.printStackTrace(System.err);
            throw new SystemException(-1, sqle.getMessage());
        } finally {
            if (con != null) {
                connectionBroker.freeConnection(con);
                // System.err.println("Closed database connection");
            }
        }
        return id;
    }

    /**
     * Add a (name, type) combination if the name does not yet exist.
     * Return -1 if the definition was not added and the definitionId if the definition was added.
     */
    public int addDefinition(Access access, String name, String type) throws SystemException {
        int defId = -1;
        Connection con = null;

        try {
            con = connectionBroker.getConnection();

            if (getDefinitionId(con, name) != -1)
                return -1;

            String query2 = "INSERT INTO definitions VALUES (0, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(query2);

            stmt.setString(1, name);
            stmt.setString(2, type);
            stmt.executeUpdate();

            defId = getDefinitionId(con, name);

            stmt.close();
            // connectionBroker.freeConnection(con);
        } catch (SQLException sqle) {
            sqle.printStackTrace(System.err);
            throw new SystemException(-1, sqle.getMessage());
        } finally {
            if (con != null) {
                connectionBroker.freeConnection(con);
                // System.err.println("Closed database connection");
            }
        }

        return defId;
    }

    public void addCondition(Access access, String user, String service, String condition, String comment)
            throws SystemException, UserException {
        Connection con = null;

        try {

            /*
             // First check permissions.
             if (!checkPermissions(access, "user_view") && !access.rpcUser.equals(user)) {
             // rpcUser is not allowed for this operation.
             throw new UserException(UserException.OPERATION_NOT_PERMITTED, "");
             }
             */
            con = connectionBroker.getConnection();

            String query = "INSERT INTO conditions (service_id, user_id, condition, comment) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(query);

            int userId = getUserId(con, user);
            int serviceId = getServiceId(con, service);

            stmt.setInt(1, serviceId);
            stmt.setInt(2, userId);
            stmt.setString(3, condition);
            stmt.setString(4, comment);

            stmt.executeUpdate();

            stmt.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace(System.err);
            throw new SystemException(-1, sqle.getMessage());
        } finally {
            if (con != null) {
                connectionBroker.freeConnection(con);
                // System.err.println("Closed database connection");
            }
        }

    }

    public void editValue(Access access, int id, String value, String condition) throws SystemException {
        Connection con = null;

        try {
            con = connectionBroker.getConnection();

            String query1 = "UPDATE parameters SET value = ?, condition = ? WHERE id = ?";

            PreparedStatement stmt = con.prepareStatement(query1);

            stmt.setString(1, value);
            stmt.setString(2, condition);
            stmt.setInt(3, id);
            stmt.executeUpdate();

            stmt.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace(System.err);
            throw new SystemException(-1, sqle.getMessage());
        } finally {
            if (con != null) {
                connectionBroker.freeConnection(con);
                // System.err.println("Closed database connection");
            }
        }

    }

    public void editCondition(Access access, int id, String condition, String comment) throws SystemException {

        Connection con = null;

        try {
            con = connectionBroker.getConnection();

            String query1 = "UPDATE conditions SET condition = ?, comment = ? WHERE id = ?";

            PreparedStatement stmt = con.prepareStatement(query1);

            stmt.setString(1, condition);
            stmt.setString(2, comment);
            stmt.setInt(3, id);
            stmt.executeUpdate();

            stmt.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace(System.err);
            throw new SystemException(-1, sqle.getMessage());
        } finally {
            if (con != null) {
                connectionBroker.freeConnection(con);
                // System.err.println("Closed database connection");
            }
        }

    }

    public void deleteValue(Access access, int id) throws SystemException {
        Connection con = null;

        try {
            con = connectionBroker.getConnection();
            String query = "DELETE FROM parameters WHERE id = ?";
            PreparedStatement stmt = con.prepareStatement(query);

            stmt.setInt(1, id);

            stmt.executeUpdate();

            stmt.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace(System.err);
            throw new SystemException(-1, sqle.getMessage());
        } finally {
            if (con != null) {
                connectionBroker.freeConnection(con);
                // System.err.println("Closed database connection");
            }
        }
    }

    public void deleteCondition(Access access, int id) throws SystemException {
        Connection con = null;

        try {
            con = connectionBroker.getConnection();

            String query = "DELETE FROM conditions WHERE id = ?";
            PreparedStatement stmt = con.prepareStatement(query);

            stmt.setInt(1, id);

            stmt.executeUpdate();

            stmt.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace(System.err);
            throw new SystemException(-1, sqle.getMessage());
        } finally {
            if (con != null) {
                connectionBroker.freeConnection(con);
                // System.err.println("Closed database connection");
            }
        }
    }

    public void addValue(Access access, String user, String definition, String value, String condition)
            throws SystemException, UserException {

        Connection con = null;

        try {

            // // First check permissions.
            // if (!checkPermissions(access, "user_view") && !access.rpcUser.equals(userId)) {
            // // rpcUser is not allowed for this operation.
            // throw new UserException(UserException.OPERATION_NOT_PERMITTED, "");
            // }

            con = connectionBroker.getConnection();

            int userId = getUserId(con, user);
            int defId = getDefinitionId(con, definition);

            String query = "INSERT INTO parameters (parameter_id, user_id, value, condition) VALUES (?, ?, ?, ?)";

            // String query = "INSERT INTO parameters (parameter_id, user_id, value, condition) VALUES (" + defId + ", " +
            // userId + ", '" + value + "', '" + condition + "')";
            Util.debugLog("addValue(): " + query);
            PreparedStatement stmt = con.prepareStatement(query);

            stmt.setInt(1, defId);
            stmt.setInt(2, userId);
            stmt.setString(3, value);
            stmt.setString(4, condition);

            stmt.executeUpdate();

        } catch (SQLException sqle) {
            sqle.printStackTrace(System.err);
            throw new SystemException(-1, sqle.getMessage());
        } finally {
            if (con != null) {
                connectionBroker.freeConnection(con);
                // System.err.println("Closed database connection");
            }
        }

    }

    private int getUserId(Connection con, String name) throws SQLException {

        String query2 = "SELECT id, name FROM users WHERE name = ?";

        PreparedStatement stmt = con.prepareStatement(query2);

        stmt.setString(1, name);
        ResultSet rs = stmt.executeQuery();

        int id = -1;

        if (rs.next()) {
            id = rs.getInt(1);
        }
        rs.close();
        stmt.close();

        return id;
    }

    /*
     public int getGroupId(Connection con, String groupName) throws SQLException {

     //    int serviceId = getServiceId(con, service);
     String query = "SELECT group_id FROM service_group WHERE name = " + groupName;
     PreparedStatement stmt = con.prepareStatement(query);
     ResultSet rs = stmt.executeQuery();
     int id = -1;
     if (rs.next()) {
     id = rs.getInt(1);
     }
     rs.close();
     stmt.close();
     return id;
     }*/

    private int getServiceId(Connection con, String name) throws SQLException {

        String query2 = "SELECT id, name FROM services WHERE name = ?";

        PreparedStatement stmt = con.prepareStatement(query2);

        stmt.setString(1, name);
        ResultSet rs = stmt.executeQuery();

        int id = -1;

        if (rs.next()) {
            id = rs.getInt(1);
        }
        rs.close();
        stmt.close();

        return id;
    }

    private int getServiceGroupId(Connection con, String name) throws SQLException {

        String query = "SELECT id FROM service_group WHERE name = ?";

        PreparedStatement stmt = con.prepareStatement(query);

        stmt.setString(1, name);
        ResultSet rs = stmt.executeQuery();

        int id = -1;

        if (rs.next()) {
            id = rs.getInt(1);
        }
        rs.close();
        stmt.close();

        return id;
    }

    private int getDefinitionId(Connection con, String name) throws SQLException {

        String query2 = "SELECT parameter_id, name FROM definitions WHERE name = ?";

        PreparedStatement stmt = con.prepareStatement(query2);

        stmt.setString(1, name);
        ResultSet rs = stmt.executeQuery();

        int id = -1;

        if (rs.next()) {
            id = rs.getInt(1);
        }
        rs.close();
        stmt.close();

        return id;
    }

    public int addAuthorisation(Access access, String user, int groupId) throws SystemException, UserException {

        Connection con = null;

        try {
            con = connectionBroker.getConnection();
            int userId = getUserId(con, user);

            // Util.debugLog(2, "addAuthorisation(): user: " + name + ", service: " + service);

            String query3 = "INSERT INTO group_authorisation VALUES (?, ?)";

            // Check if authorisation already exists.
            /* if (isAuthorized(con, groupId, userId))
             return -1;*/

            // Util.debugLog(2, "Authorising: " + userId + " and " + serviceId);

            PreparedStatement stmt = con.prepareStatement(query3);

            stmt.setInt(1, userId);
            stmt.setInt(2, groupId);
            stmt.executeUpdate();

            stmt.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace(System.err);
            throw new SystemException(-1, sqle.getMessage());
        } finally {
            if (con != null) {
                connectionBroker.freeConnection(con);
                // System.err.println("Closed database connection");
            }
        }

        return 0;
    }

    public int deleteAuthorisation(Access access, String name, int serviceGroupId) throws SystemException {
        Connection con = null;

        try {

            con = connectionBroker.getConnection();

            // Util.debugLog(2, "deleteAuthorisation(): user: " + name + ", service: " + service);

            String query3 = "DELETE FROM group_authorisation WHERE user_id = ? AND group_id = ?";

            int userId = getUserId(con, name);

            // Check if authorisation already exists.
            /* if (isAuthorized(con, "service", userId) == -1)
             return -1;*/

            // Util.debugLog(2, "Deleting authorisation for: " + userId + " and " + serviceId);

            PreparedStatement stmt = con.prepareStatement(query3);

            stmt.setInt(1, userId);
            stmt.setInt(2, serviceGroupId);
            stmt.executeUpdate();

            stmt.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace(System.err);
            throw new SystemException(-1, sqle.getMessage());
        } finally {
            if (con != null) {
                connectionBroker.freeConnection(con);
                // System.err.println("Closed database connection");
            }
        }

        return 0;
    }

    /**
     * Add a service, if the service does not yet exist.
     * Return -1 if the service was not added and the serviceId if the service was added.
     */
    public int addService(Access access, String name, int groupId) throws SystemException {
        Connection con = null;
        int serviceId = -1;

        try {

            con = connectionBroker.getConnection();

            if (getServiceId(con, name) != -1)
                return -1;

            String query4 = "SELECT MAX(id)+1 FROM services";
            String query2 = "INSERT INTO services VALUES (?, ?, ?)";
            // String query5 = "INSERT INTO service_in_group VALUES (?, ?)";

            // Set locks.
            PreparedStatement stmt = null;

            this.lockTable(con, "services", this.currentDBMS, true);

            // Find next service id.
            stmt = con.prepareStatement(query4);
            ResultSet rs = stmt.executeQuery();

            if (rs.next())
                serviceId = rs.getInt(1);
            rs.close();
            stmt.close();

            // Insert new service.
            stmt = con.prepareStatement(query2);
            stmt.setInt(1, serviceId);
            stmt.setString(2, name);
            stmt.setInt(3, groupId);
            stmt.executeUpdate();
            stmt.close();

            // Insert into service_in_group table.
            // stmt = con.prepareStatement(query5);
            // stmt.setInt(1, groupId);
            // stmt.setInt(2, serviceId);
            // stmt.executeUpdate();
            // stmt.close();

            // Free locks.
            this.lockTable(con, "services", this.currentDBMS, false);

        } catch (SQLException sqle) {
            sqle.printStackTrace(System.err);
            throw new SystemException(-1, sqle.getMessage());
        } finally {
            if (con != null) {
                connectionBroker.freeConnection(con);
                // System.err.println("Closed database connection");
            }
        }
        return serviceId;
    }

    /**
     * Add a serviceGroup, if the service does not yet exist.
     * Return -1 if the service was not added and the serviceId if the service was added.
     */
    public int addServiceGroup(Access access, String name, String servlet) throws SystemException {
        Connection con = null;
        int serviceGroupId = -1;

        try {

            con = connectionBroker.getConnection();

            if (getServiceGroupId(con, name) != -1)
                return -1;

            String query1 = "SELECT MAX(id)+1 FROM service_group";
            String query2 = "INSERT INTO service_group VALUES (?, ?, ?)";

            // Set locks.
            PreparedStatement stmt = null;

            this.lockTable(con, "service_group", this.currentDBMS, true);

            // Find next service id.
            stmt = con.prepareStatement(query1);
            ResultSet rs = stmt.executeQuery();

            if (rs.next())
                serviceGroupId = rs.getInt(1);
            rs.close();
            stmt.close();

            // Insert new service.
            stmt = con.prepareStatement(query2);
            stmt.setInt(1, serviceGroupId);
            stmt.setString(2, name);
            stmt.setString(3, servlet);
            stmt.executeUpdate();
            stmt.close();

            // Free locks.
            this.lockTable(con, "service_group", this.currentDBMS, false);

        } catch (SQLException sqle) {
            sqle.printStackTrace(System.err);
            throw new SystemException(-1, sqle.getMessage());
        } finally {
            if (con != null) {
                connectionBroker.freeConnection(con);
                // System.err.println("Closed database connection");
            }
        }
        return serviceGroupId;
    }

    private boolean isAuthorized(Connection con, int groupId, int userId) throws SQLException {

        boolean result = false;
        String query = "SELECT s.id FROM service_group s, group_authorisation a WHERE "
                + "s.id = a.service_id AND a.user_id = ? AND s.id = ?";
        PreparedStatement stmt = con.prepareStatement(query);

        stmt.setInt(1, userId);
        stmt.setInt(2, groupId);
        ResultSet rs = stmt.executeQuery();

        if (rs.next())
            result = true;
        rs.close();
        stmt.close();
        return result;
    }

    /**
     * Return service ID or -1 if not authorized.
     */
    private int isAuthorized(Connection con, String service, int userID)
            throws SQLException {

        Util.debugLog("in isAuthorized(), service = >" + service + "<, userID = " + userID);
        String query = "SELECT s.id FROM services s, group_authorisation a WHERE "
                + "s.name = ? AND s.group_id = a.group_id AND a.user_id = ?";

        PreparedStatement stmt = con.prepareStatement(query);

        stmt.setString(1, service);
        stmt.setInt(2, userID);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            int id = rs.getInt(1);

            rs.close();
            stmt.close();
            return id;
        } else {
            rs.close();
            stmt.close();
            return -1;
        }
    }

    private String getCurrentDateQuery() {

        if (this.currentDBMS == DBMS_MSSQL)
            return "CONVERT(char(20), GETDATE(), 105)";
        else if (this.currentDBMS == DBMS_MYSQL)
            return "CURDATE()";
        else
            return "";
    }

    private String getCurrentTimeQuery() {
        if (this.currentDBMS == DBMS_MSSQL)
            return "CONVERT(char(20), GETDATE(), 108)";
        else if (this.currentDBMS == DBMS_MYSQL)
            return "CURTIME()";
        else
            return "";
    }

    private void lockTable(Connection con, String table, int DBMS, boolean lock) throws SQLException {

        if (lock) {
            if (DBMS == DBMS_MYSQL) {
                String query = "LOCK TABLES " + table + " WRITE";
                Statement stmt = con.createStatement();

                stmt.executeUpdate(query);
                stmt.close();
            } else
            if (DBMS == DBMS_MSSQL) {
                con.setAutoCommit(false);
                con.setTransactionIsolation(con.TRANSACTION_SERIALIZABLE);
            }
        } else {
            if (DBMS == DBMS_MYSQL) {
                String query = "UNLOCK TABLES";
                Statement stmt = con.createStatement();

                stmt.executeUpdate(query);
                stmt.close();
            } else
            if (DBMS == DBMS_MSSQL) {
                con.commit();
                con.setAutoCommit(true);
                con.setTransactionIsolation(con.TRANSACTION_NONE);
            }
        }
    }

    private synchronized int updateAccessCount(Connection con) throws SQLException {

        int counter = 0;

        String query2 = "SELECT a_count FROM access_count";
        String query3 = "UPDATE access_count SET a_count = ?";

        // Lock access_count.
        lockTable(con, "access_count", this.currentDBMS, true);
        // Read value
        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery(query2);

        if (rs.next()) {
            counter = rs.getInt(1);
            counter++;
            rs.close();
            statement.close();
        }

        // Write value
        PreparedStatement stmt = con.prepareStatement(query3);

        stmt.setInt(1, counter);
        stmt.executeUpdate();
        stmt.close();
        // Unlock access_count table
        lockTable(con, "access_count", this.currentDBMS, false);

        return counter;
    }

    public synchronized int getNextAanvraagNummer(Access access) throws SystemException {

        int counter = 0;
        Connection con = null;

        try {
            con = connectionBroker.getConnection();

            String query2 = "SELECT a_count FROM polis_count";
            String query3 = "UPDATE polis_count SET a_count = ?";

            Statement statement = con.createStatement();

            // Lock access_count table
            // statement.executeUpdate(query1);
            this.lockTable(con, "polis_count", this.currentDBMS, true);

            // Read value
            ResultSet rs = statement.executeQuery(query2);

            if (rs.next()) {
                counter = rs.getInt(1);
                counter++;
                rs.close();
                statement.close();
            }

            // Write value
            PreparedStatement stmt = con.prepareStatement(query3);

            stmt.setInt(1, counter);
            stmt.executeUpdate();
            stmt.close();

            // Unlock access_count table
            // statement.executeUpdate(query4);
            this.lockTable(con, "polis_count", this.currentDBMS, false);

        } catch (SQLException sqle) {
            sqle.printStackTrace(System.err);
            throw new SystemException(-1, sqle.getMessage());
        } finally {
            if (con != null) {
                connectionBroker.freeConnection(con);
                // System.err.println("Closed database connection");
            }
        }

        return counter;
    }

    public int logAccess(Access access)
            throws SystemException {
        String query = "INSERT INTO access (id, user_id, service_id, entered, tijdstip, address, host, user_agent) values "
                + "(?, ?, ?, " + this.getCurrentDateQuery() + ", "
                + this.getCurrentTimeQuery() + ", ?, ?, ?)";

        Connection con = null;
        int count = -1;

        try {
            con = connectionBroker.getConnection();

            count = updateAccessCount(con);
            PreparedStatement stmt = con.prepareStatement(query);

            java.util.Date datum = new java.util.Date();
            java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");

            // Util.debugLog(2, "in logAccess(): datum is " + format.format(datum));

            stmt.setInt(1, count);
            stmt.setInt(2, access.userID);
            stmt.setInt(3, access.serviceID);
            stmt.setString(4, access.ipAddress);
            stmt.setString(5, access.hostName);
            stmt.setString(6, access.userAgent);

            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace(System.err);
            throw new SystemException(-1, sqle.getMessage());
        } finally {
            if (con != null) {
                connectionBroker.freeConnection(con);
                // System.err.println("Closed database connection");
            }
        }

        return count;
    }

    public ConditionData showCondition(Access access, int id) throws SystemException {

        String query = "SELECT id, condition, comment FROM conditions WHERE id = ?";

        Connection con = null;
        ConditionData cnd = null;

        try {

            con = connectionBroker.getConnection();

            PreparedStatement stmt = con.prepareStatement(query);

            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                cnd = new ConditionData();
                cnd.id = rs.getInt(1);
                cnd.condition = rs.getString(2);
                cnd.comment = rs.getString(3);
            }

            stmt.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace(System.err);
            throw new SystemException(-1, sqle.getMessage());
        } finally {
            if (con != null) {
                connectionBroker.freeConnection(con);
                // System.err.println("Closed database connection");
            }
        }

        return cnd;
    }

    public Vector showConditions(Access access, String user, String service) throws SystemException {

        Vector result = new Vector();
        Connection con = null;
        String query = "SELECT id, service_id, user_id, condition, comment FROM conditions WHERE service_id = ? AND user_id = ?";

        try {
            con = connectionBroker.getConnection();
            int userId = getUserId(con, user);
            int serviceId = getServiceId(con, service);

            PreparedStatement stmt = con.prepareStatement(query);

            stmt.setInt(1, serviceId);
            stmt.setInt(2, userId);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ConditionData cnd = new ConditionData();

                cnd.id = rs.getInt(1);
                cnd.serviceId = rs.getInt(2);
                cnd.userId = rs.getInt(3);
                cnd.condition = rs.getString(4);
                cnd.comment = rs.getString(5);
                result.add(cnd);
            }

            stmt.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace(System.err);
            throw new SystemException(-1, sqle.getMessage());
        } finally {
            if (con != null) {
                connectionBroker.freeConnection(con);
                // System.err.println("Closed database connection");
            }
        }

        return result;
    }

    public Vector showAuthorised(Access access, String user) throws SystemException {

        Vector result = new Vector();
        Connection con = null;

        try {
            con = connectionBroker.getConnection();

            String query1 = "SELECT service_id FROM authorisation WHERE user_id = ?";
            String query2 = "SELECT id FROM users WHERE name = ?";
            String query3 = "SELECT name FROM services WHERE id = ?";

            PreparedStatement stmt = con.prepareStatement(query2);

            stmt.setString(1, user);
            ResultSet rs = stmt.executeQuery();

            int userId = -1;

            if (rs.next()) {
                userId = rs.getInt(1);
            }

            stmt = con.prepareStatement(query1);
            stmt.setInt(1, userId);
            rs = stmt.executeQuery();

            int serviceId = -1;

            while (rs.next()) {
                serviceId = rs.getInt(1);
                PreparedStatement stmt2 = con.prepareStatement(query3);

                stmt2.setInt(1, serviceId);
                ResultSet rs2 = stmt2.executeQuery();
                String service = "";

                if (rs2.next()) {
                    service = rs2.getString(1);
                    rs2.close();
                }
                stmt2.close();
                result.add(service);
            }

            rs.close();
            stmt.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace(System.err);
            throw new SystemException(-1, sqle.getMessage());
        } finally {
            if (con != null) {
                connectionBroker.freeConnection(con);
                // System.err.println("Closed database connection");
            }
        }

        return result;
    }

    /**
     * Check if a user is authorized to perform a certain service. If so
     * return an Access object (if either userID = -1 or serviceID = -1,
     * user is not authorized).
     */

    public Access authorizeUser(DbConnectionBroker broker, String user, String password,
            String rpcName, String userAgent, String ipAddress,
            String hostName, boolean log)
            throws SystemException {
        int userID = -1;
        int serviceID = -1;
        int accessID = -1;
        Connection con = null;
        Access access = null;

        try {
            con = broker.getConnection();
            Util.debugLog(this, "con: " + con);
            if (con == null) // Oops. lost connection or something, reinitialize broker.
            {
                // THIS SHOULD NOT HAPPEN, ALTHOUGH IT DOES SOMETIMES, THAT'S WHY THE FOLLOWING PATCH:
                // System.err.println("Lost connections in pool, creating new pool");
                ResourceBundle rb = ResourceBundle.getBundle("navajoserver");

                try {
                    broker = new DbConnectionBroker(rb.getString("authorisation_driver"),
                            rb.getString("authorisation_url"),
                            rb.getString("authorisation_user"),
                            rb.getString("authorisation_pwd"),
                            2, 25, "/tmp/log.db", 0.05);
                } catch (ClassNotFoundException cnfe) {// System.err.println("failed: " + cnfe.getMessage());
                }
                Util.debugLog(this, "Re-Opened connection to AUTHORISATION DB");
                con = broker.getConnection();
            }

            userID = isAuthenticated(con, user, password);

            Util.debugLog(this, "userID: " + userID);
            if (userID != -1)
                serviceID = isAuthorized(con, rpcName, userID);
            else
                throw new SystemException(SystemException.NOT_AUTHORISED, "Not authorized");

            if (serviceID == -1)
                throw new SystemException(SystemException.NOT_AUTHENTICATED, "Not authenticated");

            Util.debugLog(this, "serviceID: " + serviceID);

            access = new Access(-1, userID, serviceID, user, rpcName, userAgent, ipAddress, hostName);

            if (log) {
                accessID = logAccess(access);
                access.accessID = accessID;
            }

        } catch (SQLException sqle) {
            sqle.printStackTrace(System.err);
            throw new SystemException(-1, sqle.getMessage());
        } finally {
            if (con != null) {
                broker.freeConnection(con);
            }
        }

        return access;
    }

    public void logTiming(Access access, int part, long timespent) throws SystemException {

        String query = "INSERT INTO timing (access_id, part, timespent) VALUES (?, ?, ?)";
        Connection con = null;

        try {
            con = connectionBroker.getConnection();

            PreparedStatement stmt = con.prepareStatement(query);

            stmt.setInt(1, access.accessID);
            stmt.setInt(2, part);
            stmt.setFloat(3, (float) timespent);
            stmt.executeUpdate();
            stmt.close();

        } catch (SQLException sqle) {
            sqle.printStackTrace(System.err);
            throw new SystemException(-1, sqle.getMessage());
        } finally {
            if (con != null) {
                connectionBroker.freeConnection(con);
            }
        }
    }

    /**
     * Log an action to log table.
     * TODO: User should be able to specify a list of triggers. The triggers are defined using
     * error codes.
     */
    public void logAction(Access access, int level, String comment)
            throws SystemException {
        String query = "INSERT INTO log (user_id, service_id, access_id, log_level, comment) VALUES (?, ?, ?, ?, ?)";
        Connection con = null;

        try {
            con = connectionBroker.getConnection();

            PreparedStatement stmt = con.prepareStatement(query);

            stmt.setInt(1, access.userID);
            stmt.setInt(2, access.serviceID);
            stmt.setInt(3, access.accessID);
            stmt.setInt(4, level);
            // byte [] aap = comment.getBytes();
            // Util.debugLog("About to write (" + comment.length() + ") :\n");
            // Util.debugLog(comment);
            // java.io.StringReader reader = new java.io.StringReader(comment);
            // stmt.setCharacterStream(5, reader, comment.length());
            stmt.setString(5, comment);
            // stmt.setBytes(5, aap);
            // stmt.setString(5, comment);
            // stmt.setObject(5, comment);
            stmt.executeUpdate();

            stmt.close();

        } catch (SQLException sqle) {
            sqle.printStackTrace(System.err);
            throw new SystemException(-1, sqle.getMessage());
        } finally {
            if (con != null) {
                connectionBroker.freeConnection(con);
                // System.err.println("Closed database connection");
            }
        }
    }

    /**
     * Check conditions for given rpc_name.
     */

    public ConditionData[] checkConditions(Access access) throws UserException, SystemException {
        int rpc = access.serviceID;
        int user = access.userID;
        ArrayList messages = new ArrayList();

        String query = "SELECT * FROM conditions WHERE user_id = ? AND service_id = ?";
        Connection con = null;

        try {
            con = connectionBroker.getConnection();
            PreparedStatement stmt = con.prepareStatement(query);

            stmt.setInt(1, user);
            stmt.setInt(2, rpc);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String condition = rs.getString("condition");
                String comment = rs.getString("comment");
                int id = rs.getInt("id");

                ConditionData cd = new ConditionData();

                cd.condition = condition;
                cd.comment = comment;
                cd.id = id;
                messages.add(cd);

            }

            rs.close();
            stmt.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace(System.err);
            throw new SystemException(-1, sqle.getMessage());
        } finally {
            if (con != null) {
                connectionBroker.freeConnection(con);
                // System.err.println("Closed database connection");
            }
        }

        if (messages.size() > 0) {
            ConditionData[] msgArray = new ConditionData[messages.size()];

            messages.toArray(msgArray);
            return msgArray;
        } else
            return null;
    }

    /**
     * Return all users in the database.
     */
    public Vector allUsers(Access access) throws SystemException {

        Vector result = new Vector();
        boolean all = false;
        Connection con = null;

        try {
            // Check if user has correct permissions.
            all = true;// checkPermissions(access, "user_view");

            String query = "SELECT name FROM users";

            con = connectionBroker.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                String user = rs.getString(1);

                if (!all && user.equals(access.rpcUser))
                    result.add(user);
                else if (all)
                    result.add(user);
            }

            rs.close();
            stmt.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace(System.err);
            throw new SystemException(-1, sqle.getMessage());
        } finally {
            if (con != null) {
                connectionBroker.freeConnection(con);
                // System.err.println("Closed database connection");
            }
        }

        return result;
    }

    /**
     * Return all services in the database.
     */
    public Vector allServices(Access access) throws SystemException {

        Vector result = new Vector();

        String query = "SELECT name FROM services";
        Connection con = null;

        try {
            con = connectionBroker.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                String user = rs.getString(1);

                result.add(user);
            }

            rs.close();
            stmt.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace(System.err);
            throw new SystemException(-1, sqle.getMessage());
        } finally {
            if (con != null) {
                connectionBroker.freeConnection(con);
                // System.err.println("Closed database connection");
            }
        }

        return result;
    }

    public Vector allServiceGroups(Access access) throws SystemException {

        Vector result = new Vector();

        String query = "SELECT id, name, servlet FROM service_group";
        Connection con = null;

        try {
            con = connectionBroker.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                ServiceGroup sg = new ServiceGroup();

                sg.id = rs.getInt(1);
                sg.name = rs.getString(2);
                sg.servlet = rs.getString(3);
                result.add(sg);
            }

            rs.close();
            stmt.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace(System.err);
            throw new SystemException(-1, sqle.getMessage());
        } finally {
            if (con != null) {
                connectionBroker.freeConnection(con);
                // System.err.println("Closed database connection");
            }
        }

        return result;
    }

    /**
     * Return all parameter definitions in the database.
     */
    public Vector allDefinitions(Access access) throws SystemException {

        Vector result = new Vector();

        String query = "SELECT name, type, parameter_id FROM definitions";
        Connection con = null;

        try {
            con = connectionBroker.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                Parameter param = new Parameter();

                param.name = rs.getString(1);
                param.type = rs.getString(2);
                param.def_id = rs.getInt(3);
                result.add(param);
            }

            rs.close();
            stmt.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace(System.err);
            throw new SystemException(-1, sqle.getMessage());
        } finally {
            if (con != null) {
                connectionBroker.freeConnection(con);
                // System.err.println("Closed database connection");
            }
        }

        return result;
    }

    /**
     * Return all parameter definitions in the database.
     */
    public Vector allAuthorisationForUser(Access access, String user) throws SystemException {

        Vector result = new Vector();

        String query = "SELECT group_id FROM group_authorisation where user_id= ?";
        Connection con = null;

        try {
            con = connectionBroker.getConnection();
            int userId = getUserId(con, user);

            PreparedStatement stmt = con.prepareStatement(query);

            stmt.setInt(1, userId);

            // System.err.println("query: " + stmt.toString());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String groupId = rs.getString(1);

                result.add(groupId);
            }

            rs.close();
            stmt.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace(System.err);
            throw new SystemException(-1, sqle.getMessage());
        } finally {
            if (con != null) {
                connectionBroker.freeConnection(con);
                // System.err.println("Closed database connection");
            }
        }

        return result;
    }

    /**
     * Return all parameter definitions in the database.
     */
    public Vector allParametersForUser(Access access, String user) throws SystemException {

        Vector result = new Vector();

        String query = "SELECT parameter_id, value, id, condition FROM parameters where user_id= ?";
        Connection con = null;

        try {
            con = connectionBroker.getConnection();
            int userId = getUserId(con, user);

            PreparedStatement stmt = con.prepareStatement(query);

            stmt.setInt(1, userId);

            // System.err.println("query: " + stmt.toString());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Parameter param = new Parameter();

                param.def_id = rs.getInt(1);
                param.value = rs.getString(2);
                param.id = rs.getInt(3);
                param.condition = rs.getString(4);
                result.add(param);
            }

            rs.close();
            stmt.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace(System.err);
            throw new SystemException(-1, sqle.getMessage());
        } finally {
            if (con != null) {
                connectionBroker.freeConnection(con);
                // System.err.println("Closed database connection");
            }
        }

        return result;
    }

    public Vector allConditionsForUser(Access access, String user) throws SystemException {

        Vector result = new Vector();
        Connection con = null;
        String query = "SELECT id, service_id, user_id, condition, comment FROM conditions WHERE user_id = ?";

        try {
            con = connectionBroker.getConnection();
            int userId = getUserId(con, user);

            PreparedStatement stmt = con.prepareStatement(query);

            stmt.setInt(1, userId);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ConditionData cnd = new ConditionData();

                cnd.id = rs.getInt(1);
                cnd.serviceId = rs.getInt(2);
                cnd.userId = rs.getInt(3);
                cnd.condition = rs.getString(4);
                cnd.comment = rs.getString(5);
                result.add(cnd);
            }

            stmt.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace(System.err);
            throw new SystemException(-1, sqle.getMessage());
        } finally {
            if (con != null) {
                connectionBroker.freeConnection(con);
                // System.err.println("Closed database connection");
            }
        }

        return result;
    }

    private String getLengthQuery() {
        if (this.currentDBMS == DBMS_MYSQL)
            return "LENGTH";
        else if (this.currentDBMS == DBMS_MSSQL)
            return "LEN";
        else
            return "LENGTH";
    }

    public void addDispatch(Access access, String service, String servlet) throws SystemException {

        String query = "INSERT INTO dispatch VALUES ('" + service + "', '"
                + servlet + "')";

        Util.debugLog(query);

        Connection con = connectionBroker.getConnection();

        try {
            Statement stmt = con.createStatement();

            stmt.executeUpdate(query);
            stmt.close();
        } catch (Exception e) {// System.err.println(e.getMessage());
        } finally {
            connectionBroker.freeConnection(con);
        }
    }

    public String getServlet(Access access) throws SystemException {

        String query = "SELECT servlet FROM service_group WHERE id = ?";
        String query2 = "SELECT group_id FROM services WHERE name = ?";
        String result = "";
        Connection con = null;

        try {

            con = connectionBroker.getConnection();

            PreparedStatement stmt = con.prepareStatement(query2);

            stmt.setString(1, access.rpcName);
            // System.err.println("stmt: " +stmt.toString());
            ResultSet rs = stmt.executeQuery();
            int groupId = -1;

            while (rs.next()) {
                groupId = rs.getInt(1);
            }
            if (rs != null)
                rs.close();
            stmt.close();

            // System.err.println("groupId: " +Integer.toString(groupId));

            stmt = con.prepareStatement(query);
            stmt.setInt(1, groupId);
            // System.err.println("stmt: " +stmt.toString());
            rs = stmt.executeQuery();

            if (rs.next()) {
                result = rs.getString("servlet");
                rs.close();
            } else {
                // System.err.println("Servlet not found for service: " + service);
                throw new SystemException(-1, "Servlet not found for service: " + access.rpcName);
            }
            stmt.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace(System.err);
            throw new SystemException(-1, sqle.getMessage());
        } finally {
            if (con != null) {
                connectionBroker.freeConnection(con);
                // System.err.println("Closed database connection");
            }
        }
        return result;
    }

    /**
     * Get parameter values.
     */
    public Parameter[] getParameters(Access access) throws SystemException {

        String query = "SELECT d.name, p.value, d.type, p.condition FROM definitions d, parameters p "
                + "WHERE p.user_id = ? AND d.parameter_id=p.parameter_id";

        ArrayList parms = new ArrayList();
        Connection con = null;

        try {
            con = connectionBroker.getConnection();

            PreparedStatement stmt = con.prepareStatement(query);

            stmt.setInt(1, access.userID);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String name = "";
                String value = "";
                String type = "";
                String condition = "";

                name = rs.getString(1);
                value = rs.getString(2);
                type = rs.getString(3);
                condition = rs.getString(4);
                Parameter p = new Parameter();

                p.name = name;
                p.value = value;
                p.type = type;
                p.condition = condition;
                parms.add(p);
                // Util.debugLog(2, "Parameters: ("+name+", " + value + ", " + type + ", " + condition + ")");
                // parms.store(name, value, type, condition, doc);
            }

            stmt.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace(System.err);
            throw new SystemException(-1, sqle.getMessage());
        } finally {
            if (con != null) {
                connectionBroker.freeConnection(con);
                // System.err.println("Closed database connection");
            }
        }

        Parameter[] pa = new Parameter[parms.size()];

        pa = (Parameter[]) parms.toArray(pa);

        return pa;
    }

    public Parameter showParameter(Access access, int id) throws SystemException, UserException {
        String query = "SELECT p.id, d.name, p.value, d.type, p.condition, p.user_id FROM definitions d, parameters p "
                + "WHERE p.id = ? AND d.parameter_id=p.parameter_id";
        Connection con = null;
        Parameter parm = null;

        try {
            // First check permissions.
            boolean all = true;// checkPermissions(access, "user_view");

            con = connectionBroker.getConnection();

            PreparedStatement stmt = con.prepareStatement(query);

            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            int userId = -1;

            if (rs.next()) {
                parm = new Parameter();
                parm.id = rs.getInt(1);
                parm.name = rs.getString(2);
                parm.value = rs.getString(3);
                parm.type = rs.getString(4);
                parm.condition = rs.getString(5);
                userId = rs.getInt(6);
            }

            stmt.close();
            connectionBroker.freeConnection(con);

            if (!all && (userId != access.userID)) {
                throw new UserException(UserException.OPERATION_NOT_PERMITTED, "");
            }

        } catch (SQLException sqle) {
            sqle.printStackTrace(System.err);
            throw new SystemException(-1, sqle.getMessage());
        } finally {
            if (con != null) {
                connectionBroker.freeConnection(con);
                // System.err.println("Closed database connection");
            }
        }
        return parm;
    }

    public Parameter showParameter(Access access, String user) throws SystemException, UserException {
        String query = "SELECT p.id, d.name, p.value, d.type, p.condition, p.user_id FROM definitions d, parameters p "
                + "WHERE p.id = ? AND d.parameter_id=p.parameter_id";
        Connection con = null;
        Parameter parm = null;

        try {
            // First check permissions.
            boolean all = true;// checkPermissions(access, "user_view");

            con = connectionBroker.getConnection();
            int id = getUserId(con, user);

            PreparedStatement stmt = con.prepareStatement(query);

            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            int userId = -1;

            if (rs.next()) {
                parm = new Parameter();
                parm.id = rs.getInt(1);
                parm.name = rs.getString(2);
                parm.value = rs.getString(3);
                parm.type = rs.getString(4);
                parm.condition = rs.getString(5);
                userId = rs.getInt(6);
            }

            stmt.close();
            connectionBroker.freeConnection(con);

            if (!all && (userId != access.userID)) {
                throw new UserException(UserException.OPERATION_NOT_PERMITTED, "");
            }

        } catch (SQLException sqle) {
            sqle.printStackTrace(System.err);
            throw new SystemException(-1, sqle.getMessage());
        } finally {
            if (con != null) {
                connectionBroker.freeConnection(con);
                // System.err.println("Closed database connection");
            }
        }
        return parm;
    }

    public Vector showParameters(Access access, String user) throws SystemException {

        Vector result = new Vector();

        String query = "SELECT p.id, d.name, p.value, d.type, p.condition FROM definitions d, parameters p "
                + "WHERE p.user_id = ? AND d.parameter_id=p.parameter_id";

        // String query = "SELECT p.id, d.name, p.value, d.type, p.condition FROM definitions d, parameters p " +
        // "WHERE p.user_id = "+ userId +" AND d.parameter_id=p.parameter_id";

        Connection con = null;

        try {
            con = connectionBroker.getConnection();
            int userId = getUserId(con, user);
            PreparedStatement stmt = con.prepareStatement(query);

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String name = "";
                String value = "";
                String type = "";
                String condition = "";

                int id = rs.getInt(1);

                name = rs.getString(2);
                value = rs.getString(3);
                type = rs.getString(4);
                condition = rs.getString(5);

                Parameter parm = new Parameter();

                parm.id = id;
                parm.name = name;
                parm.condition = condition;
                parm.value = value;
                parm.type = type;

                // System.err.println("id "+id);
                // System.err.println("name "+name);
                // System.err.println("condition "+condition);
                // System.err.println("value "+value);
                // System.err.println("type "+type);

                result.add(parm);
            }

            rs.close();
            stmt.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace(System.err);
            throw new SystemException(-1, sqle.getMessage());
        } finally {
            if (con != null) {
                connectionBroker.freeConnection(con);
                // System.err.println("Closed database connection");
            }
        }

        return result;
    }

    private boolean checkPermissions(Access access, String permission) throws SystemException {

        boolean result = false;
        String query =
                "SELECT p.id FROM permissions p, permission_defs d WHERE p.permission_id=d.id AND d.name = ? AND p.user_id = ?";

        Connection con = null;

        try {
            con = connectionBroker.getConnection();

            PreparedStatement stmt = con.prepareStatement(query);
            int userId = getUserId(con, access.rpcUser);

            stmt.setString(1, permission);
            stmt.setInt(2, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next())
                result = true;

            stmt.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace(System.err);
            throw new SystemException(-1, sqle.getMessage());
        } finally {
            if (con != null) {
                connectionBroker.freeConnection(con);
                // System.err.println("Closed database connection");
            }
        }

        return result;
    }

    public static void main(String args[]) {
        Connection c = null;

        try {
            // Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            // c = DriverManager.getConnection("jdbc:odbc:authorisation", "", "");
            // Util.debugLog("Got connection");



            // DbConnectionBroker myBroker = new DbConnectionBroker("sun.jdbc.odbc.JdbcOdbcDriver",
            // "jdbc:odbc:authorisation",
            // "", "",
            // 2, 25, "/tmp/log.db", 0.1);

            DbConnectionBroker myBroker = new DbConnectionBroker("org.gjt.mm.mysql.Driver",
                    "jdbc:mysql://localhost/authorisation",
                    "", "",
                    2, 25, "/tmp/log.db", 0.1);

            Authorisation auth = new Authorisation(Authorisation.DBMS_MYSQL, myBroker);

            Util.debugLog("Created pool:");

            // Access access = new Access(78, 1, 2, "ARJEN", "AAP", "", "192.65.323.1", "piupohost", myBroker, Authorisation.DBMS_MSSQL);
            Access access = new Access(78, 1, 2, "ARJEN", "AAP", "", "localhost", "piupohost");

            Util.debugLog("Access = " + access);
            auth.logAction(access, 3, "Geen commentaar");
            Util.debugLog("created logaction");

            Vector a = auth.allServices(access);

            for (int i = 0; i < a.size(); i++) {// System.err.println("service: "+(String)a.get(i));
            }

        } catch (Exception e) {
            Util.debugLog(e + "");
        }

        Util.debugLog("Leaving program");

    }

    /**
     * Return all users in the database.
     */
    public Vector allUserIds(Access access) throws SystemException {

        Vector result = new Vector();
        boolean all = false;
        Connection con = null;

        try {
            // Check if user has correct permissions.
            all = true;// checkPermissions(access, "user_view");

            String query = "SELECT id FROM users";

            con = connectionBroker.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                String userId = rs.getString(1);

                if (!all && userId.equals(access.rpcUser))
                    result.add(userId);
                else if (all)
                    result.add(userId);
            }

            rs.close();
            stmt.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace(System.err);
            throw new SystemException(-1, sqle.getMessage());
        } finally {
            if (con != null) {
                connectionBroker.freeConnection(con);
                // System.err.println("Closed database connection");
            }
        }

        return result;
    }

    /**
     * Return all servicesId in the database.
     */
    public Vector allServiceIds(Access access) throws SystemException {

        Vector result = new Vector();

        String query = "SELECT id FROM services";
        Connection con = null;

        try {
            con = connectionBroker.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                String serviceId = rs.getString(1);

                result.add(serviceId);
            }

            rs.close();
            stmt.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace(System.err);
            throw new SystemException(-1, sqle.getMessage());
        } finally {
            if (con != null) {
                connectionBroker.freeConnection(con);
                // System.err.println("Closed database connection");
            }
        }

        return result;
    }

    /**
     * Return a simple select from the database.
     * expecting only 1 result
     */
    public String simpleSelect(Access access, String target, String table, String keyName, String key) throws SystemException {

        String result = "";

        String query = "SELECT " + target + " FROM " + table + " where "
                + keyName + " = '" + key + "'";

        // System.err.println("query = " + query);

        Connection con = null;

        try {
            con = connectionBroker.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                result = rs.getString(1);
            }

            rs.close();
            stmt.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace(System.err);
            throw new SystemException(-1, sqle.getMessage());
        } finally {
            if (con != null) {
                connectionBroker.freeConnection(con);
                // System.err.println("Closed database connection");
            }
        }

        return result;
    }

    public Vector[] select(Access access, String query, int aantalResult) throws SystemException {
        Vector[] result = new Vector[aantalResult];

        for (int i = 0; i < result.length; i++) {
            result[i] = new Vector();
        }

        Connection con = null;

        // System.err.println("query = " + query);

        try {
            con = connectionBroker.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                for (int i = 1; i <= aantalResult; i++) {

                    String s = rs.getString(i);

                    result[i - 1].add(s);
                }
            }

            rs.close();
            stmt.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace(System.err);
            throw new SystemException(-1, sqle.getMessage());
        } finally {
            if (con != null) {
                connectionBroker.freeConnection(con);
                // System.err.println("Closed database connection");
            }
        }

        return result;

    }

    /**
     * Return a select from the database.
     */
    public Vector[] select(Access access, String[] targets, String table, String[] whereColumNames, String[] whereColumValues) throws SystemException {
        int aantalSelect = targets.length;

        int aantalWhere = 0;

        if (whereColumNames != null) {
            aantalWhere = whereColumNames.length;
        }
        String select = "";

        String where = "";

        Vector[] result = new Vector[aantalSelect];

        for (int i = 0; i < result.length; i++) {
            result[i] = new Vector();
        }

        for (int i = 0; i < aantalSelect; i++) {
            select = select + targets[i];
            if (i < (aantalSelect - 1)) {
                select = select + " , ";
            }
        }

        if (whereColumNames != null) {
            int aantalWhereNames = whereColumNames.length;

            for (int i = 0; i < aantalWhereNames; i++) {
                where = where + whereColumNames[i] + " = " + "'"
                        + whereColumValues[i] + "'";
                if (i < (aantalWhere - 1)) {
                    where = where + " and ";
                }
            }
        }

        String query = "";

        if (where.equals("")) {
            query = "SELECT " + select + " FROM " + table;
        } else {
            query = "SELECT " + select + " FROM " + table + " where " + where;
        }
        Connection con = null;

        // System.err.println("query = " + query);

        try {
            con = connectionBroker.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                for (int i = 1; i <= aantalSelect; i++) {

                    String s = rs.getString(i);

                    result[i - 1].add(s);
                }
            }

            rs.close();
            stmt.close();
        } catch (SQLException sqle) {
            sqle.printStackTrace(System.err);
            throw new SystemException(-1, sqle.getMessage());
        } finally {
            if (con != null) {
                connectionBroker.freeConnection(con);
                // System.err.println("Closed database connection");
            }
        }

        return result;
    }

    public void update(Access access, String table, String[] setColumNames, String[] setColumValues, String[] whereColumNames, String[] whereColumValues) throws SystemException, UserException {
        Connection con = null;
        int aantalSetNames = setColumNames.length;
        int aantalSetValues = setColumValues.length;
        int aantalWhereNames = whereColumNames.length;
        int aantalWhereValues = whereColumValues.length;

        // First check permissions.
        /*
         if (!checkPermissions(access, "user_view")) {
         // rpcUser is not allowed for this operation.
         throw new UserException(UserException.OPERATION_NOT_PERMITTED, "");
         }
         */
        if (aantalSetNames != aantalSetValues) {// System.err.println("\n\n\n !!!WARRING!!! for SET: aantal columNames != aantal columValues \n\n\n");
        }
        if (aantalWhereNames != aantalWhereValues) {// System.err.println("\n\n\n !!!WARRING!!! for WHERE: aantal columNames != aantal columValues \n\n\n");
        }

        // boolean succeed = true;

        String set = "";
        String where = "";

        for (int i = 0; i < aantalSetNames; i++) {
            // set=set+setColumNames[i]+"="+"'"+setColumValues[i]+"'";
            set = set + setColumNames[i] + "=?";
            // Util.debugLog("Set: " + setColumNames[i] + " = " + setColumValues[i]);
            if (i < (aantalSetNames - 1)) {
                set = set + ",";
            }
        }

        for (int i = 0; i < aantalWhereNames; i++) {
            // where=where+whereColumNames[i]+"="+"'"+whereColumNames[i]+"'";
            where = where + whereColumNames[i] + "=?";
            // Util.debugLog("Where" + whereColumNames[i] + " = " + whereColumNames[i]);
            if (i < (aantalWhereNames - 1)) {
                where = where + " and ";
            }
        }

        String query = "update " + table + " set " + set + " where " + where;

        // System.err.println("query = " + query);

        try {
            con = connectionBroker.getConnection();

            // Statement stmt = con.createStatement();
            PreparedStatement stmt = con.prepareStatement(query);

            for (int i = 0; i < aantalSetNames; i++) {
                Util.debugLog(">>>>>>>>>>>>>>>>>>>>>SET " + setColumValues[i]);
                stmt.setString(i + 1, setColumValues[i]);
            }
            for (int i = 0; i < aantalWhereNames; i++) {
                Util.debugLog(">>>>>>>>>>>>>>>>>>>>>WHERE " + whereColumValues[i]);
                stmt.setString(i + 1 + aantalSetNames, whereColumValues[i]);
            }
            // System.err.println("DEBUG " + stmt.toString());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException sqle) {
            // succeed=false;
            sqle.printStackTrace(System.err);
            throw new SystemException(-1, sqle.getMessage());
        } finally {
            if (con != null) {
                connectionBroker.freeConnection(con);
                // System.err.println("Closed database connection");
            }
        }

        // return succeed;
    }

}
