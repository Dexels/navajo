package com.dexels.navajo.document.base;

/**
 * <p>Title: ShellApplet</p>
 * <p>Description: </p>
 * <p>Part of the Navajo mini client, based on the NanoXML parser</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels </p>
 * @author Frank Lyaruu
 * @version 1.0
 */

import java.util.*;
// import com.dexels.navajo.tipi.tipixml.*;
// import com.dexels.sportlink.client.swing.*;
// import com.dexels.navajo.nanoclient.*;
import com.dexels.navajo.document.*;
//import com.dexels.navajo.document.nanoimpl.PropertyImpl;

import java.util.regex.*;
import java.io.*;
import javax.swing.tree.*;

public class BaseMessageImpl extends BaseNode implements Message, TreeNode {
    protected String myName = "";

    private String myType = "";

    private String myMode = "";

    private String myCondition = "";

    private int myIndex = -1;

    protected final TreeMap propertyMap = new TreeMap();

    protected final ArrayList propertyList = new ArrayList();

    protected final TreeMap messageMap = new TreeMap();

    protected final List messageList = new ArrayList();

    private BaseMessageImpl myParent = null;

    private MessageMappable myStringMap = null;

    private int startIndex = -1;

    private int endIndex = -1;

    private boolean isRootMessage = false;

    // private List myDefinitionList = null;
    // private Map myDefinitionMap = null;

    protected BaseMessageImpl definitionMessage = null;

    public BaseMessageImpl(Navajo n) {
        super(n);
        myType = Message.MSG_TYPE_SIMPLE;
    }

    public BaseMessageImpl(Navajo n, String name) {
        super(n);
        myName = name;
        myType = Message.MSG_TYPE_SIMPLE;
    }

    public final void setRootMessage(boolean b) {
        isRootMessage = b;
    }

    public final String getType() {
        return myType;
    }

    public final void setType(String type) {
        myType = type;
    }

    public final String getName() {
        return myName;
    }

    public final void setCondition(String condition) {
        myCondition = condition;
    }

    public final void setName(String name) {
        myName = name;
    }

    public final void setMode(String mode) {
        myMode = mode;
    }

    public final String getMode() {
        return myMode;
    }

    public final void clearAllSelections() throws NavajoException {
        for (int i = 0; i < propertyList.size(); i++) {
            Property p = (Property) propertyList.get(i);
            p.clearSelections();
        }
        for (int i = 0; i < messageList.size(); i++) {
            BaseMessageImpl p = (BaseMessageImpl) messageList.get(i);
            p.clearAllSelections();
        }

    }

    public Message addMessage(Message m) {

        if (m == null) {
            // System.err.println("Ignoring null message. Not adding message");
            return null;
        }

        // Do not add messages with mode "ignore".
        if (m.getMode().endsWith(Message.MSG_MODE_IGNORE)) {
            // System.out.println("IGNORING ADDMESSAGE(), MODE =
            // IGNORE!!!!!!!");
            return null;
        }
        // System.err.println("SETTING PARENT OF MESSAGE: "+m.getName()+" type:
        // "+m.getType()+" I am: "+getName()+" my type: "+getType());
        m.setParent(this);
        if (this.getType().equals(Message.MSG_TYPE_ARRAY)) {
            return addMessage(m, false);
        } else {
            return addMessage(m, true);
        }
        // return addMessage(m, false);

    }

    public final Message addMessage(Message m, boolean overwrite) {
        String name = m.getName();

        if (getMessage(name) != null && !overwrite && !this.getType().equals(Message.MSG_TYPE_ARRAY)) {
            return getMessage(name);
        }

        if (getMessage(name) != null && overwrite) {
            removeChildMessage(getMessage(m.getName()));
        }
        /**
         * If message is array type, insert new message as "element".
         */
        messageMap.put(m.getName(), m);
        if (getType().equals(MSG_TYPE_ARRAY)) {
        	if ( !m.getType().equals(MSG_TYPE_DEFINITION) ) {
        		m.setIndex(messageList.size());
        	}
            m.setName(getName());
        }
        messageList.add(m);

        return m;
    }

