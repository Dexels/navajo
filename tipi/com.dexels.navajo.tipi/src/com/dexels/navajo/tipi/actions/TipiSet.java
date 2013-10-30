package com.dexels.navajo.tipi.actions;

import com.dexels.navajo.document.Property;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.internal.TipiAction;
import com.dexels.navajo.tipi.internal.TipiEvent;
import com.dexels.navajo.tipi.internal.TipiReference;

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
public final class TipiSet extends TipiAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2911787602531330195L;

	@Override
	public final void execute(TipiEvent event)
			throws com.dexels.navajo.tipi.TipiException,
			com.dexels.navajo.tipi.TipiBreakException {
		String path = getParameter("element").getValue();

		Object element = getEvaluatedParameterValue("element", event);

		Object value = getEvaluatedParameterValue("value", event);
		if (element == null) {
			throw new TipiException(
					"Error in setValue: to evaluation failed. Expression: "
							+ path + " (from: " + value + ")");
		}
		if (value != null || hasParameter("value")) {
			if (element instanceof Property) {
				Property p = (Property) element;
				p.setAnyValue(value);
			} else if (element instanceof TipiReference) {
				TipiReference p = (TipiReference) element;
				p.setValue(value);
			} else {
				throw new TipiException(
						"Error in setValue: illegal 'to' parameter. Expression: "
								+ path + " (from: " + value + ")");
			}

		}
		if (hasParameter("direction")) {
			String direction = (String) getEvaluatedParameterValue("direction",
					event);
			if (element instanceof Property) {
				Property p = (Property) element;
				p.setDirection(direction);
			} else {
				throw new TipiException(
						"Error in set: Can't change direction of attributeref:"
								+ path);
			}
		}
		if (hasParameter("description")) {
			String description = (String) getEvaluatedParameterValue(
					"description", event);
			if (element instanceof Property) {
				Property p = (Property) element;
				p.setDescription(description);
			} else {
				throw new TipiException(
						"Error in set: Can't change description of attributeref:"
								+ path);
			}
		}
		if (hasParameter("propertyType")) {
			String type = (String) getEvaluatedParameterValue("propertyType",
					event);
			if (element instanceof Property) {
				Property p = (Property) element;
				p.setType(type);
			} else {
				throw new TipiException(
						"Error in set: Can't change type of attributeref:"
								+ path);
			}
		}

		if (hasParameter("subType")) {
			String type = (String) getEvaluatedParameterValue("propertyType",
					event);
			if (element instanceof Property) {
				Property p = (Property) element;
				p.setSubType(type);
			} else {
				throw new TipiException(
						"Error in set: Can't subType type of attributeref:"
								+ path);
			}
		}

	}
}
