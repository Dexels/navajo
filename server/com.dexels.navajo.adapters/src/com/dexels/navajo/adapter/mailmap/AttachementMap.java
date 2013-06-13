package com.dexels.navajo.adapter.mailmap;

import java.io.Serializable;

import com.dexels.navajo.adapter.MailMap;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.Mappable;
import com.dexels.navajo.script.api.MappableException;
import com.dexels.navajo.script.api.UserException;

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
  public String attachContentDisposition = "attachment";
  public String attachContentType = null;
  // Encoding is e.g. base64
  public String encoding = null;

  public void load(Access access) throws MappableException, UserException {
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
  public String getAttachContentDisposition() {
	    return attachContentDisposition;
  }
  public void setAttachContentDisposition(String s) {
	  attachContentDisposition = s;
  }
  public String getAttachContentType() {
	    return attachContentType;
}
public void setAttachContentType(String s) {
	  attachContentType = s;
}

  public static void main(String [] args) {
	  MailMap mm = new MailMap();
	  AttachementMap am = new AttachementMap();
	  mm.setAttachment(am);
  }
}