    public final void addMessage(Message m, int index) throws NavajoException {
        if (!getType().equals(Message.MSG_TYPE_ARRAY)) {
            throw new NavajoExceptionImpl("Can not add to with index to messages, if it is not an array message. Is that clear?");
        }
        messageList.add(index, m);
        messageMap.put(m.getName(), m);
        m.setIndex(index);
        m.setName(getName());
        m.setParent(this);
    }

    public ArrayList getAllMessages() {
        return new ArrayList(messageList);
    }

    public final void addProperty(Property q) {

        BasePropertyImpl p = (BasePropertyImpl) q;
        if (propertyMap.get(p.getName()) == null) {
            propertyList.add(q);
            propertyMap.put(p.getName(), p);
            p.setParent(this);
        } else {
            this.removeProperty((Property) propertyMap.get(p.getName()));
            addProperty(q);
        }
        initPropertyFromDefinition(q);
    }

    
	private void initPropertyFromDefinition(Property q) {
		// Set default values from definition message.
        BaseMessageImpl parentArrayMessage = (BaseMessageImpl) getArrayParentMessage();
        if ( parentArrayMessage != null) {

            Property definitionProperty = parentArrayMessage.getPropertyDefinition(myName);

            if (definitionProperty != null) {
              if (q.getDescription() == null || "".equals(q.getDescription())) {
            	  q.setDescription(definitionProperty.getDescription());
              }
              if (q.getDirection() == null || "".equals(q.getDirection())) {
                q.setDirection(definitionProperty.getDirection());
              }
              if (q.getType() == null || "".equals(q.getType())) {
            	 q.setType(definitionProperty.getType());
              }
//              if (q.getLength() == null) {
//                length = definitionProperty.getLength();
//              }
              if (q.getSubType() == null) {
                if (definitionProperty.getSubType() != null) {
                  q.setSubType(definitionProperty.getSubType());
                }
                else {
                	q.setSubType(null);
                }
              }
              else {
                if (definitionProperty.getSubType() != null) {
                  /**
                       * Concatenated subtypes. The if the same key of a subtype is present
                   * in both the property and the definition property.
                   */
                  q.setSubType(definitionProperty.getSubType() + "," + q.getSubType());
                }
              }

              if (q.getValue() == null || "".equals(q.getValue())) {
                q.setValue(definitionProperty.getValue());
              }
            }
          }
	}

    public final ArrayList getAllProperties() {
        return getTotalPropertyList();
    }
    
    private final ArrayList getTotalPropertyList() {
    	if (getArrayParentMessage()==null) {
			return propertyList;
		}
    	if (getArrayParentMessage().getDefinitionMessage()==null) {
    		return propertyList;
    	}
    	ArrayList resList = new ArrayList();
//    	resList.addAll(propertyList);
    	for (Iterator iter = getArrayParentMessage().getDefinitionMessage().getAllProperties().iterator(); iter.hasNext();) {
			Property element = (Property) iter.next();
			if (element!=null) {
				Property local = (Property)propertyMap.get(element.getName());
				if (local!=null) {
					mergeProperty(local,element);
				}
			}
			resList.add(getProperty(element.getName()));
		}
    	return resList;
    }

    private void mergeProperty(Property local, Property definition) {
    	if (local.getDescription()==null) {
			local.setDescription(definition.getDescription());
		}
    	if (local.getCardinality()==null) {
			local.setCardinality(definition.getCardinality());
		}
    	if (local.getDirection()==null) {
			local.setDirection(definition.getDirection());
		}
       	if (local.getType()==null) {
			local.setType(definition.getType());
		}
       	if (local.getValue()==null || "".equals(local.getValue())) {
			local.setValue(definition.getValue());
		}
    	
	}

