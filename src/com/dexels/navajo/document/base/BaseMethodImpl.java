package com.dexels.navajo.document.base;
import java.util.*;

import com.dexels.navajo.document.*;

public class BaseMethodImpl extends BaseNode implements Method {
  private ArrayList myRequiredMessages = new ArrayList();
  private String myName = "";
  private Message myParent = null;
  private String myDescription = null;
  private String myServer;

  public BaseMethodImpl(Navajo n) {
    super(n);
  }
  public BaseMethodImpl(Navajo n, String name) {
    super(n);
    myName = name;
  }

  public final ArrayList getRequiredMessages() {
    return (ArrayList) myRequiredMessages;
  }

  public final void setAllRequired(ArrayList al) {
    myRequiredMessages = al;
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
/** @todo SERVER?! */
    BaseMethodImpl m = (BaseMethodImpl)NavajoFactory.getInstance().createMethod(n,getName(),getServer());
    m.setAllRequired(getRequiredMessages());
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
//  	BaseRequiredImpl req = new BaseRequiredImpl();
//  	req.setMessage(message);
//  	if (filter != null) {
//  		req.setFilter(filter);
//  	}
//    myRequiredMessages.add(req);
  }

  public final void addRequired(Message message) {
    myRequiredMessages.add(message.getFullMessageName());
    /**@todo Implement this com.dexels.navajo.document.Method abstract method*/
  }
  
  /**
   * Return true if a certaing message needs to be included in serialized form based
   * upon definitions in required.
   * 
   * @param message
   * @return
   */
  public final boolean includeMessage(Message message) {
  	
  
  	for (int i = 0; i < myRequiredMessages.size(); i++) {
  		Required req = (Required) myRequiredMessages.get(i);
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
  
  public Map getAttributes() {
      Map m = new HashMap();
      m.put("name", myName);
      return m;
  }


 public List getChildren() {
     
     return myRequiredMessages;
  }
public String getTagName() {
    return "method";
}
public Object getRef() {
    throw new UnsupportedOperationException("getRef not possible on base type. Override it if you need it");
}

  }