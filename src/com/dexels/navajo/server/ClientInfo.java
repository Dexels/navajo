package com.dexels.navajo.server;

/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels BV</p>
 * @author Arjen Schoneveld
 * @version 1.0
 */

public class ClientInfo {

  public String ip;
  public String host;
  public int parseTime;
  public String encoding;
  public boolean compressedRecv;
  public boolean compressedSend;
  public int contentLength;

  public ClientInfo(String ip, String host, String encoding, int parseTime, boolean compressedrecv, boolean compressedsend, int contentLength) {
    this.ip = ip;
    this.host = host;
    this.parseTime = parseTime;
    this.encoding = encoding;
    this.compressedRecv = compressedrecv;
    this.compressedSend = compressedsend;
    this.contentLength = contentLength;
  }

  public String getIP() {
    return this.ip;
  }

  public String getHost() {
    return this.host;
  }
  public int getParseTime() {
    return parseTime;
  }
  public String getEncoding() {
    return encoding;
  }
  public boolean isCompressedRecv() {
    return compressedRecv;
  }
  public boolean isCompressedSend() {
    return compressedSend;
  }
  public int getContentLength() {
    return contentLength;
  }

}