	public final ArrayList getProperties(String regularExpression) throws NavajoException {

        if (regularExpression.startsWith(Navajo.PARENT_MESSAGE + Navajo.MESSAGE_SEPARATOR)) {
            regularExpression = regularExpression.substring((Navajo.PARENT_MESSAGE + Navajo.MESSAGE_SEPARATOR).length());
            return getParentMessage().getProperties(regularExpression);
        } else if (regularExpression.startsWith(Navajo.MESSAGE_SEPARATOR)) { // We
                                                                                // have
                                                                                // an
                                                                                // absolute
                                                                                // offset
        // Navajo d = new NavajoImpl(this.ref.getOwnerDocument());
            Navajo d = getRootDoc();
            return d.getProperties(regularExpression.substring(1));
        } else {
            ArrayList props = new ArrayList();
            Property prop = null;
            ArrayList messages = null;
            ArrayList sub = null;
            ArrayList sub2 = null;
            String property = null;
            Message message = null;

            StringTokenizer tok = new StringTokenizer(regularExpression, Navajo.MESSAGE_SEPARATOR);
            String messageList = "";

            int count = tok.countTokens();

            for (int i = 0; i < count - 1; i++) {
                property = tok.nextToken();
                messageList += property;
                if ((i + 1) < count - 1) {
                    messageList += Navajo.MESSAGE_SEPARATOR;
                }
            }
            String realProperty = tok.nextToken();

            if (!messageList.equals("")) {
                messages = this.getMessages(messageList);
            } else {
                messages = new ArrayList();
                messages.add(this);
            }

            Pattern pattern = Pattern.compile(realProperty);
            for (int i = 0; i < messages.size(); i++) {
                message = (Message) messages.get(i);
                ArrayList allProps = message.getAllProperties();
                try {
                    for (int j = 0; j < allProps.size(); j++) {
                        String name = ((Property) allProps.get(j)).getName();
                        if (pattern.matcher(name).matches()) {
                            props.add(allProps.get(j));
                        }
                    }
                } catch (Exception re) {
                    throw new NavajoExceptionImpl(re.getMessage());
                }
            }
            return props;
        }
    }

    public void refreshExpression() throws NavajoException {
        ArrayList aa = getAllMessages();
        for (int i = 0; i < aa.size(); i++) {
            Message current = (Message) aa.get(i);
            current.refreshExpression();
        }
        ArrayList bb = getAllProperties();
        for (int j = 0; j < bb.size(); j++) {
            Property current = (Property) bb.get(j);
            current.refreshExpression();
        }

    }

    public Message getMessage(String name) {

        // System.err.println("in getMessage("+name+")");
        if (name.startsWith("../")) {
            return getParentMessage().getMessage(name.substring(3));
        }

        if (name.startsWith("/")) {
            return getRootDoc().getMessage(name.substring(1));
        }
        if (name.indexOf("/") >= 0) {
            return getByPath(name);
        }

        if (name.indexOf("@") >= 0) {
            // System.err.println("Found reference to Array element message:
            // "+name);
            StringTokenizer arEl = new StringTokenizer(name, "@");
            String realName = arEl.nextToken();
            Message array = getMessage(realName);
            if (array != null) {
                if ((array.getType() != null) && (array.getType().equals(Message.MSG_TYPE_ARRAY))) {
                    if (arEl.hasMoreTokens()) {
                        String index = arEl.nextToken();
                        int i = 0;
                        try {
                            i = Integer.parseInt(index);
                        } catch (NumberFormatException ex) {
                            ex.printStackTrace();
                        }
                        return array.getMessage(i);
                    }
                }
            }
        }

        return (Message) messageMap.get(name);
    }

