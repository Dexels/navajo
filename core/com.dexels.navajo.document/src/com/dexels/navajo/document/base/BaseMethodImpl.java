package com.dexels.navajo.document.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dexels.navajo.document.ExpressionEvaluator;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Method;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.Required;

public class BaseMethodImpl extends BaseNode implements Method {
    private static final long serialVersionUID = 4307457202134807432L;
    protected List<BaseRequiredImpl> myRequiredMessages = new ArrayList<BaseRequiredImpl>();
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

    @Override
    public final List<String> getRequiredMessages() {
        List<String> result = new ArrayList<String>();
        for (Required required : myRequiredMessages) {
            result.add(required.getMessage());
        }
        return result;
    }

    public final void setAllRequired(List<BaseRequiredImpl> al) {
        myRequiredMessages.addAll(al);
    }

    @Override
    public final String getName() {
        return myName;
    }

    @Override
    public final String getDescription() {
        return myDescription;
    }

    @Override
    public final void setDescription(String s) {
        myDescription = s;
    }

    public final void setParent(Message m) {
        myParent = m;
    }

    public final Message getParent() {
        return myParent;
    }

    @Override
    public final void setServer(String s) {
        myServer = s;
    }

    @Override
    public final String getServer() {
        return myServer;
    }

    @Override
    public final Method copy(Navajo n) {

        BaseMethodImpl m = (BaseMethodImpl) NavajoFactory.getInstance().createMethod(n, getName(), getServer());
        // ArrayList<BaseRequiredImpl> al = new ArrayList<BaseRequiredImpl>();
        for (String d : getRequiredMessages()) {
            m.addRequired(d);
        }
        return m;
    }

    public final String getPath() {
        if (myParent != null) {
            return myParent.getFullMessageName() + "/" + getName();
        }
        return "/" + getName();
    }

    @Override
    public final void setName(String name) {
        myName = name;
    }

    @Override
    public final void addRequired(String message) {
        addRequired(message, null);
    }

    @Override
    public final void addRequired(String message, String filter) {

    }

    @Override
    public final void addRequired(Message message) {
        BaseRequiredImpl bri = new BaseRequiredImpl();
        bri.setMessage(message.getFullMessageName());
        myRequiredMessages.add(bri);
        /**
         * @todo Implement this com.dexels.navajo.document.Method abstract method
         */
    }

    /**
     * Return true if a certain message needs to be included in serialized form
     * based upon definitions in required.
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
                    Operand o = expr.evaluate(req.getFilter(), getRootDoc(), null, message, null,null);
                    if (o.value instanceof Boolean) {
                        return ((Boolean) o.value).booleanValue();
                    }
                } catch (Exception e) {
                }
            }
        }

        return true;
    }

    @Override
    public Map<String, String> getAttributes() {
        Map<String, String> m = new HashMap<String, String>();
        m.put("name", myName);
        return m;
    }

    @Override
    public List<BaseNode> getChildren() {
        // does not serialize required parts of methods. don't know why, but it
        // really couldn't work
        return null;
    }

    @Override
    public String getTagName() {
        return "method";
    }

    @Override
    public Object getRef() {
        throw new UnsupportedOperationException("getRef not possible on base type. Override it if you need it");
    }

}