package com.dexels.navajo.client.impl;

import java.net.*;
import java.io.*;
import java.util.Map;
import java.security.Permission;
import com.dexels.navajo.client.*;
import com.dexels.navajo.document.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class NavajoHttpUrlConnection extends URLConnection {
  private final BaseNavajoHandler myHandler;
  private final URLConnection myConnection;
  private ClientInterface myInterface = null;
  private final String myMethod;
  public NavajoHttpUrlConnection(BaseNavajoHandler h,URL u,ClientInterface ci,String method) throws IOException {
    super(u);
    myHandler = h;
    myInterface = ci;
    myMethod = method;
    myConnection = u.openConnection();
  }

  public void connect() throws java.io.IOException {

    System.err.println("About to connect...");
    myConnection.connect();
//    super.connect();
  }
  public void disconnect() {
    /**@todo Implement this java.net.HttpURLConnection abstract method*/
  }

  public Navajo doTransaction(Navajo n) throws ClientException {
    return myInterface.doSimpleSend(n,myMethod);
  }

  public void addRequestProperty(String key, String value) {
    myConnection.addRequestProperty(key,value);
  }

  public boolean getAllowUserInteraction() {
    return myConnection.getAllowUserInteraction();
  }

  public Object getContent() throws IOException {
    return myConnection.getContent();
  }

  public Object getContent(Class[] classes) throws IOException {
    return myConnection.getContent(classes);
  }

  public String getContentEncoding() {
    return myConnection.getContentEncoding();
  }

  public int getContentLength() {
    return myConnection.getContentLength();
  }

  public String getContentType() {
    return myConnection.getContentType();
  }

  public long getDate() {
    return myConnection.getDate();
  }

  public boolean getDefaultUseCaches() {
    return URLConnection.getDefaultAllowUserInteraction();
  }

  public boolean getDoInput() {
    return myConnection.getDoInput();
  }

  public boolean getDoOutput() {
    return myConnection.getDoOutput();
  }

  public long getExpiration() {
    return myConnection.getExpiration();
  }

  public String getHeaderField(String name) {
    return myConnection.getHeaderField(name);
  }

  public String getHeaderField(int n) {
    return myConnection.getHeaderField(n);
  }

  public long getHeaderFieldDate(String name, long Default) {
    return myConnection.getHeaderFieldDate(name,Default);
  }

  public int getHeaderFieldInt(String name, int Default) {
    return myConnection.getHeaderFieldInt(name,Default);
  }

  public String getHeaderFieldKey(int n) {
    return myConnection.getHeaderFieldKey(n);
  }

  public Map getHeaderFields() {
    return myConnection.getHeaderFields();
  }

  public long getIfModifiedSince() {
    return myConnection.getIfModifiedSince();
  }

  public InputStream getInputStream() throws IOException {
    return myConnection.getInputStream();
  }

  public long getLastModified() {
    return myConnection.getLastModified();
  }

  public OutputStream getOutputStream() throws IOException {
    return myConnection.getOutputStream();
  }

  public Permission getPermission() throws IOException {
    return myConnection.getPermission();
  }

  public Map getRequestProperties() {
    return myConnection.getRequestProperties();
  }

  public String getRequestProperty(String key) {
    return     myConnection.getRequestProperty(key);
  }

  public URL getURL() {
    return     myConnection.getURL();
  }

  public boolean getUseCaches() {
    return     myConnection.getUseCaches();
  }

  public void setAllowUserInteraction(boolean allowuserinteraction) {
    myConnection.setAllowUserInteraction(allowuserinteraction);
  }

  public void setDefaultUseCaches(boolean defaultusecaches) {
    myConnection.setDefaultUseCaches(defaultusecaches);
  }

  public void setDoInput(boolean doinput) {
    myConnection.setDoInput(doinput);
  }

  public void setDoOutput(boolean dooutput) {
    myConnection.setDoOutput(dooutput);
  }

  public void setIfModifiedSince(long ifmodifiedsince) {
    myConnection.setIfModifiedSince(ifmodifiedsince);
  }

  public void setRequestProperty(String key, String value) {
    myConnection.setRequestProperty(key,value);
  }

  public void setUseCaches(boolean usecaches) {
    myConnection.setUseCaches(useCaches);
  }

}
