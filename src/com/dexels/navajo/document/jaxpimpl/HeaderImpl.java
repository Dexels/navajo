package com.dexels.navajo.document.jaxpimpl;

/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version $Id$
 */

import java.util.*;

import com.dexels.navajo.document.*;
import org.w3c.dom.*;
import com.dexels.navajo.document.jaxpimpl.xml.XMLutils;
import com.dexels.navajo.document.jaxpimpl.xml.XMLDocumentUtils;
import javax.xml.transform.stream.StreamResult;

public final class HeaderImpl implements Header {

  // I think ref should be final
  private Element ref;
//  private Map attributeMap = null;

  
  public HeaderImpl(Element ref) {
    this.ref = ref;
  }

  public void addLazyMessagePath(String path, int startIndex, int endIndex, int total) {
    throw new UnsupportedOperationException("Method addLazyMessage not implemented in jaxpimpl");
  }

  public final Object getRef() {
    return ref;
  }

       /**
     * Get the name of the user_agent from a Navajo message.
     */
    public final String getUserAgent() {

        String value = "";
        Element n = (Element)
                XMLutils.findNode(ref, "http");
        if (n != null)
            value = n.getAttribute("user_agent");

        return value;
    }

    /**
     * Get the ip address from a Navajo message.
     */
    public final String getIPAddress() {

        String value = "";

        Element n = (Element)
                XMLutils.findNode(ref, "client");

        if (n != null)
            value = n.getAttribute("address");

        return value;
    }

    /**
     * Set the IP address of a navajo request.
     *
     * @param message
     */
    public final void setRequestData(String ipAddress, String host) {
      Element client = (Element)
                XMLutils.findNode(ref, "client");
      if (client == null) {
        Element header = (Element) XMLutils.findNode(ref, "header");
        client = ref.getOwnerDocument().createElement("client");
        header.appendChild(client);
      }
      client.setAttribute("address", ipAddress);
      client.setAttribute("host", host);
    }

    
    public void setAttribute(String key, String value) {
    	ref.setAttribute(key,value);
    }

    public String getAttribute(String key) {
    	return ref.getAttribute(key);
    }

    /**
     * Get the hostname from a Navajo message.
     */
    public final String getHostName() {

        String value = "";

        Element n = (Element)
                XMLutils.findNode(ref, "client");

        if (n != null)
            value = n.getAttribute("host");

        return value;
    }

    /**
     * Get the expiration interval.
     */
    public final long getExpirationInterval() {
        String s = "";
        Element n = (Element)
                XMLutils.findNode(ref, "transaction");

        s = n.getAttribute("expiration_interval");
        if ((s == null) || (s.equals("")))
            return -1;
        return Long.parseLong(s);
    }

    public void setExpirationInterval(long l) {
      Element n = (Element)
               XMLutils.findNode(ref, "transaction");

       n.setAttribute("expiration_interval", l+"");

    }
    /**
     * Get the defined lazy messages from the control tag.
     *
     * <transaction rcp_usr="" rpc_pwd="" rpc_name="">
     *   <lazymessage name="/MemberData" startindex="10" endindex="100"/>
     * </transaction>
     * @param message
     * @return
     */
    public final com.dexels.navajo.document.LazyMessageImpl getLazyMessages() {

        com.dexels.navajo.document.LazyMessageImpl lm =
                        new com.dexels.navajo.document.LazyMessageImpl();
        Element n = (Element)
                XMLutils.findNode(ref, "transaction");
        NodeList list = n.getChildNodes();
        for (int i = 0; i < list.getLength(); i++) {
            Node child = list.item(i);
            if (child.getNodeName().equals("lazymessage")) {
              Element e = (Element) child;
              String name = e.getAttribute("name");
              String si = e.getAttribute("startindex");
              String ei = e.getAttribute("endindex");
              String te = e.getAttribute("lazy_total");
              boolean valid = true;

              int startIndex = 0;
              try {
                startIndex = Integer.parseInt(si);
              } catch (Exception ex) {
                valid = false;
              }

              int endIndex = 0;
              try {
                 endIndex = Integer.parseInt(ei);
              } catch (Exception ex) {
                 valid = false;
              }

              int totalElements = 0;
              try {
                totalElements = Integer.parseInt(te);
              }
              catch (Exception ex) {
                valid = false;
              }

              if (valid) {
                  lm.addLazyMessage(name, startIndex, endIndex, totalElements);
              }
            }
        }
        return lm;
    }

    /**
     * Get the name of the service (RPC name) from a Navajo message.
     */
    public final String getRPCName() {

        String rpcName = "";
        Element n = (Element)
                XMLutils.findNode(ref, "transaction");

        rpcName = n.getAttribute("rpc_name");

        return rpcName;
    }

