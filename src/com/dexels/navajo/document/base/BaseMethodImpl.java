package com.dexels.navajo.document.base;
import java.util.*;

import com.dexels.navajo.document.*;

public class BaseMethodImpl extends BaseNode implements Method {
  protected ArrayList<BaseRequiredImpl> myRequiredMessages = new ArrayList<BaseRequiredImpl>();
  protected String myName = "";
  protected Message myParent = null;
  protected String myDescription = null;
  protected String myServer;

  public BaseMethodImpl(Navajo n) {
    super(n);
  }
  public BaseMethodImpl(Navajo n, String name) {
    super(n);
    myName = name;
  }

  public final ArrayList<String> getRequiredMessages() {
	  ArrayList<String> result = new ArrayList<String>();
	  for (Required required : myRequiredMessages) {
		result.add(required.getMessage());
	}
	  return result;
  }

  public final void setAllRequired(ArrayList<BaseRequiredImpl> al) {
	  myRequiredMessages.addAll(al);
  }

  public final String getName() {
    return myName;
  }

  public final String getDescription() {
    return myDescription;
  }

  public final void setDescription(String s) {
    myDescription = s;
  }


  public final void setParent(Message m ) {
    myParent = m;
  }

  public final Message getParent() {
    return myParent;
  }

  public final void setServer(String s) {
    myServer = s;
  }

  public final String getServer() {
    return myServer;
  }

  public final Method copy(Navajo n) {

    BaseMethodImpl m = (BaseMethodImpl)NavajoFactory.getInstance().createMethod(n,getName(),getServer());
//    ArrayList<BaseRequiredImpl> al = new ArrayList<BaseRequiredImpl>();
    for (String d : getRequiredMessages()) {
    	m.addRequired(d);
    }
    return m;
  }

  public final String getPath() {
    if (myParent!=null) {
      return myParent.getFullMessageName()+"/"+getName();
    } else {
      return "/"+getName();
    }
  }

  public final void setName(String name) {
    myName = name;
  }

  public final void addRequired(String message) {
  	addRequired(message, null);
  }
  
  public final void addRequired(String message, String filter) {

  }

  public final void addRequired(Message message) {
	  BaseRequiredImpl bri = new BaseRequiredImpl();
	  bri.setMessage(message.getFullMessageName());
    myRequiredMessages.add(bri);
    /**@todo Implement this com.dexels.navajo.document.Method abstract method*/
  }
  
  /**
   * Return true if a certain message needs to be included in serialized form based
   * upon definitions in required.
   * 
   * @param message
   * @return
   */
  public final boolean includeMessage(Message message) {
  	
  
  	for (int i = 0; i < myRequiredMessages.size(); i++) {
  		Required req = myRequiredMessages.get(i);
  		if (req.getFilter() != null && !req.getFilter().equals("") && req.getMessage().equals(message.getName())) {
  			ExpressionEvaluator expr = NavajoFactory.getInstance().getExpressionEvaluator();
  			try {
  				Operand o = expr.evaluate(req.getFilter(), getRootDoc(), null, message);
  				if (o.value instanceof Boolean) {
  					return ((Boolean) o.value).booleanValue();
  				}
  			} catch (Exception e) {}
  		}
  	}
  	
  	return true;
  }
  
  public Map<String,String> getAttributes() {
      Map<String,String> m = new HashMap<String,String>();
      m.put("name", myName);
      return m;
  }


 public List<BaseNode> getChildren() {
     // does not serialize required parts of methods. don't know why, but it really couldn't work
     return null;
  }
public String getTagName() {
    return "method";
}
public Object getRef() {
    throw new UnsupportedOperationException("getRef not possible on base type. Override it if you need it");
}

  }