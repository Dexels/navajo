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
public class BaseOperationsImpl extends BaseNode {

	private static final long serialVersionUID = 9105044646681827267L;
	private final List<BaseNode> myOperations = new ArrayList<BaseNode>();
  public BaseOperationsImpl(Navajo n) {
    super(n);
  }

    @Override
	public Map<String,String> getAttributes() {
        return null;
    }

    @Override
	public List<BaseNode> getChildren() {
        return myOperations;
    }

    @Override
	public String getTagName() {
        return "operations";
    }

   
    public void addOperation(Operation m) {
        if (!(m instanceof BaseOperationImpl)) {
            throw new IllegalArgumentException("Wrong impl, ouwe!");
        }
        BaseOperationImpl bmi = (BaseOperationImpl)m;
        myOperations.add(bmi);
    }
    
    public Method getMethod(String s) {
        for (int i = 0; i < myOperations.size(); i++) {
          Method m = (Method)myOperations.get(i);
          if (m.getName().equals(s)) {
            return m;
          }
        }
        return null;
      }

    public ArrayList<Operation> getAllOperations() {
    	ArrayList<Operation> al = new ArrayList<Operation>();
        for (int i = 0; i < myOperations.size(); i++) {
        	Operation m = (Operation)myOperations.get(i);
            al.add(m);
        }
        return al;
    }

    public void clear() {
    	myOperations.clear();
    }
    
}

// EOF $RCSfile$ //