    // public ArrayList getMessages(String regexp) {
    // Iterator it = messageMap.values().iterator();
    // ArrayList selected = new ArrayList();
    // while (it.hasNext()) {
    // Message m = (Message) it.next();
    // if (compliesWith(m, regexp)) {
    // selected.add(m);
    // }
    // }
    // return selected;
    // }
    //
    // private ArrayList getMessagesByPath(String regExpPath) {
    //
    // }
    /**
     * Return all messages that match a given regular expression. Regular
     * expression may include sub-messages and even absolute message references
     * starting at the root level.
     */
    public ArrayList getMessages(String regularExpression) throws NavajoException {

        ArrayList messages = new ArrayList();
        ArrayList sub = null;
        ArrayList sub2 = null;

        if (regularExpression.startsWith(Navajo.PARENT_MESSAGE + Navajo.MESSAGE_SEPARATOR)) {
            regularExpression = regularExpression.substring((Navajo.PARENT_MESSAGE + Navajo.MESSAGE_SEPARATOR).length());
            return getParentMessage().getMessages(regularExpression);
        } else if (regularExpression.startsWith(Navajo.MESSAGE_SEPARATOR)) { // We
                                                                                // have
                                                                                // an
                                                                                // absolute
                                                                                // offset

            return myDocRoot.getMessages(regularExpression);
        } else // Contains submessages.
        if (regularExpression.indexOf(Navajo.MESSAGE_SEPARATOR) != -1) { // contains
                                                                            // a
                                                                            // path,
                                                                            // descent
                                                                            // it
                                                                            // first
            StringTokenizer tok = new StringTokenizer(regularExpression, Navajo.MESSAGE_SEPARATOR);
            Message m = null;

            while (tok.hasMoreElements()) {
                String msgName = tok.nextToken();

                if (sub == null) { // First message in path.
                    sub = getMessages(msgName);
                } else { // Subsequent submessages in path.
                    messages = new ArrayList();
                    for (int i = 0; i < sub.size(); i++) {
                        m = (Message) sub.get(i);
                        sub2 = m.getMessages(msgName);
                        messages.addAll(sub2);
                    }
                    sub = messages;
                }
            }
            return sub;
        } else {
            ArrayList msgList = getAllMessages();
            ArrayList result = new ArrayList();
            try {
                String index = null;

                if (regularExpression.indexOf("@") != -1) {
                    StringTokenizer arEl = new StringTokenizer(regularExpression, "@");
                    regularExpression = arEl.nextToken();
                    index = arEl.nextToken();

                }
                Pattern pattern = Pattern.compile(regularExpression);
                for (int i = 0; i < msgList.size(); i++) {
                    Message m = (Message) msgList.get(i);
                    String name = m.getName();
                    if (m.getType().equals(Message.MSG_TYPE_ARRAY) && pattern.matcher(name).matches()) { // If
                                                                                                            // message
                                                                                                            // is
                                                                                                            // array
                                                                                                            // type
                                                                                                            // add
                                                                                                            // all
                                                                                                            // children.
                        if (index == null) {
                            result.addAll(m.getAllMessages());
                        } else {
                            try {
                                result.add(m.getMessage(Integer.parseInt(index)));
                            } catch (Exception pe) {
                                throw new NavajoExceptionImpl("Could not parse array index: " + index);
                            }
                        }
                    } else {
                        if (pattern.matcher(name).matches()) {
                            result.add(msgList.get(i));
                        }
                    }
                }

            } catch (Exception re) {
                throw new NavajoExceptionImpl(re.getMessage());
            }
            return result;
        }
    }

    protected final boolean compliesWith(Message m, String expression) {
        return m.getName().startsWith(expression);
    }

    protected final boolean compliesWith(Property p, String expression) {
        return p.getName().startsWith(expression);
    }

    public final Property getProperty(String s) {
        // return (Property) propertyMap.get(s);
        // System.err.println("Getting property: "+s+" my path:
        // "+getFullMessageName());

        if (s.startsWith("/")) {
            return getRootDoc().getProperty(s.substring(1));
        }

        return getPropertyByPath(s);
    }

    public final int getIndex() {
        return myIndex;
    }

    public final void setIndex(int index) {
        myType = Message.MSG_TYPE_ARRAY_ELEMENT;
        myIndex = index;
    }

    private Message getValueMessage(int i) {
        return (Message) getAllMessages().get(i);

    }

    public Message getMessage(int i) {

        if (i >= getChildMessageCount()) {
            // System.err.println("Message index out of range");
        }
        // if (myDefinitionList!=null) {
        // return buildMessageForRow(i);
        // }
        if ( i >= getAllMessages().size() ) {
            return null;
        }
        return (Message) getAllMessages().get(i);
    }

