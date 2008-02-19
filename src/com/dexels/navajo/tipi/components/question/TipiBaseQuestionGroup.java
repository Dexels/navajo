package com.dexels.navajo.tipi.components.question;

import java.util.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.actions.*;
import com.dexels.navajo.tipi.components.core.*;
import com.dexels.navajo.tipi.internal.*;

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

public abstract class TipiBaseQuestionGroup extends TipiDataComponentImpl {
	protected String messagePath = null;

	protected String questionDefinitionName = null;

	protected String questionGroupDefinitionName = null;

	protected final List<TipiBaseQuestion> myQuestions = new ArrayList<TipiBaseQuestion>();

	protected TipiBaseQuestionList myQuestionList = null;

	private String subComponent = null;

	private String subConstraint = null;

//	private String subQuestionPath;

	public TipiBaseQuestionGroup() {
	}

	public void setQuestionList(TipiBaseQuestionList tql) {
		myQuestionList = tql;
	}

	public void removeFromContainer(Object c) {
		// panel.remove((Component)c);
	}

	public void setComponentValue(String name, Object object) {
		if (name.equals("messagePath")) {
			messagePath = (String) object;
		}
		if (name.equals("questionDefinitionName")) {
			questionDefinitionName = (String) object;
		}
		if (name.equals("questionGroupDefinitionName")) {
			questionGroupDefinitionName = (String) object;
		}
		if (name.equals("subComponent")) {
			subComponent = (String) object;
		}
		if (name.equals("subConstraint")) {
			subConstraint = (String) object;
		}

//		if (name.equals("subQuestionPath")) {
//			subQuestionPath = (String) object;
//		}

		super.setComponentValue(name, object);
	}

	public Object getComponentValue(String name) {
		if (name.equals("valid")) {
			return new Boolean(isValid());
		}
		if (name.equals("description")) {
			return getDescription();
		}
		return super.getComponentValue(name);
	}

	private void recursiveListQuestions(TipiComponent start, List<TipiBaseQuestion> result) {
		if (start instanceof TipiBaseQuestion) {
			result.add((TipiBaseQuestion)start);
		}
		for (int i = 0; i < start.getChildCount(); i++) {
			recursiveListQuestions(start.getTipiComponent(i), result);
		}
	}

	public boolean isValid() {
		List<TipiBaseQuestion> l = new ArrayList<TipiBaseQuestion>();
		recursiveListQuestions(this, l);
		// System.err.println("QUESTIONS:::: "+l.size());
		for (int i = 0; i < l.size(); i++) {
			TipiBaseQuestion tq = l.get(i);
			if (!tq.isRecursiveValid()) {
				return false;
			}
		}
		// System.err.println("No invalid questions found. Group is valid.");
		return true;
	}

	public void loadData(Navajo n, String method) throws TipiException {
		// removeInstantiatedChildren();

		// NO Super.loaddata?!
		myQuestions.clear();

		TipiComponent currentComponent = this;

		Message m = n.getMessage(messagePath);
		if (m == null) {
			return;
		}

		if (subComponent != null) {
			currentComponent = getTipiComponentByPath(subComponent);
			if (currentComponent == null) {
				currentComponent = this;
			}

		}
		List<TipiComponent> localprops = getRecursiveProperties();
		for (int i = 0; i < localprops.size(); i++) {
			PropertyComponent o = (PropertyComponent) localprops.get(i);
			Property pp = m.getProperty(o.getPropertyName());
			if (pp != null) {
				o.setProperty(pp);

			}
		}

		Message question = m.getMessage("Question");
		if (question != null) {
			for (int i = 0; i < question.getArraySize(); i++) {
				Message current = question.getMessage(i);
				String id = current.getProperty("Id").getValue();
				TipiBaseQuestion tc = (TipiBaseQuestion) TipiInstantiateTipi.instantiateByDefinition(currentComponent, false, id,
						questionDefinitionName, subConstraint);
				tc.setValue("messagePath", current.getFullMessageName());
				tc.setValue("questionDefinitionName", questionDefinitionName);
				tc.setQuestionGroup(this);
				tc.setQuestionList(myQuestionList);
				tc.loadData(n, myContext);
				myQuestions.add(tc);
			}
		}
		updateQuestions();
		myQuestionList.setGroupValid(isValid(), this);
	}

	public String getDescription() {
		Navajo n = getNavajo();
		if (n == null) {
			return "-";
		}
		Message m = n.getMessage(messagePath);
		if (m == null) {
			return "--";
		}
		Property p = m.getProperty("Description");
		if (p == null) {
			return "---";
		}
		return "" + p.getValue();
	}

	public void updateQuestions() {
		for (int i = 0; i < myQuestions.size(); i++) {
			TipiBaseQuestion tq = myQuestions.get(i);
			tq.updateSubQuestions();
		}

		boolean valid = isValid();
		if (myQuestionList != null) {
			myQuestionList.setGroupValid(valid, this);
		}
	}

}
