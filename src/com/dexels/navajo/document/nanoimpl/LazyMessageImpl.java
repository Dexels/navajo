package com.dexels.navajo.document.nanoimpl;

import java.util.ArrayList;
//import com.dexels.sportlink.client.swing.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.nanoclient.*;
//import com.dexels.navajo.swingclient.components.lazy.*;

import com.dexels.navajo.document.lazy.MessageListener;
import com.dexels.navajo.document.*;
import com.dexels.navajo.document.lazy.*;
import com.dexels.navajo.client.*;

public class LazyMessageImpl
    extends MessageImpl
    implements LazyMessage, Runnable {

  private int total = 0;
  private int shown = -1;
  private int remaining = -1;
  private int loadedMessageCount = -1;

  private int itemsBefore = 0;
  private int itemsAfter = 100;

  private static final int NOT_LOADED = 1;
  private static final int LOADED = 2;
  private static final int TOUCHED = 3;

//  boolean[] loaded = null;

  private boolean running = true;
  private int cycles = 0;
  private int unloadedMessageCount = -1;

  private Message[] loaded = null;
  private int[] touch = null;
  private String myService = null;
  private Navajo myRequestMessage = null;

  // A field to store the submessage data, until it can be parsed.
  private XMLElement subMessageData = null;

  private Thread myMessageThread = null;
  private ArrayList myMessageListeners = new ArrayList();
  private String myResponseMessageName;

  public LazyMessageImpl(Navajo n, String name, int windowSize) {
    super(n, name);
    this.itemsAfter = windowSize;
    myMessageThread = new Thread(this);
  }

  public void setRequest(String service, Navajo m) {
    myService = service;
    myRequestMessage = m;
    parseSubMessageData();
  }

  public void setResponseMessageName(String s) {
    myResponseMessageName = s;
  }

  private void parseSubMessageData() {
    if (subMessageData == null) {
      System.err.println("Can not parse sub message data: Not parsed!");
      return;
    }
//    total = Integer.parseInt((String)getProperty("Total").getValue());
//    shown = Integer.parseInt((String)getProperty("Shown").getValue());
//    remaining = Integer.parseInt((String)getProperty("Remaining").getValue());
    loaded = new Message[total];
    touch = new int[total];
    for (int i = 0; i < touch.length; i++) {
      touch[i] = NOT_LOADED;
    }
    unloadedMessageCount = total;
//    int end = total - remaining;
//    int start = end - shown;
    int end = getEndIndex();
    int start = getStartIndex();
//    int start = end - shown;

    System.err.println("Starting at: " + start);
    System.err.println("Ending at: " + end);

    int messageCount = 0;
    for (int i = 0; i < subMessageData.countChildren(); i++) {

      XMLElement child = (XMLElement) subMessageData.getChildren().elementAt(i);
      String name = child.getName();
//      if (name.equals("property")) {
//        Property p = Navajo.createProperty(myDocRoot,(String)child.getAttribute("name"));
//        this.addProperty(p);
//        p.fromXml(child);
//      }
      if (name.equals("message")) {
        String childName = (String) child.getAttribute("name");
        MessageImpl m = (MessageImpl) NavajoFactory.getInstance().createMessage(
            myDocRoot, childName);
        m.fromXml(child);
//        this.addMessage(m);
        loadMessage(messageCount + start, m);
        messageCount++;
        loadedMessageCount++;
      }
    }
  }

  public void startUpdateThread() {
    myMessageThread.start();
  }

  public void fromXml(XMLElement e) {
//    System.err.println("Loading message with: " + e.toString());
    total = processString( (String) e.getAttribute("lazy_total"));
    shown = processString( (String) e.getAttribute("array_size"));
    remaining = processString( (String) e.getAttribute("lazy_remaining"));
    setStartIndex(processString( (String) e.getAttribute("startindex")));
    setEndIndex(processString( (String) e.getAttribute("endindex")));

    for (int i = 0; i < e.countChildren(); i++) {
      XMLElement child = (XMLElement) e.getChildren().elementAt(i);
      String name = child.getName();
      if (name.equals("property")) {
        /** @todo BEWARE */
        PropertyImpl p = null;
        try {
          p = (PropertyImpl) NavajoFactory.getInstance().createProperty(
              myDocRoot, (String) child.getAttribute("name"), "", "", 0, "", "");
        }
        catch (NavajoException ex) {
          ex.printStackTrace();
        }
        this.addProperty(p);
        p.fromXml(child);
      }
    }
    subMessageData = e;
    loadedMessageCount = 0;
// EXPERIMENTAL
    parseSubMessageData();
  }

  private int processString(String s) {
    if (s == null || "".equals(s)) {
      return -1;
    }
    try {
      return Integer.parseInt(s);
    }
    catch (Exception ex) {
      ex.printStackTrace();
      return -1;
    }

  }

  private void loadMessage(int t, Message m) {
//    System.out.println("loading: "+t);
//    loaded[t] = m;
    setLocalMessage(t, m);
  }

  public int getChildMessageCount() {
    return total;
  }

  public int getStartIndex() {
    return getEndIndex() - getShown();
  }

  public int getEndIndex() {
    return getTotal() - getRemaining();
  }

  public int getShown() {
    return shown;
  }

  public int getRemaining() {
    return remaining;
  }

  public int getTotal() {
    return total;
  }

  public int getLoadedCount() {
    return loadedMessageCount;
  }

  public void merge(LazyMessage lm, int start, int end) {
    System.err.println("Merging from " + start + " to " + end);
    int mergeCount = 0;

    if (lm.getTotal() != getTotal()) {
      System.err.println("Totals differ???! Maybe clear cache?");
    }

    for (int i = start; i < end; i++) {
      if (i >= getTotal()) {
        break;
      }
      Message m = lm.getLocalMessage(i);
      if (m != null) {
        if (getLocalMessage(i) == null) {
          mergeCount++;
        }
        setLocalMessage(i, m);
      }
    }
    remaining -= mergeCount;

  }

  // from zero
  public Message getLocalMessage(int index) {
    return loaded[index];
  }

  // from zero
  public void setLocalMessage(int index, Message m) {

    ( (MessageImpl) m).setMessageMap(getMessageMap());
    loaded[index] = m;
    touch[index] = LOADED;
  }

  // from zero
  public void touch(int index) {
    touch[index] = TOUCHED;
  }

  // from zero
  private Message retrieve(int index) {
    int startIndex = index - itemsBefore;
    int endIndex = index + itemsAfter;
    System.err.println("IN SYNC RETRIEVE, startIndex is " + startIndex + ", endIndex is " + endIndex);
    /** @todo FIX AGAIN */
      try {
        myRequestMessage.getLazyMessagePath(getPath()).setStartIndex(startIndex);
        myRequestMessage.getLazyMessagePath(getPath()).setEndIndex(endIndex);
        myRequestMessage.getLazyMessagePath(getPath()).setTotalRows(total);
        LazyMessage reply = NavajoClientFactory.getClient().doLazySend(
            myRequestMessage, myService, myResponseMessageName, startIndex,
            endIndex, total);
        merge(reply, startIndex, endIndex);
        return getMessage(index);
      }
      catch (ClientException ex) {
        ex.printStackTrace();
        return null;
      }
  }

  // from zero?

  public Message getMessage(int index) {
//    System.err.println("LazyMessageImpl: null? "+myRequestMessage==null);
    if (getLocalMessage(index) == null) {
//      retrieve(index);
      touch(index);
      return getLocalMessage(index);
    }
//    System.err.println("Returning cached message");
    return getLocalMessage(index);
  }

  private Message getSyncMessage(int index) {
    if (getLocalMessage(index) == null) {
      return retrieve(index);
    }
    return getLocalMessage(index);

  }

  public Message getMessage(String name) {
    throw new RuntimeException(
        "getMessage(String) not supported in LazyMessageImpl");
  }

  public ArrayList getMessages(String regexp) {
    throw new RuntimeException(
        "getMessages(String) not supported in LazyMessageImpl");
  }

  public Message addMessage(Message m) {
    throw new RuntimeException("addMessage not supported in LazyMessageImpl");
  }

  public void addMessageListener(MessageListener ml) {
    myMessageListeners.add(ml);
  }

  public void run() {
    while (isRunning() && unloadedMessageCount > 0) {
//      System.err.println("LAZYMESSAGECYCLE:");
//      System.err.println("myThread: "+Thread.currentThread().toString());
//      System.err.println("updatethread: "+myMessageThread.toString());
      try {
//        Thread.currentThread().sleep(500);
//        System.err.println("Unloaded messaged: "+unloadedMessageCount+" Still running. Cycle: "+cycles++);
        int unloadedMessageCount = 0;
        for (int i = 0; i < touch.length; i++) {
          if (!running) {
            System.err.println("LazyMessage thread killed!");
            return;
          }

          if (touch[i] == TOUCHED) {
            synchronized (this) {
              Message m = getSyncMessage(i);
            }
            fireEventToListeners(i - itemsBefore, i + itemsAfter);
            loadedMessageCount += itemsAfter + itemsBefore;
          }
          if (touch[i] != LOADED) {
            unloadedMessageCount++;
          }
//          System.err.println("Message: "+i+" "+touch[i]);
          if (!isRunning()) {
            System.err.println("Lazy message is dying!");
            return;
          }
        }
        this.unloadedMessageCount = unloadedMessageCount;
      }
      catch (Exception ex) {
        ex.printStackTrace();
      }
    }
    System.err.println("All messages loaded. Ending sync thread");
  }

  private void fireEventToListeners(int startIndex, int endIndex) {
    for (int i = 0; i < myMessageListeners.size(); i++) {
      MessageListener current = (MessageListener) myMessageListeners.get(i);
      current.messageLoaded(startIndex, endIndex);
    }

  }

  public synchronized void kill() {
    running = false;
    myMessageThread.interrupt();
    System.err.println("Lazy message thread killed. ");
  }

  public synchronized boolean isRunning() {
    return running;
  }

  public ArrayList getAllMessages() {
    ArrayList al = new ArrayList();
    for (int i = 0; i < getTotal(); i++) {
      Message m = getMessage(i);
      al.add(m);
    }
    return al;
  }

  public XMLElement toXml(XMLElement parent) {
    XMLElement m = super.toXml(parent);
    if (getRemaining() >= 0) {
      m.setAttribute("lazy_total", "" + getTotal());
    }
    if (getRemaining() >= 0) {
      m.setAttribute("lazy_remaining", "" + getRemaining());
    }

    return m;
  }
  public int getArraySize() {
    return getTotal();
  }
  public int getItemsAfter() {
    return itemsAfter;
  }
  public void setItemsAfter(int itemsAfter) {
    this.itemsAfter = itemsAfter;
  }
}
