package com.dexels.navajo.document.nanoimpl;
import java.io.*;
import java.util.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.document.base.*;

/**
 * <p>Title: ShellApplet</p>
 * <p>Description: </p>
 * <p>Part of the Navajo mini client, based on the NanoXML parser</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels </p>
 * @author Frank Lyaruu
 * @version 1.0
 * @deprecated
 */


@Deprecated
public final class NavajoImpl extends BaseNavajoImpl implements Navajo, NanoElement {


//  private Map myDepSet = null;

  public NavajoImpl() {
     /** @todo Check.. */
    myHeader = (HeaderImpl)NavajoFactory.getInstance().createHeader(this,"","","",-1);
  }

  public String getImplementationName() {
      return "NANO";
  }
  
  public void addHeader(Header h) {
	super.addHeader(h);
//    myHeader.merge((HeaderImpl)h);
  }


  public Navajo copy() {
    Navajo ni = NavajoFactory.getInstance().createNavajo();
    NavajoImpl n = (NavajoImpl)ni;
//    n.setRootMessage(cop getRootMessage().copy(n));
    ArrayList<Message> al = getAllMessages();
    for (int i = 0; i < al.size(); i++) {
      Message m = al.get(i);
      Message m2 = copyMessage(m,n);
      n.addMessage(m2);
    }

    for (int i = 0; i < myMethods.getAllMethods().size(); i++) {
      Method m = myMethods.getAllMethods().get(i);
      Method m2 = n.copyMethod(m,n);
      n.addMethod(m2);
    }
    return n;
  }



/**
 * @deprecated
 *  (non-Javadoc)
 * @see com.dexels.navajo.document.Navajo#addLazyMessagePath(java.lang.String, int, int, int)
 */
  @Deprecated
public void addLazyMessagePath(String path, int startIndex, int endIndex, int total) {
///** @todo Fix this one */
    myHeader.addLazyMessagePath(path, startIndex, endIndex, total);
  }
//

  public static Method createMethod(NavajoImpl n, String name) {
    return new MethodImpl(n,name);
  }

 
  public XMLElement toXml() {
  	return toXml(false, null);
  }

  public XMLElement toXml(boolean condense, String method) {
    XMLElement x = new CaseSensitiveXMLElement();
    toXmlElement(x, condense, method);
    return x;
//    return rootMessage.generateTml(myHeader);

  }

  private final void toXmlElement(XMLElement x, boolean condense, String method) {
//    XMLElement x=  ((MessageImpl)rootMessage).toXml(null);
    x.setName("tml");
    x.setAttribute("documentImplementation", getImplementationName());
    
//    System.err.println("\n\nMY HEADER: "+x);
    if (myHeader != null) {
      x.addChild( ( (HeaderImpl) myHeader).toXml(null));
      ( (MessageImpl) rootMessage).generateTml(this, myHeader, x, condense, method);
    }
//    System.err.println("MY HEADERAGAIN: "+x+"\n\n");
//    rootMessage.generateTml(myHeader,x);
    if (!condense) {
        addMethods(x);
    }
  }

  private final void addMethods(XMLElement x) {
    XMLElement methods = new CaseSensitiveXMLElement();
    methods.setName("methods");
    x.addChild(methods);
    for (int i = 0; i < myMethods.getAllMethods().size(); i++) {
      MethodImpl m = (MethodImpl) myMethods.getAllMethods().get(i);
      XMLElement mx = m.toXml(x);
      methods.addChild(mx);
    }
  }

  public void fromXml(XMLElement e) {
    List<XMLElement> v = e.getChildren();
    ((MessageImpl)rootMessage).fromXml(e);
    for (int i = 0; i < v.size(); i++) {
      XMLElement x = v.get(i);
      String name = x.getName();
      if (name.equals("methods")) {
        loadMethods(x);
      } else if (name.equals("header")) {
         ((HeaderImpl) myHeader).fromXml(x);
      }
    }
  }

