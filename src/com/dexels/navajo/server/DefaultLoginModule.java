package com.dexels.navajo.server;


import java.util.*;
import java.io.IOException;
import javax.security.auth.*;
import javax.security.auth.callback.*;
import javax.security.auth.login.*;
import javax.security.auth.spi.*;
import java.sql.*;


/**
 * <p> This LoginModule authenticates users with a password.
 *
 * <p> This LoginModule only recognizes one user:	testUser
 * <p> testUser's password is:	testPassword
 *
 * <p> If testUser successfully authenticates itself,
 * a <code>JaasPrincipal</code> with the testUser's username
 * is added to the Subject.
 *
 * <p> This LoginModule recognizes the debug option.
 * If set to true in the login Configuration,
 * debug messages will be output to the output stream, System.out.
 *
 * @version 1.18, 01/11/00
 */
public class DefaultLoginModule implements LoginModule {
    // initial state
    private Subject subject;
    private CallbackHandler callbackHandler;
    private Map sharedState;
    private Map options;

    // configurable option
    private boolean debug = false;

    // the authentication status
    private boolean succeeded = false;
    private boolean commitSucceeded = false;

    // username and password
    private String username;
    private char[] password;
    private int userID;
    private int serviceID;
    private String serviceName;

    private NavajoPrincipal userPrincipal;
    private Access access;

    // DB connection
    private Connection con = null;

    private int isAuthorized(Connection con, String service, int userID)
            throws SQLException {

        String query = "SELECT s.id FROM services s, group_authorisation a WHERE "
                + "s.name = ? AND s.group_id = a.group_id AND a.user_id = ?";

        PreparedStatement stmt = con.prepareStatement(query);

        stmt.setString(1, service);
        stmt.setInt(2, userID);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            int id = rs.getInt(1);

            return id;
        } else
            return -1;
    }

    /**
     * Return user ID or -1 if not authenticated.
     */
    private int isAuthenticated(Connection con, String user, String password) throws SQLException {
        String query = "SELECT id, name, password FROM users WHERE name = ?";

        PreparedStatement stmt = con.prepareStatement(query);

        stmt.setString(1, user);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            int id = rs.getInt(1);
            String usr = rs.getString(2);
            String pwd = rs.getString(3);

            rs.close();
            stmt.close();
            if (pwd.equalsIgnoreCase(password)) {
                return id;
            } else
                return -1;
        } else
            return -1;
    }

    public void initialize(Subject subject, CallbackHandler callbackHandler, Map sharedState, Map options) {

        this.subject = subject;
        this.callbackHandler = callbackHandler;
        this.sharedState = sharedState;
        this.options = options;

        // initialize any configured options
        debug = "true".equalsIgnoreCase((String) options.get("debug"));
    }

    public boolean login() throws LoginException {

        // prompt for a username and password
        if (callbackHandler == null)
            throw new LoginException("Error: no CallbackHandler available " + "to garner authentication information from the user");

        NavajoCallBackHandler navajoCallBackHandler = (NavajoCallBackHandler) callbackHandler;

        con = navajoCallBackHandler.getDBBroker().getConnection();

        Callback[] callbacks = new Callback[3];

        callbacks[0] = new NameCallback("username:");
        callbacks[1] = new PasswordCallback("password:", false);
        callbacks[2] = new ServiceCallback("service:");

        try {
            callbackHandler.handle(callbacks);

            username = ((NameCallback) callbacks[0]).getName();
            char[] tmpPassword = ((PasswordCallback) callbacks[1]).getPassword();
            StringBuffer sPassword = new StringBuffer(password.length);

            for (int i = 0; i < password.length; i++) {
                sPassword.append(password[i]);
            }

            if (tmpPassword == null) {
                // treat a NULL password as an empty password
                tmpPassword = new char[0];
            }
            password = new char[tmpPassword.length];
            System.arraycopy(tmpPassword, 0, password, 0, tmpPassword.length);
            ((PasswordCallback) callbacks[1]).clearPassword();

            ServiceCallback sc = (ServiceCallback) callbacks[2];

            serviceName = sc.getService();
            userID = isAuthenticated(con, username, sPassword.toString());
            if (userID != -1)
                serviceID = isAuthorized(con, serviceName, userID);

            if ((serviceID == -1) || (userID == -1)) {
                username = null;
                for (int i = 0; i < password.length; i++)
                    password[i] = ' ';
                password = null;
                throw new FailedLoginException("Authentication failed");
            }
        } catch (java.io.IOException ioe) {
            throw new LoginException(ioe.toString());
        } catch (UnsupportedCallbackException uce) {
            throw new LoginException("Error: " + uce.getCallback().toString() + " not available to garner authentication information " + "from the user");
        } catch (java.sql.SQLException sqle) {
            throw new LoginException(sqle.toString());
        } finally {
            navajoCallBackHandler.getDBBroker().freeConnection(con);
        }
        return true;
    }

    public boolean commit() throws LoginException {
        if (succeeded == false) {
            return false;
        } else {
            userPrincipal = new NavajoPrincipal(username);
            userPrincipal.setServiceID(serviceID);
            userPrincipal.setUserID(userID);
            userPrincipal.setServiceName(serviceName);
            if (!subject.getPrincipals().contains(userPrincipal))
                subject.getPrincipals().add(userPrincipal);

            if (debug) {
                System.out.println("\t\t[DefaultLoginModule] " + "added NavajoPrincipal to Subject");
            }
            username = null;
            for (int i = 0; i < password.length; i++)
                password[i] = ' ';
            password = null;

            commitSucceeded = true;
            return true;
        }
    }

    public boolean abort() throws LoginException {
        if (succeeded == false) {
            return false;
        } else if (succeeded == true && commitSucceeded == false) {
            succeeded = false;
            username = null;
            if (password != null) {
                for (int i = 0; i < password.length; i++)
                    password[i] = ' ';
                password = null;
            }
            userPrincipal = null;
        } else {
            logout();
        }
        return true;
    }

    public boolean logout() throws LoginException {

        subject.getPrincipals().remove(userPrincipal);
        succeeded = false;
        succeeded = commitSucceeded;
        username = null;
        if (password != null) {
            for (int i = 0; i < password.length; i++)
                password[i] = ' ';
            password = null;
        }
        userPrincipal = null;
        return true;
    }

}
