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

import java.beans.*;
import java.util.*;
import java.util.regex.*;

import javax.swing.tree.*;

import com.dexels.navajo.document.*;


public  class BaseMessageImpl extends BaseNode implements Message, Comparable<Message> {
    protected String myName = "";

    private String myType = "";

    private String myMode = "";

    private String myCondition = "";

    private int myIndex = -1;

    protected TreeMap<String,Property> propertyMap = null;

    protected ArrayList<Property> propertyList = null;

    protected TreeMap<String,Message> messageMap = null;

    private ArrayList<Message> messageList = null;

    private BaseMessageImpl myParent = null;

    private MessageMappable myStringMap = null;

    private int startIndex = -1;

    private int endIndex = -1;

    
    private String orderBy = "";

	private List<PropertyChangeListener> myPropertyDataListeners;

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

    public final String getType() {
        return myType;
    }
    
    public final void setType(String type) {
        myType = type;
    }
    
    public final String getOrderBy(){
      return orderBy;
    }
    
    public final void setOrderBy(String order){
      orderBy = order;
    }

    public final String getName() {
        return myName;
    }

    public final void setCondition(String condition) {
        myCondition = condition;
    }

    /**
     * For internal use only, sets the initial message name.
     * 
     * @param name
     */
    protected final void setNameInitially(String name) {
    	myName = name;
    }
    
    /**
     * Changes the name of a message.
     * 
     * @param name
     */
    public final void setName(String name) {
    	// Fix the messageMap collection to account for the changed message name.
    	if ( getParentMessage() != null ) {
    		if ( ((BaseMessageImpl) getParentMessage()).messageMap != null ) {
    			((BaseMessageImpl) getParentMessage()).messageMap.remove(myName);
    		}
    		if ( ((BaseMessageImpl) getParentMessage()).messageMap != null ) {
    			((BaseMessageImpl) getParentMessage()).messageMap.put(name, this);
    		}
    	}
    	myName = name;
    }

    public final void setMode(String mode) {
        myMode = mode;
    }

    public final String getMode() {
        return myMode;
    }

    public final void clearAllSelections() throws NavajoException {
    	if ( propertyList != null ) {

    		for (int i = 0; i < propertyList.size(); i++) {
    			Property p = propertyList.get(i);
    			if ( p.getType().equals(Property.SELECTION_PROPERTY)) {
    				p.clearSelections();
    			}
    		}
    	}

    	if (messageList!=null) {
    		for (int i = 0; i < messageList.size(); i++) {
    			BaseMessageImpl p = (BaseMessageImpl) messageList.get(i);
    			p.clearAllSelections();
    		}
    	}

    }

    public Message addMessage(Message m) {
        if (m == null) {
            return null;
        }
        // Do not add messages with mode "ignore".
        if (m.getMode().endsWith(Message.MSG_MODE_IGNORE)) {
            return null;
        }
        if (this.getType().equals(Message.MSG_TYPE_ARRAY)) {
            return addMessage(m, false);
        } else {
            return addMessage(m, true);
        }
    }

    public final Message addMessage(Message m, boolean overwrite) {
    	if (messageList==null) {
			messageList = new ArrayList<Message>();
		}
    	if (messageMap==null) {
    		messageMap = new TreeMap<String,Message>();
		}
    	
    	m.setParent(this);
  	 
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
          ((BaseMessageImpl) m).setNameInitially(getName());
      }
      messageList.add(m);

