package com.dexels.navajo.adapter;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.UserException;

public class CopyMessage implements Mappable {

  private Navajo outputDoc;
  private Navajo inDoc;
  public boolean useOutputDoc = true;
  public String copyMessageFrom = "";
  public String copyMessageTo = "";

  public void load(Access access) throws MappableException, UserException {
    outputDoc = access.getOutputDoc();
    inDoc = access.getInDoc();
  }

  public void store() throws MappableException, UserException {

    if (copyMessageFrom.equals("") && copyMessageTo.equals(""))
      throw new UserException( -1,
          "copyMessageFrom and copyMessageTo have to be specified");

    Message from = (useOutputDoc) ? outputDoc.getMessage(this.copyMessageFrom) : inDoc.getMessage(this.copyMessageFrom);

    if (from == null)
      throw new UserException( -1,
                              "Could not find message " + this.copyMessageFrom +
                              " in output document");

    Message to = (useOutputDoc) ? outputDoc.copyMessage(from, outputDoc) : inDoc.copyMessage(from, outputDoc);
    to.setName(this.copyMessageTo);

    try {
      outputDoc.addMessage(to);
    }
    catch (Exception e) {
      throw new UserException( -1, e.getMessage(), e);
    }
  }

  public void kill() {

  }

  public void setUseOutputDoc(boolean b) {
    this.useOutputDoc = b;
  }

  public void setCopyMessageFrom(String name) {
    this.copyMessageFrom = name;
  }

  public void setCopyMessageTo(String name) {
    this.copyMessageTo = name;
  }

}