package com.dexels.navajo.document.base;
import java.util.*;

import com.dexels.navajo.document.*;

public class BaseTransactionImpl extends BaseNode {
//  private ArrayList myRequiredMessages = new ArrayList();
//  private String myName = "";
//  private Message myParent = null;
//  private String myDescription = null;
//  private String myServer;
private String rpc_name;
private String rpc_usr;
private String rpc_pwd;
private String rpc_schedule;
protected String myRequestId;

  public BaseTransactionImpl(Navajo n) {
    super(n);
  }

 
  public Map<String,String> getAttributes() {
      Map<String,String> m = new HashMap<String,String>();
      if (rpc_name!=null) {
          m.put("rpc_name", rpc_name);
      }
      if (rpc_usr!=null) {
          m.put("rpc_usr", rpc_usr);
      }
      if (rpc_pwd!=null) {
          m.put("rpc_pwd", rpc_pwd);
      }
      if (rpc_schedule!=null) {
          m.put("rpc_schedule", rpc_schedule);
      }
      if (myRequestId!=null) {
          m.put("requestid", myRequestId);
      }
      return m;
  }


 public List<? extends BaseNode> getChildren() {
     return null;
  }
public String getTagName() {
    return "transaction";
}
public final String getRpc_name() {
    return rpc_name;
}
public final void setRpc_name(String rpc_name) {
    this.rpc_name = rpc_name;
}
public final String getRpc_pwd() {
    return rpc_pwd;
}
public final void setRpc_pwd(String rpc_pwd) {
    this.rpc_pwd = rpc_pwd;
}
public final String getRpc_usr() {
    return rpc_usr;
}
public final void setRpc_usr(String rpc_usr) {
    this.rpc_usr = rpc_usr;
}
public final void setRpc_schedule(String rpc_schedule) {
    this.rpc_schedule = rpc_schedule;
}
public final String getRpc_schedule() {
    return rpc_schedule;
}
public String getRequestId() {
    return myRequestId;
}


public void setRequestId(String id) {
    myRequestId = id;
}
  }