    // Returns an array element
    public Message getMessage(String name, int index) {
        Message m = getMessage(name);
        if (m == null) {
            // System.err.println("No such message: " + name);
            // Thread.dumpStack();
            return null;
        }
        if (!m.getType().equals(Message.MSG_TYPE_ARRAY)) {
            System.err.println("Found a non array message, when querying for an array element");
            return null;
        }
        return m.getMessage(index);
    }

    public final void removeChildMessage(Message msg) {
        messageList.remove(msg);
        messageMap.remove(msg.getName());
    }

    private int currentTotal = -1;

    public int getCurrentTotal() {
        return currentTotal;
    }

    public void setCurrentTotal(int aap) {
        currentTotal = aap;
    }

    public Property getPropertyDefinition(String name) {
    	if ( definitionMessage != null ) {
    		return definitionMessage.getProperty(name);
    	} else {
    		return null;
    	}
    }

    public int getChildMessageCount() {
        return getAllMessages().size();
    }

    public final void addArrayMessage(Message m) {
        if (!MSG_TYPE_ARRAY.equals(getType())) {
            throw new RuntimeException("Adding array element to non-array message");
        }
        m.setName(getName());
        addMessage(m);
    }

    public final Message copy() throws NavajoException {
        Navajo empty = NavajoFactory.getInstance().createNavajo();
        Message result = copy(empty);
        empty.addMessage(result);
        return result;
    }

    public final Message copy(Navajo n) {
        BaseMessageImpl cp = (BaseMessageImpl) NavajoFactory.getInstance().createMessage(n, getName());
        cp.setRootDoc(n);
        ArrayList myMsg = getAllMessages();
        cp.setEndIndex(getEndIndex());
        cp.setStartIndex(getStartIndex());
        cp.setIndex(getIndex());
        cp.setMode(getMode());
        cp.setType(getType());

        for (int i = 0; i < myMsg.size(); i++) {
            BaseMessageImpl current = (BaseMessageImpl) myMsg.get(i);
            if (current == this) {
                throw new RuntimeException("CYCLIC Message copy found!");
            }

            Message cc = current.copy(n);
            cp.addMessage(cc);
        }
        ArrayList myProp = getAllProperties();
        for (int i = 0; i < myProp.size(); i++) {
            BasePropertyImpl current = (BasePropertyImpl) myProp.get(i);
            Property cc = current.copy(n);
            cp.addProperty(cc);
        }
        return cp;
    }

    public final void prune() {
        ArrayList myMsg = getAllMessages();
        for (int i = 0; i < myMsg.size(); i++) {
            BaseMessageImpl current = (BaseMessageImpl) myMsg.get(i);
            current.prune();
        }
        ArrayList myProp = getAllProperties();
        for (int i = 0; i < myProp.size(); i++) {
            BasePropertyImpl current = (BasePropertyImpl) myProp.get(i);
            current.prune();
        }
    }

    public final void setMessageMap(MessageMappable m) {
        myStringMap = m;
        ArrayList myMsg = getAllMessages();
        for (int i = 0; i < myMsg.size(); i++) {
            BaseMessageImpl current = (BaseMessageImpl) myMsg.get(i);
            if (current != null) {
                current.setMessageMap(m);
            }
        }
    }

    public final MessageMappable getMessageMap() {
        return myStringMap;
    }

    public final String toString() {
        // return super.toString();
        if (myStringMap != null) {
            return myStringMap.getMessageLabel(this);
        }
        return getName();
    }

    public final void setParent(Message m) {
        if (m == null) {
            // System.err.println("==========================\nDeleting
            // parent.... Bad idea\n\n\n");
            return;
        }
        // System.err.println("Setting parent to "+m.getName());
        // System.err.println("Full: "+m.getFullMessageName()+" type:
        // "+m.getType());
        myParent = (BaseMessageImpl) m;
    }

    // public final Message getParent() {
    // return myParent;
    // }

