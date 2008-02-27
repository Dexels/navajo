package com.dexels.navajo.tipi.actions;

import java.util.*;

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

	@SuppressWarnings("unchecked")
	private void setByValue(String path, Operand evaluated, Operand evaluatedValue) throws TipiException {
		if (evaluated.value instanceof Property) {
			Property p = (Property) evaluated.value;
			if(evaluatedValue.value instanceof String) {
				try {
					Selection s = p.getSelectionByValue((String) evaluatedValue.value);
					p.setSelected(s);

				} catch (NavajoException e) {
					e.printStackTrace();
				}
			} 
			if(evaluatedValue.value instanceof List) {
				List<Selection> s = (List<Selection>)evaluatedValue.value;
				ArrayList<String> keys = new ArrayList<String>();
				for (Selection selection : s) {
					keys.add(selection.getValue());
				}
				try {
					p.setSelected(keys);
				} catch (NavajoException e) {
					e.printStackTrace();
				}
			}
		} else  {
			throw new TipiException("Error in selectValue: illegal 'to' parameter. Expression: "+path+" (from: "+evaluated.value.getClass()+")");
		}
	}
	
	@SuppressWarnings("unchecked")
	private void setByName(String path, Operand evaluated, Operand evaluatedName) throws TipiException {
		if (evaluated.value instanceof Property) {
			Property p = (Property) evaluated.value;
			try {
				System.err.println("In set by name: "+p.getFullPropertyName()+" cardinality: "+p.getCardinality());
				System.err.println("NAME: "+evaluatedName.value);
				System.err.println("CLASS: "+evaluatedName.value.getClass());
				if(evaluatedName.value instanceof String) {
					System.err.println("String found: "+evaluatedName.value);
					Selection s = p.getSelection((String) evaluatedName.value);
					p.setSelected(s);
				} 
				if(evaluatedName.value instanceof ArrayList) {
					ArrayList<Selection> l = (ArrayList<Selection>)evaluatedName.value;
					System.err.println("ArrayList Found");
					if(l.size()==0) {
						System.err.println("Empty");
						p.setSelected(new ArrayList<String>());
					}
					if(l.size()==1) {
						Selection ss = l.get(0);
						System.err.println("Single. Setting to name: "+ss.getValue());
						Selection s = p.getSelection(ss.getValue());
						p.setSelected(s);
					}
				} 
				
			} catch (NavajoException e) {
				e.printStackTrace();
			}
			
			
			
			
		} else  {
			throw new TipiException("Error in selectValue: illegal 'to' parameter. Expression: "+path+" (from: "+evaluated.value.getClass()+")");
		}
	}
}
