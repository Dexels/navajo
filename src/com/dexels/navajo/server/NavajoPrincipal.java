package com.dexels.navajo.server;


import java.security.Principal;


/**
 * <p> This class implements the <code>Principal</code> interface
 * and represents a user.
 *
 * <p> Principals such as this <code>NavajoPrincipal</code>
 * may be associated with a particular <code>Subject</code>
 * to augment that <code>Subject</code> with an additional
 * identity.  Refer to the <code>Subject</code> class for more information
 * on how to achieve this.  Authorization decisions can then be based upon
 * the Principals associated with a <code>Subject</code>.
 *
 * @version 1.4, 01/11/00
 * @see java.security.Principal
 * @see javax.security.auth.Subject
 */
public class NavajoPrincipal implements Principal, java.io.Serializable {

    private String name;
    private int userID;
    private int serviceID;
    private String serviceName;

    public NavajoPrincipal(String name) {
        if (name == null)
            throw new NullPointerException("illegal null input");
        this.name = name;
    }

    public int getUserID() {
        return this.userID;
    }

    public void setUserID(int i) {
        this.userID = i;
    }

    public int getServiceID() {
        return this.serviceID;
    }

    public void setServiceID(int i) {
        this.serviceID = i;
    }

    public String getName() {
        return name;
    }

    public String getServiceName() {
        return this.serviceName;
    }

    public void setServiceName(String s) {
        this.serviceName = s;
    }

    public String toString() {
        return ("NavajoPrincipal:  " + name);
    }

    public boolean equals(Object o) {
        if (o == null)
            return false;

        if (this == o)
            return true;

        if (!(o instanceof NavajoPrincipal))
            return false;
        NavajoPrincipal that = (NavajoPrincipal) o;

        if (this.getName().equals(that.getName()))
            return true;
        return false;
    }

    public int hashCode() {
        return name.hashCode();
    }
}
