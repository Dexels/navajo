package com.dexels.navajo.tipi.components.question;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Operand;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.tipi.TipiDataComponent;
import com.dexels.navajo.tipi.TipiEventListener;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.TipiExecutable;
import com.dexels.navajo.tipi.actions.TipiInstantiateTipi;
import com.dexels.navajo.tipi.components.core.TipiDataComponentImpl;
import com.dexels.navajo.tipi.internal.PropertyComponent;
import com.dexels.navajo.tipi.tipixml.XMLElement;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */
public abstract class TipiBaseQuestion extends TipiDataComponentImpl {

	private static final long serialVersionUID = -5862387049579716030L;
	private String messagePath = null;
	private String questionDefinitionName = null;
	private TipiBaseQuestionGroup questionGroup = null;
	private TipiBaseQuestionList myQuestionList = null;
	private String visibleCondition = null;
	private String validationCondition = null;
	private Message myMessage = null;
	private String myId;
	private String subQuestionPath;

	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiBaseQuestion.class);
	
	public TipiBaseQuestion() {
		super();
	}

	public void setComponentValue(String name, Object object) {
		if (name.equals("messagePath")) {
			messagePath = (String) object;
		}
		if (name.equals("questionDefinitionName")) {
			questionDefinitionName = (String) object;
		}
		if (name.equals("subQuestionPath")) {
			subQuestionPath = (String) object;

		}

		super.setComponentValue(name, object);
	}

	public void setQuestionGroup(TipiBaseQuestionGroup tqg) {
		questionGroup = tqg;
	}

	public void setQuestionList(TipiBaseQuestionList tql) {
		myQuestionList = tql;
	}

	private void recursiveListQuestions(TipiComponent start,
			List<TipiComponent> result) {
		if (start instanceof TipiBaseQuestion) {
			result.add(start);
		}
		for (int i = 0; i < start.getChildCount(); i++) {
			recursiveListQuestions(start.getTipiComponent(i), result);
		}
	}

	/**
	 * @param context  
	 */
	public void loadData(final Navajo n, final TipiContext context) {
		visibleCondition = null;
		validationCondition = null;
		//
		// removeInstantiatedChildren();
		// mySubQuestions.clear();
		Message m = n.getMessage(messagePath);
		myMessage = m;
		if (m == null) {
			return;
		}
		Property titleProperty = m.getProperty("Title");
		if (titleProperty != null) {
			String val = titleProperty.getValue();
			setQuestionBorder(val);
		}
		Property visibleConditionProperty = m.getProperty("VisibleCondition");
		if (visibleConditionProperty != null) {
			visibleCondition = visibleConditionProperty.getValue();
		}
		// Property enabledConditionProperty =
		// m.getProperty("EnabledCondition");
		// if (enabledConditionProperty != null) {
		// enabledCondition = enabledConditionProperty.getValue();
		// }
		Property validationConditionProperty = m
				.getProperty("ValidationCondition");
		if (validationConditionProperty != null) {
			validationCondition = validationConditionProperty.getValue();
		}
		List<PropertyComponent> propertyList = getRecursiveProperties();
		for (int i = 0; i < propertyList.size(); i++) {
			PropertyComponent o = propertyList.get(i);
			Property pp = m.getProperty(o.getPropertyName());
			if (pp != null) {
				o.setProperty(pp);
			}
			if ("Value".equals(o.getPropertyName())) {
				// valueComponent = o;
			}
			o.addTipiEventListener(new TipiEventListener() {
				public boolean performTipiEvent(String eventtype,
						Map<String, Object> source, boolean sync)
						throws TipiException {
					updateQuestionList();
					return true;
				}

				public void eventStarted(TipiExecutable te, Object event) {
				}

				public void eventFinished(TipiExecutable te, Object event) {
				}
			});
		}
		// TODO: Change this to a recursive search:
		TipiDataComponent tdc = null;
		if (subQuestionPath != null) {
			tdc = (TipiDataComponent) getTipiComponentByPath(subQuestionPath);
		} else {
		}
		Message question = m.getMessage("Question");

		if (question != null && tdc != null) {
			for (int i = 0; i < question.getArraySize(); i++) {
				Message current = question.getMessage(i);
				Property idProp = current.getProperty("Id");
				if (idProp == null) {
					continue;
				}
				myId = idProp.getValue();
				try {
					TipiBaseQuestion tc = (TipiBaseQuestion) TipiInstantiateTipi
							.instantiateByDefinition(tdc, false, myId,
									questionDefinitionName, null, null);
					tc.setValue("messagePath", current.getFullMessageName());
					tc.setValue("questionDefinitionName",
							questionDefinitionName);
					tc.loadData(n, myContext);
					tc.setQuestionGroup(questionGroup);
					tc.setQuestionList(myQuestionList);
					// mySubQuestions.add(tc);
				} catch (TipiException ex) {
					logger.error("Error: ",ex);
				}
			}
			updateSubQuestions();
		}

	}

	protected abstract void setQuestionBorder(String val);

	public void updateQuestionGroup() {
		if (questionGroup != null) {
			questionGroup.updateQuestions();
		}
	}

	public void updateQuestionList() {
		if (myQuestionList != null) {
			myQuestionList.updateQuestionList();
		}
	}

	public boolean isRelevant() {
		// if (true) {
		// return true;
		// }
		if (visibleCondition != null) {
			Operand o = myContext.evaluate(visibleCondition, this, null,
					myMessage);
			if (o != null) {
				return ((Boolean) o.value).booleanValue();
			}
			return false;
		}
		return true;
	}

	public abstract void setQuestionVisible(boolean b);

	public void updateSubQuestions() {
		if (visibleCondition != null) {
			setQuestionVisible(isRelevant());
		}
		boolean invalidFound = false;
		List<TipiComponent> subQ = getSubQuestionList();
		for (int i = 0; i < subQ.size(); i++) {
			TipiBaseQuestion tq = (TipiBaseQuestion) subQ.get(i);
			tq.updateSubQuestions();
			if (tq.isValid() == false) {
				invalidFound = true;
				logger.warn("FOUND AN INVALID CHILD: " + tq.getPath());
			}
		}
		if (invalidFound) {
			setValid(false, "Subvraag incorrect");
		} else {
			if (!isValid()) {
				setValid(false);
			} else {
				setValid(true);
			}
		}
	}

	private List<TipiComponent> getSubQuestionList() {
		List<TipiComponent> subQ = new ArrayList<TipiComponent>();
		TipiComponent tdc = null;
		if (subQuestionPath != null) {
			tdc = getTipiComponentByPath(subQuestionPath);
			if (tdc == null) {
				logger.info("::: NULL subcomponent");
			} else {
				recursiveListQuestions(tdc, subQ);
			}
		} else {
		}
		return subQ;
	}

	public boolean isRecursiveValid() {
		if (!isRelevant()) {
			return true;
		}
		if (!isValid()) {
			return false;
		}
		List<TipiComponent> subQ = getSubQuestionList();
		for (int i = 0; i < subQ.size(); i++) {
			TipiBaseQuestion tq = (TipiBaseQuestion) subQ.get(i);
			if (tq.isValid() == false) {
				return false;
			}
		}
		return true;
	}

	public void setValid(boolean b) {
		Property errorMessageProperty = myMessage.getProperty("ErrorMessage");
		String errorMessage = null;
		String msg = "Error";
		if (errorMessageProperty != null) {
			errorMessage = errorMessageProperty.getValue();
		}
		if (errorMessage != null) {
			Operand o = myContext.evaluate(errorMessage, this, null, myMessage);
			msg = "" + o.value;
		}
		setValid(b, msg);
	}

	public abstract void setValid(boolean b, String msg);

	public boolean isValid() {
		if (!isRelevant()) {
			return true;
		}
		if (validationCondition != null) {
			Operand o = myContext.evaluate(validationCondition, this, null,
					myMessage);
			if (o != null) {
				boolean result = ((Boolean) o.value).booleanValue();
				return result;
			}
			logger.error("No evaluation. Shit.");
			return true;
		}
		return true;
	}

	public void load(XMLElement def, XMLElement instance, TipiContext context)
			throws TipiException {
		super.load(def, instance, context);
	}

	public void setContainerLayout(Object layout) {
	}
}
