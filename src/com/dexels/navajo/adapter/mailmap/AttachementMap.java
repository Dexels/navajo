package com.dexels.navajo.adapter.mailmap;

import java.io.Serializable;

import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.server.Parameters;
import com.dexels.navajo.adapter.MailMap;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.NavajoConfig;
import com.dexels.navajo.server.UserException;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.document.types.Binary;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class AttachementMap implements Mappable, Serializable, AttachmentMapInterface {

/**
	 * 
	 */
	private static final long serialVersionUID = 533613230177877706L;

// The readable descriptive name of the attachement.
  public String attachFileName = "unknown name";
  
  // Either attachFileContent or attachFile should be used!
  public Binary attachFileContent = null;
  public String attachFile = null;
  
  public String attachContentHeader = null;
  // Encoding is e.g. base64
  public String encoding = null;

  public void load(Parameters parms, Navajo inMessage, Access access, NavajoConfig config) throws MappableException, UserException {
  }

  public void store() throws MappableException, UserException {
  }

  public void kill() {
  }
  
  public void setEncoding(String s) {
	 this.encoding = s;
  }
  public String getEncoding() {
	 return encoding;
  }
  public Binary getAttachFileContent() {
    return attachFileContent;
  }
  public void setAttachFileContent(Binary attachFileContent) {
    this.attachFileContent = attachFileContent;
  }
  public void setAttachFileName(String attachFileName) {
    this.attachFileName = attachFileName;
  }
  public String getAttachFileName() {
    return attachFileName;
  }
  public String getAttachFile() {
    return attachFile;
  }
  public void setAttachFile(String attachFile) {
    this.attachFile = attachFile;
  }
  public void setAttachContentHeader(String s) {
  	attachContentHeader = s;
  }

  public static void main(String [] args) {
	  MailMap mm = new MailMap();
	  AttachementMap am = new AttachementMap();
	  mm.setAttachment(am);
  }
}