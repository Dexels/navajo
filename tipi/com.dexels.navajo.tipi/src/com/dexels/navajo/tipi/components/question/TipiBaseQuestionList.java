/*
 * Created on Feb 16, 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.tipi.components.question;

// import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.Selection;
import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiComponentMethod;
import com.dexels.navajo.tipi.TipiDataComponent;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.actions.TipiInstantiateTipi;
import com.dexels.navajo.tipi.components.core.TipiDataComponentImpl;
import com.dexels.navajo.tipi.internal.TipiEvent;

/**
 * @author frank
 * 
 */
public abstract class TipiBaseQuestionList extends TipiDataComponentImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6411968075446267733L;
	protected String messagePath = null;
	protected String questionDefinitionName = null;
	protected String questionGroupDefinitionName = null;

	protected final List<TipiBaseQuestionGroup> myGroups = new ArrayList<TipiBaseQuestionGroup>();
	protected final Set<TipiBaseQuestionGroup> myValidGroups = new HashSet<TipiBaseQuestionGroup>();
	private String subQuestionPath = null;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiBaseQuestionList.class);
	
	protected abstract Object getGroupConstraints(Message groupMessage);

	@Override
	public void setComponentValue(String name, Object object) {
		if (name.equals("messagePath")) {
			messagePath = (String) object;
		}
		// if (name.equals("groupMode")) {
		// groupMode = (String) object;
		// }
		//
		if (name.equals("questionDefinitionName")) {
			questionDefinitionName = (String) object;
		}
		if (name.equals("questionGroupDefinitionName")) {
			questionGroupDefinitionName = (String) object;
		}
		if (name.equals("subQuestionPath")) {
			subQuestionPath = (String) object;

		}

		super.setComponentValue(name, object);
	}

	@Override
	public Object getComponentValue(String name) {
		if (name.equals("valid")) {
			return new Boolean(isValid());
		}
		return super.getComponentValue(name);
	}

	public boolean isValid() {
		for (int i = 0; i < getChildCount(); i++) {
			TipiDataComponent tc = (TipiDataComponent) getTipiComponent(i);
			Boolean b = (Boolean) tc.getValue("valid");
			if (!b.booleanValue()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * @param b  
	 */
	public void setValid(boolean b) {
		// Map m = new HashMap();
		// m.put("valid",new Boolean(b));
		// try {
		// performTipiEvent("onValidationChanged", m, false);
		// }
		// catch (TipiException ex) {
		// logger.error("Error: ",ex);
		// }
	}

	@Override
	protected void performComponentMethod(final String name,
			final TipiComponentMethod compMeth, TipiEvent event)
			throws TipiBreakException {
		if ("flatten".equals(name)) {
			String serviceName = (String) compMeth.getEvaluatedParameter(
					"serviceName", event).value;
			Operand serviceOperand = compMeth.getEvaluatedParameter(
					"serviceUrl", event);
			Operand usernameOperand = compMeth.getEvaluatedParameter(
					"username", event);
			Operand passwordOperand = compMeth.getEvaluatedParameter(
					"password", event);
			Operand pincodeOperand = compMeth.getEvaluatedParameter("pincode",
					event);
			Operand keystoreOperand = compMeth.getEvaluatedParameter(
					"keystore", event);
			Operand keypassOperand = compMeth.getEvaluatedParameter("keypass",
					event);
			Operand isFinalOperand = compMeth.getEvaluatedParameter("final",
					event);
			String serviceUrl = null;
			String username = null;
			String password = null;
			String pincode = null;
			String keystore = null;
			String keypass = null;
			boolean isFinal = false;
			if (serviceOperand != null) {
				serviceUrl = (String) serviceOperand.value;
			}
			if (usernameOperand != null) {
				username = (String) usernameOperand.value;
			}
			if (passwordOperand != null) {
				password = (String) passwordOperand.value;
			}
			if (pincodeOperand != null) {
				pincode = (String) pincodeOperand.value;
			}
			if (keystoreOperand != null) {
				keystore = (String) keystoreOperand.value;
			}
			if (keypassOperand != null) {
				keypass = (String) keypassOperand.value;
			}
			if (isFinalOperand != null) {
				isFinal = ((Boolean) isFinalOperand.value).booleanValue();
			}

			try {
				flatten(serviceName, serviceUrl, username, password, pincode,
						keystore, keypass, isFinal);
			} catch (NavajoException ex) {
				logger.error("Error performing flattening method: ",ex);
			}
		}
		super.performComponentMethod(name, compMeth, event);
	}

	@Deprecated
	public void flatten(String serviceName, String server, String username,
			String password, String pincode, String keystore, String keypass,
			boolean isFinal) throws NavajoException, TipiBreakException {
		Navajo input = getNearestNavajo();
		Message questionList = input.getMessage("QuestionList@0");
		Navajo n = NavajoFactory.getInstance().createNavajo();
		Message aap = input.getMessage("ObjectForm");
		if (aap == null) {
			// hmm no aap.
			aap = input.getMessage("FormData");
		}
		Message m = null;
		if (aap != null) {
			m = aap.copy(n);
			n.addMessage(m);
		} else {
			throw NavajoFactory.getInstance().createNavajoException(
					"Error: No ObjectForm or FromData message found.");
		}
		m.getProperty("Status").setAnyValue(isFinal ? "FINAL" : "CHANGED");

		Message formData = input.getMessage("FormData");
		if (formData != null) {
			Message m2 = formData.copy(n);
			n.addMessage(m2);
		}

		Message sendForm = input.getMessage("SendForm");
		if (sendForm != null) {
			Message m2 = sendForm.copy(n);
			n.addMessage(m2);
		}

		Message clubMsg = input.getMessage("Club");
		if (clubMsg != null) {
			n.addMessage(clubMsg.copy(n));
		}
		Property pin = NavajoFactory.getInstance().createProperty(n, "Pincode",
				Property.STRING_PROPERTY, pincode, 16, "", Property.DIR_IN);
		m.addProperty(pin);

		Property date = NavajoFactory.getInstance()
				.createProperty(n, "TimeStamp", Property.DATE_PROPERTY, "", 0,
						"", Property.DIR_IN);
		date.setValue(new Date());
		m.addProperty(date);

		Message answers = NavajoFactory.getInstance().createMessage(n,
				"Answers", Message.MSG_TYPE_ARRAY);
		n.addMessage(answers);
		ArrayList<Message> questionGroups = questionList.getMessage("Group")
				.getAllMessages();
		for (int i = 0; i < questionGroups.size(); i++) {
			Message group = questionGroups.get(i);
			flattenGroup(group, answers);
		}
		logger.info("Flatten complete: Final input for output: ");
		n.write(System.err);
		myContext.performTipiMethod(this, n, "*", serviceName, true, null, -1,
				server, username, password, keystore, keypass);
	}

	private final void flattenGroup(Message groupMessage, Message answerMessage)
			throws NavajoException {
		Property id = groupMessage.getProperty("Id");
		Message questions = groupMessage.getMessage("Question");
		if (questions == null) {
			return;
		}
		for (int i = 0; i < questions.getArraySize(); i++) {
			Message current = questions.getMessage(i);
			flattenQuestion(current, answerMessage, "/" + id.getValue());
		}
	}

	private final void flattenQuestion(Message questionMessage,
			Message answerMessage, String prefixString) throws NavajoException {
		Property value = questionMessage.getProperty("Value");
		Property id = questionMessage.getProperty("Id");
		Message answer = createAnswerMessage(answerMessage.getRootDoc(),
				prefixString + "/" + id.getValue(), value);
		answerMessage.addMessage(answer);
		Message subQuestions = questionMessage.getMessage("Question");
		if (subQuestions == null) {
			return;
		}
		for (int i = 0; i < subQuestions.getArraySize(); i++) {
			Message current = subQuestions.getMessage(i);
			flattenQuestion(current, answerMessage,
					prefixString + "/" + id.getValue());
		}
	}

	private Message createAnswerMessage(Navajo answerDoc, String id,
			Property value) throws NavajoException {
		Property newValue = NavajoFactory.getInstance()
				.createProperty(answerDoc, "Value", value.getType(),
						value.getValue(), value.getLength(),
						value.getDescription(), value.getDirection());
		newValue.setCardinality(value.getCardinality());
		Message answerMessage = NavajoFactory.getInstance().createMessage(
				answerDoc, "Answer");
		Property idProp = NavajoFactory.getInstance().createProperty(answerDoc,
				"Id", Property.STRING_PROPERTY, id, 0, "", Property.DIR_IN);
		List<Selection> al = value.getAllSelections();
		for (int i = 0; i < al.size(); i++) {
			Selection current = al.get(i);
			Selection s = NavajoFactory.getInstance().createSelection(
					answerDoc, current.getName(), current.getValue(),
					current.isSelected());
			newValue.addSelection(s);
		}
		answerMessage.addProperty(idProp);
		answerMessage.addProperty(newValue);
		return answerMessage;
	}

	
	/**
	 * I think super.loadData should be called... Giving it a shot...
	 */
	@Override
	public void loadData(final Navajo n, final String method)
			throws TipiException {
		super.loadData(n, method);
		final TipiBaseQuestionList me = this;
		myGroups.clear();
		myValidGroups.clear();
		removeAllChildren();
		runSyncInEventThread(new Runnable() {

			@Override
			public void run() {
				myNavajo = n;
				Message m = n.getMessage(messagePath);
				if (m == null) {
					return;
				}
				if (!m.getType().equals(Message.MSG_TYPE_ARRAY)) {
					return;
				}
				for (int i = 0; i < m.getArraySize(); i++) {
					Message current = m.getMessage(i);
					String id = current.getProperty("Id").getValue();
					String questionGroupDefinitionName = TipiBaseQuestionList.this.questionGroupDefinitionName;
					Property questionGroupProperty = current.getProperty("QuestionGroup");
					if(questionGroupProperty!=null) {
						if(questionGroupProperty.getValue()!=null) {
							questionGroupDefinitionName = questionGroupProperty.getValue();
						}
					}
					
					String questionDefinitionName = TipiBaseQuestionList.this.questionDefinitionName;
					Property questionLayoutProperty = current.getProperty("QuestionLayout");
					if(questionLayoutProperty!=null) {
						if(questionLayoutProperty.getValue()!=null) {
							questionDefinitionName = questionLayoutProperty.getValue();
						}
					}
					
					TipiDataComponent tc = null;
					try {
						tc = (TipiDataComponent) TipiInstantiateTipi
								.instantiateByDefinition(me, false, id,
										questionGroupDefinitionName,
										getGroupConstraints(current), null);
						tc.setValue("messagePath", current.getFullMessageName());
						tc.setValue("questionDefinitionName",
								questionDefinitionName);
						tc.setValue("questionGroupDefinitionName",
								questionGroupDefinitionName);
						tc.setValue("subQuestionPath", subQuestionPath);
						if (tc instanceof TipiBaseQuestionGroup) {
							TipiBaseQuestionGroup tqg = (TipiBaseQuestionGroup) tc;
							tqg.setQuestionList(me);
							myGroups.add(tqg);
							// subsequent changes will be caught. Assume valid
							myValidGroups.add(tqg);
						} else {
							logger.error("This is _not_ good");
						}
						tc.loadData(n, method);
					} catch (TipiException e) {
						logger.error("Error: ",e);
					} catch (TipiBreakException e) {
						logger.debug("Error: ",e);
					}
				}
				// SwingTipiContext.debugSwingTree((Component)getContainer(),
				// 0);
			}
		});
	}

	public void setGroupValid(boolean valid, TipiBaseQuestionGroup group) {
		boolean oldvalid = myValidGroups.containsAll(myGroups);
		if (valid) {
			if (!myValidGroups.contains(group)) {
				myValidGroups.add(group);
			}
		} else {
			if (myValidGroups.contains(group)) {
				myValidGroups.remove(group);
			}
		}
		boolean newvalid = myValidGroups.containsAll(myGroups);
		if (oldvalid != newvalid) {
			updateValidity();
		}

	}

	private void updateValidity() {
		boolean valid = myValidGroups.containsAll(myGroups);
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("valid", new Boolean(valid));
		try {
			performTipiEvent("onValidationChanged", m, true);
		} catch (TipiException e) {
			logger.error("Error: ",e);
		}
	}

	public void updateQuestionList() {
		for (Iterator<TipiBaseQuestionGroup> itt = myGroups.iterator(); itt
				.hasNext();) {
			TipiBaseQuestionGroup element = itt.next();
			element.updateQuestions();
		}
	}
}
