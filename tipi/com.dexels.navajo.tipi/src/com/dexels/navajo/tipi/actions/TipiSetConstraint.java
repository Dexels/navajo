/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.actions;

import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.TipiSuspendException;
import com.dexels.navajo.tipi.internal.TipiAction;
import com.dexels.navajo.tipi.internal.TipiEvent;

public class TipiSetConstraint  extends TipiAction {

	private static final long serialVersionUID = 6907943940877078785L;

	@Override
	protected void execute(TipiEvent event) throws TipiBreakException,
			TipiException, TipiSuspendException {
		// TODO Auto-generated method stub
		TipiComponent component = (TipiComponent) getEvaluatedParameterValue("path", event);
		String constraint = (String) getEvaluatedParameterValue("constraint", event);
		TipiComponent parent = component.getTipiParent();
		Object constr = parent.getLayout().parseConstraint(constraint, 0);
		component.setConstraints(constr);
		parent.getLayout().addToLayout(component.getContainer(), constr);
		parent.getLayout().doUpdate();
		
	}

}