  private final void loadMethods(XMLElement e) {
    myMethods.clear();
    List<XMLElement> v = e.getChildren();
    for (int i = 0; i < v.size(); i++) {
      XMLElement x = v.get(i);
      String name = (String)x.getAttribute("name");
      MethodImpl m = (MethodImpl)createMethod(this,name);
      m.fromXml(x);
      myMethods.addMethod(m);
    }
  }



 /**
 * @deprecated
 *  (non-Javadoc)
 * @see com.dexels.navajo.document.Navajo#getLazyMessagePath(java.lang.String)
 */
  @Deprecated
public LazyMessagePath getLazyMessagePath(String path) {
    return myHeader.getLazyMessagePath(path);
  }

  public void write(java.io.Writer writer) throws NavajoException {
  	try {
        write(writer, false, null);
    } catch (RuntimeException e) {
        e.printStackTrace();
    }
  }

  public void write(OutputStream o) throws NavajoException {
  	write(o, false, null);
  }

  public void write(OutputStream o, boolean condense, String method) {
    try {
      OutputStreamWriter w = new OutputStreamWriter(o);
      toXml(condense, method).write(w);
      w.flush();
    }
    catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  public String toString() {
    return toXml().toString();
  }

  public void write(Writer w, boolean condense, String method) {
    try {
      toXml(condense, method).write(w);
      w.flush();
    }
    catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  public void clearAllSelections() {
    try {
      ( (MessageImpl) rootMessage).clearAllSelections();
    }
    catch (NavajoException ex) {
      ex.printStackTrace();
    }
  }

  public void appendDocBuffer(Object o) {
    XMLElement xe = (XMLElement)o;
    fromXml(xe);
  }

  public Object getMessageBuffer() {
    return toXml();
  }

  public Method copyMethod(Method m, Navajo n) {
    MethodImpl mi = (MethodImpl)m;
    Method m2 = mi.copy(n);
    return m2;
  }
  public Method copyMethod(String s, Navajo n) {
    MethodImpl mi = (MethodImpl)getMethod(s);
    Method m2 = mi.copy(n);
    return m2;
  }



  /**
   * DEBUGGING: write a specific message (name) to a file (filename).
   */
  public void writeMessage(String name, String filename) throws NavajoException {
     try {
          FileWriter out = new FileWriter("/tmp/" + filename);
          ((MessageImpl)getMessage(name)).toXml(null).write(out);
      } catch (Exception e) {
        e.printStackTrace();
      }
  }

  public String writeDocument(String filename) {
    try {
         FileWriter out = new FileWriter("/tmp/" + filename);
         toXml().write(out);
         return "succes";
     } catch (Exception e) {
         return "failure";
     }
  }



  public void read(java.io.Reader stream) throws NavajoException {
    XMLElement xe = new CaseSensitiveXMLElement();
    try {
      xe.parseFromReader(stream);
    }
    catch (XMLParseException ex) {
      throw new NavajoExceptionImpl(ex);
    }
    catch (IOException ex) {
      throw new NavajoExceptionImpl(ex);
    }
    fromXml(xe);
  }

  public void read(java.io.InputStream stream) throws NavajoException {
    InputStreamReader isr = new InputStreamReader(stream);
    read(isr);
  }

//  public void updateDependencySet() throws NavajoException {
//    myDepSet = NavajoFactory.getInstance().getExpressionEvaluator().createDependencyMap(this);
//  }

  public boolean includeMessage(Message m, String method) {

  	//System.err.println("in NavajoImpl includeMessage(), #methods: " + myMethods.size() + ", method = " + method);
  	MethodImpl methObj = (MethodImpl) getMethod(method);
  	if (methObj != null) {
  		return methObj.includeMessage(m);
  	} else {
  		return true;
  	}
  }
  public final void writeComponent(Writer w) throws IOException {
      toXml().write(w);
  }
}