      return m;
  }


    public final void addMessage(Message m, int index) throws NavajoException {
       	if (messageList==null) {
			messageList = new ArrayList<Message>();
		}
    	if (messageMap==null) {
    		messageMap = new TreeMap<String,Message>();
		}
    	
        if (!getType().equals(Message.MSG_TYPE_ARRAY)) {
            throw new NavajoExceptionImpl("Can not add to with index to messages, if it is not an array message. Is that clear?");
        }
        
        messageList.add(index, m);
        messageMap.put(m.getName(), m);
        m.setIndex(index);
        ((BaseMessageImpl) m).setNameInitially(getName());
        m.setParent(this);
    }

    public ArrayList<Message> getAllMessages() {
    	if (messageList==null) {
			return new ArrayList<Message>();
		}
        return new ArrayList<Message>(messageList);
    }

    public final void addProperty(Property q) {
    	if(q==null) {
    		throw new NullPointerException("Message: can not add null property");
    	}
    	if (propertyMap==null) {
			propertyMap = new TreeMap<String,Property>();
		}
    	if (propertyList==null) {
    		propertyList = new ArrayList<Property>();
		}
    	
        BasePropertyImpl p = (BasePropertyImpl) q;
        if (propertyMap.get(p.getName()) == null) {
            propertyList.add(q);
            propertyMap.put(p.getName(), p);
            p.setParent(this);
        } else {
            this.removeProperty(propertyMap.get(p.getName()));
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

    public final ArrayList<Property> getAllProperties() {
        return getTotalPropertyList();
    }
    
    private final ArrayList<Property> getTotalPropertyList() {
    	if (propertyList==null) {
			propertyList = new ArrayList<Property>();
		}
    	if (propertyMap==null) {
			propertyMap = new TreeMap<String,Property>();
		}
    	if (getArrayParentMessage()==null) {
			return propertyList;
		}
    	if (getArrayParentMessage().getDefinitionMessage()==null) {
    		return propertyList;
    	}
    	ArrayList<Property> resList = new ArrayList<Property>();
//    	resList.addAll(propertyList);
    	for (Iterator<Property> iter = getArrayParentMessage().getDefinitionMessage().getAllProperties().iterator(); iter.hasNext();) {
			Property element = iter.next();
			if (element!=null) {
				Property local = propertyMap.get(element.getName());
				if (local!=null) {
					mergeProperty(local,element);
				}
				resList.add(getProperty(element.getName()));
			}
		}
    	for (Iterator<Property> iter = propertyList.iterator(); iter.hasNext();) {
			Property element = iter.next();
			if (!resList.contains(element)) {
				resList.add(element);
			}
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

	public final ArrayList<Property> getProperties(String regularExpression) throws NavajoException {

        if (regularExpression.startsWith(Navajo.PARENT_MESSAGE + Navajo.MESSAGE_SEPARATOR)) {
            regularExpression = regularExpression.substring((Navajo.PARENT_MESSAGE + Navajo.MESSAGE_SEPARATOR).length());
            return getParentMessage().getProperties(regularExpression);
        } else if (regularExpression.startsWith(Navajo.MESSAGE_SEPARATOR)) { // We
                                                                                // have
                                                                                // an
                                                                                // absolute
                                                                                // offset
            Navajo d = getRootDoc();
            return d.getProperties(regularExpression.substring(1));
        } else {
            ArrayList<Property> props = new ArrayList<Property>();
            ArrayList<Message> messages = null;
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
                messages = new ArrayList<Message>();
                messages.add(this);
            }

            Pattern pattern = Pattern.compile(realProperty);
            for (int i = 0; i < messages.size(); i++) {
                message = messages.get(i);
                ArrayList<Property> allProps = message.getAllProperties();
                try {
                    for (int j = 0; j < allProps.size(); j++) {
                        String name = allProps.get(j).getName();
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

    public void refreshExpression() throws NavajoException, ExpressionChangedException {
       
    	if ( messageList != null ) {
    		for (int i = 0; i < messageList.size(); i++) {
    			Message current = messageList.get(i);
    			current.refreshExpression();
    		}
    	}
        for (Property current : getAllProperties()) {
            current.refreshExpression();		
		}

    }

    public Message getMessage(String name) {
        if (name.startsWith("../")) {
        	if ( getParentMessage() == null ) {
        		return null;
        	} else {
        		return getParentMessage().getMessage(name.substring(3));
        	}
        }

        if (name.startsWith("/")) {
            return getRootDoc().getMessage(name.substring(1));
        }
        if (name.indexOf("/") >= 0) {
            return getByPath(name);
        }

        if (name.indexOf("@") >= 0) {
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
        if (messageMap==null) {
			return null;
		}
        return messageMap.get(name);
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
    public ArrayList<Message> getMessages(String regularExpression) throws NavajoException {

        //ArrayList messages = new ArrayList();
        ArrayList<Message> sub = null;
        ArrayList<Message> sub2 = null;

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
                	ArrayList<Message> messages = new ArrayList<Message>();
                    for (int i = 0; i < sub.size(); i++) {
                        m = sub.get(i);
                        sub2 = m.getMessages(msgName);
                        messages.addAll(sub2);
                    }
                    sub = messages;
                }
            }
            return sub;
        } else {
           
            ArrayList<Message> result = new ArrayList<Message>();
            try {
                String index = null;

                if (regularExpression.indexOf("@") != -1) {
                    StringTokenizer arEl = new StringTokenizer(regularExpression, "@");
                    regularExpression = arEl.nextToken();
                    index = arEl.nextToken();

                }
                Pattern pattern = Pattern.compile(regularExpression);
                if ( messageList != null ) {
                	for (int i = 0; i < messageList.size(); i++) {
                		BaseMessageImpl m = (BaseMessageImpl) messageList.get(i);
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
                				if ( m.messageList != null ) {
                					result.addAll(m.messageList);
                				}
                			} else {
                				try {
                					result.add(m.getMessage(Integer.parseInt(index)));
                				} catch (Exception pe) {
                					throw new NavajoExceptionImpl("Could not parse array index: " + index);
                				}
                			}
                		} else {
                			if (pattern.matcher(name).matches()) {
                				result.add(messageList.get(i));
                			}
                		}
                	}
                }

            } catch (Exception re) {
                throw new NavajoExceptionImpl(re);
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

    public final Message getMessage(int i) {
    	if (messageMap==null || messageList==null) {
			return null;
		}
        if ( i >= messageList.size() ) {
            return null;
        }
        return messageList.get(i);
    }

    // Returns an array element
    public final Message getMessage(String name, int index) {
        Message m = getMessage(name);
        if (m == null) {
             return null;
        }
        if (!m.getType().equals(Message.MSG_TYPE_ARRAY)) {
            System.err.println("Found a non array message, when querying for an array element");
            return null;
        }
        return m.getMessage(index);
    }

    public final void removeChildMessage(Message msg) {
       	if (messageList==null || messageMap== null) {
       		return;
       	}
        messageList.remove(msg);
        messageMap.remove(msg.getName());
    }

    private int currentTotal = -1;

    public final int getCurrentTotal() {
        return currentTotal;
    }

    public final void setCurrentTotal(int aap) {
        currentTotal = aap;
    }

    public Property getPropertyDefinition(String name) {
    	if ( definitionMessage != null ) {
    		return definitionMessage.getProperty(name);
    	} else {
    		return null;
    	}
    }

    public final int getChildMessageCount() {
    	if (messageList==null) {
			return 0;
		}
        return messageList.size();
    }

    public final void addArrayMessage(Message m) {
        if (!MSG_TYPE_ARRAY.equals(getType())) {
            throw new RuntimeException("Adding array element to non-array message");
        }
        ((BaseMessageImpl) m).setNameInitially(getName());
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
       
        cp.setEndIndex(getEndIndex());
        cp.setStartIndex(getStartIndex());
        cp.setIndex(getIndex());
        cp.setMode(getMode());
        cp.setType(getType());

        if ( messageList != null ) {
        	for (int i = 0; i < messageList.size(); i++) {
        		BaseMessageImpl current = (BaseMessageImpl) messageList.get(i);
        		if (current == this) {
        			throw new RuntimeException("CYCLIC Message copy found!");
        		}

        		Message cc = current.copy(n);
        		cp.addMessage(cc);
        	}
        }
        
        for (Property current : getAllProperties()) {
			cp.addProperty(current.copy(n));
		}
     
        return cp;
    }

    public final void prune() {

    	if ( messageList != null ) {
    		for (int i = 0; i < messageList.size(); i++) {
    			BaseMessageImpl current = (BaseMessageImpl) messageList.get(i);
    			current.prune();
    		}
    	}

        for (Property current : getAllProperties()) {
        	((BasePropertyImpl)current).prune();
		}
    }

    public final void setMessageMap(MessageMappable m) {
    	myStringMap = m;

    	if ( messageList != null ) {
    		for (int i = 0; i < messageList.size(); i++) {
    			BaseMessageImpl current = (BaseMessageImpl) messageList.get(i);
    			if (current != null) {
    				current.setMessageMap(m);
    			}
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
        myParent = (BaseMessageImpl) m;
    }

      public final Message getByPath(String path) {
        /** @todo ARRAY SUPPORT */
        if (path.startsWith("../")) {
            Message m = getParentMessage().getMessage(path.substring(3));
            // I THINK! It did not make sense at all
            return m;
        }

        if (path.startsWith("/")) {
            path = path.substring(1);
        }

        int slash = path.indexOf("/");
        if (slash < 0) {
            return getMessage(path);
        } else {
            String messagename = path.substring(0, slash);
            Message m = getMessage(messagename);
            if (m != null) {
                return m.getMessage(path.substring(slash + 1));
            } else {
                return null;
            }
        }
    }

    public final Property getPropertyByPath(String pth) {
        String path = null;
        if (pth.startsWith("/")) {
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
        	if (propertyList==null || propertyMap==null) {
				return null;
			}
        	if (propertyList.size()!=propertyMap.size()) {
				System.err.println("Warning: Propertymap sizE: "+propertyMap.size()+" listsize: "+propertyList.size());
			}
        	
            Property pp =  propertyMap.get(path);
            if (pp==null) {
                // check for definition messages
                Message arrayP = getArrayParentMessage();
                if (arrayP!=null) {
                    Message def = arrayP.getDefinitionMessage();
                    if (def!=null && def.getProperty(path) != null) {
                    	Property res = def.getProperty(path).copy(getRootDoc());
                    	if (def.getType()==null || "".equals(def.getType())) {
							throw new IllegalStateException("DEFINITION PROPERTY FOUND WITHOUT TYPE!");
						}
                    	res.setType(res.getType());
                    	
                    	addProperty(res);
                    	return res;
                    }
                }
            }
            return pp;
            // return getProperty(path);
        } else {
            String msgname = path.substring(0, slash);
            String propname = path.substring(slash, path.length());
            BaseMessageImpl ms = (BaseMessageImpl) getMessage(msgname);
            if (ms != null) {
                return ms.getPropertyByPath(propname);
            } else {
                // Thread.dumpStack();
            }
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

    public final int getStartIndex() {
        return startIndex;
    }

    public final int getEndIndex() {
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

    public final int getArraySize() {
    	if (messageList==null || messageMap==null) {
			return 0;
		}
        return messageList.size();
    }

    public final void setArraySize(int i) {
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
    	if (propertyList!=null) {
            propertyList.remove(p);
		}
    	if (propertyMap!=null) {
            propertyMap.remove(p.getName());
		}
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
        ArrayList<Property> p = n.getProperties("/Arr[aA][yY]@0/Apenoot");
        System.err.println("p = " + p.get(0).getValue());
    }

    public final boolean isEqual(Message o) {
        return isEqual(o, "");
    }

    public final boolean isEqual(Message o, String skipProperties) {
        BaseMessageImpl other = (BaseMessageImpl) o;
        if (!other.getName().equals(this.getName())) {
            return false;
        }
        // Check sub message structure.
        List<Message> allOther = other.messageList;
        //ArrayList allMe = messag;
        if ( allOther != null && messageList == null ) {
        	return false;
        }
        if ( messageList != null && allOther == null ) {
        	return false;
        }
        if (allOther != null && messageList != null && allOther.size() != messageList.size()) {
            return false;
        }
        if ( allOther != null ) {
        	for (int i = 0; i < allOther.size(); i++) {
        		Message otherMsg = allOther.get(i);
        		boolean match = false;
        		for (int j = 0; j < messageList.size(); j++) {
        			Message myMsg = messageList.get(j);
        			if (myMsg.isEqual(otherMsg, skipProperties)) {
        				match = true;
        				j = messageList.size() + 1;
        			}
        		}
        		if (!match) {
        			return false;
        		}
        	}
        }
        // Check property structure.
        ArrayList<Property> allOtherProps = other.getAllProperties();
        ArrayList<Property> allMyProps = this.getAllProperties();
        if (allOtherProps.size() != allMyProps.size()) {
            return false;
        }
        
        for (int i = 0; i < allOtherProps.size(); i++) {
            Property otherProp = allOtherProps.get(i);
            boolean match = false;
            // Check whether property name exists in skipProperties list.
            if (skipProperties.indexOf(otherProp.getName()) != -1) {
                match = true;
            } else {
                for (int j = 0; j < allMyProps.size(); j++) {
                    Property myProp = allMyProps.get(j);
                    if (myProp.isEqual(otherProp)) {
                        match = true;
                        j = allMyProps.size() + 1;
                    }
                }
            }
            
            if (!match) {
            	return false;
            }
        }
        return true;
    }

    private Message createEmptyMessage() {
        return null;
    }


    public final Message getDefinitionMessage() {
        return definitionMessage;
    }

    public final void setDefinitionMessage(Message m) {
    	this.definitionMessage = (BaseMessageImpl) m;
    	if ( m == null ) {
    		return;
    	}
    	ArrayList<Property> myDefinitionList = m.getAllProperties();
    	for (int j = 0; j < myDefinitionList.size(); j++) {
    		Property pq = myDefinitionList.get(j);
    		String pname = pq.getName();
    		if (getProperty(pname)==null) {
    			Property pi = pq.copy(getRootDoc());
    			addProperty(pi);
    		}
    	}
    }
    
    public  final Map<String,String> getAttributes() {
        Map<String,String> m = new HashMap<String,String>();
        m.put("name", myName);
        if(!"".equals(orderBy)){
          m.put("orderby", orderBy);
        }
        if (myType != null) {
            m.put("type", myType);
            if (Message.MSG_TYPE_ARRAY_ELEMENT.equals(myType)) {
                m.put("index", "" + myIndex);
            }
        }
        return m;
    }

    public final List<BaseNode> getChildren() {
    	ArrayList<BaseNode> al = new ArrayList<BaseNode>();
    	if (propertyList==null) {
		  
		} else {
			for (Property p : propertyList) {
				BasePropertyImpl pmi = (BasePropertyImpl)p;
				al.add(pmi);
			}
		}	
    	if ( getDefinitionMessage() != null ) {
        	al.add((BaseMessageImpl)getDefinitionMessage());
        }
        if (messageList!=null) {
            if(!"".equals(orderBy) && getType().equals(Message.MSG_TYPE_ARRAY)){
                Collections.sort(messageList);
              }
            for (Message m : messageList) {
            	al.add((BaseNode) m);
			}
		}
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

    

    public final int getChildCount() {
      return getAllProperties().size() + ( messageList != null ? messageList.size() : 0);
    }

    public final TreeNode getChildAt(int childIndex) {
      if ( messageList != null && childIndex >= getAllProperties().size()) {
        return (TreeNode) messageList.get(childIndex -
                                               getAllProperties().size());
      }
      else {
        return (TreeNode) getAllProperties().get(childIndex);
      }
    }

	@SuppressWarnings("unchecked")
	public final int compareTo(Message m)  {
	  if(m !=null){
		if(getType().equals(Message.MSG_TYPE_ARRAY_ELEMENT)){
		  if(getArrayParentMessage() != null){
			String order = this.getArrayParentMessage().getOrderBy();

			if(!"".equals(order)){
			  
			  // Parse the orderby attribute
			  // Put them in a set
			  StringTokenizer tok = new StringTokenizer(order, ",");
			  List<String> orderValues = new LinkedList<String>();
			  while(tok.hasMoreTokens()){
				String token = tok.nextToken();
				orderValues.add(token.trim());
			  }
			  
			  // while messages are equal and there are more orderValues keep ordering
			  int compare = 0;
			  Iterator<String> it = orderValues.iterator();
			  while(it.hasNext() && compare == 0){
				String oV = it.next();
				// If DESC we flip the direction
				int desc = -1;
				if(oV.indexOf(" ") > 0){
				  String sort = oV.substring(oV.indexOf(" ") + 1);
				  oV = oV.substring(0, oV.indexOf(" "));
				  if("DESC".equals(sort.toUpperCase())){
					desc = 1;
				  }				  
				}
				
				// Check whether oV is a function instead of a property.
				//TODO IS THIS NECESSARY?
				if ( oV.indexOf("(") != -1 ) {
					// It is a function.

					String compareFunction = oV.substring(0, oV.indexOf("("));
					Comparator c = null;
					try {
						Class<? extends Comparator> compareClass = null;
						//System.err.println("Instantiating function " + compareFunction);
						ExpressionEvaluator ee = NavajoFactory.getInstance().getExpressionEvaluator();
						ClassLoader cl = ee.getScriptClassLoader();
						//System.err.println("Classloader is " + cl);
						compareClass = (Class<? extends Comparator>) Class.forName(compareFunction, true, cl);
						c =  compareClass.newInstance();
						compare = c.compare(this, m);	
					} catch (Exception e) {
						e.printStackTrace(System.err);
						compare = 0;
					}
					
				} else {
					// Now we assume oV is an existing property in both messages


					//System.err.println("Getting property compare: '" + oV + "',  descending? " + desc );
					Property myOvProp = getProperty(oV);
					if ( myOvProp == null ) {
						System.err.println("WARNING: error while sorting message. Could not sort property named: " + oV);
						return 0;
					}
					Property compOvProp = m.getProperty(oV);
					compare = desc * compOvProp.compareTo(myOvProp);
				}
				
				//System.err.println("Compared value: " + compare);
			  }
			  return compare;			  
			}
		  }
		}
	  }
	  return 0;
	}
	

	public void firePropertyDataChanged(Property p,Object oldValue, Object newValue) {
//		System.err.println("Message changed: "+getName()+" index: "+getIndex());
		if(getArrayParentMessage()!=null) {
			getArrayParentMessage().firePropertyDataChanged(p,oldValue,newValue);
		} else {
			getRootDoc().firePropertyDataChanged(p, oldValue, newValue);
		}
		if (myPropertyDataListeners != null) {
			for (int i = 0; i < myPropertyDataListeners.size(); i++) {
				PropertyChangeListener c =  myPropertyDataListeners.get(i);
				c.propertyChange(new PropertyChangeEvent(p,"value",oldValue, newValue));
	
//				System.err.println("Alpha: PROPERTY DATA CHANGE Fired: " + oldValue + " - " + newValue);
				// Thread.dumpStack();
			}
		}
	}

	public void addPropertyChangeListener(PropertyChangeListener p) {
		if (myPropertyDataListeners == null) {
			myPropertyDataListeners = new ArrayList<PropertyChangeListener>();
		}
		myPropertyDataListeners.add(p);
		if(myPropertyDataListeners.size()>1) {
			System.err.println("Multiple property listeners detected!" + myPropertyDataListeners.size());
		}
	}

	public void removePropertyChangeListener(PropertyChangeListener p) {
		if (myPropertyDataListeners == null) {
			return;
		}
		myPropertyDataListeners.remove(p);
	}
	
}