    public Message getByPath(String path) {
        /** @todo ARRAY SUPPORT */
        if (path.startsWith("../")) {
            Message m = getParentMessage().getMessage(path.substring(3));
        }

        if (path.startsWith("/")) {
            path = path.substring(1);
        }

        int slash = path.indexOf("/");
        if (slash < 0) {
            return getMessage(path);
        } else {
            // System.err.println("Index: " + slash + " of: " + path);
            String messagename = path.substring(0, slash);
            // System.err.println("Sumbessage: " + messagename);
            Message m = getMessage(messagename);
            if (m != null) {
                return m.getMessage(path.substring(slash + 1));
            } else {
                return null;
            }
        }
    }

    public final Property getPropertyByPath(String pth) {
        /** @todo ARRAY SUPPORT */
        // System.err.println("PARSING PATH: "+pth);
        String path = null;
        if (pth.startsWith("/")) {
            // System.err.println("PATH STARTED WITH SLASH. STRIPPING /");
            path = pth.substring(1);
        } else {
            path = pth;
        }
        if (path.startsWith("..")) {
            if (getParentMessage() == null) {
                return null;
            }
            return getParentMessage().getProperty(path.substring(3));
        }

        int slash = path.indexOf("/");
        if (slash < 0) {
            // System.err.println("No slashes left. Getting property: "+path);

        	if (propertyList.size()!=propertyMap.size()) {
				System.err.println("Warning: Propertymap sizE: "+propertyMap.size()+" listsize: "+propertyList.size());
			}
        	
            Property pp =  (Property) propertyMap.get(path);
            if (pp==null) {
                // check for definition messages
                Message arrayP = getArrayParentMessage();
                if (arrayP!=null) {
                    Message def = arrayP.getDefinitionMessage();
                    if (def!=null){
                        System.err.println("FOUND DEFINITIONMESSAGE: "+def.getFullMessageName());
                    }
                    if (def!=null && def.getProperty(path) != null) {
                    	Property res = def.getProperty(path).copy(getRootDoc());
                    	addProperty(res);
                    	System.err.println("Duplicated property: "+res.getName());
                    	return res;
                    }
                }
            }
            return pp;
            // return getProperty(path);
        } else {
            String msgname = path.substring(0, slash);
            String propname = path.substring(slash, path.length());
            // System.err.println("Getting message: "+msgname);
            BaseMessageImpl ms = (BaseMessageImpl) getMessage(msgname);
            if (ms != null) {
                return ms.getPropertyByPath(propname);
            } else {
                // Thread.dumpStack();
            }

            // System.err.println("SubProperty: "+propname);
            return null;
        }
    }

    public final String getPath() {
        if (myParent != null) {
            if (myParent.getType().equals(Message.MSG_TYPE_ARRAY)) {
                return myParent.getPath() + "@" + getIndex();
            }
            return myParent.getPath() + "/" + getName();
        } else {
            return getName();
        }
    }

    public int getStartIndex() {
        return startIndex;
    }

    public int getEndIndex() {
        return endIndex;
    }

    public final void setStartIndex(int i) {
        startIndex = i;
    }

    public void setEndIndex(int i) {
        endIndex = i;
    }

    /**
     * UPDATE: This is
     * @todo Beware, the functionality is different than the jaxpimpl version If
     *       this is an array element, it will return the array. The jaxpimpl
     *       will never return an array msg, it will return its parent
     */
public final Message getParentMessage() {
      if (myParent==null) {
        return null;
    }
      if(Message.MSG_TYPE_ARRAY.equals(myParent.getType())) {
          return myParent.getParentMessage();
      }
    return myParent;
  }
    /**
     * Added this method to bridge the difference between nano and jaxp
     */
    public final Message getArrayParentMessage() {
        return myParent;
    }

    public final Message addElement(Message m) {
        if (!getType().equals(Message.MSG_TYPE_ARRAY)) {
            throw new IllegalArgumentException("Can not add element to non-array type message!");
        }
        if ( !m.getType().equals(MSG_TYPE_DEFINITION )) {
        	m.setIndex(getArraySize());
        }
        addMessage(m);
        return m;
    }

    public int getArraySize() {
        return messageList.size();
    }

    public void setArraySize(int i) {
        throw new UnsupportedOperationException("Dont know what this method should do.");
    }

