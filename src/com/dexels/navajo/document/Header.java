package com.dexels.navajo.document;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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

public interface Header extends java.io.Serializable {

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
     *
     * @return
     */
    public void setExpirationInterval(long l);

    /**
     * Get the defined lazy messages from the control tag.
     * @deprecated
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
     * Returns the percentage completed in a asynchronous instance
     */
    public int getCallBackProgress();

    /**
     * Returns whether the asynchronous server process has completed
     */
    public boolean isCallBackFinished();


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

    /**
      * Sets the interrupt.
      * Type of interrupt supplied:
      * - kill (kill and don't show results)
      * - stop (stop and show results thus far)
      * - suspend (suspend and continue after result have been shown)
      *
      * @return
      */
    public void setCallBackInterrupt(String interrupt);

    /**
     * Remove ALL callback pointers.
     */
    public void removeCallBackPointers();

//    /**
//    * Serialize a header as a string to a particular writer.
//    *
//    * @param writer
//    */
//   public void write(java.io.Writer writer);
//
//   /**
//     * Serialize a header as a string to a particular output stream.
//     *
//     * @param stream
//     */
//   public void write(java.io.OutputStream stream);

   /**
    * @deprecated
    * @param path
    * @return
    */
    public LazyMessagePath getLazyMessagePath(String path);

    
    /**
     * @deprecated
     * @param path
     * @param startIndex
     * @param endIndex
     * @param total
     */
    public void addLazyMessagePath(String path, int startIndex, int endIndex, int total);


    /**
     * Adds/sets an attribute for the header. Can be used for a variety of purposes.
     * Adds a key value pair to the header node
     * @param key
     * @param value
     */
    public void setAttribute(String key, String value);

    /**
     * Get an attribute value given a key
     * @param key String
     * @return String
     */
    public String getAttribute(String key);
    
    /**
     * Get the unique request id.
     * 
     * @return
     */
    public String getRequestId();
    
    public Map getAttributes();
    
    public void setRequestId(String id);

	public void addPiggyBackData(Map element);

	public Set getPiggybackData();
	
	public void clearPiggybackData();

	public void write(OutputStream err) throws NavajoException;

}
