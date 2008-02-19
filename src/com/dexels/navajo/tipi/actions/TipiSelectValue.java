package com.dexels.navajo.tipi.actions;

import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.internal.*;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */
public final class TipiSelectValue extends TipiAction {

	public final void execute(TipiEvent event) throws com.dexels.navajo.tipi.TipiException, com.dexels.navajo.tipi.TipiBreakException {

		String path = getParameter("property").getValue();
//		String value = getParameter("value").getValue();
		Operand evaluated = evaluate(path, event);
		Operand evaluatedValue = getEvaluatedParameter("value", event); // evaluate(value, event);
		Operand evaluatedName = getEvaluatedParameter("name", event); // evaluate(value, event);
		
		if (evaluated == null || evaluated.value==null) {
			throw new TipiException("Error in selectValue: to evaluation failed. Expression: "+path+" (value: "+evaluatedValue.value+")");
		}
		if(evaluatedValue==null && evaluatedName == null) {
			throw new TipiException("Either select a name or a value attribute.");
		}
		if(evaluatedValue!=null && evaluatedName != null) {
			throw new TipiException("Either select a name or a value attribute, not both");
		}
		if (evaluatedValue == null) {
			setByName(path, evaluated, evaluatedName);
		} else {
			setByValue(path, evaluated, evaluatedValue);
			
		}
	}

	private void setByValue(String path, Operand evaluated, Operand evaluatedValue) throws TipiException {
		if (evaluated.value instanceof Property) {
			Property p = (Property) evaluated.value;
			try {
				Selection s = p.getSelectionByValue((String) evaluatedValue.value);
				p.setSelected(s);

			} catch (NavajoException e) {
				e.printStackTrace();
			}
		} else  {
			throw new TipiException("Error in selectValue: illegal 'to' parameter. Expression: "+path+" (from: "+evaluated.value.getClass()+")");
		}
	}
	
	private void setByName(String path, Operand evaluated, Operand evaluatedName) throws TipiException {
		if (evaluated.value instanceof Property) {
			Property p = (Property) evaluated.value;
			try {
				Selection s = p.getSelection((String) evaluatedName.value);
				p.setSelected(s);
			} catch (NavajoException e) {
				e.printStackTrace();
			}
		} else  {
			throw new TipiException("Error in selectValue: illegal 'to' parameter. Expression: "+path+" (from: "+evaluated.value.getClass()+")");
		}
	}
}