    public final boolean isArrayMessage() {
        return MSG_TYPE_ARRAY.equals(getType());
    }

    public final String getFullMessageName() {
        return getPath();
    }

    public final Property getPathProperty(String path) {
        return getPropertyByPath(path);
    }

    public final void removeMessage(Message msg) {
        removeChildMessage(msg);
    }

    public final void removeMessage(String msg) {
        removeChildMessage(getMessage(msg));
    }

    public final void removeProperty(Property p) {
        propertyList.remove(p);
        propertyMap.remove(p.getName());
        /**
         * @todo Implement this com.dexels.navajo.document.Message abstract
         *       method
         */
    }

    public final void setLazyRemaining(int c) {
        /**
         * @todo Implement this com.dexels.navajo.document.Message abstract
         *       method
         */
    }

    public final void setLazyTotal(int c) {
        /**
         * @todo Implement this com.dexels.navajo.document.Message abstract
         *       method
         */
    }

    public final boolean contains(String name) {
        boolean b = getMessage(name) != null;
        if (!b) {
            return getProperty(name) != null;
        }
        return b;
    }

    public static void main(String[] args) throws Exception {
        System.setProperty("com.dexels.navajo.DocumentImplementation", "com.dexels.navajo.document.nanoimpl.NavajoFactoryImpl");

        Navajo n = NavajoFactory.getInstance().createNavajo();
        Message array = NavajoFactory.getInstance().createMessage(n, "Array", Message.MSG_TYPE_ARRAY);
        for (int i = 0; i < 5; i++) {
            Message sub = NavajoFactory.getInstance().createMessage(n, "Array");
            array.addMessage(sub);
            Property p = NavajoFactory.getInstance().createProperty(n, "Apenoot", "string", "i=" + i, 10, "", "in");
            sub.addProperty(p);
        }
        n.addMessage(array);
        ArrayList p = n.getProperties("/Arr[aA][yY]@0/Apenoot");
        System.err.println("p = " + ((Property) p.get(0)).getValue());
    }

    public boolean isEqual(Message o) {
        return isEqual(o, "");
    }

    public boolean isEqual(Message o, String skipProperties) {

        System.err.println("in Message.isEqual(), my name is " + getName() + ", other is " + getName() + ", skipProperties = " + skipProperties);
        Message other = (Message) o;
        if (!other.getName().equals(this.getName())) {
            return false;
        }
        // Check sub message structure.
        ArrayList allOther = other.getAllMessages();
        ArrayList allMe = this.getAllMessages();
        System.err.println("my msg size is " + allMe.size() + ", other msg size is " + allOther.size());
        if (allOther.size() != allMe.size()) {
            return false;
        }
        for (int i = 0; i < allOther.size(); i++) {
            Message otherMsg = (Message) allOther.get(i);
            boolean match = false;
            for (int j = 0; j < allMe.size(); j++) {
                Message myMsg = (Message) allMe.get(j);
                if (myMsg.isEqual(otherMsg, skipProperties)) {
                    match = true;
                    j = allMe.size() + 1;
                }
            }
            if (!match) {
                return false;
            }
        }
        // Check property structure.
        ArrayList allOtherProps = other.getAllProperties();
        ArrayList allMyProps = this.getAllProperties();
        if (allOtherProps.size() != allMyProps.size()) {
            return false;
        }
        
        for (int i = 0; i < allOtherProps.size(); i++) {
            Property otherProp = (Property) allOtherProps.get(i);
            boolean match = false;
            // Check whether property name exists in skipProperties list.
            if (skipProperties.indexOf(otherProp.getName()) != -1) {
                match = true;
            } else {
                for (int j = 0; j < allMyProps.size(); j++) {
                    Property myProp = (Property) allMyProps.get(j);
                    if (myProp.isEqual(otherProp)) {
                        match = true;
                        j = allMyProps.size() + 1;
                    }
                }
            }
            
            if (!match) {
//            	System.err.println("Is NOT equal property");
//            	System.err.println("other: " + otherProp.getName() + ", " + otherProp.getType() + ", >" + otherProp.getValue() + "<");
//                if ( this.getProperty(otherProp.getName()) != null ) {
//                	Property p = this.getProperty(otherProp.getName());
//                	System.err.println("my: " + p.getName() + ", " + p.getType() + ", >" + p.getValue() + "<");
//                    
//                } else {
//                	System.err.println("I do not have this property");
//                }
            	return false;
            }
        }
        return true;
    }