     /**
     * Get the name of the user (RPC user) from a Navajo message.
     */
    public final String getRPCUser() {

        String rpcUser = "";

        Element n = (Element)
                XMLutils.findNode(ref, "transaction");

        rpcUser = n.getAttribute("rpc_usr");

        return rpcUser;
    }

    /**
     * Get the password of the user (RPC password) from a Navajo message.
     */
    public final String getRPCPassword() {

        String rpcPwd = "";

        Element n = (Element)
                XMLutils.findNode(ref, "transaction");

        rpcPwd = n.getAttribute("rpc_pwd");

        return rpcPwd;
    }

    public final void setRPCPassword(String s) {
        Element n = (Element)
                XMLutils.findNode(ref, "transaction");

        n.setAttribute("rpc_pwd", s);

    }

    /**
     * Set the Navajo service name in the header.
     *
     * @param s
     */
    public final void setRPCName(String s) {
        Element n = (Element)
                XMLutils.findNode(ref, "transaction");

        n.setAttribute("rpc_name", s);
    }

    /**
     * Sets the Navajo username in the header.
     * @param s
     */
    public final void setRPCUser(String s) {
        Element n = (Element)
                XMLutils.findNode(ref, "transaction");

        n.setAttribute("rpc_usr", s);
    }

    /**
     *
     * @param name
     * @param pointer
     * @param percReady
     * @param isFinished
     */
    public final void setCallBack(String name, String pointer, int percReady, boolean isFinished, String interrupt) {

      Element n = (Element)
                XMLutils.findNode(ref, "callback");
      if (n == null) {
        n = ref.getOwnerDocument().createElement("callback");
        ref.appendChild(n);
      }
      Element object = (Element) XMLutils.findNodeWithAttributeValue(ref.getOwnerDocument(), "object", "name", name);
      if (object == null) {
        object = ref.getOwnerDocument().createElement("object");
        n.appendChild(object);
        object.setAttribute("name", name);
        object.setAttribute("ref", pointer);
      }
      object.setAttribute("finished", (isFinished ? "true" : "false"));
      if (isFinished)
        percReady = 100;
      object.setAttribute("perc_ready", percReady+"");
      object.setAttribute("interrupt", interrupt);
    }


    public final void setCallBackInterrupt(String interrupt) {
      throw new UnsupportedOperationException("setInterrupt not (yet) implemented in jaxpimpl");
    }

    /**
     *
     * @param name
     * @return
     */
    public final String getCallBackPointer(String name) {
      Element n = (Element) XMLutils.findNode(ref, "callback");
      if (n == null)
        return null;
      Element object = (Element) XMLutils.findNodeWithAttributeValue(ref.getOwnerDocument(), "object", "name", name);
      if (object == null)
        return null;
      else
        return object.getAttribute("ref");
    }

    public void removeCallBackPointers() {
      Element n = (Element) XMLutils.findNode(ref, "callback");
      Node p = n.getParentNode();
      p.removeChild(n);
    }

    public final void write(java.io.Writer writer) {
      try {
        XMLDocumentUtils.toXML(this.ref, "", "", "", new StreamResult(writer));
      }
      catch (NavajoException ex) {
        ex.printStackTrace(System.err);
      }
    }

    public final void write(java.io.OutputStream stream) {
      try {
        XMLDocumentUtils.toXML(this.ref, "", "", "", new StreamResult(stream));
      }
      catch (NavajoException ex) {
        ex.printStackTrace(System.err);
      }
    }

    public final String getCallBackInterupt(String name) {
       Element n = (Element)
                XMLutils.findNode(ref, "callback");
      if (n == null)
        return null;
      Element object = (Element) XMLutils.findNodeWithAttributeValue(ref.getOwnerDocument(), "object", "name", name);
      if (object == null)
        return null;
      else {
        String interrupt = object.getAttribute("interrupt");
        if (interrupt != null)
          return interrupt;
        else
          return "";
      }
    }

    public LazyMessagePath getLazyMessagePath(String path) {
      throw new UnsupportedOperationException("Sorry, getLazyMessagePath is not supported in JaxpImpl!");
    }

    public int getCallBackProgress() {
      return -1;
    }

    /**
     * Returns whether the asynchronous server process has completed
     */
    public boolean isCallBackFinished() {
      return false;
    }

	public String getRequestId() {
		Element transaction = (Element) XMLutils.findNode(ref, "transaction");
		return transaction.getAttribute("requestid");
	}

	public static void main (String [] args) throws Exception {
		Navajo n = NavajoFactoryImpl.getInstance().createNavajo();
		Header h = NavajoFactoryImpl.getInstance().createHeader(n , "aap", "noot", "mies", -1 );
		n.addHeader(h);
		n.write(System.err);
	}

	public void setRequestId(String id) {
		Element transaction = (Element) XMLutils.findNode(ref, "transaction");
		transaction.setAttribute("requestid", id);
	}

}
