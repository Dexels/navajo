package com.dexels.navajo.document;

import com.dexels.navajo.document.jaxpimpl.*;
import com.dexels.navajo.document.lazy.*;

/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version $Id$
 *
 * The Header object specifies general control data regarding a Navajo message.
 *
 */

public interface Header {

     /**
     * Get the name of the user_agent (optional) from a Navajo message.
     */
    public String getUserAgent();

    /**
     * Get the ip address (optional) from a Navajo message.
     */
    public String getIPAddress();

    /**
     * Set the IP address (optional) of a navajo request.
     *
     * @param message
     */
    public void setRequestData(String ipAddress, String host);

    /**
     * Get the hostname (optional) from a Navajo message.
     */
    public String getHostName();

    /**
     * Get the expiration interval. -1 is no caching, value > 0 defines the number of
     * milliseconds that a cached entity is valid.
     *
     */
    public long getExpirationInterval();

    /**
     * Get the defined lazy messages from the control tag.
     *
     * <transaction rcp_usr="" rpc_pwd="" rpc_name="">
     *   <lazymessage name="/MemberData" startindex="10" endindex="100"/>
     * </transaction>
     * @param message
     * @return
     */
    public LazyMessageImpl getLazyMessages();

    /**
     * Get the name of the service (RPC name) from a Navajo message.
     */
    public String getRPCName();

    /**
     * Get the name of the user (RPC user) from a Navajo message.
     */
    public String getRPCUser();

    /**
     * Get the password of the user (RPC password) from a Navajo message.
     */
    public String getRPCPassword();

    /**
     * Get the internal representation of the Header object.
     *
     * @return
     */
    public Object getRef();

    /**
     * Sets the password in the header.
     *
     * @param s
     */
    public void setRPCPassword(String s);

    /**
     * Set the Navajo service name in the header.
     *
     * @param s
     */
    public void setRPCName(String s);

    /**
     * Sets the Navajo username in the header.
     * @param s
     */
    public void setRPCUser(String s);

    /**
     * Set the callback in the header for asynchronous mappable objects.
     * Object names should be unique within the header.
     * If the object name already exists, it is updated with the given values.
     *
     * @param name
     * @param pointer
     * @param isFinished
     */
    public void setCallBack(String name, String pointer, int percReady, boolean isFinished, String interrupt);

    /**
     * Return the callback ref of the object with the given name.
     * @param name
     * @return
     */
    public String getCallBackPointer(String object);

    /**
     * Check whether an interrupt is requested for a callback object.
     * Type of interrupt is returned:
     * - kill (kill and don't show results)
     * - stop (stop and show results thus far)
     * - suspend (suspend and continue after result have been shown)
     *
     * @return
     */
    public String getCallBackInterupt(String object);

}