    private Message createEmptyMessage() {
        return null;
    }

//    public Message addMessage(int index) {
//        if (getType().equals(Message.MSG_TYPE_ARRAY)) {
//            throw new IllegalStateException("Can only add empty messages to arraymessages.");
//        }
//        if (definitionMessage == null) {
//            throw new IllegalStateException("Can only add empty messages when definitions present.");
//        }
//        Message newChild = createEmptyMessage();
//        try {
//            addMessage(newChild, index);
//        } catch (NavajoException ex) {
//            ex.printStackTrace();
//        }
//        return newChild;
//    }

    /**
     * Add empty message at the end
     */
//    public Message addMessage() {
//        return addMessage(getArraySize());
//    }

    public Message getDefinitionMessage() {
        return definitionMessage;
    }

    public void setDefinitionMessage(Message m) {
    	this.definitionMessage = (BaseMessageImpl) m;
//  	Definition message stuff.
    	if ( m == null ) {
    		return;
    	}
    	
//  	System.err.println("Ok, searching");
    	ArrayList myDefinitionList = m.getAllProperties();
  	System.err.println("# of properties found in definition: "+myDefinitionList.size());
    	for (int j = 0; j < myDefinitionList.size(); j++) {
    		Property pq = (Property)myDefinitionList.get(j);
    		String pname = pq.getName();
    		if (getProperty(pname)==null) {
    			//System.err.println("\n\nCreating prop: "+pname+" ::: "+getIndex());
    			Property pi = (Property)pq.copy(getRootDoc());
    			addProperty(pi);
    			//System.err.println("pi::::::::::: "+pi.toXml(null).toString());
    		}
    	}
    }
    
    public Map getAttributes() {
        Map m = new HashMap();
        m.put("name", myName);
        if (myType != null) {
            m.put("type", myType);
            if (Message.MSG_TYPE_ARRAY_ELEMENT.equals(myType)) {
                m.put("index", "" + myIndex);
            }
        }
        return m;
    }

    public List getChildren() {
        ArrayList al = new ArrayList(propertyList);
        if ( getDefinitionMessage() != null ) {
        	al.add(getDefinitionMessage());
        }
        al.addAll(messageList);
        return al;
    }

    public Object getRef() {
        throw new UnsupportedOperationException("getRef not possible on base type. Override it if you need it");
    }

    public Object clone(String newName) {
        throw new UnsupportedOperationException("Can not clone properties (yet)");
    }

    public String getTagName() {
        return Message.MSG_DEFINITION;
    }

    

    public int getChildCount() {
      return getAllProperties().size() + getAllMessages().size();
    }

    public TreeNode getChildAt(int childIndex) {
      if (childIndex >= getAllProperties().size()) {
        return (TreeNode) getAllMessages().get(childIndex -
                                               getAllProperties().size());
      }
      else {
        return (TreeNode) getAllProperties().get(childIndex);
      }

    }

    public Enumeration children() {
      Vector v = new Vector(getAllProperties());
      v.addAll(getAllMessages());
      return v.elements();
    }

    public int getIndex(TreeNode t) {
      for (int i = 0; i < getAllProperties().size(); i++) {
        if (getAllProperties().get(i) == t) {
          return i;
        }
      }
      for (int i = 0; i < getAllMessages().size(); i++) {
        if (getAllMessages().get(i) == t) {
          return i;
        }
      }
      return 0;
    }

    public boolean isLeaf() {
      return messageList.size() == 0;
    }

    public boolean getAllowsChildren() {
      return true;
    }

    public TreeNode getParent() {
      return (TreeNode) getArrayParentMessage();
    }

}
