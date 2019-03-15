package com.dexels.navajo.document.base;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dexels.navajo.document.Navajo;

public class BaseTransactionImpl extends BaseNode {
	private static final long serialVersionUID = -6325965122672286543L;
private String rpcname;
private String rpcusr;
private String rpcpwd;
private String rpcschedule;
protected String myRequestId;


  public BaseTransactionImpl(Navajo n) {
    super(n);
  }

 
  @Override
public Map<String,String> getAttributes() {
      Map<String,String> m = new HashMap<>();
      if (rpcname!=null) {
          m.put("rpc_name", rpcname);
      }
      if (rpcusr!=null) {
          m.put("rpc_usr", rpcusr);
      }
      if (rpcpwd!=null) {
          m.put("rpc_pwd", rpcpwd);
      }
      if (rpcschedule!=null) {
          m.put("rpc_schedule", rpcschedule);
      }
      if (myRequestId!=null) {
          m.put("requestid", myRequestId);
      }
      return m;
  }


 @Override
public List<? extends BaseNode> getChildren() {
     return null;
  }
@Override
public String getTagName() {
    return "transaction";
}
public final String getRpc_name() {
    return rpcname;
}
public final void setRpc_name(String rpc_name) {
    this.rpcname = rpc_name;
}
public final String getRpc_pwd() {
    return rpcpwd;
}
public final void setRpc_pwd(String rpc_pwd) {
    this.rpcpwd = rpc_pwd;
}
public final String getRpc_usr() {
    return rpcusr;
}


public final void setRpc_usr(String rpc_usr) {
    this.rpcusr = rpc_usr;
}
public final void setRpc_schedule(String rpc_schedule) {
    this.rpcschedule = rpc_schedule;
}
public final String getRpc_schedule() {
    return rpcschedule;
}
public String getRequestId() {
    return myRequestId;
}


public void setRequestId(String id) {
    myRequestId = id;
}


  }