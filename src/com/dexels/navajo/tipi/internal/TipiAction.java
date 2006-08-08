package com.dexels.navajo.tipi.internal;

import java.util.*;
import com.dexels.navajo.parser.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.actions.*;
import com.dexels.navajo.tipi.studio.*;
import com.dexels.navajo.tipi.tipixml.*;
import com.dexels.navajo.document.*;

public abstract class TipiAction implements TipiExecutable {
    protected TipiContext myContext;

    protected TipiActionFactory myActionFactory;

    //  protected TipiEvent myEvent;
    protected TipiComponent myComponent;

    protected String myType;

    protected Map parameterMap = new HashMap();

    //  protected TipiCondition myCondition;
    protected abstract void execute(TipiEvent event) throws TipiBreakException, TipiException;
    private TipiEvent myEvent = null;
    //  protected TipiActionBlock myActionBlock;

    public static final boolean INLINE_ACTIONS = true;

  
    public void addParameter(TipiValue tv) {
//        System.err.println("ADDING PARAMETER:\n"+tv.toString());
//        Thread.dumpStack();
        parameterMap.put(tv.getName(), tv);
    }

    public void performAction(TipiEvent te) throws TipiBreakException, TipiException {
    	myContext.debugLog("action", myType);
    	myEvent = te;
        if (myComponent.isDisposed()) {
            System.err.println("\n**** BREAKING. COMPONENT DISPOSED: " + myComponent.getPath());
//            Thread.dumpStack();
            throw new TipiBreakException();
        }
        try {
            myContext.performedAction(myComponent, this, te);
        } catch (BlockActivityException ex1) {
            System.err.println("Blocked exception");
            return;
        }
        execute(te);
        myEvent = null;
    }

    public XMLElement store() {
        XMLElement xe = new CaseSensitiveXMLElement();
        xe.setName("action");
        xe.setAttribute("type", getType());
        Iterator it = parameterMap.keySet().iterator();
        while (it.hasNext()) {
            String name = (String) it.next();
//            System.err.println("Storing: " + name);
            TipiValue value = (TipiValue) parameterMap.get(name);

//            System.err.println("DefaultValue: " + value.getValue() + " default: " + value.getDefaultValue());
            if (value.getValue() == null) {
//                System.err.println("Skipping null: " + value.getName());
                continue;
            }
            if (value.getValue().equals(value.getDefaultValue())) {
//                System.err.println("Skipping default.");
                continue;
            }
//            System.err.println("Storing action. value: " + value.getValue() + " defaultValue: " + value.getDefaultValue());
            if (INLINE_ACTIONS) {
                xe.setAttribute(name, value.getValue());
            } else {
                XMLElement pr = new CaseSensitiveXMLElement();
                pr.setName("param");
                pr.setAttribute("name", name);
                pr.setAttribute("value", value.getValue());
                xe.addChild(pr);
            }

        }
//        System.err.println("Stored actiontype: " + getType() + " # of params: " + xe.getChildren().size());
        return xe;
    }

    public void setType(String type) {
        myType = type;
    }

    public String getType() {
        return myType;
    }

    public boolean hasParameter(String name) {
        return parameterMap.containsKey(name);
    }

    public TipiValue getParameter(String name) {
        return (TipiValue) parameterMap.get(name);
    }

    public ArrayList getParams() {
        ArrayList parms = new ArrayList(parameterMap.values());
        return parms;
    }
    
    public Set getParameterNames() {
        return parameterMap.keySet();
    }

    public Operand evaluate(String expr, TipiEvent event) {
        //    if (event==null) {
        //      System.err.println("EVALUATING: "+expr+" No event. ");
        //    } else {
        //      System.err.println("EVALUATING: "+expr+" event:
        // "+event.getEventName());
        //    }
        Message m = null;
        Navajo n = null;
        if (myComponent != null) {
            n = myComponent.getNearestNavajo();
        }
        if (TipiDataComponent.class.isInstance(myComponent)) {
            TipiDataComponent tdc = (TipiDataComponent) myComponent;
            //      n = tdc.getNavajo();
            String prefix = tdc.getPrefix();
            if (n != null && prefix != null) {
                m = n.getMessage(prefix);
            }
        }
        return myContext.evaluate(expr, myComponent, event, n, m);
    }

    public Operand getEvaluatedParameter(String name, TipiEvent event) {
        TipiValue t = getParameter(name);
        if (t == null) {
            return null;
        }
        return evaluate(t.getValue(), event);
    }

    public void setContext(TipiContext tc) {
        myContext = tc;
    }

    public void setComponent(TipiComponent tc) {
        myComponent = tc;
    }

    public TipiComponent getComponent() {
        return myComponent;
    }

    //
    //  public void setEvent(TipiEvent te) {
    //    myEvent = te;
    //  }

    public int getExecutableChildCount() {
        return 0;
    }

    public TipiExecutable getExecutableChild(int index) {
        return null;
    }
    
    public TipiEvent getEvent() {
        return myEvent;
        
    }
    public void setEvent(TipiEvent e) {
        myEvent = e;
        
    }

}