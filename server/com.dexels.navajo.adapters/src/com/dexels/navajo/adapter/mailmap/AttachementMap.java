package com.dexels.navajo.adapter.mailmap;

import java.io.File;
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

  @Override
public void load(Access access) throws MappableException, UserException {
  }

  @Override
public void store() throws MappableException, UserException {
  }

  @Override
public void kill() {
  }
  
  @Override
public void setEncoding(String s) {
	 this.encoding = s;
  }
  @Override
public String getEncoding() {
	 return encoding;
  }
  @Override
public Binary getAttachFileContent() {
    return attachFileContent;
  }
  @Override
public void setAttachFileContent(Binary attachFileContent) {
    this.attachFileContent = attachFileContent;
  }
  @Override
public void setAttachFileName(String attachFileName) {
    this.attachFileName = attachFileName;
  }
  @Override
public String getAttachFileName() {
    return attachFileName;
  }
  @Override
public String getAttachFile() {
    return attachFile;
  }
  @Override
  public void setAttachFile(String attachFile) throws UserException {
	// Check whether file exists.
	if (! ( new File(attachFile).exists() ) ) {
		throw new UserException(-1, "Could not attach file to mail. File not found: " + attachFile);
	}
    this.attachFile = attachFile;
  }
  @Override
public void setAttachContentHeader(String s) {
  	attachContentHeader = s;
  }
  @Override
public String getAttachContentDisposition() {
	    return attachContentDisposition;
  }
  @Override
public void setAttachContentDisposition(String s) {
	  attachContentDisposition = s;
  }
  @Override
public String getAttachContentType() {
	    return attachContentType;
}
@Override
public void setAttachContentType(String s) {
	  attachContentType = s;
}

  public static void main(String [] args) {
	  MailMap mm = new MailMap();
	  AttachementMap am = new AttachementMap();
	  mm.setAttachment(am);
  }
}
