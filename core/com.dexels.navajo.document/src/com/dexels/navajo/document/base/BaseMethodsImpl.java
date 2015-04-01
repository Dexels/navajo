package com.dexels.navajo.document.base;

import java.util.*;

import com.dexels.navajo.document.*;

/**
 * <p>Title: ShellApplet</p>
 * <p>Description: </p>
 * <p>Part of the Navajo mini client, based on the NanoXML parser</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels </p>
 * <p>$Id$</p>
 * @author Frank Lyaruu
 * @version $Revision$
 */
public class BaseMethodsImpl extends BaseNode {

	private static final long serialVersionUID = 9105044646681827267L;
	private final List<BaseNode> myMethods = new ArrayList<BaseNode>();
  public BaseMethodsImpl(Navajo n) {
    super(n);
  }

    @Override
	public Map<String,String> getAttributes() {
        return null;
    }

    @Override
	public List<BaseNode> getChildren() {
        return myMethods;
    }

    @Override
	public String getTagName() {
        return "methods";
    }

   
    public void addMethod(Method m) {
        if (!(m instanceof BaseMethodImpl)) {
            throw new IllegalArgumentException("Wrong impl, ouwe!");
        }
        BaseMethodImpl bmi = (BaseMethodImpl)m;
        myMethods.add(bmi);
    }
    
    public Method getMethod(String s) {
        for (int i = 0; i < myMethods.size(); i++) {
          Method m = (Method)myMethods.get(i);
          if (m.getName().equals(s)) {
            return m;
          }
        }
        return null;
      }

    public List<Method> getAllMethods() {
        List<Method> al = new ArrayList<Method>();
        for (int i = 0; i < myMethods.size(); i++) {
            Method m = (Method)myMethods.get(i);
            al.add(m);
        }
        return al;
    }

    public void clear() {
        myMethods.clear();
    }
    
}

// EOF $RCSfile$ //
