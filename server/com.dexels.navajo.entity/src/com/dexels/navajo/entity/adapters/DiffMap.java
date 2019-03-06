package com.dexels.navajo.entity.adapters;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.Mappable;
import com.dexels.navajo.script.api.MappableException;
import com.dexels.navajo.script.api.UserException;


public class DiffMap implements Mappable {
	
	private String previous;
	private String current;
	private String appendTo;
	private String messageName;
	private List<Object> keys = new ArrayList<>();
	private Access myAccess;

	public void setPrevious(String previous) {
		this.previous = previous;
	}

	public void setCurrent(String current) {
		this.current = current;
	}

	public void setKeys(List<Object> keys) {
		this.keys = keys;
	}
	
	public void setAppendTo(String appendTo) {
		this.appendTo = appendTo;
	}
	
	public void setMessageName(String messageName) {
		this.messageName = messageName;
	}

	@Override
	public void load(Access access) throws MappableException, UserException {
		myAccess = access;
	}
	
	@Override
	public void store() throws MappableException, UserException {
		if (previous == null || current == null || appendTo == null || messageName == null) {
			throw new UserException(-1, "missing required fields: previous, current, appendTo or messageName is null");
		}
		Message previousMessage = getMessage(previous);
		if (previousMessage == null) {
			throw new UserException(-1, "Not found: previous " + previous + " is not found");
		}
		if (!previousMessage.isArrayMessage()) {
			throw new UserException(-1, "Not supported: previous " + previous + " is not an array message");
		}
		Message currentMessage = getMessage(current);
		if (currentMessage == null) {
			throw new UserException(-1, "Not found: current " + current + " is not found");
		}
		if (!currentMessage.isArrayMessage()) {
			throw new UserException(-1, "Not supported: current " + current + " is not an array message");
		}
		Message appendToMessage = getMessage(appendTo);
		if (appendToMessage == null) {
			throw new UserException(-1, "Not found: appendTo " + appendTo + " is not found");
		}
		Message result = NavajoFactory.getInstance().createMessage(myAccess.getOutputDoc(), messageName, Message.MSG_TYPE_ARRAY);
		appendToMessage.addMessage(result);
		for (Message element : previousMessage.getElements()) {
			// make a copy to leave the original untouched
			Message resultElement = element.copy(myAccess.getOutputDoc());
			try {
				String diff = getDiffForMatchingKeys(currentMessage, resultElement);
				if ("".equals(diff)) {
					// nothing
				} else {
					// modify, should take the properties from the new message!
					resultElement.addProperty(NavajoFactory.getInstance().createProperty(myAccess.getOutputDoc(), "Modify", Property.BOOLEAN_PROPERTY, String.valueOf(true), 0, null, Property.DIR_OUT));
					resultElement.addProperty(NavajoFactory.getInstance().createProperty(myAccess.getOutputDoc(), "Diff", Property.STRING_PROPERTY, String.valueOf(diff), 0, null, Property.DIR_OUT));
				}
				result.addElement(resultElement);
			} catch (NoSuchElementException e) {
				// delete
				resultElement.addProperty(NavajoFactory.getInstance().createProperty(myAccess.getOutputDoc(), "Delete", Property.BOOLEAN_PROPERTY, String.valueOf(true), 0, null, Property.DIR_OUT));
			}
		}
		for (Message element : currentMessage.getElements()) {
			Message resultElement = element.copy(myAccess.getOutputDoc());
			try {
				getDiffForMatchingKeys(previousMessage, resultElement);
			} catch (NoSuchElementException e) {
				// insert
				resultElement.addProperty(NavajoFactory.getInstance().createProperty(myAccess.getOutputDoc(), "Insert", Property.BOOLEAN_PROPERTY, String.valueOf(true), 0, null, Property.DIR_OUT));
				result.addElement(resultElement);
			}
		}
	}
	
	@Override
	public void kill() {
	}
	
	private String getDiffForMatchingKeys(Message checkArrayMessage, Message previous) {
		for (Message checkArrayElement : checkArrayMessage.getElements()) {
			boolean allKeysEqual = true;
			for (Object keyObject : keys) {
				String key = keyObject.toString();
				if (checkArrayElement.getProperty(key) == null || previous.getProperty(key) == null || !checkArrayElement.getProperty(key).isEqual(previous.getProperty(key))) {
					allKeysEqual = false;
					break;
				}
			}
			if (allKeysEqual) {
				return diff(previous, checkArrayElement);
			}
 		}
		throw new NoSuchElementException("no message with matching keys found");
	}
	
	private String diff(Message previous, Message current) {
		String diff = "";
		// check all properties
		for (Property previousProperty : previous.getAllProperties()) {
			Property currentProperty = current.getProperty(previousProperty.getName());
			// it can happen that a previous property does not exist for this property
			if (currentProperty == null && previousProperty.getValue() == null) {
				continue;
			}
			if (!previousProperty.isEqual(currentProperty)) {
				diff += previousProperty.getName() + "(" + previousProperty.getValue() + " -> " + (currentProperty == null ? "null" : currentProperty.getValue()) + ");";
				// IMPORTANT: As a side effect set the previous property to the value of the current property
				previousProperty.setAnyValue(currentProperty == null ? null : currentProperty.getTypedValue());
			}
		}
		for (Message previousMessage : previous.getAllMessages()) {
			Message currentMessage = current.getMessage(previousMessage.getName());
			if (currentMessage != null) {
				String messageDiff = diff(previousMessage, currentMessage);
				if (!messageDiff.equals("")) {
					diff += previousMessage.getName() + "(" + messageDiff + "),";
				}
			}
		}
		return diff;
	}
	
	private Message getMessage(String messageName) {
		if (messageName.startsWith("[")) {
			// get from indoc
			return myAccess.getInDoc().getMessage(messageName.substring(1, messageName.length() - 1));
		} else {
			// get from outdoc
			if (messageName.startsWith("/")) {
				if (messageName.equals("/")) {
					return myAccess.getOutputDoc().getRootMessage();
				}
				// absolute
				return myAccess.getOutputDoc().getMessage(messageName.substring(1));
			} else {
				// relative
				return myAccess.getCurrentOutMessage().getMessage(messageName);
			}
		}
	}

}
