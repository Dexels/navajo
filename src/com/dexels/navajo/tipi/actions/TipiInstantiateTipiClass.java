package com.dexels.navajo.tipi.actions;

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
public class TipiInstantiateTipiClass extends TipiInstantiateTipi {
	public void execute(TipiEvent event) throws com.dexels.navajo.tipi.TipiException, com.dexels.navajo.tipi.TipiBreakException {
		instantiateTipi(true, event);
	}

	protected void instantiateTipi(boolean byClass, TipiEvent event) throws TipiException {

		Boolean force = (Boolean) getEvaluatedParameterValue("force", event);
		if(force==null) {
			force = Boolean.FALSE;
		}
		String id = (String) getEvaluatedParameterValue("id", event);
		String constraints = (String) getEvaluatedParameterValue("constraints", event);
		
		String location = null;
		TipiComponent parent = null;
		
		Object eval = getEvaluatedParameterValue("location", event);
		if(eval instanceof String) {
			location = (String) eval;
			if(location.startsWith("/")) {
				parent = myContext.getTipiComponentByPath(location);
			} else {
				parent = myComponent.getTipiComponentByPath(location);
			}

		} else {
			parent = (TipiComponent)eval;
		}
		String className = (String) getEvaluatedParameter("class", event).value;
		instantiateTipi(myContext, myComponent, byClass, parent, force, id, className, null,
				parameterMap, constraints);
	}
}